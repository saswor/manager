package com.manage.project.system.statement.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.manage.project.system.statement.domain.OrderApply;
import com.manage.project.system.statement.domain.StatementProduct;
import com.manage.project.system.statement.domain.StatementSupply;
import com.manage.project.system.statement.mapper.OrderApplyMapper;
import com.manage.project.system.statement.mapper.StatementProductMapper;
import com.manage.project.system.statement.mapper.StatementSupplyMapper;
import com.manage.project.system.statement.vo.StatementProductVo;
import com.manage.project.system.statement.vo.StatementVo;

/**
 * 补货对账售出明细 服务层实现
 * 
 * @author 张佳伟
 * @date 2018-10-16
 */
@Service
public class StatementProductServiceImpl implements IStatementProductService {
	
	@Autowired
	private StatementProductMapper statementProductMapper;
	
	@Autowired
	private StatementSupplyMapper statementSupplyMapper;
	
	@Autowired
	private OrderApplyMapper orderApplyMapper;
	
    /**
	 * 查询补货账单售出明细列表
	 * 
	 * @param sOrderId 补货编号
	 * @param siteId 站点编号
	 * @return 补货对账售出明细
	 */
	@Override
	public List<StatementProductVo> selectStatementProductList(StatementProductVo vo) {
		vo.setOutType("1");
		return statementProductMapper.selectStatementProductList(vo);
	}

	/**
	 * 导入对账信息
	 * 
	 * @param statementProductList 导入对账信息列表
	 * @param vo 	封装的站点编号和补货编号
	 * @return 
	 */
	@Override
	@Deprecated
	@Transactional
	public int importStatement(List<StatementVo> importList, StatementProductVo vo) {
		vo.setOutType("1");
		List<StatementProductVo> list = statementProductMapper.selectStatementProductList(vo);
		StatementSupply statementSupply = statementSupplyMapper.selectStatementSupplyBySOrderIdAndSiteId(vo.getSOrderId(),vo.getSiteId());
		//将导入的对账信息转map
		Map<String, StatementVo> map = importList.stream().collect(Collectors.toMap(StatementVo::getTradeNo, Function.identity()));
		//异常商品数量
		int abnomarlNum=0;
		//异常金额
		float totalAbnomarlMoney=0F;
		//对账状态,默认3正常
		String statementState="";
		
		int ret=1;
		//判断是否所有商品都进行了对账
		boolean fullFlag=true;
		//判断补货对账是否异常
		boolean totalExceptionFlag=false;
		
		//遍历数据库中的补货商品售出明细,与导入账单比对
		for (StatementProductVo statementProduct : list) {		
			String payState = statementProduct.getPayState();
			String returnType = statementProduct.getReturnType();
			String pTradeNo = statementProduct.getPTradeNo();
			String rTradeNo = statementProduct.getRTradeNo();
			Float saleRMoney = statementProduct.getSaleRMoney();
			Float saleReturn = statementProduct.getSaleReturn();
			StatementVo payVo = map.get(pTradeNo);
			StatementVo returnVo = map.get(rTradeNo);
			if(payVo==null&&returnVo==null) {
				fullFlag=false;
				continue;
			}
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
					totalExceptionFlag=true;
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
					totalExceptionFlag=true;
				}
			}
			//对账异常
			if(exceptionFlag) {
				statementProduct.setCurState("3");
				statementProduct.setAbnomarlMoney(payAbnomarlMoney-returnAbnomarlMoney);
				abnomarlNum++;
				totalAbnomarlMoney+=statementProduct.getAbnomarlMoney();
				totalExceptionFlag=true;
			}else {
				//正常
				statementProduct.setCurState("2");
				statementProduct.setAbnomarlMoney(payAbnomarlMoney-returnAbnomarlMoney);
			}
			int result = statementProductMapper.updateStatementProduct(statementProduct);
			if(result==0) {
				ret=0;
			}
		}
		//修改补货对账表
		if(!fullFlag) {
			statementState="2";
		}else if(totalExceptionFlag){
			statementState="4";
		}else {
			statementState="3";
		}
		statementSupply.setAbnomarlNum(abnomarlNum);
		statementSupply.setAbnomarlMoney(totalAbnomarlMoney);
		statementSupply.setStatementState(statementState);
		int result = statementSupplyMapper.updateStatementSupply(statementSupply);
		if(result==0) {
			ret=0;
		}
		return ret;
	}
	
	
	/**
	 * 修改异常对账信息
	 * 
	 * @param vo 要修改的对账信息
	 * @return 
	 */
	@Override
	@Transactional
	public int update(StatementProductVo vo) {
		int ret1 = statementProductMapper.updateStatementProduct(vo);
		StatementProductVo statementProductVo = statementProductMapper.selectStatementProductById(vo.getLogid());
		OrderApply orderApply = new OrderApply();
		orderApply.setOrderId(statementProductVo.getOrderId());
		orderApply.setPayState(vo.getPayState());
		orderApply.setPayPrice(vo.getSaleRMoney());
		orderApply.setReturnType(vo.getReturnType());
		orderApply.setReturnMoney(vo.getSaleReturn());
		int ret2 = orderApplyMapper.updateOrderApply(orderApply);
		return (ret1+ret2==2?1:0);
	}

	/**
	 * 查询补货商品售出明细详细信息
	 * 
	 * @param logid 编号
	 * @return 补货商品售出明细详细信息
	 */
	@Override
	public StatementProductVo selectStatementProductById(String logid) {
		return statementProductMapper.selectStatementProductById(logid);
	}
	
}
