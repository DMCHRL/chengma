package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumGroup;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.EnvUtil;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.common.util.StringUtil;
import com.chengma.devplatform.domain.TlbAccount;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.TlbAccountRepository;
import com.chengma.devplatform.service.MailService;
import com.chengma.devplatform.service.TlbAccountDataService;
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.mapper.TlbAccountMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class TlbAccountServiceImpl implements TlbAccountService {

    private final TlbAccountRepository tlbAccountRepository;

    private final TlbAccountMapper tlbAccountMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private SerialNoService serialNoService;

    @Autowired
    private UserService userService;

    @Autowired
    private TlbAccountDataService tlbAccountDataService;

    public TlbAccountServiceImpl(TlbAccountRepository tlbAccountRepository, TlbAccountMapper tlbAccountMapper){
        this.tlbAccountRepository=tlbAccountRepository;
        this.tlbAccountMapper=tlbAccountMapper;
    }
    
    @Override
    public Page<TlbAccountDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


       /* String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String accountName = formParams.get("accountName") == null ? "" : formParams.get("accountName").toString();
        String parentId = formParams.get("parentId") == null ? "" : formParams.get("parentId").toString();
        String group = formParams.get("group") == null ? "" : formParams.get("group").toString();
        String eaGroup = formParams.get("eaGroup") == null ? "" : formParams.get("eaGroup").toString();

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();*/

        StringBuilder column = new StringBuilder(" select a.* ");
        StringBuilder cond = new StringBuilder(" from t_tlb_account a join jhi_user u on a.c_user_id=u.id  ");

        Page<TlbAccountDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, TlbAccountDTO.class);
        return page;
    }

    @Override
    public TlbAccountDTO save(TlbAccountDTO tlbAccountDTO) {
        return tlbAccountMapper.toDto(tlbAccountRepository.save(tlbAccountMapper.toEntity(tlbAccountDTO)));
    }

    @Override
    public TlbAccountDTO createTlbAccountDTO(TlbAccountDTO tlbAccountDTO) {
        //添加
        if(StringUtils.isBlank(tlbAccountDTO.getId())){
            HashMap<String, Object> response = new HashMap<>();
            if(tlbAccountDTO.getId() == null ){
                tlbAccountDTO.setEnableTrade("N");
                tlbAccountDTO.setComment("N");
                tlbAccountDTO.setCreateAt(new Date());
                tlbAccountDTO.setMt4Password(DevplatformConstants.USER_DEFAULT_PASSWORD);
                tlbAccountDTO.setSeePassword(DevplatformConstants.TLB_ACCOUNT_SEE_PASSWORD);

                //accountName默认为crm的firstName
                String userId = tlbAccountDTO.getUserId();
                if(StringUtils.isNotBlank(userId)){
                    User user = userService.getUserWithAuthorities(userId);
                    tlbAccountDTO.setAccountName(user.getFirstName());
                }
                if(StringUtils.isEmpty(tlbAccountDTO.getAccount())){
                    tlbAccountDTO.setAccount(genAccountNo("TXA3"));
                }
            }
            TlbAccount tlbAccount = tlbAccountMapper.toEntity(tlbAccountDTO);
            tlbAccount = tlbAccountRepository.save(tlbAccount);

            //初始化交易账号具体数据
            tlbAccountDataService.initTlbAccountDataDTO(tlbAccount.getAccount());

            return tlbAccountMapper.toDto(tlbAccount);
        }else{
            //修改
        }
        
        return tlbAccountMapper.toDto(tlbAccountRepository.save(tlbAccountMapper.toEntity(tlbAccountDTO)));
    }

    @Override
    public HashMap<String, Object> checkCreateTlbAccountDTO(TlbAccountDTO tlbAccountDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public TlbAccountDTO findOne(String id) {
        return tlbAccountMapper.toDto(tlbAccountRepository.findOne(id));
    }

    @Override
    public List<TlbAccountDTO> findAll() {
        return tlbAccountMapper.toDto(tlbAccountRepository.findAll());
    }

    @Override
    public void delete(String id) {
        tlbAccountDataService.deleteByAccount(findOne(id).getAccount());
        tlbAccountRepository.delete(id);
    }

    @Override
    public String genAccountNo(String group) {
        //TODO
        String accountNo = serialNoService.getAccountNo(EnumGroup.valueOf(group).value());
        while(findByAccount(accountNo) != null){
            accountNo = serialNoService.getAccountNo(EnumGroup.valueOf(group).value());
        }
        return accountNo;
    }

    @Override
    public TlbAccountDTO findByAccount(String account) {
        return tlbAccountMapper.toDto(tlbAccountRepository.findOneByAccountEquals(account));
    }
}
