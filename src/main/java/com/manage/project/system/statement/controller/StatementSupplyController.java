package com.manage.project.system.statement.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.Parameter;
import com.manage.project.system.base.service.IParameterService;
import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.service.IStatementBalanceService;
import com.manage.project.system.statement.service.IStatementSupplyService;
import com.manage.project.system.statement.thread.StatementTask;
import com.manage.project.system.statement.vo.AlipayStatementVo;
import com.manage.project.system.statement.vo.StatementDateVo;
import com.manage.project.system.statement.vo.StatementProductVo;
import com.manage.project.system.statement.vo.StatementSupplyVo;
import com.manage.project.system.statement.vo.StatementVo;
import com.manage.project.system.statement.vo.WechatStatementVo;
import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.framework.web.page.TableDataInfo;

/**
 * 对账补货 信息操作处理
 * 
 * @author 张佳伟
 * @date 2018-10-16
 */
@Controller
@RequestMapping("/system/statementSupply")
public class StatementSupplyController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(StatementSupplyController.class);
	
	@Autowired
	private IStatementSupplyService statementSupplyService;
	
	@Autowired
	private IStatementBalanceService statementBalanceService;
	
	@Autowired
	private IParameterService parameterService;
	
	/**
	 * 查询对账补货列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StatementSupply statementSupply){
		if (SystemUtil.isZhilai()) {
			statementSupply.setCorpId("");
		} else {
			statementSupply.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		startPage();
        List<StatementSupplyVo> list = statementSupplyService.selectStatementSupplyList(statementSupply);
        TableDataInfo dataTable = getDataTable(list);
        for (StatementSupplyVo vo : list) {
        	String siteId = vo.getSiteId();
        	Vending vending = SystemUtil.getVendingBase(siteId);
        	if(vending!=null) {
        		vo.setPointId(vending.getPointId());
        		vo.setPointName(SystemUtil.getVendingPointNameFromCache(vending.getPointId()));
        	}
        	vo.setLineName(SystemUtil.getVendingLineNameFromCache(vo.getLineId()));
        	vo.setSalteStateName(SystemUtil.parseSalteState(vo.getSalteState()));
		}
		return AjaxResult.success(dataTable);
	}
	
	/**
	 * 查询对账补货详细信息
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String logid)
	{
		StatementSupplyVo vo = statementSupplyService.selectStatementSupplyById(logid);
		if(vo!=null) {
			String districtId = vo.getDistrictId();
			vo.setDistrictName(SystemUtil.getVendingDistrictNameFromCache(districtId));
			String siteId = vo.getSiteId();
			Vending vending = SystemUtil.getVendingBase(siteId);
			if(vending!=null) {
				vo.setPointId(vending.getPointId());
				vo.setPointName(SystemUtil.getVendingPointNameFromCache(vending.getPointId()));
			}
			vo.setLineName(SystemUtil.getVendingLineNameFromCache(vo.getLineId()));
			StockInfo stock = SystemUtil.getStockInfo(vo.getWmId());
			if(stock!=null) {
				vo.setWmName(stock.getStockName());
			}
			vo.setSalteStateName(SystemUtil.parseSalteState(vo.getSalteState()));
			vo.setStatementStateName(SystemUtil.parseStatementState(vo.getStatementState()));
			vo.setCurStateName(SystemUtil.parseStatementSupplyCurState(vo.getCurState()));
		}		
		return AjaxResult.success(vo);
	}
	
	/**
	 * 导入对账
	 */
	@PostMapping("/importStatement")
	@ResponseBody
	public AjaxResult importStatement(@RequestParam(value = "file", required = false) MultipartFile file,StatementSupplyVo statementSupply,String payType){
		String paraName="";
		String corpId = ShiroUtils.getUser().getCorpId();
		paraName= "corp_"+corpId;
		if (SystemUtil.isZhilai()) {
			statementSupply.setCorpId("");
		} else {			
			statementSupply.setCorpId(corpId);			
		}
		try {
			List<StatementVo> importList= new ArrayList<StatementVo>();
			if("1".equals(payType)) {
				// 创建excel工具类，返回Excel中的数据
				ExcelUtil<AlipayStatementVo> util = new ExcelUtil<AlipayStatementVo>(AlipayStatementVo.class);				
				List<AlipayStatementVo> alipayList = util.importExcel("Statement", file.getInputStream());
				importList.addAll(alipayList);
			}else {
				// 创建excel工具类，返回Excel中的数据
				ExcelUtil<WechatStatementVo> util = new ExcelUtil<WechatStatementVo>(WechatStatementVo.class);
				List<WechatStatementVo> wechatList = util.importExcel("Statement", file.getInputStream());
				importList.addAll(wechatList);
			}
			//对上传账单的开始时间进行校验
			Date startTime=importList.get(0).getPayTime();
			Date lastTime=importList.get(importList.size()-1).getPayTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastDate = sdf.format(lastTime);
			AjaxResult checkResult = checkDate(paraName, payType, startTime, statementSupply.getTradeSTime());
			Map zhead = (Map) checkResult.get("zhead");
			if(!("0000".equals(zhead.get("reTCode")))) {
				return checkResult;
			}
			//创建额外线程执行对账过程
			log.info("对账 time:"+DateUtils.getTime());
			StatementTask statementTask = new StatementTask();
			statementTask.setImportList(importList);
			statementTask.setStatementSupply(statementSupply);
			Thread thread = new Thread(statementTask);
			thread.start();
			updateLastDate(lastDate, payType, paraName);
			return success();
			//return toAjax(statementSupplyService.importStatement(importList,statementSupply));			
		} catch (Exception e) {
			return error("导入Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 对账结算
	 */
	@Log(title = "对账结算", action = BusinessType.INSERT)
	@PostMapping("/balance")
	@ResponseBody
	public AjaxResult balance(@RequestBody CommonVo commonVo){
		String ids = commonVo.getIds();
		if(StringUtils.isEmpty(ids)) {
			return AjaxResult.error("请至少选中一行");
		}
		String[] logids = ids.split(",");
		List<StatementSupplyVo> list = statementSupplyService.selectStatementSupplyListByLogids(logids);
		int ret=0;
		log.info("补货账单结算 time:"+DateUtils.getTime());
		if(StringUtils.isEmpty(list)) {
			return AjaxResult.error("请至少选中一行");
		}
		for (StatementSupplyVo vo : list) {
			if("3".equals(vo.getStatementState())) {
				ret+=statementBalanceService.balance(vo);
			}else {
				return AjaxResult.error("对账成功才可以结算");
			}	
		}
		return toAjax(ret==list.size()?1:0);
	}
	
	/**
	 * 检查上传的账单日期
	 * 
	 * @param paraName 存放上次上传账单的记录名称
	 * @param payType 支付类型
	 * @param startTime 本次上传的起始日期
	 * @param tradeSTime 要对账的交易开始日期
	 * @return 检查的结果
	 */
	private AjaxResult checkDate(String paraName,String payType,Date startDate,String tradeSTime) {
		//查出上次导入的账单的截止日期,和本次的开始时间比对
		Parameter parameter = parameterService.selectParameterByName(paraName);
		String createTime="";
		String lastDate="";
		if(parameter!=null) {
			StatementDateVo dateVo = JSON.parseObject(parameter.getValue(), StatementDateVo.class);
			createTime = dateVo.getCreateTime();
			//清除掉今天之前保存的数据
			if(!DateUtils.getDate().equals(createTime)) {
				log.info("删除今天之前的上传账单的截止日期信息:");
				parameterService.delete(parameter.getLogid());
				parameter=null;
			}
		}
		//获取上次上传订单的截止日期
		if(parameter!=null) {
			String value = parameter.getValue();
			 StatementDateVo vo = JSONObject.parseObject(value, StatementDateVo.class);
			 
			 if("1".equals(payType)) {
				 lastDate = vo.getAlipayDate();
			 }
			 if("2".equals(payType)) {
				 lastDate = vo.getWechatDate();
			 }
		}
		//和上次上传账单时间比对
		if(StringUtil.isNotEmpty(lastDate)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.dateTime("yyyy-MM-dd", lastDate));
			calendar.add(Calendar.DAY_OF_YEAR, 2);
			if(startDate.before(calendar.getTime())) {
				return success();
			}
			else {
				return error("上次上传账单的交易开始时间是"+lastDate+",请按时间顺序上传账单");
			}
		}else {
			//之前上传账单时间不存在,和交易开始时间比对
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.dateTime("yyyy-MM-dd", tradeSTime));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			if(startDate.before(calendar.getTime())) {
				return success();
			}else {
				return error("交易开始时间是"+tradeSTime+",请按时间顺序上传账单");
			}
		}
	}
	
	/**
	 * 修改上传账单的截止日期
	 * 
	 * @param lastDate 本次上传账单的截止日期
	 * @param payType 支付类型
	 * @param paraName 记录上传账单日期的记录名
	 * @return 结果
	 */
	private int updateLastDate(String lastTime,String payType,String paraName) {
		Parameter parameter = parameterService.selectParameterByName(paraName);
		//如果之前记录不存在,重新插入一条记录
		if(parameter==null) {
			Parameter updateParam = new Parameter();
			updateParam.setLogid(UUID.randomUUID().toString());
			updateParam.setName(paraName);
			String corpId=paraName.substring(5);
			updateParam.setParaCode(SystemUtil.getSeqId(corpId, "sys_parameter"));
			updateParam.setDescription(corpId+"上次上传账单时间");
			StatementDateVo statementDateVo = new StatementDateVo();
			statementDateVo.setCreateTime(DateUtils.getDate());
			if("1".equals(payType)) {
				statementDateVo.setAlipayDate(lastTime);
			}
			if("2".equals(payType)) {
				statementDateVo.setWechatDate(lastTime);
			}
			updateParam.setValue(JSONObject.toJSONString(statementDateVo));
			log.info("插入新的上传账单的截止日期记录:");
			return parameterService.insert(updateParam);
		}else {
			//存在修改原本记录的value值
			String value = parameter.getValue();
			StatementDateVo statementDateVo = JSONObject.parseObject(value, StatementDateVo.class);
			if("1".equals(payType)) {
				statementDateVo.setAlipayDate(lastTime);
			}
			if("2".equals(payType)) {
				statementDateVo.setWechatDate(lastTime);
			}
			parameter.setValue(JSONObject.toJSONString(statementDateVo));
			log.info("修改上传账单的截止日期记录:");
			return parameterService.update(parameter);
		}
	}
}

