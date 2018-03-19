package com.wme.common.utils.poi;

import com.wme.common.utils.ReflectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Title: 基于POI Excel 导出通用工具类
 * @Auther: ming
 * @Date: 2017/10/21
 * @Version: 1.0
 */
public class PoiExportExcel {
	/** 最大行数 */
	private final static int MAX_ROW = 50000;
	/** 默认日期类型格式 */
	private final static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private HSSFWorkbook wb;
	private CellStyle titleStyle; // 标题行样式
	private Font titleFont; // 标题行字体
	private CellStyle dateStyle; // 日期行样式
	private Font dateFont; // 日期行字体
	private CellStyle headStyle; // 表头行样式
	private Font headFont; // 表头行字体
	private CellStyle contentStyle; // 内容行样式
	private Font contentFont; // 内容行字体

	/**
	 * 初始化所有的方法，初始化Excel所有的样式
	 */
	private void init(InputStream in) throws IOException {
		if(in != null){
			wb = new HSSFWorkbook(in);
		}else{
			wb = new HSSFWorkbook();
		}

		titleFont = wb.createFont();
		titleStyle = wb.createCellStyle();
		dateStyle = wb.createCellStyle();
		dateFont = wb.createFont();
		headStyle = wb.createCellStyle();
		headFont = wb.createFont();
		contentStyle = wb.createCellStyle();
		contentFont = wb.createFont();

		initTitleCellStyle();
		initTitleFont();
//		initDateCellStyle();
//		initDateFont();
		initHeadCellStyle();
		initHeadFont();
		initContentCellStyle();
		initContentFont();
	}

	/**
	 * 创建标题行(需合并单元格)
	 */
	private void createTableTitleRow(ExcelEntry setInfo, HSSFSheet[] sheets, int sheetNum) {
		CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, setInfo.getFieldNames().get(sheetNum).length);
		sheets[sheetNum].addMergedRegion(titleRange);
		HSSFRow titleRow = sheets[sheetNum].createRow(0);
		titleRow.setHeight((short) 800);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
	}

	/**
	 * 创建表头行(需合并单元格)
	 */
	private void creatTableHeadRow(ExcelEntry excelEntry, HSSFSheet[] sheets, int sheetNum) {
		// 表头,从0行开始创建
		HSSFRow headRow = sheets[sheetNum].createRow(0);
		headRow.setHeight((short) 350);
		// 列头名称
		for (int num = 0, len = excelEntry.getHeadNames().get(sheetNum).length; num < len; num++) {
			HSSFCell headCell = headRow.createCell(num);
			headCell.setCellStyle(headStyle);
			headCell.setCellValue(excelEntry.getHeadNames().get(sheetNum)[num]);
		}
	}

	/**
	 * 创建内容行
	 * @param contentRow Excel行
	 */
	private HSSFCell[] getCells(HSSFRow contentRow, int num) {
		HSSFCell[] cells = new HSSFCell[num];
		for (int i = 0, len = cells.length; i < len; i++) {
			cells[i] = contentRow.createCell(i);
			cells[i].setCellStyle(contentStyle);
		}
		cells[0].setCellValue(contentRow.getRowNum());
		return cells;
	}

	/**
	 * 初始化标题行样式
	 */
	private void initTitleCellStyle() {
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleStyle.setFont(titleFont);
		titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
	}

	/**
	 * 初始化日期行样式
	 */
	private void initDateCellStyle() {
		dateStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dateStyle.setFont(dateFont);
		dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
	}

	/**
	 * 初始化表头行样式
	 */
	private void initHeadCellStyle() {
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headStyle.setFont(headFont);
		headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
		headStyle.setBorderTop(BorderStyle.MEDIUM);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		headStyle.setTopBorderColor(IndexedColors.BLUE.index);
		headStyle.setBottomBorderColor(IndexedColors.BLUE.index);
		headStyle.setLeftBorderColor(IndexedColors.BLUE.index);
		headStyle.setRightBorderColor(IndexedColors.BLUE.index);
	}

	/**
	 * 初始化内容行样式
	 */
	private void initContentCellStyle() {
		contentStyle.setAlignment(HorizontalAlignment.CENTER);
		contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		contentStyle.setFont(contentFont);
		contentStyle.setBorderTop(BorderStyle.THIN);
		contentStyle.setBorderBottom(BorderStyle.THIN);
		contentStyle.setBorderLeft(BorderStyle.THIN);
		contentStyle.setBorderRight(BorderStyle.THIN);
		contentStyle.setTopBorderColor(IndexedColors.BLUE.index);
		contentStyle.setBottomBorderColor(IndexedColors.BLUE.index);
		contentStyle.setLeftBorderColor(IndexedColors.BLUE.index);
		contentStyle.setRightBorderColor(IndexedColors.BLUE.index);
		contentStyle.setWrapText(true); // 字段换行
	}

	/**
	 * 初始化标题行字体
	 */
	private void initTitleFont() {
		titleFont.setFontName("华文楷体");
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBold(true);
		titleFont.setCharSet(Font.DEFAULT_CHARSET);
		titleFont.setColor(IndexedColors.BLUE_GREY.index);
	}

	/**
	 * 初始化日期行字体，暂时不用
	 */
	private void initDateFont() {
		dateFont.setFontName("隶书");
		dateFont.setFontHeightInPoints((short) 10);
		dateFont.setBold(true);
		dateFont.setCharSet(Font.DEFAULT_CHARSET);
		dateFont.setColor(IndexedColors.BLUE_GREY.index);
	}

	/**
	 * 初始化表头行字体
	 */
	private void initHeadFont() {
		headFont.setFontName("宋体");
		headFont.setFontHeightInPoints((short) 10);
		headFont.setBold(true);
		headFont.setCharSet(Font.DEFAULT_CHARSET);
		headFont.setColor(IndexedColors.BLUE_GREY.index);
	}

	/**
	 * 初始化内容行字体
	 */
	private void initContentFont() {
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 10);
		contentFont.setBold(false);
		contentFont.setCharSet(Font.DEFAULT_CHARSET);
		contentFont.setColor(IndexedColors.BLUE_GREY.index);
	}

	/**
	 * 将List中的对象输出为输出流 （一次性,写入）
	 * @param excelEntry  需要导出的实体对象
	 */
	public void exportExcelBySheet(ExcelEntry excelEntry) throws IOException {
		exportExcelBySheet(excelEntry, null, null);
	}
	
	/**
	 * 将List中的对象输出为输出流(excel中追加写入)
	 * @param excelEntry  需要导出的实体对象
	 * @param continueLastSheet 当sheet未达到MAX row的时候，是否追加到当前sheet
	 */
	public void exportExcelBySheet(ExcelEntry excelEntry, InputStream in, Boolean continueLastSheet) throws IOException {
		init(in); // 初始化

		int rowNum = 0;
		HSSFSheet sheet = null;
		if(continueLastSheet != null && continueLastSheet){
			// 取得sheet的数目
			int sheetNumber  = wb.getNumberOfSheets();
			if(sheetNumber > 0){
				sheet = wb.getSheetAt(sheetNumber-1); // 根据index取得sheet对象
				rowNum = sheet.getLastRowNum();
			}
		}

		List<?> dataList = excelEntry.getDataList();
		for (Object obj : dataList) {
			if(rowNum % MAX_ROW == 0){
				sheet = createSheet(wb,excelEntry);
				rowNum = 0;
			}
			putCreateRow(obj,sheet,++rowNum,excelEntry.getFieldNames().get(0));
		}
		// 将Excel 已流的方式输出 
		wb.write(excelEntry.getOut());
	}

	/**
	 * 获取HSSFWorkbook最后一个HSSFSheet
	 * @param wb
	 * @return
	 */
	public HSSFSheet createSheet(HSSFWorkbook wb,ExcelEntry excelEntry) {
		HSSFSheet sheet = wb.createSheet("mySheet" + (wb.getNumberOfSheets()+1));
		/** 创建表头内容 */
		creatTableHeadRow(excelEntry, new HSSFSheet[]{sheet}, 0);
		return sheet;
	}

	public void putCreateRow(Object obj,HSSFSheet sheet,int rowNum,String[] fieldNames) {
		HSSFRow contentRow = sheet.createRow(rowNum);
		// 设置单元格高度
		contentRow.setHeight((short) 400);
		HSSFCell[] cells = getCells(contentRow, fieldNames.length);
		int cellNum = 0;  
		// 循环所有的字段，通过类反射取值
		if (fieldNames != null) {
			for (int num = 0; num < fieldNames.length; num++) {
				Object value = ReflectionUtils.invokeGetterMethod(obj, fieldNames[num]);
				if (value instanceof Date){
					cells[cellNum].setCellValue(value == null ? "" : DEFAULT_DATE_FORMAT.format(value));
				}else{
					cells[cellNum].setCellValue(value == null ? "" : value.toString());
				}
				cellNum++;
			}
		}
	}

}
