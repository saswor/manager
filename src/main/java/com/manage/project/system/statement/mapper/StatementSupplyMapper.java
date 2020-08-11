package com.manage.project.system.statement.mapper;

import java.util.List;

import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.vo.StatementSupplyVo;

/**
 * 对账补货 数据层
 * 
 * @author zhangjiawei
 * @date 2018-10-16
 */
public interface StatementSupplyMapper {
	
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
	 * 根据补货编号和站点编号查询补货对账信息
	 * 
	 * @param sOrderId	补货编号
	 * @param siteId	站点编号
	 * @return	补货信息
	 */
	public StatementSupplyVo selectStatementSupplyBySOrderIdAndSiteId(String sOrderId, String siteId);

	/**
	 * 根据记录编号数组批量查询补货对账记录
	 * 
	 * @param logids 记录编号数组
	 * @return 补货对账记录
	 */
	public List<StatementSupplyVo> selectStatementSupplyListByLogids(String[] logids);
	
}