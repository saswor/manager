package com.manage.project.system.statement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.project.system.statement.service.IStatementProductService;
import com.manage.project.system.statement.vo.StatementProductVo;
import com.manage.project.system.statement.vo.StatementVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 补货对账售出明细
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
@Controller
@RequestMapping("/system/statementProduct")
public class StatementProductController extends BaseController{
	
	@Autowired
	private IStatementProductService statementProductService;

	/**
	 * 导入对账信息
	 */
	@Log(title = "对账商品", action = BusinessType.IMPORT)
	@Deprecated
	@PostMapping( "/importStatement")
	@ResponseBody
	public AjaxResult importStatement(@RequestParam(value = "file", required = false) MultipartFile file,StatementProductVo vo) {
		try {
			ExcelUtil<StatementVo> util = new ExcelUtil<StatementVo>(StatementVo.class);
			// 创建excel工具类，返回Excel中的数据
			List<StatementVo> importList = util.importExcel("Statement", file.getInputStream());// 导入		
			return toAjax(statementProductService.importStatement(importList,vo));			
		} catch (Exception e) {
			return error("导入Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 查询补货对账售出明细列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(StatementProductVo vo)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			vo.setCorpId("");
		} else {
			vo.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<StatementProductVo> list = statementProductService.selectStatementProductList(vo);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询补货对账售出明细详细信息
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String logid)
	{
		StatementProductVo vo = statementProductService.selectStatementProductById(logid);
        return AjaxResult.success(vo);
	}
	
	/**
	 * 修改对账信息
	 */
	@Log(title = "对账商品", action = BusinessType.UPDATE)
	@PostMapping( "/update")
	@ResponseBody
	public AjaxResult update(@RequestBody StatementProductVo vo) {	
		return toAjax(statementProductService.update(vo));			
	}
}
