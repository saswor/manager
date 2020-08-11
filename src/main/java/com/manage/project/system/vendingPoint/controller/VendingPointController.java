package com.manage.project.system.vendingPoint.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.manage.common.utils.DateUtils;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.poi.ExcelUtil;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.aspectj.lang.annotation.Log;
import com.manage.framework.aspectj.lang.constant.BusinessType;
import com.manage.framework.config.ManageConfig;
import com.manage.project.common.Constant;
import com.manage.project.common.vo.CommonVo;
import com.manage.project.system.base.domain.Dispatch;
import com.manage.project.system.base.service.IDispatchService;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;
import com.manage.project.system.vending.service.IVendingService;
import com.manage.project.system.vendingPoint.domain.VendingDistrict;
import com.manage.project.system.vendingPoint.domain.VendingLine;
import com.manage.project.system.vendingPoint.domain.VendingPoint;
import com.manage.project.system.vendingPoint.service.IVendingLineService;
import com.manage.project.system.vendingPoint.service.IVendingPointService;
import com.manage.framework.web.controller.BaseController;
import com.manage.framework.web.domain.AjaxResult;

/**
 * 售货机绑定的点位 信息操作处理
 * 
 * @author xufeng
 * @date 2018-08-31
 */
@Controller
@RequestMapping("/system/vendingPoint")
public class VendingPointController extends BaseController
{
	@Autowired
	private IVendingPointService vendingPointService;
	@Autowired
	private IVendingService vendingService;
	@Autowired
	private IVendingLineService vendingLineService;
	@Autowired
	private IDispatchService dispatchService;
	@Autowired
	private BussinessCacheService bussinessCacheService;
	
	private Logger log = LoggerFactory.getLogger(VendingPointController.class);
	
	/**
	 * 查询售货机绑定的点位列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public AjaxResult list(VendingPoint vendingPoint)
	{
		startPage();
		if (SystemUtil.isZhilai()) {	// 宇宙星空用户查询所有
			vendingPoint.setCorpId("");
		} else {
			vendingPoint.setCorpId(ShiroUtils.getUser().getCorpId());
		}
        List<VendingPoint> list = vendingPointService.selectVendingPointList(vendingPoint);
        return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 大屏地图使用的点位列表数据，要返回前端要求的地图格式
	 * @return
	 */
	@GetMapping("/dpList")
	@ResponseBody
	public AjaxResult dpList()
	{
		VendingPoint vendingPoint = new VendingPoint();
		vendingPoint.setCorpId(SystemUtil.getCorpId());
        List<VendingPoint> list = vendingPointService.selectVendingPointList(vendingPoint);
        Map<String, String[]> result = new HashMap<String, String[]>();
//        Map<String, Integer> resultData = new HashMap<String, Integer>();
        List<Map<String, Object>> resultData = new ArrayList<Map<String, Object>>();
        for (VendingPoint vp : list) {
        	String[] ll = {vp.getLongitude(), vp.getLatitude()};
        	result.put(vp.getName(), ll);
        	
        	Map<String, Object> m = new HashMap<String, Object>();
        	m.put("name", vp.getName());
        	m.put("value", 1);
        	resultData.add(m);
        }
        
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("result", result);
        m.put("resultData", resultData);
        
        return AjaxResult.success(m);
	}

	/**
	 * 新增保存售货机绑定的点位
	 */
	@Log(title = "售货机绑定的点位", action = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@RequestBody VendingPoint vendingPoint)
	{	
		String corpId = vendingPoint.getCorpId();
		if(StringUtils.isEmpty(corpId)) {
			corpId = ShiroUtils.getUser().getCorpId();
			vendingPoint.setCorpId(corpId);
		}
		//查询编码是否重复
		String code = vendingPoint.getCode();
		if(StringUtils.isEmpty(code)) {
			return AjaxResult.error("编码不能为空");
		}else {
			if(checkCodeExist(code, corpId)) {
				return AjaxResult.error("编码重复,请重新输入编码");
			}
		}
		if(vendingPoint.getCode().length() > 30){
			return AjaxResult.error("点位编码输入字段过长");
		}
		if(StringUtils.isEmpty(vendingPoint.getName())){
			return AjaxResult.error("点位名称不能为空");
		}
		if(vendingPoint.getName().length() > 50){
			return AjaxResult.error("点位名称输入字段过长");
		}
		if(StringUtils.isEmpty(vendingPoint.getLineId())){
			return AjaxResult.error("线路名称不能为空");
		}
		if(StringUtils.isEmpty(vendingPoint.getCorpId())){
			return AjaxResult.error("所属商户不能为空");
		}
		if(StringUtils.isEmpty(vendingPoint.getCountry()) || StringUtils.isEmpty(vendingPoint.getProvince()) || StringUtils.isEmpty(vendingPoint.getCity()) || StringUtils.isEmpty(vendingPoint.getDistrict())){
			return AjaxResult.error("所属行政区域不能为空");
		}
		if(StringUtils.isEmpty(vendingPoint.getAdderss())){
			return AjaxResult.error("详细地址不能为空");
		}
		if(vendingPoint.getAdderss().length() > 50){
			return AjaxResult.error("详细地址输入字段过长");
		}
		//校验经度
		if(!Pattern.matches("(\\-|\\+)?(((\\d|[1-9]\\d|1[0-7]\\d|0{1,3})\\.\\d{0,6})|(\\d|[1-9]\\d|1[0-7]\\d|0{1,3})|180\\.0{0,6}|180)", vendingPoint.getLongitude())) {
			return AjaxResult.error("经度格式错误");
		}
		//校验纬度
		if(!Pattern.matches("(\\-|\\+)?([0-8]?\\d{1}\\.\\d{0,6}|90\\.0{0,6}|[0-8]?\\d{1}|90)", vendingPoint.getLatitude())) {
			return AjaxResult.error("纬度格式错误");
		}
		if (StringUtils.isEmpty(vendingPoint.getDescription())) {
			vendingPoint.setDescription("无");
		}
		int r = vendingPointService.insertVendingPoint(vendingPoint);
		
		return toAjax(r);
	}
	
	/**
	 * 修改保存售货机绑定的点位
	 */
	@Log(title = "售货机绑定的点位", action = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@RequestBody VendingPoint vendingPoint)
	{	
		VendingPoint point = vendingPointService.selectVendingPointById(vendingPoint.getLogid());
		String code = point.getCode();
		String updateCode = vendingPoint.getCode();
		//判断是否修改了编码
		if(StringUtils.isNotEmpty(updateCode)&&!updateCode.equals(code)) {
			//查询编码是否重复
			if(checkCodeExist(updateCode, point.getCorpId())) {
				return AjaxResult.error("编码重复,请重新输入编码");
			}
		}
		if(vendingPoint.getCode().length() > 30){
			return AjaxResult.error("点位编码输入字段过长");
		}
		if(vendingPoint.getName().length() > 50){
			return AjaxResult.error("点位名称输入字段过长");
		}
		if(vendingPoint.getAdderss().length() > 50){
			return AjaxResult.error("详细地址输入字段过长");
		}
		//校验经度
		if(!Pattern.matches("(\\-|\\+)?(((\\d|[1-9]\\d|1[0-7]\\d|0{1,3})\\.\\d{0,6})|(\\d|[1-9]\\d|1[0-7]\\d|0{1,3})|180\\.0{0,6}|180)", vendingPoint.getLongitude())) {
			return AjaxResult.error("经度格式错误");
		}
		//校验纬度
		if(!Pattern.matches("(\\-|\\+)?([0-8]?\\d{1}\\.\\d{0,6}|90\\.0{0,6}|[0-8]?\\d{1}|90)", vendingPoint.getLatitude())) {
			return AjaxResult.error("纬度格式错误");
		}
		if (StringUtils.isEmpty(vendingPoint.getDescription())) {
			vendingPoint.setDescription("无");
		}
		int r = vendingPointService.updateVendingPoint(vendingPoint);
		return toAjax(r);
	}
	
	
	/**
	 * 删除售货机绑定的点位 
	 */
	@Log(title = "售货机绑定的点位", action = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(@RequestBody CommonVo ids)
	{	
		String[] pointIds = ids.getIds().split(",");
		Integer num = vendingService.selectVendingNumByPointIds(pointIds);
		if(num>0) {
			return AjaxResult.error("点位有绑定的售货机,无法删除");
		}
		/*for (String pointId : pointIds) {
			Vending vending = new Vending();
			vending.setPointId(pointId);
			List<Vending> list = vendingService.selectVendingList(vending);
			if(StringUtils.isNotEmpty(list)) {
				return AjaxResult.error("点位有绑定的售货机,无法删除");
			}
		}*/
		return toAjax(vendingPointService.deleteVendingPointByIds(ids.getIds()));
	}
	
	/**
	 * 查询点位列表
	 */
	@GetMapping("/listAll")
	@ResponseBody
	public AjaxResult listAll(VendingPoint vendingPoint)
	{
		vendingPoint.setCorpId(SystemUtil.getCorpId());
        List<VendingPoint> list = vendingPointService.selectVendingPoint(vendingPoint);
        return AjaxResult.success(getDataTable(list));
	}
	
	/**
	 * 判断编码是否存在
	 * 
	 * @param code 编码
	 * @param corpId 公司编号
	 * @return 存在返回true,不存在返回false
	 */
	public boolean checkCodeExist(String code,String corpId) {
		VendingPoint point = new VendingPoint();
		point.setCode(code);
		point.setCorpId(corpId);
		List<VendingPoint> list = vendingPointService.selectVendingPointList(point);
		return StringUtils.isNotEmpty(list);
	}
	
	/**
	 * 导入点位
	 */
	@Log(title = "售货机绑定的点位", action = BusinessType.IMPORT)
	@PostMapping( "/importExcel")
	@ResponseBody
	public AjaxResult importProductExcel(@RequestParam(value = "file", required = false) MultipartFile file) {
		FileOutputStream fos = null;
		try {
			ExcelUtil<VendingPoint> util = new ExcelUtil<VendingPoint>(VendingPoint.class);
			List<VendingPoint> vendingPointList = util.importExcel("点位模板", file.getInputStream());// 导入
			HSSFWorkbook workbook = (HSSFWorkbook)WorkbookFactory.create(file.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            int row=1;
            //保存线路信息
            for (VendingPoint vendingPoint : vendingPointList) {
            	AjaxResult ajaxResult = vendingPointService.saveImportVendingPoint(vendingPoint);
            	HSSFRow hssfRow = sheet.getRow(row);
        		HSSFCell cell = hssfRow.getCell(11);
        		if(cell==null) {
        			cell = hssfRow.createCell(11);
        		}
        		row++;
            	if(ajaxResult.isSuccess()) {
            		cell.setCellValue("成功");
            	}else {
            		cell.setCellValue(ajaxResult.getMsg());
            	}
			}
            bussinessCacheService.initVendingPoint();
            //将导入结果写入excel返回给用户
            String path="file/excel/model/"+ShiroUtils.getCorpId()+"/点位导入结果_"+DateUtils.dateTimeNow("yyyyMMddhhmmss")+".xls";
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
	 * 导出点位
	 */
	@Log(title = "售货机绑定的点位", action = BusinessType.EXPORT)
	@PostMapping("/exportExcel")
	@ResponseBody
	public AjaxResult exportExcel(@RequestBody VendingPoint vendingPoint) {
		try {
			ExcelUtil<VendingPoint> util = new ExcelUtil<VendingPoint>(VendingPoint.class);
			vendingPoint.setCorpId(SystemUtil.getCorpId());
			List<VendingPoint> list = vendingPointService.selectVendingPointList(vendingPoint);
			for (VendingPoint point : list) {
				point.setAddresses(point.getAddresses());
			}
            return util.exportExcel(list, "点位信息");
		} catch (Exception e) {
			log.error("导出点位信息失败,时间:"+DateUtils.getTime(),e);
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
	/**
	 * 下载模板
	 */
	@Log(title = "售货机绑定的点位", action = BusinessType.EXPORT)
	@PostMapping("/downLoadExcelModel")
	@ResponseBody
	public AjaxResult downLoadExcelModel() {
		FileInputStream inStream = null;
		FileOutputStream fos = null;
		try {
			String excelModelPath = SystemUtil.getExcelModelPath("point");		
            inStream = new FileInputStream(new File(ManageConfig.getUploadPrefix()+excelModelPath));
            HSSFWorkbook workbook = (HSSFWorkbook)WorkbookFactory.create(inStream);
			if(inStream!=null){
                inStream.close();
            }
            HSSFSheet sheet = workbook.getSheetAt(0);        
            //查询所有的线路信息
            VendingLine vendingLine = new VendingLine();
            vendingLine.setCorpId(SystemUtil.getCorpId());
            List<VendingLine> vendingLineList = vendingLineService.selectVendingLineList(vendingLine);
            String[] lineList = new String[1000];
            for (int i = 0; i < vendingLineList.size(); i++) {
            	VendingLine line = vendingLineList.get(i);
            	lineList[i]=line.getName()+"--"+line.getLineId();
			}
            //设置线路下拉框
            ExcelUtil.addDropDownList(workbook, sheet, lineList, 1, Constant.DROP_DOWN_LIST_ROWS, 2);
			//设置行政区多级联动
            createCascadeCell(workbook, sheet, 1, 3);
			//写入文件
			File file = new File(ManageConfig.getExcelPath()+"model/"+ShiroUtils.getCorpId()+"/点位模板_"+DateUtils.dateTimeNow("yyyyMMddhhmmss")+".xls");
			if(file.exists()) {
				file.delete();
			}
			File dir = file.getParentFile();
			if(!dir.exists()) {
				dir.mkdirs();
			}
			fos=new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
            //设置行政区域多级联动
//			inStream=new FileInputStream(new File(ManageConfig.getUploadPrefix()+excelModelPath));
//            XSSFWorkbook xssfWorkbook = (XSSFWorkbook)WorkbookFactory.create(inStream);
//            inStream.close();
//            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
//            createCascadeCell(xssfWorkbook, xssfSheet, 1, 3);
//            fos=new FileOutputStream(file);
//            xssfWorkbook.write(fos);
//            fos.close();
            return AjaxResult.success("file/excel/model/"+ShiroUtils.getCorpId()+"/点位模板_"+DateUtils.dateTimeNow("yyyyMMddhhmmss")+".xls");
		} catch (Exception e) {
			log.error("下载点位模板失败,时间:"+DateUtils.getTime(),e);
			 try {
	            	if(inStream!=null){
	                    inStream.close();
	                }
	                inStream=null;
	                if(fos!=null){
	                	fos.close();
	                }
	                fos=null;
	            } catch (Exception exception) {                
	                log.error("下载点位模板,关闭文件流失败,时间:"+DateUtils.getTime(),exception);
	            }
			return error("导出Excel失败，请联系网站管理员！");
		}
	}
	
	private void createCascadeCell(HSSFWorkbook workbook,HSSFSheet sheet,int startRow,int startColumn) throws Exception {
		
		//查询所有行政区域信息
		List<Dispatch> dispatchList = dispatchService.selectDispatchList(new Dispatch());
		Map<String, List<String>> dispatchMap = new HashMap<String,List<String>>();
		Map<String, Dispatch> parentMap = new HashMap<String,Dispatch>();
		TreeMap<Integer, List<String>> treeMap = new TreeMap<Integer,List<String>>();
		for (Dispatch dispatch : dispatchList) {
			//如果不是最后一级,将信息存入父级map
			if(dispatch.getLevel()!=Constant.DISPATCH_LAST_LEVEL) {
				parentMap.put(dispatch.getId(), dispatch);
			}
			//将等级信息放入treeMap
			List<String> levelList = treeMap.get(dispatch.getLevel());
			if(levelList==null) {
				levelList=new ArrayList<String>();
				treeMap.put(dispatch.getLevel(), levelList);
			}
			levelList.add(dispatch.getName()+dispatch.getId());
			//将区域信息按父节点存入map
			if(dispatchMap.get(dispatch.getParentId())==null) {
				List<String> list = new ArrayList<String>();
				list.add(dispatch.getName()+ dispatch.getId());
				dispatchMap.put(dispatch.getParentId(), list);
			}else {
				List<String> list = dispatchMap.get(dispatch.getParentId());
				list.add(dispatch.getName()+ dispatch.getId());
			}
		}
		List<String> countryList = treeMap.get(Constant.DISPATCH_FIRST_LEVEL);
        //将有子区域的父区域放到一个list中
        List<String> areaFatherNameList = new ArrayList<String>();
        Set<String> parentMapKeySet = parentMap.keySet();
        for (String parentMapKey : parentMapKeySet) {
        	Dispatch dispatch = parentMap.get(parentMapKey);
        	areaFatherNameList.add(dispatch.getName()+dispatch.getId());
		}
        //将所有子区域封装到map中
        Map<String,List<String>> areaMap = new HashMap<String,List<String>>();
        Set<Entry<String, List<String>>> dispatchMapEntrySet = dispatchMap.entrySet();
        for (Entry<String, List<String>> entry : dispatchMapEntrySet) {
        	String dispatchId = entry.getKey();
        	if(Constant.DISPATCH_START_PARENT_ID.equals(dispatchId)) {
        		continue;
        	}
        	Dispatch dispatch = parentMap.get(dispatchId);
        	areaMap.put(dispatch.getName()+dispatch.getId(), entry.getValue());
		}

				
		Sheet hideSheet = workbook.createSheet("site_sheet");
		workbook.setSheetHidden(workbook.getSheetIndex("site_sheet"), true);
		
		int rowId = 0;
		// 设置第一行，存省的信息
		Row proviRow = hideSheet.createRow(rowId++);
		proviRow.createCell(0).setCellValue("国家列表");
		for (int i = 0; i < countryList.size(); i++) {
			Cell proviCell = proviRow.createCell(i + 1);
			proviCell.setCellValue(countryList.get(i));
		}
		// 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。
		Iterator<String> keyIterator = areaMap.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			List<String> son = areaMap.get(key);
			
			Row row = hideSheet.createRow(rowId++);
			row.createCell(0).setCellValue(key);
			for (int i = 0; i < son.size(); i++) {
				Cell cell = row.createCell(i + 1);
				cell.setCellValue(son.get(i));
			}
			
			// 添加名称管理器
			String range = ExcelUtil.getRange(1, rowId, son.size());
			Name name = workbook.createName();
			name.setNameName(key);
			String formula = "site_sheet!" + range;
			name.setRefersToFormula(formula);
		}

		// 国家规则
		DVConstraint provConstraint = DVConstraint.createExplicitListConstraint(countryList.toArray(new String[] {}));
		CellRangeAddressList provRangeAddressList = new CellRangeAddressList(1, Constant.DROP_DOWN_LIST_ROWS, 3, 3);
		DataValidation provinceDataValidation = new HSSFDataValidation(provRangeAddressList, provConstraint);
		provinceDataValidation.createErrorBox("error", "请选择正确的国家");
		sheet.addValidationData(provinceDataValidation);
		
		for (int i = 1; i <= Constant.DROP_DOWN_LIST_ROWS; i++) {
		    ExcelUtil.setDataValidation(sheet,"D",i,4);
		    ExcelUtil.setDataValidation(sheet,"E",i,5);
		    ExcelUtil.setDataValidation(sheet,"F",i,6);
		}
	}
}
