package com.manage.project.system.base.controller;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.Corp;
import com.manage.project.system.base.service.ICorpService;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/corp")
public class CorpController extends BaseController
{
	private Logger log=LoggerFactory.getLogger(CorpController.class);
	@Autowired
	private ICorpService corpService;
	
	/**
	 * 查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(Corp corp)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			corp.setCorpId("");
		} else {
			corp.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<Corp> list = corpService.selectCorpList(corp);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
	 */
	@Log(title = "商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody Corp corp)
	{
		//只有宇宙星空用户有权限添加商户
		if(!SystemUtil.isZhilai()) {
			return AjaxResult.error("您没有权限进行当前操作");
		}
		// 校验商户名称
		if (StringUtils.isEmpty(corp.getCorpName())) {
			return AjaxResult.error("商户名称不能为空");
		}
		//校验商户名称是否中文、英文、数字组成
		if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", corp.getCorpName())) {
		return AjaxResult.error("商户名称只能是中文、英文、数字组成");
		}
		Corp corpName = corpService.selectCorpByCorpName(corp.getCorpName());
		if (corpName != null) {
			return AjaxResult.error("商户名称已被使用");
		}
		// 校验商户id
		if (StringUtils.isEmpty(corp.getCorpId())) {
			return AjaxResult.error("商户编码不能为空");
		}
		Corp c = corpService.selectCorpById(corp.getCorpId());
		if (c != null) {
			return AjaxResult.error("商户编码已被使用");
		}
		if (corp.getCorpId().length()!=4 ) {
			return AjaxResult.error("商户编码位数错误");
		}
		//校验商户编码是数字类型
    	if(!Pattern.matches("^[0-9]*$", corp.getCorpId())) {
    	     return error("商户编码只能是数字");
    	}
    	//校验负责人
    	if (StringUtils.isEmpty(corp.getLeader())) {
    		return AjaxResult.error("负责人不能为空");
		}
    	//校验负责人是中文和英文
    	if(!Pattern.matches("^[A-Za-z\u4e00-\u9fa5]+$", corp.getLeader())) {
   	     return error("负责人只能是中文和英文");
    	}
    	//校验负责人电话
    	if (StringUtils.isEmpty(corp.getLeaderMobile())) {
    		return AjaxResult.error("负责人电话不能为空");
		}
    	if (corp.getLeaderMobile().length()!=11) {
			return AjaxResult.error("负责人电话位数错误");
		}
    	//校验负责人电话是数字类型
    	if(!Pattern.matches("^[0-9]*$", corp.getLeaderMobile())) {
    	     return error("负责人电话只能是数字类型");
    	}
		corp.setLogid(UUID.randomUUID().toString());
		corp.setCreateTime(DateUtils.getTime());
		corp.setStateTime(DateUtils.getTime());
		if(StringUtils.isEmpty(corp.getCurState())) {
			corp.setCurState("1");
		}
		int result = corpService.insertCorp(corp);
		if(result==1) {
			log.info("新增商户:"+corp.toString()+",时间:"+DateUtils.getTime());
		}else {
			log.error("新增商户失败:"+corp.toString()+",时间:"+DateUtils.getTime());
		}		
		return toAjax(result);
	}
	
	/**
	 * 修改保存商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
	 */
	@Log(title = "商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody Corp corp)
	{	
		// 校验商户名称
		if (StringUtils.isEmpty(corp.getCorpName())) {
			return AjaxResult.error("商户名称不能为空");
		}
		//校验商户名称是否中文、英文、数字组成
		if(!Pattern.matches("^[\u4E00-\u9FA5A-Za-z0-9]+$", corp.getCorpName())) {
		return AjaxResult.error("商户名称只能是中文、英文、数字组成");
		}
		Corp corpName = corpService.selectCorpByCorpName(corp.getCorpName());
		if (corpName != null&&!corpName.getCorpId().equals(corp.getCorpId())) {
			return AjaxResult.error("商户名称已被使用");
		}
    	//校验负责人
    	if (StringUtils.isEmpty(corp.getLeader())) {
    		return AjaxResult.error("负责人不能为空");
		}
    	//校验负责人是中文和英文
    	if(!Pattern.matches("^[A-Za-z\u4e00-\u9fa5]+$", corp.getLeader())) {
   	     return error("负责人只能是中文和英文");
    	}
    	//校验负责人电话
    	if (StringUtils.isEmpty(corp.getLeaderMobile())) {
    		return AjaxResult.error("负责人电话不能为空");
		}
    	if (corp.getLeaderMobile().length()!=11) {
			return AjaxResult.error("负责人电话位数错误");
		}
    	//校验负责人电话是数字类型
    	if(!Pattern.matches("^[0-9]*$", corp.getLeaderMobile())) {
    	     return error("负责人电话只能是数字类型");
    	}
		int result = corpService.updateCorp(corp);
		if(result==1) {
			log.info("修改商户:"+corp.toString()+",时间:"+DateUtils.getTime());
		}else {
			log.error("修改商户失败:"+corp.toString()+",时间:"+DateUtils.getTime());
		}		
		return toAjax(result);
	}
	
	/**
	 * 删除商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。
	 */
	@Log(title = "商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{	
		if(!SystemUtil.isZhilai()) {
			return AjaxResult.error("您没有权限进行当前操作");
		}
		String[] idArr = ids.getIds().split(",");
		for (int i = 0; i < idArr.length; i++) {
			Corp corp = corpService.selectCorpById(idArr[i]);
			log.info("删除商户:"+corp.toString()+",时间:"+DateUtils.getTime());
		}
		return toAjax(corpService.deleteCorpByIds(ids.getIds()));
	}
	
	/**
	 * 模糊查询商户(托管公司，售货机平台可以托管客户从宇宙星空购买的柜子设备)。列表
	 */
	@GetMapping("/vaguelist")
	@ResponseBody
	public AjaxResult vaguelist(String corpName)
	{
        List<Corp> list = corpService.selectCorpByName(corpName);
		return AjaxResult.success(list);
	}
	
	/**
	 * 导出商户
	 */
	@Log(title = "导出商户", action = BusinessType.EXPORT)
	@PostMapping( "/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody Corp corp)
	{	
		if (SystemUtil.isZhilai()) {
			corp.setCorpId("");
		} else {
			corp.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		try {
			List<Corp> list = corpService.selectCorpList(corp);
			for (Corp corp2 : list) {
				corp2.setCurStateName(corp2.getCurStateName());
			}
	        ExcelUtil<Corp> util = new ExcelUtil<Corp>(Corp.class);
	        return util.exportExcel(list, "商户信息");
		}catch (Exception e) {
			log.error("导出商户信息失败:",e);
			return AjaxResult.error("导出商户信息失败,请联系管理员");
		}
	}
	
}
