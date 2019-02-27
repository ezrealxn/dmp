package com.ezreal.ad_dmp.utils

import org.apache.spark.sql.Row

object Demand2Kpi {
  def apply(row:Row):(Int,Int,Int,Int,Int,Int,Int,Double,Double)={
    /** 取出几个需要进行判断的参数 */
    val requestmode = row.getAs[Int]("requestmode")
    val processnode = row.getAs[Int]("processnode")
    val iseffective = row.getAs[Int]("iseffective")
    val isbilling = row.getAs[Int]("isbilling")
    val isbid = row.getAs[Int]("isbid")
    val iswin = row.getAs[Int]("iswin")
    val adorderid = row.getAs[Int]("adorderid")



    /** 先拿 原始请求数，有效请求数，广告请求数  因为这三个的条件栏位都差不多 */
    val (rawReq, effReq, adReq) = if(requestmode==1 && processnode ==3){
      (1,1,1)
    }else if(requestmode ==1 && processnode >=2){
      (1,1,0)
    }else if(requestmode ==1 && processnode >=1){
      (1,0,0)
    }else{
      (0,0,0)
    }
    /** 再拿 参与竞价数，竞价成功数，DSP广告消费 , DSP广告成本 */
    val (cyjjs,jjcgs,cost,cb) = if(iseffective ==1 && isbilling ==1 && isbid ==1 && adorderid!=0){
      (1,0,0d,0d)
    }else if(iseffective ==1 && isbilling ==1 && iswin ==1){
      (0,1,row.getAs[Double]("winprice") / 1000,row.getAs[Double]("dpayment")/1000)
    }else{
      (0,0,0d,0d)
    }

    /** 展示数和点击数 */
    val (show,click) = if(requestmode ==3 && iseffective ==1 ){
      (0,1)
    }else if(requestmode ==2 && iseffective ==1){
      (1,0)
    }else{
      (0,0)
    }

    (rawReq,effReq,adReq,cyjjs,jjcgs,show,click,cost,cb)
  }
}
