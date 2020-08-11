package com.manage.project.system.job;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.domain.SupplyOrder;
import com.manage.project.system.supply.domain.SupplyProduct;
import com.manage.project.system.supply.domain.SupplyVproduct;
import com.manage.project.system.supply.mapper.*;
import com.manage.project.system.supply.service.ISupplyOrderService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingMapper;
import com.manage.project.system.vending.service.IVendingLanepService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName SupplyWarnTaskService
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 14:34$ 2018-10-12$
 **/
@Service
@EnableScheduling
public class SupplyOrderGenTaskService {
	
	private Logger log=LoggerFactory.getLogger(SupplyOrderGenTaskService.class);
    @Autowired
    private SupplyConfigMapper supplyConfigMapper;
    @Autowired
    private VendingMapper vendingMapper;
    @Autowired
    private VendingLanepMapper vendingLanepMapper;
    @Autowired
    private SupplyVendingMapper supplyVendingMapper;

    @Autowired
    private SupplyOrderMapper supplyOrderMapper;
    @Autowired
    private SupplyProductMapper supplyProductMapper;

    @Autowired
    private SupplyVproductMapper supplyVproductMapper;
    
    @Autowired
    private ISupplyOrderService supplyOrderService;
    /**
     * 每隔一段时间检查补货配置是否要生成补货单
     */
    @Scheduled(cron ="0 0/1 * * * ?")
    public void lineStockWarn(){
    	log.info("定时任务生成补货单:"+DateUtils.getTime());
        List<SupplyConfig> supplyConfigList=supplyConfigMapper.selectSupplyConfigList(null);
        //根据补货配置
        for(SupplyConfig supplyConfig:supplyConfigList){
            String strategy=supplyConfig.getStrategy();
            JSONArray jsonArray=JSONArray.parseArray(strategy);
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String evey=jsonObject.getString("every");
                String value=jsonObject.getString("value");
                String time=jsonObject.getString("time");
                if(Constant.strategyEvery_month.equals(evey)){

                }else if(Constant.strategyEvery_week.equals(evey)){
                    Calendar cal=Calendar.getInstance();
                    //判断如果是当前日期等于配置的日期则生成补货单
                    if((cal.get(Calendar.DAY_OF_WEEK)-1)==Integer.parseInt(value)){
                    	//supplyOrderService.createSupplyOrderBySupplyConfig(supplyConfig,null);
                    	//查询时间是否符合
                    	if(StringUtils.isNotEmpty(time)) {
	                    	int hour=Integer.parseInt(time.split(":")[0]);
	                    	int minute=Integer.parseInt(time.split(":")[1]);
	                    	int second=Integer.parseInt(time.split(":")[2]);
	                    	cal.set(Calendar.HOUR_OF_DAY, hour);
	                    	cal.set(Calendar.MINUTE,minute);
	                    	cal.set(Calendar.SECOND,second);
	                    	if(supplyConfig.getAdvTime()==null) {
	                    		supplyConfig.setAdvTime(0);
	                    	}
	                    	cal.add(Calendar.MINUTE, -supplyConfig.getAdvTime());
	                    	Date supplyTime = cal.getTime();
	                    	if(supplyTime.after(new Date())) {
	                    		return;
	                    	}
                    	}
                    	//查询今天有无生成过补货单信息
	                    SupplyOrder supplyOrderP=new SupplyOrder();
	                    supplyOrderP.setRefreshDate(DateUtils.dateTimeNow("YYYY-MM-dd"));
	                    supplyOrderP.setSupplyId(supplyConfig.getSupplyId());
	                    List<SupplyOrder> supplyOrderToday=supplyOrderMapper.selectSupplyOrderByRefreshDate(supplyOrderP);
	                    if(supplyOrderToday==null||supplyOrderToday.isEmpty()){
	                    	try {
	                    		//查询补货配置下是否存在未完结的补货单
//	                    		String sOrderId = supplyOrderService.selectNotFinishSupplyOrder(supplyConfig.getSupplyId());
//	                    		if(StringUtils.isNotEmpty(sOrderId)) {
//	                    			continue;
//	                    		}
	                    		supplyOrderService.getLineThreadByLineId(supplyConfig.getLineId()).createSupplyOrderBySupplyConfig(supplyConfig, null,false);
	                    	}catch (Exception e) {
								log.error("生成补货单失败,补货配置编号"+supplyConfig.getSupplyId()+",线路编号"+supplyConfig.getLineId()+",时间"+DateUtils.getTime(),e);
							}
	                    	
	                    }                  	
                    }
                }
            }
        }
    }
    
//    public void lineStockWarn(){
//    	log.info("定时任务生成补货单:"+DateUtils.getTime());
//        List<SupplyConfig> supplyConfigList=supplyConfigMapper.selectSupplyConfigList(null);
//        //根据补货配置
//        for(SupplyConfig supplyConfig:supplyConfigList){
//            String strategy=supplyConfig.getStrategy();
//            JSONArray jsonArray=JSONArray.parseArray(strategy);
//            for(int i=0;i<jsonArray.size();i++){
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                String evey=jsonObject.getString("every");
//                String value=jsonObject.getString("value");
//                String time=jsonObject.getString("time");
//                if(Constant.strategyEvery_month.equals(evey)){
//
//                }else if(Constant.strategyEvery_week.equals(evey)){
//                    Calendar cal=Calendar.getInstance();
//                    //判断如果是当前日期等于配置的日期则生成补货单
//                    if((cal.get(Calendar.DAY_OF_WEEK)-1)==Integer.parseInt(value)){
//                        //查询今天有无生成过补货单信息
//                        SupplyOrder supplyOrderP=new SupplyOrder();
//                        supplyOrderP.setRefreshDate(DateUtils.dateTimeNow("YYYY-MM-dd"));
//                        supplyOrderP.setSupplyId(supplyConfig.getSupplyId());
//                        SupplyOrder supplyOrderR=supplyOrderMapper.selectSupplyOrderByRefreshDate(supplyOrderP);
//                        //当前日期没有生成补货订单或没有更新补货订单
//                        if(supplyOrderR==null){
//                            //查询是否有未完成完成的补货订单
//                            supplyOrderP=new SupplyOrder();
//                            supplyOrderP.setCurState(Constant.supplyOrderCur_finshed);
//                            supplyOrderP.setSupplyId(supplyConfig.getSupplyId());
//                            supplyOrderR=supplyOrderMapper.selectNoFinshOrderBySupplyId(supplyOrderP);
//                            //查询线路下的站点信息
//                            Vending vendingP=new Vending();
//                            vendingP.setLineId(supplyConfig.getLineId());
//                            vendingP.setCurState(Constant.VENDING_CURSTATE_1);
//                            List<Vending> vendingList=this.vendingMapper.selectVendingList(vendingP);
//                            Map<String,Vending> vendingMap=new HashMap<>();
//                            String[] siteIds = new String[vendingList.size()];
//                            int index=0;
//                            for(Vending vending:vendingList){
//                                siteIds[index]=vending.getSiteId();
//                                vendingMap.put(vending.getSiteId(),vending);
//                                index++;
//                            }
//                            //新生成补货订单
//                            if(supplyOrderR==null){
//                                //创建补货订单
//                                SupplyOrder supplyOrder=new SupplyOrder();
//                                supplyOrder.setCurState(Constant.supplyConfigCur_wait);
//                                supplyOrder.setSupplyId(supplyConfig.getSupplyId());
//                                supplyOrder.setRefreshDate(DateUtils.dateTimeNow("YYYY-MM-dd"));
//                                supplyOrder.setCorpId(supplyConfig.getCorpId());
//                                supplyOrder.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                supplyOrder.setLogid(UUID.randomUUID().toString());
//                                supplyOrder.setFinshState(Constant.supplyOrderCur_noFinsh);
//                                supplyOrder.setLineId(supplyConfig.getLineId());
//                                supplyOrder.setName(supplyConfig.getName());
//                                supplyOrder.setSOrderId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyOrder_table));
//                                supplyOrder.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                supplyOrder.setStockState(Constant.supplyConfigCheck_noCheck);
//                                supplyOrder.setSupplierId(supplyConfig.getSupplierId());
//
//                                supplyOrder.setType(supplyConfig.getType());//补货类型
//                                supplyOrder.setWmId(supplyConfig.getWmId());//仓库编号
//                                supplyOrder.setSStateTime("");
//                                supplyOrder.setSupplyFTime("");
//                                supplyOrder.setCheckId("");
//                                //创建商品补货清单
//                                Map<String,Integer> supplyProductMap=new HashMap<>();
//                                Map<String,Integer> supplyProductCurMap=new HashMap<>();
//                                for(String siteId:siteIds){
//                                    //获取站点补货商品信息
//                                    List<VendingLanep> vendingLanepList=null;
//                                    //阈值补货，按商品编号分组
//                                    if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
//                                        vendingLanepList=vendingLanepMapper.selectSupplyVPThreshold(siteId);
//                                    }else{//全补齐，按商品编号分组
//                                        vendingLanepList=vendingLanepMapper.selectSupplyVProduct(siteId);
//                                    }
//                                    if(vendingLanepList!=null && vendingLanepList.size()>0) {
//                                        //更新补货信息
//                                        Vending vending=vendingMap.get(siteId);
//                                        Vending vendingS=new Vending();
//                                        vendingS.setLogid(vending.getLogid());
//                                        vendingS.setSupplyState("1");
//                                        vendingS.setSupplySTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                        this.vendingMapper.updateVending(vendingS);
//                                        for (VendingLanep vendingLanep : vendingLanepList) {
//                                            SupplyVproduct supplyVproduct = new SupplyVproduct();
//                                            supplyVproduct.setSOrderId(supplyOrder.getSOrderId());
//                                            supplyVproduct.setBuyPrice(0F);
//                                            supplyVproduct.setCorpId(supplyConfig.getCorpId());
//                                            supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                            supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                                            supplyVproduct.setDistrictId(vendingMap.get(vendingLanep.getSiteId()).getDistrictId());
//                                            supplyVproduct.setPointId(vendingMap.get(vendingLanep.getSiteId()).getPointId());
//                                            supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
//                                            supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
//                                            supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
//                                            supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
//                                            supplyVproduct.setLineId(supplyConfig.getLineId());
//                                            supplyVproduct.setLogid(UUID.randomUUID().toString());
//                                            supplyVproduct.setProductId(vendingLanep.getProductId());
//                                            supplyVproduct.setSupplyNum(vendingLanep.getCapacity() - vendingLanep.getCurCap());
//                                            supplyVproduct.setRSupplyNum(0);
//                                            supplyVproduct.setSaleNum(0);
//                                            supplyVproduct.setSiteId(vendingLanep.getSiteId());
//                                            supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                            supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(), Constant.supplyVProduct_table));
//                                            supplyVproduct.setInvalidTime("");
//                                            this.supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
//                                            if (supplyProductMap.get(vendingLanep.getProductId()) == null) {
//                                                supplyProductMap.put(vendingLanep.getProductId(), vendingLanep.getCapacity() - vendingLanep.getCurCap());
//                                                supplyProductCurMap.put(vendingLanep.getProductId(), vendingLanep.getCurCap());
//                                            } else {
//                                                supplyProductMap.put(vendingLanep.getProductId(), supplyProductMap.get(vendingLanep.getProductId()) + (vendingLanep.getCapacity() - vendingLanep.getCurCap()));
//                                                supplyProductCurMap.put(vendingLanep.getProductId(), supplyProductCurMap.get(vendingLanep.getProductId()) + vendingLanep.getCurCap());
//                                            }
//                                        }
//                                    }
//                                }
//                                //创建站点货道补货清单
//                                int supplyNum=0;//补货总数
//                                for(String key:supplyProductMap.keySet()){
//                                    SupplyProduct supplyProduct=new SupplyProduct();
//                                    supplyProduct.setSOrderId(supplyOrder.getSOrderId());
//                                    supplyProduct.setBuyPrice(0f);
//                                    supplyProduct.setCorpId(supplyConfig.getCorpId());
//                                    supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                    supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                                    supplyProduct.setLogid(UUID.randomUUID().toString());
//                                    supplyProduct.setOutNum(0);
//                                    supplyProduct.setProductId(key);
//                                    supplyProduct.setSProductId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
//                                    supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                    supplyProduct.setSupplyId(supplyConfig.getSupplyId());
//                                    supplyProduct.setSupplyNum(supplyProductMap.get(key));
//                                    this.supplyProductMapper.insertSupplyProduct(supplyProduct);
//                                    supplyNum+=supplyProductMap.get(key);
//                                }
//                                //补货当前数
//                                int curNum=0;
//                                for(String key:supplyProductCurMap.keySet()){
//                                    curNum+=supplyProductCurMap.get(key);
//                                }
//                                supplyOrder.setCurPNum(curNum);//当前商品数
//                                supplyOrder.setSupplyNum(supplyNum);//补货数量
//                                supplyOrder.setNum(supplyConfig.getWaitSVNum());//当前补货站点数
//                                supplyOrder.setMaxPNum(supplyConfig.getMaxPNum());//商品存放最大库存数
//                                this.supplyOrderMapper.insertSupplyOrder(supplyOrder);
//                            }else{//老的补货订单更新补货数量
//                                Map<String,Integer> supplyProductMap=new HashMap<>();
//                                Map<String,Integer> supplyProductCurMap=new HashMap<>();
//                                for(String siteId:siteIds) {
//                                    //获取站点补货商品信息
//                                    List<VendingLanep> vendingLanepList = null;
//                                    //阈值补货，按商品编号分组
//                                    if (supplyConfig.getType().equals(Constant.supplyType_yuzhi)) {
//                                        vendingLanepList = vendingLanepMapper.selectSupplyVPThreshold(siteId);
//                                    } else {//全补齐，按商品编号分组
//                                        vendingLanepList = vendingLanepMapper.selectSupplyVProduct(siteId);
//                                    }
//                                    if(vendingLanepList!=null && vendingLanepList.size()>0) {
//                                        Vending vending=vendingMap.get(siteId);
//                                        Vending vendingS=new Vending();
//                                        vendingS.setLogid(vending.getLogid());
//                                        vendingS.setSupplyState("1");
//                                        vendingS.setSupplySTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                        this.vendingMapper.updateVending(vendingS);
//                                     for (VendingLanep vendingLanep : vendingLanepList) {
//                                        SupplyVproduct supplyVproductP = new SupplyVproduct();
//                                        supplyVproductP.setSOrderId(supplyOrderR.getSOrderId());
//                                        supplyVproductP.setLaneSId(vendingLanep.getLaneSId());
//                                        supplyVproductP.setLaneEId(vendingLanep.getLaneEId());
//                                        supplyVproductP.setSiteId(vendingLanep.getSiteId());
//                                        SupplyVproduct supplyVproduct = this.supplyVproductMapper.selectSupplyVproductByVpId(supplyVproductP);
//                                        if (supplyVproduct == null) {
//                                            supplyVproduct = new SupplyVproduct();
//                                            supplyVproduct.setSOrderId(supplyOrderR.getSOrderId());
//                                            supplyVproduct.setBuyPrice(0F);
//                                            supplyVproduct.setCorpId(supplyConfig.getCorpId());
//                                            supplyVproduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                            supplyVproduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                                            supplyVproduct.setDistrictId(vendingMap.get(vendingLanep.getSiteId()).getDistrictId());
//                                            supplyVproduct.setPointId(vendingMap.get(vendingLanep.getSiteId()).getPointId());
//                                            supplyVproduct.setFinshState(Constant.supplyVProductOut_noOut);
//                                            supplyVproduct.setInvalidState(Constant.supplyConfigOut_alOver);
//                                            supplyVproduct.setLaneEId(vendingLanep.getLaneEId());
//                                            supplyVproduct.setLaneSId(vendingLanep.getLaneSId());
//                                            supplyVproduct.setLineId(supplyConfig.getLineId());
//                                            supplyVproduct.setLogid(UUID.randomUUID().toString());
//                                            supplyVproduct.setProductId(vendingLanep.getProductId());
//                                            supplyVproduct.setRSupplyNum(0);
//                                            supplyVproduct.setSupplyNum(vendingLanep.getCapacity() - vendingLanep.getCurCap());
//                                            supplyVproduct.setSaleNum(0);
//                                            supplyVproduct.setSiteId(vendingLanep.getSiteId());
//                                            supplyVproduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                            supplyVproduct.setSVendingId(SystemUtil.getSeqId(supplyConfig.getCorpId(), Constant.supplyVProduct_table));
//                                            supplyVproduct.setInvalidTime("");
//                                            this.supplyVproductMapper.insertSupplyVproduct(supplyVproduct);
//                                            if (supplyProductMap.get(vendingLanep.getProductId()) == null) {
//                                                supplyProductMap.put(vendingLanep.getProductId(), vendingLanep.getCapacity() - vendingLanep.getCurCap());
//                                                supplyProductCurMap.put(vendingLanep.getProductId(), vendingLanep.getCurCap());
//                                            } else {
//                                                supplyProductMap.put(vendingLanep.getProductId(), supplyProductMap.get(vendingLanep.getProductId()) + (vendingLanep.getCapacity() - vendingLanep.getCurCap()));
//                                                supplyProductCurMap.put(vendingLanep.getProductId(), supplyProductCurMap.get(vendingLanep.getProductId()) + vendingLanep.getCurCap());
//                                            }
//                                        } else {
//                                            if (supplyVproduct.getSupplyNum() < (vendingLanep.getCapacity() - vendingLanep.getCurCap())) {//本次更新的补货数量如果比原来的大则更新 比原来的小不更新
//                                                supplyVproduct.setRSupplyNum(vendingLanep.getCapacity() - vendingLanep.getCurCap());
//                                                this.supplyVproductMapper.updateSupplyVproduct(supplyVproduct);
//                                                if (supplyProductMap.get(vendingLanep.getProductId()) == null) {
//                                                    supplyProductMap.put(vendingLanep.getProductId(), vendingLanep.getCapacity() - vendingLanep.getCurCap());
//                                                    supplyProductCurMap.put(vendingLanep.getProductId(), vendingLanep.getCurCap());
//                                                } else {
//                                                    supplyProductMap.put(vendingLanep.getProductId(), supplyProductMap.get(vendingLanep.getProductId()) + (vendingLanep.getCapacity() - vendingLanep.getCurCap()));
//                                                    supplyProductCurMap.put(vendingLanep.getProductId(), supplyProductCurMap.get(vendingLanep.getProductId()) + vendingLanep.getCurCap());
//                                                }
//                                            } else {
//                                                if (supplyProductMap.get(vendingLanep.getProductId()) == null) {
//                                                    supplyProductMap.put(vendingLanep.getProductId(), supplyVproduct.getSupplyNum());
//                                                    supplyProductCurMap.put(vendingLanep.getProductId(), vendingLanep.getCurCap());
//                                                } else {
//                                                    supplyProductMap.put(vendingLanep.getProductId(), supplyProductMap.get(vendingLanep.getProductId()) + supplyVproduct.getSupplyNum());
//                                                    supplyProductCurMap.put(vendingLanep.getProductId(), supplyProductCurMap.get(vendingLanep.getProductId()) + vendingLanep.getCurCap());
//                                                }
//                                            }
//                                        }
//                                    }
//                                  }
//                                }
//                                //创建站点货道补货清单
//                                int supplyNum=0;//补货总数
//                                for(String key:supplyProductMap.keySet()){
//                                    SupplyProduct supplyProductP=new SupplyProduct();
//                                    supplyProductP.setSOrderId(supplyOrderR.getSOrderId());
//                                    supplyProductP.setProductId(key);
//                                    SupplyProduct supplyProduct=this.supplyProductMapper.selectSupplyProductByPId(supplyProductP);
//                                    if(supplyProduct==null){
//                                        supplyProduct=new SupplyProduct();
//                                        supplyProduct.setSOrderId(supplyOrderR.getSOrderId());
//                                        supplyProduct.setBuyPrice(0f);
//                                        supplyProduct.setCorpId(supplyConfig.getCorpId());
//                                        supplyProduct.setCreateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                        supplyProduct.setCurState(Constant.supplyVProductCur_waitSupply);
//                                        supplyProduct.setLogid(UUID.randomUUID().toString());
//                                        supplyProduct.setOutNum(0);
//                                        supplyProduct.setProductId(key);
//                                        supplyProduct.setSProductId(SystemUtil.getSeqId(supplyConfig.getCorpId(),Constant.supplyProduct_table));
//                                        supplyProduct.setStateTime(DateUtils.dateTimeNow("YYYY-MM-dd HH:mm:ss"));
//                                        supplyProduct.setSupplyId(supplyConfig.getSupplyId());
//                                        supplyProduct.setSupplyNum(supplyProductMap.get(key));
//                                        this.supplyProductMapper.insertSupplyProduct(supplyProduct);
//                                        supplyNum+=supplyProductMap.get(key);
//                                    }else{
//                                        //如果货道最新的补货数量比原来的大则更新补货数量
//                                        if(supplyProduct.getSupplyNum()<supplyProductMap.get(key)) {
//                                            supplyProduct.setSupplyNum(supplyProductMap.get(key));
//                                            supplyNum+=supplyProductMap.get(key);
//                                        }else{
//                                            supplyNum+=supplyProduct.getSupplyNum();
//                                        }
//                                    }
//                                }
//                                //补货当前数
//                                int curNum=0;
//                                for(String key:supplyProductCurMap.keySet()){
//                                    curNum+=supplyProductCurMap.get(key);
//                                }
//                                supplyOrderR.setCurPNum(curNum);//当前商品数
//                                supplyOrderR.setSupplyNum(supplyNum);//补货数量
//                                supplyOrderR.setNum(supplyConfig.getWaitSVNum());//当前补货站点数
//                                supplyOrderR.setMaxPNum(supplyConfig.getMaxPNum());//商品存放最大库存数
//                                supplyOrderR.setRefreshDate(DateUtils.dateTimeNow("YYYY-MM-dd"));
//                                this.supplyOrderMapper.updateSupplyOrder(supplyOrderR);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
