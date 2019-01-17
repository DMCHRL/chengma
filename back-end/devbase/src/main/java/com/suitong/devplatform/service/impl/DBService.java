package com.suitong.devplatform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

    private  String dbType = null;

    public DBService(Environment environment) {
        this.environment = environment;
        dbType = environment.getProperty("spring.jpa.database");
    }

    public String query(){
        String sql = "";
        if("oracle".equalsIgnoreCase(dbType)){
            sql = "group by srm.sys_menu_id";
        }else if("mysql".equalsIgnoreCase(dbType)){
            sql = "group by srm.id";
        }
        return sql;
    }

    public String lessTime(){
        String sql = "";
        if("oracle".equalsIgnoreCase(dbType)){
            sql = " < sysdate ";
        }else if("mysql".equalsIgnoreCase(dbType)){
            sql = " < now() ";
        }
        return sql;
    }

    public String greaterTime(){
        String sql = "";
        if("oracle".equalsIgnoreCase(dbType)){
            sql = " > sysdate ";
        }else if("mysql".equalsIgnoreCase(dbType)){
            sql = " > now() ";
        }
        return sql;
    }

    public String date2char(String column, String format){
        String sql = "";
        if("oracle".equalsIgnoreCase(dbType)){
            sql = " to_char("+column + ",'" + format+ "') ";
        }else if("mysql".equalsIgnoreCase(dbType)){
            sql = " Date("+column+") ";
        }
        return sql;
    }
    public String date2char(String column){
        String format = "yyyy-MM-dd";
        String sql = date2char(column, format);
        return sql;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
