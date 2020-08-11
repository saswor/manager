package com.manage.project.system.base.service;

import com.manage.project.system.base.domain.Dispatch;
import java.util.List;

/**
 * 区域目录树管理，每个公司拥有自己的区域。 服务层
 * 
 * @author xufeng
 * @date 2018-09-03
 */
public interface IDispatchService 
{
	/**
     * 查询区域目录树管理，每个公司拥有自己的区域。信息
     * 
     * @param logid 区域目录树管理，每个公司拥有自己的区域。ID
     * @return 区域目录树管理，每个公司拥有自己的区域。信息
     */
	public Dispatch selectDispatchById(String logid);
	
	/**
     * 查询区域目录树管理，每个公司拥有自己的区域。列表
     * 
     * @param dispatch 区域目录树管理，每个公司拥有自己的区域。信息
     * @return 区域目录树管理，每个公司拥有自己的区域。集合
     */
	public List<Dispatch> selectDispatchList(Dispatch dispatch);
	
	/**
     * 新增区域目录树管理，每个公司拥有自己的区域。
     * 
     * @param dispatch 区域目录树管理，每个公司拥有自己的区域。信息
     * @return 结果
     */
	public int insertDispatch(Dispatch dispatch);
	
	/**
     * 修改区域目录树管理，每个公司拥有自己的区域。
     * 
     * @param dispatch 区域目录树管理，每个公司拥有自己的区域。信息
     * @return 结果
     */
	public int updateDispatch(Dispatch dispatch);
		
	/**
     * 删除区域目录树管理，每个公司拥有自己的区域。信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteDispatchByIds(String ids);
	
	/**
     * 根据父id查询其子行政区划
     * 
     * @param parentid 父id
     * @return 区域目录树管理，每个公司拥有自己的区域。集合
     */
	public List<Dispatch> selectDispatchByParentId(String parentid);
	
}
