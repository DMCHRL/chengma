package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppComment;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppCommentRepository;
import com.chengma.devplatform.service.HppCommentService;
import com.chengma.devplatform.service.dto.HppCommentDTO;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.service.mapper.HppCommentMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HppCommentServiceImpl implements HppCommentService {

    private final Logger log = LoggerFactory.getLogger(HppCommentServiceImpl.class);

    private final HppCommentRepository hppCommentRepository;

    private final HppCommentMapper hppCommentMapper;



    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private DBService dbService;

    public HppCommentServiceImpl(HppCommentRepository hppCommentRepository, HppCommentMapper hppCommentMapper) {
        this.hppCommentRepository = hppCommentRepository;
        this.hppCommentMapper = hppCommentMapper;
    }

    @Override
    public HppCommentDTO save(HppCommentDTO hppCommentDTO) {
        hppCommentDTO.setCreateAt(new Date());
        HppComment hppComment =hppCommentMapper.toEntity(hppCommentDTO);
        HppComment hppComment1 =hppCommentRepository.save(hppComment);
        return hppCommentMapper.toDto(hppComment1);
    }

    @Override
    public HashMap<String, Object> checkSave(HppCommentDTO hppCommentDTO) {
        HashMap<String,Object> checkMap=new HashMap<String,Object>();
        if(StringUtils.isBlank(hppCommentDTO.getContext())){
            checkMap.put("statusCode", ResponseResult.FAIL_CODE);
            checkMap.put("msg","请输入评论内容");
            return checkMap;
        }

        if(StringUtils.isBlank(hppCommentDTO.getMobile())){
            checkMap.put("statusCode", ResponseResult.FAIL_CODE);
            checkMap.put("msg","请登录");
            return checkMap;
        }

        if(StringUtils.isBlank(hppCommentDTO.getVideoId())){
            checkMap.put("statusCode", ResponseResult.FAIL_CODE);
            checkMap.put("msg","请选择视频");
            return checkMap;
        }
        checkMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return checkMap;
    }

    @Override
    public HashMap<String, Object> findForApp(String videoId) {
        String listSql="SELECT\n" +
                "\tc.*, m.c_head_img AS head_img,\n" +
                "\tm.c_user_name AS username\n" +
                "FROM\n" +
                "\tt_hpp_comment c\n" +
                "JOIN t_hpp_mobile_user m ON c.c_mobile = m.c_phone\n" +
                "AND c.c_video_id ='"+videoId+"'order by d_create_at desc";
        List<HppCommentDTO> list= baseDao.findListBySql(listSql,HppCommentDTO.class);
        HashMap<String,Object> response = new HashMap<>();
        response.put("list",list);
        response.put("total",list.size());
        return response;
    }

    @Override
    public Page<HppCommentDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String videoName = formParams.get("videoName") == null ? "" : formParams.get("videoName").toString();
        String videoTypeId = formParams.get("videoTypeId") == null ? "" : formParams.get("videoTypeId").toString();
       /* String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String typeId = formParams.get("typeId") == null ? "" : formParams.get("typeId").toString();*/

        StringBuilder column = new StringBuilder(" SELECT\n" +
                "\tc.*,v.c_video_name ");

        StringBuilder cond = new StringBuilder("FROM\n" +
                "\tt_hpp_comment c\n" +
                "JOIN t_hpp_video v ON c.c_video_id = v.c_id\n" +
                "AND exists (select null from t_hpp_mobile_user m where m.c_phone=c.c_mobile)\n" +
                "AND v.c_video_type_id = '"+videoTypeId+"'\n");


        if(StringUtils.isNotBlank(videoName)){
            cond.append(" and v.c_video_name like '%"+videoName+"%' ");
        }

       /* String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "h."+ ReflectUtils.getColumnName(HppVideo.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }*/

        Page<HppCommentDTO> page = pageCommon.execPage(column.toString(), cond.toString(),page_number, page_size, HppCommentDTO.class);
        return page;
    }

    @Override
    public HppCommentDTO findOne(String id) {
        return hppCommentMapper.toDto(hppCommentRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppCommentRepository.delete(id);
    }

}
