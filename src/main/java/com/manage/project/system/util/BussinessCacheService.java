package com.manage.project.system.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.stock.service.IStockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.DateUtils;
import com.manage.framework.redis.RedisOps;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.service.ICorpService;
import com.manage.project.system.base.domain.DictData;
import com.manage.project.system.base.service.IDictDataService;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.base.domain.Parameter;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.service.IDispatchService;
import com.manage.project.system.base.service.IParameterService;
import com.manage.project.system.base.service.IUserService;
import com.manage.project.system.index.domain.ReportBoard;
import com.manage.project.system.index.domain.ReportDsale;
import com.manage.project.system.index.domain.ReportMsale;
import com.manage.project.system.index.domain.ReportOsale;
import com.manage.project.system.index.service.IndexService;
import com.manage.project.system.index.util.IndexUtil;
import com.manage.project.system.index.vo.OneMonthReviewVo;
import com.manage.project.system.index.vo.OperateReviewVo;
import com.manage.project.system.index.vo.column.YsColChartVo;
import com.manage.project.system.index.vo.line.TodaySummaryVo;
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.service.IProductClassifyService;
import com.manage.project.system.product.service.IProductInfoService;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.service.IVendingModelService;
import com.manage.project.system.vending.service.IVendingService;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.project.system.vendingPoint.domain.VendingPoint;
import com.manage.project.system.vendingPoint.service.IVendingDistrictService;
import com.manage.project.system.vendingPoint.service.IVendingLineService;
import com.manage.project.system.vendingPoint.service.IVendingPointService;

/**
 * 用于缓存业务数据，项目所有缓存的插入操作均在此完成
 * @author xufeng
 *
 */
@Service
public class BussinessCacheService {
	private static final Logger log = LoggerFactory.getLogger(BussinessCacheService.class);

	@Autowired
	private IDispatchService dispatchService;
	
	@Autowired
    private IDictDataService dictDataService;
	
	@Autowired
	private ICorpService corpService;
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private IVendingModelService vendingModelService;
	
	@Autowired
	private IVendingDistrictService vendingDistrictService;
	
	@Autowired
	private IVendingLineService vendingLineService;
	
	@Autowired
	private IVendingPointService vendingPointService;
	
	@Autowired
	private IVendingService vendingService;
	
	@Autowired
	private IProductInfoService productInfoService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IProductClassifyService productClassifyService;

	@Autowired
	private IStockInfoService stockInfoService;
	
	@Autowired
	private IParameterService parameterService;
	
	@Autowired
	private RedisOps redisOps;
	
	/**
	 * 缓存人员信息
	 */
	public void initUser() {
		log.info("初始化用户信息进缓存 time:" + DateUtils.getTime());

		User user = new User();
		List<User> list = userService.selectUserList(user);	//.selectProductInfoList(productInfo);//.selectVendingModelList(vendingModel);
		Map<Long, User> map = new HashMap<Long, User>();
		Map<String, User> mapLoginId = new HashMap<String, User>();
		Map<String, User> mapLoginName = new HashMap<String, User>();
		for (User c : list) {
			map.put(c.getUserId(), c);
			mapLoginId.put(c.getCorpId()+c.getLoginName(), c);
			mapLoginName.put(c.getLoginName(), c);
		}
		CacheUtils.put(Constant.USER_CACHE, map);
		CacheUtils.put(Constant.LOGIN_USER_CACHE, mapLoginId);
		CacheUtils.put(Constant.LOGIN_NAME_USER_CACHE, mapLoginName);
	}
	/**
	 * 缓存仓库信息
	 */
	public void initStockInfo() {
		log.info("初始化仓库信息进缓存 time:" + DateUtils.getTime());

		StockInfo stockInfo = new StockInfo();
		List<StockInfo> list = stockInfoService.selectStockInfoList(stockInfo);	//.selectProductInfoList(productInfo);//.selectVendingModelList(vendingModel);
		Map<String, StockInfo> map = new HashMap<String, StockInfo>();
		for (StockInfo c : list) {
			map.put(c.getStockId(), c);
		}
		CacheUtils.put(Constant.STOCKINFO_CACHE, map);
	}
	
	/**
	 * 初始化商户信息进缓存
	 */
	@Scheduled(cron = "0/30 * * * * ?")
	public void initProduct() {

		log.debug("初始化商品信息进缓存 time:" + DateUtils.getTime());

		ProductInfo productInfo = new ProductInfo();
		List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);//.selectVendingModelList(vendingModel);
		Map<String, ProductInfo> map = new HashMap<String, ProductInfo>();
		for (ProductInfo c : list) {
			map.put(c.getProductId(), c);
		}
		CacheUtils.put(Constant.PRODUCT_CACHE, map);
	}
	
	 /* 初始化商户信息进缓存
	 */
	public void initProductClass() {
		log.info("初始化商品分类信息进缓存 time:" + DateUtils.getTime());

		ProductClassify productClassify = new ProductClassify();
		List<ProductClassify> list = productClassifyService.selectProductClassifyList(productClassify);
		Map<String, ProductClassify> map = new HashMap<String, ProductClassify>();
		for (ProductClassify c : list) {
			map.put(c.getClassifyId(), c);
		}
		CacheUtils.put(Constant.PRODUCT_CLASS_CACHE, map);
	}
	
	/**
	 * 初始化所有行政区划缓存数据
	 */
	public void initDispatchCache() {
		log.info("初始化所有行政区划缓存数据 time:" + DateUtils.getTime());
		Dispatch dispatch = new Dispatch();
		List<Dispatch> list = dispatchService.selectDispatchList(dispatch);
		Map<String, Dispatch> map = new HashMap<String, Dispatch>();
		for (Dispatch d : list) {
			map.put(d.getId(), d);
		}
		CacheUtils.put(Constant.DISPATCH_CACHE, map);
	}
	
	/**
	 * 初始化数据字典进入缓存
	 */
	public void initDictCache() {
		log.info("初始化数据字典进入缓存 time:" + DateUtils.getTime());
		DictData dictData = new DictData();
		List<DictData> list = dictDataService.selectDictDataList(dictData);
		Map<String, DictData> map = new HashMap<String, DictData>();
		for (DictData d : list) {
			map.put(d.getDictType()+"_"+d.getDictValue(), d);
		}
		CacheUtils.put(Constant.DICT_CACHE, map);
	}
	
	/**
	 * 初始化商户信息进缓存
	 */
	public void initCorp() {
		log.info("初始化商户信息进缓存 time:" + DateUtils.getTime());
		Corp corp = new Corp();
		List<Corp> list = corpService.selectCorpList(corp);
		Map<String, Corp> map = new HashMap<String, Corp>();
		for (Corp c : list) {
			map.put(c.getCorpId(), c);
		}
		CacheUtils.put(Constant.CORP_CACHE, map);
	}
	
	/**
	 * 初始化系统参数进缓存
	 */
	public void initSysParameter() {
		log.info("初始化系统参数进缓存 time:" + DateUtils.getTime());
		
		List<Parameter> list = parameterService.selectParameterList(new Parameter());
		Map<String, Parameter> map = new HashMap<String, Parameter>();
		for (Parameter parameter : list) {
			map.put(parameter.getName(), parameter);
		}
		CacheUtils.put(Constant.PARAMETER_CACHE, map);
	}
	
	/**
	 * 初始化机型信息进缓存
	 * key为modelid+corpId  
	 */
	public void initVendingModel() {
		log.info("初始化机型信息进缓存 time:" + DateUtils.getTime());
		
		VendingModel vendingModel = new VendingModel();
		List<VendingModel> list = vendingModelService.selectVendingModelList(vendingModel);
		Map<String, VendingModel> map = new HashMap<String, VendingModel>();
		for (VendingModel c : list) {
			map.put(c.getDeviceCode(), c);
		}
		CacheUtils.put(Constant.DEVICE_CACHE, map);
	}
	
	/**
	 * 初始化区域信息进缓存
	 */
	public void initVendingDistrict() {
		log.info("初始化区域信息进缓存 time:" + DateUtils.getTime());
		
		VendingDistrict vendingDistrict = new VendingDistrict();
		List<VendingDistrict> list = vendingDistrictService.selectVendingDistrictList(vendingDistrict);
		Map<String, VendingDistrict> map = new HashMap<String, VendingDistrict>();
		for (VendingDistrict c : list) {
			map.put(c.getDistrictId(), c);
		}
		CacheUtils.put(Constant.VENDING_DISPATCH_CACHE, map);
	}
	
	/**
	 * 初始化线路信息进缓存
	 */
	public void initVendingLine() {
		log.info("初始化线路信息进缓存 time:" + DateUtils.getTime());
		
		VendingLine vendingLine = new VendingLine();
		List<VendingLine> list = vendingLineService.selectVendingLineList(vendingLine);
		Map<String, VendingLine> map = new HashMap<String, VendingLine>();
		for (VendingLine c : list) {
			map.put(c.getLineId(), c);
		}
		CacheUtils.put(Constant.VENDING_LINE_CACHE, map);
	}
	
	/**
	 * 初始化线路信息进缓存
	 */
	public void initVendingPoint() {
		log.info("初始化线路信息进缓存 time:" + DateUtils.getTime());
		
		VendingPoint vendingPoint = new VendingPoint();
		List<VendingPoint> list = vendingPointService.selectVendingPointList(vendingPoint);
		Map<String, VendingPoint> map = new HashMap<String, VendingPoint>();
		for (VendingPoint c : list) {
			map.put(c.getPointId(), c);
		}
		CacheUtils.put(Constant.VENDING_POINT_CACHE, map);
	}
	
	// --------------------首页使用--------------------
	/**
	 * 初始化首页运营总览缓存
	 */
	public void initIndex(String corpId) {
		// 查询首页运营总览
		ReportBoard reportBoard = indexService.selectReportBoardById(corpId);
//		IndexSummaryVo indexSummaryVo = new IndexSummaryVo();
		// 初始化总览
		OperateReviewVo operateReviewVo = new OperateReviewVo();
//		indexSummaryVo.setOperateReviewVo(operateReviewVo);
		if (reportBoard != null) {
			if (reportBoard.getOnlineNum() != null) {
				operateReviewVo.setOnlineMachine(reportBoard.getOnlineNum().toString());
			} else {
				operateReviewVo.setOnlineMachine("0");
			}
			if (reportBoard.getOfflineNum() != null) {
				if (reportBoard.getOfflineDayNum() == null) {
					reportBoard.setOfflineDayNum(0);
				}
				if (reportBoard.getOfflineNum() == null) {
					reportBoard.setOfflineNum(0);
				}
				
				int num = reportBoard.getOfflineDayNum() + reportBoard.getOfflineNum();
				operateReviewVo.setOutlineMachine(String.valueOf(num));
			} else {
				operateReviewVo.setOutlineMachine("0");
			}
			if (reportBoard.getProfit() != null) {
				operateReviewVo.setTotalProfit(reportBoard.getProfit().toString());
			} else {
				operateReviewVo.setTotalProfit("0");
			}
			if (reportBoard.getSaleMoney() != null) {
				operateReviewVo.setTotalSale(reportBoard.getSaleMoney().toString());
			} else {
				operateReviewVo.setTotalSale("0");
			}
			if (reportBoard.getSaleNum() != null) {
				operateReviewVo.setTotalSaleNum(reportBoard.getSaleNum().toString());
			} else {
				operateReviewVo.setTotalSaleNum("0");
			}
		}		
		Map<String, String> map = new HashMap<String,String>();
		//CacheUtils.put("serverCache",Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId, operateReviewVo);
		log.debug("初始化首页运营总览缓存数据 time:" + DateUtils.getTime()+",公司"+corpId+","+JSONObject.toJSONString(operateReviewVo));
		redisOps.setObject(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId, operateReviewVo);
	}
	
	/**
	 * 初始化首页近一月总览数据，放入缓存
	 * @param corpId	商户id
	 */
	public void initIndexOneMonth(String corpId) {
		//log.info("初始化首页缓存近一月总览数据 time:" + DateUtils.getTime());
		// 今天		
		String now = DateUtils.getFetureDate(0);
		List<String> nowl = new ArrayList<String>();
		nowl.add(now);
		OneMonthReviewVo today = indexService.selectTodayReview(corpId);
		if (today == null) {
			today = new OneMonthReviewVo();
		}
		today.setType(Constant.INDEX_DATA_TYPE_2_1);
		// 昨天
//		Date ydate = DateUtils.getPastDate(-1);
		OneMonthReviewVo yvo = indexService.selectSDsaleByDate(corpId, DateUtils.getListDays(1));
		if (yvo == null) {
			yvo = new OneMonthReviewVo();
		}
		yvo.setType(Constant.INDEX_DATA_TYPE_2_2);
		// 近7日
//		Date j7date = DateUtils.getPastDate(-7);
		OneMonthReviewVo j7vo = indexService.selectSDsaleByDate(corpId, DateUtils.getListDays(7));
		if (j7vo == null) {
			j7vo = new OneMonthReviewVo();
		}
		j7vo.setType(Constant.INDEX_DATA_TYPE_2_3);
		// 近30日
//		Date j30date = DateUtils.getPastDate(-30);
		OneMonthReviewVo j30vo = indexService.selectSDsaleByDate(corpId, DateUtils.getListDays(30));
		if (j30vo == null) {
			j30vo = new OneMonthReviewVo();
		}
		j30vo.setType(Constant.INDEX_DATA_TYPE_2_4);
		
		List<OneMonthReviewVo> list = new ArrayList<OneMonthReviewVo>();
		list.add(today);
		list.add(yvo);
		list.add(j7vo);
		list.add(j30vo);
		//CacheUtils.put("serverCache",Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId, list);
		log.debug("初始化首页缓存近一月总览数据 time:" + DateUtils.getTime()+",公司"+corpId+","+JSONObject.toJSONString(list));
		redisOps.setObject(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId, list);
	}

	/**
	 * 初始化首页缓存营收分析数据
	 * @param corpId	商户id
	 */
	public void initSelfYearSale(String corpId) {
		//log.debug("初始化首页缓存营收分析数据 time:" + DateUtils.getTime());
		// 近半年
		List<ReportMsale> list = indexService.selectReportMsaleByDate(corpId, DateUtils.getListMonths(6));
		//获取近半年的月份
		List<String> halfYear = DateUtils.getListMonths(6);
		//将没有数据的月份填充0
		List<ReportMsale> resultList6 = IndexUtil.fillingZero(list, halfYear);
		YsColChartVo y6vo = IndexUtil.assembleColumn(resultList6);
		CacheUtils.put(Constant.INDEX_SUMMARY_YS6_CACHE_+corpId, y6vo);
		// 近1年
		List<ReportMsale> list12 = indexService.selectReportMsaleByDate(corpId, DateUtils.getListMonths(12));
		//获取全年的月份
		List<String> fullYear = DateUtils.getListMonths(12);
		//将没有数据的月份填充0
		List<ReportMsale> resultList12 = IndexUtil.fillingZero(list12, fullYear);
		YsColChartVo y12vo = IndexUtil.assembleColumn(resultList12);
		log.debug("初始化首页缓存营收分析数据近6月 time:" + DateUtils.getTime()+",公司"+corpId+","+JSONObject.toJSONString(y6vo));
		log.debug("初始化首页缓存营收分析数据近12月 time:" + DateUtils.getTime()+",公司"+corpId+","+JSONObject.toJSONString(y12vo));
		CacheUtils.put(Constant.INDEX_SUMMARY_YS12_CACHE_+corpId, y12vo);
	}
	
	/**
	 * 初始化缓存首页今日销售汇总 显示曲线图使用
	 * @param corpId	商户id
	 */
	public void initTodaySummary(String corpId) {
		//log.debug("初始化缓存首页今日销售汇总 显示曲线图使用 time:" + DateUtils.getTime());
		Date now = new Date();
		List<ReportDsale> dsales = indexService.selectTodayReportDsale(corpId, DateUtils.getYYYY(now), DateUtils.getMM(now), DateUtils.getDD(now));
		TodaySummaryVo todaysale = IndexUtil.assembleLine(dsales);
		log.debug("初始化缓存首页今日销售汇总 显示曲线图使用 time:" + DateUtils.getTime()+",公司"+corpId+","+JSONObject.toJSONString(todaysale));
		CacheUtils.put(Constant.INDEX_SUMMARY_TODAYSALE_CACHE_+corpId, todaysale);
	}
	
	/**
	 * 初始化缓存首页今日销售点位排行 top10
	 * @param corpId
	 */
	public void initTodayLineTop10(String corpId) {
		//log.debug("初始化缓存首页今日销售点位排行 top10 time:" + DateUtils.getTime());
		List<ReportOsale> lineTop10 = indexService.getTodayLineTop10(corpId);
		log.debug("初始化缓存首页今日销售点位排行 top10 time:" + DateUtils.getTime()+",公司"+corpId+","+JSONObject.toJSONString(lineTop10));
		CacheUtils.put(Constant.INDEX_SUMMARY_TODAYDW_CACHE_+corpId, lineTop10);
	}
	
	/**
	 * 初始化缓存首页今日销售商品排行 top10
	 * @param corpId
	 */
	public void initTodayProductTop10(String corpId) {
		//log.debug("初始化缓存首页今日销售商品排行 top10 time:" + DateUtils.getTime());
		List<ReportOsale> productTop10 = indexService.getTodayProductTop10(corpId);

		List<Map<String, Object>> list = IndexUtil.assembleTie(productTop10);
		log.debug("初始化缓存首页今日销售商品排行 top10 time:" + DateUtils.getTime()+",公司"+corpId+","+JSONObject.toJSONString(list));
		CacheUtils.put(Constant.INDEX_SUMMARY_TODAYSP_CACHE_+corpId, list);
	}
	
	/**
	 * 初始化宇宙星空缓存数据
	 */
	public void initZhilaiCache() {
		log.debug("初始化宇宙星空缓存数据 time:" + DateUtils.getTime());
		// 取得其它商户缓存并相加		
		Collection<Corp> corps;
		Object o = CacheUtils.get(Constant.CORP_CACHE);
		if (o == null) {
			corps = corpService.selectCorpList(new Corp());
		} else {
			Map<String, Corp> map = (Map<String, Corp>)o;
			corps = map.values();
		}
		
		OperateReviewVo operateReview = new OperateReviewVo();
		OneMonthReviewVo today = new OneMonthReviewVo();
		today.setType(Constant.INDEX_DATA_TYPE_2_1);
		OneMonthReviewVo yvo = new OneMonthReviewVo();
		yvo.setType(Constant.INDEX_DATA_TYPE_2_2);
		OneMonthReviewVo j7vo = new OneMonthReviewVo();
		j7vo.setType(Constant.INDEX_DATA_TYPE_2_3);
		OneMonthReviewVo j30vo = new OneMonthReviewVo();
		j30vo.setType(Constant.INDEX_DATA_TYPE_2_4);
		for (Corp corp : corps) {			
			String corpId = corp.getCorpId();
			if(Constant.ZHILAI_CORP_ID.equals(corpId)) {
				continue;
			}
			// 初始化首页运营总览缓存
			Object o1 = (OperateReviewVo) redisOps.getObject(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_ + corpId);
			//Object o1 = CacheUtils.get("serverCache",Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+corpId);
			if (o1 != null) {
				OperateReviewVo orv = (OperateReviewVo)o1;
				Integer onlineMachine = Integer.valueOf(operateReview.getOnlineMachine()) + Integer.valueOf(orv.getOnlineMachine());
				operateReview.setOnlineMachine(onlineMachine.toString());
				Integer outlineMachine = Integer.valueOf(operateReview.getOutlineMachine()) + Integer.valueOf(orv.getOutlineMachine());
				operateReview.setOutlineMachine(outlineMachine.toString());
				Float totalProfit = Float.valueOf(operateReview.getTotalProfit()) + Float.valueOf(orv.getTotalProfit());
				operateReview.setTotalProfit(totalProfit.toString());
				Float totalSale = Float.valueOf(operateReview.getTotalSale()) + Float.valueOf(orv.getTotalSale());
				operateReview.setTotalSale(totalSale.toString());
				Integer totalSaleNum = Integer.valueOf(operateReview.getTotalSaleNum()) + Integer.valueOf(orv.getTotalSaleNum());
				operateReview.setTotalSaleNum(totalSaleNum.toString());
			}
			redisOps.setObject(Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+Constant.ZHILAI_CORP_ID, operateReview);
			//CacheUtils.put("serverCache",Constant.INDEX_SUMMARY_TOTALREVIEW_CACHE_+Constant.ZHILAI_CORP_ID, operateReview);
			// 初始化首页近一月总览数据，放入缓存
			Object o2 = redisOps.getObject(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId);
			//Object o2 = CacheUtils.get("serverCache",Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+corpId);
//			OneMonthReviewVo today = new OneMonthReviewVo();
//			today.setType(Constant.INDEX_DATA_TYPE_2_1);
//			OneMonthReviewVo yvo = new OneMonthReviewVo();
//			yvo.setType(Constant.INDEX_DATA_TYPE_2_2);
//			OneMonthReviewVo j7vo = new OneMonthReviewVo();
//			j7vo.setType(Constant.INDEX_DATA_TYPE_2_3);
//			OneMonthReviewVo j30vo = new OneMonthReviewVo();
//			j30vo.setType(Constant.INDEX_DATA_TYPE_2_4);
			if (o2 != null) {				
				List<OneMonthReviewVo> list = (List<OneMonthReviewVo>)o2;
				for (OneMonthReviewVo omr : list) {
					switch (omr.getType()) {
					case Constant.INDEX_DATA_TYPE_2_1:
						Float totalProfit0 = Float.valueOf(today.getTotalProfit()) + Float.valueOf(omr.getTotalProfit());
						today.setTotalProfit(totalProfit0.toString());
						Float totalSale0 = Float.valueOf(today.getTotalSale()) + Float.valueOf(omr.getTotalSale());
						today.setTotalSale(totalSale0.toString());
						Integer totalSaleNum0 = Integer.valueOf(today.getTotalSaleNum()) + Integer.valueOf(omr.getTotalSaleNum());
						today.setTotalSaleNum(totalSaleNum0.toString());
						break;
					case Constant.INDEX_DATA_TYPE_2_2:
						Float totalProfit = Float.valueOf(yvo.getTotalProfit()) + Float.valueOf(omr.getTotalProfit());
						yvo.setTotalProfit(totalProfit.toString());
						Float totalSale = Float.valueOf(yvo.getTotalSale()) + Float.valueOf(omr.getTotalSale());
						yvo.setTotalSale(totalSale.toString());
						Integer totalSaleNum = Integer.valueOf(yvo.getTotalSaleNum()) + Integer.valueOf(omr.getTotalSaleNum());
						yvo.setTotalSaleNum(totalSaleNum.toString());
						break;
					case Constant.INDEX_DATA_TYPE_2_3:
						Float totalProfit1 = Float.valueOf(j7vo.getTotalProfit()) + Float.valueOf(omr.getTotalProfit());
						j7vo.setTotalProfit(totalProfit1.toString());
						Float totalSale1 = Float.valueOf(j7vo.getTotalSale()) + Float.valueOf(omr.getTotalSale());
						j7vo.setTotalSale(totalSale1.toString());
						Integer totalSaleNum1 = Integer.valueOf(j7vo.getTotalSaleNum()) + Integer.valueOf(omr.getTotalSaleNum());
						j7vo.setTotalSaleNum(totalSaleNum1.toString());
						break;
					case Constant.INDEX_DATA_TYPE_2_4:
						Float totalProfit2 = Float.valueOf(j30vo.getTotalProfit()) + Float.valueOf(omr.getTotalProfit());
						j30vo.setTotalProfit(totalProfit2.toString());
						Float totalSale2 = Float.valueOf(j30vo.getTotalSale()) + Float.valueOf(omr.getTotalSale());
						j30vo.setTotalSale(totalSale2.toString());
						Integer totalSaleNum2 = Integer.valueOf(j30vo.getTotalSaleNum()) + Integer.valueOf(omr.getTotalSaleNum());
						j30vo.setTotalSaleNum(totalSaleNum2.toString());
						break;
					}
				}
			}
			List<OneMonthReviewVo> list = new ArrayList<OneMonthReviewVo>();
			list.add(today);
			list.add(yvo);
			list.add(j7vo);
			list.add(j30vo);
			redisOps.setObject(Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+Constant.ZHILAI_CORP_ID, list);
			//CacheUtils.put("serverCache",Constant.INDEX_SUMMARY_ONEMONTH_CACHE_+Constant.ZHILAI_CORP_ID, list);
			//	初始化缓存近半年营收分析数据
			// 近半年
			List<String> m = DateUtils.getListMonths(6);
			List<ReportMsale> listMsale = indexService.selectReportMsaleForZhi(m);
			//将没有数据的月份填充0
			List<ReportMsale> resultList6 = IndexUtil.fillingZero(listMsale, m);
			YsColChartVo y6vo = IndexUtil.assembleColumn(resultList6);
			CacheUtils.put(Constant.INDEX_SUMMARY_YS6_CACHE_+Constant.ZHILAI_CORP_ID, y6vo);
			// 近1年
			m = DateUtils.getListMonths(12);
			List<ReportMsale> list12 = indexService.selectReportMsaleForZhi(m);
			//将没有数据的月份填充0
			List<ReportMsale> resultList12 = IndexUtil.fillingZero(list12, m);
			YsColChartVo y12vo = IndexUtil.assembleColumn(resultList12);
			CacheUtils.put(Constant.INDEX_SUMMARY_YS12_CACHE_+Constant.ZHILAI_CORP_ID, y12vo);
			// 初始化缓存首页今日销售汇总 显示曲线图使用
			Date now = new Date();
			List<ReportDsale> dsales = indexService.selectTodayReportZhi(DateUtils.getYYYY(now), DateUtils.getMM(now), DateUtils.getDD(now));
			TodaySummaryVo todaysale = IndexUtil.assembleLine(dsales);
			CacheUtils.put(Constant.INDEX_SUMMARY_TODAYSALE_CACHE_+Constant.ZHILAI_CORP_ID, todaysale);
			// 初始化缓存首页今日销售点位排行 top10
			List<ReportOsale> lineTop10 = indexService.getTodayLineTop10("");
			CacheUtils.put(Constant.INDEX_SUMMARY_TODAYDW_CACHE_+Constant.ZHILAI_CORP_ID, lineTop10);
			// 
			List<ReportOsale> productTop10 = indexService.getTodayProductTop10("");
			List<Map<String, Object>> listProductTop10  = IndexUtil.assembleTie(productTop10);
			CacheUtils.put(Constant.INDEX_SUMMARY_TODAYSP_CACHE_+Constant.ZHILAI_CORP_ID, listProductTop10);
		}
	}
	
	/**
	 * 初始化首页缓存
	 */
	public void initIndexCache() {
		log.debug("初始化首页缓存 time:" + DateUtils.getTime());
		// 获取所有商户
		Collection<Corp> corps;
		Object o = CacheUtils.get(Constant.CORP_CACHE);
		if (o == null) {
			corps = corpService.selectCorpList(new Corp());
		} else {
			Map<String, Corp> map = (Map<String, Corp>)o;
			corps = map.values();
		}
		for (Corp corp : corps) {
			
			String corpId = corp.getCorpId();
			if (Constant.ZHILAI_CORP_ID.equals(corpId)) {	// 如果是宇宙星空则先不进行缓存初始化，放在之后进行
				continue;
			}
			log.debug("初始化商户:"+corp.getCorpId()+","+corp.getCorpName()+" 首页缓存 time:" + DateUtils.getTime());
			this.initIndex(corpId);// 初始化首页运营总览缓存
			this.initIndexOneMonth(corpId);// 初始化首页近一月总览数据，放入缓存
			this.initSelfYearSale(corpId);//	初始化缓存近半年营收分析数据
			this.initTodaySummary(corpId);// 初始化缓存首页今日销售汇总 显示曲线图使用
			this.initTodayLineTop10(corpId);//初始化缓存首页今日销售点位排行 top10
			this.initTodayProductTop10(corpId);	// 初始化缓存首页今日销售商品排行 top10
		}
		// 初始化宇宙星空缓存
		this.initZhilaiCache();
	}
	
	/**
	 * 初始化项目所有缓存
	 */
	public void initAllCache() {
		log.info("initAllCache time:" + DateUtils.getTime());
		this.initDispatchCache();// 初始化行政区划缓存
		this.initDictCache();	// 初始化数据字典进入缓存
		this.initCorp();	// 初始化商户信息进缓存
		this.initVendingDistrict();// 初始化区域信息进缓存
		this.initVendingLine();	// 初始化线路信息进缓存
		this.initVendingPoint();	// 初始化点位信息进缓存
		this.initVendingModel();	// 初始化机型信息进缓存
		this.initVendingNum();// 初始化售卖机各状态机器的数量，主要用于售卖机列表页面使用
		this.initProduct();// 初始化商品信息
		this.initUser();	// 缓存人员信息
		this.initProductClass();//  缓存商品分类信息
		
		this.initIndexCache();	// 初始化首页
		this.initVending(); // 初始化售卖机基础信息
		this.initStockInfo();
		//初始化系统参数
		this.initSysParameter();
	}
	
	/**
	 * 初始化售卖机基础信息
	 */
	public void initVending() {
		Vending vending = new Vending();
		List<Vending> list = vendingService.selectVendingList(vending);
		Map<String, Vending> map = new HashMap<String, Vending>();
		for (Vending v : list) {			
			map.put(v.getSiteId(), v);
		}
		CacheUtils.put(Constant.VENDING_BASE_CACHE, map);
	}
	
	/**
	 * 缓存一个售卖机基础信息
	 */
	public void cacheVending(String siteId) {
		Vending vending = new Vending();
		vending.setSiteId(siteId);
		List<Vending> list = vendingService.selectVendingList(vending);
		if (list != null && !list.isEmpty()) {
			vending = list.get(0);
			Object o = CacheUtils.get(Constant.VENDING_BASE_CACHE);
			if (o != null) {
				Map<String, Vending> map = (Map<String, Vending>) o;
				map.put(vending.getSiteId(), vending);
				CacheUtils.put(Constant.VENDING_BASE_CACHE, map);
			}
		}
	}
	
	/**
	 * 初始化售卖机各状态机器的数量，主要用于售卖机列表页面使用
	 */
	public void initVendingNum() {
		Collection<Corp> corps;
		Object o = CacheUtils.get(Constant.CORP_CACHE);
		if (o == null) {
			corps = corpService.selectCorpList(new Corp());
		} else {
			Map<String, Corp> map = (Map<String, Corp>)o;
			corps = map.values();
		}
		// 对每个商户进行缓存,宇宙星空统计所有售卖机数量
		for (Corp corp : corps) {
			Vending vending = new Vending();
			if (!corp.getCorpId().equals(Constant.ZHILAI_CORP_ID)) {
				vending.setCorpId(corp.getCorpId());
			}
			List<Vending> all = vendingService.selectVendingList(vending);
			int allNum = all.size();	// 所有售卖机数量
			// 查询在线机器数
			vending.setNetSate(Constant.NET_STAE_ONLINE);
			List<Vending> onlineList = vendingService.selectVendingList(vending);
			int onlineNum = onlineList.size();
			// 查询离线机器数
			vending.setNetSate(Constant.NET_STAE_OFFLINE);
			List<Vending> outlineList = vendingService.selectVendingList(vending);
			int outlineNum = outlineList.size();
			// 查询未认证机器数
			vending.setNetSate("");
			vending.setCurState(Constant.VENDING_CURSTATE_NOTREGISTER);
			List<Vending> wzList = vendingService.selectVendingList(vending);
			int wzNum = wzList.size();
			// 查询已删除机器数
			vending.setCurState(Constant.VENDING_CURSTATE_DELETE);
			List<Vending> delList = vendingService.selectVendingList(vending);
			int delNum = delList.size();
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("allNum", allNum);
			map.put("onlineNum", onlineNum);
			map.put("outlineNum", outlineNum);
			map.put("wzNum", wzNum);
			map.put("delNum", delNum);
			CacheUtils.put(Constant.VENDING_NUM_CACHE_+corp.getCorpId(), map);
		}
	}
	
	
	
	public static void main(String[] args ) {
		OperateReviewVo operateReview = new OperateReviewVo();
		System.out.println(Float.valueOf(operateReview.getOnlineMachine()));
	}
}
