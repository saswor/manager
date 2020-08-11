package com.manage.project.system.statement.service;

import java.util.List;

import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.vo.StatementSupplyVo;
import com.manage.project.system.statement.vo.StatementVo;

/**
 * 对账补货 服务层接口
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
public interface IStatementSupplyService 
{
	/**
     * 查询对账补货信息
     * 
     * @param statementSupplyVo 对账补货ID
     * @return 对账补货信息
     */
	public StatementSupplyVo selectStatementSupplyById(String logid);
	
	/**
     * 查询对账补货列表
     * 
     * @param statementSupply 对账补货信息
     * @return 对账补货集合
     */
	public List<StatementSupplyVo> selectStatementSupplyList(StatementSupply statementSupply);
		
	/**
     * 修改对账补货
     * 
     * @param statementSupply 对账补货信息
     * @return 结果
     */
	public int updateStatementSupply(StatementSupply statementSupply);

	
	/**
     * 修改对账补货对账状态
     * 
     * @param statementSupply 对账补货信息
     * @return 结果
     */
	public int updateStatementSupplyState(String sOrderId,String siteId);

	/**
	 * 导入对账
	 * 
	 * @param importList 导入的账单信息
	 * @param statementSupply 数据库中的的账单信息
	 * @return
	 */
	public int importStatement(List<StatementVo> importList, StatementSupply statementSupply);

	/**
	 * 根据记录编号数组批量查询补货对账记录
	 * 
	 * @param logids 记录编号数组
	 * @return 补货对账记录
	 */
	public List<StatementSupplyVo> selectStatementSupplyListByLogids(String[] logids);
}
