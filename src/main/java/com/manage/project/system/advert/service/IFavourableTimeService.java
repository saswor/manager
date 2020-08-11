package com.manage.project.system.advert.service;

import com.manage.project.system.advert.domain.FavourableTime;
import java.util.List;

/**
 * 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IFavourableTimeService 
{
	/**
     * 查询优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * 
     * @param logid 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。ID
     * @return 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     */
	public FavourableTime selectFavourableTimeById(String logid);
	
	/**
     * 查询优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。列表
     * 
     * @param favourableTime 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * @return 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。集合
     */
	public List<FavourableTime> selectFavourableTimeList(FavourableTime favourableTime);
	
	/**
     * 新增优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。
     * 
     * @param favourableTime 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * @return 结果
     */
	public int insertFavourableTime(FavourableTime favourableTime);
	
	/**
     * 修改优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。
     * 
     * @param favourableTime 优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * @return 结果
     */
	public int updateFavourableTime(FavourableTime favourableTime);
		
	/**
     * 删除优惠售货机列，不管是统一优惠还是分时段优惠都会生成优惠时间段，统一优惠只会有一条记录，分时段可能包含多条记录。信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFavourableTimeByIds(String ids);
	
}
