package com.manage.project.system.supply.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.domain.SupplyProduct;
import com.manage.project.system.supply.domain.SupplyVorder;
import com.manage.project.system.supply.domain.SupplyVproduct;
import com.manage.project.system.supply.mapper.SupplyConfigMapper;
import com.manage.project.system.supply.mapper.SupplyOrderMapper;
import com.manage.project.system.supply.mapper.SupplyProductMapper;
import com.manage.project.system.supply.mapper.SupplyVendingMapper;
import com.manage.project.system.supply.mapper.SupplyVorderMapper;
import com.manage.project.system.supply.mapper.SupplyVproductMapper;
import com.manage.project.system.supply.service.ISupplyOrderService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingMapper;

/**
 * 生成补货单处理程序
 * 
 * @author zhangjiawei
 *
 */
@Component
@Scope("prototype") 
public class SupplyOrderHandler{
	
	
	
	@Autowired
	private SupplyOrderMapper supplyOrderMapper;
    @Autowired
	private SupplyVproductMapper supplyVproductMapper;
    @Autowired
	private SupplyProductMapper supplyProductMapper;
    @Autowired
	private VendingMapper vendingMapper;
    @Autowired
    private VendingLanepMapper vendingLanepMapper;
    @Autowired
    private SupplyVorderMapper supplyVorderMapper;
    
	private Lock lock = new ReentrantLock();
	
	@Autowired
	private ISupplyOrderService supplyOrderService;

	@Transactional
	public int createSupplyOrderBySupplyConfig(SupplyConfig supplyConfig,String site,boolean updateFlag) {
		List<SupplyVorder> supplyVorderList = new ArrayList<SupplyVorder>();
		List<SupplyVorder> supplyVorderUpdateList = new ArrayList<SupplyVorder>();
		List<SupplyProduct> supplyProductList = new ArrayList<SupplyProduct>();
		List<SupplyProduct> supplyProductUpdateList = new ArrayList<SupplyProduct>();
		List<SupplyVproduct> supplyVproductList = new ArrayList<SupplyVproduct>();
		List<SupplyVproduct> supplyVproductUpdateList = new ArrayList<SupplyVproduct>();

		//查询线路下已认证的站点信息
		Vending vendingSelect = new Vending();
		vendingSelect.setLineId(supplyConfig.getLineId());
		vendingSelect.setCurState("1");
		List<Vending> vendingList=vendingMapper.selectVendingList(vendingSelect);
		//检查线路下是否有售货机存在
		if(vendingList==null||vendingList.isEmpty()) {
			return 4;
		}
		String[] siteIds = new String[vendingList.size()];
		int index=0;
		Map<String,Vending> vendingMap=new HashMap<>();
		for(Vending vending:vendingList){
			//检查售货机的货道配置
			VendingLanep vendingLanepSelect = new VendingLanep();
			vendingLanepSelect.setSiteId(vending.getSiteId());
			vendingLanepSelect.setLaneSate(Constant.VENDING_LANESTATE_NORMAL);
			List<VendingLanep> vendingLanepList = vendingLanepMapper.selectVendingLanepList(vendingLanepSelect);
			boolean vendingProductFullFlag=true;
			for (VendingLanep vendingLanep : vendingLanepList) {				
				if(vendingLanep!=null&&(StringUtils.isEmpty(vendingLanep.getProductId())||vendingLanep.getCapacity()==null||vendingLanep.getCapacity()<1
						||vendingLanep.getWarnCap()==null||vendingLanep.getWarnCap()<1)) {
					if(site==null) {
						vendingProductFullFlag=false;
					}else {
						return 5;
					}	
				}
			}
			if(vendingProductFullFlag) {
				siteIds[index]=vending.getSiteId();
				vendingMap.put(vending.getSiteId(),vending);
				index++;
			}
			
		}
		//查询是否有未完成完成的补货订单
		SupplyOrder supplyOrderSelect=new SupplyOrder();
		supplyOrderSelect.setCurState("2");
		supplyOrderSelect.setSupplyId(supplyConfig.getSupplyId());
		SupplyOrder supplyOrderNotFinish=supplyOrderMapper.selectNoFinshOrderBySupplyId(supplyOrderSelect);
		lock.lock();
		try {
			//新生成补货订单
	        if(supplyOrderNotFinish==null){  
	    		//创建补货订单
	            SupplyOrder supplyOrder=new SupplyOrder();
	            supplyOrder.setCurState(Constant.supplyConfigCur_wait);
	            supplyOrder.setSupplyId(supplyConfig.getSupplyId());
	            supplyOrder.setRefreshDate(DateUtils.dateTimeNow("YYYY-MM-dd"));
	            supplyOrder.setCorpId(supplyConfig.getCorpId());
	            supplyOrder.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	            supplyOrder.setLogid(UUID.randomUUID().toString());
	            supplyOrder.setFinshState(Constant.supplyOrderCur_noFinsh);
	            supplyOrder.setLineId(supplyConfig.getLineId());
	            supplyOrder.setName(supplyConfig.getName());
	            supplyOrder.setSOrderId(SystemUtil.getSeqIdLong(supplyConfig.getCorpId(),Constant.supplyOrder_table));
	            supplyOrder.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	            supplyOrder.setStockState(Constant.supplyConfigCheck_noCheck);
	            supplyOrder.setSupplierId(supplyConfig.getSupplierId());

	            supplyOrder.setType(supplyConfig.getType());//补货类型
	            supplyOrder.setWmId(supplyConfig.getWmId());//仓库编号
	            supplyOrder.setSStateTime("");
	            supplyOrder.setSupplyFTime("");
	            supplyOrder.setCheckId("");
	            
	            supplyOrder.setOutNum(0);
	            supplyOrder.setSupplyrNum(0);
	            supplyOrder.setSupplydNum(0);
	            supplyOrder.setSupplylNum(0);
	            supplyOrder.setSupplynNum(0);
	            supplyOrder.setSupplydType("1");

	            //创建商品补货清单
	            Map<String,Integer> supplyProductMap=new HashMap<>();
	            Map<String,Integer> supplyProductCurMap=new HashMap<>();
	            //存放补货商品信息主键
	            Map<String, String> productMap = new HashMap<String,String>();
	            for(String siteId:siteIds){
	            	if(!StringUtils.isEmpty(siteId)&&(site==null||siteId.equals(site))) {
	            		//获取站点补货商品信息
	                    List<VendingLanep> vendingLanepList=null;
	                    //阈值补货，按商品编号分组
	                    if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
	                        vendingLanepList=vendingLanepMapper.selectSupplyVPThreshold(siteId);
	                    }else{//全补齐，按商品编号分组
	                        vendingLanepList=vendingLanepMapper.selectSupplyVProduct(siteId);
	                    }
	                    if(vendingLanepList==null||vendingLanepList.isEmpty()) {
	                    	continue;
	                    }
	                    //补货站点记录
	                    SupplyVorder supplyVorder = new SupplyVorder();
	                    supplyVorder.setLogid(UUID.randomUUID().toString());
	                    supplyVorder.setVorderId(SystemUtil.getSeqIdLong(supplyConfig.getCorpId(), "as_supply_vorder"));
	                    supplyVorder.setSorderId(supplyOrder.getsOrderId());
	                    supplyVorder.setSupplierId(supplyOrder.getSupplierId());
	                    supplyVorder.setCorpId(supplyConfig.getCorpId());
	                    supplyVorder.setSupplyId(supplyOrder.getSupplyId());
	                    supplyVorder.setCurState(Constant.SUPPLY_ORDER_CUR_STATE_WAIT);
	                    supplyVorder.setStateTime(DateUtils.getTime());
	                    supplyVorder.setSiteId(siteId);
	                    supplyVorder.setLineId(supplyConfig.getLineId());
	                    supplyVorder.setCreateTime(DateUtils.getTime());               
	                    //站点需补货数量
	                    int vendingSupplyNum=0;
	                    for(VendingLanep vendingLanep:vendingLanepList){
	                        SupplyVproduct supplyVproduct=new SupplyVproduct();
	                        supplyVproduct.setSOrderId(supplyOrder.getSOrderId());
	                        supplyVproduct.setvOrderId(supplyVorder.getVorderId());
	                        if(productMap.get(vendingLanep.getProductId())==null) {
	                        	productMap.put(vendingLanep.getProductId(), SystemUtil.getSeqIdLong(supplyConfig.getCorpId(),Constant.supplyProduct_table));
	                        }
	                        supplyVproduct.setsProductId(productMap.get(vendingLanep.getProductId()));
	                        supplyVproduct.setBuyPrice(0F);
	                        supplyVproduct.setCorpId(supplyConfig.getCorpId());
	                        supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                        supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
	                        supplyVproduct.setDistrictId(vendingMap.get(vendingLanep.getSiteId()).getDistrictId());
	                        supplyVproduct.setPointId(vendingMap.get(vendingLanep.getSiteId()).getPointId());
	                        supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
	                        supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
	                        supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
	                        supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
	                        supplyVproduct.setLineId(supplyConfig.getLineId());
	                        supplyVproduct.setLogid(UUID.randomUUID().toString());
	                        supplyVproduct.setProductId(vendingLanep.getProductId());
	                        supplyVproduct.setSupplyNum(vendingLanep.getCapacity()-vendingLanep.getCurCap());
	                        supplyVproduct.setRSupplyNum(0);
	                        supplyVproduct.setSaleNum(0);
	                        supplyVproduct.setSiteId(vendingLanep.getSiteId());
	                        supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                        supplyVproduct.setSVendingId(SystemUtil.getSeqIdLong(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
	                        supplyVproduct.setInvalidTime("");
	                        supplyVproductList.add(supplyVproduct);
	                        vendingSupplyNum+=supplyVproduct.getSupplyNum();                    
	                        if(supplyProductMap.get(vendingLanep.getProductId())==null){
	                            supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getCapacity()-vendingLanep.getCurCap());
	                            supplyProductCurMap.put(vendingLanep.getProductId(),vendingLanep.getCurCap());
	                        }else{
	                            supplyProductMap.put(vendingLanep.getProductId(),supplyProductMap.get(vendingLanep.getProductId())+(vendingLanep.getCapacity()-vendingLanep.getCurCap()));
	                            supplyProductCurMap.put(vendingLanep.getProductId(),supplyProductCurMap.get(vendingLanep.getProductId())+vendingLanep.getCurCap());
	                        }
	                    }
	                    //设置站点补货数量
	                    supplyVorder.setSupplyNum(vendingSupplyNum);             
	                    supplyVorderList.add(supplyVorder);
	                }                    
	            } 
	            //创建站点货道补货清单
	            int supplyNum=0;//补货总数
	            for(String key:supplyProductMap.keySet()){
	                SupplyProduct supplyProduct=new SupplyProduct();
	                supplyProduct.setSOrderId(supplyOrder.getSOrderId());
	                supplyProduct.setBuyPrice(0f);
	                supplyProduct.setCorpId(supplyConfig.getCorpId());
	                supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
	                supplyProduct.setLogid(UUID.randomUUID().toString());
	                supplyProduct.setOutNum(0);
	                supplyProduct.setProductId(key);
	                supplyProduct.setSProductId(productMap.get(key));
	                supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                supplyProduct.setSupplyId(supplyConfig.getSupplyId());
	                supplyProduct.setSupplyNum(supplyProductMap.get(key));
	                supplyProduct.setStockOutNum(0);
	                
	                supplyProduct.setSupplyrNum(0);
	                supplyProduct.setSupplynNum(0);
	                supplyProduct.setSupplydNum(0);
	                supplyProduct.setSupplylNum(0);
	                
	                supplyProductList.add(supplyProduct);
	                supplyNum+=supplyProductMap.get(key);
	            }
	            //补货当前数
	            int curNum=0;
	            for(String key:supplyProductCurMap.keySet()){
	                curNum+=supplyProductCurMap.get(key);
	            }
	            supplyOrder.setCurPNum(curNum);//当前商品数
	            supplyOrder.setSupplyNum(supplyNum);//补货数量
	            supplyOrder.setNum(supplyConfig.getWaitSVNum());//当前补货站点数
	            supplyOrder.setMaxPNum(supplyConfig.getMaxPNum());//商品存放最大库存数
	            if(supplyVproductList.isEmpty()) {
	            	lock.unlock();
                	return 8;
                }else {
                	supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);
                }
	            supplyOrderMapper.insertSupplyOrder(supplyOrder);
	            if(!supplyVorderList.isEmpty()) {
	            	supplyVorderMapper.insertSupplyVorderBatch(supplyVorderList);
	            }
	            if(!supplyProductList.isEmpty()) {
	            	supplyProductMapper.insertSupplyProductBatch(supplyProductList);
	            }
	    	}else{
	    		//如果需要修改原有补货记录
	    		if(updateFlag) {
	    			//创建商品补货清单
    	            Map<String,Integer> supplyProductMap=new HashMap<>();
    	            Map<String,Integer> supplyProductCurMap=new HashMap<>();
    	            //存放补货商品信息主键
    	            Map<String, String> productMap = new HashMap<String,String>();
    	            Map<String, SupplyProduct> productUpdateMap = new HashMap<String,SupplyProduct>();
    	            //存放补货站点主键
    	            Map<String, String> siteMap = new HashMap<String,String>();
    	            Map<String, String> siteUpdateUpdateMap = new HashMap<String,String>();
    	            //存放补货站点货道商品主键
    	            Map<String, String> laneProductMap = new HashMap<String,String>();
    	            Map<String, String> laneProductUpdateMap = new HashMap<String,String>();
	    			for(String siteId:siteIds){
	    				if(!StringUtils.isEmpty(siteId)&&(site==null||siteId.equals(site))) {
		            		//获取站点补货商品信息
		                    List<VendingLanep> vendingLanepList=null;
		                    //阈值补货，按商品编号分组
		                    if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
		                        vendingLanepList=vendingLanepMapper.selectSupplyVPThreshold(siteId);
		                    }else{//全补齐，按商品编号分组
		                        vendingLanepList=vendingLanepMapper.selectSupplyVProduct(siteId);
		                    }
		                    if(vendingLanepList==null||vendingLanepList.isEmpty()) {
		                    	continue;
		                    }
		                    SupplyVorder supplyVorder = null;
		                    //查询当前站点是否有补货记录
		                    SupplyVorder supplyVorderSelect = new SupplyVorder();
		    	            supplyVorderSelect.setSorderId(supplyOrderNotFinish.getsOrderId());
		    	            supplyVorderSelect.setSiteId(siteId);
		    	            supplyVorderSelect.setCurState("1");
		    	            List<SupplyVorder> selectSupplyVorderList = supplyVorderMapper.selectSupplyVorderList(supplyVorderSelect);		    	            
		    	            if(selectSupplyVorderList==null||selectSupplyVorderList.isEmpty()) {
		    	            	//补货站点记录
			                    supplyVorder = new SupplyVorder();
			                    supplyVorder.setLogid(UUID.randomUUID().toString());
			                    supplyVorder.setVorderId(SystemUtil.getSeqIdLong(supplyConfig.getCorpId(), "as_supply_vorder"));
			                    supplyVorder.setSorderId(supplyOrderNotFinish.getsOrderId());
			                    supplyVorder.setSupplierId(supplyOrderNotFinish.getSupplierId());
			                    supplyVorder.setCorpId(supplyConfig.getCorpId());
			                    supplyVorder.setSupplyId(supplyOrderNotFinish.getSupplyId());
			                    supplyVorder.setCurState(Constant.SUPPLY_ORDER_CUR_STATE_WAIT);
			                    supplyVorder.setStateTime(DateUtils.getTime());
			                    supplyVorder.setSiteId(siteId);
			                    supplyVorder.setLineId(supplyConfig.getLineId());
			                    supplyVorder.setCreateTime(DateUtils.getTime());  
			                    supplyVorderList.add(supplyVorder);
		    	            }else {
		    	            	supplyVorder=selectSupplyVorderList.get(0);
		    	            	supplyVorderUpdateList.add(supplyVorder);
		    	            }            
		                    //站点需补货数量
		                    int vendingSupplyNum=0;
		                    for(VendingLanep vendingLanep:vendingLanepList){
		                    	SupplyVproduct supplyVproduct=null;
		                    	//查询记录是否已经存在
		                    	SupplyVproduct supplyVproductSelect=new SupplyVproduct();
		                    	supplyVproductSelect.setSOrderId(supplyOrderNotFinish.getsOrderId());
		                    	supplyVproductSelect.setLaneSId(vendingLanep.getLaneSId());
		                    	supplyVproductSelect.setLaneEId(vendingLanep.getLaneEId());
		                    	supplyVproductSelect.setSiteId(siteId);
		                    	supplyVproductSelect.setCurState("1");
		                    	List<SupplyVproduct> selectSupplyVproductList = supplyVproductMapper.selectSupplyVproductList(supplyVproductSelect);
		                    	if(selectSupplyVproductList==null||selectSupplyVproductList.isEmpty()) {
		                    		supplyVproduct=new SupplyVproduct();
			                        supplyVproduct.setSOrderId(supplyOrderNotFinish.getSOrderId());
			                        supplyVproduct.setvOrderId(supplyVorder.getVorderId());
			                        supplyVproduct.setsProductId(productMap.get(vendingLanep.getProductId()));
			                        supplyVproduct.setBuyPrice(0F);
			                        supplyVproduct.setCorpId(supplyConfig.getCorpId());
			                        supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
			                        supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
			                        supplyVproduct.setDistrictId(vendingMap.get(vendingLanep.getSiteId()).getDistrictId());
			                        supplyVproduct.setPointId(vendingMap.get(vendingLanep.getSiteId()).getPointId());
			                        supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
			                        supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
			                        supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
			                        supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
			                        supplyVproduct.setLineId(supplyConfig.getLineId());
			                        supplyVproduct.setLogid(UUID.randomUUID().toString());
			                        supplyVproduct.setProductId(vendingLanep.getProductId());
			                        supplyVproduct.setSaleNum(0);
			                        supplyVproduct.setSiteId(vendingLanep.getSiteId());
			                        supplyVproduct.setSVendingId(SystemUtil.getSeqIdLong(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
			                        supplyVproduct.setInvalidTime("");
			                        supplyVproductList.add(supplyVproduct);
		                    	}else {
		                    		supplyVproduct=selectSupplyVproductList.get(0);
		                    		supplyVproductUpdateList.add(supplyVproduct);
		                    	}
		                    		
		                        
		                        if(productMap.get(vendingLanep.getProductId())==null&&productUpdateMap.get(vendingLanep.getProductId()).getsProductId()==null) {
		                        	//查询是否已存在正在补货的记录
		                        	SupplyProduct supplyProductSelect = new SupplyProduct();
		                        	supplyProductSelect.setCurState("1");
		                        	supplyProductSelect.setsOrderId(supplyOrderNotFinish.getsOrderId());
		                        	supplyProductSelect.setProductId(vendingLanep.getProductId());
		                        	List<SupplyProduct> selectSupplyProductList = supplyProductMapper.selectSupplyProductList(supplyProductSelect);
		                        	if(selectSupplyProductList==null||selectSupplyProductList.isEmpty()) {	
		                        		productMap.put(vendingLanep.getProductId(), SystemUtil.getSeqIdLong(supplyConfig.getCorpId(),Constant.supplyProduct_table));
		                        	}else {
		                        		productUpdateMap.put(vendingLanep.getProductId(), selectSupplyProductList.get(0));
		                        	}
		                        }
		                        if(productMap.get(vendingLanep.getProductId())!=null) {
		                        	supplyVproduct.setsProductId(productMap.get(vendingLanep.getProductId()));
		                        }else {
		                        	supplyVproduct.setsProductId(productUpdateMap.get(vendingLanep.getProductId()).getsProductId());
		                        }
		                        supplyVproduct.setSupplyNum(vendingLanep.getCapacity()-vendingLanep.getCurCap());
		                        supplyVproduct.setRSupplyNum(0);		                        
		                        supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
		                        vendingSupplyNum+=supplyVproduct.getSupplyNum();                    
		                        if(supplyProductMap.get(vendingLanep.getProductId())==null){
		                            supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getCapacity()-vendingLanep.getCurCap());
		                            supplyProductCurMap.put(vendingLanep.getProductId(),vendingLanep.getCurCap());
		                        }else{
		                            supplyProductMap.put(vendingLanep.getProductId(),supplyProductMap.get(vendingLanep.getProductId())+(vendingLanep.getCapacity()-vendingLanep.getCurCap()));
		                            supplyProductCurMap.put(vendingLanep.getProductId(),supplyProductCurMap.get(vendingLanep.getProductId())+vendingLanep.getCurCap());
		                        }
		                    }
		                    //设置站点补货数量
		                    supplyVorder.setSupplyNum(vendingSupplyNum);             
		                    
		                }                    
		            } 
		            //创建站点货道补货清单
		            int supplyNum=0;//补货总数
		            for(String key:productMap.keySet()){
		                SupplyProduct supplyProduct=new SupplyProduct();
		                supplyProduct.setSOrderId(supplyOrderNotFinish.getSOrderId());
		                supplyProduct.setBuyPrice(0f);
		                supplyProduct.setCorpId(supplyConfig.getCorpId());
		                supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
		                supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
		                supplyProduct.setLogid(UUID.randomUUID().toString());
		                supplyProduct.setOutNum(0);
		                supplyProduct.setProductId(key);
		                supplyProduct.setSProductId(productMap.get(key));
		                supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
		                supplyProduct.setSupplyId(supplyConfig.getSupplyId());
		                supplyProduct.setSupplyNum(supplyProductMap.get(key));
		                supplyProduct.setStockOutNum(0);
		                
		                supplyProduct.setSupplyrNum(0);
		                supplyProduct.setSupplynNum(0);
		                supplyProduct.setSupplydNum(0);
		                supplyProduct.setSupplylNum(0);
		                
		                supplyProductList.add(supplyProduct);
		                supplyNum+=supplyProductMap.get(key);
		            }
		            for (String key : productUpdateMap.keySet()) {
		            	SupplyProduct supplyProduct = productUpdateMap.get(key);
		            	supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
		            	supplyProduct.setSupplyNum(supplyProductMap.get(key));
		            	supplyProductUpdateList.add(supplyProduct);
		                supplyNum+=supplyProductMap.get(key);
					}
		            //补货当前数
		            int curNum=0;
		            for(String key:supplyProductCurMap.keySet()){
		                curNum+=supplyProductCurMap.get(key);
		            }
		            supplyOrderNotFinish.setCurPNum(curNum);//当前商品数
		            supplyOrderNotFinish.setSupplyNum(supplyNum);//补货数量
		            supplyOrderNotFinish.setNum(supplyConfig.getWaitSVNum());//当前补货站点数
		            supplyOrderNotFinish.setMaxPNum(supplyConfig.getMaxPNum());//商品存放最大库存数
		            supplyOrderMapper.updateSupplyOrder(supplyOrderNotFinish);
		            if(!supplyVorderList.isEmpty()) {
		            	supplyVorderMapper.insertSupplyVorderBatch(supplyVorderList);
		            }
		            for (SupplyVorder supplyVorder : supplyVorderUpdateList) {
		            	supplyVorderMapper.updateSupplyVorder(supplyVorder);
					}
		            if(!supplyProductList.isEmpty()) {
		            	supplyProductMapper.insertSupplyProductBatch(supplyProductList);
		            }
		            for (SupplyProduct supplyProduct : supplyProductUpdateList) {
		            	supplyProductMapper.updateSupplyProduct(supplyProduct);
					}
		            if(!supplyVproductList.isEmpty()) {
		            	supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);
		            }
		            for (SupplyVproduct supplyVproduct : supplyVproductUpdateList) {
		            	supplyVproductMapper.updateSupplyVproduct(supplyVproduct);
					}
	    		}else {
	    			//已存在补货单
		        	if(StringUtils.isEmpty(site)) {
		        		lock.unlock();
		        		return 6;
		        	}else {
		        		SupplyVorder supplyVorderSelect = new SupplyVorder();
		        		supplyVorderSelect.setSiteId(site);
		        		supplyVorderSelect.setSorderId(supplyOrderNotFinish.getSOrderId());
		        		List<SupplyVorder> curSupplyVorderList = supplyVorderMapper.selectSupplyVorderList(supplyVorderSelect);
		        		if(curSupplyVorderList!=null&&!curSupplyVorderList.isEmpty()) {
		        			lock.unlock();
		        			return 7;
		        		}else {
	        				//获取站点补货商品信息
	                        List<VendingLanep> vendingLanepList=null;
	                        //阈值补货，按商品编号分组
	                        if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
	                            vendingLanepList=vendingLanepMapper.selectSupplyVPThreshold(site);
	                        }else{//全补齐，按商品编号分组
	                            vendingLanepList=vendingLanepMapper.selectSupplyVProduct(site);
	                        }
	                        if(vendingLanepList==null||vendingLanepList.isEmpty()) {
	                        	lock.unlock();
	                        	return 8;
		                    }
	                        //创建商品补货清单
	                        Map<String,Integer> supplyProductMap=new HashMap<>();
	                        Map<String,Integer> supplyProductCurMap=new HashMap<>();
	                        //存放补货商品信息主键
	                        Map<String, String> productMap = new HashMap<String,String>();
	                        Map<String, SupplyProduct> productExistMap = new HashMap<String,SupplyProduct>();
	                        //补货站点记录
	                        SupplyVorder supplyVorder = new SupplyVorder();
	                        supplyVorder.setLogid(UUID.randomUUID().toString());
	                        supplyVorder.setVorderId(SystemUtil.getSeqIdLong(supplyConfig.getCorpId(), "as_supply_vorder"));
	                        supplyVorder.setSorderId(supplyOrderNotFinish.getsOrderId());
	                        supplyVorder.setSupplierId(supplyOrderNotFinish.getSupplierId());
	                        supplyVorder.setCorpId(supplyConfig.getCorpId());
	                        supplyVorder.setSupplyId(supplyOrderNotFinish.getSupplyId());
	                        supplyVorder.setCurState(Constant.SUPPLY_ORDER_CUR_STATE_WAIT);
	                        supplyVorder.setStateTime(DateUtils.getTime());
	                        supplyVorder.setSiteId(site);
	                        supplyVorder.setLineId(supplyConfig.getLineId());
	                        supplyVorder.setCreateTime(DateUtils.getTime());   
	                        supplyVorderList.add(supplyVorder);
	                        //站点需补货数量
	                        int vendingSupplyNum=0;
	                        for(VendingLanep vendingLanep:vendingLanepList){
	                            SupplyVproduct supplyVproduct=new SupplyVproduct();
	                            supplyVproduct.setSOrderId(supplyOrderNotFinish.getSOrderId());
	                            supplyVproduct.setvOrderId(supplyVorder.getVorderId());
	                            SupplyProduct supplyProductSelect = new SupplyProduct();
	                            supplyProductSelect.setSOrderId(supplyOrderNotFinish.getSOrderId());
	                            supplyProductSelect.setProductId(vendingLanep.getProductId());
	                            List<SupplyProduct> selectSupplyProductList = supplyProductMapper.selectSupplyProductList(supplyProductSelect);
	                            if(selectSupplyProductList==null||selectSupplyProductList.isEmpty()) {
	                            	if(productMap.get(vendingLanep.getProductId())==null) {
	                                	productMap.put(vendingLanep.getProductId(), SystemUtil.getSeqIdLong(supplyConfig.getCorpId(),Constant.supplyProduct_table));
	                                }
	                            	supplyVproduct.setsProductId(productMap.get(vendingLanep.getProductId()));
	                            }else {
	                            	productExistMap.put(selectSupplyProductList.get(0).getProductId(), selectSupplyProductList.get(0));
	                            	supplyVproduct.setsProductId(selectSupplyProductList.get(0).getsProductId());
	                            }                  
	                            
	                            supplyVproduct.setBuyPrice(0F);
	                            supplyVproduct.setCorpId(supplyConfig.getCorpId());
	                            supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                            supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
	                            supplyVproduct.setDistrictId(vendingMap.get(vendingLanep.getSiteId()).getDistrictId());
	                            supplyVproduct.setPointId(vendingMap.get(vendingLanep.getSiteId()).getPointId());
	                            supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
	                            supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
	                            supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
	                            supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
	                            supplyVproduct.setLineId(supplyConfig.getLineId());
	                            supplyVproduct.setLogid(UUID.randomUUID().toString());
	                            supplyVproduct.setProductId(vendingLanep.getProductId());
	                            supplyVproduct.setSupplyNum(vendingLanep.getCapacity()-vendingLanep.getCurCap());
	                            supplyVproduct.setRSupplyNum(0);
	                            supplyVproduct.setSaleNum(0);
	                            supplyVproduct.setSiteId(vendingLanep.getSiteId());
	                            supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                            supplyVproduct.setSVendingId(SystemUtil.getSeqIdLong(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
	                            supplyVproduct.setInvalidTime("");
	                            supplyVproductList.add(supplyVproduct);
	                            vendingSupplyNum+=supplyVproduct.getSupplyNum();                    
	                            //this.supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
	                            if(supplyProductMap.get(vendingLanep.getProductId())==null){
	                                supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getCapacity()-vendingLanep.getCurCap());
	                                supplyProductCurMap.put(vendingLanep.getProductId(),vendingLanep.getCurCap());
	                            }else{
	                                supplyProductMap.put(vendingLanep.getProductId(),supplyProductMap.get(vendingLanep.getProductId())+(vendingLanep.getCapacity()-vendingLanep.getCurCap()));
	                                supplyProductCurMap.put(vendingLanep.getProductId(),supplyProductCurMap.get(vendingLanep.getProductId())+vendingLanep.getCurCap());
	                            }
	                        }
	                        //创建站点货道补货清单
	                        int supplyNum=0;//补货总数
	                        for(String key:supplyProductMap.keySet()){
	                            SupplyProduct supplyProduct=new SupplyProduct();
	                            supplyProduct.setSOrderId(supplyOrderNotFinish.getSOrderId());
	                            supplyProduct.setBuyPrice(0f);
	                            supplyProduct.setCorpId(supplyConfig.getCorpId());
	                            supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                            supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
	                            supplyProduct.setLogid(UUID.randomUUID().toString());
	                            supplyProduct.setOutNum(0);
	                            supplyProduct.setProductId(key);
	                            
	                            supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
	                            supplyProduct.setSupplyId(supplyConfig.getSupplyId());
	                            
	                            supplyProduct.setStockOutNum(0);
	                            if(productMap.get(key)!=null) {
	                            	supplyProduct.setSProductId(productMap.get(key));
	                            	supplyProduct.setSupplyNum(supplyProductMap.get(key)); 	

	                            	supplyProduct.setSupplyrNum(0);
	            	                supplyProduct.setSupplynNum(0);
	            	                supplyProduct.setSupplydNum(0);
	            	                supplyProduct.setSupplylNum(0);
	        		                
	                            	supplyProductList.add(supplyProduct);
	                            }else {
	                            	supplyProduct.setSProductId(productExistMap.get(key).getSProductId());
	                            	supplyProduct.setSupplyNum(productExistMap.get(key).getSupplyNum()+supplyProductMap.get(key));
	                            	supplyProductUpdateList.add(supplyProduct);
	                            }
	                            
	                            //this.supplyProductMapper.insertSupplyProduct(supplyProduct);
	                            supplyNum+=supplyProductMap.get(key);
	                        }
	                        //设置站点补货数量
	                        supplyVorder.setSupplyNum(vendingSupplyNum);             
	                        supplyOrderNotFinish.setSupplyNum(supplyOrderNotFinish.getSupplyNum()+vendingSupplyNum);
	                        if(supplyVproductList.isEmpty()) {
	                        	lock.unlock();
	                        	return 8;
	                        }else {
	                        	supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);
	                        }
	                        supplyOrderMapper.updateSupplyOrder(supplyOrderNotFinish);
	                        if(supplyVorderList!=null&&supplyVorderList.size()!=0) {
	                        	supplyVorderMapper.insertSupplyVorderBatch(supplyVorderList);
	                        }
	                        if(!supplyProductList.isEmpty()) {
	                        	supplyProductMapper.insertSupplyProductBatch(supplyProductList);
	                        }
	                        for (SupplyProduct supplyProduct : supplyProductUpdateList) {
	                        	supplyProductMapper.updateSupplyProduct(supplyProduct);
	    					}
	                        
	                        //supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);                    
	        			}
		        	}
        		}
        	}
	        lock.unlock();
		}catch (Exception e) {
			lock.unlock();
			//log
			throw new RuntimeException();
		}
		return 1;
	}

	
}
