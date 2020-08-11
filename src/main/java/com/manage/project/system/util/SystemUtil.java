package com.manage.project.system.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.project.system.stock.domain.StockInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.common.utils.spring.SpringUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.domain.DictData;
import com.manage.project.system.base.domain.Parameter;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.base.service.SystemService;
import com.manage.project.system.base.service.UserServiceImpl;
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.project.system.vendingPoint.domain.VendingPoint;

/**
 * 项目通用工具类
 * @author xufeng
 *
 */
public class SystemUtil {
	
	private static Logger log = LoggerFactory.getLogger(SystemUtil.class);
	
	/**
	 * 获取系统参数
	 * 
	 * @param name
	 * @return
	 */
	public static String getSysParameter(String name) {
		Object o = CacheUtils.get(Constant.PARAMETER_CACHE);
		if (o == null) {
			return null;
		}
		Map<String, Parameter> map = (Map<String, Parameter>)o;		
		return map.get(name).getValue();
	}
	
	public static String getSeqId(String corpId, String tableName) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		StringBuffer buf = new StringBuffer();
		String id =systemService.getGenderById(corpId, tableName);
		String tmp = "" + id;
		for (int i = 0; i < 7 - tmp.length(); i++)
				buf.append("0");
		buf.append(tmp);
		return corpId+"-"+buf.toString();//+"_"+System.nanoTime();
	}
	
	public static String getSeqIdShort(String corpId, String tableName) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		StringBuffer buf = new StringBuffer();
		String id =systemService.getGenderById(corpId, tableName);
		String tmp = "" + id;
		for (int i = 0; i < 7 - tmp.length(); i++)
				buf.append("0");
		buf.append(tmp);
		return corpId+"-"+buf.toString();
	}
	
	public static String getSeqIdLong(String corpId, String tableName) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		StringBuffer buf = new StringBuffer();
		String id =systemService.getGenderById(corpId, tableName);
		String tmp = "" + id;
		for (int i = 0; i < 7 - tmp.length(); i++)
				buf.append("0");
		buf.append(tmp);
		return corpId+"-"+buf.toString()+"_"+System.nanoTime();
	}

	public static void getCallGenOrderBase(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallGenOrderBase(map);
	}
	
	public static String getCorpId() {
		String corpId = ShiroUtils.getUser().getCorpId();
		if (corpId != null && corpId.equals(Constant.ZHILAI_CORP_ID)) {
			return "";
		} else {
			return corpId;
		}
	}
	
	/**
	 * 根据售卖机编号得到售卖机基础信息
	 * @param siteId
	 * @return
	 */
	public static Vending getVendingBase(String siteId) {
		Object o = CacheUtils.get(Constant.VENDING_BASE_CACHE);
		
		if (o == null) {
			return null;
		}
		Map<String, Vending> map = (Map<String, Vending>)o;		
		Vending v = map.get(siteId);
		return v;
	}
	
	/**
	 * 获取机型
	 * @param deviceId
	 * @return
	 */
	public static VendingModel getVendingModel(String deviceId) {
		Object o = CacheUtils.get(Constant.DEVICE_CACHE);
		
		if (o == null) {
			return null;
		}
		Map<String, VendingModel> map = (Map<String, VendingModel>)o;		
		VendingModel v = map.get(deviceId);
		return v;
	}
	
	/**
	 * 获取机型编码
	 * @param deviceId
	 * @return
	 */
	public static String getDeviceCode(String deviceId) {
		VendingModel vendingModel = getVendingModel(deviceId);
		if(vendingModel==null) {
			return "";
		}else {
			return vendingModel.getDeviceId();
		}
	}


	/**
	 * 根据售卖机编号得到售卖机基础信息
	 * @param stockId
	 * @return
	 */
	public static StockInfo getStockInfo(String stockId) {
		Object o = CacheUtils.get(Constant.STOCKINFO_CACHE);

		if (o == null) {
			return null;
		}
		Map<String, StockInfo> map = (Map<String, StockInfo>)o;
		StockInfo v = map.get(stockId);
		if(v==null)
			return  new StockInfo();
		else
		   return v;
	}
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	public static User getUserById(Long userId) {
		Object o = CacheUtils.get(Constant.USER_CACHE);
		
		if (o == null) {
			IUserService userService = SpringUtils.getBean(UserServiceImpl.class);
			User user = userService.selectByUserId(userId);
			//缓存不存在,从数据库中查询放入缓存
			if(user!=null) {
				Map<Long, User> map = new HashMap<Long, User>();
				Map<String, User> mapLoginId = new HashMap<String, User>();
				Map<String, User> mapLoginName = new HashMap<String, User>();
				map.put(user.getUserId(), user);
				mapLoginId.put(user.getCorpId()+user.getLoginName(), user);
				mapLoginName.put(user.getLoginName(), user);
				CacheUtils.put(Constant.USER_CACHE, map);
				CacheUtils.put(Constant.LOGIN_USER_CACHE, mapLoginId);
				CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, mapLoginName);
				CacheUtils.get(Constant.USER_CACHE);
				return user;
			}
			return null;
		}
		Map<Long, User> map = (Map<Long, User>)o;		
		User cou = map.get(userId);
		if (cou != null) {
			return cou;
		}else {
			//缓存不存在,从数据库中查询放入缓存
			IUserService userService = SpringUtils.getBean(UserServiceImpl.class);
			User user = userService.selectByUserId(userId);
			if(user!=null) {
				Map<String, User> mapLoginId = null;
				Map<String, User> mapLoginName = null;
				Object login = CacheUtils.get(Constant.LOGIN_USER_CACHE);
				if(login!=null) {
					mapLoginId=(Map<String, User>)login;
				}else {
					mapLoginId = new HashMap<String, User>();
				}
				Object loginName = CacheUtils.get(Constant.LOGIN_NAME_USER_CACHE);
				if(mapLoginName!=null) {
					mapLoginName=(Map<String, User>)loginName;
				}else {
					mapLoginName = new HashMap<String, User>();
				}
				map.put(user.getUserId(), user);
				mapLoginId.put(user.getCorpId()+user.getLoginName(), user);
				mapLoginName.put(user.getLoginName(), user);
				CacheUtils.put(Constant.USER_CACHE, map);
				CacheUtils.put(Constant.LOGIN_USER_CACHE, mapLoginId);
				CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, mapLoginName);
				CacheUtils.get(Constant.USER_CACHE);
				return user;
			}else {
				return null;
			}
		}
	}

	/**
	 * 根据用户id获取用户信息
	 * @param
	 * @return
	 */
	public static User getUserByLoginId(String corpId,String loginId) {
		Object o = CacheUtils.get(Constant.LOGIN_USER_CACHE);

		if (o == null) {
			IUserService userService = SpringUtils.getBean(UserServiceImpl.class);
			User user = userService.selectUserByLoginName(loginId);
			//缓存不存在,从数据库中查询放入缓存
			if(user!=null) {
				Map<Long, User> map = new HashMap<Long, User>();
				Map<String, User> mapLoginId = new HashMap<String, User>();
				Map<String, User> mapLoginName = new HashMap<String, User>();
				map.put(user.getUserId(), user);
				mapLoginId.put(user.getCorpId()+user.getLoginName(), user);
				mapLoginName.put(user.getLoginName(), user);
				CacheUtils.put(Constant.USER_CACHE, map);
				CacheUtils.put(Constant.LOGIN_USER_CACHE, mapLoginId);
				CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, mapLoginName);
				CacheUtils.get(Constant.USER_CACHE);
				return user;
			}
			return null;
		}
		Map<String, User> mapLoginId = (Map<String, User>)o;
		User cou = mapLoginId.get(corpId+loginId);
		if (cou != null) {
			return cou;
		}else {
			//缓存不存在,从数据库中查询放入缓存
			IUserService userService = SpringUtils.getBean(UserServiceImpl.class);
			User user = userService.selectUserByLoginName(loginId);
			if(user!=null) {
				Map<Long, User> userIdMap = null;
				Map<String, User> mapLoginName = null;
				Object userId = CacheUtils.get(Constant.USER_CACHE);
				if(userId!=null) {
					userIdMap=(Map<Long, User>)userId;
				}else {
					userIdMap = new HashMap<Long, User>();
				}
				Object loginName = CacheUtils.get(Constant.LOGIN_NAME_USER_CACHE);
				if(mapLoginName!=null) {
					mapLoginName=(Map<String, User>)loginName;
				}else {
					mapLoginName = new HashMap<String, User>();
				}
				userIdMap.put(user.getUserId(), user);
				mapLoginId.put(user.getCorpId()+user.getLoginName(), user);
				mapLoginName.put(user.getLoginName(), user);
				CacheUtils.put(Constant.USER_CACHE, userIdMap);
				CacheUtils.put(Constant.LOGIN_USER_CACHE, mapLoginId);
				CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, mapLoginName);
				CacheUtils.get(Constant.USER_CACHE);
				return user;
			}else {
				return null;
			}
		}
	}
	
	/**
	 * 根据登录名获取用户信息
	 * @param
	 * @return
	 */
	public static User getUserByLoginName(String loginId) {
		Object o = CacheUtils.get(Constant.LOGIN_NAME_USER_CACHE);

		if (o == null) {
			IUserService userService = SpringUtils.getBean(UserServiceImpl.class);
			User user = userService.selectUserByLoginName(loginId);
			//缓存不存在,从数据库中查询放入缓存
			if(user!=null) {
				Map<Long, User> map = new HashMap<Long, User>();
				Map<String, User> mapLoginId = new HashMap<String, User>();
				Map<String, User> mapLoginName = new HashMap<String, User>();
				map.put(user.getUserId(), user);
				mapLoginId.put(user.getCorpId()+user.getLoginName(), user);
				mapLoginName.put(user.getLoginName(), user);
				CacheUtils.put(Constant.USER_CACHE, map);
				CacheUtils.put(Constant.LOGIN_USER_CACHE, mapLoginId);
				CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, mapLoginName);
				CacheUtils.get(Constant.USER_CACHE);
				return user;
			}
			return null;
		}
		Map<String, User> mapLoginName = (Map<String, User>)o;
		User cou = mapLoginName.get(loginId);
		if (cou != null) {
			return cou;
		}else {
			//缓存不存在,从数据库中查询放入缓存
			IUserService userService = SpringUtils.getBean(UserServiceImpl.class);
			User user = userService.selectUserByLoginName(loginId);
			if(user!=null) {
				Map<Long, User> userIdMap = null;
				Map<String, User> mapLoginId = null;
				Object userId = CacheUtils.get(Constant.USER_CACHE);
				if(userId!=null) {
					userIdMap=(Map<Long, User>)userId;
				}else {
					userIdMap = new HashMap<Long, User>();
				}
				Object loginIdObj = CacheUtils.get(Constant.LOGIN_USER_CACHE);
				if(mapLoginId!=null) {
					mapLoginId=(Map<String, User>)loginIdObj;
				}else {
					mapLoginId = new HashMap<String, User>();
				}
				userIdMap.put(user.getUserId(), user);
				mapLoginId.put(user.getCorpId()+user.getLoginName(), user);
				mapLoginName.put(user.getLoginName(), user);
				CacheUtils.put(Constant.USER_CACHE, userIdMap);
				CacheUtils.put(Constant.LOGIN_USER_CACHE, mapLoginId);
				CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, mapLoginName);
				CacheUtils.get(Constant.USER_CACHE);
				return user;
			}else {
				return null;
			}
		}
	}
	
	/**
	 * 根据用户编号删除用户缓存
	 * 
	 * @param userId
	 */
	public static void deleteUserCacheByUserId(Long userId) {
		Object o = CacheUtils.get(Constant.USER_CACHE);
		
		if (o != null) {
			Map<Long, User> map = (Map<Long, User>)o;
			map.remove(userId);			
			CacheUtils.put(Constant.USER_CACHE, map);
		}
		
	}
	
	/**
	 * 根据登录名和公司编号删除用户缓存
	 * 
	 * @param userId
	 */
	public static void deleteUserCacheByLoginNameAndCorpId(String loginName,String corpId) {
		Object obj = CacheUtils.get(Constant.LOGIN_USER_CACHE);
		if(obj!=null) {
			Map<String, User> map = (Map<String, User>)obj;
			map.remove(corpId+loginName);			
			CacheUtils.put(Constant.LOGIN_USER_CACHE, map);
		}
	}
	
	/**
	 * 根据登录名删除用户缓存
	 * 
	 * @param userId
	 */
	public static void deleteUserCacheByLoginName(String loginName) {
		Object obj = CacheUtils.get(Constant.LOGIN_NAME_USER_CACHE);
		if(obj!=null) {
			Map<String, User> map = (Map<String, User>)obj;
			map.remove(loginName);
			CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, map);
		}
	}
	
	/**
	 * 获取产品名称
	 * @param productId
	 * @param corpId
	 * @return
	 */
	public static String getProductNameById(String productId) {
		Object o = CacheUtils.get(Constant.PRODUCT_CACHE);
		
		if (o == null) {
			return "";
		}
		Map<String, ProductInfo> map = (Map<String, ProductInfo>)o;		
		ProductInfo cou = map.get(productId);
		if (cou != null) {
			return cou.getName();
		}
		return "";
	}
	
	/**
	 * 获取产品信息
	 * @param productId
	 * @param corpId
	 * @return
	 */
	public static ProductInfo getProductById(String productId) {
		Object o = CacheUtils.get(Constant.PRODUCT_CACHE);
		
		if (o == null) {
			return null;
		}
		Map<String, ProductInfo> map = (Map<String, ProductInfo>)o;		
		return map.get(productId);
	}
	
	/**
	 * 判断用户是否为宇宙星空用户
	 * @return
	 */
	public static boolean isZhilai() {
		String corpId = ShiroUtils.getUser().getCorpId();
		if (corpId != null && corpId.equals(Constant.ZHILAI_CORP_ID)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 折扣
	 * 翻译优惠方式,01:购买折扣 02:消费立减 
	 * @param
	 * @return
	 */
	public static String parseFavWay(String favWay) {
		return parseDictValue("sys_fav_way",favWay);
	}
	
	/**
	 * 翻译投放方式 1:立即投放 2:延时投放
	 * @param transferType 1 2
	 * @return
	 */
	public static String parseDeliveryType(String deliveryType) {
		return parseDictValue("sys_delivery_type",deliveryType);
	}
	
	/**
	 * 翻译下发类型 1:全量 2:增量
	 * @param
	 * @return
	 */
	public static String parseTransferType(String transferType) {
		return parseDictValue("sys_transfer_type",transferType);
	}
	
	/**
	 * 折扣
	 * 翻译优惠对象 1:整机 2:单品
	 * @param
	 * @return
	 */
	public static String parseFavTarget(String favTarget) {
		return parseDictValue("sys_fav_target",favTarget);
	}
	
	/**
	 * 折扣
	 * 翻译折扣状态 1:等待执行 2:正在执行 3:执行完成 4:已删除
	 * @param
	 * @return
	 */
	public static String parseFavState(String favState) {
		return parseDictValue("sys_fav_state",favState);
	}
	
	/**
	 * 翻译售卖状态,将状态码转成文字
	 * @param sellState
	 * @return
	 */
	public static String parseSellState(String sellState) {
		return parseDictValue("sys_sell_state",sellState);
	}
	
	/**
	 * 翻译入库状态,将状态码转成文字
	 * @param
	 * @return
	 */
	public static String parseInputState(String inputState) {
		return parseDictValue("sys_input_state",inputState);
	}
	
	/**
	 * 翻译采购状态,将状态码转成文字
	 * @param
	 * @return
	 */
	public static String parseBuyState(String buyState) {
		return parseDictValue("sys_buy_state",buyState);
	}
	
	/**
	 * 翻译厂商id为名称
	 * @param factoryId
	 * @return
	 */
	public static String parseFactoryId(String factoryId) {
		return parseDictValue("sys_factory",factoryId);
	}
	
	/**
	 * 翻译审核状态id为名称
	 * @param checkState
	 * @return
	 */
	public static String parseCheckState(String checkState) {
		return parseDictValue("sys_check_state",checkState);
	}
	
	/**
	 *  翻译货柜类型,将货柜类型转成文字
	 * @param
	 * @return
	 */
	public static String parseCabinetType(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_cabinet_type",code);
	}
	
	/**
	 *  翻译外挂类型,将外挂类型转成文字
	 * @param
	 * @return
	 */
	public static String parseHangType(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_hang_type",code);
	}
	
	/**
	 *  翻译广告位编码为文字
	 * @param strategyPoint 广告位编码
	 * @return
	 */
	public static String parseStrategyPoint(String strategyPoint) {	// 从缓存中查找名称
		return parseDictValue("sys_strategy_point",strategyPoint);
	}
	
	/**
	 *  翻译网络状态,将网络码转成文字
	 * @param netSate
	 * @return
	 */
	public static String parseNetSate(String netSate) {	// 从缓存中查找名称
		return parseDictValue("sys_net_sate",netSate);
	}
	
	/**
	 * 优惠类型翻译文字 1:统一优惠 2:分时段优惠 
	 * @param favType
	 * @return
	 */
	public static String parseFavType(String favType) {	// 从缓存中查找名称
		return parseDictValue("sys_fav_type",favType);
	}
	
	/**
	 *  翻译支付方式,将支付方式转成文字
	 * @param payType
	 * @return
	 */
	public static String parsePayType(String payType) {	// 从缓存中查找名称
		if (payType == null || payType.equals("")) {
			return "";
		}
		String[] p = payType.split(",");
		if (p.length == 0) {
			return "";
		}
		List<String> list = new ArrayList<String>();
		for (String s : p) {
			String a = parseDictValue("sys_pay_type",s);
			if (!StringUtils.isEmpty(a)) {
				list.add(a);
			}
		}
		return StringUtils.join(list.toArray(), ",");
	}
	
	/**
	 *  翻译事件类型,将事件类型转成文字
	 * @param
	 * @return
	 */
	public static String parseWarnType(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_warn_type",code);
	}
	
	/**
	 *  翻译素材文件类型 1:视频 2:图片 3:文本
	 * @param code
	 * @return
	 */
	public static String parseMediaType(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_media_type",code);
	}
	
	/**
	 *  翻译事件类型,将事件类型转成文字
	 * @param eventType
	 * @return
	 */
	public static String parseEventType(String eventType) {	// 从缓存中查找名称
		return parseDictValue("sys_event_type",eventType);
	}
	
	/**
	 *  翻译告警级别,将告警级别转成文字
	 * @param
	 * @return
	 */
	public static String parseWarnLevel(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_warn_level",code);
	}
	
	/**
	 *  翻译告警状态,将告警状态转成文字
	 * @param
	 * @return
	 */
	public static String parseWarnState(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_warn_state",code);
	}
	
	/**
	 *  状态 1:未解决 2:已处理
	 * @param
	 * @return
	 */
	public static String parseWarnCurState(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_warn_curstate",code);
	}
	
	/**
	 * 包装类型id翻译成文字
	 * @param code
	 * @return
	 */
	public static String parseBagType(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_bag_type",code);
	}
	
	/**
	 *  补货状态 1:等待补货 2:补货中 3:补货完成
	 * @param
	 * @return
	 */
	public static String parseSupplyState(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_supply_state",code);
	}
	
	/**
	 *  翻译库存状态 1:未审核 2:已出库
	 * @param
	 * @return
	 */
	public static String parseStockState(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_stock_state",code);
	}
	
	/**
	 *  翻译订单商品当前状态 当前状态 01:申请 02:支付成功03:支付失败 04:提前取货 05:客户已取货  06:客户取消 07:出货故障 08:已回收09:退款 10:完结
	 * @param
	 * @return
	 */
	public static String parseOrderOperAction(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_oper_action",code);
	}
	
	/**
	 *  翻译订单当前状态 1:申请 2:提前取货3:客户已取货 4:已回收
	 * @param
	 * @return
	 */
	public static String parseOrderCurState(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_order_cur_state",code);
	}
	
	/**
	 *  出柜状态 1:未出柜 2:正常已出柜 3:异常已出柜 4:出柜失败
	 * @param
	 * @return
	 */
	public static String parseBoxState(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_out_state",code);
	}
	
	public static void main(String[] args) {
		String payType = "a,b";
		String[] p = payType.split(",");
//		List<String> list = Arrays.asList(p);
		
//		String[] userid = {"aa","bb","cc"};
//		List<String> userList = new ArrayList<String>();
//		Collections.addAll(userList, userid);
		
		String str1=StringUtils.join(p, ",");
		
		System.out.println(str1);
	}
	/**
	 * 翻译网络制式,将网络制式转成文字
	 * @param netWork
	 * @return
	 */
	public static String parseNetWork(String netWork) {	// 从缓存中查找名称
		return parseDictValue("sys_net_work",netWork);
	}
	
	/**
	 * 从缓存中返回商户对象
	 * @param corpId
	 * @return
	 */
	public static Corp getCorpById(String corpId) {	// 从缓存中查找名称
		Object o = CacheUtils.get(Constant.CORP_CACHE);
		if (o == null) {
			return null;
		}
		Map<String, Corp> map = (Map<String, Corp>)o;
		return map.get(corpId);
	}
	
	/**
	 * 从缓存中返回商户名称
	 * @param corpId
	 * @return
	 */
	public static String getCorpNameById(String corpId) {	// 从缓存中查找名称
		Corp corp = getCorpById(corpId);
		if (corp == null) {
			return "";
		}
		return corp.getCorpName();
	}
	
	/**
	 * 翻译数据字典,返回数据字典存的文字
	 * @param dicttype
	 * @param dictvalue
	 * @return
	 */
	public static String parseDictValue(String dicttype, String dictvalue) {
		Object o = CacheUtils.get(Constant.DICT_CACHE);
		
		if (o == null) {
			return "";
		}
		Map<String, DictData> map = (Map<String, DictData>)o;		
		DictData cou = map.get(dicttype +"_"+ dictvalue);
		if (cou != null) {
			return cou.getDictLabel();
		}
		return "";
	}
	
	/**
	 * 从缓存中获取点位
	 * @param pointId	点位id
	 * @param corpId	商户id
	 * @return	点位对象
	 */
	public static VendingPoint getVendingPointFromCache(String pointId) {
		Object o = CacheUtils.get(Constant.VENDING_POINT_CACHE);
		if (o == null) {
			return null;
		}
		Map<String, VendingPoint> map = (Map<String, VendingPoint>)o;
		return map.get(pointId);		
	}
	
	/**
	 * 获取点位名称
	 * @param pointId
	 * @param corpId
	 * @return
	 */
	public static String getVendingPointNameFromCache(String pointId) {
		VendingPoint vp = getVendingPointFromCache(pointId);
		if (vp == null) {
			return "";
		}
		return vp.getName();
	}
	
	/**
	 * 从缓存中获取线路
	 * @param lineId	线路id
	 * @param corpId	商户id
	 * @return	线路对象
	 */
	public static VendingLine getVendingLineFromCache(String lineId) {
		Object o = CacheUtils.get(Constant.VENDING_LINE_CACHE);
		if (o == null) {
			return null;
		}
		Map<String, VendingLine> map = (Map<String, VendingLine>)o;
		return map.get(lineId);		
	}
	
	/**
	 * 从缓存中获取线路
	 * @param lineId	线路id
	 * @param corpId	商户id
	 * @return	线路名称
	 */
	public static String getVendingLineNameFromCache(String lineId) {
		VendingLine vendingLine = getVendingLineFromCache(lineId);
		if (vendingLine == null) {
			return "";
		}
		return vendingLine.getName();	
	}
	
	/**
	 * 从缓存中获取区域
	 * @param districtId	区域id
	 * @param corpId	商户id
	 * @return	区域对象
	 */
	public static VendingDistrict getVendingDistrictFromCache(String districtId) {
		Object o = CacheUtils.get(Constant.VENDING_DISPATCH_CACHE);
		if (o == null) {
			return null;
		}
		Map<String, VendingDistrict> map = (Map<String, VendingDistrict>)o;
		return map.get(districtId);		
	}
	
	/**
	 * 从缓存中获取区域
	 * @param districtId	区域id
	 * @param corpId	商户id
	 * @return	区域名称
	 */
	public static String getVendingDistrictNameFromCache(String districtId) {
		VendingDistrict vendingDistrict = getVendingDistrictFromCache(districtId);
		if (vendingDistrict == null) {
			return "";
		}
		return vendingDistrict.getName();	
	}
	
	/**
	 * 根据商品分类id获取分类详情
	 * @param classId
	 * @return
	 */
	public static ProductClassify getProductClassify(String classId) {
		Object o = CacheUtils.get(Constant.PRODUCT_CLASS_CACHE);
		
		if (o == null) {
			return null;
		}
		Map<String, ProductClassify> map = (Map<String, ProductClassify>)o;		
		return map.get(classId);
	}
	
	/**
	 * 将数据库中json格式的图片地址转为图片地址
	 * @param jsonpic
	 * @return
	 */
	public static String jsonpicToPic(String jsonpic) {
		List<Map> list = JSONObject.parseArray(jsonpic, Map.class);
		if (list == null || list.isEmpty()) {
			return "";
		}
		Map map = list.get(0);
		Object pic = map.get("pic");
		if (pic == null) {
			return "";
		} else {
			return (String)pic;
		}
		
	}

	public static void getCallPointDay(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallPointDay(map);
	}
	public static void getCallPointMonth(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallPointMonth(map);
	}
	public static void getPointYear(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getPointYear(map);
	}
	public static void getCallLineDay(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallLineDay(map);
	}
	public static void getCallLineMonth(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallLineMonth(map);
	}
	public static void getCallPointYear(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallPointYear(map);
	}
	public static void getCallLineYear(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallLineYear(map);
	}
	public static void getCallPtimeDay(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallPtimeDay(map);
	}
	public static void getCallPtimeMonth(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallPtimeMonth(map);
	}
	public static void getCallPtimeWeek(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallPtimeWeek(map);
	}
	public static void getCallVtimeDay(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallVtimeDay(map);
	}
	public static void getCallVtimeMonth(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallVtimeMonth(map);
	}
	public static void getCallVtimeWeek(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallVtimeWeek(map);
	}
	public static void getCallReportBoard(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallReportBoard(map);
	}
	public static void getCallReportDsale(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallReportDsale(map);
	}
	public static void getCallReportMsale(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallReportMsale(map);
	}
	public static void getCallReportOsale(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallReportOsale(map);
	}
	/**
	 * 生成新每日排行榜
	 * 
	 * @param map
	 */
	public static void getCallOsaleDay(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallOsaleDay(map);
	}
	/**
	 * 生成新每日排行榜历史记录
	 * 
	 * @param map
	 */
	public static void getCallOsaleDayHistory(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallOsaleDayHistory(map);
	}
	/**
	 * 生成月度季度年度排行榜
	 * 
	 * @param map
	 */
	public static void getCallOsaleOther(Map<String,Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallOsaleOther(map);
	}
	/**
	 * 生成月度季度年度排行榜历史记录
	 * 
	 * @param map
	 */
	public static void getCallOsaleOtherHistory(Map<String,Object> map) {
		
	}
	
	public static void getCallVtimeQuarter(Map<String, Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallVtimeQuarter(map);
	}
	
	public static void getCallVtimeHalfYear(Map<String, Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallVtimeHalfYear(map);
	}
	
	public static void getCallVtimeYear(Map<String, Object> map) {
		SystemService systemService = (SystemService)SpringUtils.getBean("systemService");
		systemService.getCallVtimeYear(map);
	}
		
	/**
	 *  翻译支付状态
	 * @param code
	 * @return
	 */
	public static String parsePayState(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_pay_state",code);
	}
	
	/**
	 *  退款状态 0:无 1:全额退款 2:部分退款 3:退款失败
	 * @param code
	 * @return
	 */
	public static String parseReturnType(String code) {	// 从缓存中查找名称
		return parseDictValue("sys_return_type",code);
	}

	/**
	 *  销售状态 1:待销售 2:销售中 3:销售完成
	 * @param code
	 * @return
	 */
	public static String parseSalteState(String code) {
		return parseDictValue("sys_salte_state",code);
	}

	/**
	 *  对账状态 1:等待售完 2:等待对账 3:对账正常 4:对账异常
	 * @param code
	 * @return
	 */
	public static String parseStatementState(String code) {
		return parseDictValue("sys_statement_state",code);
	}

	/**
	 *  结算状态 1:未提交 2:已提交
	 * @param code
	 * @return
	 */
	public static String parseStatementSupplyCurState(String code) {
		return parseDictValue("sys_statement_supply_cur_state",code);
	}

	/**
	 * 获取支付平台url
	 * 
	 * @return
	 */
	public static String getPayUrl(String code) {
		return parseDictValue("pay_ip","1");
	}

	public static String getVendingNameBySiteId(String siteId) {
		Vending vending = getVendingBase(siteId);
		if(vending!=null) {
			return vending.getSiteName();
		}else {
			return "";
		}
	}

	public static String getExcelModelPath(String modelName) {
		return parseDictValue("excel_model",modelName);
	}

	

}
