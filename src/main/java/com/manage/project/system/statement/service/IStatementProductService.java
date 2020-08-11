package com.manage.project.system.statement.service;

import java.util.List;
import com.manage.project.system.statement.vo.StatementProductVo;
import com.manage.project.system.statement.vo.StatementVo;

/**
 * 补货对账售出明细服务层接口
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
public interface IStatementProductService {
	
	/**
	 * 查询补货账单售出明细列表
	 * 
	 * @param vo 封装的查询条件 
	 * @return 补货对账售出明细
	 */
	public List<StatementProductVo> selectStatementProductList(StatementProductVo vo);

	/**
	 * 导入对账信息
	 * 
	 * @param statementProductList 导入对账信息列表
	 * @param vo 封装的补货账单售出明细查询条件
	 * @return 
	 */
	@Deprecated
	public int importStatement(List<StatementVo> importList, StatementProductVo vo);

	/**
	 * 修改补货账单售出明细信息
	 * 
	 * @param vo 补货账单售出明细信息
	 * @return
	 */
	public int update(StatementProductVo vo);

	/**
	 * 根据id补货账单售出明细信息
	 * 
	 * @param logid 记录编号
	 * @return 补货账单售出明细信息
	 */
	public StatementProductVo selectStatementProductById(String logid);

}
