package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.EnvUtil;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.domain.TlbUser;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.TlbUserRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbUserDTO;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.service.mapper.TlbUserMapper;
import org.apache.commons.lang3.StringUtils;
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
public class TlbUserServiceImpl implements TlbUserService {

    private final Logger log = LoggerFactory.getLogger(TlbUserServiceImpl.class);

    private final TlbUserRepository tlbUserRepository;

    private final TlbUserMapper tlbUserMapper;

    @Autowired
    private BankInfoService bankInfoService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private DBService dbService;

    @Autowired
    private UserService userService;

    @Autowired
    private MT4Service mt4Service;

    @Autowired
    private SerialNoService serialNoService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private FileStreamManageService fileStreamManageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private MailService mailService;


    public TlbUserServiceImpl(TlbUserRepository tlbUserRepository, TlbUserMapper tlbUserMapper) {
        this.tlbUserRepository = tlbUserRepository;
        this.tlbUserMapper = tlbUserMapper;
    }


    @Override
    public TlbUserDTO save(TlbUserDTO tlbUserDTO) {
        log.debug("Request to save SysForm : {}", tlbUserDTO);
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

        if(!StringUtils.isEmpty(tlbUserDTO.getCardPositive()) && tlbUserDTO.getCardPositive().startsWith(prefix)) {
            uploadTlbUserImage(decoder, tlbUserDTO.getCardPositive(), imagePath, prefix, newFileName);
            tlbUserDTO.setCardPositive(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(!StringUtils.isEmpty(tlbUserDTO.getIdPositive()) && tlbUserDTO.getIdPositive().startsWith(prefix)) {
            newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            uploadTlbUserImage(decoder, tlbUserDTO.getIdPositive(), imagePath, prefix, newFileName);
            tlbUserDTO.setIdPositive(videoImageWebPath + sdf.format(new Date()) + "/" + newFileName);
        }
        if(!StringUtils.isEmpty(tlbUserDTO.getIdOther()) && tlbUserDTO.getIdOther().startsWith(prefix) ) {
            newFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
            uploadTlbUserImage(decoder, tlbUserDTO.getIdOther(), imagePath, prefix, newFileName);
            tlbUserDTO.setIdOther(videoImageWebPath+ sdf.format(new Date()) + "/"  + newFileName);
        }

        if(StringUtils.isBlank(tlbUserDTO.getId())){
            tlbUserDTO.setCreateAt(new Date());
            tlbUserDTO.setStatus(DevplatformConstants.TLB_USER_STATUS_APPLYING);

            //检查推荐人
            if(null == tlbUserDTO.getRecommendation()){
                tlbUserDTO.setRecommendation(EnvUtil.getAdminUserId());
            }else if (null == userService.findOne(tlbUserDTO.getRecommendation())){
                tlbUserDTO.setRecommendation(EnvUtil.getAdminUserId());
            }
        }

        TlbUser tlbUser = tlbUserMapper.toEntity(tlbUserDTO);
        tlbUser = tlbUserRepository.save(tlbUser);

        return tlbUserMapper.toDto(tlbUser);
    }

    @Override
    public Page<TlbUserDTO> pageList(HashMap<String, Object> params) {
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

        StringBuilder column = new StringBuilder(" select t.*, ifnull(b.first_name, '') as approvedname ");
        column.append(", ifnull(c.first_name, '') as recommendationName");

        StringBuilder cond = new StringBuilder("  from t_tlb_user t left join jhi_user b on b.id = t.c_approved_by ");
        cond.append(" left join jhi_user c on c.id = t.c_recommendation ");
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
            String columnName= "t."+ ReflectUtils.getColumnName(TlbUser.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }
        Page<TlbUserDTO>   page = pageCommon.execPage(column.toString(), cond.toString(), orderBy,page_number, page_size, TlbUserDTO.class);
        return page;
    }

    @Override
    public List<TlbUserDTO> findAll(HashMap<String, Object> params) {

        //List<TlbUser> tlbUsers = tlbUserRepository.findAll();

        //tlbUserRepository.findAll()

        List<TlbUserDTO> list = baseDao.findListBySql("select c_id, c_user_id, c_username, c_phone from t_tlb_user", TlbUserDTO.class);

        /*List<TlbUserDTO> tlbUserDTOS = new ArrayList<>();
        for(TlbUser tlbUser : tlbUsers){
            tlbUserDTOS.add(tlbUserMapper.toDto(tlbUser));
        }*/

        return list;
    }

    @Override
    public List<TlbUserDTO> queryComponentPage(Long formId, String visible) {
        return null;
    }

    @Override
    public TlbUserDTO findOne(String id) {
        TlbUserDTO tlbUserDTO = tlbUserMapper.toDto(tlbUserRepository.findOne(id));
        if(StringUtils.isNotBlank(tlbUserDTO.getRecommendation())){
            UserDTO userDTO = userService.findOne(tlbUserDTO.getRecommendation());
            if(userDTO != null){
               tlbUserDTO.setRecommendationName(userDTO.getFirstName());
            }
        }
        return tlbUserDTO;
    }

    @Override
    public TlbUserDTO findOneByUserId(String userId) {
        return tlbUserMapper.toDto(tlbUserRepository.findTlbUserByUserId(userId));
    }

    @Override
    public void delete(String id) {
        tlbUserRepository.delete(id);
    }

    @Override
    public HashMap<String, Object> approved(String id, HashMap<String, Object> params) {
        TlbUser tlbUser = tlbUserRepository.findOne(id);
        HashMap<String, Object> retMap = new HashMap<>();

        //重要信息检查
        if(DevplatformConstants.TLB_USER_STATUS_PASSED.equals(tlbUser.getStatus())){
            retMap.put("msg", "已经生成帐号，不必再生成");
            return retMap;
        }

        if(null == tlbUser.getPhone() || "".equals(tlbUser.getPhone())){
            retMap.put("msg", "电话号码为空");
            return retMap;
        }

        if(null != userService.findUserByMobile(tlbUser.getPhone())){
            retMap.put("msg", "此电话号码已经注册CRM，请联系客户");
            return retMap;
        }

        if(null == tlbUser.getEmail() || "".equals(tlbUser.getEmail())){
            retMap.put("msg", "邮箱为空");
            return retMap;
        }

        if(null != userService.requestPasswordReset(tlbUser.getEmail())){
            retMap.put("msg", "此邮箱已经注册CRM，请联系客户");
            return retMap;
        }


        User u = userService.getUserWithAuthorities();
        String userId = EnvUtil.getAdminUserId();
        if(u != null){
            userId = u.getId();
        }
        String group = params.get("group") == null ? "demoTX" : params.get("group").toString();
        tlbUser.setAddress(params.get("address") == null ? "no address" : params.get("address").toString());
        tlbUser.setStatus(DevplatformConstants.TLB_USER_STATUS_PASSED);
        tlbUser.setApprovedBy(userId);
        tlbUser.setApprovedAt(new Date());
        tlbUserRepository.save(tlbUser);

        User user = createUser(tlbUser);

        //發送郵件
        mailService.sendCreateUserMail(user);

        tlbUser.setUserId(user.getId());
        tlbUser = tlbUserRepository.save(tlbUser);

        BankInfoDTO bankInfoDTO = createUserBankInfo(tlbUser);

        TlbAccountDTO accountDTO = createAccount(tlbUser, group);
        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public TlbAccountDTO createAccount(String password) {

        String userId = userService.getUserWithAuthorities().getId();
        TlbUser tlbUser = tlbUserMapper.toEntity(findOneByUserId(userId));
        if(null == tlbUser){
            return null;
        }
        return createAccount(tlbUser, "demoTX", password,null);

    }

    private void uploadTlbUserImage(BASE64Decoder decoder, String str, String imagePath, String prefix, String newFileName){
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

    private TlbAccountDTO createAccount(TlbUser tlbUser, String group){
        return createAccount(tlbUser, group, null,null);
    }
    private TlbAccountDTO createAccount(TlbUser tlbUser, String group, String mt4Password, String seePassword){

            TlbAccountDTO tlbAccountDTO = new TlbAccountDTO();

            String accountNo = tlbAccountService.genAccountNo(group);
            tlbAccountDTO.setAccount(accountNo);
            tlbAccountDTO.setAccountName(tlbUser.getUsername());
            tlbAccountDTO.setEmail(tlbUser.getEmail());
            tlbAccountDTO.setGroup(group);
            tlbAccountDTO.setUserId(tlbUser.getUserId());
            tlbAccountDTO.setMt4Password(mt4Password == null ? DevplatformConstants.USER_DEFAULT_PASSWORD : mt4Password);
            tlbAccountDTO.setSeePassword(seePassword==null? DevplatformConstants.TLB_ACCOUNT_SEE_PASSWORD:mt4Password);

            UserDTO userDTO = userService.findOne(tlbUser.getUserId());
            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setLangKey(userDTO.getLangKey());
            user.setPasswordRemark(userDTO.getPasswordRemark());
            user.setFirstName(userDTO.getFirstName());
            user.setLogin(userDTO.getLogin());

            TlbAccountDTO accountDTO = tlbAccountService.save(tlbAccountDTO);

            //發送郵件
            mailService.sendCreateTlbAccountMail(user, tlbAccountDTO);

            //mt4Service.createMT4Acount(accountDTO, tlbUser);
            return accountDTO;
    }

    private User createUser(TlbUser tlbUser){
        User user = new User();
        User u = userService.getUserWithAuthorities();
        String userId = EnvUtil.getAdminUserId();
        if(u != null){
            userId = u.getId();
        }
        //如果没有推荐码，则由管理员继承
        String parentId = tlbUser.getRecommendation() != null ? tlbUser.getRecommendation() : EnvUtil.getAdminUserId();
        //保存用户信息
        user.setLogin(tlbUser.getEmail());
        user.setFirstName(tlbUser.getUsername());
        user.setEmail(tlbUser.getEmail());
        user.setMobile(tlbUser.getPhone());
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

        TlbUserDTO tlbUserDTO = this.findOne(id);
        if(null != tlbUserDTO.getUserId() && StringUtils.isNotEmpty(tlbUserDTO.getUserId())){
            retMap.put("msg", "该资料已经审批通过并生成CRM帐号，不能删除");
        }
        return retMap;
    }

    private BankInfoDTO createUserBankInfo(TlbUser tlbUser){
        BankInfoDTO bankInfoDTO = new BankInfoDTO();
        bankInfoDTO.setbNum(tlbUser.getCardNumber());
        bankInfoDTO.setBank(tlbUser.getOpeningBank());
        bankInfoDTO.setUserId(tlbUser.getUserId());
        bankInfoDTO.setSubBank(tlbUser.getBranch());
        return bankInfoService.save(bankInfoDTO);
    }


}
