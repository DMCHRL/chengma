package com.suitong.devplatform.service;

import com.suitong.devplatform.common.constant.DevplatformConstants;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.common.util.RandomUtil;
import com.suitong.devplatform.config.Constants;
import com.suitong.devplatform.domain.Authority;
import com.suitong.devplatform.domain.User;
import com.suitong.devplatform.repository.AuthorityRepository;
import com.suitong.devplatform.repository.UserRepository;
import com.suitong.devplatform.security.AuthoritiesConstants;
import com.suitong.devplatform.security.SecurityUtils;
import com.suitong.devplatform.service.dto.UserDTO;
import com.suitong.devplatform.service.impl.DBService;
import com.suitong.devplatform.service.mapper.UserMapper;
import com.suitong.devplatform.web.rest.vm.LoginVM;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().after(new Date(System.currentTimeMillis() - 86400000)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
                .filter(User::getActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(new Date());
                    return user;
                });
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
            userDTO.getAuthorities().forEach(
                    authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        user.setResetDate(new Date());

        String encryptedPassword = null;
        if (userDTO.getId() != null) { //更新
            User passwordUser = userRepository.findOne(userDTO.getId());
            encryptedPassword = passwordUser.getPassword();
        } else {
            user.setDelFlag("1");
            encryptedPassword = passwordEncoder.encode(Constants.DEFAULT_PASSWORD);
        }
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());

        if (user.getActivated()) {
            user.setErrorCount(0L);//只要是启用状态就把错误次数清0
        }

        userRepository.save(user);

        User u = getUserWithAuthorities(); //当前用户
        if (userDTO.getId() != null) {
            sysOperateLogService.addLog(u.getLogin(), u.getFirstName(), "2", new Date(), "修改用户：" + userDTO.getLogin());
        } else {
            sysOperateLogService.addLog(u.getLogin(), u.getFirstName(), "2", new Date(), "新增用户：" + userDTO.getLogin());
        }
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            log.debug("Changed Information for User: {}", user);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findOne(userDTO.getId()))
                .map(user -> {
                    user.setLogin(userDTO.getLogin());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(authorityRepository::findOne)
                            .forEach(managedAuthorities::add);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }


    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    /**
     * Save a user.
     */
    public ResponseResult save(UserDTO userDTO) {
        ResponseResult response = new ResponseResult();

        String login = userDTO.getLogin();
        String firstName = userDTO.getFirstName();
        Date expireDate = userDTO.getExpireDate();
        Date passwordExpireDate = userDTO.getPasswordExpireDate();
        if (null == login || "".equals(login)) {
            response.setMsgCode(DevplatformConstants.USER_INVALID_LOGIN);
            return response;
        } else if (null == firstName || "".equals(firstName)) {
            response.setMsgCode(DevplatformConstants.USER_INVALID_FIRSTNAME);
            return response;
        } else if (null == expireDate) {
            response.setMsgCode(DevplatformConstants.USER_INVALID_EXPIRE_DATE);
            return response;
        } else if (null == passwordExpireDate) {
            response.setMsgCode(DevplatformConstants.USER_INVALID_PASSWORD_EXPIRE);
            return response;
        }

        User u = duplicateLogin(userDTO.getLogin(), userDTO.getEmail());
        if (null != u && !u.getId().equals(userDTO.getId())) {
            if (u.getLogin().equals(userDTO.getLogin())) {
                response.setMsgCode(DevplatformConstants.USER_DUPLICATE_LOGIN);
            } else {
                response.setMsgCode(DevplatformConstants.USER_DUPLICATE_EMAIL);
            }
            return response;
        }

        try {
            createUser(userDTO);
            response.setStatusCode(ResponseResult.SUCCESS_CODE);
        } catch (Exception e) {
            response.setMsgCode(DevplatformConstants.ERROR_ERRORMSG);
        }
        return response;
    }

    /**
     * findAll
     */
    @Transactional(readOnly = true)
    public HashMap<String, Object> findAll(HashMap<String, Object> params) {
        int page_number = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int page_size = (params.get("page_size") == null ? 2 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String login = formParams.get("login") == null ? "" : formParams.get("login").toString();
        String firstName = formParams.get("firstName") == null ? "" : formParams.get("firstName").toString();
        String activatedStr = formParams.get("activated") == null ? "" : formParams.get("activated").toString();

        StringBuilder column = new StringBuilder(" select id ");
        column.append(" ,login,password_hash,first_name,last_name ");
        column.append(" ,email,image_url,activated,lang_key ");
        column.append(" ,activation_key,reset_key,created_by,created_date ");
        column.append(" ,reset_date,last_modified_by,last_modified_date ");

        column.append(" ,c_char_no char_no,c_sex sex,c_id_no id_no ");
        column.append(" ,i_dept_id dept_id,c_tel tel,c_mobile mobile ");
        column.append(" ,d_onboard_date onboard_date,d_expire_date expire_date,c_status status ");
        column.append(" ,c_del_flag del_flag,d_password_expire_date password_expire_date ");
        column.append(" ,case activated when '1' then '" + Constants.STATUS_ENABLE + "' else '" + Constants.STATUS_DISABLE + "' end activated_show ");
        column.append(" ,case when d_expire_date " + dbService.lessTime() + " or d_password_expire_date " + dbService.lessTime() + " then '1' else '0' end is_expire ");

        StringBuilder cond = new StringBuilder(" from jhi_user where 1 = 1 and c_del_flag = '1' ");

        if (StringUtils.isNotBlank(login)) {
            cond.append(" and login like '%" + login + "%'");
        }
        if (StringUtils.isNotBlank(firstName)) {
            cond.append(" and first_name like '%" + firstName + "%'");
        }
        if (StringUtils.isNotBlank(activatedStr)) {
            cond.append(" and activated = '" + activatedStr + "'");
        }

        Page<UserDTO> page = pageCommon.execPage(column.toString(), cond.toString(), page_number, page_size, UserDTO.class);

        HashMap<String, Object> response = new HashMap<>();
        response.put("page", page);
        response.put("data", page.getContent());
        response.put("page_number", page_number);
        response.put("page_size", page_size);

        return response;
    }

    /**
     * Get one user by id.
     */
    @Transactional(readOnly = true)
    public UserDTO findOne(Long id) {
        User user = userRepository.findOne(id);
        UserDTO dto = userMapper.userToUserDTO(user);
        return dto;
    }

    /**
     * deleteUser
     */
    public HashMap<String, Object> deleteUser(String ids) {
        HashMap<String, Object> response = new HashMap<>();
        StringBuilder column = new StringBuilder(" update jhi_user set c_del_flag = '0', activated = 0 ");
        column.append(" where id in (" + ids + ") ");
        jdbcTemplate.execute(column.toString());

        User user = getUserWithAuthorities();
        ids = ids.replace("'", "");//去掉单引号
        String[] idArray = ids.split(",");
        for (int i = 0; i < idArray.length; i++) {
            Long id = Long.valueOf(idArray[i]);
            UserDTO userDTO = findOne(id);
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "删除用户：" + userDTO.getLogin());
        }
        response.put("statusCode", HttpStatus.OK);
        return response;
    }

    /**
     * resetPassword
     */
    public HashMap<String, Object> resetPassword(Long id) {
        User user = userRepository.findOne(id);
        user.setPassword(passwordEncoder.encode(Constants.DEFAULT_PASSWORD));
        userRepository.save(user);

        User u = getUserWithAuthorities();//当前用户
        sysOperateLogService.addLog(u.getLogin(), u.getFirstName(), "2", new Date(), "重置用户：" + user.getLogin() + " 的密码");
        HashMap<String, Object> response = new HashMap<>();
        response.put("statusCode", HttpStatus.OK);
        return response;
    }

    /**
     * editPassword
     */
    public ResponseResult editPassword(HashMap<String, Object> params) {
        String login = (params.get("login") == null ? "" : params.get("login").toString());
        String oldPassword = (params.get("oldPassword") == null ? "" : params.get("oldPassword").toString());
        String newPassword1 = (params.get("newPassword1") == null ? "" : params.get("newPassword1").toString());
//        String newPassword2 = (params.get("newPassword2") == null ? "" : params.get("newPassword2").toString());

        ResponseResult response = new ResponseResult();

        try {
            //暂时没弄懂这两行的逻辑
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, oldPassword);
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

            Optional<User> op = getUserWithAuthoritiesByLogin(login);
            boolean b = op.isPresent();
            if (b) {
                User u = op.get();
                u.setPassword(passwordEncoder.encode(newPassword1));
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
     * loginVerification
     */
    public ResponseResult loginVerification(LoginVM loginVM) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setStatusCode(ResponseResult.SUCCESS_CODE);

        Optional<User> op = getUserWithAuthoritiesByLogin(loginVM.getUsername());
        boolean b = op.isPresent();
        if (b) {
            User u = op.get();
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

   /* public ResponseResult userRegister(Map<String, Object> params) {
        User user = new User();
        ResponseResult response = new ResponseResult();
        String mobile = params.get("mobile") == null ? "" : params.get("mobile").toString();
        String firstName = params.get("firstName") == null ? "" : params.get("firstName").toString();
        String storeName = params.get("storeName") == null ? "" : params.get("storeName").toString();
        String validateCode = params.get("validateCode") == null ? "" : params.get("validateCode").toString();
        String openId = params.get("openId") == null ? "" : params.get("openId").toString();
        User checkUser = this.checkMobile(mobile);
        if (checkUser != null) {
            response.setStatusCode(ResponseResult.FAIL_CODE);
            response.setMsgCode("您的手机已进行注册");
            return response;
        }

        checkUser = this.checkOpenId(openId);
        if (checkUser != null) {
            response.setStatusCode(ResponseResult.FAIL_CODE);
            response.setMsgCode("您的微信号已进行注册");
            return response;
        }

        HashMap<String, Object> mobileMap = new HashMap<String, Object>();
        mobileMap.put("mobileNum", mobile);
        mobileMap.put("validateCode", validateCode);
        ResponseResult validateResult = mobileValidateService.verification(mobileMap);
        if (!"0000".equals(validateResult.getStatusCode())) {
            response.setStatusCode(ResponseResult.FAIL_CODE);
            response.setMsgCode("验证码错误");
            return response;
        }

        //保存用户信息
        user.setLogin(openId);
        user.setFirstName(firstName);
        user.setMobile(mobile);
        user.setOpenId(openId);
        String password = passwordEncoder.encode(Constants.DEFAULT_PASSWORD);
        user.setPassword(password);
        user.setActivated(true);
        user.setLangKey("zh-cn");
        user.setCreatedBy("weixin");
        user.setCreatedDate(new Date());
        user.setLastModifiedBy("weixin");
        user.setDelFlag("1");
        userRepository.save(user);
        //保存角色关系
        SysRole role = sysRoleService.querySysRoleByNo(AuthoritiesConstants.ROLE_NO_STORE_ADMIN);
        HashMap<String, Object> userRoleMap = new HashMap<String, Object>();
        List<Integer> roleIds = new ArrayList<Integer>();
        userRoleMap.put("userId", user.getId());
        roleIds.add(role.getId().intValue());
        userRoleMap.put("roleIds", roleIds);
        sysUserRoleService.saveUserRole(userRoleMap);
        //保存店铺信息
        this.createStore(user, storeName);

        response.setStatusCode(ResponseResult.SUCCESS_CODE);
        return response;
    }
*/
 /*   private User checkMobile(String mobile) {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.* from jhi_user u where u.c_mobile = '" + mobile + "'");
        List<User> list = baseDao.findListBySql(sql.toString(), "", User.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }*/

/*    public User checkOpenId(String openId) {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.* from jhi_user u where u.c_open_id = '" + openId + "'");
        List<User> list = baseDao.findListBySql(sql.toString(), "", User.class);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }*/


    /**
     * checkUserExpire
     */
    public HashMap<String, Object> checkUserExpire() {
        HashMap<String, Object> response = new HashMap<>();
        StringBuilder column = new StringBuilder(" update jhi_user set activated = 0 ");
        column.append(" where d_expire_date " + dbService.lessTime() + " or d_password_expire_date " + dbService.lessTime() + " ");
        jdbcTemplate.execute(column.toString());
        response.put("statusCode", HttpStatus.OK);
        return response;
    }

    /**
     * 启用禁用用户
     */
    public HashMap<String, Object> enableSysUser(HashMap<String, Object> params) {
        Long userId = params.get("userId") == null ? null : Long.valueOf(params.get("userId").toString());
        String activated = params.get("activated") == null ? null : params.get("activated").toString();
        StringBuilder column = new StringBuilder(" update jhi_user set activated = " + activated + " ");
        if ("1".equals(activated)) {
            column.append(" ,i_error_count = 0 ");
        }
        column.append(" where id = " + userId + " ");
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

    /*private void createStore(User user, String storeName) {
        String storeCode = serialNoService.getStoreSerialNo();
        Date date = new Date();
        StoreDTO store = new StoreDTO();
        store.setUserId(user.getId());
        store.setStoreCode(storeCode);
        store.setCreateAt(date);
        store.setCreateBy(user.getId());
        store.setLastUpdateAt(date);
        store.setLastUpdateBy(user.getId());
        store.setStoreCreate(date);
        store.setStoreName(storeName);
        store.setStoreUrl("");
        store.setVersion(0);
        storeService.save(store);
    }*/

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
     * @param login
     * @return
     */
    @Transactional(readOnly = true)
    public User duplicateLogin(String login, String email) {
        List<User> list = userRepository.duplicateLogin(login, email);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
