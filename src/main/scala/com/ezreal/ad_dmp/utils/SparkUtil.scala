package com.ezreal.ad_dmp.utils

import org.apache.spark.sql.SparkSession

object SparkUtil {

  def getSession()={
    System.setProperty("HADOOP_USER_NAME","root")
    SparkSession.builder().master("local[*]").appName(this.getClass.getSimpleName).getOrCreate()
  }

}
