package com.manage.project.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常量
 * @author xufeng
 *
 */
public class Constant {
	
	// --------------全局缓存key---------
	
	// 商户缓存key,如corp_cache_8888,为corpid为8888的商户缓存key。以后该商户所有缓存均在此缓存里
	// 如    corp_cache_8888:
	//          corp_vending_cache: 该商户售卖机Map
	public static final String CORP_CACHE_ = "corp_cache_";
	
	// -------------系统参数缓存key----------
	public static final String PARAMETER_CACHE = "parameter_cache";
	// -------------行政区划缓存key----------
	public static final String DISPATCH_CACHE = "dispatch_cache";
	// -------------数据字典缓存key----------
	public static final String DICT_CACHE = "dict_cache";
	// -------------商户信息缓存key----------
	public static final String CORP_CACHE = "corp_cache";
	// -------------机型信息缓存key----------
	public static final String DEVICE_CACHE = "device_cache";
	// -------------区域信息缓存key----------
	public static final String VENDING_DISPATCH_CACHE = "vending_dispatch_cache";
	// -------------线路信息缓存key----------
	public static final String VENDING_LINE_CACHE = "vending_line_cache";
	// -------------线路信息缓存key----------
	public static final String VENDING_POINT_CACHE = "vending_point_cache";
	// -------------售卖机数量缓存key----------
	public static final String VENDING_NUM_CACHE_ = "vending_num_cache_";
	// -------------商品信息缓存key----------
	public static final String PRODUCT_CACHE = "product_cache";
	// -------------商品分类信息缓存key----------
	public static final String PRODUCT_CLASS_CACHE = "product_class_cache";
	// -------------人员信息缓存key----------
	public static final String USER_CACHE = "user_cache";
	public static final String LOGIN_USER_CACHE = "login_user_cache";
	public static final String LOGIN_NAME_USER_CACHE = "login_name_user_cache";
	// -------------售卖机基础信息缓存key----------
	public static final String VENDING_BASE_CACHE = "vending_base_cache";
	// -------------仓库信息缓存key----------
	public static final String STOCKINFO_CACHE = "stock_info_cache";
	// -------------支付配置测试缓存 -------------
	public static final String PAY_TEST = "pay_test_";
	
	// ---------缓存key----------
//	public static final String INDEX_CORP_CACHE_ = "index_corp_cache_";	// 首页各商户缓存key，如index_corp_cache__8888,8888为corpId
	// ---------下面首页缓存在INDEX_CORP_CACHE_各商户缓存内部 ------------
	public static final String INDEX_SUMMARY_TOTALREVIEW_CACHE_ = "index_summary_totalreview_cache_";	// 首页运营总览缓存key
	public static final String INDEX_SUMMARY_ONEMONTH_CACHE_ = "index_summary_onemonth_cache_";	// 首页近一月总览缓存key
	public static final String INDEX_SUMMARY_YS_CACHE_ = "index_summary_ys_cache_";	// 首页营收分析缓存key
	public static final String INDEX_SUMMARY_YS6_CACHE_ = "index_summary_ys6_cache_";	// 首页营收分析,近半年缓存key
	public static final String INDEX_SUMMARY_YS12_CACHE_ = "index_summary_ys12_cache_";	// 首页营收，近1年分析缓存key
	public static final String INDEX_SUMMARY_TODAYSALE_CACHE_ = "index_summary_todaysale_cache_";	// 首页今日销售汇总缓存key
	public static final String INDEX_SUMMARY_TODAYDW_CACHE_ = "index_summary_todaydw_cache_";	// 首页今日销售点位top10排行缓存key
	public static final String INDEX_SUMMARY_TODAYSP_CACHE_ = "index_summary_todaysp_cache_";	// 首页今日销售商品排行缓存key

	// --------首页使用---------
	public static final int INDEX_DATA_TYPE_1 = 1;	// 首页运营总览
	public static final int INDEX_DATA_TYPE_2 = 2;	// 首页近一月总览
	public static final int INDEX_DATA_TYPE_2_1 = 1;	// 首页近一月总览,今天
	public static final int INDEX_DATA_TYPE_2_2 = 2;	// 首页近一月总览，昨天
	public static final int INDEX_DATA_TYPE_2_3 = 3;	// 首页近一月总览,近7日
	public static final int INDEX_DATA_TYPE_2_4 = 4;	// 首页近一月总览,近30日
	public static final int INDEX_DATA_TYPE_3 = 3;	// 首页营收分析近半年
	public static final int INDEX_DATA_TYPE_4 = 4;	// 首页营收分析近1年
	public static final int INDEX_DATA_TYPE_5 = 5;	// 首页今日销售汇总
	public static final int INDEX_DATA_TYPE_6 = 6;	// 首页今日销售点位排行
	public static final int INDEX_DATA_TYPE_7 = 7;	// 首页今日销售商品排行
	public static final int INDEX_DATA_TYPE_8 = 8;	// 大屏今日销售点位排行
	
	// ---------as_report_osale 表的 saleType 字段类型-------
	public static final String SALE_TYPE_PRODUCT = "1";	// 1:商品 
	public static final String SALE_TYPE_POINT = "2";	// 2:点位 
	public static final String SALE_TYPE_LINE = "3";	// 3:线路
	
	// ---------外挂类型 1:是 2:否-------
	public static final String HANG_TYPE_TRUE = "1";	// 1:是
	public static final String HANG_TYPE_FALSE = "2";	// 2:否 
	
	// ---------网络状态(0:在线 1:离线)-------
	public static final String NET_STAE_ONLINE = "0";
	public static final String NET_STAE_OFFLINE = "1";
	
	// ---------售货机状态 1:已认证售货机 2:未认证售货机 3:已删除售货机-------
	public static final String VENDING_CURSTATE_REGISTER = "1";
	public static final String VENDING_CURSTATE_NOTREGISTER = "2";
	public static final String VENDING_CURSTATE_DELETE = "3";
	
	// ---------采购状态 1:正常 2:删除-------
	public static final String PURCHASE_CUR_STATE_NORMAL = "1";	// 1:正常
	public static final String PURCHASE_CUR_STATE_DELETE = "2";	// 2:删除
	
	// ---------审核状态 1:未审核 2:审核通过 3:审核失败-------
	public static final String PURCHASE_CHECK_STATE_WAIT = "1";
	public static final String PURCHASE_CHECK_STATE_SUCCESS = "2";
	public static final String PURCHASE_CHECK_STATE_FAIL = "3";
	
	// ---------库存状态 1:未入库 2:已入库-------
	public static final String STOCK_STATE_WAIT = "1";
	public static final String STOCK_STATE_SUCCESS = "2";
	
	// ---------入库类型 1:正常入库 2:冲正-------
	/**正常入库*/
	public static final String INBOUND_TYPE_NORMAL = "1";
	/**冲正*/
	public static final String INBOUND_TYPE_UPDATE = "2";
	
	// -------------其它-------
	public static final String DEFAULT_CORP_ID = "8888";	// 默认托管公司编号	
	public static final String ZHILAI_CORP_ID = "8888";	// 宇宙星空公司编号
    //-------------补货类型 1:全部齐 2:阈值补齐-------
	public static final String supplyType_yuzhi="2";//阈值补齐
	// -------------supplyconfig表名-------
	public static final String supplyConfig_table="as_supply_config";//阈值补齐
	public static final String supplyOrder_table="as_supply_order";//阈值补齐
	public static final String supplyVProduct_table="as_supply_vproduct";//阈值补齐
	public static final String supplyProduct_table="as_supply_product";//阈值补齐
	// -------------库存等级-------
	public static final int storyLevel_frist=1;//库存等级1
	public static final int storyLevel_two=2;//库存等级2
	public static final int storyLevel_three=3;//库存等级3

	// --------------补货配置表达式值-------------
	public static final String strategyEvery_week="1";
	public static final String strategyEvery_month="2";

	// --------------补货配置状态-------------
	public static final String supplyConfigCur_finsh="2";//已完成补货
	public static final String supplyConfigCur_wait="1";//等待补货

	// --------------补货记录状态-------------
	public static final String SUPPLY_ORDER_CUR_STATE_FINISH="2";//已完成补货
	//public static final String supplyOrderCur_finshing="2";//补货中
	public static final String SUPPLY_ORDER_CUR_STATE_WAIT="1";//等待补货

	// --------------补货订单完成状态-------------
	public static final String supplyOrderCur_noFinsh="0";//未完成
	public static final String supplyOrderCur_alFinsh="1";//正常完成
	public static final String supplyOrderCur_overFinsh="2";//超期完成

	// --------------补货订单库存审核状态-------------
	public static final String supplyConfigCheck_noCheck="1";//未审核
	public static final String supplyConfigCur_alOut="2";//已出库

	// --------------站点货道补货商品信息状态-------------
	public static final String supplyVProductCur_waitSupply="1";//等待补货
	public static final String supplyConfigCur_alFinsh="2";//已完成补货

	// --------------站点货道补货商品信息状态-------------
	public static final String supplyVProductOut_noOut="1";//未出柜
	public static final String supplyConfigOut_alOut="2";//已出柜

	// --------------站点货道补货商品信息状态-------------
	public static final String supplyVProductOverTime_noOver="1";//已过期
	public static final String supplyConfigOut_alOver="2";//未过期

	// ---------商品下架状态 1:等待下架 2:已下架-------
	public static final String UNDER_STATE_WAIT = "1";
	public static final String UNDER_STATE_FINISH = "2";
//	public static final String UNDER_STATE_3 = "3";
	
	/**下发类型 1:立即升级 */
	public static final String UPGRADE_ISSUEDTYPE_NOW="1";
	/**下发类型  2:延迟升级*/
	public static final String UPGRADE_ISSUEDTYPE_LATER="2";
	
	// 图片返回地址
//	public static final String BACKPRPFILE="/img/";

	//商品图片地址
	public static final String PRPDUCTIMGPROFILE = "/front/img/product/";
	//广告图片地址
	public static final String ADVERTIMGPROFILE = "/front/img/advert/";
	//商品分类图片地址
	public static final String PRPDUCTCLASSIFYIMGPROFILE = "/front/img/productclassify/";
	//用户图片地址
	public static final String USERIMGPROFILE = "/front/img/user/";

	//支付配置操作
	public static final String PAYCONFIG_INSERT = "00";//新增
	public static final String PAYCONFIG_UPDATE = "01";//修改
	public static final String PAYCONFIG_DELETE = "02";//删除
	
	public static final String PAYSTATEWAIT = "1";	//等待支付
	public static final String PAYSTATESUCCESS = "2";	//支付成功
	public static final String PAYSTATEFAIL = "3";	//支付失败

	//售货机货道状态
	/**售货机货道状态:正常*/
	public static final String VENDING_LANESTATE_NORMAL = "1";	
	/**售货机货道状态:等待禁用*/
	public static final String VENDING_LANESTATE_WAIT_LOCK= "2";
	/**售货机货道状态:禁用*/
	public static final String VENDING_LANESTATE_LOCKED= "3";
	/**售货机货道状态:等待解锁*/
	public static final String VENDING_LANESTATE_WAIT_UNLOCK= "4";
	/**售货机货道状态:损坏*/
	public static final String VENDING_LANESTATE_DAMAGE= "5";

	/**售货机平台版本 安卓*/
	public static final String PLATTYPE_ANDROID = "01";
	/**售货机平台版本 windows*/
	public static final String PLATTYPE_WINDOWS = "02";
	/**售货机平台版本 单片机*/
	public static final String PLATTYPE_SINGLECHIP = "03";
	/**售货机平台版本 基本款*/
	public static final String PLATTYPE_BASE = "04";
	
	//图片上传
//	/**文件类型-文本*/
//	public static final String FILE_TYPE_TEXT="01";
//	/**文件类型-图片*/
//	public static final String FILE_TYPE_PICTURE="02";
//	/**文件类型-视频*/
//	public static final String FILE_TYPE_VIDEO="03";
	/**文件类型-文本*/
	public static final String MEDIA_TYPE_TEXT="3";
	/**文件类型-图片*/
	public static final String MEDIA_TYPE_PICTURE="2";
	/**文件类型-视频*/
	public static final String MEDIA_TYPE_VIDEO="1";
	/**图片类型-商品*/
	public static final String TYPE_PRODUCT="goods";
	/**图片类型-广告*/
	public static final String TYPE_ADVERT="advert";
	/**图片类型-微信证书*/
	public static final String TYPE_LICENSE="license";

	/**升级任务状态 01:准备升级*/
	public static final String UPGRADE_TASK_STATE_WAIT="01";
	/**升级任务状态 02:正在升级*/
	public static final String UPGRADE_TASK_STATE_UPDATING="02";
	/**升级任务状态 03:升级成功*/
	public static final String UPGRADE_TASK_STATE_SUCCESS="03";
	/**升级任务状态 04:升级失败*/
	public static final String UPGRADE_TASK_STATE_FAILED="04";
	
	/**升级任务状态 1:app升级*/
	public static final String UPGRADE_TYPE_APP="1";
	/**升级任务状态 2:固件升级*/
	public static final String UPGRADE_TYPE_FIRMWARE="2";
	/**升级任务状态 3:vcs升级*/
	public static final String UPGRADE_TYPE_VCS="3";

	/**售货机命令类型 01:售货机*/
	public static final String VENDING_CMD_TYPE_VENDING="01";
	/**售货机命令类型 02:商品*/
	public static final String VENDING_CMD_TYPE_PRODUCT="02";
	/**售货机命令类型 03:订单*/
	public static final String VENDING_CMD_TYPE_ORDER="03";
	
	/**执行状态 0:执行中*/
	public static final String VENDING_CMD_STATE_EXCUTING="0";
	/**执行状态 1:执行成功*/
	public static final String VENDING_CMD_STATE_SUCCESS="1";
	/**执行状态 2:执行失败*/
	public static final String VENDING_CMD_STATE_FAILED="2";
	
	/**是否推送命令:否*/
	public static final String IS_PUSH_FALSE="0";
	/**是否推送命令:是*/
	public static final String IS_PUSH_TRUE="1";
	
	/**广告状态 1:等待执行*/
	public static final String ADVERT_CUS_STATE_WAIT="1";
	/**广告状态 2:执行中*/
	public static final String ADVERT_CUS_STATE_EXCUTING="2";
	/**广告状态 3:失效*/
	public static final String ADVERT_CUS_STATE_INVALID="3";
	/**广告状态 4:已删除*/
	public static final String ADVERT_CUS_STATE_DELETE="4";
	
	/**投放方式 1:立即投放 */
	public static final String ADVERT_DILIVERYTYPE_NOW="1";
	/**投放方式 2:延迟投放*/
	public static final String ADVERT_DILIVERYTYPE_LATER="2";

	/**下发类型 1:全量 */
	public static final String ADVERT_OPER_TYPE_FULL="1";
	/**投放方式 2:增量*/
	public static final String ADVERT_OPER_TYPE_INCREASE="2";
	
	public static Map<String,String> cmdMap = new HashMap<String,String>();
	
	/**售货机命令 0101 系统重启通知*/
	public static final String CMD_SYSTEM_RESTART="0101";
	/**售货机命令 0102 应用重启通知*/
	public static final String CMD_APPLICATION_RESTART="0102";
	/**售货机命令 0103 系统初始化通知*/
	public static final String CMD_SYSTEM_INIT="0103";
	/**售货机命令 0104 停止服务通知*/
	public static final String CMD_STOP_SERVICE="0104";
	/**售货机命令 0105 恢复服务通知*/
	public static final String CMD_RECOVERY_SERVICE="0105";
	/**售货机命令 0106 系统升级通知*/
	public static final String CMD_SYSTEM_UPGRADE="0106";
	/**售货机命令 0107 优惠更新通知*/
	public static final String CMD_FAVOURABLE_UPDATE="0107";
	/**售货机命令 0108 广告更新通知*/
	public static final String CMD_ADVERT_UPDATE="0108";
	/**售货机命令 0109 日志上报通知*/
	public static final String CMD_LOG_REPORT="0109";
	/**售货机命令 0201 货道商品停售*/
	public static final String CMD_VENDING_LANE_LOCK="0201";
	/**售货机命令 0202 货道商品开售*/
	public static final String CMD_VENDING_LANE_UNLOCK=" 0202";
	
	/**上传状态 1:等待上传*/
	public static final String VENDING_LOGFILE_CURSTATE_WAIT="1";
	/**上传状态 2:上传中*/
	public static final String VENDING_LOGFILE_CURSTATE_UPLOAD="2";
	/**上传状态 3:上传完成*/
	public static final String VENDING_LOGFILE_CURSTATE_FINISH="3";
	
	/**文本图标地址*/
	public static final String TEXT_ICON_ADDRESS="/front/img/icon/text.jpg";
	/**视频图标地址*/
	public static final String VIDEO_ICON_ADDRESS="/front/img/icon/video.jpg";
	
	/**文件大小单位列表*/
	public static final List<String> FILE_SIZE_UNIT=Arrays.asList("B","KB","MB","GB","TB","PB");
	
	/**商品包装类型列表*/
	public static final String[] PRODUCT_BAG_TYPE_LIST=new String[]{"瓶装--1","灌装--2","袋装--3","盒装--4","杯装--5"};

	/**下载的excel模板下拉框默认最大行数*/
	public static final int DROP_DOWN_LIST_ROWS = 100;
	
	/**行政区域第一级*/
	public static final int DISPATCH_FIRST_LEVEL=0;
	/**行政区域最后一级*/
	public static final int DISPATCH_LAST_LEVEL=3;
	/**行政区域起始parentId*/
	public static final String DISPATCH_START_PARENT_ID="0";
	
	static {
		cmdMap.put(CMD_SYSTEM_RESTART, "系统重启通知");
		cmdMap.put(CMD_APPLICATION_RESTART, "应用重启通知");
		cmdMap.put(CMD_SYSTEM_INIT, "系统初始化通知");
		cmdMap.put(CMD_STOP_SERVICE, "停止服务通知");
		cmdMap.put(CMD_RECOVERY_SERVICE, "恢复服务通知");
		cmdMap.put(CMD_SYSTEM_UPGRADE, "系统升级通知");
		cmdMap.put(CMD_FAVOURABLE_UPDATE, "优惠更新通知");
		cmdMap.put(CMD_ADVERT_UPDATE, "广告更新通知");
		cmdMap.put(CMD_LOG_REPORT, "日志上报通知");
	}

}
