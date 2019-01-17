package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.Html2Util;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppNews;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppNewsRepository;
import com.chengma.devplatform.service.AppPushService;
import com.chengma.devplatform.service.HppNewsService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppNewsDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.service.mapper.HppNewsMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class HppNewsServiceImpl implements HppNewsService {

    private final Logger log = LoggerFactory.getLogger(HppNewsServiceImpl.class);

    private final HppNewsRepository hppNewsRepository;

    private final HppNewsMapper hppNewsMapper;

    @Autowired
    private AppPushService appPushService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private DBService dbService;

    public HppNewsServiceImpl(HppNewsRepository hppNewsRepository, HppNewsMapper hppNewsMapper) {
        this.hppNewsRepository = hppNewsRepository;
        this.hppNewsMapper = hppNewsMapper;
    }

    @Override
    public HppNewsDTO save(HppNewsDTO hppNewsDTO) {
        if(StringUtils.isBlank(hppNewsDTO.getId())){
            Date now = new Date();
            hppNewsDTO.setCreateTime(now);
            hppNewsDTO.setUpdateTime(now);
            hppNewsDTO.setRead(0);
            User user = userService.getUserWithAuthorities();
            hppNewsDTO.setUserId(user.getId());
            //hppNewsDTO.setStatus(DevplatformConstants.HPP_NEWS_STATUS_N);
        }else{
            HppNews hppNews = hppNewsRepository.findOne(hppNewsDTO.getId());
            hppNewsDTO.setUserId(hppNews.getUserId());
            hppNewsDTO.setCreateTime(hppNews.getCreateTime());
            hppNewsDTO.setUpdateTime(new Date());
            hppNewsDTO.setRead(hppNews.getRead());
            hppNewsDTO.setStatus(hppNews.getStatus());
        }
        HppNews hppNews =hppNewsMapper.toEntity(hppNewsDTO);
        HppNews hppNews1 =hppNewsRepository.save(hppNews);
        return hppNewsMapper.toDto(hppNews1);
    }

    @Override
    public HashMap<String, Object> checkSave(HppNewsDTO hppNewsDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppNewsDTO.getTitle())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入新闻标题");
            return retMap;
        }

        if(StringUtils.isBlank(hppNewsDTO.getType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入新闻类别");
            return retMap;
        }

        if(StringUtils.isBlank(hppNewsDTO.getListImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传新闻列表显示图");
            return retMap;
        }

        if(StringUtils.isBlank(hppNewsDTO.getContext())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入新闻内容");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Page<HppNewsDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String title = formParams.get("title") == null ? "" : formParams.get("title").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_hpp_news ");

        //非超级管理员
        User currentUser=userService.getUserWithAuthorities();
        /*if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and c_user_id='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(title)){
            cond.append(" and c_title like '%"+title+"%' ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppNews.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppNewsDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppNewsDTO.class);
        return page;
    }

    @Override
    public HppNewsDTO findOne(String id) {
        return hppNewsMapper.toDto(hppNewsRepository.findOne(id));
    }

    /*@Cacheable(value="New",key = "#id")*/
    @Override
    public HppNewsDTO readOne(String id) {
        HppNews hppNews = hppNewsRepository.findOne(id);
        //System.out.println("没有用缓存========================================================");
        hppNews.setRead(hppNews.getRead()+1);
        hppNews = hppNewsRepository.save(hppNews);
        return hppNewsMapper.toDto(hppNews);
    }

    @Override
    public void delete(String id) {
        hppNewsRepository.delete(id);
    }

    /*@Cacheable(value="News",key = "'news'")*/
    @Override
    public List<HppNewsDTO> findList() {
        String sql = "select * from t_hpp_news where c_status = '"+DevplatformConstants.HPP_NEWS_STATUS_Y+"' order by d_create_time desc";
        return baseDao.findListBySql(sql,HppNewsDTO.class);
    }

   /* @Cacheable(value="New",key = "#id")*/
    @Override
    public HppNewsDTO showOne(String id) {
        HppNews hppNews = hppNewsRepository.findOne(id);
        System.out.println("没有用缓存========================================================");
        String status = hppNews.getStatus();
        if(StringUtils.isBlank(status) || status.equals(DevplatformConstants.HPP_NEWS_STATUS_N)){
            status = DevplatformConstants.HPP_NEWS_STATUS_Y;
        }else{
            status = DevplatformConstants.HPP_NEWS_STATUS_N;
        }
        hppNews.setStatus(status);
        hppNews = hppNewsRepository.save(hppNews);
        return hppNewsMapper.toDto(hppNews);
    }

    @Override
    public void sendNews(String id) {
       HppNews hppNews = hppNewsRepository.findOne(id);
       appPushService.sendNews(hppNews.getTitle(), Html2Util.getContent(hppNews.getContext()).substring(0,15)+"...",hppNews.getId());
    }
}
