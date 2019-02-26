package com.ezreal.ad_dmp.demand

import com.ezreal.ad_dmp.utils.SparkUtil
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
    /** 获得数据 */
    val df1: DataFrame = spark.read.parquet("hdfs://ezreal1:9000/dmp/parquet/")
    /** 注册成表 */
    df1.createTempView("t_dmp")
    /** sql */
    val df2: DataFrame = spark.sql("select provincename,cityname,sum(select requestmode from t_dmp where requestmode=1 and processnode>=1) as 'requestNum' from t_dmp group by provincename,cityname")
    df2.show(10,false)
  }
}
