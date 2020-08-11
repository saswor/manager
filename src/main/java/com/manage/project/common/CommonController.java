package com.manage.project.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.manage.common.exception.file.FileNameLengthLimitExceededException;
import com.manage.common.utils.CacheUtils;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.file.FileUploadUtils;
import com.manage.common.utils.file.FileUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.web.domain.AjaxResult;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.vending.domain.VendingLogfile;
import com.manage.project.system.vending.service.IVendingLogfileService;

/**
 * 通用请求处理
 * 
 */
@Controller
@CrossOrigin
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
//	public static final String FILE_TYPE_TEXT="01";
//	
//	public static final String FILE_TYPE_PICTURE="02";
//	
//	public static final String FILE_TYPE_VIDEO="03";
//	
//	public static final String TYPE_PRODUCT="goods";
//	
//	public static final String TYPE_ADVERT="advert";
//	
//	public static final String TYPE_LICENSE="license";

	@Autowired
    private ManageConfig manageConfig;
	
	@Autowired
    private IVendingLogfileService vendingLogfileService;
	
	@RequestMapping("common/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
		try {
			String filePath = ResourceUtils.getURL("classpath:").getPath() + "static/file/" + fileName;

			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + setFileDownloadHeader(request, realFileName));
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}

	public String setFileDownloadHeader(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		final String agent = request.getHeader("USER-AGENT");
		String filename = fileName;
		if (agent.contains("MSIE")) {
			// IE浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			// 火狐浏览器
			filename = new String(fileName.getBytes(), "ISO8859-1");
		} else if (agent.contains("Chrome")) {
			// google浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			// 其它浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;

	}

	@RequestMapping("/system/common/uploaduploadImg")
	@ResponseBody
	public AjaxResult uploadImg(@RequestParam("file") MultipartFile file) {
		if (file != null) {
			Map<String, String> map = new HashMap<String, String>();
			// 原始文件名
			String originalFileName = file.getOriginalFilename();
			// 获取图片后缀
			String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
			// 图片存储路径
			String filePath = ManageConfig.getProfile();
			try {
				String fileName = FileUploadUtils.upload(filePath, file, suffix);
				map.put("pic", manageConfig.getImgProfile() + fileName);
				map.put("oName", originalFileName);
				map.put("nName", fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return AjaxResult.success(map);
		} else {
			return AjaxResult.error();
		}
	}

	public static void main(String[] args) {
		float u = 5620;
		System.out.println(u/1024);
		
		
//		if (!StringUtils.isEmpty("200")) {
//			try {
//				long s = Long.valueOf("200");
//				long a = s*1024;
//				System.out.println(a);
//			} catch (Exception e) {
//				
//			}
//		}
	}
	
	/**
	 * 上传图片
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/system/common/upload")
	@ResponseBody
	public AjaxResult upload(@RequestParam(value = "file", required = true) MultipartFile file,String picType,String fileType,String payType) {
		if (file != null) {
			long size = file.getSize();	// 大小(M)			
			Map<String, String> map = new HashMap<String, String>();
			// 原始文件名
			String originalFileName = file.getOriginalFilename();
			// 获取图片后缀
			String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
			// 图片存储路径
			String filePathPrefix = manageConfig.getUploadPath();
			String fileName;
			String filePathSuffix="";
			String filePathMiddle="";
			String filePath="";
			if(picType==null){
				picType="";
			}
			if(fileType==null){
				fileType="";
			}
			try {
				if(Constant.TYPE_LICENSE.equals(picType)) {
					//检查文件格式是不是p12文件
					if(!".p12".equals(suffix)) {
						return AjaxResult.error("文件格式错误,请上传p12文件");
					}
					filePath=manageConfig.getLicenseFilePath();
					fileName = FileUploadUtils.upload(filePath, file, suffix);
//					File destFile = new File(manageConfig.getLicenseFilePath() + ShiroUtils.getCorpId()+"_weChat_"+payType+".p12");
//					File srcFile = new File(manageConfig.getLicenseFilePath() + fileName);
//					if(destFile.exists()) {
//						destFile.delete();
//					}
//					srcFile.renameTo(destFile);
					map.put("pic", "front/license/" + fileName);
				}else {
					filePathSuffix="src/";
					switch(fileType) {
						case Constant.MEDIA_TYPE_TEXT:
							filePathMiddle="text/";
							break;
						case Constant.MEDIA_TYPE_PICTURE:
							filePathMiddle="pic/";
							break;
						case Constant.MEDIA_TYPE_VIDEO:
							filePathMiddle="video/";
							break;
						default:
					}
					filePath=filePathPrefix+filePathMiddle+filePathSuffix;
					fileName = FileUploadUtils.upload(manageConfig.getUploadPrefix()+filePath, file, suffix);
					map.put("pic", filePath + fileName);
				}
			} catch (FileSizeLimitExceededException | FileNameLengthLimitExceededException | IOException e) {
				log.error("上传图片失败",e);
				return AjaxResult.error("上传文件失败!");
			}
			map.put("oName", originalFileName);
			map.put("nName", fileName);		
			map.put("size", String.valueOf(size));	// 大小(M)
			
			BufferedImage image;
			try {
				image = ImageIO.read(file.getInputStream());			
				if (image != null) {// 如果image=null 表示上传的不是图片格式
					String px = image.getWidth() + "*" + image.getHeight();	
					map.put("px", px);	// 分辨率				
				}
			} catch (IOException e) {
				map.put("px", "");	// 分辨率	
			}
			return AjaxResult.success(map);
		} else {
			return AjaxResult.error();
		}
	}
	
	/**
	 * 检查上传图片的大小和分辨率
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private boolean checkProductPic(MultipartFile file) throws IOException{
		if(file!=null){
			long size = file.getSize();
			if(size>200*1024){
				return false;
			}
			BufferedImage image = ImageIO.read(file.getInputStream()); 
			int width = image.getWidth();
			int height = image.getHeight();
			if(width>140||height>140){
				return false;
			}
			return true;
		}else{
			return false;
		}
		 
	}
	
	/**
	 * 上传版本文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/system/common/versionUpload")
	@ResponseBody
	public AjaxResult versionUpload(@RequestParam(value = "file", required = true) MultipartFile file,String picType,String fileType,String payType) {
		if (file != null) {
			long size = file.getSize();	// 大小(M)			
			Map<String, String> map = new HashMap<String, String>();
			// 原始文件名
			String originalFileName = file.getOriginalFilename();
			// 获取文件后缀
			String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
			if(!".apk".equalsIgnoreCase(suffix)) {
				return AjaxResult.error("文件格式错误,请上传apk格式文件");
			}
			String lowerCase = originalFileName.toLowerCase();
			if(!lowerCase.matches("^[0-9A-Za-z_\\-\\.]+[\\.]apk$")) {
				return AjaxResult.error("文件名只允许数字,字母,小数点,横杠和下划线");
			}
			// 文件存储路径前缀
			String filePathPrefix = manageConfig.getUploadPrefix();
			String filePath=filePathPrefix+"front/version/";
			try {
				InputStream fis = file.getInputStream();
				String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
				String fileName = FileUploadUtils.upload(filePath, file, suffix);
				map.put("fileName", originalFileName);
				map.put("fileType", suffix.substring(1));
				map.put("localPath", "/front/version/"+fileName);		
				map.put("fileSize", String.valueOf(size));	// 大小(M)
				map.put("fileMDF", md5);	
				return AjaxResult.success(map);
			} catch (FileSizeLimitExceededException | FileNameLengthLimitExceededException | IOException e) {
				log.error("上传图片失败",e);
				return AjaxResult.error("上传文件失败!");
			}
		} else {
			return AjaxResult.error("上传文件不存在!");
		}
	}
	
	/**
	 * 上传广告文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/system/common/adUpload")
	@ResponseBody
	public AjaxResult adUpload(@RequestParam(value = "file", required = true) MultipartFile file,String picType,String fileType,String payType) {
		if (file != null) {
			long size = file.getSize();	// 大小(M)			
			Map<String, String> map = new HashMap<String, String>();
			// 原始文件名
			String originalFileName = file.getOriginalFilename();
			// 获取图片后缀
			String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
			// 图片存储路径
			String filePath = manageConfig.getSrcFilePath();
			String fileName;
			try {
				fileName = FileUploadUtils.upload(filePath, file, suffix);
				map.put("pic", "front/src/" + fileName);
				map.put("oName", originalFileName);
				map.put("nName", fileName);		
				map.put("size", String.valueOf(size));	// 大小(M)
				BufferedImage image;		
				image = ImageIO.read(file.getInputStream());			
				if (image != null) {// 如果image=null 表示上传的不是图片格式
					String px = image.getWidth() + "*" + image.getHeight();	
					map.put("px", px);	// 分辨率				
				}
				return AjaxResult.success(map);
			} catch (FileSizeLimitExceededException | FileNameLengthLimitExceededException | IOException e) {
				log.error("上传图片失败",e);
				return AjaxResult.error("上传文件失败!");
			} 
		} else {
			return AjaxResult.error("上传失败");
		}
	}

	@RequestMapping("/unlogin")
	@ResponseBody
	public AjaxResult unlogin() {
		AjaxResult result = new AjaxResult();
		Map m = new HashMap();
        m.put("retMsg", "please login.");
        m.put("reTCode", "408");
        result.put("zhead", m);
		return result;
	}
	
	@RequestMapping("/system/logfile/upload")
	@ResponseBody
	public String logfileUpload(@RequestParam(value = "file", required = true) MultipartFile file) {
		log.info("上传日志文件时间:"+DateUtils.getTime());
		if (file != null) {
			long size = file.getSize();	// 大小(M)			
			Map<String, String> map = new HashMap<String, String>();
			// 原始文件名
			String originalFileName = file.getOriginalFilename();
			String fileId = originalFileName.split("_")[0];
			try {
				String path="file/logfile/"+originalFileName;
				String url=manageConfig.getIp()+"/"+path;
				file.transferTo(new File(manageConfig.getUploadPrefix()+"file/logfile/",originalFileName));
				//修改日志记录
				VendingLogfile vendingLogfile = vendingLogfileService.selectVendingLogfileByFileId(fileId);
				if(vendingLogfile!=null) {
					vendingLogfile.setFileNum(vendingLogfile.getFileNum()+1);
					vendingLogfile.setFileSize(vendingLogfile.getFileSize()+size);
					String fileUrl = vendingLogfile.getFileUrl();
					String filePath = vendingLogfile.getFilePath();
					if(StringUtils.isNotEmpty(fileUrl)) {
						fileUrl=fileUrl+"|"+url;
						filePath=filePath+"|"+path;
					}else {
						fileUrl=url;
						filePath=path;
					}
					vendingLogfile.setFileUrl(fileUrl);
					vendingLogfile.setFilePath(filePath);
					if(originalFileName.contains("end")) {
						vendingLogfile.setCurState(Constant.VENDING_LOGFILE_CURSTATE_FINISH);
						vendingLogfile.setStateTime(DateUtils.getTime());
					}
					vendingLogfileService.updateVendingLogfile(vendingLogfile);
					return "0000";
				}else {
					log.error("上传日志文件失败,日志记录不存在,日志编号:"+fileId+"时间:"+DateUtils.getTime());
					return "500";
				}
				
			} catch (Exception e) {
				log.error("上传日志文件失败,日志编号:"+fileId+"时间:"+DateUtils.getTime(),e);
				return "500";
			}
		}else {
			log.error("上传日志文件失败，文件不存在,时间:"+DateUtils.getTime());
			return "500";
		}

	}
	
	@GetMapping("/system/clearCache")
	@Log(title="清楚系统缓存",action=BusinessType.UPDATE)
	public AjaxResult clearCache(String cacheName,String key) {
		//只有超级管理员可以清理缓存
		User user = ShiroUtils.getUser();
		if(!"super".equals(user.getLoginName())) {
			return AjaxResult.error("只有超级管理员可以清理缓存");
		}
		log.info("清楚系统缓存,cacheName:"+cacheName+",key:"+key+",时间:"+DateUtils.getTime());
		CacheUtils.remove(cacheName, key);
		return AjaxResult.success();
	}
}

