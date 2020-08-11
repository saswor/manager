package com.manage.project.system.vending.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vending.mapper.VendingPlaneMapper;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingPlane;
import com.manage.project.system.vending.service.IVendingPlaneService;
import com.manage.common.support.Convert;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 售货机货道 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingPlaneServiceImpl implements IVendingPlaneService 
{
	@Autowired
	private VendingPlaneMapper vendingPlaneMapper;

	/**
     * 查询售货机货道信息
     * 
     * @param logid 售货机货道ID
     * @return 售货机货道信息
     */
    @Override
	public VendingPlane selectVendingPlaneById(String logid)
	{
	    return vendingPlaneMapper.selectVendingPlaneById(logid);
	}
	
	/**
     * 查询售货机货道列表
     * 
     * @param vendingPlane 售货机货道信息
     * @return 售货机货道集合
     */
	@Override
	public List<VendingPlane> selectVendingPlaneList(VendingPlane vendingPlane)
	{
	    return vendingPlaneMapper.selectVendingPlaneList(vendingPlane);
	}
	
    /**
     * 新增售货机货道
     * 
     * @param vendingPlane 售货机货道信息
     * @return 结果
     */
	@Override
	public int insertVendingPlane(VendingPlane vendingPlane)
	{
		vendingPlane.setCorpId(ShiroUtils.getUser().getCorpId());
		vendingPlane.setLogid(UUID.randomUUID().toString());
		vendingPlane.setMLaneId(SystemUtil.getSeqIdLong(vendingPlane.getCorpId(), "as_vending_plane"));
	    return vendingPlaneMapper.insertVendingPlane(vendingPlane);
	}
	
	/**
     * 修改售货机货道
     * 
     * @param vendingPlane 售货机货道信息
     * @return 结果
     */
	@Override
	public int updateVendingPlane(VendingPlane vendingPlane)
	{
	    return vendingPlaneMapper.updateVendingPlane(vendingPlane);
	}

	/**
     * 删除售货机货道对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingPlaneByIds(String ids)
	{
		return vendingPlaneMapper.deleteVendingPlaneByIds(Convert.toStrArray(ids));
	}

	@Override
	public int deleteVendingPlaneByConfigId(String ids) {
		return vendingPlaneMapper.deleteVendingPlaneByConfigId(Convert.toStrArray(ids));
	}
	
}
