package com.ezreal.ad_dmp.example

import com.ezreal.ad_dmp.utils.SparkUtil
import org.apache.spark.sql.DataFrame
/** 这个程序是为了查看一下数据是否正确 */
object Test {
  def main(args: Array[String]): Unit = {
    /** 实例化sparkSession对象 */
    val spark = SparkUtil.getSession()
    /** 读取parquet数据 */
    val frame: DataFrame = spark.read.parquet("hdfs://ezreal1:9000/dmp/parquet/")
    /** 打印schema信息 */
    frame.printSchema()
    /** 查看10条数据 */
    frame.show(10,false)
  }
}
