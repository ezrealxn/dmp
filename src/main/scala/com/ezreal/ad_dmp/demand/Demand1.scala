package com.ezreal.ad_dmp.demand

import java.util.Properties

import com.ezreal.ad_dmp.utils.SparkUtil
import org.apache.spark.sql.DataFrame

/**
  * 需求：省市数据分布概况报表
  * 要求将统计结果，写入mysql、并输出成json文件
  */
object Demand1 {
  def main(args: Array[String]): Unit = {
    /** 实例化sparkSession */
    val spark = SparkUtil.getSession()
    /** 读取数据 */
    val df1: DataFrame = spark.read.parquet("hdfs://ezreal1:9000/dmp/parquet/")
    /** spark core方式 */
    val result1: DataFrame = df1.groupBy("requestdate,provincename,cityname").count()

    /** sql的方式 */
    /** 将df注册成表*/
    df1.createTempView("t_dmp")
    /** sql */
    val result: DataFrame = spark.sql("select requestdate,provincename,cityname,count(1) 'active_num' from t_dmp group by requestdate,provincename,cityname")
    /** 一个properties */
    val prop = new Properties()
    prop.setProperty("user","root")
    prop.setProperty("password","root")
    /** 写出到mysql */
    result.write.jdbc("jdbc:mysql://localhost:3306/dmp?useUnicode=true&characterEncoding=UTF-8&useSSL=false","province_dmp",prop)
    /** 再写出json到文件系统 */
    result.coalesce(2).write.json("hdfs://ezreal1:9000/dmp/jsonout/")

    /** 关闭连接 */
    spark.close()

  }
}
