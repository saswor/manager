package com.manage.project.system.job;

import com.manage.common.utils.DateUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.domain.SupplyVending;
import com.manage.project.system.supply.mapper.SupplyConfigMapper;
import com.manage.project.system.supply.mapper.SupplyVendingMapper;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName SupplyWarnTaskService
 * @Dscription TDD
 * @Author 邬思杰
 * @Version 1.0
 * @Date 14:34$ 2018-10-12$
 **/
@Service
@EnableScheduling
public class SupplyWarnTaskService {
	@Autowired
    private SupplyConfigMapper supplyConfigMapper;
    @Autowired
    private VendingMapper vendingMapper;
    @Autowired
    private VendingLanepMapper vendingLanepMapper;
    @Autowired
    private SupplyVendingMapper supplyVendingMapper;
    //@Autowired
	private Logger log=LoggerFactory.getLogger(SupplyWarnTaskService.class);

    /**
     * 检查站点货道库存的库存情况，根据站点的补货配置方式生成补货预警库存量
     */
    @Scheduled(cron ="0 0/3 * * * ?")
    public void lineStockWarn(){
    	log.info("生成补货库存预警存量:"+DateUtils.getTime());
        List<SupplyConfig> supplyConfigList=supplyConfigMapper.selectSupplyConfigList(null);
        //循环执行补货配置信息检查,有待优化
        for(SupplyConfig supplyConfig:supplyConfigList){
        	try {
        		//查询线路下的站点信息
                Vending vendingP=new Vending();
                vendingP.setLineId(supplyConfig.getLineId());
                List<Vending> vendingList=this.vendingMapper.selectNeverDelVendingList(vendingP);
                String[] siteIds = new String[vendingList.size()];
                int index=0;
                for(Vending vending:vendingList){
                    siteIds[index]=vending.getSiteId();
                    index++;
                    //修改对应售货机的补货配置
                    SupplyVending supplyVending = supplyVendingMapper.selectSupplyVendingBySiteId(vending.getSiteId());
                    Map<String, BigDecimal> vendingCap = vendingMapper.selectVendingCap(vending.getSiteId());
                    BigDecimal pmaxNum = vendingCap.get("pmaxNum");
                    if(pmaxNum==null) {
                    	supplyVending.setMaxPNum(0);
                    }else {
                    	supplyVending.setMaxPNum(pmaxNum.intValue());
                    }
                    BigDecimal pcurNum = vendingCap.get("pcurNum");
                    if(pcurNum==null) {
                    	supplyVending.setCurPNum(0);
                    }else {
                    	supplyVending.setCurPNum(pcurNum.intValue());
                    }
                    supplyVendingMapper.updateSupplyVending(supplyVending);
                }
                //根据配置的补货方式统计待补货商品数和站点的数量
                List<VendingLanep> vendingLanepList=null;
                if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
                    vendingLanepList=this.vendingLanepMapper.selectCapacityThresholdBySiteIds(siteIds);
                }else{
                    vendingLanepList=this.vendingLanepMapper.selectCapacityBySiteIds(siteIds);
                }
                //统计所有站点当前商品容量和最大库存容量数据以及库存等级
                int curPnum=0;
                int maxPnum=0;
                for(VendingLanep vendingLanep:vendingLanepList){
                    curPnum=curPnum+vendingLanep.getCurCap();
                    maxPnum=maxPnum+vendingLanep.getCapacity();
                }
                supplyConfig.setMaxPNum(maxPnum);
                supplyConfig.setCurPNum(curPnum);
                supplyConfig.setWaitSPNum(maxPnum-curPnum);//待补货商品数量
                supplyConfig.setSupplyPnum(maxPnum-curPnum);//待补货商品数量
                supplyConfig.setWaitSVNum(vendingLanepList.size());//待补货站点数量
                this.supplyConfigMapper.updateSupplyConfig(supplyConfig);
        	}catch (Exception e) {
				log.error("生成补货告警信息失败：补货单"+supplyConfig.getSupplyId()+",名称"+supplyConfig.getName()+",时间"+DateUtils.getTime(),e);
			}
            
        }
    }
}
