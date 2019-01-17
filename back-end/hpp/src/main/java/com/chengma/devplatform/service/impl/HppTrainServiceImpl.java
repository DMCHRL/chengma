package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.HppTrain;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppTrainRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.HppTrainApplyService;
import com.chengma.devplatform.service.HppTrainService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppTrainDTO;
import com.chengma.devplatform.service.mapper.HppTrainMapper;
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
public class HppTrainServiceImpl implements HppTrainService {

    private final HppTrainRepository hppTrainRepository;

    private final HppTrainMapper hppTrainMapper;

    @Autowired
    private HppTrainApplyService hppTrainApplyService;

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

    public HppTrainServiceImpl(HppTrainRepository hppTrainRepository, HppTrainMapper hppTrainMapper){
        this.hppTrainRepository=hppTrainRepository;
        this.hppTrainMapper=hppTrainMapper;
    }

    @Override
    public Page<HppTrainDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String trainName = formParams.get("trainName") == null ? "" : formParams.get("trainName").toString();
        /*String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();*/

        StringBuilder column = new StringBuilder(" select *  ");

        StringBuilder cond = new StringBuilder(" from t_hpp_train where 1=1");

        //非超级管理员
      /*  User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and c_user_id ='"+currentUser.getId()+"'");
        }*/
        if(StringUtils.isNotBlank(trainName)){
            cond.append(" and c_train_name like '%"+trainName+"%'");
        }

        Page<HppTrainDTO> page = pageCommon.execPage(column.toString(), cond.toString(),  page_number, page_size, HppTrainDTO.class);
        return page;
    }

    @Override
    public HppTrainDTO save(HppTrainDTO hppTrainDTO) {

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

        if(!StringUtils.isEmpty(hppTrainDTO.getImg1()) && hppTrainDTO.getImg1().startsWith(prefix)) {
            fileStreamManageService.uploadImage(decoder, hppTrainDTO.getImg1(), imagePath, prefix, newFileName);
            hppTrainDTO.setImg1(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(!StringUtils.isEmpty(hppTrainDTO.getImg2()) && hppTrainDTO.getImg2().startsWith(prefix)) {
            newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            fileStreamManageService.uploadImage(decoder, hppTrainDTO.getImg2(), imagePath, prefix, newFileName);
            hppTrainDTO.setImg2(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(!StringUtils.isEmpty(hppTrainDTO.getListImg()) && hppTrainDTO.getListImg().startsWith(prefix)) {
            newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            fileStreamManageService.uploadImage(decoder, hppTrainDTO.getListImg(), imagePath, prefix, newFileName);
            hppTrainDTO.setListImg(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }

        //添加
        if(StringUtils.isBlank(hppTrainDTO.getId())){
            Date now=new Date();
            hppTrainDTO.setCreateAt(now);
            hppTrainDTO.setUpdateAt(now);
            User currentUser=userService.getUserWithAuthorities();
            hppTrainDTO.setUserId(currentUser.getId());
        }else{
            //修改
            HppTrain currentTrain =  hppTrainRepository.findOne(hppTrainDTO.getId());
            hppTrainDTO.setCreateAt(currentTrain.getCreateAt());
            hppTrainDTO.setUserId(currentTrain.getUserId());
            hppTrainDTO.setUpdateAt(new Date());
        }

        HppTrain hppTrain = hppTrainMapper.toEntity(hppTrainDTO);
        hppTrain=hppTrainRepository.save(hppTrain);

        return hppTrainMapper.toDto(hppTrain);
    }


    @Override
    public HppTrainDTO findOne(String id) {
        if(StringUtils.isBlank(id))return null;
        return hppTrainMapper.toDto(hppTrainRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppTrainRepository.delete(id);
    }

    @Override
    public HppTrainDTO findByName(String name, String userId) {
        return hppTrainMapper.toDto(hppTrainRepository.findByName(name,userId));
    }

    @Override
    public HashMap<String, Object> checkHppTrain(HppTrainDTO hppTrainDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppTrainDTO.getTrainName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入培训名称");
            return retMap;
        }
        if(StringUtils.isBlank(hppTrainDTO.getTrainPlace())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入培训地点");
            return retMap;
        }
        HppTrainDTO currentHppTrain = this.findByName(hppTrainDTO.getTrainName(),userService.getUserWithAuthorities().getId());
        if (currentHppTrain != null) {
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "培训已存在");
            return retMap;
        }
        if(hppTrainDTO.getTrainTime()==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入培训时间");
            return retMap;
        }
        if(hppTrainDTO.getEndTime()==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入培训结束时间");
            return retMap;
        }
        User currentUser=userService.getUserWithAuthorities();
        if(currentUser==null){
            //未对权限处理
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录有效账号");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Long applyCount(String trainId) {
        return hppTrainApplyService.countByCourseMealId(trainId);
    }

    @Override
    public List<HppTrainDTO> findAll() {
        StringBuffer sql= new StringBuffer("select * from t_hpp_train ");
        String orderBy=" ORDER BY d_train_time desc";
        return baseDao.findListBySql(sql.toString(),orderBy,HppTrainDTO.class);
    }
}
