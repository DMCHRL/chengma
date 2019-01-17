package com.chengma.devplatform.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * excel导出工具类
 */
public class ExcelExport {

    private Workbook workbook;

    private Sheet sheet;

    private int columnLen = 0;

    private List<ColumnAttr> columnList = new ArrayList<>();

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

    /**
     * 创建工作表
     *
     * @param name 表名
     */
    public void createSheet(String name) {
        sheet = workbook.createSheet(name);
    }

    /**
     * 切换工作表
     *
     * @param name 表名
     */
    public void switchSheet(String name) {
        sheet = workbook.getSheet(name);
    }

    /**
     * 设置列数
     *
     * @param columnLen 列数
     */
    public void setColumnLen(int columnLen) {
        this.columnLen = columnLen;
    }

    /**
     * 设置列宽
     *
     * @param colNum 列序号
     * @param width  宽度
     */
    public void setColumnWidth(int colNum, int width) {
        sheet.setColumnWidth(colNum, width * 256);
    }

    /**
     * 设置行高
     *
     * @param rowNum 行序号
     * @param height 高度
     */
    public void setRowHeight(int rowNum, int height) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        row.setHeightInPoints(height);
    }

    /**
     * 设置数据列表
     *
     * @param list       需要导出的数据列表
     * @param rowSetting 行处理 有内容设置：contentSetting和列设置columnSetting
     * @param <T>        泛型
     * @throws Exception 导出异常
     */
    public <T> void setList(List<T> list, IRowSetting rowSetting)
            throws Exception {
        boolean hasHead = rowSetting.columnSetting(columnList);
        String[] getMethod = this.convertGetMethod(columnList);
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
//        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerStyle.setFont(headerFont);
        int start = rowSetting.startRow();
        if (start < 0) {
            throw new Exception("开始行不能少于0！");
        }
        if (hasHead) {
            Row header = sheet.createRow(start);
            short col = 0;
            for (ColumnAttr head : columnList) {
                Cell headerCell = header.createCell(col);
                if (head.getCellStyle() == null) {
                    headerCell.setCellStyle(headerStyle);
                } else {
                    headerCell.setCellStyle(head.getCellStyle());
                }
                headerCell.setCellValue(head.getHeaderName());
                if (head.getWidth() > 0) {
                    sheet.setColumnWidth(col, head.getWidth() * 256);
                }
                col++;
            }
            start++;
        }
        columnLen = columnList.size();
        int i = 0;
        if (list != null && list.size() > 0) {
            T first = list.get(0);
            Class<?> objClass = first.getClass();
            Method[] realMethod = new Method[columnLen];
            for (int n = 0; n < columnLen; n++) {
                Method met = objClass.getMethod(getMethod[n]);
                realMethod[n] = met;
            }

            for (T obj : list) {
                Row contentRow = sheet.createRow(start);
                rowSetting.contentSetting(obj, i);
                for (short j = 0; j < columnLen; j++) {
                    Method method = realMethod[j];
                    Object getResult = method.invoke(obj);
                    if (getResult != null) {
                        Cell contentCell = null;
                        if (contentRow.getCell(j) == null) {
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

    /**
     * 导出
     *
     * @param fileName 自定义文件名
     * @param response HTTP响应
     */
    public void export(String fileName, HttpServletResponse response) {
        String postfix = ".xlsx";
        if (!fileName.contains(postfix)) {
            fileName += postfix;
        }
        OutputStream out = null;
        try {
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并单元格
     *
     * @param firstRow 起始行
     * @param lastRow  末行
     * @param firstCol 起始列
     * @param lastCol  末列
     */
    public void mergeCell(int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(region);
    }

    /**
     * 设置内容
     *
     * @param cell 单元格
     * @param obj  内容
     */
    public void setValue(Cell cell, Object obj) {
        this.setCellValue(cell, obj);
    }

    /**
     * 获取单元格
     *
     * @param rowNum 行序号
     * @param colNum 列序号
     * @return Cell
     */
    public Cell getCell(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        return cell;
    }

    /**
     * 获取单元格样式
     *
     * @param cell 单元格
     * @return XSSFCellStyle
     */
    public XSSFCellStyle getCellStyle(XSSFCell cell) {
        return cell.getCellStyle();
    }

    /**
     * 创建单元格样式
     *
     * @return CellStyle
     */
    public CellStyle createCellStyle() {
        return workbook.createCellStyle();
    }

    /**
     * 创建字体
     *
     * @return Font
     */
    public Font createFont() {
        return workbook.createFont();
    }

    private String[] convertGetMethod(List<ColumnAttr> list) throws Exception {
        String[] getMethods = new String[list.size()];
        int i = 0;
        for (ColumnAttr attr : list) {
            if (StringUtils.isBlank(attr.getAttribute())) {
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
        if (obj != null) {
            Class<?> type = obj.getClass();
            String typeName = type.getName();
            if ("java.lang.String".equals(typeName)) {
                cell.setCellValue((String) obj);
            } else if ("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
                cell.setCellValue((Integer) obj);
            } else if ("java.lang.Double".equals(typeName) || "double".equals(typeName)) {
                cell.setCellValue((Double) obj);
            } else if ("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
                cell.setCellValue((Long) obj);
            } else if ("java.lang.Short".equals(typeName) || "short".equals(typeName)) {
                cell.setCellValue((Short) obj);
            } else if ("java.lang.Float".equals(typeName) || "float".equals(typeName)) {
                cell.setCellValue((Float) obj);
            } else if ("java.lang.Boolean".equals(typeName) || "boolean".equals(typeName)) {
                cell.setCellValue((Boolean) obj);
            } else if ("java.sql.Timestamp".equals(typeName)) {
                cell.setCellValue(DateUtil.formatSimpleDate((Date) obj));
            } else if ("java.math.BigDecimal".equals(typeName)) {
                cell.setCellValue(obj.toString());
            } else {
                cell.setCellValue((String) obj);
            }
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }
}
