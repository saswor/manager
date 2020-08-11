package com.manage.project.system.vendingPoint.service;

import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import java.util.List;

/**
 * 管理线路的区域 服务层
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public interface IVendingDistrictService 
{
	/**
     * 查询管理线路的区域信息
     * 
     * @param districtId 管理线路的区域ID
     * @return 管理线路的区域信息
     */
	public VendingDistrict selectVendingDistrictById(String districtId);
	
	/**
     * 查询管理线路的区域列表
     * 
     * @param vendingDistrict 管理线路的区域信息
     * @return 管理线路的区域集合
     */
	public List<VendingDistrict> selectVendingDistrictList(VendingDistrict vendingDistrict);
	
	/**
     * 新增管理线路的区域
     * 
     * @param vendingDistrict 管理线路的区域信息
     * @return 结果
     */
	public int insertVendingDistrict(VendingDistrict vendingDistrict);
	
	/**
     * 修改管理线路的区域
     * 
     * @param vendingDistrict 管理线路的区域信息
     * @return 结果
     */
	public int updateVendingDistrict(VendingDistrict vendingDistrict);
		
	/**
     * 删除管理线路的区域信息
     * 
     * @param districtIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteVendingDistrictByIds(String districtIds);

	/**
	 * 根据编号数组查询区域
	 * 
	 * @param idArr
	 * @return
	 */
	public List<VendingDistrict> selectVendingDistrictByIds(String[] idArr);

	/**
	 * 保存区域信息
	 * 
	 * @param vendingDistrict
	 * @return
	 */
	public AjaxResult saveImportVendingDistrict(VendingDistrict vendingDistrict);
	
}
