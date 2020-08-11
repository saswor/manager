package com.manage.project.system.advert.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.project.common.Constant;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.advert.domain.AdvertMaterial;
import com.manage.project.system.advert.service.IAdvertMaterialService;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 广告素材媒体 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/advertMaterial")
public class AdvertMaterialController extends BaseController
{
	@Autowired
	private IAdvertMaterialService advertMaterialService;
	
	@Autowired
	private ManageConfig manageConfig;
	
	private Logger log = LoggerFactory.getLogger(AdvertMaterialController.class);
	
	/**
	 * 查询广告素材媒体列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(AdvertMaterial advertMaterial)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			advertMaterial.setCorpId("");
		} else {
			advertMaterial.setCorpId(ShiroUtils.getUser().getCorpId());
		}
		advertMaterial.setCorpId(SystemUtil.getCorpId());
        List<AdvertMaterial> list = advertMaterialService.selectAdvertMaterialList(advertMaterial);
        for (AdvertMaterial material : list) {
        	//文本和视频类型统一返回一个地址
			if(Constant.MEDIA_TYPE_TEXT.equals(material.getMediaType())) {
				material.setMediaUrl(manageConfig.getIp()+Constant.TEXT_ICON_ADDRESS);
			}
			if(Constant.MEDIA_TYPE_VIDEO.equals(material.getMediaType())) {
				material.setMediaUrl(manageConfig.getIp()+Constant.VIDEO_ICON_ADDRESS);
			}
		}
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 新增保存广告素材媒体
	 */
	@Log(title = "广告素材媒体", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody AdvertMaterial advertMaterial)
	{		
		try {
			int result = advertMaterialService.insertAdvertMaterial(advertMaterial);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("素材已经下发,无法修改");
			}else if(result==3) {
				return AjaxResult.error("素材名不能为空");
			}else if(result==4) {
				return AjaxResult.error("素材名已存在");
			}else if(result==5) {
				return AjaxResult.error("素材类型不能为空");
			}else if(result==6) {
				return AjaxResult.error("上传的文件不存在");
			}else if(result==7) {
				return AjaxResult.error("上传的图片格式错误,当前支持png,jpg,jpeg格式");
			}else if(result==8) {
				return AjaxResult.error("保存视频格式错误,当前支持mp4,wmv格式");
			}else if(result==9) {
				return AjaxResult.error("上传的文件不存在");
			}else if(result==10) {
				return AjaxResult.error("保存文件失败");
			}else if(result==11) {
				return AjaxResult.error("保存文本格式错误,当前支持txt格式");
			}else {
				return AjaxResult.error("添加失败");
			}
		}catch (Exception e) {
			log.error("添加广告素材失败:"+JSONObject.toJSONString(advertMaterial),e);
			return AjaxResult.error("添加失败");
		}
		
	}
	
	/**
	 * 修改保存广告素材媒体
	 */
	@Log(title = "广告素材媒体", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody AdvertMaterial advertMaterial)
	{	
		try {
			int result = advertMaterialService.updateAdvertMaterial(advertMaterial);
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("素材已经下发,无法修改");
			}else if(result==3) {
				return AjaxResult.error("素材名不能为空");
			}else if(result==4) {
				return AjaxResult.error("素材名已存在");
			}else if(result==5) {
				return AjaxResult.error("素材类型不能为空");
			}else if(result==6) {
				return AjaxResult.error("上传的文件不存在");
			}else if(result==7) {
				return AjaxResult.error("上传的图片格式错误,当前支持png,jpg,jpeg格式");
			}else if(result==8) {
				return AjaxResult.error("保存视频格式错误,当前支持mp4,wmv格式");
			}else if(result==9) {
				return AjaxResult.error("上传的文件不存在");
			}else if(result==10) {
				return AjaxResult.error("保存文件失败");
			}else if(result==11) {
				return AjaxResult.error("保存文本格式错误,当前支持txt格式");
			}else {
				return AjaxResult.error("修改失败");
			}
		}catch (Exception e) {
			log.error("修改广告素材失败:"+JSONObject.toJSONString(advertMaterial),e);
			return AjaxResult.error("修改失败");
		}
	}
	
	/**
	 * 删除广告素材媒体
	 */
	@Log(title = "广告素材媒体", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{		
		try {
			int result = advertMaterialService.deleteAdvertMaterialByIds(ids.getIds());
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("请至少选中一行");
			}else if(result==3) {
				return AjaxResult.error("素材已经下发,无法删除");
			}else {
				return AjaxResult.error("删除失败");
			}
		}catch (Exception e) {
			log.error("删除广告素材失败:",e);
			return AjaxResult.error("删除失败");
		}
	}
	
}
