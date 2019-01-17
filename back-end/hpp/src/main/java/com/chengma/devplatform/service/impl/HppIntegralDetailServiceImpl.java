package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppNotice;
import com.chengma.devplatform.repository.HppIntegralDetailRepository;
import com.chengma.devplatform.service.HppIntegralDetailService;
import com.chengma.devplatform.service.dto.HppIntegralDetailDTO;
import com.chengma.devplatform.service.dto.HppNoticeDTO;
import com.chengma.devplatform.service.mapper.HppIntegralDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/10/12.
 */
@Service
@Transactional
public class HppIntegralDetailServiceImpl implements HppIntegralDetailService {

    private final HppIntegralDetailRepository hppIntegralDetailRepository;

    private final HppIntegralDetailMapper hppIntegralDetailMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    public HppIntegralDetailServiceImpl(HppIntegralDetailRepository hppIntegralDetailRepository, HppIntegralDetailMapper hppIntegralDetailMapper){
        this.hppIntegralDetailRepository=hppIntegralDetailRepository;
        this.hppIntegralDetailMapper=hppIntegralDetailMapper;
    }

    @Override
    public Page<HppIntegralDetailDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String type = formParams.get("type") == null ? "" : formParams.get("type").toString();
        String mobile = formParams.get("mobile") == null ? "" : formParams.get("mobile").toString();
      /*  String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();
*/
        StringBuilder column = new StringBuilder("SELECT\n" +
                "\t*");

        StringBuilder cond = new StringBuilder(" FROM\n" +
                "\tt_hpp_integral_detail\n" +
                "WHERE\n" +
                "\t1 = 1");

        //非超级管理员
        /*User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and c_create_by='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(type)){
            cond.append(" and c_type like '%"+type+"%' ");
        }

        if(StringUtils.isNotBlank(mobile)){
            cond.append(" and c_mobile like '%"+mobile+"%' ");
        }

        cond.append(" ORDER BY d_create_time desc");

       /* String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppNotice.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }*/

        Page<HppIntegralDetailDTO> page = pageCommon.execPage(column.toString(), cond.toString(),page_number, page_size, HppIntegralDetailDTO.class);
        return page;
    }

    @Override
    public HppIntegralDetailDTO save(HppIntegralDetailDTO hppIntegralDetailDTO) {
        return hppIntegralDetailMapper.toDto(hppIntegralDetailRepository.save(hppIntegralDetailMapper.toEntity(hppIntegralDetailDTO)));
    }

    @Override
    public HppIntegralDetailDTO createHppIntegralDetailDTO(HppIntegralDetailDTO hppIntegralDetailDTO) {
        //添加
        if(StringUtils.isBlank(hppIntegralDetailDTO.getId())){

        }else{
            //修改
        }

        return hppIntegralDetailMapper.toDto(hppIntegralDetailRepository.save(hppIntegralDetailMapper.toEntity(hppIntegralDetailDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateHppIntegralDetailDTO(HppIntegralDetailDTO hppIntegralDetailDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppIntegralDetailDTO findOne(String id) {
        return hppIntegralDetailMapper.toDto(hppIntegralDetailRepository.findOne(id));
    }

    @Override
    public List<HppIntegralDetailDTO> findAll() {
        return hppIntegralDetailMapper.toDto(hppIntegralDetailRepository.findAll());
    }

    @Override
    public void delete(String id) {
        hppIntegralDetailRepository.delete(id);
    }

    @Override
    public List<HppIntegralDetailDTO> findByCommunityOrFriend(String type,String mobile) {

        String sql = "select * from t_hpp_integral_detail where c_type = '"+type+"' and c_mobile='"+mobile+"' and TO_DAYS(d_create_time)=TO_DAYS(NOW())";
        return baseDao.findListBySql(sql,HppIntegralDetailDTO.class);
    }

    @Override
    public HppIntegralDetailDTO findByFriend(String uuid) {
        return hppIntegralDetailMapper.toDto(hppIntegralDetailRepository.findByTypeEqualsAndUuidEquals(DevplatformConstants.INTEGRAL_DETAIL_TYPE_FRIEND,uuid));
    }

    @Override
    public List<HppIntegralDetailDTO> findByMobile(String mobile) {
        return hppIntegralDetailMapper.toDto(hppIntegralDetailRepository.findByMobileEqualsOrderByCreateTimeDesc(mobile));
    }
}
