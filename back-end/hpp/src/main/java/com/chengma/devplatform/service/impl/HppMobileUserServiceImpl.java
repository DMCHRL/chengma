package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppMobileUser;
import com.chengma.devplatform.repository.HppMobileUserRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.HppMobileUserService;
import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.chengma.devplatform.service.mapper.HppMobileUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


/**
 * Service Implementation for managing HppMobileValidate.
 *
 * @author administrator
 */
@Service
@Transactional
public class HppMobileUserServiceImpl implements HppMobileUserService {

    private final Logger log = LoggerFactory.getLogger(HppMobileUserServiceImpl.class);

    private final HppMobileUserRepository hppMobileUserRepository;

    private final HppMobileUserMapper hppMobileUserMapper;


    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private Environment environment;

    public HppMobileUserServiceImpl(HppMobileUserRepository hppMobileUserRepository, HppMobileUserMapper hppMobileUserMapper) {
        this.hppMobileUserRepository = hppMobileUserRepository;
        this.hppMobileUserMapper = hppMobileUserMapper;
    }

    /**
     * 保存
     *
     * @param hppMobileUserDTO 短信验证码信息
     * @return HppMobileUserDTO
     */
    @Override
    public HppMobileUserDTO save(HppMobileUserDTO hppMobileUserDTO) {
        log.debug("Request to save Mobile : {}", hppMobileUserDTO);
        HppMobileUser mobile = hppMobileUserMapper.toEntity(hppMobileUserDTO);
        mobile = hppMobileUserRepository.save(mobile);
        return hppMobileUserMapper.toDto(mobile);
    }

    @Override
    public HppMobileUserDTO edit(HppMobileUserDTO hppMobileUserDTO) {

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

        if(!StringUtils.isEmpty(hppMobileUserDTO.getHeadImg()) && hppMobileUserDTO.getHeadImg().startsWith(prefix)) {
            fileStreamManageService.uploadImage(decoder, hppMobileUserDTO.getHeadImg(), imagePath, prefix, newFileName);
            hppMobileUserDTO.setHeadImg(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        HppMobileUser currentMobileUser=hppMobileUserRepository.findOne(hppMobileUserDTO.getId());
        currentMobileUser.setUpdateAt(new Date());
        currentMobileUser.setHeadImg(hppMobileUserDTO.getHeadImg());
        currentMobileUser.setUserName(hppMobileUserDTO.getUserName());
        return hppMobileUserMapper.toDto(hppMobileUserRepository.save(currentMobileUser));
    }

    @Override
    public HashMap<String, Object> checkHppMobileUserDTO(HppMobileUserDTO hppMobileUserDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppMobileUserDTO.getUserName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入昵称");
            return retMap;
        }
        List<HppMobileUser> list = hppMobileUserRepository.findByUserNameEqualsAndPhoneEquals(hppMobileUserDTO.getUserName(), hppMobileUserDTO.getPhone());
        if (list != null&&list.size()>0) {
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "昵称已存在");
            return retMap;
        }
        if(StringUtils.isBlank(hppMobileUserDTO.getHeadImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传头像");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppMobileUserDTO findOne(String id) {
        return hppMobileUserMapper.toDto(hppMobileUserRepository.findOne(id));
    }

    @Override
    public Page<HppMobileUserDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String mobile = formParams.get("mobile") == null ? "" : formParams.get("mobile").toString();

        String type = formParams.get("type") == null ? "" : formParams.get("type").toString();
        String startTime = formParams.get("startTime") == null ? "" : formParams.get("startTime").toString();
        String endTime = formParams.get("endTime") == null ? "" : formParams.get("endTime").toString();

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();


        StringBuilder column = new StringBuilder(" select u.*,i.i_balance as integral,count(u2.c_recommendation) as recommendation_total,count(id.c_id) as share_total");
        StringBuilder cond = new StringBuilder(" from t_hpp_mobile_user u\n" +
                "\tLEFT JOIN t_hpp_user u2 ON u.c_recommendation = u2.c_recommendation and u2.c_status='"+DevplatformConstants.HPP_USER_STATUS_PASSED+"'\n"+
                "\tLEFT JOIN t_hpp_integral i on i.c_mobile_num = u.c_phone\n"+
                "\tLEFT JOIN t_hpp_integral_detail id ON id.c_mobile = u.c_phone and (id.c_type='"+ DevplatformConstants.INTEGRAL_DETAIL_TYPE_COMMUNITY+"' or id.c_type='"+DevplatformConstants.INTEGRAL_DETAIL_TYPE_FRIEND+"')");
        cond.append(" where 1 = 1 ");

        if (StringUtils.isNotBlank(mobile)) {
            cond.append(" and u.c_phone like '%" + mobile + "%' ");
        }

        if (StringUtils.isNotBlank(type)) {
            if(type.equals("follow")){
                cond.append(" and u.c_follow_flag='Y' ");
            }else if(type.equals("buy")){
                cond.append(" and u.c_buy_flag='Y' ");
            }else if(type.equals("open")){
                cond.append(" and u.c_open_flag='Y' ");
            }
        }
        if(StringUtils.isNotBlank(startTime)){
            cond.append(" and TO_DAYS(u.d_create_at)>=TO_DAYS('"+startTime+"') ");
        }

        if(StringUtils.isNotBlank(endTime)){
            cond.append(" and TO_DAYS(u.d_create_at)<=TO_DAYS('"+endTime+"') ");
        }
        // String orderBy = " order by c_mobile_num asc ";
        cond.append(" GROUP BY u.c_id");

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName="u."+ ReflectUtils.getColumnName(HppMobileUser.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<HppMobileUserDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, HppMobileUserDTO.class);
        return page;
    }

    @Override
    public HppMobileUserDTO findByMobile(String phone) {
        String sql ="SELECT\n" +
                "\tu.*, count(u2.c_recommendation_ed) AS recommendation_total\n" +
                "FROM\n" +
                "\tt_hpp_mobile_user u\n" +
                "LEFT JOIN t_hpp_mobile_user u2 ON u.c_recommendation = u2.c_recommendation_ed\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "and u.c_phone='"+phone+"'";
        List<HppMobileUserDTO> list = baseDao.findListBySql(sql,HppMobileUserDTO.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<HppMobileUserDTO> findAll() {
        return hppMobileUserMapper.toDto(hppMobileUserRepository.findAll());
    }

    @Override
    public HppMobileUserDTO findByRecommendation(String recommendation) {
        return hppMobileUserMapper.toDto(hppMobileUserRepository.findByRecommendationEquals(recommendation));
    }

    @Override
    public HashMap<String, Object> setCid(HashMap<String, Object> params) {
        HashMap<String, Object>  retMap = new HashMap<>();
        String mobile = params.get("mobile") == null? "":(String)params.get("mobile");
        String cid = params.get("cid") == null? "":(String)params.get("cid");

        HppMobileUserDTO hppMobileUserDTO = findByMobile(mobile);
        if(StringUtils.isBlank(mobile) || hppMobileUserDTO == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "用户不存在");
            return retMap;
        }

        if(StringUtils.isBlank(cid)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "cid不能为空");
            return retMap;
        }
        hppMobileUserDTO.setCid(cid);
        hppMobileUserRepository.save(hppMobileUserMapper.toEntity(hppMobileUserDTO));
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public void followFlagY(String mobile) {
        HppMobileUserDTO hppMobileUserDTO = this.findByMobile(mobile);
        hppMobileUserDTO.setFollowFlag("Y");
        hppMobileUserRepository.save(hppMobileUserMapper.toEntity(hppMobileUserDTO));
    }

    @Override
    public void followFlagN(String mobile) {
        HppMobileUserDTO hppMobileUserDTO = this.findByMobile(mobile);
        hppMobileUserDTO.setFollowFlag("N");
        hppMobileUserRepository.save(hppMobileUserMapper.toEntity(hppMobileUserDTO));
    }

    @Override
    public void buyFlagY(String mobile) {
        HppMobileUserDTO hppMobileUserDTO = this.findByMobile(mobile);
        hppMobileUserDTO.setBuyFlag("Y");
        hppMobileUserRepository.save(hppMobileUserMapper.toEntity(hppMobileUserDTO));
    }

    @Override
    public void openFlag(String mobile) {
        HppMobileUserDTO hppMobileUserDTO = this.findByMobile(mobile);
        hppMobileUserDTO.setOpenFlag("Y");
        hppMobileUserRepository.save(hppMobileUserMapper.toEntity(hppMobileUserDTO));
    }


    @Override
    public List<HppMobileUserDTO> findWithFollowFlag() {
        return hppMobileUserMapper.toDto(hppMobileUserRepository.findWithFollowFlag());
    }

    @Override
    public List<HppMobileUserDTO> findWithBuyFlag() {
        return hppMobileUserMapper.toDto(hppMobileUserRepository.findWithBuyFlag());
    }

    @Override
    public Page<HppMobileUserDTO> findByRecommendationEd(HashMap<String,Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");
        String recommendation = formParams.get("recommendation") == null ? "" : formParams.get("recommendation").toString();
        String mobile = formParams.get("mobile") == null ? "" : formParams.get("mobile").toString();
        StringBuilder column = new StringBuilder(" select *");
        StringBuilder cond = new StringBuilder(" from t_hpp_mobile_user where c_id IN (select u.c_user_id from t_hpp_user u where u.c_recommendation ='"+recommendation+"' and u.c_status='"+DevplatformConstants.HPP_USER_STATUS_PASSED+"')");
        if (StringUtils.isNotBlank(mobile)) {
            cond.append(" and c_phone like '%" + mobile + "%' ");
        }

        Page<HppMobileUserDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, HppMobileUserDTO.class);
        return page;
    }
}
