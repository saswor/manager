package com.manage.project.system.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.project.system.stock.mapper.StockInboundMapper;
import com.manage.project.system.stock.mapper.StockPinboundMapper;
import com.manage.project.system.stock.mapper.StockProductMapper;
import com.manage.project.system.stock.mapper.StockPurchaseMapper;
import com.manage.project.system.stock.mapper.StockWarehouseMapper;
import com.manage.project.system.stock.domain.StockInbound;
import com.manage.project.system.stock.domain.StockPinbound;
import com.manage.project.system.stock.domain.StockProduct;
import com.manage.project.system.stock.domain.StockPurchase;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.service.IStockInboundService;
import com.manage.project.system.stock.vo.StockInboundParamVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 仓库入库记录 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class StockInboundServiceImpl implements IStockInboundService 
{
	@Autowired
	private StockInboundMapper stockInboundMapper;
	@Autowired
	private StockPinboundMapper stockPinboundMapper;
	@Autowired
	private StockProductMapper stockProductMapper;
	@Autowired
	private StockWarehouseMapper stockWarehouseMapper;
	@Autowired
	private StockPurchaseMapper stockPurchaseMapper;
	/**
     * 查询仓库入库记录信息
     * 
     * @param logid 仓库入库记录ID
     * @return 仓库入库记录信息
     */
    @Override
	public StockInbound selectStockInboundById(String logid)
	{
	    return stockInboundMapper.selectStockInboundById(logid);
	}
	
	/**
     * 查询仓库入库记录列表
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 仓库入库记录集合
     */
	@Override
	public List<StockInbound> selectStockInboundList(StockInbound stockInbound)
	{
	    return stockInboundMapper.selectStockInboundList(stockInbound);
	}
	
    /**
     * 新增仓库入库记录
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 结果
     */
	@Override
	public int insertStockInbound(StockInbound stockInbound)
	{
	    return stockInboundMapper.insertStockInbound(stockInbound);
	}
	
	/**
     * 修改仓库入库记录
     * 
     * @param stockInbound 仓库入库记录信息
     * @return 结果
     */
	@Override
	public int updateStockInbound(StockInbound stockInbound)
	{
	    return stockInboundMapper.updateStockInbound(stockInbound);
	}

	/**
     * 删除仓库入库记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteStockInboundByIds(String ids)
	{
		return stockInboundMapper.deleteStockInboundByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 查询仓库入库记录列表
     * 
     * @return 仓库入库记录集合
     */
	@Override
	public List<StockInbound> selectStockInbound(StockInboundParamVo stockInboundParamVo) {
		return stockInboundMapper.selectStockInbound(stockInboundParamVo);
	}

	/**
	 * 提交入库
	 * @param stockPinbounds	商品入库信息
	 * @return
	 * @throws RuntimeException
	 */
	@Override
	@Transactional
	public int submitInbound(List<StockPinbound> stockPinbounds) throws RuntimeException {
		int r = 0;
		int pnum=0,tnum=0;
		float totalPrice=0F;
		String stockId = stockPinbounds.get(0).getStockId();
		for (StockPinbound stockPinbound : stockPinbounds) {
			stockPinbound.setTotalPrice(stockPinbound.getBuyPrice()*stockPinbound.getPnum());
			r = r + stockPinboundMapper.updateStockPinbound(stockPinbound);
			if(stockPinbound.getPnum()>0) {
				tnum++;
				pnum+=stockPinbound.getPnum();
				totalPrice+=stockPinbound.getBuyPrice()*stockPinbound.getPnum();
				stockPinbound.setTotalPrice(totalPrice);
				//修改系统仓库库存
				StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(stockPinbound.getCorpId(), stockPinbound.getProductId());
				if(stockProduct!=null) {
					Integer curNum = stockProduct.getCurNum();
					Integer totalNum = stockProduct.getTotalNum();
					stockProduct.setCurNum(curNum+stockPinbound.getPnum());
					stockProduct.setTotalNum(totalNum+stockPinbound.getPnum());
					r+=stockProductMapper.updateStockProduct(stockProduct);
				}else {
					throw new RuntimeException("系统库存信息不存在");
				}
				//修改对应仓库的库存信息
				StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(stockPinbound.getProductId(),stockPinbound.getStockId(),stockPinbound.getCorpId());
				if(stockWarehouse!=null) {
					Integer curNum = stockWarehouse.getCurNum();
					Integer totalNum = stockWarehouse.getTotalNum();
					stockWarehouse.setCurNum(curNum+stockPinbound.getPnum());
					stockWarehouse.setTotalNum(totalNum+stockPinbound.getPnum());
					r+=stockWarehouseMapper.updateStockWarehouse(stockWarehouse);
				}else {
					throw new RuntimeException("仓库库存信息不存在");
				}
			}
		}
		StockInbound stockInbound = stockInboundMapper.selectStockInboundByWInboundId(stockPinbounds.get(0).getWinboundId());
		stockInbound.setPnum(pnum);
		stockInbound.setTnum(tnum);
		stockInbound.setTotalPrice(totalPrice);
		stockInbound.setCurState("2");
		stockInbound.setStateTime(DateUtils.getTime());
		stockInbound.setInboundId(ShiroUtils.getLoginName());
		r+=stockInboundMapper.updateStockInbound(stockInbound);
		//修改采购单的状态
		StockPurchase stockPurchase = stockPurchaseMapper.selectStockPurchaseByWpurchaseId(stockInbound.getWpurchaseId());
		stockPurchase.setStockState("2");
		stockPurchaseMapper.updateStockPurchase(stockPurchase);
		return r;
	}

	/**
	 * 根据入库编号查询入库记录
	 * 
	 * @param winboundId 入库编号
	 * @return 入库记录
	 */
	@Override
	public StockInbound selectStockInboundByWInboundId(String winboundId) {
		return stockInboundMapper.selectStockInboundByWInboundId(winboundId);
	}
	
	/**
	 * 提交入库冲正
	 * @param stockPinbounds	商品入库信息
	 * @return
	 * @throws RuntimeException
	 */
	@Override
	@Transactional
	public int releaseInbound(List<StockPinbound> stockPinbounds) {
		//获取冲正对冲的入库记录主单
		StockInbound stockInbound = stockInboundMapper.selectStockInboundByWInboundId(stockPinbounds.get(0).getWinboundId());
		if(stockInbound == null) {
			return 2;
		}
		//如果主单是冲正单,直接返回
		if("2".equals(stockInbound.getInboundType())) {
			return 6;
		}
		//查询该入库单是否已经存在冲正单
		StockInbound stockInboundSelect = new StockInbound();
		stockInboundSelect.setWpurchaseId(stockInbound.getWpurchaseId());
		stockInboundSelect.setInboundType("2");
		List<StockInbound> stockInboundList = stockInboundMapper.selectStockInboundList(stockInboundSelect);
		if(stockInboundList!=null&&!stockInboundList.isEmpty()) {
			return 7;
		}
			
		String corpId = stockInbound.getCorpId();
		String stockId = stockInbound.getStockId();
		//判断冲正数是否大于入库数
		//判断冲正数是否大于库存数
		List<StockWarehouse> stockWarehouseList = new ArrayList<>();
		List<StockProduct> stockProductList = new ArrayList<>();
		List<StockPinbound> stockReleasePInboundList = new ArrayList<>();
		for(StockPinbound var : stockPinbounds) {
			if(var.getReInboundNum() == 0)
				continue;
			stockReleasePInboundList.add(var);
			StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(var.getProductId(), stockId, corpId);
			StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(corpId, var.getProductId());
			if(stockWarehouse == null || stockProduct == null)
				return 3;
			if(stockWarehouse.getCurNum()<var.getReInboundNum() || stockProduct.getCurNum()<var.getReInboundNum())
				return 4;
			stockWarehouse.setCurNum(stockWarehouse.getCurNum()-var.getReInboundNum());
			stockWarehouse.setTotalNum(stockWarehouse.getTotalNum()-var.getReInboundNum());
			stockWarehouseList.add(stockWarehouse);
			stockProduct.setCurNum(stockProduct.getCurNum()-var.getReInboundNum());
			stockProduct.setTotalNum(stockProduct.getTotalNum()-var.getReInboundNum());
			stockProductList.add(stockProduct);
		}
		if(stockReleasePInboundList.size()==0)
			return 5;
		
		//更新仓库库存
		for(StockWarehouse var : stockWarehouseList) {
			stockWarehouseMapper.updateStockWarehouse(var);
		}
		//更新系统商品库存
		for(StockProduct var : stockProductList) {
			stockProductMapper.updateStockProduct(var);
		}
		
		//新增冲正商品记录
		String wInboundId = SystemUtil.getSeqId(corpId, "as_stock_inbound");
		int sumPnun = 0;
		float sumTotalPrice=0F;
		String curTime = DateUtils.getTime();
		for(StockPinbound var : stockReleasePInboundList) {
			var.setLogid(UUID.randomUUID().toString());
			var.setWpInboundId(SystemUtil.getSeqId(corpId, "as_stock_pinbound"));
			var.setWinboundId(wInboundId);
			var.setInboundType("2");
			var.setSupplierId("");
			var.setPnum(var.getReInboundNum());
			var.setTotalPrice(var.getReInboundNum()*var.getBuyPrice());
			var.setCreateTime(curTime);
			stockPinboundMapper.insertStockPinbound(var);
			sumPnun += var.getReInboundNum();
			sumTotalPrice+=var.getTotalPrice();
		}
		//插入冲正主单
		StockInbound stockReleaseInbound = new StockInbound();
		stockReleaseInbound.setWinboundId(wInboundId);
		stockReleaseInbound.setLogid(UUID.randomUUID().toString());
		stockReleaseInbound.setWpurchaseId(stockInbound.getWpurchaseId());//记录原采购记录编号
		stockReleaseInbound.setStockId(stockId);
		stockReleaseInbound.setStokcName(stockInbound.getStokcName());
		stockReleaseInbound.setTnum(stockReleasePInboundList.size());
		stockReleaseInbound.setPnum(sumPnun);
		stockReleaseInbound.setTotalPrice(sumTotalPrice);
		stockReleaseInbound.setCurState("2");
		stockReleaseInbound.setStateTime(curTime);
		stockReleaseInbound.setInboundId(ShiroUtils.getLoginName());
		stockReleaseInbound.setInboundType("2");
		stockReleaseInbound.setCorpId(corpId);
		stockReleaseInbound.setCreateTime(curTime);
		stockInboundMapper.insertStockInbound(stockReleaseInbound);
		return 1;
	}
}
