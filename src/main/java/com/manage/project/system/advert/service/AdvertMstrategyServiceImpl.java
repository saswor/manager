package com.manage.project.system.advert.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.advert.mapper.AdvertMstrategyMapper;
import com.manage.project.system.advert.domain.AdvertMstrategy;
import com.manage.project.system.advert.service.IAdvertMstrategyService;
import com.manage.common.support.Convert;

/**
 * 广告配置 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class AdvertMstrategyServiceImpl implements IAdvertMstrategyService 
{
	@Autowired
	private AdvertMstrategyMapper advertMstrategyMapper;

	/**
     * 查询广告配置信息
     * 
     * @param logid 广告配置ID
     * @return 广告配置信息
     */
    @Override
	public AdvertMstrategy selectAdvertMstrategyById(String logid)
	{
	    return advertMstrategyMapper.selectAdvertMstrategyById(logid);
	}
	
	/**
     * 查询广告配置列表
     * 
     * @param advertMstrategy 广告配置信息
     * @return 广告配置集合
     */
	@Override
	public List<AdvertMstrategy> selectAdvertMstrategyList(AdvertMstrategy advertMstrategy)
	{
	    return advertMstrategyMapper.selectAdvertMstrategyList(advertMstrategy);
	}
	
    /**
     * 新增广告配置
     * 
     * @param advertMstrategy 广告配置信息
     * @return 结果
     */
	@Override
	public int insertAdvertMstrategy(AdvertMstrategy advertMstrategy)
	{
	    return advertMstrategyMapper.insertAdvertMstrategy(advertMstrategy);
	}
	
	/**
     * 修改广告配置
     * 
     * @param advertMstrategy 广告配置信息
     * @return 结果
     */
	@Override
	public int updateAdvertMstrategy(AdvertMstrategy advertMstrategy)
	{
	    return advertMstrategyMapper.updateAdvertMstrategy(advertMstrategy);
	}

	/**
     * 删除广告配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAdvertMstrategyByIds(String ids)
	{
		return advertMstrategyMapper.deleteAdvertMstrategyByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<AdvertMstrategy> selectAdvertMstrategyByStrategyId(String strategyId) {
		return advertMstrategyMapper.selectAdvertMstrategyByStrategyId(strategyId);
	}
	
}
