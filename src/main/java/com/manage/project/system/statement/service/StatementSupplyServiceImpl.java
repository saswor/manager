package com.manage.project.system.statement.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.mapper.StatementProductMapper;
import com.manage.project.system.statement.mapper.StatementSupplyMapper;
import com.manage.project.system.statement.vo.StatementProductVo;
import com.manage.project.system.statement.vo.StatementSupplyVo;
import com.manage.project.system.statement.vo.StatementVo;

/**
 * 对账补货 服务层实现
 * 
 * @author 张佳伟
 * @date 2018-10-16
 */
@Service
public class StatementSupplyServiceImpl implements IStatementSupplyService 
{
	@Autowired
	private StatementSupplyMapper statementSupplyMapper;
	
	@Autowired
	private StatementProductMapper statementProductMapper;

	/**
     * 查询对账补货信息
     * 
     * @param logid 对账补货ID
     * @return 对账补货信息
     */
    @Override
	public StatementSupplyVo selectStatementSupplyById(String logid)
	{
	    return statementSupplyMapper.selectStatementSupplyById(logid);
	}
	
	/**
     * 查询对账补货列表
     * 
     * @param statementSupply 对账补货信息
     * @return 对账补货集合
     */
	@Override
	public List<StatementSupplyVo> selectStatementSupplyList(StatementSupply statementSupply)
	{
	    return statementSupplyMapper.selectStatementSupplyList(statementSupply);
	}
	
	/**
     * 修改对账补货信息
     * 
     * @param statementSupply 对账补货信息
     * @return 结果
     */
	@Override
	public int updateStatementSupply(StatementSupply statementSupply)
	{
	    return statementSupplyMapper.updateStatementSupply(statementSupply);
	}
	
	/**
     * 修改对账补货对账状态
     * 
     * @param sOrderId 补货编号
     * @param siteId 售货机编号
     * @return 结果
     */
	public int updateStatementSupplyState(String sOrderId,String siteId) {
		StatementProductVo StatementProductVo = new StatementProductVo();
		StatementProductVo.setSOrderId(sOrderId);
		StatementProductVo.setSiteId(siteId);
		List<StatementProductVo> list = statementProductMapper.selectStatementProductList(StatementProductVo);
		StatementSupplyVo statementSupplyVo = statementSupplyMapper.selectStatementSupplyBySOrderIdAndSiteId(sOrderId, siteId);
		//等待对账数量
		int waitNum=0;
		//对账正常数量
		int successNum=0;
		//对账异常数量
		int abnomarlNum=0;
		//异常金额
		float abnomarlMoney=0F;		
		for (StatementProductVo vo : list) {
			if(vo!=null&&"1".equals(vo.getOutType())) {
				//对账正常
				if("3".equals(vo.getCurState())) {
					successNum++;
				}
				//对账异常
				if("4".equals(vo.getCurState())) {
					abnomarlNum++;
					abnomarlMoney+=vo.getAbnomarlMoney();
				}
				//等待对账
				if("3".equals(vo.getCurState())) {
					waitNum++;
				}
			}
		}
		//全部对账完成
		if(waitNum==0) {
			//对账正常
			if(abnomarlNum==0) {
				statementSupplyVo.setCurState("3");
				//对账异常
			}else {
				statementSupplyVo.setCurState("4");
			}
			//部分信息未对账
		}else {
			statementSupplyVo.setCurState("2");
		}
		statementSupplyVo.setAbnomarlNum(abnomarlNum);
		statementSupplyVo.setAbnomarlMoney(abnomarlMoney);
		return statementSupplyMapper.updateStatementSupply(statementSupplyVo);
	}

	/**
	 * 导入对账信息
	 * 
	 * @param importList 导入对账信息列表
	 * @param statementSupply 	要对账的补货单的查询条件
	 * @return 
	 */
	@Override
	@Transactional
	public int importStatement(List<StatementVo> importList, StatementSupply statementSupply) {
		//将导入的对账信息转map
		Map<String, StatementVo> map = importList.stream().collect(Collectors.toMap(StatementVo::getTradeNo, Function.identity()));
		List<StatementProductVo> productList = statementProductMapper.selectStatementProductListBySupply(statementSupply);
		int ret=1;
		//将所有商品售出明细与导入的账单比对
		for (StatementProductVo statementProduct : productList) {
			String payState = statementProduct.getPayState();
			String returnType = statementProduct.getReturnType();
			String pTradeNo = statementProduct.getPTradeNo();
			String rTradeNo = statementProduct.getRTradeNo();
			Float saleRMoney = statementProduct.getSaleRMoney();
			Float saleReturn = statementProduct.getSaleReturn();
			StatementVo payVo = map.get(pTradeNo);
			StatementVo returnVo = map.get(rTradeNo);
			//判断补货商品售出明细是否异常
			boolean exceptionFlag=false;			
			float payAbnomarlMoney=0F;
			float returnAbnomarlMoney=0F;
			//支付账单对账
			if(payVo!=null) {
				payAbnomarlMoney=payVo.getPayMoney()-saleRMoney;
				//正常
				if("1".equals(payVo.getPayState())&&"2".equals(returnType)&&returnAbnomarlMoney==0) {
				
				}else {
					//异常
					exceptionFlag=true;
				}
			}
			//退款账单对账
			if(returnVo!=null) {
				returnAbnomarlMoney=returnVo.getPayMoney()-saleReturn;
				//正常
				if("2".equals(returnVo.getPayState())&&"2".equals(payState)&&payAbnomarlMoney==0) {
					
				}else {
					//异常
					exceptionFlag=true;
				}
			}
			//对账异常
			if(exceptionFlag) {
				statementProduct.setCurState("4");
				statementProduct.setAbnomarlMoney(payAbnomarlMoney-returnAbnomarlMoney);
			}else {
				//正常
				statementProduct.setCurState("3");
				statementProduct.setAbnomarlMoney(payAbnomarlMoney-returnAbnomarlMoney);
			}
			int result = statementProductMapper.updateStatementProduct(statementProduct);
			if(result==0) {
				ret=0;
			}
		}
		List<StatementSupplyVo> supplyList = statementSupplyMapper.selectStatementSupplyList(statementSupply);
		//修改每条补货单的对账状态
		for (StatementSupplyVo statementSupplyVo : supplyList) {
			int result = updateStatementSupplyState(statementSupplyVo.getSOrderId(),statementSupplyVo.getSiteId());
			if(result==0) {
				ret=0;
			}
		}
		return ret;
	}

	/**
	 * 根据记录编号数组批量查询补货对账记录
	 * 
	 * @param logids 记录编号数组
	 * @return 补货对账记录
	 */
	@Override
	public List<StatementSupplyVo> selectStatementSupplyListByLogids(String[] logids) {
		return statementSupplyMapper.selectStatementSupplyListByLogids(logids);
	}
	
}
