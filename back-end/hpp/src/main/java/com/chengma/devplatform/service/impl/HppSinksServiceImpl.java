package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.HppSinks;
import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppSinksRepository;
import com.chengma.devplatform.service.FileStreamManageService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppSinksDTO;
import com.chengma.devplatform.service.HppSinksService;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.service.mapper.HppSinksMapper;
import org.apache.commons.lang.StringUtils;
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
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class HppSinksServiceImpl implements HppSinksService {

    private final Logger log = LoggerFactory.getLogger(HppSinksServiceImpl.class);

    private final HppSinksRepository hppSinksRepository;

    private final HppSinksMapper hppSinksMapper;



    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private DBService dbService;

    @Autowired
    private BaseDao baseDao;

    public HppSinksServiceImpl(HppSinksRepository hppSinksRepository, HppSinksMapper hppSinksMapper) {
        this.hppSinksRepository = hppSinksRepository;
        this.hppSinksMapper = hppSinksMapper;
    }

    @Override
    public HppSinksDTO save(HppSinksDTO hppSinksDTO) {

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

        if(!StringUtils.isEmpty(hppSinksDTO.getBackground()) && hppSinksDTO.getBackground().startsWith(prefix)) {
            fileStreamManageService.uploadImage(decoder, hppSinksDTO.getBackground(), imagePath, prefix, newFileName);
            hppSinksDTO.setBackground(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }

        if(!StringUtils.isEmpty(hppSinksDTO.getListImg()) && hppSinksDTO.getListImg().startsWith(prefix)) {
            newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            fileStreamManageService.uploadImage(decoder,hppSinksDTO.getBackground(), imagePath, prefix, newFileName);
            hppSinksDTO.setListImg(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }

        if(StringUtils.isBlank(hppSinksDTO.getId())){
            Date now = new Date();
            hppSinksDTO.setCreateTime(now);
            hppSinksDTO.setUpdateTime(now);
        }else{
            HppSinks hppSinks = hppSinksRepository.findOne(hppSinksDTO.getId());
            hppSinksDTO.setCreateTime(hppSinks.getCreateTime());
            hppSinksDTO.setUpdateTime(new Date());
        }
        HppSinks hppSinks =hppSinksMapper.toEntity(hppSinksDTO);
        HppSinks hppSinks1 =hppSinksRepository.save(hppSinks);
        return hppSinksMapper.toDto(hppSinks1);
    }

    @Override
    public  HashMap<String,Object> checkSave(HppSinksDTO hppSinksDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(StringUtils.isBlank(hppSinksDTO.getSinksName())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入汇商名称");
            return retMap;
        }
        if(StringUtils.isBlank(hppSinksDTO.getSinksName2())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入汇商次名");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getListImg())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传列表图片");
            return retMap;
        }
        if(StringUtils.isBlank(hppSinksDTO.getBackground())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传背景图片");
            return retMap;
        }
        if(hppSinksDTO.getOpenTime() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入成立时间");
            return retMap;
        }

        if(hppSinksDTO.getCountry() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入所属国家");
            return retMap;
        }

        if(hppSinksDTO.getLots() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入最小手数");
            return retMap;
        }

        if(hppSinksDTO.getLever() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入最大杠杆");
            return retMap;
        }

        if(hppSinksDTO.getPointType() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入点数类型");
            return retMap;
        }

        if(hppSinksDTO.getMainPoint() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入主要点差");
            return retMap;
        }

        if(hppSinksDTO.getTradeType() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入交易品种");
            return retMap;
        }

        if(hppSinksDTO.getRemark() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入平台介绍");
            return retMap;
        }

        if(hppSinksDTO.getPlatformType() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入平台类型");
            return retMap;
        }

        if(hppSinksDTO.getAccountType() == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入开设账户类型");
            return retMap;
        }
        String peel = hppSinksDTO.getPeel();
        if(StringUtils.isBlank(peel)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入剥头皮");
            return retMap;
        }

        String hedging = hppSinksDTO.getHedging();
        if(StringUtils.isBlank(hedging)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入对冲");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getMaxTrade())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入最大交易量");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getFundOut())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入出金方式");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getFundIn())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入入金方式");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getFundOutCost())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入出金手续费");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getFundInCost())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入入金手续费");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getRollovers())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入隔夜利息");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getExplosionRatio())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入爆仓比例");
            return retMap;
        }
        String rmb = hppSinksDTO.getRmb();
        if(StringUtils.isBlank(rmb)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入是否支持人民币入金");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getEvaluate())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入评价");
            return retMap;
        }

        if(StringUtils.isBlank(hppSinksDTO.getEvaluateScore())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入评价分数");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public Page<HppSinksDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String sinksName = formParams.get("sinksName") == null ? "" : formParams.get("sinksName").toString();
       /* String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();*/

        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_hpp_sinks where 1=1 ");

        /*//非超级管理员
        User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.getDepartment().equals(EnumRole.ADMIN.value())){
            cond.append(" and t.c_user_id='"+currentUser.getId()+"'");
        }*/

        if(StringUtils.isNotBlank(sinksName)){
            cond.append(" and c_sinks_name like '%"+sinksName+"%' ");
        }

       /* String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "h."+ ReflectUtils.getColumnName(HppVideo.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }*/
        Page<HppSinksDTO> page = pageCommon.execPage(column.toString(), cond.toString(),page_number, page_size, HppSinksDTO.class);
        return page;
    }

    @Override
    public HppSinksDTO findOne(String id) {
        return hppSinksMapper.toDto(hppSinksRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppSinksRepository.delete(id);
    }

    @Override
    public List<HppSinksDTO> findList() {
        String sql = "select c_id,c_sinks_name,c_sinks_name2,c_explain,c_list_img from t_hpp_sinks";
        List<HppSinksDTO> list = baseDao.findListBySql(sql,HppSinksDTO.class);
        return list;
    }
}
