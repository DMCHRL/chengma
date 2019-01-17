package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.HppExchange;
import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.domain.HppMobileUser;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppExchangeRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppExchangeMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class HppExchangeServiceImpl implements HppExchangeService {

    private final HppExchangeRepository hppExchangeRepository;

    private final HppExchangeMapper hppExchangeMapper;


    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private HppVideoService hppVideoService;

    @Autowired
    private HppIntegralService hppIntegralService;

    @Autowired
    private BaseDao baseDao;

    public HppExchangeServiceImpl( HppExchangeRepository hppExchangeRepository,HppExchangeMapper hppExchangeMapper){
        this.hppExchangeRepository=hppExchangeRepository;
        this.hppExchangeMapper=hppExchangeMapper;
    }

    @Override
    public HppExchangeDTO findByMobileIdAndVideoId(String mobileId, String videoId) {
        return hppExchangeMapper.toDto(hppExchangeRepository.findByMobileIdAndVideoId(mobileId,videoId));
    }

    @Override
    public Page<HppExchangeDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        /*String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();*/

        StringBuilder column = new StringBuilder(" SELECT\n" +
                "\te.*,v.c_video_name,vt.c_video_type_name  ");

        StringBuilder cond = new StringBuilder(" FROM\n" +
                "\tt_hpp_exchange e JOIN t_hpp_video v ON e.c_video_id = v.c_id\n" +
                "JOIN t_hpp_video_type vt ON vt.c_id =v.c_video_type_id ");

        //非超级管理员
        /*User currentUser=userService.getUserWithAuthorities();
        if(!currentUser.equals(EnumRole.ADMIN.value())){
            cond.append(" and vt.c_user_id ='"+currentUser.getId()+"'");
        }*/

        Page<HppExchangeDTO> page = pageCommon.execPage(column.toString(), cond.toString(),  page_number, page_size, HppExchangeDTO.class);
        return page;
    }

    @Override
    public HppExchangeDTO save(HppExchangeDTO hppExchangeDTO) {
        return hppExchangeMapper.toDto(hppExchangeRepository.save(hppExchangeMapper.toEntity(hppExchangeDTO)));
    }

    @Override
    public HppExchangeDTO findOne(String id) {
        return hppExchangeMapper.toDto(hppExchangeRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppExchangeRepository.delete(id);
    }

    @Override
    public HashMap<String, Object> checkExchange(HashMap<String, Object> params) {
        HashMap<String, Object>  retMap = new HashMap<>();
        String mobileNum = (params.get("mobileNum") == null ? "" : (params.get("mobileNum").toString()));
        String videoId = (params.get("videoId") == null ? "" : (params.get("videoId").toString()));

        if(StringUtils.isBlank(mobileNum)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录...");
            return retMap;
        }
        if(StringUtils.isBlank(videoId)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择视频");
            return retMap;
        }

        if(this.findByMobileIdAndVideoId(mobileNum,videoId)!=null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "该视频已兑换");
            return retMap;
        }
        HppVideoDTO hppVideoDTO=hppVideoService.findOne(videoId);
        HppIntegralDTO hppIntegralDTO=hppIntegralService.findByMobileNum(mobileNum);
        Double integralPrice=hppVideoDTO.getIntegralPrice();
        Double balance=hppIntegralDTO.getBalance()-integralPrice;
        if(balance<0){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "积分不足");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HppExchangeDTO exchange(HashMap<String, Object> params) {
        String mobileNum = (params.get("mobileNum") == null ? "" : (params.get("mobileNum").toString()));
        String videoId = (params.get("videoId") == null ? "" : (params.get("videoId").toString()));

        HppVideoDTO hppVideoDTO=hppVideoService.findOne(videoId);
        HppIntegralDTO hppIntegralDTO=hppIntegralService.findByMobileNum(mobileNum);
        Double integralPrice=hppVideoDTO.getIntegralPrice();
        Double balance=hppIntegralDTO.getBalance()-integralPrice;
        //更新积分
        hppIntegralDTO.setBalance(balance);
        hppIntegralDTO.setUsed(hppIntegralDTO.getUsed()+integralPrice);
        hppIntegralService.save(hppIntegralDTO);

        //更新兑换次数
        int exchangeNum = hppVideoDTO.getExchangeNum() == null ? 0 : hppVideoDTO.getExchangeNum();
        hppVideoDTO.setExchangeNum(exchangeNum+1);
        hppVideoService.save(hppVideoDTO);

        //保存兑换信息
        HppExchangeDTO hppExchangeDTO=new HppExchangeDTO();
        hppExchangeDTO.setCreateAt(new Date());
        hppExchangeDTO.setMobileNum(mobileNum);
        hppExchangeDTO.setPrice(integralPrice);
        return hppExchangeMapper.toDto(hppExchangeRepository.save(hppExchangeMapper.toEntity(hppExchangeDTO)));
    }

    @Override
    public List<HppIntegralViewDTO> findList() {
        User user = userService.getUserWithAuthorities();
        if(user.getMobile() !=null){
            String sql = "select * from t_hpp_exchange_view where mobile='"+user.getMobile()+"'";
            return baseDao.findListBySql(sql,HppIntegralViewDTO.class);
        }
        return null;
    }
}
