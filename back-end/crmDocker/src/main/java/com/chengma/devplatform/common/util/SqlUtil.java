package com.chengma.devplatform.common.util;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2018/8/2.
 */
public class SqlUtil {

    /**
     * 拼凑order by 语句
     * @param column
     * @param sort
     * @return
     */
    public static String orderBySql(String column,String sort){
        if(StringUtils.isBlank(column))return "";
        StringBuffer sql=new StringBuffer(" order by ");
        sql.append(column);
        if(StringUtils.isNotBlank(sort)){
            if(sort.equals(DevplatformConstants.SORT_ASC)){
                sql.append(" "+DevplatformConstants.SORT_ASC);
            }else if(sort.equals(DevplatformConstants.SORT_DESC)){
                sql.append(" "+DevplatformConstants.SORT_DESC);
            }
        }
        return sql.toString();
    }
}
