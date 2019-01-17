package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppVideoType;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppVideoTypeRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.HppVideoService;
import com.chengma.devplatform.service.HppVideoTypeService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppVideoTypeDTO;
import com.chengma.devplatform.service.mapper.HppVideoTypeMapper;
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
public class HppVideoTypeServiceImpl implements HppVideoTypeService {

    private final HppVideoTypeRepository hppVideoTypeRepository;

    private final HppVideoTypeMapper hppVideoTypeMapper;

    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private HppVideoService hppVideoService;

    public HppVideoTypeServiceImpl(HppVideoTypeRepository hppVideoTypeRepository, HppVideoTypeMapper hppVideoTypeMapper){
        this.hppVideoTypeRepository=hppVideoTypeRepository;
        this.hppVideoTypeMapper=hppVideoTypeMapper;
    }

    @Override
    public Page<HppVideoTypeDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String videoTypeName = formParams.get("videoTypeName") == null ? "" : formParams.get("videoTypeName").toString();
        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_hpp_video_type where 1=1 ");

        User currentUser=userService.getUserWithAuthorities();
        //非超级管理员
        /*if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and c_user_id ='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(videoTypeName)){
            cond.append(" and c_video_type_name like '%"+videoTypeName+"%'");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= ReflectUtils.getColumnName(HppVideoType.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }

        Page<HppVideoTypeDTO> page = pageCommon.execPage(column.toString(), cond.toString(),orderBy,page_number, page_size, HppVideoTypeDTO.class);
        return page;
    }

    @Override
    public HppVideoTypeDTO save(HppVideoTypeDTO hppVideoTypeDTO) {
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

        if(!StringUtils.isEmpty(hppVideoTypeDTO.getImg()) && hppVideoTypeDTO.getImg().startsWith(prefix)) {
            fileStreamManageService.uploadImage(decoder, hppVideoTypeDTO.getImg(), imagePath, prefix, newFileName);
            hppVideoTypeDTO.setImg(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(StringUtils.isBlank(hppVideoTypeDTO.getId())){
            //添加
            Date now=new Date();
            hppVideoTypeDTO.setCreateAt(now);
            hppVideoTypeDTO.setUpdateAt(now);
            hppVideoTypeDTO.setDayPush(DevplatformConstants.VIDEO_TYPE_DAY_PUSH_NO);
            User currentUser=userService.getUserWithAuthorities();
            hppVideoTypeDTO.setUserId(currentUser.getId());
            if(hppVideoTypeDTO.getSortNum() == null){
                hppVideoTypeDTO.setSortNum(99);
            }
        }else{
            //修改
            HppVideoType currentHppVideoType=hppVideoTypeRepository.findOne(hppVideoTypeDTO.getId());
            hppVideoTypeDTO.setCreateAt(currentHppVideoType.getCreateAt());
            hppVideoTypeDTO.setUpdateAt(new Date());
            hppVideoTypeDTO.setUserId(hppVideoTypeDTO.getUserId());
            hppVideoTypeDTO.setDayPush(currentHppVideoType.getDayPush());
        }
        return hppVideoTypeMapper.toDto(hppVideoTypeRepository.save(hppVideoTypeMapper.toEntity(hppVideoTypeDTO)));
    }


    @Override
    public void delete(String id) {
        hppVideoTypeRepository.delete(id);
    }

    @Override
    public Long countByTypeId(String typeId){
        return hppVideoService.countByTypeId(typeId);
    }

    @Override
    public HppVideoTypeDTO findOne(String id) {
        return hppVideoTypeMapper.toDto(hppVideoTypeRepository.findOne(id));
    }

    @Override
    public HppVideoTypeDTO findByTypeNameAndUserId(String typeName, String userId) {
        return hppVideoTypeMapper.toDto(hppVideoTypeRepository.findByTypeNameAndUserId(typeName,userId));
    }

    @Override
    public HashMap<String, Object> checkHppVideoType(HppVideoTypeDTO hppVideoTypeDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppVideoTypeDTO.getVideoTypeName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入视频类别名称");
            return retMap;
        }
        if(StringUtils.isBlank(hppVideoTypeDTO.getImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传图片");
            return retMap;
        }
        HppVideoTypeDTO currentHppVideoType = this.findByTypeNameAndUserId(hppVideoTypeDTO.getVideoTypeName(),userService.getUserWithAuthorities().getId());
        if (currentHppVideoType != null) {
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "视频类别已存在");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public List<HppVideoTypeDTO> findAll() {
        StringBuffer sql=new StringBuffer("select vt.*,count(v.c_id) as phase from t_hpp_video_type vt join t_hpp_video v on vt.c_id=v.c_video_type_id GROUP BY vt.c_id order by vt.i_sort_num asc ");
        return baseDao.findListBySql(sql.toString(),HppVideoTypeDTO.class);
    }

    @Override
    public List<HppVideoTypeDTO> comboList() {
        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_hpp_video_type where 1=1 ");

        User currentUser=userService.getUserWithAuthorities();
        //非超级管理员
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and c_user_id ='"+currentUser.getId()+"'");
        }
       return baseDao.findListBySql(column.toString(),cond.toString(),HppVideoTypeDTO.class);
    }

    @Override
    public HppVideoTypeDTO editDayPush(String id) {
        HppVideoType hppVideoType= hppVideoTypeRepository.findOne(id);
        User user=userService.getUserWithAuthorities();
        hppVideoType.setApproveId(user.getId());
        hppVideoType.setUpdateAt(new Date());
        hppVideoType.setDayPush(DevplatformConstants.VIDEO_TYPE_DAY_PUSH_YES);
        return hppVideoTypeMapper.toDto(hppVideoTypeRepository.save(hppVideoType));
    }


    @Override
    public HashMap<String, Object> checkDayPush(String id) {
        HashMap<String, Object>  retMap = new HashMap<>();
        User user=userService.getUserWithAuthorities();
        if(user==null||!(user.getDepartment().equals(EnumRole.ADMIN.value())||user.getDepartment().equals(EnumRole.SERVICE.value()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录有效用户");
            return retMap;
        }
        if( hppVideoTypeRepository.findOne(id)==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择有效视频类别");
            return retMap;
        }
        Long count=hppVideoTypeRepository.countDayPush();
        if(count>=6){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "每日推荐达到上限...");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HashMap<String, Object> cancelDayPush(String id) {
        HashMap<String, Object>  retMap = new HashMap<>();

        User user=userService.getUserWithAuthorities();
        if(!(user.getDepartment().equals(EnumRole.ADMIN.value())||user.getDepartment().equals(EnumRole.SERVICE.value()))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录有效用户");
            return retMap;
        }
        HppVideoType hppVideoType=hppVideoTypeRepository.findOne(id);
        if(hppVideoType==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择有效视频类别");
            return retMap;
        }
        hppVideoType.setDayPush(DevplatformConstants.VIDEO_TYPE_DAY_PUSH_NO);
        hppVideoType.setApproveId(user.getId());
        hppVideoType.setUpdateAt(new Date());
        hppVideoTypeRepository.save(hppVideoType);
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public List<HppVideoTypeDTO> findDayPush() {
        StringBuilder column = new StringBuilder(" select vt.*,count(v.c_id) as phase from t_hpp_video_type vt join t_hpp_video v on vt.c_id=v.c_video_type_id and  vt.c_day_push = '"+ DevplatformConstants.VIDEO_TYPE_DAY_PUSH_YES+"' GROUP BY vt.c_id order by vt.i_sort_num desc ");
        return baseDao.findListBySql(column.toString(),HppVideoTypeDTO.class);
    }
}
