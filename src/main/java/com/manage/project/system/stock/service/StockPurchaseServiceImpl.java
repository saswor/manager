package com.manage.project.system.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.stock.mapper.StockInboundMapper;
import com.manage.project.system.stock.mapper.StockPinboundMapper;
import com.manage.project.system.stock.mapper.StockPpurchaseMapper;
import com.manage.project.system.stock.mapper.StockProductMapper;
import com.manage.project.system.stock.mapper.StockPurchaseMapper;
import com.manage.project.system.stock.mapper.StockWarehouseMapper;
import com.manage.project.common.Constant;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.stock.domain.StockInbound;
import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.stock.domain.StockPinbound;
import com.manage.project.system.stock.domain.StockPpurchase;
import com.manage.project.system.stock.domain.StockProduct;
import com.manage.project.system.stock.domain.StockPurchase;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.service.IStockPurchaseService;
import com.manage.project.system.stock.vo.ExtraPurchaseVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 仓库采购记录 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class StockPurchaseServiceImpl implements IStockPurchaseService 
{
	@Autowired
	private StockPurchaseMapper stockPurchaseMapper;
	
	@Autowired
	private StockPpurchaseMapper stockPpurchaseMapper;
	@Autowired
	private StockInboundMapper stockInboundMapper;
	@Autowired
	private StockPinboundMapper stockPinboundMapper;
	@Autowired
	private StockProductMapper stockProductMapper;
	@Autowired
	private StockWarehouseMapper stockWarehouseMapper;
	/**
     * 查询仓库采购记录信息
     * 
     * @param logid 仓库采购记录ID
     * @return 仓库采购记录信息
     */
    @Override
	public StockPurchase selectStockPurchaseById(String logid)
	{
	    return stockPurchaseMapper.selectStockPurchaseById(logid);
	}
	
	/**
     * 查询仓库采购记录列表
     * 
     * @param stockPurchase 仓库采购记录信息
     * @return 仓库采购记录集合
     */
	@Override
	public List<StockPurchase> selectStockPurchaseList(StockPurchase stockPurchase)
	{
	    return stockPurchaseMapper.selectStockPurchaseList(stockPurchase);
	}
	
    /**
     * 新增仓库采购记录
     * 
     * @param stockPurchase 仓库采购记录信息
     * @return 结果
     */
	@Override
	public int insertStockPurchase(StockPurchase stockPurchase)
	{
	    return stockPurchaseMapper.insertStockPurchase(stockPurchase);
	}
	
	@Override
	@Transactional
	public int insertPurchase(StockPurchase stockPurchase, List<StockPpurchase> stockPpurchases)
	{
	    int r = stockPurchaseMapper.insertStockPurchase(stockPurchase);
	    for (StockPpurchase stockPpurchase : stockPpurchases) {
	    	if (stockPpurchase.getIsDel() == 0) {	// 如果在页面上没有删除，则插入
	    		stockPpurchaseMapper.insertStockPpurchase(stockPpurchase);
	    	}
	    }
	    return r;	    
	}
	
	/**
     * 修改仓库采购记录
     * 
     * @param stockPurchase 仓库采购记录信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateStockPurchase(StockPurchase stockPurchase)
	{
		if(Constant.PURCHASE_CHECK_STATE_SUCCESS.equals(stockPurchase.getCheckState())) {
			//修改采购单的采购金额
	    	List<StockPpurchase> stockPpurchases = stockPurchase.getStockPpurchases();
	    	Float sumTotalPrice=0F;
	    	for (StockPpurchase stockPpurchase : stockPpurchases) {
	    		//采购金额不能为0
	    		Float buyPrice = stockPpurchase.getBuyPrice();
	    		if(buyPrice==null) {
	    			return -1;
	    		}
	    		//采购金额要在0-1000000之间
	    		if(buyPrice<=0||buyPrice>=1000000) {
	    			return -2;
	    		}
	    		Float totalPrice=stockPpurchase.getBuyPrice()*stockPpurchase.getPnum();
	    		sumTotalPrice+=totalPrice;
	    		stockPpurchase.setTotalPrice(totalPrice);
	    		stockPpurchaseMapper.updateStockPpurchase(stockPpurchase);
			}
	    	stockPurchase.setTotalPrice(sumTotalPrice);
		}
	    int r = stockPurchaseMapper.updateStockPurchase(stockPurchase);
	    if (stockPurchase.getCheckState().equals(Constant.PURCHASE_CHECK_STATE_FAIL)) {	// 如果审核未通过
	    	return r;
	    }else {
	    	
	    }
	    
	    String wPurchaseId = stockPurchase.getWpurchaseId();
	    stockPurchase = stockPurchaseMapper.selectStockPurchaseById(wPurchaseId);
	    StockPpurchase stockPpurchase = new StockPpurchase();
	    stockPpurchase.setWpurchaseId(wPurchaseId);
	    List<StockPpurchase> stockPpurchases = stockPpurchaseMapper.selectStockPpurchaseList(stockPpurchase);
	    // 新增入库单
	    String corpId = stockPurchase.getCorpId();
	    StockInbound stockInbound = new StockInbound();
	    stockInbound.setLogid(UUID.randomUUID().toString());
	    String wInboundId = SystemUtil.getSeqId(corpId, "as_stock_inbound");
	    stockInbound.setWinboundId(wInboundId);
	    String stockId = stockPurchase.getStockId();
	    String stokcName = stockPurchase.getStokcName();
	    stockInbound.setStockId(stockId);
	    stockInbound.setStokcName(stokcName);
	    stockInbound.setPnum(0);
	    stockInbound.setTnum(0);
	    stockInbound.setTotalPrice(0f);
	    stockInbound.setCurState("1");
	    String curTime = DateUtils.getTime();
	    stockInbound.setStateTime(curTime);
	    stockInbound.setWpurchaseId(stockPurchase.getWpurchaseId());
	    stockInbound.setCorpId(corpId);
	    stockInbound.setInboundId(ShiroUtils.getUserId().toString());
	    stockInbound.setInboundType("1");
	    stockInbound.setCreateTime(curTime);
	    stockInboundMapper.insertStockInbound(stockInbound);
	    // 新增入库明细
	    for (StockPpurchase sp : stockPpurchases) {
	    	StockPinbound s = new StockPinbound();
	    	s.setLogid(UUID.randomUUID().toString());
	    	s.setWpInboundId(SystemUtil.getSeqId(corpId, "as_stock_pinbound"));
	    	s.setWinboundId(wInboundId);
	    	s.setStockId(stockId);
	    	s.setStokcName(stokcName);
	    	s.setProductId(sp.getProductId());
	    	s.setProductName(sp.getProductName());
	    	s.setPnum(0);
	    	s.setBuyPrice(sp.getBuyPrice());
	    	s.setTotalPrice(0f);
	    	s.setCorpId(corpId);
	    	s.setCreateTime(curTime);
	    	stockPinboundMapper.insertStockPinbound(s);
	    }
	    //修改仓库的库存
	    for (StockPpurchase sp : stockPpurchases) {
	    	StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(sp.getCorpId(),sp.getProductId());
	    	//当前商品库存存在,修改库存数量
	    	if(stockProduct!=null) {
	    		Integer curNum = stockProduct.getCurNum()==null?0:stockProduct.getCurNum();
	    		curNum+=sp.getPnum();
	    	}
		}
	    return r;
	}

	/**
     * 删除仓库采购记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStockPurchaseByIds(String ids)
	{
		return stockPurchaseMapper.deleteStockPurchaseByIds(Convert.toStrArray(ids));
	}

	/**
	 * 根据仓采购记录编号查询记录
	 * 
	 * @param wpurchaseId 仓采购记录编号
	 * @return
	 */
	@Override
	public StockPurchase selectStockPurchaseByWpurchaseId(String wpurchaseId) {
		return stockPurchaseMapper.selectStockPurchaseByWpurchaseId(wpurchaseId);
	}

	/**
	 * 补货过程中额外采购
	 * 
	 * @param stockWarehouses
	 * @return
	 */
	@Override
	@Transactional
	public int extraPurchase(ExtraPurchaseVo vo) {
		//采购商品为空
		List<StockWarehouse> list=vo.getStockWarehouses();
		if(list==null||list.isEmpty()) {
			return 2;
		}
		String userId=ShiroUtils.getUserId().toString();
		String loginName=ShiroUtils.getLoginName();
		String userName="";
		User user = ShiroUtils.getUser();
		if(user!=null) {
			userName=user.getUserName();
		}
		String corpId=ShiroUtils.getCorpId();
		String stockId=vo.getStockId();
		String stockName="";
		StockInfo stock = SystemUtil.getStockInfo(stockId);
		if(stock!=null) {
			stockName=stock.getStockName();
		}
		String wpurchaseId = SystemUtil.getSeqId(corpId, "as_stock_purchase");
		String winboundId = SystemUtil.getSeqId(corpId, "as_stock_inbound");
		
		List<StockWarehouse> stockWarehouses = new ArrayList<StockWarehouse>();
		List<StockPpurchase> stockPpurchases = new ArrayList<StockPpurchase>();
		List<StockPinbound> stockPinbounds = new ArrayList<StockPinbound>();
		List<StockProduct> stockProducts = new ArrayList<StockProduct>();
		StockInbound stockInbound = new StockInbound();
		StockPurchase stockPurchase = new StockPurchase();
		
		//采购单
		stockPurchase.setLogid(UUID.randomUUID().toString());		
		stockPurchase.setWpurchaseId(wpurchaseId);
		stockPurchase.setStockId(stockId);
		stockPurchase.setStokcName(stockName);
		stockPurchase.setProductId("");
		stockPurchase.setProductName("");
		stockPurchase.setCurState(Constant.PURCHASE_CUR_STATE_NORMAL);
		stockPurchase.setStateTime(DateUtils.getTime());
		stockPurchase.setCheckState(Constant.PURCHASE_CHECK_STATE_SUCCESS);
		stockPurchase.setCheckTime(DateUtils.getTime());
		stockPurchase.setCheckId(userId);
		stockPurchase.setStockState(Constant.STOCK_STATE_SUCCESS);
		stockPurchase.setStockSTime(DateUtils.getTime());
		stockPurchase.setDescription("");
		stockPurchase.setCorpId(corpId);
		stockPurchase.setCreateTime(DateUtils.getTime());
		stockPurchase.setSupplyId(userId);
		stockPurchase.setSupplyName(userName);
		//入库单		
		stockInbound.setLogid(UUID.randomUUID().toString());
		stockInbound.setWinboundId(winboundId);
		stockInbound.setWpurchaseId(wpurchaseId);
		stockInbound.setStockId(stockId);
		stockInbound.setStokcName(stockName);
		stockInbound.setCurState(Constant.STOCK_STATE_SUCCESS);
		stockInbound.setStateTime(DateUtils.getTime());
		stockInbound.setInboundId(loginName);
		stockInbound.setCorpId(corpId);
		stockInbound.setCreateTime(DateUtils.getTime());
		stockInbound.setInboundType(Constant.INBOUND_TYPE_NORMAL);
		
		int sumPnum=0;
		float sumTotalPrice=0F;
		int tnum=0;
		for (StockWarehouse sw : list) {
			String productId=sw.getProductId();
			String productName=SystemUtil.getProductNameById(productId);
			int pnum=sw.getpNum();
			float buyPrice=sw.getBuyPrice();
			float totalPrice=pnum*buyPrice;
			if(pnum<0) {
				return 3;
			}
			if(pnum>9999999) {
				return 4;
			}
			if(pnum==0) {
				continue;
			}
			tnum++;
			sumPnum+=pnum;
			sumTotalPrice+=totalPrice;
			//采购单明细
			StockPpurchase stockPpurchase = new StockPpurchase();
			stockPpurchase.setLogid(UUID.randomUUID().toString());
			stockPpurchase.setWppurchaseId(SystemUtil.getSeqId(corpId, "as_stock_ppurchase"));
			stockPpurchase.setWpurchaseId(wpurchaseId);
			stockPpurchase.setStockId(stockId);
			stockPpurchase.setStokcName(stockName);
			stockPpurchase.setProductId(productId);
			stockPpurchase.setProductName(productName);
			stockPpurchase.setPnum(pnum);
			stockPpurchase.setBuyPrice(buyPrice);
			stockPpurchase.setTotalPrice(totalPrice);
			stockPpurchase.setCorpId(corpId);
			stockPpurchase.setCreateTime(DateUtils.getTime());
			stockPpurchases.add(stockPpurchase);
			//入库单明细
			StockPinbound stockPinbound = new StockPinbound();
			stockPinbound.setLogid(UUID.randomUUID().toString());
			stockPinbound.setWpInboundId(SystemUtil.getSeqId(corpId, "as_stock_pinbound"));
			stockPinbound.setWinboundId(winboundId);
			stockPinbound.setStockId(stockId);
			stockPinbound.setStokcName(stockName);
			stockPinbound.setProductId(productId);
			stockPinbound.setProductName(productName);
			stockPinbound.setPnum(pnum);
			stockPinbound.setBuyPrice(buyPrice);
			stockPinbound.setTotalPrice(totalPrice);
			stockPinbound.setCorpId(corpId);
			stockPinbound.setCreateTime(DateUtils.getTime());
			stockPinbound.setInboundType(Constant.INBOUND_TYPE_NORMAL);		
			stockPinbounds.add(stockPinbound);
			//查询出对应商品的仓库库存
			StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(productId, stockId, corpId);
			if(stockWarehouse!=null) {
				stockWarehouse.setCurNum(stockWarehouse.getCurNum()+pnum);
				stockWarehouse.setTotalNum(stockWarehouse.getTotalNum()+pnum);
				stockWarehouses.add(stockWarehouse);
			}else {
				return 5;
			}		
			//查询出对应商品的系统库存
			StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(corpId, productId);
			if(stockProduct!=null) {
				stockProduct.setCurNum(stockProduct.getCurNum()+pnum);
				stockProduct.setTotalNum(stockProduct.getTotalNum()+pnum);
				stockProducts.add(stockProduct);
			}else {
				return 6;
			}
		}
		if(sumPnum==0) {
			return 2;
		}
		stockPurchase.setPnum(sumPnum);
		stockPurchase.setTotalPrice(sumTotalPrice);
		stockPurchase.setTnum(tnum);
		stockInbound.setPnum(sumPnum);
		stockInbound.setTotalPrice(sumTotalPrice);
		stockInbound.setTnum(tnum);
		//修改对应表中信息,后期优化
		stockPurchaseMapper.insertStockPurchase(stockPurchase);
		stockInboundMapper.insertStockInbound(stockInbound);
		for (StockProduct stockProduct : stockProducts) {
			stockProductMapper.updateStockProduct(stockProduct);
		}
		for (StockWarehouse stockWarehouse : stockWarehouses) {
			stockWarehouseMapper.updateStockWarehouse(stockWarehouse);
		}
		for (StockPpurchase stockPpurchase : stockPpurchases) {
			stockPpurchaseMapper.insertStockPpurchase(stockPpurchase);
		}
		for (StockPinbound stockPinbound : stockPinbounds) {
			stockPinboundMapper.insertStockPinbound(stockPinbound);
		}
		return 1;
	}
	
}
