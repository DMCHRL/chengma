package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.EnvUtil;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.RegExpValidator;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.*;
import com.chengma.devplatform.repository.HppUserRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.*;
import com.chengma.devplatform.service.mapper.HppUserMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class HppUserServiceImpl implements HppUserService {

    private final Logger log = LoggerFactory.getLogger(HppUserServiceImpl.class);

    private final HppUserRepository hppUserRepository;

    private final HppUserMapper hppUserMapper;

    @Autowired
    private BankInfoService bankInfoService;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Autowired
    private HppSinksService hppSinksService;

    @Autowired
    private HppMobileUserService hppMobileUserService;

    @Autowired
    private HppStrategyDataService hppStrategyDataService;

    @Autowired
    private HppMobileValidateService hppMobileValidateService;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private MailService mailService;


    public HppUserServiceImpl(HppUserRepository hppUserRepository, HppUserMapper hppUserMapper) {
        this.hppUserRepository = hppUserRepository;
        this.hppUserMapper = hppUserMapper;
    }


    @Override
    public HppUserDTO save(HppUserDTO hppUserDTO) {
        log.debug("Request to save SysForm : {}", hppUserDTO);
        HashMap<String,Object> response = new HashMap<>();

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

        if(!StringUtils.isEmpty(hppUserDTO.getCardPositive()) && hppUserDTO.getCardPositive().startsWith(prefix)) {
            uploadHppUserImage(decoder, hppUserDTO.getCardPositive(), imagePath, prefix, newFileName);
            hppUserDTO.setCardPositive(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(!StringUtils.isEmpty(hppUserDTO.getIdPositive()) && hppUserDTO.getIdPositive().startsWith(prefix)) {
            newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            uploadHppUserImage(decoder, hppUserDTO.getIdPositive(), imagePath, prefix, newFileName);
            hppUserDTO.setIdPositive(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(!StringUtils.isEmpty(hppUserDTO.getIdOther()) && hppUserDTO.getIdOther().startsWith(prefix) ) {
            newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            uploadHppUserImage(decoder, hppUserDTO.getIdOther(), imagePath, prefix, newFileName);
            hppUserDTO.setIdOther(videoImageWebPath+ sdf.format(new Date()) + "/"  + newFileName);
        }

        if(StringUtils.isBlank(hppUserDTO.getId())){
            hppUserDTO.setCreateAt(new Date());
        }else{
            HppUser hppUser = hppUserRepository.findOne(hppUserDTO.getId());
            hppUserDTO.setCreateAt(hppUser.getCreateAt());
        }
        hppUserDTO.setStatus(DevplatformConstants.HPP_USER_STATUS_APPLYING);

        User mobileUser = userService.getUserWithAuthorities();
        HppMobileUserDTO hppMobileUserDTO = hppMobileUserService.findByMobile(mobileUser.getMobile());
        if(hppMobileUserDTO != null){
            hppUserDTO.setUserId(hppMobileUserDTO.getId());  //保存开户的手机用户
        }

        //检查推荐人
        if(null == hppUserDTO.getRecommendation()){
            hppUserDTO.setRecommendation(EnvUtil.getAdminUserId());
        }else if ((null == userService.findByCharNo(hppUserDTO.getRecommendation()) && null == hppMobileUserService.findByRecommendation(hppUserDTO.getRecommendation())) ){
            hppUserDTO.setRecommendation(EnvUtil.getAdminUserId());
        }

        //通知开户管理员
        User user = userService.findByDepartment(EnumRole.SERVICE.value());
        if(user != null){
            noticeToCharge(user.getMobile());
        }

        //通知申请人
        if(mobileUser != null){
            notice(mobileUser.getMobile());
        }

        HppUser hppUser = hppUserMapper.toEntity(hppUserDTO);
        hppUser = hppUserRepository.save(hppUser);

        return hppUserMapper.toDto(hppUser);
    }

    @Override
    public Page<HppUserDTO> pageList(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String username = formParams.get("username") == null ? "" : formParams.get("username").toString();
        String phone = formParams.get("phone") == null ? "" : formParams.get("phone").toString();
        String status = formParams.get("status") == null ? "" : formParams.get("status").toString();
        String type = formParams.get("type") == null ? "" : formParams.get("type").toString();

        String approvedname = formParams.get("approvedname") == null ? "" : formParams.get("approvedname").toString();
        String bCreateAt = formParams.get("bCreateAt") == null ? "" : formParams.get("bCreateAt").toString();
        String eCreateAt = formParams.get("eCreateAt") == null ? "" : formParams.get("eCreateAt").toString();
        String bApprovedAt = formParams.get("bApprovedAt") == null ? "" : formParams.get("bApprovedAt").toString();
        String eApprovedAt = formParams.get("eApprovedAt") == null ? "" : formParams.get("eApprovedAt").toString();

        StringBuilder column = new StringBuilder(" select t.*, ifnull(b.first_name, '') as approvedname ,s.c_sinks_name as sinks_name");
        column.append(", ifnull(c.c_phone, '') AS recommendation_name");

        StringBuilder cond = new StringBuilder("  from t_hpp_user t left join jhi_user b on b.id = t.c_approved_by ");
        cond.append(" LEFT JOIN t_hpp_mobile_user c ON c.c_recommendation = t.c_recommendation join t_hpp_sinks s on s.c_id = t.c_sinks_type");
        cond.append(" where 1 = 1 ");

        String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();

        if (StringUtils.isNotBlank(username)) {
            cond.append(" and t.c_username LIKE '%"+username+"%' ");
        }
        if (StringUtils.isNotBlank(phone)) {
            cond.append(" and t.c_phone LIKE '%"+phone+"%' ");
        }
        if (StringUtils.isNotBlank(status)) {
            cond.append(" and t.c_status LIKE '%"+status+"%' ");
        }
        if (StringUtils.isNotBlank(type)) {
            cond.append(" and t.c_type LIKE '%"+type+"%' ");
        }

        if (StringUtils.isNotBlank(approvedname)) {
            cond.append(" and b.first_name like '%" + approvedname + "%' ");
        }
        if (StringUtils.isNotBlank(bCreateAt)) {
            cond.append(" and TO_DAYS(t.d_create_at) >= TO_DAYS('"+bCreateAt+"') ");
        }
        if (StringUtils.isNotBlank(eCreateAt)) {
            cond.append(" and TO_DAYS(t.d_create_at) <= TO_DAYS('"+eCreateAt+"') ");
        }
        if (StringUtils.isNotBlank(bApprovedAt)) {
            cond.append(" and TO_DAYS(t.d_approved_at) >= TO_DAYS('"+bApprovedAt+"') ");
        }
        if (StringUtils.isNotBlank(eApprovedAt)) {
            cond.append(" and TO_DAYS(t.d_approved_at) <= TO_DAYS('"+eApprovedAt+"') ");
        }

        String orderBy="";
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "t."+ ReflectUtils.getColumnName(HppUser.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<HppUserDTO>   page = pageCommon.execPage(column.toString(), cond.toString(), orderBy,page_number, page_size, HppUserDTO.class);
        return page;
    }

    @Override
    public List<HppUserDTO> findAll(HashMap<String, Object> params) {

        //List<HppUser> hppUsers = hppUserRepository.findAll();

        //hppUserRepository.findAll()

        List<HppUserDTO> list = baseDao.findListBySql("select c_id, c_user_id, c_username, c_phone from t_hpp_user", HppUserDTO.class);

        /*List<HppUserDTO> hppUserDTOS = new ArrayList<>();
        for(HppUser hppUser : hppUsers){
            hppUserDTOS.add(hppUserMapper.toDto(hppUser));
        }*/

        return list;
    }

    @Override
    public List<HppUserDTO> queryComponentPage(Long formId, String visible) {
        return null;
    }

    @Override
    public HppUserDTO findOne(String id) {
        HppUserDTO hppUserDTO = hppUserMapper.toDto(hppUserRepository.findOne(id));
        if(StringUtils.isNotBlank(hppUserDTO.getRecommendation())){
            HppMobileUserDTO hppMobileUserDTO = hppMobileUserService.findByRecommendation(hppUserDTO.getRecommendation());
            if(hppMobileUserDTO != null){
               hppUserDTO.setRecommendationName(hppMobileUserDTO.getPhone());
            }
            HppSinksDTO hppSinksDTO =hppSinksService.findOne(hppUserDTO.getSinksType());
            if(hppSinksDTO !=null){
                hppUserDTO.setSinksName(hppSinksDTO.getSinksName());
            }
        }
        return hppUserDTO;
    }

    @Override
    public HppUserDTO findOneByUserId(String userId) {
        return hppUserMapper.toDto(hppUserRepository.findByUserIdEquals(userId));
    }

    @Override
    public void delete(String id) {
        hppUserRepository.delete(id);
    }

    @Override
    public HashMap<String, Object> approved(String id, HashMap<String, Object> params) {
        HppUser hppUser = hppUserRepository.getOne(id);
        HashMap<String, Object> retMap = new HashMap<>();

        //重要信息检查
        /*if(DevplatformConstants.HPP_USER_STATUS_PASSED.equals(hppUser.getStatus())){
            retMap.put("msg", "已经生成帐号，不必再生成");
            return retMap;
        }*/

        if(null == hppUser.getPhone() || "".equals(hppUser.getPhone())){
            retMap.put("msg", "电话号码为空");
            return retMap;
        }

       /* if(null != userService.findUserByMobile(hppUser.getPhone())){
            retMap.put("msg", "此电话号码已经注册CRM，请联系客户");
            return retMap;
        }*/

        if(null == hppUser.getEmail() || "".equals(hppUser.getEmail())){
            retMap.put("msg", "邮箱为空");
            return retMap;
        }

       /* if(null != userService.requestPasswordReset(hppUser.getEmail())){
            retMap.put("msg", "此邮箱已经注册CRM，请联系客户");
            return retMap;
        }*/


        User u = userService.getUserWithAuthorities();
        String userId = EnvUtil.getAdminUserId();
        if(u != null){
            userId = u.getId();
        }
        String group = params.get("group") == null ? "demoHPP" : params.get("group").toString();
        hppUser.setAddress(params.get("address") == null ? "no address" : params.get("address").toString());
        hppUser.setStatus(DevplatformConstants.HPP_USER_STATUS_PASSED);
        hppUser.setApprovedBy(userId);
        hppUser.setApprovedAt(new Date());
        hppUserRepository.save(hppUser);

        /*User user = userService.findUserByMobile(hppUser.getPhone());
        if(user == null){
            user = createUser(hppUser);
        }*/

        //發送郵件
        //mailService.sendCreateUserMail(hppUser.getEmail());

        hppMobileUserService.openFlag(hppUser.getPhone()); //标记为开户用户

        hppUser = hppUserRepository.save(hppUser);
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public TlbAccountDTO createAccount(String password) {

        String userId = userService.getUserWithAuthorities().getId();
        HppUser hppUser = hppUserMapper.toEntity(findOneByUserId(userId));
        if(null == hppUser){
            return null;
        }
        return createAccount(hppUser, "demoTX", password,null);

    }

    private void uploadHppUserImage(BASE64Decoder decoder, String str, String imagePath, String prefix, String newFileName){
        HashMap<String, Object> params = new HashMap<>();
        InputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(decoder.decodeBuffer(str.substring(prefix.length())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        params.put("uploadPath", imagePath);
        params.put("newFileName", newFileName);
        fileStreamManageService.uploadFile(inputStream,params);
    }

    private TlbAccountDTO createAccount(HppUser hppUser, String group){
        return createAccount(hppUser, group, null,null);
    }
    private TlbAccountDTO createAccount(HppUser hppUser, String group, String mt4Password, String seePassword){

            TlbAccountDTO tlbAccountDTO = new TlbAccountDTO();

            String accountNo = tlbAccountService.genAccountNo(group);
            tlbAccountDTO.setAccount(accountNo);
            tlbAccountDTO.setAccountName(hppUser.getUsername());
            tlbAccountDTO.setEmail(hppUser.getEmail());
            tlbAccountDTO.setGroup(group);
            tlbAccountDTO.setMobileNum(hppUser.getPhone());
            tlbAccountDTO.setSinkType(hppUser.getSinksType());  //汇商
            tlbAccountDTO.setMt4Password(mt4Password == null ? DevplatformConstants.USER_DEFAULT_PASSWORD : mt4Password);
            //tlbAccountDTO.setSeePassword(seePassword==null? DevplatformConstants.HPP_ACCOUNT_SEE_PASSWORD:mt4Password);

            /*UserDTO userDTO = userService.findOne(hppUser.getUserId());
            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setLangKey(userDTO.getLangKey());
            user.setPasswordRemark(userDTO.getPasswordRemark());
            user.setFirstName(userDTO.getFirstName());
            user.setLogin(userDTO.getLogin());*/

            HppStrategyDataDTO hppStrategyDataDTO =new HppStrategyDataDTO();
            hppStrategyDataDTO.setAccount(accountNo);
            /**
             * ....
             */
            hppStrategyDataService.save(hppStrategyDataDTO);

            TlbAccountDTO accountDTO = tlbAccountService.save(tlbAccountDTO);

            //發送郵件
            //mailService.sendCreateTlbAccountMail(user, tlbAccountDTO);

            //mt4Service.createMT4Acount(accountDTO, hppUser);
            return accountDTO;
    }

    private User createUser(HppUser hppUser){
        User user = new User();
        User u = userService.getUserWithAuthorities();
        String userId = EnvUtil.getAdminUserId();
        if(u != null){
            userId = u.getId();
        }
        //如果没有推荐码，则由管理员继承
        String parentId = hppUser.getRecommendation() != null ? hppUser.getRecommendation() : EnvUtil.getAdminUserId();
        //保存用户信息
        user.setLogin(hppUser.getEmail());
        user.setFirstName(hppUser.getUsername());
        user.setEmail(hppUser.getEmail());
        user.setMobile(hppUser.getPhone());
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

    public HashMap<String, Object> checkDeleteUser(String id){
        HashMap<String, Object> retMap = new HashMap<>();

        HppUserDTO hppUserDTO = this.findOne(id);
        if(null != hppUserDTO.getUserId() && StringUtils.isNotEmpty(hppUserDTO.getUserId())){
            retMap.put("msg", "该资料已经审批通过并生成CRM帐号，不能删除");
        }
        return retMap;
    }

    private BankInfoDTO createUserBankInfo(HppUser hppUser){
        BankInfoDTO bankInfoDTO = new BankInfoDTO();
        bankInfoDTO.setbNum(hppUser.getCardNumber());
        bankInfoDTO.setBank(hppUser.getOpeningBank());
        bankInfoDTO.setUserId(hppUser.getUserId());
        bankInfoDTO.setSubBank(hppUser.getBranch());
        return bankInfoService.save(bankInfoDTO);
    }

    @Override
    public HashMap<String, Object> checkSaveUser(HppUserDTO hppUserDTO) {
        HashMap<String, Object> retMap = new HashMap<>();

        //重要信息检查
        if(StringUtils.isBlank(hppUserDTO.getUsername())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入用户名");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getPhone())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入手机号");
            return retMap;
        }

        if(StringUtils.isBlank(hppUserDTO.getSinksType())||hppSinksService.findOne(hppUserDTO.getSinksType()) == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择汇商");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getUserType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择用户类型");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getEmail())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入邮箱");
            return retMap;
        }
        if(!RegExpValidator.isEmail(hppUserDTO.getEmail())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入正确的邮箱");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getIdNumber())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入身份证");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getIdPositive())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传身份证正面");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getIdOther())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传身份证背面");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getCardNumber())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入银行卡号");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getOpeningBank())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入开户行");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getBranch())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入开户支行");
            return retMap;
        }
        if(StringUtils.isBlank(hppUserDTO.getCardPositive())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请上传银行正面");
            return retMap;
        }

        if(StringUtils.isBlank(hppUserDTO.getSecondType())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择二次身份证明类型");
            return retMap;
        }

        if(StringUtils.isBlank(hppUserDTO.getSecondPositive())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请选择二次身份证明图");
            return retMap;
        }
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public List<HppUserDTO> findByMobile(String mobile) {
       return hppUserMapper.toDto(hppUserRepository.findByPhoneEqualsAndStatusEquals(mobile,"PASSED"));
    }

    /**
     * 通知管理员
     * @param mobile
     */
    public void noticeToCharge(String mobile){
        HashMap<String,Object> param = new HashMap<>();
        param.put("mobileNum",mobile);
        param.put("sendType","task");
        //param.put("content",hppStrategyOrder.getAccount()+","+hppStrategyDTO.getStrategyName());
        hppMobileValidateService.notice(param);
    }

    /**
     * 通知用户
     * @param mobile
     */
    public void notice(String mobile){
        HashMap<String,Object> param = new HashMap<>();
        param.put("mobileNum",mobile);
        param.put("sendType","applyUser");
        //param.put("content",hppStrategyOrder.getAccount()+","+hppStrategyDTO.getStrategyName());
        hppMobileValidateService.notice(param);
    }
}
