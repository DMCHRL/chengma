package com.chengma.devplatform.common.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

import java.text.DecimalFormat;

/**
 * 得到Excel表中的值
 * Created by Administrator on 2017/9/11.
 */
public class ExcelGetValue {

    /**
     * 得到Excel表中的值
     * @param hssfCell
     * Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
    @SuppressWarnings("static-access")
    public String getValue(Cell hssfCell) {

        if (hssfCell == null) {
            return "";
        }

        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {

            if(HSSFDateUtil.isCellDateFormatted(hssfCell)){
                String cellValue = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue()).toString();
                return cellValue;
            }else{
                // 返回数值类型的值
                // return String.valueOf(hssfCell.getNumericCellValue());
                // 去除科学计算法
                DecimalFormat df = new DecimalFormat("0");
                String cellValue = df.format(hssfCell.getNumericCellValue());
                return cellValue;
            }

        }else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}
