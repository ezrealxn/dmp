package com.ezreal.ad_dmp.pojo
/**
1	sessionid: String, 会话标识
2	advertisersid: Int, 广告主id
3	adorderid: Int, 广告id
4	adcreativeid: Int, 广告创意id ( >= 200000 : dsp)
5	adplatformproviderid: Int, 广告平台商id (>= 100000: rtb)
6	sdkversion: String, sdk 版本号
7	adplatformkey: String, 平台商key
8	putinmodeltype: Int, 针对广告主的投放模式,1：展示量投放2：点击
9	requestmode: Int, 数据请求方式（1:请求、2:展示、3:点击）
10	adprice: Double, 广告价格
11	adppprice: Double, 平台商价格
12	requestdate: String, 请求时间,格式为：yyyy-m-dd hh:mm:ss
13	ip: String, 设备用户的真实ip 地址
14	appid: String, 应用id
15	appname: String, 应用名称
16	uuid: String, 设备唯一标识
17	device: String, 设备型号，如htc、iphone
18	client: Int, 操作系统（1：android 2：ios 3：wp）
19	osversion: String, 设备操作系统版本
20	density: String, 设备屏幕的密度
21	pw: Int, 设备屏幕宽度
22	ph: Int, 设备屏幕高度
23	long: String, 设备所在经度
24	lat: String, 设备所在纬度
25	provincename: String, 设备所在省份名称
26	cityname: String, 设备所在城市名称
27	ispid: Int, 运营商id
28	ispname: String, 运营商名称
29	networkmannerid: Int, 联网方式id
30	networkmannername:String,联网方式名称
31	iseffective: Int, 有效标识（有效指可以正常计费的）(0：无效1：
32	isbilling: Int, 是否收费（0：未收费1：已收费）
33	adspacetype: Int, 广告位类型（1：banner 2：插屏3：全屏）
34	adspacetypename: String, 广告位类型名称（banner、插屏、全屏）
35	devicetype: Int, 设备类型（1：手机2：平板）
36	processnode: Int, 流程节点（1：请求量kpi 2：有效请求3：广告请
37	apptype: Int, 应用类型id
38	district: String, 设备所在县名称
39	paymode: Int, 针对平台商的支付模式，1：展示量投放(CPM) 2：点击
40	isbid: Int, 是否rtb
41	bidprice: Double, rtb 竞价价格
42	winprice: Double, rtb 竞价成功价格
43	iswin: Int, 是否竞价成功
44	cur: String, values:usd|rmb 等
45	rate: Double, 汇率
46	cnywinprice: Double, rtb 竞价成功转换成人民币的价格
47	imei: String, imei
48	mac: String, mac
49	idfa: String, idfa
50	openudid: String, openudid
51	androidid: String, androidid
52	rtbprovince: String, rtb 省
53	rtbcity: String, rtb 市
54	rtbdistrict: String, rtb 区
55	rtbstreet: String, rtb 街道
56	storeurl: String, app 的市场下载地址
57	realip: String, 真实ip
58	isqualityapp: Int, 优选标识
59	bidfloor: Double, 底价
60	aw: Int, 广告位的宽
61	ah: Int, 广告位的高
62	imeimd5: String, imei_md5
63	macmd5: String, mac_md5
64	idfamd5: String, idfa_md5
65	openudidmd5: String, openudid_md5
66	androididmd5: String, androidid_md5
67	imeisha1: String, imei_sha1
68	macsha1: String, mac_sha1
69	idfasha1: String, idfa_sha1
70	openudidsha1: String, openudid_sha1
71	androididsha1: String, androidid_sha1
72	uuidunknow: String, uuid_unknow tanx 密文
73	userid: String, 平台用户id
74	iptype: Int, 表示ip 类型
75	initbidprice: Double, 初始出价
76	adpayment: Double, 转换后的广告消费
77	agentrate: Double, 代理商利润率
78	lrate: Double, 代理利润率
79	adxrate: Double, 媒介利润率
80	title: String, 标题
81	keywords: String, 关键字
82	tagid: String, 广告位标识(当视频流量时值为视频ID 号)
83	callbackdate: String, 回调时间格式为:YYYY/mm/dd hh:mm:ss
84	channelid: String, 频道ID
85	mediatype: Int 媒体类型：1 长尾媒体2 视频媒体3 独立媒体默认:1
  */
case class AdData(sessionid:String,advertisersid:Int,adorderid:Int,adcreativeid:Int,adplatformproviderid:Int,
                  sdkversion:Int,adplatformkey:String,putinmodeltype:Int,requestmode:Int,adprice:Double,
                  adppprice:Double,requestdate:String,ip:String,appid:String,appname:String,
                  uuid:String,device:String,client:Int,osversion:String,density:String,
                  pw:Int,ph:Int,longi:String,lat:String,provincename:String,
                  cityname:String,ispid:Int,ispname:String,networkmannerid:Int,networkmannername:String,
                  iseffective:Int,isbilling:Int,adspacetype:Int,adspacetypename:String,devicetype:Int,
                  processnode:Int,apptype:Int,district:String,paymode:Int,isbid:Int,
                  bidprice:Double,winprice:Double,iswin:Int,cur:String,rate:Double,
                  cnywinprice:Double,imei:String,mac:String,idfa:String,openudid:String,
                  androidid:String,rtbprovince:String,rtbcity:String,rtbdistrict:String,rtbstreet:String,
                  storeurl:String,realip:String,isqualityapp:Int,bidfloor:Double,aw:Int,
                  ah:Int,imeimd5:String,macmd5:String,idfamd5:String,openudidmd5:String,
                  androididmd5:String,imeisha1:String,macsha1:String,idfasha1:String,openudidsha1:String,
                  androididsha1:String,uuidunknow:String,userid:String,iptype:Int,initbidprice:Double,
                  adpayment:Double,agentrate:Double,lrate:Double,adxrate:Double,title:String,
                  keywords:String,tagid:String,callbackdate:String,channelid:String,mediatype:Int
                 )
