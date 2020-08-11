package com.manage.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 */
@Component
@ConfigurationProperties(prefix = "manage")
public class ManageConfig
{
    /** 项目名称 */
    private String name;
    /** 版本 */
    private String version;
    /** 版权年份 */
    private String copyrightYear;
    /** 上传路径 */
    private static String profile;
    /** 获取地址开关 */
    private static boolean addressEnabled;
    /** 消息服务器地址 */
    private String messageServerUrl;
    
    public String getMessageServerUrl() {
		return messageServerUrl;
	}

	public void setMessageServerUrl(String messageServerUrl) {
		this.messageServerUrl = messageServerUrl;
	}

	private String ip;
    
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private String schema;
    /**
     * 图片路径
     */
    private String imgProfile;
    
    /**
     * 限制图片上传大小，单位kb
     */
    private String imgSize;
    /**
     * 售卖机xml文件访问地址
     */
    private String vendingXmlPath;
    /**生成excel地址*/
    private static String excelPath;
    
    /**生成excel url地址*/
    private static String excelUrl;
    
    /**上传源文件地址*/
    private static String srcFilePath;
    
    /**上传微信证书路径*/
    private String licenseFilePath;
    
    /**上传微信证书url*/
    private String licenseFileUrl;
    
    /**上传文件地址*/
    private String uploadPath;
    
    /**上传文件地址前缀*/
    private static String uploadPrefix;
     
    public static String getUploadPrefix() {
		return uploadPrefix;
	}

    private String aliNotifyUrl;
    
    private String wechatNotifyUrl;
    
    private String vendingNoticeUrl;
    
    private String payTestNotice;
    
    private String linkUrl;
    
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getPayTestNotice() {
		return payTestNotice;
	}

	public void setPayTestNotice(String payTestNotice) {
		this.payTestNotice = payTestNotice;
	}

	public String getVendingNoticeUrl() {
		return vendingNoticeUrl;
	}

	public void setUploadPrefix(String uploadPrefix) {
		this.uploadPrefix = uploadPrefix;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public void setVendingNoticeUrl(String vendingNoticeUrl) {
		this.vendingNoticeUrl = vendingNoticeUrl;
	}

	public String getWechatNotifyUrl() {
		return wechatNotifyUrl;
	}

	public void setWechatNotifyUrl(String wechatNotifyUrl) {
		this.wechatNotifyUrl = wechatNotifyUrl;
	}

	public String getAliNotifyUrl() {
		return aliNotifyUrl;
	}

	public void setAliNotifyUrl(String notifyUrl) {
		this.aliNotifyUrl = notifyUrl;
	}

	public String getLicenseFileUrl() {
		return licenseFileUrl;
	}

	public void setLicenseFileUrl(String licenseFileUrl) {
		this.licenseFileUrl = licenseFileUrl;
	}

	public static String getSrcFilePath() {
		return srcFilePath;
	}

	public void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}

	public String getLicenseFilePath() {
		return licenseFilePath;
	}

	public void setLicenseFilePath(String licenseFilePath) {
		this.licenseFilePath = licenseFilePath;
	}

	public static String getExcelUrl() {
		return excelUrl;
	}

	public void setExcelUrl(String excelUrl) {
		ManageConfig.excelUrl = excelUrl;
	}

	public static String getExcelPath() {
		return excelPath;
	}

	public void setExcelPath(String excelPath) {
		ManageConfig.excelPath = excelPath;
	}

	public String getVendingXmlPath() {
		return vendingXmlPath;
	}

	public void setVendingXmlPath(String vendingXmlPath) {
		this.vendingXmlPath = vendingXmlPath;
	}

	public String getImgSize() {
		return imgSize;
	}

	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}

	public String getImgProfile() {
		return imgProfile;
	}

	public void setImgProfile(String imgProfile) {
		this.imgProfile = imgProfile;
	}

	public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
    	ManageConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
    	ManageConfig.addressEnabled = addressEnabled;
    }

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

    
}
