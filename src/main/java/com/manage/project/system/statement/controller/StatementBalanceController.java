package com.manage.project.system.statement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.web.controller.BaseController;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.service.IStatementBalanceService;
import com.manage.project.system.statement.service.IStatementSupplyService;
import com.manage.project.system.statement.vo.StatementSupplyVo;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 根据补货账单里提交后生成结算费用,此结算是针对于采购人员的，区域下的负责人。 信息操作处理
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
@Controller
@RequestMapping("/system/statementBalance")
public class StatementBalanceController extends BaseController{
	
	@Autowired
	private IStatementBalanceService statementBalanceService;
	
	@Autowired
	private IStatementSupplyService statementSupplyService;
	
	/**
	 * 对账结算
	 */
	@Log(title = "对账结算", action = BusinessType.INSERT)
	@PostMapping("/balance")
	@Deprecated
	@ResponseBody
	public AjaxResult balance(@RequestBody CommonVo ids){	
		int i=0;
		StatementSupplyVo vo = statementSupplyService.selectStatementSupplyById(ids.getIds());
		if("3".equals(vo.getStatementState())) {
			return toAjax(statementBalanceService.balance(vo));
		}else {
			return AjaxResult.error("对账成功才可以结算");
		}	
	}
	
}
