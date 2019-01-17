package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.TlbAccountControl;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.TlbAccountControlRepository;
import com.chengma.devplatform.service.TlbAccountControlService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.TlbAccountControlDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.mapper.TlbAccountControlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class TlbAccountControlServiceImpl implements TlbAccountControlService {

    private final Logger log = LoggerFactory.getLogger(TlbAccountControlServiceImpl.class);

    private final TlbAccountControlRepository tlbAccountControlRepository;

    private final TlbAccountControlMapper tlbAccountControlMapper;

    @Autowired
    private SerialNoService serialNoService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private UserService userService;

    public TlbAccountControlServiceImpl(TlbAccountControlRepository tlbAccountControlRepository, TlbAccountControlMapper tlbAccountControlMapper) {
        this.tlbAccountControlRepository = tlbAccountControlRepository;
        this.tlbAccountControlMapper = tlbAccountControlMapper;
    }

    @Override
    public TlbAccountDTO save(TlbAccountControlDTO tlbAccountControlDTO) {
        if(tlbAccountControlDTO==null)return null;

        TlbAccountDTO tradeTlbAccountDTO=tlbAccountService.findByAccountAndPassword(tlbAccountControlDTO.getAccount(),tlbAccountControlDTO.getAccountPassword());
        if(tradeTlbAccountDTO==null){
            tlbAccountControlDTO.setTrade(DevplatformConstants.ACCOUNT_CONTROL_TRADE_NO);
        }else{
            tlbAccountControlDTO.setTrade(DevplatformConstants.ACCOUNT_CONTROL_TRADE_YES);
        }
        User user=userService.getUserWithAuthorities();
        tlbAccountControlDTO.setUserId(user.getId());
        TlbAccountControl tlbAccountControl=tlbAccountControlMapper.toEntity(tlbAccountControlDTO);
        tlbAccountControlRepository.save(tlbAccountControl);

        //可交易賬戶
        TlbAccountDTO tlbAccountDTO=tlbAccountService.findByAccountAndPassword(tlbAccountControlDTO.getAccount(),tlbAccountControlDTO.getAccountPassword());

        if(tlbAccountDTO == null){
            tlbAccountDTO=tlbAccountService.findByAccountEqualsAndSeePasswordEquals(tlbAccountControlDTO.getAccount(),tlbAccountControlDTO.getAccountPassword());
            tlbAccountDTO.setMt4Password(tlbAccountControlDTO.getAccountPassword());
        }
        tlbAccountDTO.setSeePassword(null);

        if(tlbAccountControl.getTrade().equals(DevplatformConstants.ACCOUNT_CONTROL_TRADE_YES)){
            tlbAccountDTO.setEnableTrade("Y");
        }else if(tlbAccountControl.getTrade().equals(DevplatformConstants.ACCOUNT_CONTROL_TRADE_NO)){
            tlbAccountDTO.setEnableTrade("N");
        }
        return tlbAccountDTO;
    }

    public List<TlbAccountDTO> loadRelationAccount(String userId) {
        return tlbAccountService.findByUserId(userId);
    }

    @Override
    public void delete(String id) {
        tlbAccountControlRepository.delete(id);
    }

    @Override
    public void deleteByAccount(String account,String trade) {
        tlbAccountControlRepository.deleteByAccount(account,trade);
    }

    @Override
    public HashMap<String, Object> checkTlbAccountControlDTO(TlbAccountControlDTO tlbAccountControlDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        //可交易賬戶
        TlbAccountDTO tradeAccount=tlbAccountService.findByAccountAndPassword(tlbAccountControlDTO.getAccount(),tlbAccountControlDTO.getAccountPassword());

        //觀摩賬戶
        TlbAccountDTO seeAccount=tlbAccountService.findByAccountEqualsAndSeePasswordEquals(tlbAccountControlDTO.getAccount(),tlbAccountControlDTO.getAccountPassword());
        if(tradeAccount==null&&seeAccount==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入有效賬號");
            return retMap;
        }

        User currentUser= userService.getUserWithAuthorities();
        if(currentUser==null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "請輸入有效用戶");
            return retMap;
        }
       /* String userId=tradeAccount==null?seeAccount.getUserId():tradeAccount.getUserId();
        if(currentUser.getId().equals(userId)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "自己賬號免關聯");
            return retMap;
        }*/

       /* TlbAccountControl tlbAccountControl=tlbAccountControlRepository.findByUserIdEqualsAndAccountEquals(currentUser.getId(),tlbAccountControlDTO.getAccount());
        if(tlbAccountControl!=null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "已添加...");
            return retMap;
        }*/
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public TlbAccountControlDTO findByUserIdEqualsAndAccountEqualsAndTradeEquals(String userId, String account, String trade) {
        return tlbAccountControlMapper.toDto(tlbAccountControlRepository.findByUserIdEqualsAndAccountEqualsAndTradeEquals(userId,account,trade));
    }
}
