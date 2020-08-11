package com.manage.project.system.statement.mapper;


import java.util.List;

import com.manage.project.system.statement.domain.StatementProduct;
import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.vo.StatementProductVo;	

/**
 * 补货对账售出明细 数据层
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
public interface StatementProductMapper 
{
	/**
     * 查询补货对账售出明细信息
     * 
     * @param logid 编号
     * @return 补货对账售出明细
     */
	public StatementProductVo selectStatementProductById(String logid);
	
	/**
     * 查询补货对账售出明细列表
     * 
     * @param statementProduct 封装的查询条件
     * @return 补货对账售出明细列表
     */
	public List<StatementProductVo> selectStatementProductList(StatementProduct statementProduct);
		
	/**
     * 修改补货对账售出明细
     * 
     * @param statementProduct 补货对账售出明细
     * @return 结果
     */
	public int updateStatementProduct(StatementProduct statementProduct);

	/**
	 * 根据补货单查询商品售出明细
	 * 
	 * @param statementSupply 补货单信息
	 * @return 补货对账售出明细列表
	 */
	public List<StatementProductVo> selectStatementProductListBySupply(StatementSupply statementSupply);
	

}