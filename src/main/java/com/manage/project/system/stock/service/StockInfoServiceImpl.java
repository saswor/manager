package com.manage.project.system.stock.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.stock.mapper.StockInboundMapper;
import com.manage.project.system.stock.mapper.StockInfoMapper;
import com.manage.project.system.stock.mapper.StockPurchaseMapper;
import com.manage.project.system.stock.mapper.StockWarehouseMapper;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.mapper.SupplyOrderMapper;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.common.Constant;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.mapper.ProductInfoMapper;
import com.manage.project.system.stock.domain.StockInbound;
import com.manage.project.system.stock.domain.StockInfo;
import com.manage.project.system.stock.domain.StockPurchase;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 仓库入库记录 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class StockInfoServiceImpl implements IStockInfoService 
{
	@Autowired
	private StockInfoMapper stockInfoMapper;	
	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private StockWarehouseMapper stockWarehouseMapper;
	@Autowired
	private StockPurchaseMapper stockPurchaseMapper;
	@Autowired
	private StockInboundMapper stockInboundMapper;
	@Autowired
	private SupplyOrderMapper supplyOrderMapper;
	@Autowired
	private BussinessCacheService bussinessCacheService;
	
	private Logger log=LoggerFactory.getLogger(StockInfoServiceImpl.class);

	/**
     * 查询仓库入库记录信息
     * 
     * @param logid 仓库入库记录ID
     * @return 仓库入库记录信息
     */
    @Override
	public StockInfo selectStockInfoById(String logid)
	{
	    return stockInfoMapper.selectStockInfoById(logid);
	}
	
	@Override
	public List<StockInfo> selectStockInfoList(StockInfo stockInfo)
	{
	    return stockInfoMapper.selectStockInfoList(stockInfo);
	}
	
    /**
     * 新增仓库入库记录
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertStockInfo(StockInfo stockInfo)
	{
		stockInfo.setCreateTime(DateUtils.getTime());
		stockInfo.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getCorpId();
		stockInfo.setCorpId(corpId);
		String stockId = SystemUtil.getSeqId(corpId, "as_stock_info");
		stockInfo.setStockId(stockId);
	    stockInfoMapper.insertStockInfo(stockInfo);
	    // 将该商户所商品初始化进商品库存表
	    ProductInfo productInfo = new ProductInfo();
	    productInfo.setCorpId(corpId);
	    List<ProductInfo> productList = productInfoMapper.selectProductInfoList(productInfo);
	    if (productList != null) {
	    	for (ProductInfo pi : productList) {
	    		StockWarehouse stockWarehouse = new StockWarehouse();
	    		stockWarehouse.setLogid(UUID.randomUUID().toString());
	    		stockWarehouse.setWstockId(SystemUtil.getSeqId(corpId, "as_stock_warehouse"));
	    		stockWarehouse.setStockId(stockId);
	    		stockWarehouse.setStokcName(stockInfo.getStockName());
	    		stockWarehouse.setProductId(pi.getProductId());
	    		stockWarehouse.setProductName(pi.getName());
	    		stockWarehouse.setTotalNum(0);
	    		stockWarehouse.setCurNum(0);
	    		stockWarehouse.setOverNum(0);
	    		stockWarehouse.setWarnNum(0);
	    		stockWarehouse.setCorpId(corpId);
	    		stockWarehouse.setCreateTime(DateUtils.getTime());
	    		stockWarehouseMapper.insertStockWarehouse(stockWarehouse);
	    	}
	    }
	    bussinessCacheService.initStockInfo();
	    return 1;
	}
	
	/**
     * 修改仓库入库记录
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateStockInfo(StockInfo stockInfo)
	{
		StockInfo srcStock = stockInfoMapper.selectStockInfoById(stockInfo.getLogid());
		int r = stockInfoMapper.updateStockInfo(stockInfo);
		//仓库名称修改,修改对应的冗余信息
		if(!srcStock.getStockName().equals(stockInfo.getStockName())) {
			//修改仓库库存
			StockWarehouse stockWarehouseSelect = new StockWarehouse();
			List<StockWarehouse> stockWarehouseList = stockWarehouseMapper.selectStockWarehouse(stockWarehouseSelect);
			for (StockWarehouse stockWarehouse : stockWarehouseList) {
				stockWarehouse.setStokcName(stockInfo.getStockName());
				stockWarehouseMapper.updateStockWarehouse(stockWarehouse);
			}
		}
		
		bussinessCacheService.initStockInfo();
	    return r;
	    
	}

	/**
     * 删除仓库入库记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	@Transactional
	public int deleteStockInfoByIds(String ids)
	{
		List<StockInfo> stockInfoList = stockInfoMapper.selectStockInfoByIds(ids.split(","));
		for (StockInfo stockInfo : stockInfoList) {
			//查询当前仓库是否有商品库存
			StockWarehouse stockWarehouseSelect = new StockWarehouse();
			stockWarehouseSelect.setStockId(stockInfo.getStockId());
			List<StockWarehouse> stockWarehouseList = stockWarehouseMapper.selectStockWarehouseCurNumNotNull(stockWarehouseSelect);
			if(stockWarehouseList!=null&&stockWarehouseList.size()!=0) {
				return 2;
			}
			//查询是否有未完成的采购单
			StockPurchase stockPurchaseSelect = new StockPurchase();
			stockPurchaseSelect.setStockId(stockInfo.getStockId());
			stockPurchaseSelect.setCurState(Constant.PURCHASE_CUR_STATE_NORMAL);
			stockPurchaseSelect.setCheckId(Constant.PURCHASE_CHECK_STATE_WAIT);
			List<StockPurchase> stockPurchaseList = stockPurchaseMapper.selectStockPurchaseList(stockPurchaseSelect);
			if(stockPurchaseList!=null&&stockPurchaseList.size()!=0) {
				return 3;
			}
			//查询是否有未完成的入库单
			StockInbound stockInboundSelect = new StockInbound();
			stockInboundSelect.setCurState(Constant.STOCK_STATE_WAIT);
			stockInboundSelect.setStockId(stockInfo.getStockId());
			List<StockInbound> stockInboundList = stockInboundMapper.selectStockInboundList(stockInboundSelect);
			if(stockInboundList!=null&&stockInboundList.size()!=0) {
				return 4;
			}
			//查询是否有未完成的补货单
			SupplyOrder supplyOrderSelect = new SupplyOrder();
			supplyOrderSelect.setWmId(stockInfo.getStockId());
			supplyOrderSelect.setCurState(Constant.SUPPLY_ORDER_CUR_STATE_WAIT);
			List<SupplyOrder> supplyOrderList = supplyOrderMapper.selectSupplyOrderList(supplyOrderSelect);
			if(supplyOrderList!=null&&supplyOrderList.size()!=0){
				return 5;
			}
		}
		try {
			stockInfoMapper.deleteStockInfoByIds(Convert.toStrArray(ids));			
			bussinessCacheService.initStockInfo();
			for (StockInfo stockInfo : stockInfoList) {
				stockWarehouseMapper.deleteStockWarehouseByStockId(stockInfo.getStockId());
				log.info("删除仓库:"+JSONObject.toJSONString(stockInfo));
			}
		}catch (Exception e) {
			log.error("删除仓库失败:"+DateUtils.getTime(),e);
			throw new RuntimeException();
		}
		
		return 1;
	}

}
