package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppAdvertisement;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppAdvertisementRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.HppAdvertisementService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppAdvertisementDTO;
import com.chengma.devplatform.service.mapper.HppAdvertisementMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class HppAdvertisementServiceImpl implements HppAdvertisementService {


    private final HppAdvertisementMapper hppAdvertisementMapper;

    private final HppAdvertisementRepository hppAdvertisementRepository;

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


    public HppAdvertisementServiceImpl(HppAdvertisementMapper hppAdvertisementMapper, HppAdvertisementRepository hppAdvertisementRepository){
        this.hppAdvertisementMapper  = hppAdvertisementMapper;
        this.hppAdvertisementRepository = hppAdvertisementRepository;
    }

    @Override
    public HppAdvertisementDTO save(HppAdvertisementDTO hppAdvertisementDTO) {

        BASE64Decoder decoder = new BASE64Decoder();
        String videoImageWebPath = environment.getProperty("fileManage.videoImageWebPath");
        String imagePath = environment.getProperty("fileManage.imagePath");
        String prefix = environment.getProperty("fileManage.base64Prefix");
        String newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg" ;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        imagePath = imagePath + sdf.format(new Date()) + "/";

        File dest =new File(imagePath);
        //如果文件夹不存在则创建
        if  (!dest .exists()  && !dest .isDirectory()) {
            dest .mkdir();
        }

        if(!StringUtils.isEmpty(hppAdvertisementDTO.getImg()) && hppAdvertisementDTO.getImg().startsWith(prefix)) {
            fileStreamManageService.uploadImage(decoder, hppAdvertisementDTO.getImg(), imagePath, prefix, newFileName);
            hppAdvertisementDTO.setImg(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(StringUtils.isBlank(hppAdvertisementDTO.getId())){
            //添加
            Date now = new Date();
            hppAdvertisementDTO.setCreateAt(now);
            hppAdvertisementDTO.setUpdateAt(now);
            User currentUser =userService.getUserWithAuthorities();
            hppAdvertisementDTO.setApproveId(currentUser.getId());
            if(hppAdvertisementDTO.getSortNum() == null){
                hppAdvertisementDTO.setSortNum(99);
            }
        }else{
           HppAdvertisement currentHppAdvertisement = hppAdvertisementRepository.findOne(hppAdvertisementDTO.getId());
            hppAdvertisementDTO.setCreateAt(currentHppAdvertisement.getCreateAt());
            hppAdvertisementDTO.setUpdateAt(new Date());
            hppAdvertisementDTO.setApproveId(currentHppAdvertisement.getApproveId());
        }
        HppAdvertisement hppAdvertisement = hppAdvertisementRepository.save(hppAdvertisementMapper.toEntity(hppAdvertisementDTO));
        //hppAdvertisementServiceRedis.put("AD_KEY",hppAdvertisement,-1);
        return hppAdvertisementMapper.toDto(hppAdvertisement);

    }

    @Override
    public void delete(String id) {
        hppAdvertisementRepository.delete(id);
    }


    @Override
    public Page<HppAdvertisementDTO> pageList(HashMap<String, Object> params) {
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

        StringBuilder cond = new StringBuilder(" from t_hpp_advertisement ");

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppAdvertisement.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppAdvertisementDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppAdvertisementDTO.class);
        return page;
    }

    @Override
    public HashMap<String, Object> checkHppAdvertisementDTO(HppAdvertisementDTO hppAdvertisementDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppAdvertisementDTO.getImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传图片");
            return retMap;
        }
        String type=hppAdvertisementDTO.getType();
        /*if(StringUtils.isBlank(type)||!(type.equals(DevplatformConstants.ADVERTISEMENT_TYPE_HOME)||type.equals(DevplatformConstants.ADVERTISEMENT_TYPE_VIDEO)||type.equals(DevplatformConstants.ADVERTISEMENT_TYPE_SINKS))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择类型");
            return retMap;
        }*/
        User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null||!(currentUser.getDepartment().equals(EnumRole.ADMIN.value())||currentUser.getDepartment().equals(EnumRole.SERVICE.value()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录有效账号");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppAdvertisementDTO findOne(String id) {
        return hppAdvertisementMapper.toDto( hppAdvertisementRepository.findOne(id));
    }

    @Override
    public List<HppAdvertisementDTO> loadByHome() {
        String sql="select * from t_hpp_advertisement where c_type = '"+ DevplatformConstants.ADVERTISEMENT_TYPE_HOME+"'order by i_sort_num";
        return baseDao.findListBySql(sql,HppAdvertisementDTO.class);
    }

    @Override
    public List<HppAdvertisementDTO> loadByVideo() {
        String sql="select * from t_hpp_advertisement where c_type = '"+ DevplatformConstants.ADVERTISEMENT_TYPE_VIDEO+"' order by i_sort_num";
        return baseDao.findListBySql(sql,HppAdvertisementDTO.class);
    }

    @Override
    public List<HppAdvertisementDTO> loadBySinks() {
        String sql="select * from t_hpp_advertisement where c_type = '"+ DevplatformConstants.ADVERTISEMENT_TYPE_SINKS+"' order by i_sort_num";
        return baseDao.findListBySql(sql,HppAdvertisementDTO.class);
    }

    @Override
    public List<HppAdvertisementDTO> loadByStrategy() {
        String sql="select * from t_hpp_advertisement where c_type = '"+ DevplatformConstants.ADVERTISEMENT_TYPE_STRATEGY+"' order by i_sort_num";
        return baseDao.findListBySql(sql,HppAdvertisementDTO.class);
    }

    @Override
    public List<HppAdvertisementDTO> loadByLive() {
        String sql="select * from t_hpp_advertisement where c_type = '"+ DevplatformConstants.ADVERTISEMENT_TYPE_LIVE+"' order by i_sort_num";
        return baseDao.findListBySql(sql,HppAdvertisementDTO.class);
    }
}
