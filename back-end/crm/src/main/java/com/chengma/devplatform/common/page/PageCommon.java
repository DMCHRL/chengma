package com.chengma.devplatform.common.page;

import com.chengma.devplatform.common.util.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/10/0010.
 */
@Service
public class PageCommon {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment environment;

    /**
     * 带分页功能的列表查询
     *
     * @param column      sql中查询的字段
     * @param cond        sql中from后面的语句
     * @param page_number 页码
     * @param page_size   每页数据量
     * @param modelClass  数据库对应的实体类
     * @param <T>         泛型
     * @return Page
     */
    public <T> Page<T> execPage(String column, String cond, int page_number, int page_size, Class<T> modelClass) {
        return execPage(column, cond, null, page_number, page_size, modelClass);
    }

    /**
     * 带分页功能的列表查询
     *
     * @param column      sql中查询的字段
     * @param cond        sql中from后面的语句
     * @param orderBy     sql中最后排序的语句，如果要排序的字段在表里面有，可以直接拼到cond里面
     * @param page_number 页码
     * @param page_size   每页数据量
     * @param modelClass  数据库对应的实体类
     * @param <T>         泛型
     * @return Page
     */
    public <T> Page<T> execPage(String column, String cond, String orderBy, int page_number, int page_size, Class<T> modelClass) {
        Page<T> result;
        String dbType = environment.getProperty("spring.jpa.database");
        String sqlStr = "";
        if (StringUtils.isEmpty(orderBy)) {
            orderBy = "";
        }
        if ("ORACLE".equalsIgnoreCase(dbType)) {
            int start_offset = (page_number - 1) * page_size + 1;
            int end_offset = page_number * page_size;
            sqlStr = " select * from ( SELECT A.*, ROWNUM RN FROM (" + column + cond + orderBy + ") A ) WHERE RN BETWEEN " + start_offset + " AND " + end_offset;
        } else if ("mysql".equalsIgnoreCase(dbType)) {
            int offset = (page_number - 1) * page_size;
            sqlStr = column + cond + orderBy + " limit " + offset + "," + page_size;
        }

        List<Map<String, Object>> listContent = jdbcTemplate.queryForList(sqlStr);

        List<Map<String, Object>> listCount = jdbcTemplate.queryForList(" select count(1) count from (" + column + cond + ")t ");
        int total = 0;
        if (!listCount.isEmpty()) {
            total = Integer.valueOf(listCount.get(0).get("count").toString());
        }

        List<T> list = MapUtil.convertMapList2DtoList(listContent, modelClass);

        Pageable pageable = new PageRequest(page_number - 1, page_size);

        result = new PageImpl(list, pageable, total);

        return result;
    }

}
