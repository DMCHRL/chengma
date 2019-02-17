package com.chengma.devplatform.service;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.constant.EnumGroup;
import com.chengma.devplatform.common.constant.EnumRole;
import com.chengma.devplatform.common.constant.EnumRoleName;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.RandomUtil;
import com.chengma.devplatform.common.util.ReflectUtils;
import com.chengma.devplatform.common.util.SqlUtil;
import com.chengma.devplatform.config.Constants;
import com.chengma.devplatform.domain.Authority;
import com.chengma.devplatform.domain.HppStrategyTrade;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.AuthorityRepository;
import com.chengma.devplatform.repository.UserRepository;
import com.chengma.devplatform.security.AuthoritiesConstants;
import com.chengma.devplatform.security.SecurityUtils;
import com.chengma.devplatform.service.dto.HppStrategyTradeDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.service.impl.DBService;
import com.chengma.devplatform.service.impl.SerialNoService;
import com.chengma.devplatform.service.mapper.UserMapper;
import com.chengma.devplatform.web.rest.vm.LoginVM;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Service class for managing users.
 *
 * @author administrator
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private DBService dbService;
    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private MailService mailService;

    @Autowired
    private SerialNoService serialNoService;

    @Autowired
    private HppMobileUserService hppMobileUserService;

    @Autowired
    private BaseDao baseDao;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public User activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);

        User user = userRepository.findOneByActivationKey(key);
        user.setActivated(true);
        user.setActivationKey(null);
        log.debug("Activated user: {}", user);
        return user;
    }

    public User completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        User user = userRepository.findOneByResetKey(key);
        if (user.getResetDate().after(new Date(System.currentTimeMillis() - 86400000))) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetKey(null);
            user.setResetDate(null);
            return user;
        }
        return null;
    }

    public User requestPasswordReset(String mail) {
        User user = userRepository.findOneByEmail(mail);
        if (null != user && user.getActivated()) {
            user.setResetKey(RandomUtil.generateResetKey());
            user.setResetDate(new Date());
            return user;
        }
        return null;
    }

    public User createUser(String login, String password, String firstName, String lastName, String email,
                           String imageUrl, String langKey) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setImageUrl(imageUrl);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser.setPasswordRemark(password);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(UserDTO userDTO) {

        Set<String> dtoAuthorities = new HashSet<>();
        dtoAuthorities.add("ROLE_USER");
        userDTO.setAuthorities(dtoAuthorities);

        User user = userMapper.userDTOToUser(userDTO);

        if (userDTO.getLangKey() == null) {
            user.setLangKey("zh-cn"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            Iterator<String> it = userDTO.getAuthorities().iterator();
            while (it.hasNext()) {
                String str = it.next();
                Authority auth = new Authority();
                auth.setName(str);
                authorities.add(auth);
            }
            user.setAuthorities(authorities);
        }
        user.setResetDate(new Date());

        String encryptedPassword = null;
        if (userDTO.getId() != null) { //更新
            User passwordUser = userRepository.findOne(userDTO.getId());
            encryptedPassword = passwordUser.getPassword();
        } else {
            user.setDelFlag("1");
            encryptedPassword = passwordEncoder.encode(DevplatformConstants.USER_DEFAULT_PASSWORD);
            user.setPasswordRemark(DevplatformConstants.USER_DEFAULT_PASSWORD);
        }
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setActivated(true);

        if (user.getActivated()) {
            user.setErrorCount(0L);//只要是启用状态就把错误次数清0
        }

        mailService.sendCreateUserMail(user);

        User u = getUserWithAuthorities(); //当前用户

        //注册
        if(u==null||!(u.getDepartment().equals(EnumRole.ADMIN.value())||u.getDepartment().equals(EnumRole.SERVICE))){
            if(user.getDepartment() == null){
                user.setDepartment(EnumRole.USER.value());
            }
        }else{
            //admin或service新增或修改的
            if (userDTO.getId() != null) {
                sysOperateLogService.addLog(u.getLogin(), u.getFirstName(), "2", new Date(), "修改用户：" + userDTO.getLogin());
            } else {
                sysOperateLogService.addLog(u.getLogin(), u.getFirstName(), "2", new Date(), "新增用户：" + userDTO.getLogin());
            }
        }

        userRepository.save(user);

        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName 名
     * @param lastName  姓
     * @param email     邮箱
     * @param langKey   密钥
     * @param imageUrl  照片url
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (null != user) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            save(userMapper.userToUserDTO(user));
            log.debug("Changed Information for User: {}", user);
        }
    }

    public void changePassword(String password) {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
        if (null != user) {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            save(userMapper.userToUserDTO(user));
            log.debug("Changed password for User: {}", user);
        }
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(String id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin());
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        List<Authority> list = authorityRepository.findAll();
        List<String> list2 = new ArrayList<>();
        for (Authority auth : list) {
            list2.add(auth.getName());
        }
        return list2;
    }

    /**
     * post /user/save ：保存用户信息
     *
     * @param userDTO 用户信息模型
     * @return ResponseResult
     */
    public ResponseResult save(UserDTO userDTO) {
        ResponseResult response = new ResponseResult();

        String login = userDTO.getLogin();
        String firstName = userDTO.getFirstName();
        String moblie = userDTO.getMobile();
        Date expireDate = userDTO.getExpireDate();
        Date passwordExpireDate = userDTO.getPasswordExpireDate();
        if (null == login || "".equals(login)) {
            response.setMsgCode("邮箱不能为空或邮箱已存在");
            return response;
        } else if (null == moblie || "".equals(moblie)) {
            response.setMsgCode("手机号不能为空或手机号已存在");
            return response;
        } else if (null == firstName || "".equals(firstName)) {
            response.setMsgCode(DevplatformConstants.USER_INVALID_FIRSTNAME);
            return response;
        }

        User u = duplicateLogin(userDTO.getLogin(), userDTO.getEmail());
        if (null != u && !u.getId().equals(userDTO.getId())) {
            if (u.getLogin().equals(userDTO.getLogin())) {
                response.setMsgCode("邮箱不能为空或邮箱已存在");
            } else {
                response.setMsgCode("邮箱不能为空或邮箱已存在");
            }
            return response;
        }

        if(null == userDTO.getParentId()){
            userDTO.setParentId(getUserWithAuthorities().getId());
        }

        User uMobile = findUserByMobileAndDepartment(userDTO.getMobile(),userDTO.getDepartment()); //带上身份
        if (null != uMobile && !uMobile.getId().equals(userDTO.getId())) {
            if (uMobile.getMobile().equals(userDTO.getMobile())) {
                response.setMsgCode("手机号已存在");
            }
            return response;
        }

        try {
            String recommendationNo = serialNoService.getAccountNo(EnumGroup.valueOf("recommendation").value());
            while(hppMobileUserService.findByRecommendation(recommendationNo) != null && this.findByCharNo(recommendationNo) != null){
                recommendationNo = serialNoService.getAccountNo(EnumGroup.valueOf("recommendation").value());
            }
            userDTO.setCharNo(recommendationNo);
            userDTO.setLastName(StringUtils.isEmpty(userDTO.getDepartment()) ? EnumRoleName.user.value() : EnumRoleName.valueOf(userDTO.getDepartment()).value());
            createUser(userDTO);
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        } catch (Exception e) {
            response.setMsgCode(DevplatformConstants.ERROR_ERRORMSG);
        }
        return response;
    }

    /**
     * 获取用户page。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{login:"",firstName:"",activated:""}}
     * @return HashMap
     */
    @Transactional(readOnly = true)
    public HashMap<String, Object> findAll(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");
        String login = formParams.get("login") == null ? "" : formParams.get("login").toString();
        String firstName = formParams.get("firstName") == null ? "" : formParams.get("firstName").toString();
        String activatedStr = formParams.get("activated") == null ? "" : formParams.get("activated").toString();
        String department = formParams.get("department") == null ? "" : formParams.get("department").toString();
        String mobile = formParams.get("mobile") == null ? "" : formParams.get("mobile").toString();

        String isProxy = formParams.get("isProxy") == null ? "" : formParams.get("isProxy").toString();

       /* String orderByColumn = formParams.get("orderByColumn") == null ? "" : formParams.get("orderByColumn").toString();
        String sort = formParams.get("sort") == null ? "" : formParams.get("sort").toString();*/

        StringBuilder column = new StringBuilder(" select \tu.*,\n" +
                "\tcount(u2.c_recommendation) as recommendation_total ");

        StringBuilder cond = new StringBuilder(" from jhi_user u  LEFT JOIN t_hpp_user u2 ON u.c_char_no\t= u2.c_recommendation where  c_del_flag='1'");

        //检查当前登录用户
        User user = getUserWithAuthorities();
        /*if(EnumRole.COMPANY.value().equals(user.getDepartment())){
            cond.append(" and u.c_parent_id = '" + user.getId() + "' and u.department = '" + EnumRole.PROXY.value() +"'");
        }else if(EnumRole.PROXY.value().equals(user.getIsProxy())){
            cond.append(" and 1 != 1 ");
        }*/
      /*  String orderBy="";
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderByColumn)) {
            String columnName= "u."+ReflectUtils.getColumnName(User.class,orderByColumn);
            orderBy = SqlUtil.orderBySql(columnName,sort);
        }*/

        cond.append(" GROUP BY u.id");

        Page<UserDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, UserDTO.class);

        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("data", page.getContent());
        response.put("total", page.getTotalElements());
        response.put("page_number", page_number);
        response.put("page_size", page_size);

        return response;
    }

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return UserDTO
     */
    @Transactional(readOnly = true)
    public UserDTO findOne(String id) {
        User user = userRepository.findOne(id);
        UserDTO dto = userMapper.userToUserDTO(user);
        return dto;
    }

    /**
     * 删除用户记录（可批量删除）
     *
     * @param ids 用户id，多个id之间用“,”隔开。
     * @return HashMap
     */
    public HashMap<String, Object> deleteUser(String ids) {
        ids = ids.replace("'", "");//去掉单引号
        HashMap<String, Object> response = new HashMap<>();
        StringBuilder column = new StringBuilder(" update jhi_user set c_del_flag = '0', activated = 0 ");
        column.append(" where " + dbService.inSql("id", ids) + " ");
        jdbcTemplate.execute(column.toString());

        User user = getUserWithAuthorities();
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            String id = idArray[i];
            UserDTO userDTO = findOne(id);
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "删除用户：" + userDTO.getLogin());
        }
        response.put("statusCode", HttpStatus.OK);
        return response;
    }

    /**
     * 重置用户密码
     *
     * @param id 用户id
     * @return HashMap
     */
    public HashMap<String, Object> resetPassword(String id) {
        User user = userRepository.findOne(id);
        user.setPassword(passwordEncoder.encode(DevplatformConstants.USER_DEFAULT_PASSWORD));
        userRepository.save(user);

        User u = getUserWithAuthorities();//当前用户
        sysOperateLogService.addLog(u.getLogin(), u.getFirstName(), "2", new Date(), "重置用户：" + user.getLogin() + " 的密码");
        HashMap<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.OK);
        return response;
    }

    /**
     * 修改用户密码
     *
     * @param params 参数：{login:"",oldPassword:"",newPassword1:""}
     * @return ResponseResult
     */
    public ResponseResult editPassword(HashMap<String, Object> params) {
        String login = (params.get("login") == null ? "" : params.get("login").toString());
        String oldPassword = (params.get("oldPassword") == null ? "" : params.get("oldPassword").toString());
        String newPassword1 = (params.get("newPassword") == null ? "" : params.get("newPassword").toString());

        ResponseResult response = new ResponseResult();

        try {
            //暂时没弄懂这两行的逻辑
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, oldPassword);
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            User u = getUserWithAuthoritiesByLogin(login);
            if (null != u) {
                u.setPassword(passwordEncoder.encode(newPassword1));
                u.setPasswordRemark(newPassword1);
                userRepository.save(u);
                User currentUser = getUserWithAuthorities();//当前用户
                sysOperateLogService.addLog(currentUser.getLogin(), currentUser.getFirstName(), "2", new Date(), "修改用户：" + u.getLogin() + " 的密码");
                response.setStatusCode(ResponseResult.SUCCESS_CODE);
            } else {
                response.setMsgCode(DevplatformConstants.ERROR_ERRORMSG);
            }
        } catch (AuthenticationException ae) {
            response.setMsgCode(DevplatformConstants.USER_ERROR_OLD_PASSWORD);
            return response;
        }
        return response;
    }

    /**
     * 登录信息验证
     *
     * @param loginVM 登陆信息
     * @return ResponseResult
     */
    public ResponseResult loginVerification(LoginVM loginVM) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setStatusCode(ResponseResult.SUCCESS_CODE);

        User u = getUserWithAuthoritiesByLogin(loginVM.getUsername());
        if (null != u) {
            if (!u.getActivated()) {
                responseResult.setMsgCode(DevplatformConstants.LOGIN_DISABLE);
                responseResult.setStatusCode(ResponseResult.FAIL_CODE);
            } else if (null != u.getExpireDate() && new Date().after(u.getExpireDate())) {
                responseResult.setMsgCode(DevplatformConstants.EXPIRE_LOGIN);
                responseResult.setStatusCode(ResponseResult.FAIL_CODE);
            } else if (null != u.getPasswordExpireDate() && new Date().after(u.getPasswordExpireDate())) {
                responseResult.setMsgCode(DevplatformConstants.EXPIRE_PASSWORD);
                responseResult.setStatusCode(ResponseResult.FAIL_CODE);
            } else {
                String interPassword = BCrypt.hashpw(loginVM.getPassword(), u.getPassword().substring(0, 29));
                if (u.getPassword().equals(interPassword)) {
                    u.setErrorCount(0L);
                    userRepository.save(u);//可以认为登陆成功，错误次数清0
                } else {
                    responseResult.setMsgCode(DevplatformConstants.ERROR_PASSWORD);
                    responseResult.setStatusCode(ResponseResult.FAIL_CODE);
                    if (null == u.getErrorCount()) {
                        u.setErrorCount(1L);
                    } else {
                        u.setErrorCount(u.getErrorCount() + 1);
                    }
                    responseResult.setData(u.getErrorCount());//传回错误次数
                    if (u.getErrorCount() >= 5) {
                        u.setActivated(false);
                        sysOperateLogService.addLog(u.getLogin(), u.getFirstName(), "2", new Date(), "用户：" + u.getLogin() + " 因为输入错误密码达到5次被禁用");
                    }
                    userRepository.save(u);
                    return responseResult;
                }
            }
        } else {
            responseResult.setMsgCode(DevplatformConstants.INVALID_LOGIN);
            responseResult.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return responseResult;
    }

    /**
     * 校验用户是否过期，并把用户状态置为不可用
     */
    public void checkUserExpire() {
        StringBuilder column = new StringBuilder(" update jhi_user set activated = 0 ");
        column.append(" where d_expire_date < " + dbService.sysdate() + " or d_password_expire_date < " + dbService.sysdate() + " ");
        jdbcTemplate.execute(column.toString());
    }

    /**
     * 启用、禁用用户
     *
     * @param params 参数：{userId:"",activated:""}
     * @return HashMap
     */
    public HashMap<String, Object> enableSysUser(HashMap<String, Object> params) {
        String userId = params.get("userId") == null ? "" : params.get("userId").toString();
        String activated = params.get("activated") == null ? "" : params.get("activated").toString();
        StringBuilder column = new StringBuilder(" update jhi_user set activated = " + activated + " ");
        if ("1".equals(activated)) {
            column.append(" ,i_error_count = 0 ");
        }
        column.append(" where id = '" + userId + "' ");
        jdbcTemplate.execute(column.toString());

        User user = getUserWithAuthorities();

        UserDTO userDTO = findOne(userId);
        if ("1".equals(activated)) {
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "启用用户：" + userDTO.getLogin());
        } else {
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "禁用用户：" + userDTO.getLogin());
        }
        return null;
    }

    /**
     * 把用户信息中的lastName进行base64编码
     *
     * @param userDTO 用户信息
     * @return UserDTO
     */
    public UserDTO encodeBase64(UserDTO userDTO) {
        try {
            String lastName = userDTO.getLastName() == null ? "" : userDTO.getLastName();
            lastName = org.apache.commons.codec.binary.Base64.encodeBase64String(lastName.getBytes("utf-8"));
            userDTO.setLastName(lastName);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userDTO;
    }

    /**
     * 把用户信息中的lastName进行base64解码
     *
     * @param user 用户信息
     * @return User
     */
    public User decodeBase64(User user) {
        try {
            String lastName = user.getLastName() == null ? "" : user.getLastName();
            lastName = new String(Base64.decodeBase64(lastName.getBytes()), "utf-8");
            user.setLastName(lastName);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 查找重复的login
     *
     * @param login 账号
     * @param email 邮箱
     * @return User
     */
    @Transactional(readOnly = true)
    public User duplicateLogin(String login, String email) {
        List<User> list = userRepository.duplicateLogin(login, email);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查找重复的mobile
     *
     * @param mobile 手机
     * @return User
     */
    @Transactional(readOnly = true)
    public User findUserByMobile(String mobile) {
        User user = userRepository.findOneByMobile(mobile);
        return user;
    }

    /**
     *  根据department 查找重复的mobile
     * @param mobile
     * @param department
     * @return
     */
    @Transactional(readOnly = true)
    public User findUserByMobileAndDepartment(String mobile , String department) {
        String sql = "select * from jhi_user where c_mobile ='"+mobile+"'";
        if(department.equals("mobile")){
            sql += " and department = 'mobile'";
        }else{
            sql += " and department != 'mobile'";
        }
        List<User> list = baseDao.findListBySql(sql,User.class);
        if(list != null && list.size() >0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Transactional(readOnly = true)
    public HashMap<String, Object> userRegister(Map<String, Object> params) {
        User user = new User();
        ResponseResult response = new ResponseResult();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        String email = params.get("email") == null ? "" : params.get("email").toString();
        String firstName = params.get("firstName") == null ? "" : params.get("firstName").toString();
        String storeName = params.get("storeName") == null ? "" : params.get("storeName").toString();
        String validateCode = params.get("validateCode") == null ? "" : params.get("validateCode").toString();
        //String openId = params.get("openId") == null ? "" : params.get("openId").toString();

        HashMap<String, Object> retMap = new HashMap<>();

        User checkUser = this.findUserByMobile(mobile);
        if (checkUser != null) {
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msgCode", DevplatformConstants.USER_DUPLICATE_MOBILE);
            response.setStatusCode(ResponseResult.FAIL_CODE);
            response.setMsgCode(DevplatformConstants.USER_DUPLICATE_MOBILE);
            return retMap;
        }
        /*checkUser = this.checkOpenId(openId);
        if (checkUser != null) {
            response.setStatusCode(ResponseResult.FAIL_CODE);
            response.setMsgCode(KayKnowConstants.USER_REGISTER_WEIXIN_EXIT);
            return response;
        }*/

       /* HashMap<String, Object> mobileMap = new HashMap<String, Object>();
        mobileMap.put("mobileNum", mobile);
        mobileMap.put("validateCode", validateCode);
        ResponseResult validateResult = mobileValidateService.verification(mobileMap);
        String code = "0000";
        if (!code.equals(validateResult.getStatusCode())) {
            response.setStatusCode(ResponseResult.FAIL_CODE);
            response.setMsgCode(KayKnowConstants.USER_REGISTER_VALIDATE_CODE_ERROR);
            return response;
        }*/

        //保存用户信息
        user.setLogin(email);
        user.setFirstName(firstName);
        user.setMobile(mobile);
        //user.setOpenId(openId);
        String password = passwordEncoder.encode(DevplatformConstants.USER_DEFAULT_PASSWORD);
        user.setPassword(password);
        user.setEmail(email);
        user.setActivated(true);
        user.setLangKey("zh-cn");
        user.setCreatedBy("mobile");
        user.setCreatedDate(new Date());
        user.setLastModifiedBy("mobile");
        user.setDelFlag("1");
        user.setPasswordRemark(DevplatformConstants.USER_DEFAULT_PASSWORD);
        userRepository.save(user);
        //保存角色关系
       /* SysRole role = querySysRoleByNo(AuthoritiesConstants.ROLE_NO_STORE_ADMIN);
        HashMap<String, Object> userRoleMap = new HashMap<String, Object>();
        List<String> roleIds = new ArrayList<String>();
        userRoleMap.put("userId", user.getId());
        roleIds.add(role.getId());
        userRoleMap.put("roleIds", roleIds);
        sysUserRoleService.saveUserRole(userRoleMap);*/
        //保存店铺信息
        //this.createStore(user, storeName);

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    public List<TlbAccountDTO> loadMyAccount(){
        String userId = getUserWithAuthorities().getId();
        StringBuilder stringBuilder = new StringBuilder("select * from t_tlb_account where c_user_id = '" + userId + "'");
        List<TlbAccountDTO> list = baseDao.findListBySql(stringBuilder.toString(), TlbAccountDTO.class);
        return list;
    }

    public String accountTableStr(){
        StringBuilder stringBuilder = new StringBuilder("select a.*, b.id top_id\n" +
                ", b.first_name parent_name, b.id parent_id\n" +
                "from t_tlb_account a \n" +
                "join jhi_user b on a.c_user_id = b.id  \n" +
                "join jhi_user as c on b.c_parent_id = c.id \n" +
                "where ifnull(b.c_parent_id,'') != '' \n" +
                "union all \n" +
                "select a.*, b.c_parent_id top_id \n" +
                ", c.first_name parent_name, c.id parent_id\n" +
                "from t_tlb_account a \n" +
                "join jhi_user b on a.c_user_id = b.id  \n" +
                "join jhi_user as c on b.c_parent_id = c.id \n" +
                "where ifnull(b.c_parent_id,'') != '' \n" +
                "union all \n" +
                "select a.*, c.c_parent_id top_id \n" +
                ", c.first_name parent_name, c.id parent_id\n" +
                "from t_tlb_account a \n" +
                "join jhi_user b on a.c_user_id = b.id \n" +
                "join jhi_user as c on b.c_parent_id = c.id \n" +
                "where ifnull(c.c_parent_id,'') != ''");
        return stringBuilder.toString();
    }

    /**
     * 整合后的table b , 字段有user_id, count
     * @return
     */
    public String accountTableCountStr(){
        StringBuilder stringBuilder = new StringBuilder("select top_id, b.parent_name, count(1) account_count\n" +
                "from ");
        stringBuilder.append("(\n");
        stringBuilder.append(accountTableStr());
        stringBuilder.append(") b\n");
        stringBuilder.append("group by b.top_id");
        return stringBuilder.toString();
    }

    private String proxyTableCount(){
        StringBuilder stringBuilder = new StringBuilder("select c_parent_id as user_id, count(1) as proxy_count\n" +
                "\t\t\t\t\t from jhi_user \n" +
                "\t\t\t\t\t where department = 'proxy' \n" +
                "\t\t\t\t\t group by c_parent_id");
        return stringBuilder.toString();
    }

    @Transactional(readOnly = true)
    public HashMap<String, Object> findAllAccount(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String account = formParams.get("account") == null ? "" : formParams.get("account").toString();
        String accountName = formParams.get("accountName") == null ? "" : formParams.get("accountName").toString();
        String parentId = formParams.get("parentId") == null ? "" : formParams.get("parentId").toString();
        String group = formParams.get("group") == null ? "" : formParams.get("group").toString();

        User user = getUserWithAuthorities();

        StringBuilder column = new StringBuilder("select a.* ");
        column.append(", ifnull(c.success_count, 0) success_count, ifnull(c.success_rate, 0) success_rate, ifnull(c.total_count, 0) total_count");
        column.append(", ifnull(c.total_lots, 0) total_lots");
        StringBuilder cond = new StringBuilder();
        cond.append(" from (" + accountTableStr() + ") a ");
        cond.append(" left join (" + tlbAccountService.tradeStr() + ") c on a.c_account = c.c_account ");
        cond.append(" where 1 = 1");
        cond.append(" and a.top_id = '" + user.getId() + "'");
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
            cond.append(" and a.parent_id = '" + parentId + "' ");
        }

        String orderBy = " order by c_account asc";
        Page<TlbAccountDTO> tlbAccountDTOPage = pageCommon.execPage(column.toString(), cond.toString(),orderBy, page_number, page_size, TlbAccountDTO.class);

        HashMap retMap = new HashMap();
        retMap.put("list", tlbAccountDTOPage.getContent());
        retMap.put("total", tlbAccountDTOPage.getTotalElements());
        return retMap;
    }

    public User saveDomainUser(User user){
        return userRepository.save(user);
    }

    public User findByCharNo(String charNo){
        return userRepository.findByCharNoEquals(charNo);
    }

    public User findByDepartment(String department){
        String sql =  "select  * from jhi_user where department='"+department+"' and activated=1 and c_del_flag ='1'";
        List<User> list = baseDao.findListBySql(sql,User.class);
        if(list != null && list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

}

