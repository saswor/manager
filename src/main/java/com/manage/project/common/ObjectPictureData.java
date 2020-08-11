package com.manage.project.common;

/**
 * 对象平台信息
 * 
 * @author zhangjiawei
 *
 */
public class ObjectPictureData {
	
	/**管理台图片*/
	private PictureData management;
	/**大屏售卖机图片*/
	private PictureData siteGreat;
	/**小屏售卖机图片*/
	private PictureData siteLittle;
	/**微信公众号图片*/
	private PictureData wechatPublic;
	/**补货小程序图片*/
	private PictureData supply;
	
	public PictureData getManagement() {
		return management;
	}
	public void setManagement(PictureData management) {
		this.management = management;
	}
	public PictureData getSiteGreat() {
		return siteGreat;
	}
	public void setSiteGreat(PictureData siteGreat) {
		this.siteGreat = siteGreat;
	}
	public PictureData getSiteLittle() {
		return siteLittle;
	}
	public void setSiteLittle(PictureData siteLittle) {
		this.siteLittle = siteLittle;
	}
	public PictureData getWechatPublic() {
		return wechatPublic;
	}
	public void setWechatPublic(PictureData wechatPublic) {
		this.wechatPublic = wechatPublic;
	}
	public PictureData getSupply() {
		return supply;
	}
	public void setSupply(PictureData supply) {
		this.supply = supply;
	}
	
}
