package com.suitong.devplatform.common.dao;

import com.suitong.devplatform.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by ddgui on 2017/7/17.
 */
@Service
public class BaseDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T> List<T> findListBySql(String sqlStr, Class<T> modelClass){
       return findListBySql(sqlStr, "", modelClass);
    }

    public <T> List<T> findListBySql(String column, String cond, Class<T> modelClass){
        List<Map<String, Object>> listContent = jdbcTemplate.queryForList(column + cond );
        List<T>  list = MapUtil.convertMapList2DtoList(listContent, modelClass);
        return list;
    }
}
