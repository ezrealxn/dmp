package com.ezreal.ad_dmp.demand

import com.ezreal.ad_dmp.pojo.AreaResult
import com.ezreal.ad_dmp.utils.{Demand2Kpi, SparkUtil}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * 需求：地域维度KPI报表
  * (1)	数据存储到MySQL中，省市数据分成2个字段存储；
  * (2)	需求的实现采用两种方式进行实现(SQL|Core)；
  * (3)	结果要求一个job完成；
  * (4)	需要按天统计；
  */
object Demand2 {
  def main(args: Array[String]): Unit = {
    /** 获得连接 */
    val spark: SparkSession = SparkUtil.getSession()
    /** 导入隐式转换 */
    import spark.implicits._
    /** 获得数据 */
    val df1: DataFrame = spark.read.parquet("hdfs://ezreal1:9000/dmp/parquet/")
    /** 注册成表 */
    df1.createTempView("t_dmp")
    /** 注册一个sql函数这个函数的意思是传入一个判断，如果成立则返回第一个参数，如果不成立则返回第二个参数 */
    spark.udf.register("paramIf",(b:Boolean,eff:Double,uneff:Double)=>if(b) eff else uneff)
    /** sql
      * 原始请求数	发来的所有原始请求数           rawReq
      * 有效请求数	筛选满足有效条件的请求数量      effReq
      * 广告请求数	筛选满足广告请求条件的请求数量  adReq
      * 参与竞价数	参与竞价的次数                 jjcs
      * 竞价成功数	成功竞价的次数                 jjcgs
      * 展示数	针对广告主统计：广告在终端实际被展示的数量   showNum
      * 点击数	针对广告主统计：广告展示后被受众实际点击的数量  activeNum
      * DSP广告消费	相对于投放DSP广告的广告主来说满足广告成功展示每次消费WinPrice/1000   cost
      * DSP广告成本	相对于投放DSP广告的广告主来说满足广告成功展示每次成本adpayment/1000  cb
      * */
    val df2: DataFrame = spark.sql(
      """
        |select provincename,cityname,substr(requestdate,0,10) reqdate,
        |sum(if(requestmode = 1 and processnode >=1, 1 , 0)) rawReq,
        |sum(case when requestmode =1 and processnode >=2 then 1 else 0 end) effReq,
        |sum(paramIf(requestmode=1 and processnode=3 , 1 , 0)) adReq,
        |sum(paramIf(iseffective =1 and isbilling =1 and isbid =1 and adorderid !=0 , 1 , 0)) jjcs,
        |sum(paramIf(iseffective =1 and isbilling =1 and iswin = 1 , 1 , 0)) jjcgs,
        |sum(paramIf(requestmode=2 and iseffective = 1 , 1 , 0)) showNum,
        |sum(paramIf(requestmode=3  and  iseffective = 1 , 1 , 0)) activeNum,
        |sum(paramIf(iseffective = 1  and  isbilling =1 and iswin = 1, winprice / 1000, 0))cost,
        |sum(paramIf(iseffective = 1  and  isbilling =1 and iswin = 1, adpayment/1000 ,0 )) cb
        |from t_dmp group by provincename,cityname,reqdate
      """.stripMargin)
//    df2.show(10,false)

    /** sparkcore方式 */
    /** 取出一些数据整理一下，把df转换成rdd再进行处理 */
    val rdd1: RDD[((String, String, String), (Int,Int, Int, Int, Int, Int, Int, Double, Double))] = df1.rdd.map(row => {
      val province = row.getAs[String]("provincename")
      val city = row.getAs[String]("cityname")
      val reqDate = row.getAs[String]("requestdate").substring(0, 10)
      ((province, city, reqDate), Demand2Kpi.apply(row))
    })
    /** 分组聚合 */
    val rdd2: RDD[((String, String, String), (Int, Int, Int, Int, Int, Int, Int, Double, Double))] = rdd1.reduceByKey((a, b) => {
      (a._1 + b._1, a._2 + b._2, a._3 + b._3, a._4 + b._4, a._5 + b._5, a._6 + b._6, a._7 + b._7, a._8 + b._8, a._9 + b._9)
    })
    /** 重新将rdd转换成df，封装一个样例类 */
    val result2: DataFrame = rdd2.map(rdd => AreaResult(
      rdd._1._1,
      rdd._1._2,
      rdd._1._3,
      rdd._2._1,
      rdd._2._2,
      rdd._2._3,
      rdd._2._4,
      rdd._2._5,
      rdd._2._6,
      rdd._2._7,
      rdd._2._8,
      rdd._2._9
    )).toDF()
    result2.show(10,false)



    df2.show(10,false)

  }
}
