package com.manage.project.system.advert.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.advert.mapper.FavourableTimeMapper;
import com.manage.project.system.advert.domain.FavourableTime;
import com.manage.project.system.advert.service.IFavourableTimeService;
import com.manage.common.support.Convert;

/**
 * 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class FavourableTimeServiceImpl implements IFavourableTimeService 
{
	@Autowired
	private FavourableTimeMapper favourableTimeMapper;

	/**
     * 查询优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * 
     * @param logid 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。ID
     * @return 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     */
    @Override
	public FavourableTime selectFavourableTimeById(String logid)
	{
	    return favourableTimeMapper.selectFavourableTimeById(logid);
	}
	
	/**
     * 查询优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。列表
     * 
     * @param favourableTime 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * @return 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。集合
     */
	@Override
	public List<FavourableTime> selectFavourableTimeList(FavourableTime favourableTime)
	{
	    return favourableTimeMapper.selectFavourableTimeList(favourableTime);
	}
	
    /**
     * 新增优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。
     * 
     * @param favourableTime 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * @return 结果
     */
	@Override
	public int insertFavourableTime(FavourableTime favourableTime)
	{
	    return favourableTimeMapper.insertFavourableTime(favourableTime);
	}
	
	/**
     * 修改优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。
     * 
     * @param favourableTime 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * @return 结果
     */
	@Override
	public int updateFavourableTime(FavourableTime favourableTime)
	{
	    return favourableTimeMapper.updateFavourableTime(favourableTime);
	}

	/**
     * 删除优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteFavourableTimeByIds(String ids)
	{
		return favourableTimeMapper.deleteFavourableTimeByIds(Convert.toStrArray(ids));
	}
	
}
