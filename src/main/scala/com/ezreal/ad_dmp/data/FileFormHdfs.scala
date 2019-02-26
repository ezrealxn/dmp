package com.ezreal.ad_dmp.data

import com.ezreal.ad_dmp.pojo.AdData
import com.ezreal.ad_dmp.utils.SparkUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset}

object FileFormHdfs {
  def main(args: Array[String]): Unit = {
    /** 创建一个sparkSession对象 */
    val spark = SparkUtil.getSession()
    import spark.implicits._
    /** 读取文件 */
    val ds1: Dataset[String] = spark.read.textFile("hdfs://ezreal1:9000/dmp/logs")
    /** 过滤1次 */
    val ds2: Dataset[String] = ds1.filter(str => {
      /** 切割 */
      val strs: Array[String] = str.split(",")
      /** 判断切割长度等于85 */
      strs.length == 85
    })
    /** 一个将字符串数组转换成样例类的函数 */
    val dataToCase:Array[String] => AdData = (split:Array[String]) =>{
      try {
        AdData(split(0), split(1).toInt, split(2).toInt, split(3).toInt, split(4).toInt, split(5).toInt,
          split(6), split(7).toInt, split(8).toInt, split(9).toDouble, split(10).toDouble,
          split(11), split(12), split(13), split(14), split(15),
          split(16), split(17).toInt, split(18), split(19), split(20).toInt,
          split(21).toInt, split(22), split(23), split(24), split(25),
          split(26).toInt, split(27), split(28).toInt, split(29), split(30).toInt,
          split(31).toInt, split(32).toInt, split(33), split(34).toInt, split(35).toInt,
          split(36).toInt, split(37), split(38).toInt, split(39).toInt, split(40).toDouble,
          split(41).toDouble, split(42).toInt, split(43), split(44).toDouble, split(45).toDouble,
          split(46), split(47), split(48), split(49), split(50),
          split(51), split(52), split(53), split(54), split(55),
          split(56), split(57).toInt, split(58).toDouble, split(59).toInt, split(60).toInt,
          split(61), split(62), split(63), split(64), split(65),
          split(66), split(67), split(68), split(69), split(70),
          split(71), split(72), split(73).toInt, split(74).toDouble, split(75).toDouble,
          split(76).toDouble, split(77).toDouble, split(78).toDouble, split(79), split(80),
          split(81), split(82), split(83), split(84).toInt
        )
      } catch {
        case e:Exception => null
      }
    }
    /** 过滤一下包含null的 */
    val rdd1: RDD[AdData] = ds2.rdd.map(line => {
      /** 用一个函数来把字符串数组转换成样例类 */
      dataToCase(line.split(","))
    }).filter(_ != null)

    /** 把RDD转换为Dataset */
    val df1: DataFrame = rdd1.toDF()

    df1.write.parquet("hdfs://ezreal1:9000/dmp/parquet/")

    spark.close()
  }
}
