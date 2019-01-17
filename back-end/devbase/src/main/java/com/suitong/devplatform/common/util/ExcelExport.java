package com.suitong.devplatform.common.util;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExport {

	private Workbook workbook;
	
	private Sheet sheet;
	
	private int columnLen = 0;
	
	private List<ColumnAttr> columnList = new ArrayList<ColumnAttr>();
	
	public ExcelExport() {
		workbook = new XSSFWorkbook();
	}

	public ExcelExport(String path, boolean cash) {
		try {
			workbook = new XSSFWorkbook(path);
			/*if(cash) {
				workbook = new SXSSFWorkbook((XSSFWorkbook) workbook, 100);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createSheet(String name) {
		sheet = workbook.createSheet(name);
	}
	
	public void switchSheet(String name) {
		sheet = workbook.getSheet(name);
	}
	
	public void setColumnLen(int columnLen) {
		this.columnLen = columnLen;
	}
	
	public void setColumnWidth(int colNum, int width) {
		sheet.setColumnWidth(colNum, width * 256);
	}

	public void setRowHeight(int rowNum, int height) {
		Row row = sheet.getRow(rowNum);
		if(row == null) {
			row = sheet.createRow(rowNum);
		}
		row.setHeightInPoints(height);
	}
	
	public <T> void setList(List<T> list, IRowSetting rowSetting)
			throws Exception {
		boolean hasHead = rowSetting.columnSetting(columnList);
		String[] getMethod = this.convertGetMethod(columnList);
		CellStyle headerStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		int start = rowSetting.startRow();
		if(start < 0) {
			throw new Exception("开始行不能少于0！");
		}
		if(hasHead) {
			Row header = sheet.createRow(start);
			short col = 0;
			for(ColumnAttr head : columnList) {
				Cell headerCell = header.createCell(col);
				if(head.getCellStyle() == null) {
					headerCell.setCellStyle(headerStyle);
				} else {
					headerCell.setCellStyle(head.getCellStyle());
				}
				headerCell.setCellValue(head.getHeaderName());
				if(head.getWidth() > 0) {
					sheet.setColumnWidth(col, head.getWidth() * 256);
				}
				col++;
			}
			start++;
		}
		columnLen = columnList.size();
		int i = 0;
		if(list != null && list.size() > 0) {
			T first = list.get(0);
			Class<?> objClass = first.getClass();
			Method[] realMethod = new Method[columnLen];
			for(int n = 0; n < columnLen; n++) {
				Method met = objClass.getMethod(getMethod[n]);
				realMethod[n] = met;
			}
			
			for(T obj : list) {
				Row contentRow = sheet.createRow(start);
				rowSetting.contentSetting(obj, i);
				for(short j = 0; j < columnLen; j++) {
					Method method = realMethod[j];
					Object getResult = method.invoke(obj);
					if(getResult != null) {
						Cell contentCell = null;
						if(contentRow.getCell(j) == null) {
							contentCell = contentRow.createCell(j);
						} else {
							contentCell = contentRow.getCell(j);
						}
						this.setCellValue(contentCell, getResult);
					}
				}
				i++;
				start++;
			}
		}
	}
	
	public void export(String fileName, HttpServletResponse response) {
		if(fileName.indexOf(".xlsx") == -1) {
			fileName += ".xlsx";
		}
		OutputStream out = null;
		try {
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
			//response.setContentType("application/msexcel");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void mergeCell(int firstRow, int lastRow, int firstCol, int lastCol) {
		CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		sheet.addMergedRegion(region);
	}
	
	public void setValue(Cell cell, Object obj) {
		this.setCellValue(cell, obj);
	}
	
	public Cell getCell(int rowNum, int colNum) {
		Row row = sheet.getRow(rowNum);
		if(row == null) {
			row = sheet.createRow(rowNum);
		}
		Cell cell = row.getCell(colNum);
		if(cell == null) {
			cell = row.createCell(colNum);
		}
		return cell;
	}
	
	public XSSFCellStyle getCellStyle(XSSFCell cell) {
		XSSFCellStyle style = cell.getCellStyle();
		return style;
	}
	
	public CellStyle createCellStyle() {
		return workbook.createCellStyle();
	}
	
	public Font createFont() {
		return workbook.createFont();
	}
	
	private String[] convertGetMethod(List<ColumnAttr> list) throws Exception {
		String[] getMethods = new String[list.size()];
		int i = 0;
		for(ColumnAttr attr : list) {
			if(StringUtils.isBlank(attr.getAttribute())) {
				throw new Exception("列对应的属性不能为空！");
			}
			StringBuffer result = new StringBuffer();
			result.append("get");
			result.append(attr.getAttribute().substring(0, 1).toUpperCase());
			result.append(attr.getAttribute().substring(1));
			getMethods[i] = result.toString();
			i++;
		}
		return getMethods;
	}
	
	private void setCellValue(Cell cell, Object obj) {
		if(obj != null) {
			Class<?> type = obj.getClass();
			String typeName = type.getName();
			if("java.lang.String".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue((String) obj);
			} else if("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue((Integer) obj);
			} else if("java.lang.Double".equals(typeName) || "double".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue((Double) obj);
			} else if("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue((Long) obj);
			} else if("java.lang.Short".equals(typeName) || "short".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue((Short) obj);
			} else if("java.lang.Float".equals(typeName) || "float".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell.setCellValue((Float) obj);
			} else if("java.lang.Boolean".equals(typeName) || "boolean".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
				cell.setCellValue((Boolean) obj);
			} else if("java.sql.Timestamp".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(DateUtil.formatSimpleDate((Date)obj));
			} else if("java.math.BigDecimal".equals(typeName)) {
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(obj.toString());
			} else {
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue((String) obj);
			}
		}
	}
	
}
