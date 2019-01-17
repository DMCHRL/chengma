package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.domain.HppIntegralDetail;
import com.chengma.devplatform.domain.HppStrategy;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.HppIntegralRepository;
import com.chengma.devplatform.service.HppIntegralDetailService;
import com.chengma.devplatform.service.HppIntegralService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.HppIntegralDTO;
import com.chengma.devplatform.service.dto.HppIntegralDetailDTO;
import com.chengma.devplatform.service.dto.HppIntegralViewDTO;
import com.chengma.devplatform.service.mapper.HppIntegralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class HppIntegralServiceImpl implements HppIntegralService {

    private final HppIntegralRepository hppIntegralRepository;

    private final HppIntegralMapper hppIntegralMapper;



    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private HppIntegralDetailService hppIntegralDetailService;

    public HppIntegralServiceImpl( HppIntegralRepository hppIntegralRepository,HppIntegralMapper hppIntegralMapper){
        this.hppIntegralRepository=hppIntegralRepository;
        this.hppIntegralMapper=hppIntegralMapper;
    }
    
    @Override
    public Page<HppIntegralDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public HppIntegralDTO save(HppIntegralDTO hppIntegralDTO) {
        return hppIntegralMapper.toDto(hppIntegralRepository.save(hppIntegralMapper.toEntity(hppIntegralDTO)));
    }

    @Override
    public HppIntegralDTO findOne(String id) {
        return hppIntegralMapper.toDto(hppIntegralRepository.findOne(id));
    }

    @Override
    public void delete(String id) {
        hppIntegralRepository.delete(id);
    }

    @Override
    public HppIntegralDTO findByMobileNum(String mobileNum) {
        return hppIntegralMapper.toDto(hppIntegralRepository.findByMobileNum(mobileNum));
    }

    public void addIntegral(String mobile,Double total){
        HppIntegralDTO hppIntegralDTO = findByMobileNum(mobile);
        if(hppIntegralDTO == null) return;
        hppIntegralDTO.setBalance(hppIntegralDTO.getBalance()+total);
        hppIntegralDTO.setTotal(hppIntegralDTO.getTotal()+total);
        this.save(hppIntegralDTO);
    }

    @Override
    public void reduceIntegral(String mobile, Double total) {
        HppIntegralDTO hppIntegralDTO = findByMobileNum(mobile);
        if(hppIntegralDTO == null) return;
        Double balance = hppIntegralDTO.getBalance();
        if(balance>=total){
            hppIntegralDTO.setBalance(balance-total);
            hppIntegralDTO.setUsed(hppIntegralDTO.getUsed()+total);
            this.save(hppIntegralDTO);
        }
    }

    /**
     * 分享到朋友圈
     * @param mobile
     */
    @Override
    public void shareToCommunity(String mobile) {
        List<HppIntegralDetailDTO> list =  hppIntegralDetailService.findByCommunityOrFriend(DevplatformConstants.INTEGRAL_DETAIL_TYPE_COMMUNITY,mobile);

        //每天分享前两次加分
        if(list == null || list.size() <DevplatformConstants.SHARE_COMMUNITY_NUM){
            addIntegral(mobile,DevplatformConstants.INTEGRAL_COMMUNITY);
            HppIntegralDetailDTO hppIntegralDetailDTO = new HppIntegralDetailDTO(mobile,new Date(), DevplatformConstants.INTEGRAL_IN,DevplatformConstants.INTEGRAL_DETAIL_TYPE_COMMUNITY,DevplatformConstants.INTEGRAL_COMMUNITY,null);
            hppIntegralDetailService.save(hppIntegralDetailDTO);
        }
    }

    /**
     * 分享到好友
     * @param mobile
     */
    @Override
    public void shareToFriend(String mobile) {
        List<HppIntegralDetailDTO> list =  hppIntegralDetailService.findByCommunityOrFriend(DevplatformConstants.INTEGRAL_DETAIL_TYPE_FRIEND,mobile);

        if(list == null || list.size() <DevplatformConstants.SHARE_FRIEND_NUM){
            addIntegral(mobile,DevplatformConstants.INTEGRAL_FRIEND);
            HppIntegralDetailDTO hppIntegralDetailDTO = new HppIntegralDetailDTO(mobile,new Date(), DevplatformConstants.INTEGRAL_IN,DevplatformConstants.INTEGRAL_DETAIL_TYPE_FRIEND,DevplatformConstants.INTEGRAL_FRIEND,null);
            hppIntegralDetailService.save(hppIntegralDetailDTO);
        }
    }

    @Override
    public void login(String mobile) {
        List<HppIntegralDetailDTO> list =  hppIntegralDetailService.findByCommunityOrFriend(DevplatformConstants.INTEGRAL_DETAIL_TYPE_LOGIN,mobile);

        if(list == null || list.size() <DevplatformConstants.SHARE_LOGIN_NUM){
            addIntegral(mobile,DevplatformConstants.INTEGRAL_LOGIN);
            HppIntegralDetailDTO hppIntegralDetailDTO = new HppIntegralDetailDTO(mobile,new Date(), DevplatformConstants.INTEGRAL_IN,DevplatformConstants.INTEGRAL_DETAIL_TYPE_LOGIN,DevplatformConstants.INTEGRAL_LOGIN,null);
            hppIntegralDetailService.save(hppIntegralDetailDTO);
        }
    }

    @Override
    public List<HppIntegralViewDTO> findList() {
        User user = userService.getUserWithAuthorities();

        if(user.getMobile() != null){
            String mobile = user.getMobile();
            String sql = "SELECT\n" +
                    "\tv.c_id AS id,\n" +
                    "\tv.c_img AS img,\n" +
                    "\t'video' AS type,\n" +
                    "\tv.c_video_name AS NAME,\n" +
                    "\tv.i_price AS price,\n" +
                    "\tIFNULL(v.i_exchange_num, 0) AS exchange_num,\n" +
                    "\t(\n" +
                    "\t\tCASE\n" +
                    "\t\tWHEN (\n" +
                    "\t\t\tSELECT\n" +
                    "\t\t\t\tcount(e.c_mobile_num)\n" +
                    "\t\t\tFROM\n" +
                    "\t\t\t\tt_hpp_exchange e\n" +
                    "\t\t\tWHERE\n" +
                    "\t\t\t\te.c_body_id = v.c_id\n" +
                    "\t\t\tAND e.c_mobile_num = '"+mobile+"'\n" +
                    "\t\t) >= 1 THEN\n" +
                    "\t\t\t'Y'\n" +
                    "\t\tELSE\n" +
                    "\t\t\t'N'\n" +
                    "\t\tEND\n" +
                    "\t) AS status\n" +
                    "FROM\n" +
                    "\tt_hpp_video v\n" +
                    "WHERE\n" +
                    "\ti_price > 0";
            return baseDao.findListBySql(sql, HppIntegralViewDTO.class);
        }
        return null;
    }

    @Override
    public void setIntegral(HashMap<String, Object> params) {
        String mobile = params.get("mobile") == null ? "" : (String)params.get("mobile");
        Double integral = params.get("integral") == null ? 0.0 : Double.valueOf(params.get("integral").toString());
        HppIntegralDTO hppIntegralDTO= findByMobileNum(mobile);
        Double balance = hppIntegralDTO.getBalance();

        if(integral < 0 && balance >= integral){
            this.reduceIntegral(mobile,-integral);
            HppIntegralDetailDTO out = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_OUT, DevplatformConstants.INTEGRAL_DETAIL_TYPE_SYSTEM, -integral, null);
            hppIntegralDetailService.createHppIntegralDetailDTO(out);
        }else if(integral >= 0){
            this.addIntegral(mobile,integral);
            HppIntegralDetailDTO in = new HppIntegralDetailDTO(mobile, new Date(), DevplatformConstants.INTEGRAL_IN, DevplatformConstants.INTEGRAL_DETAIL_TYPE_SYSTEM, integral, null);
            hppIntegralDetailService.createHppIntegralDetailDTO(in);
        }
    }

    @Override
    public List<HppIntegralDetailDTO> findDetailByMobile(String mobile) {
        return hppIntegralDetailService.findByMobile(mobile);
    }
}
