package com.chengma.devplatform.service.impl;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ddgui on 2017/11/16.
 */
@Service
@Transactional
public class DBService {

    private final Environment environment;

    private String dbType = null;

    public DBService(Environment environment) {
        this.environment = environment;
        dbType = environment.getProperty("spring.jpa.database");
    }

    public String sysdate() {
        String sql = "";
        if ("oracle".equalsIgnoreCase(dbType)) {
            sql = " sysdate ";
        } else if ("mysql".equalsIgnoreCase(dbType)) {
            sql = " now() ";
        }
        return sql;
    }

    public String date2char(String column, String format) {
        String sql = "";
        if ("oracle".equalsIgnoreCase(dbType)) {
            sql = " to_char(" + column + ",'" + format + "') ";
        } else if ("mysql".equalsIgnoreCase(dbType)) {
            sql = " Date(" + column + ") ";
        }
        return sql;
    }

    public String date2char(String column) {
        String format = "yyyy-MM-dd";
        String sql = date2char(column, format);
        return sql;
    }

    public String inSql(String column, String params) {
        StringBuffer cond = new StringBuffer();
        String[] ids = params.split(",");
        cond.append(" " + column + " in ( ");
        for (int i = 0; i < ids.length; i++) {
            if (i == 0) {
                cond.append(" '" + ids[i] + "' ");
            } else {
                cond.append(" ,'" + ids[i] + "' ");
            }
        }
        cond.append(" ) ");
        return cond.toString();
    }

    /**
     * 拼凑分页sql
     *
     * @param sql         逻辑sql
     * @param page_number 页码
     * @param page_size   每页数量
     * @return sql
     */
    public String paging(String sql, int page_number, int page_size) {
        int offset;
        if ("ORACLE".equalsIgnoreCase(dbType)) {
            offset = (page_number - 1) * page_size + 1;
            int end_offset = page_number * page_size;
            sql = " select * from ( SELECT A.*, ROWNUM RN FROM (" + sql + ") A ) WHERE RN BETWEEN " + offset + " AND " + end_offset;
        } else if ("mysql".equalsIgnoreCase(dbType)) {
            offset = (page_number - 1) * page_size;
            sql = sql + " limit " + offset + "," + page_size;
        }
        return sql;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
