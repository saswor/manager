package com.manage.project.system.supply.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.project.common.Constant;
import com.manage.project.system.supply.domain.SupplyConfig;
import com.manage.project.system.supply.domain.SupplyVending;
import com.manage.project.system.supply.mapper.SupplyConfigMapper;
import com.manage.project.system.supply.mapper.SupplyVendingMapper;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.Vending;
import com.manage.project.system.vending.domain.VendingLanep;
import com.manage.project.system.vending.mapper.VendingLanepMapper;
import com.manage.project.system.vending.mapper.VendingMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.common.support.Convert;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-02
 */
@Service
public class SupplyConfigServiceImpl implements ISupplyConfigService 
{
	@Autowired
	private SupplyConfigMapper supplyConfigMapper;
	@Autowired
	private VendingMapper vendingMapper;
	@Autowired
	private VendingLanepMapper vendingLanepMapper;
	@Autowired
    private SupplyVendingMapper supplyVendingMapper;
	/**
     * 查询商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * 
     * @param logid 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。ID
     * @return 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     */
    @Override
	public SupplyConfig selectSupplyConfigById(String logid)
	{
	    return supplyConfigMapper.selectSupplyConfigById(logid);
	}
	
	/**
     * 查询商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。列表
     * 
     * @param supplyConfig 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * @return 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。集合
     */
	@Override
	public List<SupplyConfig> selectSupplyConfigList(SupplyConfig supplyConfig)
	{
	    return supplyConfigMapper.selectSupplyConfigList(supplyConfig);
	}
	
    /**
     * 新增商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
     * 
     * @param supplyConfig 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * @return 结果
     */
	@Override
	@Transactional
	public int insertSupplyConfig(SupplyConfig supplyConfig)
	{
		if(StringUtils.isEmpty(supplyConfig.getName())||StringUtils.isEmpty(supplyConfig.getWmId())||StringUtils.isEmpty(supplyConfig.getSupplierId())||StringUtils.isEmpty(supplyConfig.getType())) {
			return 8;
		}
		SupplyConfig supplyConfigP=new SupplyConfig();
		String lineId=supplyConfig.getLineId();
		if(StringUtils.isEmpty(lineId)) {
			return 4;
		}
		supplyConfigP.setLineId(lineId);
		List<SupplyConfig> supplyConfiglist=this.supplyConfigMapper.selectSupplyConfigList(supplyConfigP);
		if(supplyConfiglist!=null && supplyConfiglist.size()>0){
			return 2;
		}
		supplyConfig.setLogid(UUID.randomUUID().toString());
		supplyConfig.setSupplyId(SystemUtil.getSeqId(ShiroUtils.getCorpId(),Constant.supplyConfig_table));
		supplyConfig.setCorpId(ShiroUtils.getCorpId());
		//查询线路下未删除的站点信息
		Vending vendingP=new Vending();
		vendingP.setLineId(supplyConfig.getLineId());
		List<Vending> vendingList=this.vendingMapper.selectNeverDelVendingList(vendingP);
		String[] siteIds = new String[vendingList.size()];
		if(siteIds.length==0){
			return 3;
		}
		int index=0;
		//统计所有站点当前商品容量和最大库存容量数据以及库存等级
		int curPnum=0;
		int maxPnum=0;
        for(Vending vending:vendingList){
			siteIds[index]=vending.getSiteId();
			index++;
			curPnum=curPnum+vending.getPcurNum();
			maxPnum=maxPnum+vending.getPmaxNum();
			//查询所有站点的货道是否都已经配置了商品
			VendingLanep vendingLanep = new VendingLanep();
			vendingLanep.setSiteId(vending.getSiteId());
			vendingLanep.setLaneSate(Constant.VENDING_LANESTATE_NORMAL);
			List<VendingLanep> list = vendingLanepMapper.selectVendingLanepList(vendingLanep);
			for (VendingLanep vlp : list) {
				if(vlp==null||StringUtils.isEmpty(vlp.getProductId())) {
					return 6;
				}
			}
		}
		supplyConfig.setCurPNum(curPnum);//当前商品容量
		supplyConfig.setMaxPNum(maxPnum);//最大库存商品容量
		int storyLevel=(int)((float)curPnum/(float) maxPnum)*100;
		String[] frist=supplyConfig.getFristlevel().split("-");
		String[] two=supplyConfig.getTwolevel().split("-");
		String[] three=supplyConfig.getThreelevel().split("-");
        if(storyLevel>=Integer.parseInt(frist[0]) && storyLevel<=Integer.parseInt(frist[1]))
           supplyConfig.setStoryLevel(Constant.storyLevel_frist);//库存等级
		else if(storyLevel>=Integer.parseInt(two[0]) && storyLevel<=Integer.parseInt(two[1]))
			supplyConfig.setStoryLevel(Constant.storyLevel_two);//库存等级
		else if(storyLevel>=Integer.parseInt(three[0]) && storyLevel<=Integer.parseInt(three[1]))
			supplyConfig.setStoryLevel(Constant.storyLevel_three);//库存等级
		else
			supplyConfig.setStoryLevel(Constant.storyLevel_frist);//库存等级

		//根据配置的补货方式统计待补货商品数和站点的数量
		maxPnum=0;
		curPnum=0;
		List<VendingLanep> vendingLanepList=null;
		if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
			vendingLanepList=this.vendingLanepMapper.selectCapacityThresholdBySiteIds(siteIds);
		}else{
			vendingLanepList=this.vendingLanepMapper.selectCapacityBySiteIds(siteIds);
		}
		for(VendingLanep vendingLanep:vendingLanepList){
			curPnum=curPnum+vendingLanep.getCurCap();
			maxPnum=maxPnum+vendingLanep.getCapacity();
		}
		if(maxPnum == 0){
			return 5;
		}
		supplyConfig.setMaxPNum(maxPnum);
		supplyConfig.setWaitSPNum(maxPnum-curPnum);//待补货商品数量
		supplyConfig.setSupplyPnum(maxPnum-curPnum);//待补货商品数量
		supplyConfig.setWaitSVNum(vendingLanepList.size());//待补货站点数量

		//统计每月补货次数以及下次补货时间
		JSONArray jsonArray= JSONArray.parseArray(supplyConfig.getStrategy());
		//判断补货策略是否完整
		if(jsonArray==null||jsonArray.isEmpty()) {
			return 7;
		}
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String evey=jsonObject.getString("every");
            String value=jsonObject.getString("value");
            if(StringUtils.isEmpty(evey)||StringUtils.isEmpty(value)) {
            	return 7;
            }
        }
//		List<Map> list = jsonArray.toJavaList(Map.class);
//		for (Map map : list) {
//			Set keySet = map.keySet();
//			for (Object key : keySet) {
//				if(key==null||"".equals(key)||map.get(key)==null||"".equals(map.get(key))) {
//					return 7;
//				}
//			}
//		}
		supplyConfig.setLastSTime("");//下次补货时间
		supplyConfig.setEveryTime(jsonArray.size());//每月补货次数

		supplyConfig.setNum(vendingList.size());//配置站点数量
		supplyConfig.setPendingNum(0);

		supplyConfig.setFinshNum(0);//已完成补货单数量
		supplyConfig.setSproductNum(0);//已补货商品数量
		supplyConfig.setFinshTime(0);
		SupplyVending supplyVending=null;

		for(Vending vending:vendingList){
			supplyVending=new SupplyVending();
			supplyVending.setCorpId(SystemUtil.getCorpId());
			supplyVending.setLogid(UUID.randomUUID().toString());
			supplyVending.setCurPNum(vending.getPcurNum());
			supplyVending.setFristlevel(supplyConfig.getFristlevel());
			supplyVending.setTwoLevel(supplyConfig.getTwolevel());
			supplyVending.setThreeLevel(supplyConfig.getThreelevel());
			supplyVending.setLineId(vending.getLineId());
			supplyVending.setMaxPNum(supplyConfig.getMaxPNum());
			supplyVending.setSiteId(vending.getSiteId());
			supplyVending.setName(supplyConfig.getName());
			supplyVending.setSupplyId(supplyConfig.getSupplyId());
			supplyVending.setCreateTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));
			storyLevel=(int)((float)vending.getPcurNum()/(float)vending.getPmaxNum())*100;
			if(storyLevel>Integer.parseInt(frist[0]) && storyLevel<Integer.parseInt(frist[1]))
				supplyVending.setStoryLevel(Constant.storyLevel_frist);//库存等级
			else if(storyLevel>Integer.parseInt(two[0]) && storyLevel<Integer.parseInt(two[1]))
				supplyVending.setStoryLevel(Constant.storyLevel_two);//库存等级
			else if(storyLevel>Integer.parseInt(three[0]) && storyLevel<Integer.parseInt(three[1]))
				supplyVending.setStoryLevel(Constant.storyLevel_three);//库存等级
			else
				supplyVending.setStoryLevel(Constant.storyLevel_frist);//库存等级
			supplyVendingMapper.insertSupplyVending(supplyVending);
		}
	    this.supplyConfigMapper.insertSupplyConfig(supplyConfig);
	    return 1;
	}
	
	/**
     * 修改商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。
     * 
     * @param supplyConfig 商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。信息
     * @return 结果
     */
	@Override
	@Transactional
	public int updateSupplyConfig(SupplyConfig supplyConfig)
	{
		if(StringUtils.isEmpty(supplyConfig.getName())||StringUtils.isEmpty(supplyConfig.getWmId())||StringUtils.isEmpty(supplyConfig.getSupplierId())||StringUtils.isEmpty(supplyConfig.getType())) {
			return 8;
		}
		SupplyConfig supplyConfigR=this.supplyConfigMapper.selectSupplyConfigBySupplyId(supplyConfig.getSupplyId());
		if(supplyConfigR==null){
			return 0;
		}
		supplyConfigR.setName(supplyConfig.getName());
		supplyConfigR.setDescription(supplyConfig.getDescription());
		supplyConfigR.setStrategy(supplyConfig.getStrategy());
		supplyConfigR.setFristlevel(supplyConfig.getFristlevel());
		supplyConfigR.setTwolevel(supplyConfig.getTwolevel());
		supplyConfigR.setThreelevel(supplyConfig.getThreelevel());
		supplyConfigR.setType(supplyConfig.getType());
		supplyConfigR.setWmId(supplyConfig.getWmId());
		supplyConfigR.setSupplierId(supplyConfig.getSupplierId());
		supplyConfigR.setAdvTime(supplyConfig.getAdvTime());
		//查询线路下的站点信息
		Vending vendingP=new Vending();
		vendingP.setLineId(supplyConfig.getLineId());
		List<Vending> vendingList=this.vendingMapper.selectNeverDelVendingList(vendingP);
		String[] siteIds = new String[vendingList.size()];
		int index=0;
		for(Vending vending:vendingList){
			siteIds[index]=vending.getSiteId();
			index++;
		}
		//根据配置的补货方式统计待补货商品数和站点的数量
		List<VendingLanep> vendingLanepList=this.vendingLanepMapper.selectCapacityBySiteIds(siteIds);
		if(supplyConfig.getType().equals(Constant.supplyType_yuzhi)){
			vendingLanepList=this.vendingLanepMapper.selectCapacityThresholdBySiteIds(siteIds);
		}else{
			vendingLanepList=this.vendingLanepMapper.selectCapacityBySiteIds(siteIds);
		}
		int curPnum=0;
		int maxPnum=0;
		for(VendingLanep vendingLanep:vendingLanepList){
			curPnum=curPnum+vendingLanep.getCurCap();
			maxPnum=maxPnum+vendingLanep.getCapacity();
		}
		supplyConfigR.setWaitSPNum(maxPnum-curPnum);//待补货商品数量
		supplyConfigR.setSupplyPnum(maxPnum-curPnum);//待补货商品数量
		supplyConfigR.setWaitSVNum(vendingLanepList.size());//待补货站点数量
        //更新售货机配置信息
		String[] frist=supplyConfigR.getFristlevel().split("-");
		String[] two=supplyConfigR.getTwolevel().split("-");
		String[] three=supplyConfigR.getThreelevel().split("-");
		SupplyVending supplyVendingP=new SupplyVending();
		supplyVendingP.setSupplyId(supplyConfigR.getSupplyId());
		//统计每月补货次数以及下次补货时间
		JSONArray jsonArray= JSONArray.parseArray(supplyConfig.getStrategy());
		//判断补货策略是否完整
		if(jsonArray==null||jsonArray.isEmpty()) {
			return 7;
		}
		for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String evey=jsonObject.getString("every");
            String value=jsonObject.getString("value");
            if(StringUtils.isEmpty(evey)||StringUtils.isEmpty(value)) {
            	return 7;
            }
        }
//		List<Map> list = jsonArray.toJavaList(Map.class);
//		for (Map map : list) {
//			Set keySet = map.keySet();
//			for (Object key : keySet) {
//				if(key==null||"".equals(key)||map.get(key)==null||"".equals(map.get(key))) {
//					return 7;
//				}
//			}
//		}
		supplyConfigR.setLastSTime("");//下次补货时间
		supplyConfigR.setEveryTime(jsonArray.size());//每周补货次数
		
        List<SupplyVending> supplyVendingList=this.supplyVendingMapper.selectSupplyVendingList(supplyVendingP);
        //判断线路下的所有站点都有对应的记录
        for (int i = 0; i < siteIds.length; i++) {
        	boolean flag=false;
        	for(SupplyVending supplyVending:supplyVendingList){
        		if(siteIds[i]!=null&&siteIds[i].equals(supplyVending.getSiteId())) {
					flag=true;
					break;
				}
        	}
        	//如果该站点在当前补货配置下面没有记录,插入新的记录
        	if(!flag) {
        		supplyVendingMapper.deleteSupplyVendingBySiteId(siteIds[i]);
        		Vending vending = vendingMapper.selectVendingBySiteId(siteIds[i]);
        		SupplyVending supplyVending=new SupplyVending();
    			supplyVending.setCorpId(SystemUtil.getCorpId());
    			supplyVending.setLogid(UUID.randomUUID().toString());
    			supplyVending.setCurPNum(vending.getPcurNum());
    			supplyVending.setFristlevel(supplyConfig.getFristlevel());
    			supplyVending.setTwoLevel(supplyConfig.getTwolevel());
    			supplyVending.setThreeLevel(supplyConfig.getThreelevel());
    			supplyVending.setLineId(vending.getLineId());
    			supplyVending.setMaxPNum(supplyConfig.getMaxPNum());
    			supplyVending.setSiteId(vending.getSiteId());
    			supplyVending.setName(supplyConfig.getName());
    			supplyVending.setSupplyId(supplyConfig.getSupplyId());
    			supplyVending.setCreateTime(DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));
    			float storyLevel=((float)vending.getPcurNum()/(float)vending.getPmaxNum())*100;
    			if(storyLevel>Integer.parseInt(frist[0]) && storyLevel<Integer.parseInt(frist[1]))
    				supplyVending.setStoryLevel(Constant.storyLevel_frist);//库存等级
    			else if(storyLevel>Integer.parseInt(two[0]) && storyLevel<Integer.parseInt(two[1]))
    				supplyVending.setStoryLevel(Constant.storyLevel_two);//库存等级
    			else if(storyLevel>Integer.parseInt(three[0]) && storyLevel<Integer.parseInt(three[1]))
    				supplyVending.setStoryLevel(Constant.storyLevel_three);//库存等级
    			else
    				supplyVending.setStoryLevel(Constant.storyLevel_frist);//库存等级
    			supplyVendingMapper.insertSupplyVending(supplyVending);
        	}
		}
        for(SupplyVending supplyVending:supplyVendingList){
        	//判断该补货站点是否还处于当前线路下
        	boolean flag=false;
        	for (int i = 0; i < siteIds.length; i++) {
				if(siteIds[i]!=null&&siteIds[i].equals(supplyVending.getSiteId())) {
					flag=true;
					break;
				}
			}
        	//该补货站点不在当前线路下，删除改调记录
        	if(!flag) {
        		supplyVendingMapper.deleteSupplyVendingBySiteId(supplyVending.getSiteId());
        		break;
        	}
			supplyVending.setFristlevel(supplyConfigR.getFristlevel());
			supplyVending.setTwoLevel(supplyConfigR.getTwolevel());
			supplyVending.setThreeLevel(supplyConfigR.getThreelevel());
			if(supplyVending.getCurPNum()==null) {
				supplyVending.setCurPNum(0);
			}
			if(supplyVending.getMaxPNum()==null) {
				supplyVending.setMaxPNum(0);
			}
			int storyLevel=(int)((float)supplyVending.getCurPNum()/(float)supplyVending.getMaxPNum())*100;
			if(storyLevel>=Integer.parseInt(frist[0]) && storyLevel<=Integer.parseInt(frist[1]))
				supplyVending.setStoryLevel(Constant.storyLevel_frist);//库存等级
			else if(storyLevel>=Integer.parseInt(two[0]) && storyLevel<=Integer.parseInt(two[1]))
				supplyVending.setStoryLevel(Constant.storyLevel_two);//库存等级
			else if(storyLevel>=Integer.parseInt(three[0]) && storyLevel<=Integer.parseInt(three[1]))
				supplyVending.setStoryLevel(Constant.storyLevel_three);//库存等级
			else
				supplyVending.setStoryLevel(Constant.storyLevel_frist);//库存等级
			this.supplyVendingMapper.updateSupplyVending(supplyVending);
		}
		return supplyConfigMapper.updateSupplyConfig(supplyConfigR);
	}

	/**
     * 删除商品补货配置，此补货是按线路补货，一条线路可拥有多个补货配置，一个补货配置可自由删减补货点位。对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	@Transactional
	public int deleteSupplyConfigByIds(String ids)
	{
		//批量删除补货站点
		supplyVendingMapper.deleteSupplyVendingBySupplyIds(Convert.toStrArray(ids));
		return supplyConfigMapper.deleteSupplyConfigByIds(Convert.toStrArray(ids));
	}

	@Override
	public SupplyConfig selectSupplyConfigBySupplyId(String supplyId) {
		return supplyConfigMapper.selectSupplyConfigBySupplyId(supplyId);
	}

}
