package com.suitong.devplatform.common.page;

import com.suitong.devplatform.common.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
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

    public <T> Page<T> execPage(String column, String cond, int page_number, int page_size, Class<T> modelClass){
        Page<T> result ;
        String dbType = environment.getProperty("spring.jpa.database");
        String sqlStr ="";
        if("ORACLE".equalsIgnoreCase(dbType)){
            int start_offset = (page_number - 1) * page_size + 1;
            int end_offset =    page_number * page_size ;
            sqlStr = " select * from ( SELECT A.*, ROWNUM RN FROM (" + column + cond + ") A ) WHERE RN BETWEEN " +  start_offset + " AND " + end_offset;
        }else if("mysql".equalsIgnoreCase(dbType)){
            int offset = (page_number - 1) * page_size;
            sqlStr = column + cond + " limit " + offset + "," + page_size;
        }

        List<Map<String, Object>> listContent = jdbcTemplate.queryForList(sqlStr);

        int total = Integer.valueOf(jdbcTemplate.queryForList(" select count(1) count " + cond ).get(0).get("count").toString());

        List<T>  list = MapUtil.convertMapList2DtoList(listContent, modelClass);

        Pageable pageable = new PageRequest(page_number-1, page_size);

        result = new PageImpl(list,pageable, total);

        return result;
    }

}
