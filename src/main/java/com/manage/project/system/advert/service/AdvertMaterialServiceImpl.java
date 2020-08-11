package com.manage.project.system.advert.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.project.system.advert.mapper.AdvertConfigMapper;
import com.manage.project.system.advert.mapper.AdvertMaterialMapper;
import com.manage.project.system.advert.mapper.AdvertMstrategyMapper;
import com.manage.project.common.CommonController;
import com.manage.project.common.Constant;
import com.manage.project.system.advert.controller.AdvertMaterialController;
import com.manage.project.system.advert.domain.AdvertConfig;
import com.manage.project.system.advert.domain.AdvertMaterial;
import com.manage.project.system.advert.domain.AdvertMstrategy;
import com.manage.project.system.advert.service.IAdvertMaterialService;
import com.manage.project.system.util.SystemUtil;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.support.Convert;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.PicUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.config.ManageConfig;

/**
 * 广告素材媒体 服务层实现
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Service
public class AdvertMaterialServiceImpl implements IAdvertMaterialService 
{
	@Autowired
	private AdvertMaterialMapper advertMaterialMapper;
	
	@Autowired
	private PicUtils picUtils;
	
	@Autowired
	private ManageConfig manageConfig;
	
	@Autowired
	private AdvertMstrategyMapper advertMstrategyMapper;
	
	@Autowired
	private AdvertConfigMapper advertConfigMapper;
	
	private Logger log = LoggerFactory.getLogger(AdvertMaterialServiceImpl.class);

	/**
     * 查询广告素材媒体信息
     * 
     * @param logid 广告素材媒体ID
     * @return 广告素材媒体信息
     */
    @Override
	public AdvertMaterial selectAdvertMaterialById(String logid)
	{
	    return advertMaterialMapper.selectAdvertMaterialById(logid);
	}
	
	/**
     * 查询广告素材媒体列表
     * 
     * @param advertMaterial 广告素材媒体信息
     * @return 广告素材媒体集合
     */
	@Override
	public List<AdvertMaterial> selectAdvertMaterialList(AdvertMaterial advertMaterial)
	{
	    return advertMaterialMapper.selectAdvertMaterialList(advertMaterial);
	}
	
    /**
     * 新增广告素材媒体
     * 
     * @param advertMaterial 广告素材媒体信息
     * @return 结果
     */
	@Override
	public int insertAdvertMaterial(AdvertMaterial advertMaterial)
	{
		//数据校验
		int checkResult = checkAdvertMaterial(advertMaterial);
		if(checkResult!=1) {
			return checkResult;
		}
		advertMaterial.setLogid(UUID.randomUUID().toString());
		String corpId = ShiroUtils.getCorpId();
		advertMaterial.setCorpId(corpId);
		advertMaterial.setCreateTime(DateUtils.getTime());
		advertMaterial.setMediaSUrl(advertMaterial.getMediaUrl());
		advertMaterial.setMaterialId(SystemUtil.getSeqId(corpId, "as_advert_material"));
		//素材文件处理
		int fileResult = this.adFileHandle(advertMaterial);
		if(fileResult!=1) {
			return fileResult;
		}
	    advertMaterialMapper.insertAdvertMaterial(advertMaterial);
	    return 1;
	}
	
	/**
	 * 数据校验
	 * 
	 * @param advertMaterial
	 * @return
	 */
	private int checkAdvertMaterial(AdvertMaterial advertMaterial) {
		//素材是否已经下发
		if(StringUtils.isNotEmpty(advertMaterial.getMaterialId())) {
			AdvertMstrategy advertMstrategy = new AdvertMstrategy();
			advertMstrategy.setMaterialId(advertMaterial.getMaterialId());
			List<AdvertMstrategy> advertMstrategyList = advertMstrategyMapper.selectAdvertMstrategyList(advertMstrategy);
			if(advertMstrategyList!=null&&!advertMstrategyList.isEmpty()) {
				return 2;
			}
		}	
		String mediaName = advertMaterial.getMediaName();
		String mediaType = advertMaterial.getMediaType();
		String mediaUrl = advertMaterial.getMediaUrl();
		//素材名为空
		if(StringUtils.isEmpty(mediaName)) {
			return 3;
		}
		//素材名已存在
		advertMaterial.setCorpId(ShiroUtils.getCorpId());
		AdvertMaterial material = advertMaterialMapper.selectMediaNameIsExit(advertMaterial);
		if(material!=null) {
			return 4;
		}
		//素材类型为空
		if(StringUtils.isEmpty(mediaType)) {
			return 5;
		}
		//上传的文件不存在
		if(StringUtils.isEmpty(mediaUrl)) {
			return 6;
		}
		String suffix = mediaUrl.substring(mediaUrl.lastIndexOf(".") + 1);
		//图片格式校验
		if(Constant.MEDIA_TYPE_PICTURE.equals(mediaType)) {
			if(!"png".equalsIgnoreCase(suffix)&&!"jpg".equalsIgnoreCase(suffix)&&!"jpeg".equalsIgnoreCase(suffix)) {
				return 7;
			}
		}else if(Constant.MEDIA_TYPE_VIDEO.equals(mediaType)) {
			//视频格式校验
			if(!"mp4".equalsIgnoreCase(suffix)&&!"wmv".equalsIgnoreCase(suffix)) {
				return 8;
			}
		}else if(Constant.MEDIA_TYPE_TEXT.equals(mediaType)) {
			if(!"txt".equalsIgnoreCase(suffix)) {
				return 11;
			}
		}
		return 1;
	}
	
	/**
     * 修改广告素材媒体
     * 
     * @param advertMaterial 广告素材媒体信息
     * @return 结果
     */
	@Override
	public int updateAdvertMaterial(AdvertMaterial advertMaterial)
	{
		int checkResult = checkAdvertMaterial(advertMaterial);
		//数据校验
		if(checkResult!=1) {
			return checkResult;
		}
		advertMaterial.setMediaSUrl(advertMaterial.getMediaUrl());
		//素材文件处理
		int fileResult = this.adFileHandle(advertMaterial);
		if(fileResult!=1) {
			return fileResult;
		}
	    advertMaterialMapper.updateAdvertMaterial(advertMaterial);
	    return 1;
	}

	/**
     * 删除广告素材媒体对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAdvertMaterialByIds(String ids)
	{
		if(StringUtils.isEmpty(ids)) {
			return 2;
		}
		//查询所有要删除的记录
		List<AdvertMaterial> list = advertMaterialMapper.selectAdvertMaterialByIds(ids.split(","));
		//素材是否已经下发
		String[] materialIds = ids.split(",");
		for (String materialId : materialIds) {
			if(StringUtils.isNotEmpty(materialId)) {
				AdvertMstrategy advertMstrategy = new AdvertMstrategy();
				advertMstrategy.setMaterialId(materialId);
				List<AdvertMstrategy> advertMstrategyList = advertMstrategyMapper.selectAdvertMstrategyList(advertMstrategy);
				if(advertMstrategyList!=null&&!advertMstrategyList.isEmpty()) {
					//查询对应的广告是否是已删除状态
					Map<String, String> map = new HashMap<String,String>();
					for (AdvertMstrategy mstrategy : advertMstrategyList) {
						if(map.get(mstrategy.getAdvertId())==null) {
							map.put(mstrategy.getAdvertId(), mstrategy.getAdvertId());
						}
					}
					Set<String> keySet = map.keySet();
					String[] advertIds=new String[keySet.size()];
					advertIds = keySet.toArray(advertIds);
					List<AdvertConfig> advertConfigs = advertConfigMapper.selectNotDeleteAdvertByAdvertIds(advertIds);
					if(StringUtils.isNotEmpty(advertConfigs)) {
						return 3;
					}
				}
			}
		}	
		advertMaterialMapper.deleteAdvertMaterialByIds(Convert.toStrArray(ids));
		for (AdvertMaterial advertMaterial : list) {
			log.info("删除广告素材:"+JSONObject.toJSONString(advertMaterial)+",时间:"+DateUtils.getTime());
		}
		return 1;
	}
	
	/**
	 * 广告素材上传文件处理
	 * 
	 * @param advertMaterial
	 * @return
	 */
	public int adFileHandle(AdvertMaterial advertMaterial) {
		File file = new File(ManageConfig.getUploadPrefix()+advertMaterial.getMediaUrl());
		//文件不存在
		if(!file.exists()) {
			return 9;
		}
		String[] split = advertMaterial.getMediaUrl().split("\\.");
		String suffix=split[split.length-1];
		String destPath="";
		String destDir="";
		if(Constant.MEDIA_TYPE_VIDEO.equals(advertMaterial.getMediaType())) {
			destPath="front/video/advert/"+advertMaterial.getMaterialId()+"."+suffix;
			destDir=ManageConfig.getUploadPrefix()+"front/video/advert/";
		}else if(Constant.MEDIA_TYPE_TEXT.equals(advertMaterial.getMediaType())){
			destPath="front/text/advert/"+advertMaterial.getMaterialId()+"."+suffix;
			destDir=ManageConfig.getUploadPrefix()+"front/text/advert/";
		}else {
			destPath="front/img/advert/"+advertMaterial.getMaterialId()+"."+suffix;
			destDir=ManageConfig.getUploadPrefix()+"front/img/advert/";
		}
		//目录不存在的话创建文件夹
		File dir = new File(destDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File destFile = new File(ManageConfig.getUploadPrefix()+destPath);
		if(destFile.exists()) {
			destFile.delete();
		}
		//文件保存失败
		if(!file.renameTo(destFile)) {
			return 10;
		}
		advertMaterial.setMediaUrl(manageConfig.getIp()+"/"+destPath);
		advertMaterial.setMediaSUrl(destPath);
		return 1;
	}
	
}
