package com.manage.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.manage.project.common.ObjectPictureData;

/**
 * 读取图片相关配置
 * 
 */
@Component
@ConfigurationProperties(prefix = "pic")
public class PicConfig {

	private ObjectPictureData product;
	
	private ObjectPictureData advert;

	public ObjectPictureData getProduct() {
		return product;
	}

	public void setProduct(ObjectPictureData product) {
		this.product = product;
	}

	public ObjectPictureData getAdvert() {
		return advert;
	}

	public void setAdvert(ObjectPictureData advert) {
		this.advert = advert;
	}
	
}
