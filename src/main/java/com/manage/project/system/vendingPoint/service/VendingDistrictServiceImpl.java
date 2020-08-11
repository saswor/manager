package com.manage.project.system.vendingPoint.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manage.project.system.vendingPoint.mapper.VendingDistrictMapper;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.service.IVendingDistrictService;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 管理线路的区域 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingDistrictServiceImpl implements IVendingDistrictService 
{
	@Autowired
	private VendingDistrictMapper vendingDistrictMapper;

	@Autowired
	private BussinessCacheService bussinessCacheService;
	/**
     * 查询管理线路的区域信息
     * 
     * @param districtId 管理线路的区域ID
     * @return 管理线路的区域信息
     */
    @Override
	public VendingDistrict selectVendingDistrictById(String districtId)
	{
	    return vendingDistrictMapper.selectVendingDistrictById(districtId);
	}
	
	/**
     * 查询管理线路的区域列表
     * 
     * @param vendingDistrict 管理线路的区域信息
     * @return 管理线路的区域集合
     */
	@Override
	public List<VendingDistrict> selectVendingDistrictList(VendingDistrict vendingDistrict)
	{
	    return vendingDistrictMapper.selectVendingDistrictList(vendingDistrict);
	}
	
    /**
     * 新增管理线路的区域
     * 
     * @param vendingDistrict 管理线路的区域信息
     * @return 结果
     */
	@Override
	public int insertVendingDistrict(VendingDistrict vendingDistrict)
	{
	    int r = vendingDistrictMapper.insertVendingDistrict(vendingDistrict);
	    bussinessCacheService.initVendingDistrict();
		return r;
	}
	
	/**
     * 修改管理线路的区域
     * 
     * @param vendingDistrict 管理线路的区域信息
     * @return 结果
     */
	@Override
	public int updateVendingDistrict(VendingDistrict vendingDistrict)
	{
	    int r = vendingDistrictMapper.updateVendingDistrict(vendingDistrict);
	    bussinessCacheService.initVendingDistrict();
		return r;
	}

	/**
     * 删除管理线路的区域对象
     * 
     * @param districtIds 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingDistrictByIds(String districtIds)
	{
		int r = vendingDistrictMapper.deleteVendingDistrictByIds(Convert.toStrArray(districtIds));
		bussinessCacheService.initVendingDistrict();
		return r;
	}

	/**
	 * 根据编号数组查询区域
	 * 
	 * @param idArr
	 * @return
	 */
	@Override
	public List<VendingDistrict> selectVendingDistrictByIds(String[] idArr) {
		return vendingDistrictMapper.selectVendingDistrictByIds(idArr);
	}

	/**
	 * 保存区域信息
	 * 
	 * @param vendingDistrict
	 * @return
	 */
	@Override
	public AjaxResult saveImportVendingDistrict(VendingDistrict vendingDistrict) {
		AjaxResult ajaxResult = checkVendingDistrict(vendingDistrict);
		if(!ajaxResult.isSuccess()) {
			return ajaxResult;
		}
		String corpId = ShiroUtils.getCorpId();
		vendingDistrict.setLogid(UUID.randomUUID().toString());
		vendingDistrict.setDistrictId(SystemUtil.getSeqId(corpId, "as_vending_district"));
		vendingDistrict.setCorpId(corpId);
		vendingDistrict.setCreateTime(DateUtils.getTime());
		vendingDistrictMapper.insertVendingDistrict(vendingDistrict);
		return AjaxResult.success();
	}
	
	/**
	 * 数据校验
	 * 
	 * @param vendingDistrict
	 */
	private AjaxResult checkVendingDistrict(VendingDistrict vendingDistrict) {
		String name = vendingDistrict.getName();
		String code = vendingDistrict.getCode();
		String manager = vendingDistrict.getManager();
		String mobile = vendingDistrict.getMobile();
		String description = vendingDistrict.getDescription();
		if(StringUtils.isEmpty(name)) {
			return AjaxResult.error("区域名称不能为空");
		}
		if(name.length()>50) {
			return AjaxResult.error("区域名称超过50个字符");
		}
		VendingDistrict vendingDistrictName = new VendingDistrict();
		vendingDistrictName.setName(name);
		vendingDistrictName.setCorpId(ShiroUtils.getCorpId());
		VendingDistrict nameExist = vendingDistrictMapper.selectVendingDistrictExist(vendingDistrict);
		if(nameExist!=null) {
			return AjaxResult.error("区域名称已存在");
		}
		
		if(StringUtils.isEmpty(code)) {
			return AjaxResult.error("区域编码不能为空");
		}
		if(code.length()>30) {
			return AjaxResult.error("区域名称超过30个字符");
		}
		VendingDistrict vendingDistrictCode = new VendingDistrict();
		vendingDistrictCode.setName(code);
		vendingDistrictCode.setCorpId(ShiroUtils.getCorpId());
		VendingDistrict codeExist = vendingDistrictMapper.selectVendingDistrictExist(vendingDistrictCode);
		if(codeExist!=null) {
			return AjaxResult.error("区域编码已存在");
		}
		if(manager!=null&&manager.length()>15) {
			return AjaxResult.error("负责人名称超过15个字符");
		}
		if(mobile!=null&&mobile.length()>15) {
			return AjaxResult.error("联系方式超过15个字符");
		}
//		if(mobile!=null) {
//			if(!mobile.matches("^[1][0-9]{10}$")) {
//				return AjaxResult.error("手机格式错误");
//			}
//		}
		if(description!=null&&description.length()>150) {
			return AjaxResult.error("描述超过150个字符");
		}
		return AjaxResult.success();
	}
	
}
