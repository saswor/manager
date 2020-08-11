package com.manage.project.system.vending.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vending.mapper.VendingModelMapper;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.domain.VendingModel;
import com.manage.project.system.vending.service.IVendingModelService;
import com.manage.common.support.Convert;
import com.manage.common.utils.security.ShiroUtils;

/**
 * 售货机机型管理，包括主柜和副柜机型 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingModelServiceImpl implements IVendingModelService 
{
	@Autowired
	private VendingModelMapper vendingModelMapper;
	
	@Autowired
	private BussinessCacheService bussinessCacheService;

	/**
     * 查询售货机机型管理，包括主柜和副柜机型信息
     * 
     * @param logid 售货机机型管理，包括主柜和副柜机型ID
     * @return 售货机机型管理，包括主柜和副柜机型信息
     */
    @Override
	public VendingModel selectVendingModelById(String deviceCode)
	{
	    return vendingModelMapper.selectVendingModelById(deviceCode);
	}
	
	/**
     * 查询售货机机型管理，包括主柜和副柜机型列表
     * 
     * @param vendingModel 售货机机型管理，包括主柜和副柜机型信息
     * @return 售货机机型管理，包括主柜和副柜机型集合
     */
	@Override
	public List<VendingModel> selectVendingModelList(VendingModel vendingModel)
	{
	    return vendingModelMapper.selectVendingModelList(vendingModel);
	}
	
    /**
     * 新增售货机机型管理，包括主柜和副柜机型
     * 
     * @param vendingModel 售货机机型管理，包括主柜和副柜机型信息
     * @return 结果
     */
	@Override
	public int insertVendingModel(VendingModel vendingModel)
	{
		vendingModel.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getUser().getCorpId();
		vendingModel.setCorpId(corpId);
		String deviceCode = SystemUtil.getSeqId(corpId, "as_vending_model");
		vendingModel.setStartId(1);
		vendingModel.setDeviceCode(deviceCode);
		vendingModel.setArrange(1);
		vendingModel.setRowNum(vendingModel.getRow());
	    int r = vendingModelMapper.insertVendingModel(vendingModel);
	    // 更新缓存
	    bussinessCacheService.initVendingModel();
		return r;
	}
	
	/**
     * 修改售货机机型管理，包括主柜和副柜机型
     * 
     * @param vendingModel 售货机机型管理，包括主柜和副柜机型信息
     * @return 结果
     */
	@Override
	public int updateVendingModel(VendingModel vendingModel)
	{
	    int r = vendingModelMapper.updateVendingModel(vendingModel);
	    bussinessCacheService.initVendingModel();
		return r;
	}

	/**
     * 删除售货机机型管理，包括主柜和副柜机型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingModelByIds(String ids)
	{
		int r = vendingModelMapper.deleteVendingModelByIds(Convert.toStrArray(ids));
		bussinessCacheService.initVendingModel();
		return r;
	}
	
}
