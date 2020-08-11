package com.manage.common.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.config.PicConfig;
import com.manage.project.common.CommonController;
import com.manage.project.common.Constant;
import com.manage.project.common.ObjectPictureData;
import com.manage.project.common.PictureData;
import com.manage.project.system.product.vo.PicJsonVo;

/**
 * 图片工具类
 * @author zhangjiawei
 *
 */
@Component
public class PicUtils {
	
	@Autowired
	private ManageConfig manageConfig;
	
	@Autowired
	private PicConfig picConfig;

	/**
	 * 裁剪PNG图片工具类
	 * 
	 * @param fromFile
	 *            源文件
	 * @param toFile
	 *            裁剪后的文件
	 * @param outputWidth
	 *            裁剪宽度
	 * @param outputHeight
	 *            裁剪高度
	 * @param proportion
	 *            是否是等比缩放
	 * @throws Exception 
	 */
	public static void resizePng(File fromFile, File toFile, int outputWidth, int outputHeight,
			boolean proportion) throws Exception {
		try {
/*			int outputWidth = Integer.parseInt(width);
			int outputHeight = Integer.parseInt(height);*/
			BufferedImage bi2 = ImageIO.read(fromFile);
			int newWidth;
			int newHeight;
			// 判断是否是等比缩放
			if (proportion) {
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
				double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 < rate2 ? rate1 : rate2;
				newWidth = (int) (((double) bi2.getWidth(null)) / rate);
				newHeight = (int) (((double) bi2.getHeight(null)) / rate);
			} else {
				newWidth = outputWidth; // 输出的图片宽度
				newHeight = outputHeight; // 输出的图片高度
			}
			BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = to.createGraphics();
			to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,
					Transparency.TRANSLUCENT);
			g2d.dispose();
			g2d = to.createGraphics();
			@SuppressWarnings("static-access")
			Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();
			ImageIO.write(to, "png", toFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("生成图片失败");
		}
	}

	/**
	 * 商品图片重命名
	 * 
	 * @param os
	 * @param productId
	 * @param width
	 * @param height
	 * @param type
	 * @param suffix
	 * @return
	 */
	public static String productPicRename(String os,String objectId,int width,int height,String type,String suffix) {
		//String filePath = ManageConfig.getProfile();
		StringBuilder sb = new StringBuilder();
		sb.append(objectId).append("_").append(os).append("_")
		.append(width).append("X").append(height).append("_").append(type).append(suffix);
		return sb.toString();
	}
	
	/**
	 * 图片重命名
	 * 
	 * @param os 平台
	 * @param objectId 对象编号
	 * @param width 宽度
	 * @param height 高度
	 * @param type 类型
	 * @param suffix 后缀名
	 * @return
	 */
	public static String picRename(String os,String objectId,int width,int height,String type,String suffix) {
		//String filePath = ManageConfig.getProfile();
		StringBuilder sb = new StringBuilder();
		sb.append(objectId).append("_").append(os).append("_")
		.append(width).append("X").append(height).append("_").append(type).append(suffix);
		return sb.toString();
	}
	
	/**
	 * 商品上传原图重命名
	 * 
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public static PicJsonVo productSrcPicRename(String productId,String url) throws Exception {	
		File file = new File(ManageConfig.getProfile()+"product/"+url);
		BufferedImage img = ImageIO.read(file);
		int height = img.getHeight();
		int width = img.getWidth();
		String fileName = PicUtils.productPicRename("01", productId, width, height, "1", ".png");
		PicUtils.resizePng(file, new File(ManageConfig.getProfile()+"product/"+fileName), width, height, false);
		PicJsonVo vo = new PicJsonVo();
		vo.setOs("01");
		vo.setPic(fileName);
		vo.setPx(width+"X"+height);
		vo.setType("1");
		return vo;
	}
	
	/**
	 * 图像重命名
	 * 
	 * @param objectId 对象编号
	 * @param url 图片地址
	 * @param type 商品goods,广告advert
	 * @return
	 * @throws Exception
	 */
	public static PicJsonVo srcPicRename(String objectId,String url,String type) throws Exception {
		//获取上传的图片
		String urlPrefix="";
		File file = new File(ManageConfig.getSrcFilePath()+url);
		if(Constant.TYPE_PRODUCT.equals(type)) {
			urlPrefix="product/";
		}
		if(Constant.TYPE_ADVERT.equals(type)) {
			urlPrefix="advert/";	
		}
		BufferedImage img = ImageIO.read(file);
		int height = img.getHeight();
		int width = img.getWidth();
		String fileName = PicUtils.picRename("01", objectId, width, height, "1", ".png");
		PicUtils.resizePng(file, new File(ManageConfig.getProfile()+urlPrefix+fileName), width, height, false);
		PicJsonVo vo = new PicJsonVo();
		vo.setOs("01");
		vo.setPic(fileName);
		vo.setPx(width+"X"+height);
		vo.setType("1");
		return vo;
	}
	
	/**
	 * 图片处理
	 * 
	 * @param objectId 对象名称
	 * @param type 商品goods,广告advert
	 * @param pic 图片地址
	 * @return
	 */
	public String picHandler(String objectId,String type,String pic) {
		try {
			if(StringUtils.isEmpty(pic)) {
				return "";
			}
			String[] picSplit = pic.split("/");
			String picJson = picSplit[picSplit.length-1];
			List<PicJsonVo> picJsonList = new ArrayList<PicJsonVo>();
			//获取该类型图片的信息
			ObjectPictureData picData=null;
			String filePathSuffix = "";
			if(Constant.TYPE_PRODUCT.equals(type)) {
				picData = picConfig.getProduct();
				filePathSuffix="product/";
			}else {
				picData = picConfig.getAdvert();
				filePathSuffix="advert/";
			}
			// 图片存储路径
			String filePath = ManageConfig.getProfile()+filePathSuffix;
			//路径前缀
			String prefix = manageConfig.getImgProfile();
			//获取图片后缀
			String[] split = picJson.split("\\.");
			String suffix = "."+split[split.length-1];	
			File srcImg = new File(ManageConfig.getSrcFilePath()+picJson);
			//管理台图片
			PicJsonVo productSrcPic = PicUtils.srcPicRename(objectId, picJson,type);
			picJsonList.add(productSrcPic);
			//售卖机大屏
			PictureData siteGreatInfo = picData.getSiteGreat();
			String siteGreat = PicUtils.productPicRename(siteGreatInfo.getOs(), objectId, siteGreatInfo.getWidth(), siteGreatInfo.getHeight(), "1",suffix);
			PicUtils.resizePng(srcImg, new File(filePath+siteGreat), siteGreatInfo.getWidth(), siteGreatInfo.getHeight(), false);
			//售卖机小屏
			PictureData siteLittleInfo = picData.getSiteLittle();
			String siteLittle = PicUtils.productPicRename(siteLittleInfo.getOs(), objectId, siteLittleInfo.getWidth(), siteLittleInfo.getHeight(), "1",suffix);
			PicUtils.resizePng(srcImg, new File(filePath+siteLittle), siteLittleInfo.getWidth(), siteLittleInfo.getHeight(), false);
			PicJsonVo siteJson = new PicJsonVo();
			siteJson.setOs(siteLittleInfo.getOs());
			siteJson.setPic(siteGreat+"#"+siteLittle);
			StringBuilder sb = new StringBuilder();
			sb.append(siteGreatInfo.getWidth()).append("X").append(siteGreatInfo.getHeight()).append("#")
			.append(siteLittleInfo.getWidth()).append("X").append(siteLittleInfo.getHeight());
			siteJson.setPx(sb.toString());
			siteJson.setType("1");
			picJsonList.add(siteJson);
			//购买公众号图片
			PictureData wechatPublicInfo = picData.getWechatPublic();
			String wechatPublic = PicUtils.productPicRename(wechatPublicInfo.getOs(), objectId, wechatPublicInfo.getWidth(), wechatPublicInfo.getHeight(), "1",suffix);
			PicUtils.resizePng(srcImg, new File(filePath+wechatPublic), wechatPublicInfo.getWidth(), wechatPublicInfo.getHeight(), false);
			PicJsonVo wechatPublicJson = new PicJsonVo();
			wechatPublicJson.setOs(wechatPublicInfo.getOs());
			wechatPublicJson.setPic(wechatPublic);
			wechatPublicJson.setPx(wechatPublicInfo.getWidth()+"X"+wechatPublicInfo.getHeight());
			wechatPublicJson.setType("1");
			picJsonList.add(wechatPublicJson);
			//补货小程序图片
			PictureData supplyInfo = picData.getSupply();
			String supply = PicUtils.productPicRename(supplyInfo.getOs(), objectId, supplyInfo.getWidth(), supplyInfo.getHeight(), "1",suffix);
			PicUtils.resizePng(srcImg, new File(filePath+supply), supplyInfo.getWidth(), supplyInfo.getHeight(), false);
			PicJsonVo supplyJson = new PicJsonVo();
			supplyJson.setOs(supplyInfo.getOs());
			supplyJson.setPic(supply);
			supplyJson.setPx(supplyInfo.getWidth()+"X"+supplyInfo.getHeight());
			supplyJson.setType("1");
			picJsonList.add(supplyJson);
			return JSONObject.toJSONString(picJsonList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static void main(String[] args) throws Exception {
    	PicUtils.resizePng(new File("d://1.png"), new File("d://2.png"), 120, 120,false);
	}
}
