package com.manage.project.system.vendingPoint.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.vendingPoint.mapper.VendingDistrictMapper;
import com.manage.project.system.vendingPoint.mapper.VendingLineMapper;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.project.system.vendingPoint.service.IVendingLineService;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 点位的线路 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class VendingLineServiceImpl implements IVendingLineService 
{
	@Autowired
	private VendingLineMapper vendingLineMapper;
	
	@Autowired
	private VendingDistrictMapper vendingDistrictMapper;
	
	@Autowired
	private BussinessCacheService bussinessCacheService;

	/**
     * 查询点位的线路信息
     * 
     * @param lineId 点位的线路ID
     * @return 点位的线路信息
     */
    @Override
	public VendingLine selectVendingLineById(String lineId)
	{
	    return vendingLineMapper.selectVendingLineById(lineId);
	}
	
	/**
     * 查询点位的线路列表
     * 
     * @param vendingLine 点位的线路信息
     * @return 点位的线路集合
     */
	@Override
	public List<VendingLine> selectVendingLineList(VendingLine vendingLine)
	{
	    return vendingLineMapper.selectVendingLineList(vendingLine);
	}
	
    /**
     * 新增点位的线路
     * 
     * @param vendingLine 点位的线路信息
     * @return 结果
     */
	@Override
	public int insertVendingLine(VendingLine vendingLine)
	{
		vendingLine.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getUser().getCorpId();
		vendingLine.setCorpId(corpId);
		vendingLine.setLineId(SystemUtil.getSeqId(corpId, "as_vending_line"));
	    int r = vendingLineMapper.insertVendingLine(vendingLine);
	    bussinessCacheService.initVendingLine();
		return r;
	}
	
	/**
     * 修改点位的线路
     * 
     * @param vendingLine 点位的线路信息
     * @return 结果
     */
	@Override
	public int updateVendingLine(VendingLine vendingLine)
	{
	    int r = vendingLineMapper.updateVendingLine(vendingLine);
	    bussinessCacheService.initVendingLine();
		return r;
	}

	/**
     * 删除点位的线路对象
     * 
     * @param lineIds 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteVendingLineByIds(String lineIds)
	{
		int r = vendingLineMapper.deleteVendingLineByIds(Convert.toStrArray(lineIds));
		bussinessCacheService.initVendingLine();
		return r;
	}

	@Override
	public VendingLine selectVendingLineByLineId(String lineId) {
		return vendingLineMapper.selectVendingLineByLineId(lineId);
	}

	/**
	 * 保存线路信息
	 * 
	 * @param vendingLine
	 * @return
	 */
	@Override
	public AjaxResult saveImportVendingLine(VendingLine vendingLine) {
		AjaxResult result = checkVendingLine(vendingLine);
		if(!result.isSuccess()) {
			return result;
		}
		vendingLine.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getCorpId();
		vendingLine.setLineId(SystemUtil.getSeqId(corpId, "as_vending_line"));
		vendingLine.setCorpId(corpId);
		vendingLine.setCreateTime(DateUtils.getTime());
		vendingLineMapper.insertVendingLine(vendingLine);
		return AjaxResult.success();
	}
	
	private AjaxResult checkVendingLine(VendingLine vendingLine) {
		String name = vendingLine.getName();
		String code = vendingLine.getCode();
		String districtId = vendingLine.getDistrictId();
		String description = vendingLine.getDescription();
		if(StringUtils.isEmpty(name)) {
			return AjaxResult.error("线路名称不能为空");
		}
		if(name.length()>50) {
			return AjaxResult.error("线路名称超过50个字符");
		}
		VendingLine vendingLineName = new VendingLine();
		vendingLineName.setName(name);
		vendingLineName.setCorpId(ShiroUtils.getCorpId());
		VendingLine nameExist = vendingLineMapper.selectVendingLineExist(vendingLine);
		if(nameExist!=null) {
			return AjaxResult.error("线路名称已存在");
		}
		
		if(StringUtils.isEmpty(code)) {
			return AjaxResult.error("线路编码不能为空");
		}
		if(code.length()>30) {
			return AjaxResult.error("线路名称超过30个字符");
		}
		VendingLine vendingLineCode = new VendingLine();
		vendingLineCode.setName(code);
		vendingLineCode.setCorpId(ShiroUtils.getCorpId());
		VendingLine codeExist = vendingLineMapper.selectVendingLineExist(vendingLineCode);
		if(codeExist!=null) {
			return AjaxResult.error("编码已存在");
		}
		if(StringUtils.isEmpty(districtId)) {
			return AjaxResult.error("区域不能为空");
		}
		String[] districtArr = districtId.split("--");
		String district=districtArr[districtArr.length-1];
		VendingDistrict vendingDistrict = vendingDistrictMapper.selectVendingDistrictById(district);
		if(vendingDistrict==null) {
			return AjaxResult.error("区域不存在");
		}
		vendingLine.setDistrictId(district);
		if(description!=null&&description.length()>150) {
			return AjaxResult.error("描述超过150个字符");
		}
		return AjaxResult.success();
	}
	
}

