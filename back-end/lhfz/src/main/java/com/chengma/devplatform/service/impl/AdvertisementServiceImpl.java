package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.Advertisement;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.AdvertisementRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.AdvertisementService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.AdvertisementDTO;
import com.chengma.devplatform.service.mapper.AdvertisementMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {


    private final AdvertisementMapper advertisementMapper;

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private Environment environment;

    @Autowired
    private FileStreamManageService fileStreamManageService;


    public AdvertisementServiceImpl(AdvertisementMapper advertisementMapper, AdvertisementRepository advertisementRepository){
        this.advertisementMapper  = advertisementMapper;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public AdvertisementDTO save(AdvertisementDTO advertisementDTO) {

        if(StringUtils.isBlank(advertisementDTO.getId())){
            //添加
            Date now = new Date();
            advertisementDTO.setCreateAt(now);
            advertisementDTO.setUpdateAt(now);
            User currentUser =userService.getUserWithAuthorities();
            advertisementDTO.setApproveId(currentUser.getId());
            if(advertisementDTO.getSortNum() == null){
                advertisementDTO.setSortNum(99);
            }
        }else{
           Advertisement currentAdvertisement = advertisementRepository.findOne(advertisementDTO.getId());
            advertisementDTO.setCreateAt(currentAdvertisement.getCreateAt());
            advertisementDTO.setUpdateAt(new Date());
            advertisementDTO.setApproveId(currentAdvertisement.getApproveId());
        }
        Advertisement advertisement = advertisementRepository.save(advertisementMapper.toEntity(advertisementDTO));
        return advertisementMapper.toDto(advertisement);

    }

    @Override
    public void delete(String id) {
        advertisementRepository.delete(id);
    }


    @Override
    public Page<AdvertisementDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        //非超级管理员
        /*User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
           return null;
        }*/
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_advertisement ");

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(Advertisement.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<AdvertisementDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, AdvertisementDTO.class);
        return page;
    }

    @Override
    public HashMap<String, Object> checkAdvertisementDTO(AdvertisementDTO advertisementDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(advertisementDTO.getImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传图片");
            return retMap;
        }
        String type=advertisementDTO.getType();
        /*if(StringUtils.isBlank(type)||!(type.equals(DevplatformConstants.ADVERTISEMENT_TYPE_HOME)||type.equals(DevplatformConstants.ADVERTISEMENT_TYPE_VIDEO)||type.equals(DevplatformConstants.ADVERTISEMENT_TYPE_SINKS))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择类型");
            return retMap;
        }*/
        if(StringUtils.isBlank(type)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择播放位置");
            return retMap;
        }
       /* User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null||!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录有效账号");
            return retMap;
        }*/
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public AdvertisementDTO findOne(String id) {
        return advertisementMapper.toDto( advertisementRepository.findOne(id));
    }

    @Override
    public List<AdvertisementDTO> loadByType(String type) {
        String sql = "select * from t_advertisement where c_type ='"+type+"' order by i_sort_num asc";
        return baseDao.findListBySql(sql,AdvertisementDTO.class);
    }
}
