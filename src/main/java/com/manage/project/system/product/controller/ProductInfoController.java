package com.manage.project.system.product.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.project.common.Constant;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.product.domain.ProductClassify;
import com.manage.project.system.product.domain.ProductInfo;
import com.manage.project.system.product.service.IProductClassifyService;
import com.manage.project.system.product.service.IProductInfoService;
import com.manage.project.system.product.vo.ImportProductVo;
import com.manage.project.system.product.vo.PicJsonVo;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;
/**
 * 记录商品 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/productInfo")
public class ProductInfoController extends BaseController
{
	@Autowired
	private IProductInfoService productInfoService;
	
	@Autowired
	private IProductClassifyService productClassifyService;
	
	@Autowired
	private BussinessCacheService bussinessCacheService;

	@Autowired
    private ManageConfig manageConfig;
	
	private Logger log = LoggerFactory.getLogger(ProductInfoController.class);
	
	/**
	 * 查询记录商品列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(ProductInfo productInfo)
	{
		startPage();
		if (SystemUtil.isZhilai()) {
			productInfo.setCorpId("");
		} else {
			productInfo.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
		return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 查询记录商品列表
	 */
	@GetMapping("/detail")
	@ResponseBody
	public AjaxResult detail(String logid)
	{
        ProductInfo product = productInfoService.selectProductInfoById(logid);
        String picJson = product.getPicJson();
        Map<String, String> map = new HashMap<>();
        if(StringUtils.isNotEmpty(picJson)) {
        	List<PicJsonVo> picList = JSONObject.parseArray(picJson, PicJsonVo.class);
            PicJsonVo picJsonVo = picList.get(0);   
            String picName = picJsonVo.getPic();
            map.put("name", picName);
            map.put("url", manageConfig.getImgProfile()+"product/"+picName);
        }else {
        	map.put("name", "");
            map.put("url", "");;
        }
        product.setManagePic(map);
		return AjaxResult.success(product);
	}
	
	/**
	 * 新增保存记录商品
	 */
	@Log(title = "记录商品", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody ProductInfo productInfo)
	{	
		productInfo.setProductId(null);
		AjaxResult result = checkProduct(productInfo);
		if(result!=null) {
			return result;
		}
		return toAjax(productInfoService.insertProductInfo(productInfo));
	}
	
	/**
	 * 修改保存记录商品
	 */
	@Log(title = "记录商品", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody ProductInfo productInfo)
	{	
		AjaxResult result = checkProduct(productInfo);
		if(result!=null) {
			return result;
		}
		return toAjax(productInfoService.updateProductInfo(productInfo));
	}
	
	/**
	 * 删除记录商品
	 */
	@Log(title = "记录商品", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{	
		try {
			int result = productInfoService.deleteProductInfoByIds(ids.getIds());
			if(result==1) {
				return AjaxResult.success();
			}else if(result==2) {
				return AjaxResult.error("当前商品系统库存大于0");
			}else if(result==3) {
				return AjaxResult.error("当前商品仓库库存大于0");
			}else if(result==4) {
				return AjaxResult.error("当前商品售货机库存大于0");
			}else if(result==5) {
				return AjaxResult.error("当前商品存在于售货机的货道配置中");
			}else if(result==6) {
				return AjaxResult.error("当前商品存在于配货模板的货道配置中");
			}else {
				return AjaxResult.error("删除失败");
			}
		}catch (Exception e) {
			return AjaxResult.error("删除失败");
		}
	}
	
	/**
	 * 导入商品
	 */
	@Log(title = "记录商品", action = BusinessType.IMPORT)
	@PostMapping( "/importExcel")
	@ResponseBody
	public AjaxResult importExcel(@RequestParam(value = "file", required = false) MultipartFile file) {

		try {

			ExcelUtil<ProductInfo> util = new ExcelUtil<ProductInfo>(ProductInfo.class);

			// 创建excel工具类，返回Excel中的数据
//			List<ProductInfo> productInfoList = util.importExcel("ProductInfo", file.getInputStream());// 导入
			List<ProductInfo> productInfoList = util.importExcel("ProductInfo", file.getInputStream());// 导入
			
			return toAjax(productInfoService.insertProductInfoBatch(productInfoList));
			
		} catch (Exception e) {
			return error("导入Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 导入商品
	 */
	@Log(title = "记录商品", action = BusinessType.IMPORT)
	@PostMapping( "/importProductExcel")
	@ResponseBody
	public AjaxResult importProductExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		FileOutputStream fos = null;
		try {
			
			ExcelUtil<ProductInfo> util = new ExcelUtil<ProductInfo>(ProductInfo.class);
			List<ProductInfo> productInfoList = util.importExcel("ProductInfo", file.getInputStream());// 导入
			
			//获取所有的图片
			HSSFWorkbook workbook = (HSSFWorkbook)WorkbookFactory.create(file.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            Map<String, HSSFPictureData> pictures = ExcelUtil.getPictures(sheet);
            if(pictures!=null&&!pictures.isEmpty()) {
            	Set<String> keySet = pictures.keySet();
                for (String key : keySet) {
                	if(Pattern.compile("^[\\d]*$").matcher(key).matches()) {
                		int rowNum = Integer.parseInt(key);
                		ProductInfo productInfo = productInfoList.get(rowNum-1);
                		HSSFPictureData pictureData = pictures.get(key);
                		//临时保存图片
                		String pic = ExcelUtil.printImg(pictureData, ManageConfig.getSrcFilePath(), UUID.randomUUID().toString());
                		productInfo.setPicJson(pic);
                	}
                	
    			}
            }
            int row=1;
            //保存商品信息
            for (ProductInfo productInfo : productInfoList) {
            	AjaxResult ajaxResult = productInfoService.saveImportProductInfo(productInfo);
            	HSSFRow hssfRow = sheet.getRow(row);
        		HSSFCell cell = hssfRow.getCell(12);
        		if(cell==null) {
        			cell = hssfRow.createCell(12);
        		}
        		row++;
            	if(ajaxResult.isSuccess()) {
            		cell.setCellValue("成功");
            	}else {
            		cell.setCellValue(ajaxResult.getMsg());
            	}
			}
            bussinessCacheService.initProduct();
            //将导入结果写入excel返回给用户
            String path="file/excel/model/"+ShiroUtils.getCorpId()+"/商品导入结果_"+DateUtils.dateTimeNow("yyyyMMddhhmmss")+".xls";
            //写入文件
			File returnFile = new File(ManageConfig.getUploadPrefix()+path);
			if(returnFile.exists()) {
				returnFile.delete();
			}
			File dir = returnFile.getParentFile();
			if(!dir.exists()) {
				dir.mkdirs();
			}
            fos = new FileOutputStream(returnFile);
            workbook.write(fos);
            fos.close();
            return AjaxResult.success(path);
		} catch (Exception e) {
			log.error("导入Excel失败,时间:"+DateUtils.getTime(),e);
			if(fos!=null) {
				try {
					fos.close();
				} catch (IOException exception) {
					log.error("导入Excel失败,关闭文件失败,时间:"+DateUtils.getTime(),exception);
				}
			}
			return error("导入Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 导出模板
	 */
	@Log(title = "记录商品", action = BusinessType.EXPORT)
	@PostMapping("/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody ProductInfo productInfo) {
		try {
			ExcelUtil<ProductInfo> util = new ExcelUtil<ProductInfo>(ProductInfo.class);
			productInfo.setCorpId(SystemUtil.getCorpId());
			List<ProductInfo> list = productInfoService.selectProductInfoList(productInfo);
			for (ProductInfo product : list) {
				product.setBagTypeName(SystemUtil.parseBagType(product.getBagType()));
				ProductClassify productClassify = SystemUtil.getProductClassify(product.getTypeId());
				if(productClassify!=null) {
					product.setTypeName(productClassify.getClassifyName());
				}
			}
            return util.exportExcel(list, "商品信息");
			
		} catch (Exception e) {
			log.error("导出商品信息失败,时间:"+DateUtils.getTime(),e);
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 导出 导入商品所需模板
	 */
	@Log(title = "记录商品", action = BusinessType.EXPORT)
	@PostMapping("/downLoadExcelModel")
	@ResponseBody
	public AjaxResult downLoadExcelModel() {
		FileInputStream inStream = null;
		FileOutputStream fos = null;
		try {
			String excelModelPath = SystemUtil.getExcelModelPath("product");		
            inStream = new FileInputStream(new File(ManageConfig.getUploadPrefix()+excelModelPath));
            HSSFWorkbook workbook = (HSSFWorkbook)WorkbookFactory.create(inStream);
			if(inStream!=null){
                inStream.close();
            }
            HSSFSheet sheet = workbook.getSheetAt(0);        
            //查询所有的分类信息
            ProductClassify productClassify = new ProductClassify();
            productClassify.setCorpId(SystemUtil.getCorpId());
            List<ProductClassify> productClassifyList = productClassifyService.selectProductClassifyList(productClassify);
            String[] classifyList = new String[1000];
            for (int i = 0; i < productClassifyList.size(); i++) {
            	ProductClassify classify = productClassifyList.get(i);
            	classifyList[i]=classify.getClassifyName()+"--"+classify.getClassifyId();
			}
            //设置分类下拉框
            ExcelUtil.addDropDownList(workbook, sheet, classifyList, 1, Constant.DROP_DOWN_LIST_ROWS, 3);
			//设置包装类型下拉框
			ExcelUtil.addDropDownList(workbook, sheet, Constant.PRODUCT_BAG_TYPE_LIST, 1, Constant.DROP_DOWN_LIST_ROWS, 6);
			//写入文件
			File file = new File(ManageConfig.getExcelPath()+"model/"+ShiroUtils.getCorpId()+"/商品模板_"+DateUtils.dateTimeNow("yyyyMMddhhmmss")+".xls");
			if(file.exists()) {
				file.delete();
			}
			File dir = file.getParentFile();
			if(!dir.exists()) {
				dir.mkdirs();
			}
			//设置图片单元格大小
//			for (int i = 1; i <= Constant.DROP_DOWN_LIST_ROWS; i++) {
//				HSSFRow row=null;
//				if(sheet.getRow(i)==null) {
//					row=sheet.createRow(i);
//				}else {
//					row=sheet.getRow(i);
//				}
//				row.setHeight((short)(200*20));
//			}
//			sheet.setColumnWidth(10, 200*20);
			fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
            inStream=null;
            fos=null;
            return AjaxResult.success("file/excel/model/"+ShiroUtils.getCorpId()+"/商品模板_"+DateUtils.dateTimeNow("yyyyMMddhhmmss")+".xls");
		} catch (Exception e) {
			log.error("下载商品模板失败,时间:"+DateUtils.getTime(),e);
			 try {
	            	if(inStream!=null){
	                    inStream.close();
	                }
	                inStream=null;
	                if(fos!=null) {
	                	fos.close();
	                }
	            } catch (Exception exception) {                
	                log.error("下载商品模板,关闭文件流失败,时间:"+DateUtils.getTime(),exception);
	            }
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 导入对账信息
	 */
	@Log(title = "导入商品", action = BusinessType.IMPORT)
	@PostMapping( "/importProduct")
	@ResponseBody
	public AjaxResult importStatement(@RequestParam(value = "file", required = true) MultipartFile file) {
		try {
			ExcelUtil<ImportProductVo> util = new ExcelUtil<ImportProductVo>(ImportProductVo.class);
			List<ImportProductVo> importList = util.importExcel("Statement", file.getInputStream());// 导入	
			Set<String> set = new HashSet<String>();
			for (ImportProductVo importProductVo : importList) {
				String productCode=importProductVo.getProductCode();
				if(StringUtils.isEmpty(productCode)) {
					return AjaxResult.error("编码不能为空");
				}
				if(set.contains(productCode)) {
					return AjaxResult.error("编码重复");
				}
				//判断编码是否重复
				ProductInfo param = new ProductInfo();
				String corpId = ShiroUtils.getCorpId();
				param.setCorpId(corpId);
				param.setProductCode(productCode);
				List<ProductInfo> list = productInfoService.selectProductInfoList(param);
				if(list!=null&&!list.isEmpty()) {
					return AjaxResult.error("编码不能为空");
				}
				if(StringUtils.isEmpty(importProductVo.getName())) {
					return AjaxResult.error("商品名称不能为空");
				}
				set.add(productCode);
			}
			productInfoService.insertProduct(importList);
			return AjaxResult.success();
		} catch (Exception e) {
			e.printStackTrace();
			return error("导入Excel失败，请联系网站管理员！");
		}
	}
	
	
	
	/**
	 * 检查商品
	 * 
	 * @param productInfo
	 * @return
	 */
	private AjaxResult checkProduct(ProductInfo productInfo) {
		if(StringUtils.isEmpty(productInfo.getProductCode())) {
			return AjaxResult.error("编码不能为空");
		}
		if (productInfo.getProductCode().length()>30) {
			return AjaxResult.error("编码输入字段过长");
		}
		//商品编码只能由英文,数字,横线和下划线组成
		if(!productInfo.getProductCode().matches("^[0-9A-Za-z_\\-]+$")) {
			return AjaxResult.error("编码只能由英文,数字,横线和下划线组成");
		}
		//判断编码是否重复
		ProductInfo param = new ProductInfo();
		String corpId = productInfo.getCorpId();
		if(corpId==null) {
			corpId=ShiroUtils.getCorpId();
		}
		param.setCorpId(corpId);
		param.setProductCode(productInfo.getProductCode());
		List<ProductInfo> list = productInfoService.selectProductInfoList(param);
		//如果查出相同编码和公司的商品，必须判断是同一商品
		if(StringUtils.isNotEmpty(list)) {
			for (ProductInfo pi : list) {
				if(!pi.getProductId().equals(productInfo.getProductId())) {
					return AjaxResult.error("编码重复");
				}
			}
		}
		if(StringUtils.isEmpty(productInfo.getName())) {
			return AjaxResult.error("商品名称不能为空");
		}
		if (productInfo.getName().length()>50) {
			return AjaxResult.error("商品名称输入字段过长");
		}
		param = new ProductInfo();
		param.setCorpId(corpId);
		param.setName(productInfo.getName());
		List<ProductInfo> nameList = productInfoService.selectProductInfoList(param);
		//如果查出相同编码和公司的商品，必须判断是同一商品
		if(StringUtils.isNotEmpty(nameList)) {
			for (ProductInfo pi : nameList) {
				if(!pi.getProductId().equals(productInfo.getProductId())) {
					return AjaxResult.error("商品名称重复");
				}
			}
		}
		
		if(StringUtils.isEmpty(productInfo.getFullName())) {
			return AjaxResult.error("商品全名不能为空");
		}
		if (productInfo.getFullName().length()>50) {
			return AjaxResult.error("商品全名输入字段过长");
		}
		param = new ProductInfo();
		param.setCorpId(corpId);
		param.setFullName(productInfo.getFullName());
		List<ProductInfo> fullNameList = productInfoService.selectProductInfoList(param);
		//如果查出相同编码和公司的商品，必须判断是同一商品
		if(StringUtils.isNotEmpty(fullNameList)) {
			for (ProductInfo pi : fullNameList) {
				if(!pi.getProductId().equals(productInfo.getProductId())) {
					return AjaxResult.error("商品全名重复");
				}
			}
		}
		if(StringUtils.isEmpty(productInfo.getTypeId())) {
			return AjaxResult.error("商品分类不能为空");
		}
		if(productInfo.getSalePrice()==null||productInfo.getSalePrice()<=0) {
			return AjaxResult.error("非法金额");
		}
		if(StringUtils.isEmpty(productInfo.getTypeId())) {
			return AjaxResult.error("商品分类不能为空");
		}
		if(StringUtils.isEmpty(productInfo.getBagType())) {
			return AjaxResult.error("商品包装不能为空");
		}
		if(StringUtils.isEmpty(productInfo.getSpec())) {
			return AjaxResult.error("商品规格不能为空");
		}
		return null;
	}
	
	//查询可以引用的商品
	@GetMapping("/getReferenceProductList")
	@ResponseBody
	public AjaxResult getReferenceProductList(ProductInfo productInfo) {
		productInfo.setCorpId(SystemUtil.getCorpId());
		startPage();
		List<ProductInfo> list = productInfoService.selectReferenceProductInfoList(productInfo);
		return AjaxResult.success(getDataTable(list));
	}
	
	//引用对应的商品
	@GetMapping("/referenceProduct")
	@ResponseBody
	public AjaxResult referenceProduct(String productId) {

		try {
			ProductInfo productInfo = productInfoService.selectProductInfoByProductId(productId);
			//复制商品图片
			String pic = productInfo.getPic();
			Map<String, String> map = new HashMap<>();
			if(StringUtils.isNotEmpty(pic)) {
				File srcPic = new File(ManageConfig.getUploadPrefix()+pic);
				String suffix = StringUtils.getFileSuffix(pic);
				String destPath="/front/src/"+UUID.randomUUID().toString()+"."+suffix;
				File destFile = new File(ManageConfig.getUploadPrefix()+destPath);
				if(destFile.exists()) {
					destFile.delete();
				}
				Files.copy(srcPic.toPath(), destFile.toPath());
				String picJson = productInfo.getPicJson();
				List<PicJsonVo> picList = JSONObject.parseArray(picJson, PicJsonVo.class);
	            PicJsonVo picJsonVo = picList.get(0);   
	            String picName = picJsonVo.getPic();
	            productInfo.setPic(destPath);
				productInfo.setPicJson(destPath);
	            map.put("name", picName);
	            map.put("url", manageConfig.getImgProfile()+"product/"+picName);
			}else {
				map.put("name","");
	            map.put("url","");
			}
			productInfo.setManagePic(map);
			return AjaxResult.success(productInfo);
		}catch (Exception e) {
			log.error("引用商品失败,时间:"+DateUtils.getTime(),e);
			return AjaxResult.error("引用商品失败");
		}
	}
}
