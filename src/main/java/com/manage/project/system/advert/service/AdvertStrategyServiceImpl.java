package com.manage.project.system.advert.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.advert.mapper.AdvertStrategyMapper;
import com.manage.project.system.advert.domain.AdvertStrategy;
import com.manage.project.system.advert.service.IAdvertStrategyService;
import com.manage.common.support.Convert;

/**
 * 广告配置 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class AdvertStrategyServiceImpl implements IAdvertStrategyService 
{
	@Autowired
	private AdvertStrategyMapper advertStrategyMapper;

	/**
     * 查询广告配置信息
     * 
     * @param logid 广告配置ID
     * @return 广告配置信息
     */
    @Override
	public AdvertStrategy selectAdvertStrategyById(String logid)
	{
	    return advertStrategyMapper.selectAdvertStrategyById(logid);
	}
	
	/**
     * 查询广告配置列表
     * 
     * @param advertStrategy 广告配置信息
     * @return 广告配置集合
     */
	@Override
	public List<AdvertStrategy> selectAdvertStrategyList(AdvertStrategy advertStrategy)
	{
	    return advertStrategyMapper.selectAdvertStrategyList(advertStrategy);
	}
	
    /**
     * 新增广告配置
     * 
     * @param advertStrategy 广告配置信息
     * @return 结果
     */
	@Override
	public int insertAdvertStrategy(AdvertStrategy advertStrategy)
	{
	    return advertStrategyMapper.insertAdvertStrategy(advertStrategy);
	}
	
	/**
     * 修改广告配置
     * 
     * @param advertStrategy 广告配置信息
     * @return 结果
     */
	@Override
	public int updateAdvertStrategy(AdvertStrategy advertStrategy)
	{
	    return advertStrategyMapper.updateAdvertStrategy(advertStrategy);
	}

	/**
     * 删除广告配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAdvertStrategyByIds(String ids)
	{
		return advertStrategyMapper.deleteAdvertStrategyByIds(Convert.toStrArray(ids));
	}

	@Override
	public List<AdvertStrategy> selectAdvertStrategyByAdvertId(String advertId) {
		return advertStrategyMapper.selectAdvertStrategyByAdvertId(advertId);
	}
	
}
