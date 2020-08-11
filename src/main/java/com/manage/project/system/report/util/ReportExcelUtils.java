/**
 * 
 */
package com.manage.project.system.report.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import com.manage.framework.config.ManageConfig;
import com.manage.project.system.report.vo.LineSaleMoneyVo;
import com.manage.project.system.report.vo.PointSaleMoneyVo;

/**
 * 报表导出excel工具类
 * 
 * @author zhangjiawei
 * @date 2018年11月17日
 * 
 */
public class ReportExcelUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ReportExcelUtils.class);

	/**
	 * 导出线路报表excel
	 * 
	 * @param list 报表内容
	 * @return
	 * @throws Exception 
	 */
	public static String lineReportToExcel(List<LineSaleMoneyVo> list) throws Exception{
		// 产生工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			
	        //设置居中和垂直对其
	        HSSFCellStyle cellStyle = workbook.createCellStyle();
	        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        // 产生工作表对象
	        HSSFSheet sheet = workbook.createSheet("Sheet1");  
	        HSSFRow row;
	        HSSFCell cell; 
	        // 产生首行
	        row = sheet.createRow(0);
	        // 产生单元格
	        cell = row.createCell(0);
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setCellValue("线路名称");
	        cell = row.createCell(1);
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setCellValue("点位数量");
	        cell = row.createCell(2);
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setCellValue("累计销售额");
	        sheet.addMergedRegion(new CellRangeAddress(0,0,3,32));
	        cell = row.createCell(3);
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setCellValue("每日销售额");
	        //将list写入excel的每一行
	        for (int i = 0; i < list.size(); i++) {
	        	int startRow=2*i+1;
	        	//设置合并单元格
				sheet.addMergedRegion(new CellRangeAddress(startRow,startRow+1,0,0));
				sheet.addMergedRegion(new CellRangeAddress(startRow,startRow+1,1,1));
				sheet.addMergedRegion(new CellRangeAddress(startRow,startRow+1,2,2));
				HSSFRow row1 = sheet.createRow(startRow);
				HSSFRow row2 = sheet.createRow(startRow+1);
				// 产生单元格
		        cell = row1.createCell(0);
		        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        cell.setCellValue(list.get(i).getLineName());
		        cell = row1.createCell(1);
		        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		        cell.setCellValue(list.get(i).getPointNum());
		        cell = row1.createCell(2);
		        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		        cell.setCellValue(list.get(i).getTotalSaleMoney());
		        List<Map<String, Object>> details = list.get(i).getDetails();
		        //线路明细放入到excel
		        for (int j = 0; j < details.size(); j++) {
					HSSFCell cell1 = row1.createCell(j+3);
					HSSFCell cell2 = row2.createCell(j+3);
					cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell1.setCellValue((String)details.get(j).get("date"));
					cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell2.setCellValue((double)details.get(j).get("saleMoney"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
        OutputStream out=null;
        
        try {
        	String filename = UUID.randomUUID().toString()+".xls";
        	String prefix = ManageConfig.getExcelPath();
        	String urlPrefix = ManageConfig.getExcelUrl();
        	File dir = new File(prefix);
        	if(!dir.exists()) {
        		dir.mkdirs();
        	}
        	out = new FileOutputStream(prefix + filename);
        	log.info("生成线路报表excel");
            workbook.write(out);
            out.close();
            return urlPrefix+filename;
        }
        catch (Exception e)
        {
            log.error("写入excel失败", e.getMessage());
            if(out!=null) {
            	out.close();
            }
            throw new Exception("生成excel失败");
        }
	}
	
	/**
	 * 导出点位报表excel
	 * 
	 * @param list 报表内容
	 * @return
	 * @throws Exception 
	 */
	public static String pointReportToExcel(List<PointSaleMoneyVo> list) throws Exception{
		// 产生工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			
	        //设置居中和垂直对其
	        HSSFCellStyle cellStyle = workbook.createCellStyle();
	        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        // 产生工作表对象
	        HSSFSheet sheet = workbook.createSheet("Sheet1");  
	        HSSFRow row;
	        HSSFCell cell; 
	        // 产生首行
	        row = sheet.createRow(0);
	        // 产生单元格
	        cell = row.createCell(0);
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setCellValue("点位名称");
	        cell = row.createCell(1);
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setCellValue("累计销售额");
	        sheet.addMergedRegion(new CellRangeAddress(0,0,2,31));
	        cell = row.createCell(2);
	        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        cell.setCellValue("每日销售额");
	        //将list写入excel的每一行
	        for (int i = 0; i < list.size(); i++) {
	        	int startRow=2*i+1;
	        	//设置合并单元格
				sheet.addMergedRegion(new CellRangeAddress(startRow,startRow+1,0,0));
				sheet.addMergedRegion(new CellRangeAddress(startRow,startRow+1,1,1));
				//sheet.addMergedRegion(new CellRangeAddress(startRow,startRow+1,2,2));
				HSSFRow row1 = sheet.createRow(startRow);
				HSSFRow row2 = sheet.createRow(startRow+1);
				// 产生单元格
		        cell = row1.createCell(0);
		        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        cell.setCellValue(list.get(i).getPointName());
		        cell = row1.createCell(1);
		        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		        cell.setCellValue(list.get(i).getTotalSaleMoney());
		        List<Map<String, Object>> details = list.get(i).getDetails();
		        //线路明细放入到excel
		        for (int j = 0; j < details.size(); j++) {
					HSSFCell cell1 = row1.createCell(j+2);
					HSSFCell cell2 = row2.createCell(j+2);
					cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell1.setCellValue((String)details.get(j).get("date"));
					cell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell2.setCellValue((double)details.get(j).get("saleMoney"));
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
        OutputStream out=null;
        
        try {
        	String filename = UUID.randomUUID().toString()+".xls";
        	String prefix = ManageConfig.getExcelPath();
        	String urlPrefix = ManageConfig.getExcelUrl();
        	File dir = new File(prefix);
        	if(!dir.exists()) {
        		dir.mkdirs();
        	}
        	out = new FileOutputStream(prefix + filename);
        	log.info("生成点位报表excel");
            workbook.write(out);
            out.close();
            return urlPrefix+filename;
        }
        catch (Exception e)
        {
            log.error("写入excel失败", e.getMessage());
            if(out!=null) {
            	out.close();
            }
            throw new Exception("生成excel失败");
        }
	}
}
