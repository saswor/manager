package com.manage.project.system.vending.mapper;

import com.manage.project.system.vending.domain.VendingModel;
import java.util.List;	

/**
 * 售货机机型管理，包括主柜和副柜机型 数据层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface VendingModelMapper 
{
	/**
     * 查询售货机机型管理，包括主柜和副柜机型信息
     * 
     * @param deviceId 售货机机型管理，包括主柜和副柜机型ID
     * @return 售货机机型管理，包括主柜和副柜机型信息
     */
	public VendingModel selectVendingModelById(String deviceId);
	
	/**
     * 查询售货机机型管理，包括主柜和副柜机型列表
     * 
     * @param vendingModel 售货机机型管理，包括主柜和副柜机型信息
     * @return 售货机机型管理，包括主柜和副柜机型集合
     */
	public List<VendingModel> selectVendingModelList(VendingModel vendingModel);
	
	/**
     * 新增售货机机型管理，包括主柜和副柜机型
     * 
     * @param vendingModel 售货机机型管理，包括主柜和副柜机型信息
     * @return 结果
     */
	public int insertVendingModel(VendingModel vendingModel);
	
	/**
     * 修改售货机机型管理，包括主柜和副柜机型
     * 
     * @param vendingModel 售货机机型管理，包括主柜和副柜机型信息
     * @return 结果
     */
	public int updateVendingModel(VendingModel vendingModel);
	
	/**
     * 删除售货机机型管理，包括主柜和副柜机型
     * 
     * @param logid 售货机机型管理，包括主柜和副柜机型ID
     * @return 结果
     */
	public int deleteVendingModelById(String logid);
	
	/**
     * 批量删除售货机机型管理，包括主柜和副柜机型
     * 
     * @param logids 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingModelByIds(String[] logids);
	
}