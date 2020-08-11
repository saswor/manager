package com.manage.project.system.supply.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.spring.SpringUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.supply.domain.*;
import com.manage.project.system.supply.handler.SupplyOrderHandler;
import com.manage.project.system.supply.mapper.*;
import com.manage.project.system.supply.vo.SupplyOutListVo;
import com.manage.project.system.supply.vo.SupplyProductAddVo;
import com.manage.project.system.supply.vo.SupplyProductVo;
import com.manage.project.system.supply.vo.SupplyVProductAddVo;
import com.manage.project.system.supply.vo.SupplyVendingVo;
import com.manage.project.system.supply.vo.VendingSupplyProductVo;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.common.support.Convert;

/**
 * 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class SupplyOrderServiceImpl implements ISupplyOrderService 
{
	@Autowired
	private SupplyOrderMapper supplyOrderMapper;
	@Autowired
	private SupplyVendingMapper supplyVendingMapper;
    @Autowired
	private SupplyVproductMapper supplyVproductMapper;
    @Autowired
	private SupplyProductMapper supplyProductMapper;
    @Autowired
    private SupplyConfigMapper supplyConfigMapper;
    @Autowired
	private VendingMapper vendingMapper;
    @Autowired
    private VendingLanepMapper vendingLanepMapper;
    @Autowired
    private SupplyVorderMapper supplyVorderMapper;
    
    private Lock lock = new ReentrantLock();
    
    private Map<String,SupplyOrderHandler> lineThreadMap = new HashMap<String,SupplyOrderHandler>();
    
    @Override
    public SupplyOrderHandler getLineThreadByLineId(String lineId) {
    	if(lineThreadMap.get(lineId)==null) {
//    		lock.lock();
    		try {
    			if(lineThreadMap.get(lineId)==null) {
    				lineThreadMap.put(lineId, SpringUtils.getBean(SupplyOrderHandler.class));
    			}
    		}catch (Exception e) {
    			
			}finally {
//				lock.unlock();				
			}
    	}
    	return lineThreadMap.get(lineId);
	}


	/**
     * 查询补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * 
     * @param logid 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。ID
     * @return 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     */
    @Override
	public SupplyOrder selectSupplyOrderById(String logid)
	{
	    return supplyOrderMapper.selectSupplyOrderById(logid);
	}
	
	/**
     * 查询补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。列表
     * 
     * @param supplyOrder 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * @return 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。集合
     */
	@Override
	public List<SupplyOrder> selectSupplyOrderList(SupplyOrder supplyOrder)
	{
	    return supplyOrderMapper.selectSupplyOrderList(supplyOrder);
	}
	
    /**
     * 新增补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。
     * 
     * @param supplyOrder 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * @return 结果
     */
	@Override
	public int insertSupplyOrder(SupplyOrder supplyOrder)
	{
	    return supplyOrderMapper.insertSupplyOrder(supplyOrder);
	}
	
	/**
     * 修改补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。
     * 
     * @param supplyOrder 补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。信息
     * @return 结果
     */
	
	@Override
	public int updateSupplyOrder(SupplyOrder supplyOrder){
	
		return supplyOrderMapper.updateSupplyOrder(supplyOrder);
	}
	

	/**
     * 删除补货记录，由补货配置自动生成或人工生成，相当于仓库领货清单，必须由仓库管理员审核，填写实际的出库数量。对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteSupplyOrderByIds(String ids)
	{
		return supplyOrderMapper.deleteSupplyOrderByIds(Convert.toStrArray(ids));
	}
	
	/**
	 * 生成补货单
	 */
	@Override
	public int insertSupplyOrderBySite(SupplyVProductAddVo supplyVProductAddVo) {
		SupplyVending supplyVending=this.supplyVendingMapper.selectSupplyVendingBySiteId(supplyVProductAddVo.getSiteId());
		if(supplyVending==null)
			return 2;//此站点为配置补货
		SupplyConfig supplyConfig=this.supplyConfigMapper.selectSupplyConfigBySupplyId(supplyVending.getSupplyId());
		if(supplyConfig==null){
			return 3;//此站点无补货配置
		}
		return getLineThreadByLineId(supplyConfig.getLineId()).createSupplyOrderBySupplyConfig(supplyConfig, supplyVProductAddVo.getSiteId(),false);
		//return createSupplyOrderBySupplyConfig(supplyConfig,null);
		//return createSupplyOrderBySupplyConfig(supplyConfig,supplyVProductAddVo.getSiteId());
	}
//	public int insertSupplyOrder(SupplyVProductAddVo supplyVProductAddVo) {
//		SupplyVending supplyVending=this.supplyVendingMapper.selectSupplyVendingBySiteId(supplyVProductAddVo.getSiteId());
//		if(supplyVending==null)
//			return 2;//此站点为配置补货
//		SupplyConfig supplyConfig=this.supplyConfigMapper.selectSupplyConfigBySupplyId(supplyVending.getSupplyId());
//		if(supplyConfig==null){
//			return 3;//此站点无补货配置
//		}
//		//查询线路下已认证的站点信息
//		Vending vending=this.vendingMapper.selectVendingBySiteId(supplyVProductAddVo.getSiteId());
//		Vending vendingP=new Vending();
//		vendingP.setLineId(supplyConfig.getLineId());
//		vendingP.setCurState(Constant.VENDING_CURSTATE_1);
//		List<Vending> vendingList=this.vendingMapper.selectVendingList(vendingP);
//		String[] siteIds = new String[vendingList.size()];
//		int index=0;
//		Map<String,Vending> vendingMap=new HashMap<>();
//		for(Vending vendingPP:vendingList){
//			siteIds[index]=vendingPP.getSiteId();
//			vendingMap.put(vendingPP.getSiteId(),vendingPP);
//			index++;
//		}
//		//查询是否有未完成完成的补货订单
//		SupplyOrder supplyOrderP=new SupplyOrder();
//		supplyOrderP.setCurState(Constant.supplyOrderCur_finshed);
//		supplyOrderP.setSupplyId(supplyConfig.getSupplyId());
//		SupplyOrder supplyOrderR=supplyOrderMapper.selectNoFinshOrderBySupplyId(supplyOrderP);
//		//新生成补货订单
//        if(supplyOrderR==null){
//            //创建补货订单
//            SupplyOrder supplyOrder=new SupplyOrder();
//            supplyOrder.setCurState(Constant.supplyConfigCur_wait);
//            supplyOrder.setSupplyId(supplyConfig.getSupplyId());
//            supplyOrder.setRefreshDate(DateUtils.dateTimeNow("YYYY-MM-dd"));
//            supplyOrder.setCorpId(supplyConfig.getCorpId());
//            supplyOrder.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//            supplyOrder.setLogid(UUID.randomUUID().toString());
//            supplyOrder.setFinshState(Constant.supplyOrderCur_noFinsh);
//            supplyOrder.setLineId(supplyConfig.getLineId());
//            supplyOrder.setName(supplyConfig.getName());
//            supplyOrder.setSOrderId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyOrder_table));
//            supplyOrder.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//            supplyOrder.setStockState(Constant.supplyConfigCheck_noCheck);
//            supplyOrder.setSupplierId(supplyConfig.getSupplierId());
//
//            supplyOrder.setType(supplyConfig.getType());//补货类型
//            supplyOrder.setWmId(supplyConfig.getWmId());//仓库编号
//            supplyOrder.setSStateTime("");
//            supplyOrder.setSupplyFTime("");
//            supplyOrder.setCheckId("");
//            //创建商品补货清单
//            Map<String,Integer> supplyProductMap=new HashMap<>();
//            Map<String,Integer> supplyProductCurMap=new HashMap<>();
//            for(String siteId:siteIds){
//                //获取站点补货商品信息
//                List<VendingLanep> vendingLanepList=null;
//                //阈值补货，按商品编号分组
//                if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
//                    vendingLanepList=vendingLanepMapper.selectSupplyVPThreshold(siteId);
//                }else{//全补齐，按商品编号分组
//                    vendingLanepList=vendingLanepMapper.selectSupplyVProduct(siteId);
//                }
//                for(VendingLanep vendingLanep:vendingLanepList){
//                    SupplyVproduct supplyVproduct=new SupplyVproduct();
//                    supplyVproduct.setSOrderId(supplyOrder.getSOrderId());
//                    supplyVproduct.setBuyPrice(0F);
//                    supplyVproduct.setCorpId(supplyConfig.getCorpId());
//                    supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                    supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                    supplyVproduct.setDistrictId(vendingMap.get(vendingLanep.getSiteId()).getDistrictId());
//                    supplyVproduct.setPointId(vendingMap.get(vendingLanep.getSiteId()).getPointId());
//                    supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
//                    supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
//                    supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
//                    supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
//                    supplyVproduct.setLineId(supplyConfig.getLineId());
//                    supplyVproduct.setLogid(UUID.randomUUID().toString());
//                    supplyVproduct.setProductId(vendingLanep.getProductId());
//                    supplyVproduct.setSupplyNum(vendingLanep.getCapacity()-vendingLanep.getCurCap());
//                    supplyVproduct.setRSupplyNum(0);
//                    supplyVproduct.setSaleNum(0);
//                    supplyVproduct.setSiteId(vendingLanep.getSiteId());
//                    supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                    supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
//                    supplyVproduct.setInvalidTime("");
//                    this.supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
//                    if(supplyProductMap.get(vendingLanep.getProductId())==null){
//                        supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getCapacity()-vendingLanep.getCurCap());
//                        supplyProductCurMap.put(vendingLanep.getProductId(),vendingLanep.getCurCap());
//                    }else{
//                        supplyProductMap.put(vendingLanep.getProductId(),supplyProductMap.get(vendingLanep.getProductId())+(vendingLanep.getCapacity()-vendingLanep.getCurCap()));
//                        supplyProductCurMap.put(vendingLanep.getProductId(),supplyProductCurMap.get(vendingLanep.getProductId())+vendingLanep.getCurCap());
//                    }
//                }
//                Vending curVending = SystemUtil.getVendingBase(siteId);
//                Vending vendingSave=new Vending();
//                vendingSave.setLogid(curVending.getLogid());
//                vendingSave.setSupplyState("1");
//                vendingSave.setSupplySTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                this.vendingMapper.updateVending(vendingSave);
//            }
//            //创建站点货道补货清单
//            int supplyNum=0;//补货总数
//            for(String key:supplyProductMap.keySet()){
//                SupplyProduct supplyProduct=new SupplyProduct();
//                supplyProduct.setSOrderId(supplyOrder.getSOrderId());
//                supplyProduct.setBuyPrice(0f);
//                supplyProduct.setCorpId(supplyConfig.getCorpId());
//                supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                supplyProduct.setLogid(UUID.randomUUID().toString());
//                supplyProduct.setOutNum(0);
//                supplyProduct.setProductId(key);
//                supplyProduct.setSProductId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
//                supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                supplyProduct.setSupplyId(supplyConfig.getSupplyId());
//                supplyProduct.setSupplyNum(supplyProductMap.get(key));
//                this.supplyProductMapper.insertSupplyProduct(supplyProduct);
//                supplyNum+=supplyProductMap.get(key);
//            }
//            //补货当前数
//            int curNum=0;
//            for(String key:supplyProductCurMap.keySet()){
//                curNum+=supplyProductCurMap.get(key);
//            }
//            supplyOrder.setCurPNum(curNum);//当前商品数
//            supplyOrder.setSupplyNum(supplyNum);//补货数量
//            supplyOrder.setNum(supplyConfig.getWaitSVNum());//当前补货站点数
//            supplyOrder.setMaxPNum(supplyConfig.getMaxPNum());//商品存放最大库存数
//            this.supplyOrderMapper.insertSupplyOrder(supplyOrder);
//        }else{//老的补货订单更新补货数量
//            Map<String,Integer> supplyProductMap=new HashMap<>();
//            Map<String,Integer> supplyProductCurMap=new HashMap<>();
//            for(String siteId:siteIds){
//                //获取站点补货商品信息
//                List<VendingLanep> vendingLanepList=null;
//                //阈值补货，按商品编号分组
//                if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
//                    vendingLanepList=vendingLanepMapper.selectSupplyVPThreshold(siteId);
//                }else{//全补齐，按商品编号分组
//                    vendingLanepList=vendingLanepMapper.selectSupplyVProduct(siteId);
//                }
//                for(VendingLanep vendingLanep:vendingLanepList){
//                    SupplyVproduct supplyVproductP=new SupplyVproduct();
//                    supplyVproductP.setSOrderId(supplyOrderR.getSOrderId());
//                    supplyVproductP.setLaneSId(vendingLanep.getLaneSId());
//                    supplyVproductP.setLaneEId(vendingLanep.getLaneEId());
//                    supplyVproductP.setSiteId(vendingLanep.getSiteId());
//                    SupplyVproduct supplyVproduct=this.supplyVproductMapper.selectSupplyVproductByVpId(supplyVproductP);
//                    if(supplyVproduct==null){
//                        supplyVproduct=new SupplyVproduct();
//                        supplyVproduct.setSOrderId(supplyOrderR.getSOrderId());
//                        supplyVproduct.setBuyPrice(0F);
//                        supplyVproduct.setCorpId(supplyConfig.getCorpId());
//                        supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                        supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                        supplyVproduct.setDistrictId(vendingMap.get(vendingLanep.getSiteId()).getDistrictId());
//                        supplyVproduct.setPointId(vendingMap.get(vendingLanep.getSiteId()).getPointId());
//                        supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
//                        supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
//                        supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
//                        supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
//                        supplyVproduct.setLineId(supplyConfig.getLineId());
//                        supplyVproduct.setLogid(UUID.randomUUID().toString());
//                        supplyVproduct.setProductId(vendingLanep.getProductId());
//                        supplyVproduct.setRSupplyNum(0);
//                        supplyVproduct.setSupplyNum(vendingLanep.getCapacity()-vendingLanep.getCurCap());
//                        supplyVproduct.setSaleNum(0);
//                        supplyVproduct.setSiteId(vendingLanep.getSiteId());
//                        supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                        supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
//                        supplyVproduct.setInvalidTime("");
//                        this.supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
//                        if(supplyProductMap.get(vendingLanep.getProductId())==null){
//                            supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getCapacity()-vendingLanep.getCurCap());
//                            supplyProductCurMap.put(vendingLanep.getProductId(),vendingLanep.getCurCap());
//                        }else{
//                            supplyProductMap.put(vendingLanep.getProductId(),supplyProductMap.get(vendingLanep.getProductId())+(vendingLanep.getCapacity()-vendingLanep.getCurCap()));
//                            supplyProductCurMap.put(vendingLanep.getProductId(),supplyProductCurMap.get(vendingLanep.getProductId())+vendingLanep.getCurCap());
//                        }
//                    }else{
//                        if(supplyVproduct.getSupplyNum()<(vendingLanep.getCapacity()-vendingLanep.getCurCap())) {//本次更新的补货数量如果比原来的大则更新 比原来的小不更新
//                            supplyVproduct.setRSupplyNum(vendingLanep.getCapacity() - vendingLanep.getCurCap());
//                            this.supplyVproductMapper.updateSupplyVproduct(supplyVproduct);
//                            if(supplyProductMap.get(vendingLanep.getProductId())==null){
//                                supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getCapacity()-vendingLanep.getCurCap());
//                                supplyProductCurMap.put(vendingLanep.getProductId(),vendingLanep.getCurCap());
//                            }else{
//                                supplyProductMap.put(vendingLanep.getProductId(),supplyProductMap.get(vendingLanep.getProductId())+(vendingLanep.getCapacity()-vendingLanep.getCurCap()));
//                                supplyProductCurMap.put(vendingLanep.getProductId(),supplyProductCurMap.get(vendingLanep.getProductId())+vendingLanep.getCurCap());
//                            }
//                        }else{
//                            if(supplyProductMap.get(vendingLanep.getProductId())==null){
//                                supplyProductMap.put(vendingLanep.getProductId(),supplyVproduct.getSupplyNum());
//                                supplyProductCurMap.put(vendingLanep.getProductId(),vendingLanep.getCurCap());
//                            }else{
//                                supplyProductMap.put(vendingLanep.getProductId(),supplyProductMap.get(vendingLanep.getProductId())+supplyVproduct.getSupplyNum());
//                                supplyProductCurMap.put(vendingLanep.getProductId(),supplyProductCurMap.get(vendingLanep.getProductId())+vendingLanep.getCurCap());
//                            }
//                        }
//                    }
//                }
//                Vending curVending = SystemUtil.getVendingBase(siteId);
//                Vending vendingSave=new Vending();
//                vendingSave.setLogid(curVending.getLogid());
//                vendingSave.setSupplyState("1");
//                vendingSave.setSupplySTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                this.vendingMapper.updateVending(vendingSave);
//            }
//            //创建站点货道补货清单
//            int supplyNum=0;//补货总数
//            for(String key:supplyProductMap.keySet()){
//                SupplyProduct supplyProductP=new SupplyProduct();
//                supplyProductP.setSOrderId(supplyOrderR.getSOrderId());
//                supplyProductP.setProductId(key);
//                SupplyProduct supplyProduct=this.supplyProductMapper.selectSupplyProductByPId(supplyProductP);
//                if(supplyProduct==null){
//                    supplyProduct=new SupplyProduct();
//                    supplyProduct.setSOrderId(supplyOrderR.getSOrderId());
//                    supplyProduct.setBuyPrice(0f);
//                    supplyProduct.setCorpId(supplyConfig.getCorpId());
//                    supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                    supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                    supplyProduct.setLogid(UUID.randomUUID().toString());
//                    supplyProduct.setOutNum(0);
//                    supplyProduct.setProductId(key);
//                    supplyProduct.setSProductId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
//                    supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                    supplyProduct.setSupplyId(supplyConfig.getSupplyId());
//                    supplyProduct.setSupplyNum(supplyProductMap.get(key));
//                    this.supplyProductMapper.insertSupplyProduct(supplyProduct);
//                    supplyNum+=supplyProductMap.get(key);
//                }else{
//                    //如果货道最新的补货数量比原来的大则更新补货数量
//                    if(supplyProduct.getSupplyNum()<supplyProductMap.get(key)) {
//                        supplyProduct.setSupplyNum(supplyProductMap.get(key));
//                        supplyNum+=supplyProductMap.get(key);
//                    }else{
//                        supplyNum+=supplyProduct.getSupplyNum();
//                    }
//                }
//            }
//            //补货当前数
//            int curNum=0;
//            for(String key:supplyProductCurMap.keySet()){
//                curNum+=supplyProductCurMap.get(key);
//            }
//            supplyOrderR.setCurPNum(curNum);//当前商品数
//            supplyOrderR.setSupplyNum(supplyNum);//补货数量
//            supplyOrderR.setNum(supplyConfig.getWaitSVNum());//当前补货站点数
//            supplyOrderR.setMaxPNum(supplyConfig.getMaxPNum());//商品存放最大库存数
//            supplyOrderR.setRefreshDate(DateUtils.dateTimeNow("YYYY-MM-dd"));
//            this.supplyOrderMapper.updateSupplyOrder(supplyOrderR);
//        }
//		return 1;
//	}
	
	@Override
	@Transactional
	public int createSupplyOrderBySupplyConfig(SupplyConfig supplyConfig,String site) {
		List<SupplyVorder> supplyVorderList = new ArrayList<SupplyVorder>();
		List<SupplyProduct> supplyProductList = new ArrayList<SupplyProduct>();
		List<SupplyProduct> supplyProductUpdateList = new ArrayList<SupplyProduct>();
		List<SupplyVproduct> supplyVproductList = new ArrayList<SupplyVproduct>();

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
			List<VendingLanep> vendingLanepList = vendingLanepMapper.selectVendingLanepList(vendingLanepSelect);
			for (VendingLanep vendingLanep : vendingLanepList) {
				if(vendingLanep!=null&&(StringUtils.isEmpty(vendingLanep.getProductId())||vendingLanep.getCapacity()==null||vendingLanep.getCapacity()<1
						||vendingLanep.getWarnCap()==null||vendingLanep.getWarnCap()<1)) {
					return 5;
				}
			}
			siteIds[index]=vending.getSiteId();
			vendingMap.put(vending.getSiteId(),vending);
			index++;
		}
		//查询是否有未完成完成的补货订单
		SupplyOrder supplyOrderSelect=new SupplyOrder();
		supplyOrderSelect.setCurState("2");
		supplyOrderSelect.setSupplyId(supplyConfig.getSupplyId());
		SupplyOrder supplyOrderNotFinish=supplyOrderMapper.selectNoFinshOrderBySupplyId(supplyOrderSelect);
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
            supplyOrder.setSOrderId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyOrder_table));
            supplyOrder.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
            supplyOrder.setStockState(Constant.supplyConfigCheck_noCheck);
            supplyOrder.setSupplierId(supplyConfig.getSupplierId());

            supplyOrder.setType(supplyConfig.getType());//补货类型
            supplyOrder.setWmId(supplyConfig.getWmId());//仓库编号
            supplyOrder.setSStateTime("");
            supplyOrder.setSupplyFTime("");
            supplyOrder.setCheckId("");
            //创建商品补货清单
            Map<String,Integer> supplyProductMap=new HashMap<>();
            Map<String,Integer> supplyProductCurMap=new HashMap<>();
            //存放补货商品信息主键
            Map<String, String> productMap = new HashMap<String,String>();
            for(String siteId:siteIds){
            	if(site==null||siteId.equals(site)) {
            		//获取站点补货商品信息
                    List<VendingLanep> vendingLanepList=null;
                    //阈值补货，按商品编号分组
                    if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
                        vendingLanepList=vendingLanepMapper.selectSupplyVPThreshold(siteId);
                    }else{//全补齐，按商品编号分组
                        vendingLanepList=vendingLanepMapper.selectSupplyVProduct(siteId);
                    }
                    //补货站点记录
                    SupplyVorder supplyVorder = new SupplyVorder();
                    supplyVorder.setLogid(UUID.randomUUID().toString());
                    supplyVorder.setVorderId(SystemUtil.getSeqId(supplyConfig.getCorpId(), "as_supply_vorder"));
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
                        	productMap.put(vendingLanep.getProductId(), SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
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
                        supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
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
                    //Vending curVending = SystemUtil.getVendingBase(siteId);
                    //Vending vendingSave=new Vending();
                    //vendingSave.setLogid(curVending.getLogid());
                    //vendingSave.setSupplyState("1");
                    //vendingSave.setSupplySTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
                    //this.vendingMapper.updateVending(vendingSave);
                }
                //创建站点货道补货清单
                       
            }
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
                supplyProductList.add(supplyProduct);
                //this.supplyProductMapper.insertSupplyProduct(supplyProduct);
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
            supplyOrderMapper.insertSupplyOrder(supplyOrder);
            supplyVorderMapper.insertSupplyVorderBatch(supplyVorderList);
            supplyProductMapper.insertSupplyProductBatch(supplyProductList);
            supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);
            //supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);
        }else{
        	//已存在补货单
        	if(StringUtils.isEmpty(site)) {
        		return 6;
        	}else {
        		SupplyVorder supplyVorderSelect = new SupplyVorder();
        		supplyVorderSelect.setSiteId(site);
        		supplyVorderSelect.setSorderId(supplyOrderNotFinish.getSOrderId());
        		List<SupplyVorder> curSupplyVorderList = supplyVorderMapper.selectSupplyVorderList(supplyVorderSelect);
        		if(curSupplyVorderList!=null&&!curSupplyVorderList.isEmpty()) {
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
                    //创建商品补货清单
                    Map<String,Integer> supplyProductMap=new HashMap<>();
                    Map<String,Integer> supplyProductCurMap=new HashMap<>();
                    //存放补货商品信息主键
                    Map<String, String> productMap = new HashMap<String,String>();
                    Map<String, SupplyProduct> productExistMap = new HashMap<String,SupplyProduct>();
                    //补货站点记录
                    SupplyVorder supplyVorder = new SupplyVorder();
                    supplyVorder.setLogid(UUID.randomUUID().toString());
                    supplyVorder.setVorderId(SystemUtil.getSeqId(supplyConfig.getCorpId(), "as_supply_vorder"));
                    supplyVorder.setSorderId(supplyOrderNotFinish.getsOrderId());
                    supplyVorder.setSupplierId(supplyOrderNotFinish.getSupplierId());
                    supplyVorder.setCorpId(supplyConfig.getCorpId());
                    supplyVorder.setSupplyId(supplyOrderNotFinish.getSupplyId());
                    supplyVorder.setCurState(Constant.SUPPLY_ORDER_CUR_STATE_WAIT);
                    supplyVorder.setStateTime(DateUtils.getTime());
                    supplyVorder.setSiteId(site);
                    supplyVorder.setLineId(supplyConfig.getLineId());
                    supplyVorder.setCreateTime(DateUtils.getTime());               
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
                            	productMap.put(vendingLanep.getProductId(), SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
                            }
                        }else {
                        	
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
                        supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
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
                    supplyVorderList.add(supplyVorder);
                    supplyOrderNotFinish.setSupplyNum(supplyOrderNotFinish.getSupplyNum()+vendingSupplyNum);
                    supplyOrderMapper.updateSupplyOrder(supplyOrderNotFinish);
                    supplyVorderMapper.insertSupplyVorderBatch(supplyVorderList);
                    supplyProductMapper.insertSupplyProductBatch(supplyProductList);
                    for (SupplyProduct supplyProduct : supplyProductUpdateList) {
                    	supplyProductMapper.updateSupplyProduct(supplyProduct);
					}
                    supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);
                    //supplyVproductMapper.insertSupplyVproductBatch(supplyVproductList);                    
                    return 1;
        		}
        	}        	
        }
		return 1;
	}

	/*@Override
	public int insertSupplyOrder(SupplyVProductAddVo supplyVProductAddVo) {
		SupplyVending supplyVending=this.supplyVendingMapper.selectSupplyVendingBySiteId(supplyVProductAddVo.getSiteId());
		if(supplyVending==null)
			return 2;//此站点为配置补货
		SupplyConfig supplyConfig=this.supplyConfigMapper.selectSupplyConfigBySupplyId(supplyVending.getSupplyId());
		if(supplyConfig==null){
			return 3;//此站点无补货配置
		}
		//查询线路下的站点信息
		Vending vending=this.vendingMapper.selectVendingBySiteId(supplyVProductAddVo.getSiteId());
		Vending vendingP=new Vending();
		vendingP.setLineId(supplyConfig.getLineId());
		List<Vending> vendingList=this.vendingMapper.selectVendingList(vendingP);
		String[] siteIds = new String[vendingList.size()];
		int index=0;
		for(Vending vendingPP:vendingList){
			siteIds[index]=vendingPP.getSiteId();
			index++;
		}
		//查询是否有未完成完成的补货订单
		SupplyOrder supplyOrderP=new SupplyOrder();
		supplyOrderP.setCurState(Constant.supplyConfigCur_finsh);
		supplyOrderP.setSupplyId(supplyConfig.getSupplyId());
		SupplyOrder supplyOrderR=supplyOrderMapper.selectNoFinshOrderBySupplyId(supplyOrderP);
		if(supplyOrderR==null){
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
			supplyOrder.setSOrderId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyOrder_table));
			supplyOrder.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
			supplyOrder.setStockState(Constant.supplyConfigCheck_noCheck);
			supplyOrder.setSupplierId(supplyConfig.getSupplierId());
			supplyOrder.setType(supplyConfig.getType());//补货类型
			supplyOrder.setWmId(supplyConfig.getWmId());//仓库编号
			supplyOrder.setSStateTime("");
			supplyOrder.setSupplyFTime("");
			supplyOrder.setCheckId("");
			String siteId=supplyVProductAddVo.getSiteId();
			Map<String,String> supplyProductMap=new HashMap<>();
			//获取站点补货商品信息
			for(SupplyProductAddVo vendingLanep:supplyVProductAddVo.getSupplyInfo()){
				supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getProductId());
				SupplyVproduct supplyVproduct=new SupplyVproduct();
				supplyVproduct.setSOrderId(supplyOrder.getSOrderId());
				supplyVproduct.setBuyPrice(0F);
				supplyVproduct.setCorpId(supplyConfig.getCorpId());
				supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
				supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
				supplyVproduct.setDistrictId(vending.getDistrictId());
				supplyVproduct.setPointId(vending.getPointId());
				supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
				supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
				supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
				supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
				supplyVproduct.setLineId(supplyConfig.getLineId());
				supplyVproduct.setLogid(UUID.randomUUID().toString());
				supplyVproduct.setProductId(vendingLanep.getProductId());
				supplyVproduct.setSupplyNum(Integer.parseInt(vendingLanep.getSupplyNum()));
				supplyVproduct.setRSupplyNum(0);
				supplyVproduct.setSaleNum(0);
				supplyVproduct.setSiteId(siteId);
				supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
				supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
				this.supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
			}
			//创建补货订单商品补货清单
			int supplyNum=0;//补货总数
			for(String key:supplyProductMap.keySet()){
				//统计补货订单下商品的补货量
				SupplyVproduct supplyVproductPP = new SupplyVproduct();
				supplyVproductPP.setSOrderId(supplyOrder.getSOrderId());
				supplyVproductPP.setProductId(key);
				SupplyVproduct supplyVproductRR = this.supplyVproductMapper.selectTotalSupplyNumByProductId(supplyVproductPP);

				SupplyProduct supplyProduct=new SupplyProduct();
				supplyProduct.setSOrderId(supplyOrder.getSOrderId());
				supplyProduct.setBuyPrice(0f);
				supplyProduct.setCorpId(supplyConfig.getCorpId());
				supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
				supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
				supplyProduct.setLogid(UUID.randomUUID().toString());
				supplyProduct.setOutNum(0);
				supplyProduct.setProductId(key);
				supplyProduct.setSProductId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
				supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
				supplyProduct.setSupplyId(supplyConfig.getSupplyId());
				supplyProduct.setSupplyNum(supplyVproductRR.getSupplyNum());
				this.supplyProductMapper.insertSupplyProduct(supplyProduct);
			}
			//查询线路下的当前库存数量
			VendingLanep vendingLanepRR=this.vendingLanepMapper.selectTotalNumBySiteIds(siteIds);
			supplyOrder.setCurPNum(vendingLanepRR.getCurCap());//当前商品数

			//统计补货订单的总补货、总实际补货、总销售数统计信息
			SupplyVproduct supplyVproductPP=new SupplyVproduct();
			supplyVproductPP.setSOrderId(supplyOrder.getSOrderId());
			SupplyVproduct supplyVproductRR=this.supplyVproductMapper.selectTotalSupplyNumByOrderId(supplyVproductPP);
			supplyOrder.setSupplyNum(supplyVproductRR.getSupplyNum());//补货数量

			supplyOrder.setNum(supplyConfig.getWaitSVNum());//当前补货站点数
			supplyOrder.setMaxPNum(supplyConfig.getMaxPNum());//商品存放最大库存数
			this.supplyOrderMapper.insertSupplyOrder(supplyOrder);
		}else{
			Map<String,String> supplyProductMap=new HashMap<>();
			//获取站点补货商品信息
			for(SupplyProductAddVo vendingLanep:supplyVProductAddVo.getSupplyInfo()) {
				supplyProductMap.put(vendingLanep.getProductId(),vendingLanep.getProductId());
				SupplyVproduct supplyVproductP = new SupplyVproduct();
				supplyVproductP.setSOrderId(supplyOrderR.getSOrderId());
				supplyVproductP.setSiteId(supplyVProductAddVo.getSiteId());
				supplyVproductP.setLaneSId(vendingLanep.getLaneSId());
				supplyVproductP.setLaneEId(vendingLanep.getLaneEId());
				SupplyVproduct supplyVproduct=this.supplyVproductMapper.selectSupplyVproductByVpId(supplyVproductP);
				if(supplyVproduct==null){
					supplyVproduct=new SupplyVproduct();
					supplyVproduct.setSOrderId(supplyOrderR.getSOrderId());
					supplyVproduct.setBuyPrice(0F);
					supplyVproduct.setCorpId(supplyConfig.getCorpId());
					supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
					supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
					supplyVproduct.setDistrictId(vending.getDistrictId());
					supplyVproduct.setPointId(vending.getPointId());
					supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
					supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
					supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
					supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
					supplyVproduct.setLineId(supplyConfig.getLineId());
					supplyVproduct.setLogid(UUID.randomUUID().toString());
					supplyVproduct.setProductId(vendingLanep.getProductId());
					supplyVproduct.setRSupplyNum(0);
					supplyVproduct.setSupplyNum(Integer.parseInt(vendingLanep.getSupplyNum()));
					supplyVproduct.setSaleNum(0);
					supplyVproduct.setSiteId(supplyVProductAddVo.getSiteId());
					supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
					supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyVProduct_table));
					this.supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
				}else{
					VendingLanep vendingLanepP=new VendingLanep();
					vendingLanepP.setSiteId(supplyVProductAddVo.getSiteId());
					vendingLanepP.setLaneSId(vendingLanep.getLaneSId());
					vendingLanepP.setLaneEId(vendingLanep.getLaneEId());
					VendingLanep vendingLanepR=this.vendingLanepMapper.selectVendingLanepByProId(vendingLanepP);
					supplyVproduct.setSupplyNum(vendingLanepR.getCapacity()-vendingLanepR.getCurCap());
					this.supplyVproductMapper.updateSupplyVproduct(supplyVproduct);
				}
			}
			//统计补货订单下的站点的总补货、总实际补货、总销售数统计信息
			for(String key:supplyProductMap.keySet()) {
				//查询补货订单商品的总补货量
				SupplyVproduct supplyVproductPP = new SupplyVproduct();
				supplyVproductPP.setSOrderId(supplyOrderR.getSOrderId());
				supplyVproductPP.setProductId(key);
				SupplyVproduct supplyVproductRR = this.supplyVproductMapper.selectTotalSupplyNumByProductId(supplyVproductPP);
				//查询补货订单商品汇总表是否有此商品
				SupplyProduct supplyProductP=new SupplyProduct();
				supplyProductP.setSOrderId(supplyOrderR.getSOrderId());
				supplyProductP.setProductId(key);
				SupplyProduct supplyProduct=this.supplyProductMapper.selectSupplyProductByPId(supplyProductP);
				if(supplyProduct==null){
					supplyProduct=new SupplyProduct();
					supplyProduct.setSOrderId(supplyOrderR.getSOrderId());
					supplyProduct.setBuyPrice(0f);
					supplyProduct.setCorpId(supplyConfig.getCorpId());
					supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
					supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
					supplyProduct.setLogid(UUID.randomUUID().toString());
					supplyProduct.setOutNum(0);
					supplyProduct.setProductId(key);
					supplyProduct.setSProductId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
					supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
					supplyProduct.setSupplyId(supplyConfig.getSupplyId());
					supplyProduct.setSupplyNum(supplyVproductRR.getSupplyNum());
					this.supplyProductMapper.insertSupplyProduct(supplyProduct);
				}else{
					supplyProduct.setSupplyNum(supplyVproductRR.getSupplyNum());
					this.supplyProductMapper.updateSupplyProduct(supplyProduct);
				}
			}
			//统计补货订单的总补货量
			SupplyVproduct supplyVproductPP=new SupplyVproduct();
			supplyVproductPP.setSOrderId(supplyOrderR.getSOrderId());
			SupplyVproduct supplyVproductRR=this.supplyVproductMapper.selectTotalSupplyNumByOrderId(supplyVproductPP);
			supplyOrderR.setSupplyNum(supplyVproductRR.getSupplyNum());
			//查询线路下的当前库存数量
			VendingLanep vendingLanepRR=this.vendingLanepMapper.selectTotalNumBySiteIds(siteIds);
			supplyOrderR.setCurPNum(vendingLanepRR.getCurCap());//当前商品数
			this.updateSupplyOrder(supplyOrderR);
		}
		return 1;
	}*/

	/**
	 * 查询售货机补货记录
	 * 
	 * @param vo
	 * @return
	 */
	@Override
	public List<SupplyOrder> selectSiteSupplyOrder(SupplyVendingVo vo) {
		return supplyOrderMapper.selectSiteSupplyOrder(vo);
	}


	/**
	 * 根据线路生成补货单
	 */
	@Override
	public int insertSupplyOrderByLine(String supplyId) {
		SupplyConfig supplyConfig=this.supplyConfigMapper.selectSupplyConfigBySupplyId(supplyId);
		if(supplyConfig==null){
			return 3;//此站点无补货配置
		}
		return getLineThreadByLineId(supplyConfig.getLineId()).createSupplyOrderBySupplyConfig(supplyConfig, null,false);
	}

	/**
	 * 查询是否存在未完成的补货单
	 * 
	 * @param supplyId
	 * @return 补货单编号
	 */
	@Override
	public String selectNotFinishSupplyOrder(String supplyId) {
		return supplyOrderMapper.selectNotFinishSupplyOrder(supplyId);
	}

}
