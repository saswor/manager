package com.manage.project.system.advert.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.spring.SpringUtils;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.domain.BaseEntity;
import com.manage.project.common.Constant;
import com.manage.project.system.util.SystemUtil;

/**
 * 广告素材媒体表 as_advert_material
 * 
 * @author xufeng
 * @date 2018-08-31
 */
public class AdvertMaterial extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 记录编号 */
	private String logid;
	/** 素材编号 */
	private String materialId;
	/** 托管公司编号 */
	private String corpId;
	/** 素材名称 */
	private String mediaName;
	/** 素材云端HTTP地址 */
	private String mediaUrl;
	/** 素材云端HTTP缩略图地址 */
	private String mediaSUrl;
	/** 素材文件类型 1:视频 2:图片 3:文本 */
	private String mediaType;
	/** 素材正常显示分辨率 */
	private String mediaPX;
	/** 素材大小(M) 最大支持500M */
	private Float mediaSize;
	/** 播放平台 01:柜子设备端 02:微信公众号 03:售卖app */
	private String playerPlat;
	
	private String mediaTypeName;
	
	public String getMediaFileSize() {
		if(mediaSize==null) {
			return "0";
		}else {
			int count=0;
			float size=mediaSize;
			while(size>103){
				size/=1024;
				count++;
			}
			float fileSize = new BigDecimal(size).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
			return String.valueOf(fileSize)+Constant.FILE_SIZE_UNIT.get(count);
		}
	}

	
	public String getMediaTypeName() {
		return SystemUtil.parseMediaType(mediaType);
	}

	public void setLogid(String logid) 
	{
		this.logid = logid;
	}

	public String getLogid() 
	{
		return logid;
	}
	public void setMaterialId(String materialId) 
	{
		this.materialId = materialId;
	}

	public String getMaterialId() 
	{
		return materialId;
	}
	public void setCorpId(String corpId) 
	{
		this.corpId = corpId;
	}

	public String getCorpId() 
	{
		return corpId;
	}
	public void setMediaName(String mediaName) 
	{
		this.mediaName = mediaName;
	}

	public String getMediaName() 
	{
		return mediaName;
	}
	public void setMediaUrl(String mediaUrl) 
	{
		this.mediaUrl = mediaUrl;
	}

	public String getMediaUrl() 
	{
		return mediaUrl;
	}
	public void setMediaSUrl(String mediaSUrl) 
	{
		this.mediaSUrl = mediaSUrl;
	}

	public String getMediaSUrl() 
	{
		return mediaSUrl;
	}
	public void setMediaType(String mediaType) 
	{
		this.mediaType = mediaType;
	}

	public String getMediaType() 
	{
		return mediaType;
	}
	public void setMediaPX(String mediaPX) 
	{
		this.mediaPX = mediaPX;
	}

	public String getMediaPX() 
	{
		return mediaPX;
	}
	public void setMediaSize(Float mediaSize) 
	{
		this.mediaSize = mediaSize;
	}

	public Float getMediaSize() 
	{
		return mediaSize;
	}
	public void setPlayerPlat(String playerPlat) 
	{
		this.playerPlat = playerPlat;
	}

	public String getPlayerPlat() 
	{
		return playerPlat;
	}
	
//	/**
//	 * 用于获取url路径
//	 * 
//	 * @return
//	 */
//	public String getUrl() 
//	{
//		ManageConfig manageConfig = SpringUtils.getBean(ManageConfig.class);
//		if(StringUtils.isEmpty(mediaUrl)) {
//			return "";
//		}
//		if(Constant.MEDIA_TYPE_PICTURE.equals(mediaType)) {
//			try {
//				JSONArray jsonArray = JSONObject.parseArray(mediaUrl);
//				return manageConfig.getIp()+manageConfig.getImgProfile()+"advert/"+jsonArray.getJSONObject(0).getString("pic");
//			}catch (Exception e) {
//				return "";
//			}
//			
//		}else if(Constant.MEDIA_TYPE_VIDEO.equals(mediaType)) {
//			return manageConfig.getIp()+"/"+manageConfig.getUploadPath()+"video/"+mediaUrl;
//		}else {
//			return manageConfig.getIp()+"/"+manageConfig.getUploadPath()+"text/"+mediaUrl;
//		}
//	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logid", getLogid())
            .append("materialId", getMaterialId())
            .append("corpId", getCorpId())
            .append("mediaName", getMediaName())
            .append("mediaUrl", getMediaUrl())
            .append("mediaSUrl", getMediaSUrl())
            .append("mediaType", getMediaType())
            .append("mediaPX", getMediaPX())
            .append("mediaSize", getMediaSize())
            .append("playerPlat", getPlayerPlat())
            .append("createTime", getCreateTime())
            .toString();
    }
}
