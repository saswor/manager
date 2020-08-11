package com.manage.project.system.base.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.base.mapper.DispatchMapper;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.base.service.IDispatchService;
import com.manage.common.support.Convert;

/**
 * 区域目录树管理，每个公司拥有自己的区域。 服务层实现
 * 
 * @author xufeng
 * @date 2018-09-03
 */
@Service
public class DispatchServiceImpl implements IDispatchService 
{
	@Autowired
	private DispatchMapper dispatchMapper;

	/**
     * 查询区域目录树管理，每个公司拥有自己的区域。信息
     * 
     * @param logid 区域目录树管理，每个公司拥有自己的区域。ID
     * @return 区域目录树管理，每个公司拥有自己的区域。信息
     */
    @Override
	public Dispatch selectDispatchById(String logid)
	{
	    return dispatchMapper.selectDispatchById(logid);
	}
	
	/**
     * 查询区域目录树管理，每个公司拥有自己的区域。列表
     * 
     * @param dispatch 区域目录树管理，每个公司拥有自己的区域。信息
     * @return 区域目录树管理，每个公司拥有自己的区域。集合
     */
	@Override
	public List<Dispatch> selectDispatchList(Dispatch dispatch)
	{
	    return dispatchMapper.selectDispatchList(dispatch);
	}
	
    /**
     * 新增区域目录树管理，每个公司拥有自己的区域。
     * 
     * @param dispatch 区域目录树管理，每个公司拥有自己的区域。信息
     * @return 结果
     */
	@Override
	public int insertDispatch(Dispatch dispatch)
	{
	    return dispatchMapper.insertDispatch(dispatch);
	}
	
	/**
     * 修改区域目录树管理，每个公司拥有自己的区域。
     * 
     * @param dispatch 区域目录树管理，每个公司拥有自己的区域。信息
     * @return 结果
     */
	@Override
	public int updateDispatch(Dispatch dispatch)
	{
	    return dispatchMapper.updateDispatch(dispatch);
	}

	/**
     * 删除区域目录树管理，每个公司拥有自己的区域。对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteDispatchByIds(String ids)
	{
		return dispatchMapper.deleteDispatchByIds(Convert.toStrArray(ids));
	}
	
	/**
     * 根据父id查询其子行政区划
     * 
     * @param parentid 父id
     * @return 区域目录树管理，每个公司拥有自己的区域。集合
     */
	public List<Dispatch> selectDispatchByParentId(String parentid) {
		return dispatchMapper.selectDispatchByParentId(parentid);
	}
}
