 package com.manage.common.utils.poi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDataValidationHelper;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.manage.common.utils.StringUtils;
import com.manage.framework.aspectj.lang.annotation.Excel;
import com.manage.framework.config.ManageConfig;
import com.manage.framework.shiro.web.session.OnlineWebSessionManager;
import com.manage.framework.web.domain.AjaxResult;

/**
 * Excel相关处理
 * 
 */
public class ExcelUtil<T>
{

    private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

    public Class<T> clazz;

    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    public List<T> importExcel(String sheetName, InputStream input) throws Exception
    {
        List<T> list = new ArrayList<T>();

        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet(sheetName);
        if (StringUtils.isNotEmpty(sheetName))
        {
            // 如果指定sheet名,则取指定sheet中的内容.
            sheet = workbook.getSheet(sheetName);
        }
        if (sheet == null)
        {
            // 如果传入的sheet名不存在则默认指向第1个sheet.
            sheet = workbook.getSheetAt(0);
        }
        int rows = sheet.getPhysicalNumberOfRows();

        if (rows > 0)
        {
            // 有数据时才处理 得到类的所有field.
            Field[] allFields = clazz.getDeclaredFields();
            // 定义一个map用于存放列的序号和field.
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();
            // 定义一个map用于存放列的序号和日期格式.
            Map<Integer, String> formatterMap = new HashMap<Integer, String>();
           
            for (Field field : allFields)
            {
                // 将有注解的field存放到map中.
                if (field.isAnnotationPresent(Excel.class))
                {
                	Excel attr = field.getAnnotation(Excel.class);
                	//判断字段是否需要导入
                	if(attr.isImport()) {
                		int col = getExcelCol(attr.column());// 获得列号
                    	// 设置类的私有字段属性可访问.
                        field.setAccessible(true);
                        fieldsMap.put(col, field);
                        formatterMap.put(col, attr.formatter());
                	}
                }
            }
            for (int i = 1; i < rows; i++)
            {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                if(row==null) {
                	continue;
                }
//                int cellNum = row.getPhysicalNumberOfCells();
                int cellNum = row.getLastCellNum();
                T entity = null;
                for (int j = 0; j < cellNum; j++)
                {
                    Cell cell = row.getCell(j);
                    if (cell == null)
                    {
                        continue;
                    }
                    else
                    {
                        // 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了
                        row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                        cell = row.getCell(j);
                    }

                    String c = cell.getStringCellValue();
                    if (StringUtils.isEmpty(c))
                    {
                        continue;
                    }

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = fieldsMap.get(j);
                    if(field==null) {
                    	continue;
                    }
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType)
                    {
                        field.set(entity, String.valueOf(c));
                    }
                    else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType))
                    {
                        field.set(entity, Integer.parseInt(c));
                    }
                    else if ((Long.TYPE == fieldType) || (Long.class == fieldType))
                    {
                        field.set(entity, Long.valueOf(c));
                    }
                    else if ((Float.TYPE == fieldType) || (Float.class == fieldType))
                    {
                        field.set(entity, Float.valueOf(c));
                    }
                    else if ((Short.TYPE == fieldType) || (Short.class == fieldType))
                    {
                        field.set(entity, Short.valueOf(c));
                    }
                    else if ((Double.TYPE == fieldType) || (Double.class == fieldType))
                    {
                        field.set(entity, Double.valueOf(c));
                    }
                    else if (Character.TYPE == fieldType)
                    {
                        if ((c != null) && (c.length() > 0))
                        {
                            field.set(entity, Character.valueOf(c.charAt(0)));
                        }
                    }
                    else if (java.util.Date.class == fieldType)
                    {
                        /*if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                        {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            cell.setCellValue(sdf.format(cell.getNumericCellValue()));
                            c = sdf.format(cell.getNumericCellValue());
                        }
                        else
                        {
                            c = cell.getStringCellValue();
                        }*/
                    	Date date = null;
                    	if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                        {
                    		//// 处理日期格式、时间格式
                    		if(HSSFDateUtil.isCellDateFormatted(cell)) {
                    			date = cell.getDateCellValue();
                    		}else {
                    			date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    		}
                        }
                        else
                        {
                        	//处理字符串日期
                            c = cell.getStringCellValue();
                            String formatter = formatterMap.get(j);
                            SimpleDateFormat sdf = new SimpleDateFormat(formatter);
                            try{
                            	date = sdf.parse(c);
                            }catch (Exception e) {
								try {
									double dateValue = Double.parseDouble(c);
									date = DateUtil.getJavaDate(dateValue);
								}catch (Exception e2) {
									
								}
							}
                        }
                    	field.set(entity, date);
                    }
                    else if (java.math.BigDecimal.class == fieldType)
                    {
                        c = cell.getStringCellValue();
                    }
                }
                if (entity != null)
                {
                    list.add(entity);
                }
            }
        }

        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName 工作表的名称
     */
    public AjaxResult exportExcel(List<T> list, String sheetName)
    {
        // 得到所有定义字段
        Field[] allFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<Field>();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields)
        {
            if (field.isAnnotationPresent(Excel.class))
            {
            	//判断字段是否需要导出
            	Excel annotation = field.getAnnotation(Excel.class);
            	if(annotation.isExport()) {
            		fields.add(field);
            	}
            }
        }

        // 产生工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // excel2003中每个sheet中最多有65536行
        int sheetSize = 65536;
        // 取出一共有多少个sheet.
        double sheetNo = Math.ceil(list.size() / sheetSize);
        for (int index = 0; index <= sheetNo; index++)
        {
            // 产生工作表对象
            HSSFSheet sheet = workbook.createSheet();
            if (sheetNo == 0)
            {
                workbook.setSheetName(index, sheetName);
            }
            else
            {
                // 设置工作表的名称.
                workbook.setSheetName(index, sheetName + index);
            }
            HSSFRow row;
            HSSFCell cell; // 产生单元格

            // 产生一行
            row = sheet.createRow(0);
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++)
            {
                Field field = fields.get(i);
                Excel attr = field.getAnnotation(Excel.class);
                // 创建列
                cell = row.createCell(i);
                // 设置列中写入内容为String类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                if (attr.name().indexOf("注：") >= 0)
                {
                    HSSFFont font = workbook.createFont();
                    font.setColor(HSSFFont.COLOR_RED);
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                    sheet.setColumnWidth(i, 6000);
                }
                else
                {
                    HSSFFont font = workbook.createFont();
                    // 粗体显示
                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                    // 选择需要用到的字体格式
                    cellStyle.setFont(font);
                    cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                    // 设置列宽
                    sheet.setColumnWidth(i, 3766);
                }
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);

                // 写入列名
                cell.setCellValue(attr.name());

                // 如果设置了提示信息则鼠标放上去提示.
                if (StringUtils.isNotEmpty(attr.prompt()))
                {
                    // 这里默认设了2-101列提示.
                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, i, i);
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0)
                {
                    // 这里默认设了2-101列只能选择不能输入.
                    setHSSFValidation(sheet, attr.combo(), 1, 100, i, i);
                }
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            for (int i = startNo; i < endNo; i++)
            {
                row = sheet.createRow(i + 1 - startNo);
                // 得到导出对象.
                T vo = (T) list.get(i);
                for (int j = 0; j < fields.size(); j++)
                {
                    // 获得field.
                    Field field = fields.get(j);
                    // 设置实体类私有属性可访问
                    field.setAccessible(true);
                    Excel attr = field.getAnnotation(Excel.class);
                    try
                    {
                        // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport())
                        {
                            // 创建cell
                            cell = row.createCell(j);
                            cell.setCellStyle(cs);
                            try
                            {
                                if (String.valueOf(field.get(vo)).length() > 10)
                                {
                                    throw new Exception("长度超过10位就不用转数字了");
                                }
                                // 如果可以转成数字则导出为数字类型
                                BigDecimal bc = new BigDecimal(String.valueOf(field.get(vo)));
                                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                                cell.setCellValue(bc.doubleValue());
                            }
                            catch (Exception e)
                            {
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                if (vo == null)
                                {
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue("");
                                }
                                else
                                {
                                    // 如果数据存在就填入,不存在填入空格.
                                    cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));
                                }

                            }
                        }
                    }
                    catch (Exception e)
                    {
                        log.error("导出Excel失败{}", e.getMessage());
                    }
                }
            }
        }
        try
        {
            String filename = encodingFilename(sheetName);
            //路径不存在,创建文件夹
            File file = new File(ManageConfig.getExcelPath());
            if(!file.exists()) {
            	file.mkdirs();
            }
            OutputStream out = new FileOutputStream(ManageConfig.getExcelPath() + filename);
            workbook.write(out);
            out.close();
            return AjaxResult.success(ManageConfig.getExcelUrl()+filename);
        }
        catch (Exception e)
        {
            log.error("关闭flush失败{}", e.getMessage());
            return AjaxResult.error("导出Excel失败，请联系网站管理员！");
        }
    }

    /**
     * 设置单元格上提示
     * 
     * @param sheet 要设置的sheet.
     * @param promptTitle 标题
     * @param promptContent 内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow,
            int endRow, int firstCol, int endCol)
    {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation dataValidationView = new HSSFDataValidation(regions, constraint);
        dataValidationView.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(dataValidationView);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     * 
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow,
            int firstCol, int endCol)
    {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation dataValidationList = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidationList);
        return sheet;
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xls";
        return filename;
    }

    public String getfile() throws FileNotFoundException
    {
        return ResourceUtils.getURL("classpath:").getPath() + "static/file/";
    }
    
    /**
	 * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
	 * 
	 * @param col
	 */
	public static int getExcelCol(String col) {
		col = col.toUpperCase();
		// 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = col.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}
	
	/**
	 * 单元格添加下拉菜单(不限制菜单可选项个数)<br/>
	 * [注意：此方法会添加隐藏的sheet，可调用getDataSheetInDropMenuBook方法获取用户输入数据的未隐藏的sheet]<br/>
	 * [待添加下拉菜单的单元格 -> 以下简称：目标单元格]
	 * @param @param workbook 
	 * @param @param tarSheet 目标单元格所在的sheet
	 * @param @param menuItems 下拉菜单可选项数组
	 * @param @param firstRow 第一个目标单元格所在的行号(从0开始)
	 * @param @param lastRow 最后一个目标单元格所在的行(从0开始)
	 * @param @param column 待添加下拉菜单的单元格所在的列(从0开始)
	 */
	 public static void addDropDownList(HSSFWorkbook workbook, HSSFSheet tarSheet, String[] menuItems, int firstRow, int lastRow, int column) throws Exception
	 {
		 if(null == workbook){
			 throw new Exception("workbook为null");
		 }
		 if(null == tarSheet){
			 throw new Exception("待添加菜单的sheet为null");
		 }
		 
		 //必须以字母开头，最长为31位
		 String hiddenSheetName = "a" + UUID.randomUUID().toString().replace("-", "").substring(1, 31);
		 //excel中的"名称"，用于标记隐藏sheet中的用作菜单下拉项的所有单元格
		 String formulaId = "form" + UUID.randomUUID().toString().replace("-", "");
	     HSSFSheet hiddenSheet = workbook.createSheet(hiddenSheetName);//用于存储 下拉菜单数据
	     //存储下拉菜单项的sheet页不显示
	     workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);
	     
	     HSSFRow row = null;
	     HSSFCell cell = null;
	     //隐藏sheet中添加菜单数据
	     for (int i = 0; i < menuItems.length; i++)
	     {
	         row = hiddenSheet.createRow(i);
	         //隐藏表的数据列必须和添加下拉菜单的列序号相同，否则不能显示下拉菜单
	         cell = row.createCell(column);
	         cell.setCellValue(menuItems[i]);
	     }
	     
	     HSSFName namedCell = workbook.createName();//创建"名称"标签，用于链接
	     namedCell.setNameName(formulaId);
	     namedCell.setRefersToFormula(hiddenSheetName + "!A$1:A$" + menuItems.length);
	     HSSFDataValidationHelper dvHelper = new HSSFDataValidationHelper(tarSheet);
	     DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint(formulaId);
	     
	     CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, column, column);
	     HSSFDataValidation validation = (HSSFDataValidation)dvHelper.createValidation(dvConstraint, addressList);//添加菜单(将单元格与"名称"建立关联)
         tarSheet.addValidationData(validation);
	 }
	 
 
	/**
	 * 从调用addDropDownList后添加下拉菜单的Workbook中获取用户输入数据的shee列表
	 * @param book
	 * @return
	 */
	public static List<HSSFSheet> getDataSheetInDropMenuBook(HSSFWorkbook book){
		return getUnHideSheets(book);
	}
	
	/**
	 * 获取所有未隐藏的sheet
	 * @param book
	 * @return
	 */
	public static List<HSSFSheet> getUnHideSheets(HSSFWorkbook book){
		List<HSSFSheet> ret = new ArrayList<HSSFSheet>();
		if(null == book){
			return ret;
		}
		
		int sheetCnt = book.getNumberOfSheets();
		for (int i = 0; i < sheetCnt; i++) {
			if(!book.isSheetHidden(i)){
				ret.add(book.getSheetAt(i));
			}
		}
		
		return ret;
	}
	
	/**
	 * 获取图片和位置 (xls)
	 * @param sheet
	 * @return
	 * @throws IOException
	 */
	public static Map<String, HSSFPictureData> getPictures (HSSFSheet sheet) throws IOException {
	    Map<String, HSSFPictureData> map = new HashMap<String, HSSFPictureData>();
	    HSSFPatriarch hssfPatriarch = sheet.getDrawingPatriarch();
	    if(hssfPatriarch!=null) {
	    	List<HSSFShape> list = hssfPatriarch.getChildren();
		    for (HSSFShape shape : list) {
		        if (shape instanceof HSSFPicture) {
		            HSSFPicture picture = (HSSFPicture) shape;
		            HSSFClientAnchor cAnchor = (HSSFClientAnchor)picture.getAnchor();
		            HSSFPictureData pdata = picture.getPictureData();
		            String key = cAnchor.getRow1()+""; // 行号
		            map.put(key, pdata);
		        }
		    }
	    }
	    return map;
	}
	
	/*
	 * 将图片保存到指定位置
	 */
	 public static String printImg(PictureData pictureData,String path,String picName) throws IOException {   
		 FileOutputStream out = null;
		 try {
        	// 获取图片格式  
            String ext = pictureData.suggestFileExtension();  
            byte[] data = pictureData.getData();   
            out = new FileOutputStream(path+picName+ "."+ ext);
        	out.write(data);
        	out.close();
        	out=null;
        	return path+picName+ "."+ ext;
        }catch (Exception e) {
        	log.error("保存图片失败",e);
        	if(out!=null) {
            	out.close();
            	out=null;
            }
        	return null;
		}
		
    }
	 
	 /**
	  * 将list插入指定sheet
	  * 
	  * @param sheet 要插入的sheet
	  * @param list 要插入的list
	  * @param startRow 开始行
	  * @param startColumn 开始列
	  * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	  */
	public int insertListToSheet(HSSFWorkbook workbook,HSSFSheet sheet,List<T> list,int startRow,int startColumn,String title) throws IllegalArgumentException, IllegalAccessException
	{
		HSSFRow row = null;
		HSSFCell cell = null;
		 //设置标题行
	    if(StringUtils.isNotEmpty(title)) {
	    	row = sheet.getRow(startRow);
			if(row==null) {
			    row = sheet.createRow(startRow);
			}
	    	cell = row.getCell(startColumn);
     	    if(cell==null) {
         		cell = row.createCell(startColumn);
     	    }
	    	Integer columnNumber = getExportColumnNumber();
	    	CellRangeAddress region = new CellRangeAddress(startRow, startRow, startColumn, columnNumber-1);
			sheet.addMergedRegion(region);
			cell.setCellValue(title);
			
		    //设置标题单元格格式
			HSSFCellStyle titleCellStyle = workbook.createCellStyle();
			titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			HSSFFont font = workbook.createFont();
	        font.setColor(HSSFFont.COLOR_RED);
	        titleCellStyle.setFont(font);
	        titleCellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
	        cell.setCellStyle(titleCellStyle);
	        startRow++;
	    }
		//字段单元格样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		//标题单元格样式
		HSSFCellStyle style = workbook.createCellStyle();
		row = sheet.getRow(startRow);
		if(row==null) {
		    row = sheet.createRow(startRow);
		}
	    // 得到所有定义字段
	    Field[] allFields = clazz.getDeclaredFields();
	    List<Field> fields = new ArrayList<Field>();
	    // 得到所有field并存放到一个list中.
	    int count=0;
	    for (Field field : allFields)
	    {
	        if (field.isAnnotationPresent(Excel.class))
	        {
	        	Excel annotation = field.getAnnotation(Excel.class);
	        	if(annotation.isExport()) {
	        		field.setAccessible(true);
	        		fields.add(field);
	        		String name = annotation.name();
	        		cell = row.getCell(startColumn+count);
	         	    if(cell==null) {
		         		cell = row.createCell(startColumn+count);
	         	    }
	         	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	         	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	         	    if (annotation.name().indexOf("注：") >= 0)
	                {
						HSSFFont font = workbook.createFont();
						font.setColor(HSSFFont.COLOR_RED);
						style.setFont(font);
						style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
						sheet.setColumnWidth(count, 6000);
	                }
	                else
	                {
	                    HSSFFont font = workbook.createFont();
	                    // 粗体显示
	                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	                    // 选择需要用到的字体格式
	                    style.setFont(font);
	                    style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
	                    // 设置列宽
	                    sheet.setColumnWidth(count, 3766);
	                }
	         	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	         	   	style.setWrapText(true);
	                cell.setCellStyle(style);
	         	    cell.setCellValue(name);
	         	    count++;
	        	}
	        } 
	    }
	    startRow++;
	    
	    for (T obj : list) {
		   row = sheet.getRow(startRow);
		   if(row==null) {
			   row = sheet.createRow(startRow);
		   }
		   for (int j = 0; j < fields.size(); j++) {
			   cell = row.getCell(startColumn+j);
        	   if(cell==null) {
        		   cell = row.createCell(startColumn+j);
        	   }
               Field field = fields.get(j);
               try
               {
                   if (String.valueOf(field.get(obj)).length() > 10)
                   {
                       throw new Exception("长度超过10位就不用转数字了");
                   }
                   // 如果可以转成数字则导出为数字类型
                   BigDecimal bc = new BigDecimal(String.valueOf(field.get(obj)));
                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                   cell.setCellValue(bc.doubleValue());
                   cell.setCellStyle(cellStyle);
               }
               catch (Exception e)
               {
                   cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                   if (obj == null)
                   {
                       // 如果数据存在就填入,不存在填入空格.
                       cell.setCellValue("");
                   }
                   else
                   {
                       // 如果数据存在就填入,不存在填入空格.
                       cell.setCellValue(field.get(obj) == null ? "" : String.valueOf(field.get(obj)));
                   }
                   cell.setCellStyle(cellStyle);
               }
		   }
		   startRow++;
		}
	    return startRow;
	   
//	   for (int i = 0; i < list.size(); i++) {
//		   T obj = list.get(i);
//		   row = sheet.getRow(startRow+i+1);
//		   if(row==null) {
//			   row = sheet.createRow(startRow+i+1);
//		   }
//		   for (int j = 0; j < fields.size(); j++) {
//			   cell = row.getCell(startColumn+j);
//        	   if(cell==null) {
//        		   cell = row.createCell(startColumn+j);
//        	   }
//               Field field = fields.get(j);
//               try
//               {
//                   if (String.valueOf(field.get(obj)).length() > 10)
//                   {
//                       throw new Exception("长度超过10位就不用转数字了");
//                   }
//                   // 如果可以转成数字则导出为数字类型
//                   BigDecimal bc = new BigDecimal(String.valueOf(field.get(obj)));
//                   cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//                   cell.setCellValue(bc.doubleValue());
//                   cell.setCellStyle(cellStyle);
//               }
//               catch (Exception e)
//               {
//                   cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                   if (obj == null)
//                   {
//                       // 如果数据存在就填入,不存在填入空格.
//                       cell.setCellValue("");
//                   }
//                   else
//                   {
//                       // 如果数据存在就填入,不存在填入空格.
//                       cell.setCellValue(field.get(obj) == null ? "" : String.valueOf(field.get(obj)));
//                   }
//                   cell.setCellStyle(cellStyle);
//               }
//		   }
//	   }
	        
	}
	
	/**
	 * 返回需要导出的字段个数
	 * 
	 * @return
	 */
	public Integer getExportColumnNumber() {
		int count=0;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if(field.isAnnotationPresent(Excel.class)) {
				Excel annotation = field.getAnnotation(Excel.class);
				if(annotation.isExport()) {
					count++;
				}
			}
		}
		return count++;
	}

	//多级联动使用
	/**
	 * 
	 * @param offset 偏移量，如果给0，表示从A列开始，1，就是从B列
	 * @param rowId 第几行
	 * @param colCount 一共多少列
	 * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1
	 * 
	 * @author denggonghai 2016年8月31日 下午5:17:49
	 */
	public static String getRange(int offset, int rowId, int colCount) {
		char start = (char)('A' + offset);
		if (colCount <= 25) {
			char end = (char)(start + colCount - 1);
			return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;
		} else {
			char endPrefix = 'A';
			char endSuffix = 'A';
			if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）
				if ((colCount - 25) % 26 == 0) {// 边界值
					endSuffix = (char)('A' + 25);
				} else {
					endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
				}
			} else {// 51以上
				if ((colCount - 25) % 26 == 0) {
					endSuffix = (char)('A' + 25);
					endPrefix = (char)(endPrefix + (colCount - 25) / 26 - 1);
				} else {
					endSuffix = (char)('A' + (colCount - 25) % 26 - 1);
					endPrefix = (char)(endPrefix + (colCount - 25) / 26);
				}
			}
			return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;
		}
	}
	
	public static void setDataValidation(HSSFSheet sheet,String offset,int row,int column) {
		DVConstraint formula = DVConstraint.createFormulaListConstraint("INDIRECT($"+offset+"$" + (row+1) + ")");
//		DVConstraint formula = DVConstraint.createFormulaListConstraint("INDIRECT($"+offset+"$" + 2 + ")");
		CellRangeAddressList rangeAddressList = new CellRangeAddressList(row, row, column,column);
		DataValidation cacse = new HSSFDataValidation(rangeAddressList, formula);
		cacse.createErrorBox("error", "请通过下拉框选择");
		sheet.addValidationData(cacse);
	}
	
	public static void main(String[] args) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Sheet1");
		String[] params = new String[1000];
		for (int i = 0; i < 1000; i++) {
			params[i]="下拉框选项"+i;
		}
		addDropDownList(wb, sheet, params, 0, 2, 0);
		wb.write(new FileOutputStream("d://123456.xls"));
	}
}