package com.chengma.devplatform.common.util;

import java.util.List;

/**
 * excel表格行内容设置
 */
public interface IRowSetting {

    /**
     * 设置首行位置
     *
     * @return 行首序号
     */
    int startRow();

    /**
     * 设置列头
     *
     * @param list 列属性列表
     * @return true：显示列头  false：不显示列头
     */
    boolean columnSetting(List<ColumnAttr> list);

    /**
     * 设置行内容
     *
     * @param obj 内容
     * @param row 行序号
     * @return 内容
     */
    Object contentSetting(Object obj, int row);

}
