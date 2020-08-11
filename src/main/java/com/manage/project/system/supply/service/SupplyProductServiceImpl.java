package com.manage.project.system.supply.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.common.utils.DateUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.stock.domain.StockProduct;
import com.manage.project.system.stock.domain.StockWarehouse;
import com.manage.project.system.stock.mapper.StockProductMapper;
import com.manage.project.system.stock.mapper.StockWarehouseMapper;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.domain.SupplyProduct;
import com.manage.project.system.supply.domain.SupplyVproduct;
import com.manage.project.system.supply.mapper.SupplyOrderMapper;
import com.manage.project.system.supply.mapper.SupplyProductMapper;
import com.manage.project.system.supply.mapper.SupplyVproductMapper;
import com.manage.project.system.supply.vo.SupplyProductListVo;
import com.manage.project.system.supply.vo.SupplyProductUpdateVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.common.support.Convert;

/**
 * 补货商品记录 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class SupplyProductServiceImpl implements ISupplyProductService 
{
	@Autowired
	private SupplyProductMapper supplyProductMapper;
	@Autowired
	private SupplyOrderMapper supplyOrderMapper;
	@Autowired
	private SupplyVproductMapper supplyVproductMapper;
	@Autowired
	private StockProductMapper stockProductMapper;
	@Autowired
	private StockWarehouseMapper stockWarehouseMapper;

	/**
     * 查询补货商品记录信息
     * 
     * @param logid 补货商品记录ID
     * @return 补货商品记录信息
     */
    @Override
	public SupplyProduct selectSupplyProductById(String logid)
	{
	    return supplyProductMapper.selectSupplyProductById(logid);
	}
	
	/**
     * 查询补货商品记录列表
     * 
     * @param supplyProduct 补货商品记录信息
     * @return 补货商品记录集合
     */
	@Override
	public List<SupplyProduct> selectSupplyProductList(SupplyProduct supplyProduct)
	{
	    return supplyProductMapper.selectSupplyProductList(supplyProduct);
	}
	
    /**
     * 新增补货商品记录
     * 
     * @param supplyProduct 补货商品记录信息
     * @return 结果
     */
	@Override
	public int insertSupplyProduct(SupplyProduct supplyProduct)
	{
	    return supplyProductMapper.insertSupplyProduct(supplyProduct);
	}
	
	/**
     * 修改补货商品记录
     * 
     * @param supplyProduct 补货商品记录信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateSupplyProduct(SupplyProductUpdateVo supplyProductUpdateVo)
	{	
		SupplyOrder supplyOrder = supplyOrderMapper.selectBySOrderId(supplyProductUpdateVo.getsOrderId());
		if(supplyProductUpdateVo.getSupplyProductInfo()==null||supplyProductUpdateVo.getSupplyProductInfo().size()==0) {
			return 4;
		}
		int outNum=0;
		for(SupplyProductListVo supplyProduct : supplyProductUpdateVo.getSupplyProductInfo()){
			//判断出库数量是否大于系统库存
			StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(supplyOrder.getCorpId(), supplyProduct.getProductId());
			if(stockProduct==null||supplyProduct.getOutNum()>stockProduct.getCurNum()) {
				return 2;
			}
			//判断出库数量是否大于仓库库存
			StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(supplyProduct.getProductId(), supplyOrder.getWmId(), supplyOrder.getCorpId());
			if(stockWarehouse==null||supplyProduct.getOutNum()>stockWarehouse.getCurNum()) {
				return 3;
			}
			outNum+=supplyProduct.getOutNum();
		}
		if(outNum<=0) {
			return 4;
		}
		supplyOrder.setStockState(Constant.supplyConfigCur_alOut);
		supplyOrder.setSStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
		supplyOrderMapper.updateSupplyOrder(supplyOrder);
		Map<String,String> supplyProductMap=new HashMap<>();
		for(SupplyProductListVo supplyProduct : supplyProductUpdateVo.getSupplyProductInfo()){
			supplyProductMap.put(supplyProduct.getProductId(), supplyProduct.getProductId());
			SupplyVproduct supplyVproduct = new SupplyVproduct();
			supplyVproduct.setSOrderId(supplyProductUpdateVo.getsOrderId());
			supplyVproduct.setProductId(supplyProduct.getProductId());
			List<SupplyVproduct> supplyVproductlist = supplyVproductMapper.findsupplyVproductList(supplyVproduct);
			for(SupplyVproduct supplyVproductR : supplyVproductlist){
				supplyVproductR.setBuyPrice(supplyProduct.getBuyPrice());
				supplyVproductMapper.updateSupplyVproduct(supplyVproductR);
			}
			SupplyProduct supplyProductP = new SupplyProduct();
			supplyProductP.setSOrderId(supplyProductUpdateVo.getsOrderId());
			supplyProductP.setProductId(supplyProduct.getProductId());
			SupplyProduct supplyProductR = supplyProductMapper.selectSupplyProductByPId(supplyProductP);
			if(supplyProductR.getOutNum()<=0 || supplyProductR.getOutNum()==null){
				supplyProductR.setOutNum(0);
			}
			supplyProductR.setStockOutNum(supplyProduct.getOutNum());
			supplyProductR.setBuyPrice(supplyProduct.getBuyPrice());	
		    this.supplyProductMapper.updateSupplyProduct(supplyProductR);
		    //修改系统库存和仓库库存
		    StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(supplyOrder.getCorpId(), supplyProduct.getProductId());
		    stockProduct.setCurNum(stockProduct.getCurNum()-supplyProduct.getOutNum());
		    stockProductMapper.updateStockProduct(stockProduct);
		    StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(supplyProduct.getProductId(), supplyOrder.getWmId(), supplyOrder.getCorpId());
		    stockWarehouse.setCurNum(stockWarehouse.getCurNum()-supplyProduct.getOutNum());
		    stockWarehouseMapper.updateStockWarehouse(stockWarehouse);
		}
		//throw new RuntimeException();
		return 1;
	}

	/**
     * 删除补货商品记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyProductByIds(String ids)
	{
		return supplyProductMapper.deleteSupplyProductByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<SupplyProduct> selectListBySOrderId(String sOrderId) {
		// TODO Auto-generated method stub
		return supplyProductMapper.selectListBySOrderId(sOrderId);
	}

	/**
	 * 查询商品历史采购价格
	 * 
	 * @param supplyProduct
	 * @return
	 */
	@Override
	public List<Float> selectSupplyProductPriceList(SupplyProduct supplyProduct) {
		return supplyProductMapper.selectSupplyProductPriceList(supplyProduct);
	}

	/**
	 * 查询补货商品(导出)
	 * 
	 * @param supplyOrder
	 * @return
	 */
	@Override
	public List<SupplyProduct> selectSupplyProductListForExport(SupplyOrder supplyOrder) {
		return supplyProductMapper.selectSupplyProductListForExport(supplyOrder);
	}

	/**
	 * 重新入库
	 * 
	 * @param supplyOrder
	 * @return
	 */
	@Override
	public int repeatInbound(SupplyProductUpdateVo supplyProductUpdateVo) throws RuntimeException {
		//检查补货记录
		SupplyOrder supplyOrder = supplyOrderMapper.selectBySOrderId(supplyProductUpdateVo.getsOrderId());
		if(supplyOrder == null) {
			return 2;
		}
		//检查提交的重新入库记录
		if(supplyProductUpdateVo.getSupplyProductInfo()==null||supplyProductUpdateVo.getSupplyProductInfo().size()==0) {
			return 3;
		}
		
		//判断出库数量是否等于（实际补货总数+剩余正常总数+剩余损坏总数+丢失总数）
		int supplynNumSum = 0;
		int supplydNumSum = 0;
		int supplylNumSum = 0;
		for(SupplyProductListVo supplyProductListVo : supplyProductUpdateVo.getSupplyProductInfo()){
			if(supplyProductListVo.getOutNum()!=(supplyProductListVo.getSupplyrNum()+supplyProductListVo.getSupplynNum()+supplyProductListVo.getSupplydNum()+supplyProductListVo.getSupplylNum())) {
				return 4;
			}
			supplynNumSum += supplyProductListVo.getSupplynNum();
			supplydNumSum += supplyProductListVo.getSupplydNum();
			supplylNumSum += supplyProductListVo.getSupplylNum();
		}
		//更新补货商品记录
		SupplyProduct supplyProduct = null;
		for(SupplyProductListVo supplyProductListVo : supplyProductUpdateVo.getSupplyProductInfo()){
			supplyProduct = new SupplyProduct();
			supplyProduct.setSOrderId(supplyOrder.getsOrderId());
			supplyProduct.setProductId(supplyProductListVo.getProductId());
			supplyProduct.setSupplynNum(supplyProductListVo.getSupplynNum());
			supplyProduct.setSupplydNum(supplyProductListVo.getSupplydNum());
			supplyProduct.setSupplylNum(supplyProductListVo.getSupplylNum());
			supplyProductMapper.updateSupplyProduct(supplyProduct);
		}
		//更新补货记录
		supplyOrder.setSupplynNum(supplynNumSum);
		supplyOrder.setSupplydNum(supplydNumSum);
		supplyOrder.setSupplylNum(supplylNumSum);
		supplyOrder.setSupplydType("1");
		supplyOrderMapper.updateSupplyOrder(supplyOrder);
		//更新库存
		for(SupplyProductListVo supplyProductListVo : supplyProductUpdateVo.getSupplyProductInfo()){
			if(supplyProductListVo.getSupplynNum() == 0)
				continue;
			StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(supplyOrder.getCorpId(),supplyProductListVo.getProductId());
			if(stockProduct != null) {
				stockProduct.setCurNum(stockProduct.getCurNum()+supplyProductListVo.getSupplynNum());
				stockProduct.setTotalNum(stockProduct.getTotalNum()+supplyProductListVo.getSupplynNum());
				stockProductMapper.updateStockProduct(stockProduct);
			}
			StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(supplyProductListVo.getProductId(),supplyOrder.getWmId(),supplyOrder.getCorpId());
			if(stockWarehouse != null) {
				stockWarehouse.setCurNum(stockWarehouse.getCurNum()+supplyProductListVo.getSupplynNum());
				stockWarehouse.setTotalNum(stockWarehouse.getTotalNum()+supplyProductListVo.getSupplynNum());
				stockWarehouseMapper.updateStockWarehouse(stockWarehouse);
			}
		}
		//throw new RuntimeException();
		return 1;
	}

	/**
	 * 追加出库
	 * 
	 * @param supplyProductUpdateVo
	 * @return
	 */
	@Override
	@Transactional
	public int extraOut(SupplyProductUpdateVo supplyProductUpdateVo) {
		SupplyOrder supplyOrder = supplyOrderMapper.selectBySOrderId(supplyProductUpdateVo.getsOrderId());
		if(supplyProductUpdateVo.getSupplyProductInfo()==null||supplyProductUpdateVo.getSupplyProductInfo().size()==0) {
			return 4;
		}
		int outNum=0;
		for(SupplyProductListVo supplyProduct : supplyProductUpdateVo.getSupplyProductInfo()){
			//判断出库数量是否大于系统库存
			StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(supplyOrder.getCorpId(), supplyProduct.getProductId());
			if(stockProduct==null||supplyProduct.getpNum()>stockProduct.getCurNum()) {
				return 2;
			}
			//判断出库数量是否大于仓库库存
			StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(supplyProduct.getProductId(), supplyOrder.getWmId(), supplyOrder.getCorpId());
			if(stockWarehouse==null||supplyProduct.getpNum()>stockWarehouse.getCurNum()) {
				return 3;
			}
			outNum+=supplyProduct.getpNum();
		}
		if(outNum<=0) {
			return 4;
		}
		supplyOrder.setStockState(Constant.supplyConfigCur_alOut);
		supplyOrder.setSStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
		supplyOrderMapper.updateSupplyOrder(supplyOrder);
		Map<String,String> supplyProductMap=new HashMap<>();
		for(SupplyProductListVo supplyProduct : supplyProductUpdateVo.getSupplyProductInfo()){
			supplyProductMap.put(supplyProduct.getProductId(), supplyProduct.getProductId());
			SupplyVproduct supplyVproduct = new SupplyVproduct();
			supplyVproduct.setSOrderId(supplyProductUpdateVo.getsOrderId());
			supplyVproduct.setProductId(supplyProduct.getProductId());
			List<SupplyVproduct> supplyVproductlist = supplyVproductMapper.findsupplyVproductList(supplyVproduct);
			for(SupplyVproduct supplyVproductR : supplyVproductlist){
				supplyVproductR.setBuyPrice(supplyProduct.getBuyPrice());
				supplyVproductMapper.updateSupplyVproduct(supplyVproductR);
			}
			SupplyProduct supplyProductP = new SupplyProduct();
			supplyProductP.setSOrderId(supplyProductUpdateVo.getsOrderId());
			supplyProductP.setProductId(supplyProduct.getProductId());
			SupplyProduct supplyProductR = supplyProductMapper.selectSupplyProductByPId(supplyProductP);
			if(supplyProductR.getOutNum()<=0 || supplyProductR.getOutNum()==null){
				supplyProductR.setOutNum(0);
			}
			int stockOuntNum=0;
			if(supplyProductR.getStockOutNum()!=null) {
				stockOuntNum=supplyProductR.getStockOutNum();
			}
			supplyProductR.setStockOutNum(supplyProduct.getpNum()+stockOuntNum);
			//supplyProductR.setBuyPrice(supplyProduct.getBuyPrice());	
		    this.supplyProductMapper.updateSupplyProduct(supplyProductR);
		    //修改系统库存和仓库库存
		    StockProduct stockProduct = stockProductMapper.selectStockProductByCorpIdAndProdcutId(supplyOrder.getCorpId(), supplyProduct.getProductId());
		    stockProduct.setCurNum(stockProduct.getCurNum()-supplyProduct.getpNum());
		    stockProductMapper.updateStockProduct(stockProduct);
		    StockWarehouse stockWarehouse = stockWarehouseMapper.selectStockWarehouseByProductAndStock(supplyProduct.getProductId(), supplyOrder.getWmId(), supplyOrder.getCorpId());
		    stockWarehouse.setCurNum(stockWarehouse.getCurNum()-supplyProduct.getpNum());
		    stockWarehouseMapper.updateStockWarehouse(stockWarehouse);
		}
		//throw new RuntimeException();
		return 1;
	}
	
	
	
}
