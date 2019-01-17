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
import com.chengma.devplatform.service.TlbAccountService;
import com.chengma.devplatform.service.TlbFundApplyService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbFundApplyDTO;
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
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class TlbAccountServiceImpl implements TlbAccountService {

    private final Logger log = LoggerFactory.getLogger(TlbAccountServiceImpl.class);

    private final TlbAccountRepository tlbAccountRepository;

    private final TlbAccountMapper tlbAccountMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private SerialNoService serialNoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @Autowired
    private BaseDao baseDao;

    @Autowired
    private MT4Service mt4Service;

    @Autowired
    private MailService mailService;

    @Autowired
    private TlbFundApplyService tlbFundApplyService;

    public TlbAccountServiceImpl(TlbAccountRepository tlbAccountRepository, TlbAccountMapper tlbAccountMapper) {
        this.tlbAccountRepository = tlbAccountRepository;
        this.tlbAccountMapper = tlbAccountMapper;
    }

    /**
     * Save a tlbAccount.
     */
    @Override
    public TlbAccountDTO save(TlbAccountDTO tlbAccountDTO) {
        HashMap<String, Object> response = new HashMap<>();
        if(tlbAccountDTO.getId() == null ){
            tlbAccountDTO.setEnableTrade("Y");
            tlbAccountDTO.setComment("N");
            tlbAccountDTO.setCreateAt(new Date());
            if(StringUtils.isEmpty(tlbAccountDTO.getAccount())){
                tlbAccountDTO.setAccount(genAccountNo(tlbAccountDTO.getGroup()));
            }
        }
        TlbAccount tlbAccount = tlbAccountMapper.toEntity(tlbAccountDTO);
        tlbAccount = tlbAccountRepository.save(tlbAccount);
        return tlbAccountMapper.toDto(tlbAccount);
    }

    @Override
    public TlbAccountDTO createCrmTlb(HashMap<String, Object> params) {

        User user = saveUser(params);
        //發送郵件
        mailService.sendCreateUserMail(user);

        String accountNo = params.get("account") != null ? params.get("account").toString(): genAccountNo(params.get("group").toString());

        TlbAccountDTO accountDTO = new TlbAccountDTO();
        accountDTO.setUserId(user.getId());
        accountDTO.setAccountName(user.getFirstName());
        accountDTO.setAccount(accountNo);
        accountDTO.setEmail(params.get("email").toString());
        accountDTO.setGroup(params.get("group").toString());
        accountDTO.setMt4Password(params.get("mt4Password") == null ? DevplatformConstants.USER_DEFAULT_PASSWORD : params.get("mt4Password").toString());

        //發送郵件
        mailService.sendCreateTlbAccountMail(user, accountDTO);

        return save(accountDTO);
    }

    @Override
    public HashMap<String, Object> checkAccountInfo(HashMap<String, Object> params) {
        HashMap<String, Object> retMap = new HashMap<>();

        String account = params.get("account") == null ? "" : params.get("account").toString();
        String group = params.get("group") == null ? "" : params.get("group").toString();

        if(StringUtils.isEmpty(group)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "组别为空");
            return retMap;
        }
        if(!StringUtils.isEmpty(account) && null != findByAccount(account)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "套利宝帐号已经存在,请重新输入");
            return retMap;
        }

        User user = userService.getUserWithAuthorities();
        if(!EnumRole.ADMIN.value().equals(user.getDepartment())) {
            List<TlbAccountDTO> list = findEmptyAccounts(user.getId());
           if(!list.isEmpty() && list.size() >= DevplatformConstants.ALLOW_ZROA_MONEY_ACCOUNT){
               retMap.put("statusCode", ResponseResult.FAIL_CODE);
               retMap.put("msg", "您已拥有两个零余额的账户，不可再开户");
               return retMap;
           }
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HashMap<String, Object> checkCrmMobile(HashMap<String, Object> params) {
        HashMap<String, Object> retMap = new HashMap<>();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        String email = params.get("email") == null ? "" : params.get("email").toString();

        if(StringUtils.isEmpty(email)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "邮箱为空");
            return retMap;
        }
        if(null != userService.requestPasswordReset(email)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "此邮箱已经注册CRM，请联系客户");
            return retMap;
        }

        if(StringUtils.isEmpty(mobile)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "电话号码为空");
            return retMap;
        }

        if(null != userService.findUserByMobile(mobile)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "此电话号码已经注册CRM，请联系客户");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    /**
     * findAll
     */
    @Override
    @Transactional(readOnly = true)
    public List<TlbAccountDTO> findAll(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String c_account_name = formParams.get("accountName") == null ? "" : formParams.get("accountName").toString();

        StringBuilder column = new StringBuilder(" select a.*, if(b.success_count, 0) success_count, if(b.success_rate, 0) success_rate, if(b.total_count, 0) total_count");
        StringBuilder cond = new StringBuilder(" from t_tlb_account a ");
        cond.append(" left join (" + tradeStr() + ") b on a.c_account = b.c_account ");
        cond.append(" where 1 = 1 ");

        if (StringUtils.isNotBlank(c_account_name)) {
            cond.append(" and c_account_name like '%" + c_account_name + "%' ");
        }
        String orderBy = " order by c_account desc ";
        Page<TlbAccountDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TlbAccountDTO.class);
        HashMap<String, Object> response = new HashMap<>();


        return null;
    }

    @Override
    public List<TlbAccountDTO> findEmptyAccounts(String userId) {
        return tlbAccountMapper.toDto(tlbAccountRepository.findEmptyAccounts(userId));
    }

    @Override
    public Page<TlbAccountDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");


        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String accountName = formParams.get("accountName") == null ? "" : formParams.get("accountName").toString();
        String parentId = formParams.get("parentId") == null ? "" : formParams.get("parentId").toString();
        String group = formParams.get("group") == null ? "" : formParams.get("group").toString();

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        StringBuilder column = new StringBuilder(" select a.*, ifnull(c.first_name, '') parent_name ");
        column.append(", ifnull(c.success_count, 0) success_count, ifnull(c.success_rate, 0) success_rate, ifnull(c.total_count, 0) total_count");
        column.append(", ifnull(c.total_lots, 0) total_lots");
        StringBuilder cond = new StringBuilder(" from t_tlb_account a join jhi_user as b on a.c_user_id = b.id left join jhi_user c on b.c_parent_id = c.id  ");
        cond.append(" left join (" + tradeStr() + ") c on a.c_account = c.c_account ");
        cond.append(" where 1 = 1 ");

        if (StringUtils.isNotBlank(accountName)) {
            cond.append(" and a.c_account_name like '%" + accountName + "%' ");
        }
        if (StringUtils.isNotBlank(account)) {
            cond.append(" and a.c_account like '%" + account + "%' ");
        }
        if (StringUtils.isNotBlank(group)) {
            cond.append(" and a.c_group = '" + group + "' ");
        }
        if (StringUtils.isNotBlank(parentId)) {
            cond.append(" and b.c_parent_id = '" + parentId + "' ");
        }

        String orderBy="";
        if (StringUtils.isNotBlank(orderByColumn)) {
            String columnName="";
            if(StringUtil.lineToHump("success_count").equals(orderByColumn)){
                columnName="c.success_count";
            }else if(StringUtil.lineToHump("success_rate").equals(orderByColumn)){
                columnName="c.success_rate";
            }else if(StringUtil.lineToHump("total_count").equals(orderByColumn)){
                columnName="c.total_count";
            }else if(StringUtil.lineToHump("total_lots").equals(orderByColumn)){
                columnName="c.total_lots";
            }else{
                columnName="a."+ ReflectUtils.getColumnName(TlbAccount.class,orderByColumn);
            }
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TlbAccountDTO> page = pageCommon.execPage(column.toString(), cond.toString(), orderBy, page_number, page_size, TlbAccountDTO.class);
        HashMap<String, Object> response = new HashMap<>();
        return page;
    }

    /**
     * Get one tlbAccount by id.
     */
    @Override
    @Transactional(readOnly = true)
    public TlbAccountDTO findOne(String id) {
        TlbAccount tlbAccount = tlbAccountRepository.findOne(id);
        return tlbAccountMapper.toDto(tlbAccount);
    }

    /**
     * findByCode
     */
    @Override
    @Transactional(readOnly = true)
    public TlbAccountDTO findByCode(TlbAccountDTO tlbAccountDTO) {
    /*    StringBuilder column = new StringBuilder(" select c_id ");
        column.append(" ,c_appid appid,c_mch_id mch_id,c_open_id open_id ");
        column.append(" ,c_prepay_id prepay_id,c_out_trade_no out_trade_no,c_transaction_id transaction_id ");
        column.append(" ,c_body body,c_total_fee total_fee,c_pay_success pay_success ");

        StringBuilder cond = new StringBuilder(" from t_tran_info where 1 = 1 ");

        cond.append(" and c_out_trade_no = '" + tlbAccountDTO.getOutAccountNo() + "' ");

        List<TlbAccountDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TlbAccountDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;*/
        return null;
    }

    @Override
    public TlbAccountDTO findByAccount(String account) {
        return tlbAccountMapper.toDto(tlbAccountRepository.findOneByAccountEquals(account));
    }

    @Override
    public TlbAccountDTO updateAccountMargin(String account, double lots) {

        double amount = lots * DevplatformConstants.PER_LOT_MONEY;

        TlbAccount tlbAccount = tlbAccountRepository.findOneByAccountEquals(account);
        double freeMargin =  tlbAccount.getFreeMargin()  - amount;
        tlbAccount.setFreeMargin(freeMargin);
        double margin  = (tlbAccount.getMargin() != null ? tlbAccount.getMargin() + amount : amount);
        tlbAccount.setMargin(margin);
        tlbAccount = tlbAccountRepository.save(tlbAccount);
        return tlbAccountMapper.toDto(tlbAccount);
    }

    @Override
    public TlbAccountDTO updateAccountBalance(String account, double amount) {
        TlbAccount tlbAccount = tlbAccountRepository.findOneByAccountEquals(account);
        tlbAccount.setFreeMargin(tlbAccount.getFreeMargin() == null ? amount : tlbAccount.getFreeMargin() + amount);
        tlbAccount.setBalance(tlbAccount.getBalance() == null ? amount : tlbAccount.getBalance() + amount);
        tlbAccount = tlbAccountRepository.save(tlbAccount);
        mt4Service.updateAccountMargin(account, amount);
        return tlbAccountMapper.toDto(tlbAccount);
    }

    @Override
    public boolean checkAccountAmount(String account, double amount) {
        TlbAccountDTO tlbAccount = tlbAccountMapper.toDto(tlbAccountRepository.findOneByAccountEquals(account));

        //已经申请的出金
        List<TlbFundApplyDTO> list = tlbFundApplyService.findTlbFundApplyList(account, DevplatformConstants.FUND_APPLY_OUT, DevplatformConstants.FUND_APPLY_STATUS_APPLYING);
        double fundApplyAmount = 0L;
        if(!list.isEmpty()){
             fundApplyAmount =  list.stream().collect(Collectors.summingDouble(TlbFundApplyDTO::getAmount));
        }

        if(null == tlbAccount.getFreeMargin()){
            return false;
        }
        if(tlbAccount.getFreeMargin() - fundApplyAmount + amount < 0){
            return false;
        }
        return true;
    }

    @Override
    public HashMap<String, Object> checkWithdrawals(HashMap<String, Object> params) {
        HashMap<String, Object>  retMap = new HashMap<>();
        if(null == params.get("account") || "".equals(params.get("account"))){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入套利宝帐号");
            return retMap;
        }
        String account = params.get("account").toString();
        if(null == findByAccount(account)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入有效套利宝帐号");
            return retMap;
        }
        if(null == params.get("amount") || 0 == Double.valueOf(params.get("amount").toString())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入有效金额");
            return retMap;
        }
        //验证可用金
        double amount = Double.valueOf(params.get("amount").toString());
        if(!checkAccountAmount(account, -amount)){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "除已申请出金后，可用金不够用");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);

        return retMap;
    }

    @Override
    public TlbAccountDTO withdrawals(HashMap<String, Object> params) {
        String account = params.get("account").toString();
        double amount = Double.valueOf(params.get("amount").toString());
        return updateAccountBalance(account, -amount);
    }

    @Override
    public TlbAccountDTO recharge(HashMap<String, Object> params) {
        String account = params.get("account").toString();
        double amount = Double.valueOf(params.get("amount").toString());
        return updateAccountBalance(account, amount);
    }

    @Override
    public TlbAccountDTO findOneByParam(HashMap<String, Object> params) {
        StringBuilder column = new StringBuilder(" select * ");

        StringBuilder cond = new StringBuilder(" from t_bind_bank where 1 = 1 ");

        if(null != params.get("banksn")){
            cond.append(" and c_banksn = '" + params.get("banksn") + "' ");
        }
        if(null != params.get("idcardsn")){
            cond.append(" and c_idcardsn = '" + params.get("idcardsn") + "' ");
        }
        if(null != params.get("mobile")){
            cond.append(" and c_mobile = '" + params.get("mobile") + "' ");
        }

        List<TlbAccountDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), TlbAccountDTO.class);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * delete
     */
    @Override
    public void delete(String id) {
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

    private User saveUser(HashMap<String, Object> params){

        //保存用户信息
        User user = new User();

        String mobile = params.get("mobile").toString();
        String email = params.get("email").toString();
        String parentId = params.get("parentId") != null ? params.get("parentId").toString() : EnvUtil.getAdminUserId();
        String userId = userService.getUserWithAuthorities().getId();

        user.setLogin(email);
        user.setFirstName(params.get("accountName").toString());
        user.setMobile(mobile);
        user.setEmail(email);
        user.setDepartment(EnumRole.USER.value());
        String password = passwordEncoder.encode(DevplatformConstants.USER_DEFAULT_PASSWORD);
        user.setPassword(password);
        user.setPasswordRemark(DevplatformConstants.USER_DEFAULT_PASSWORD);
        user.setActivated(true);
        user.setLangKey("zh-cn");
        user.setCreatedBy(userId);
        user.setCreatedDate(new Date());
        user.setLastModifiedBy(userId);
        user.setParentId(parentId);
        user.setDelFlag("1");
        user = userService.saveDomainUser(user);
        return user;
    }

    @Override
    public String tradeStr(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select a.c_account, sum( a.success_count) as success_count, sum( a.total_lots) as total_lots, sum( a.total_count) as total_count, round(ifnull(sum( a.success_count) / sum( a.total_count), 0) * 100, 2) as success_rate\n" +
                "from \n" +
                "(\n" +
                "\tselect c_account, count(1) as success_count, 0 as total_count, 0 as total_lots\n" +
                "\tfrom  t_tlb_trade a\n" +
                "\twhere a.c_closed = 'Y' and c_gain = 'Y'\n" +
                "\tgroup by c_account\n" +
                "\tunion all\n" +
                "\tselect c_account, 0 as success_count, count(1)  as total_count, sum(a.i_lots)  as total_lots\n" +
                "\tfrom  t_tlb_trade a\n" +
                "\twhere a.c_closed = 'Y' \n" +
                "\tgroup by c_account\n" +
                ") a\n" +
                "group by a.c_account\n");
        return stringBuilder.toString();
    }

    @Override
    public TlbAccountDTO editPassword(HashMap<String, Object> params) {

        String account = (params.get("account") == null ? "" : params.get("account").toString());
        String oldPassword = (params.get("oldPassword") == null ? "" : params.get("oldPassword").toString());
        String newPassword1 = (params.get("newPassword") == null ? "" : params.get("newPassword").toString());

        TlbAccount currentTlbAccount=tlbAccountRepository.findByAccountAndPassword(account,oldPassword);
        if(currentTlbAccount==null) {
            return null;
        }else{
            currentTlbAccount.setMt4Password(newPassword1);
            tlbAccountRepository.save(currentTlbAccount);
            return tlbAccountMapper.toDto(currentTlbAccount);
        }
    }

    @Override
    public TlbAccountDTO findByAccountAndPassword(String account, String password) {
        return tlbAccountMapper.toDto(tlbAccountRepository.findByAccountAndPassword(account,password));
    }
}
