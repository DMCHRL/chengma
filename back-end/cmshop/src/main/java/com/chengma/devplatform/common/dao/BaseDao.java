package com.chengma.devplatform.common.dao;

import com.chengma.devplatform.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ddgui on 2017/7/17.
 */
@Service
public class BaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 列表查询
     *
     * @param sqlStr     sql语句
     * @param modelClass 数据库对应的实体类
     * @param <T>        泛型
     * @return List
     */
    public <T> List<T> findListBySql(String sqlStr, Class<T> modelClass) {
        return findListBySql(sqlStr, "", modelClass);
    }

    /**
     * 列表查询
     *
     * @param column     sql中查询的字段
     * @param cond       sql中from后面的语句
     * @param modelClass 数据库对应的实体类
     * @param <T>        泛型
     * @return List
     */
    public <T> List<T> findListBySql(String column, String cond, Class<T> modelClass) {
        List<Map<String, Object>> listContent = jdbcTemplate.queryForList(column + cond);
        List<T> list = MapUtil.convertMapList2DtoList(listContent, modelClass);
        return list;
    }
}
