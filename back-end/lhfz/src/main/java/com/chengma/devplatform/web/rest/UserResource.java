package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.service.MobileUserService;
import com.chengma.devplatform.service.SysOperateLogService;
import com.chengma.devplatform.service.dto.MobileUserDTO;
import com.chengma.devplatform.service.dto.view.UserChartDTO;
import com.chengma.devplatform.service.dto.view.UserComboListDTO;
import com.chengma.devplatform.service.dto.view.UserListDTO;
import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.config.Constants;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.security.AuthoritiesConstants;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.service.mapper.UserMapper;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing users.
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "user";

    private final UserService userService;

    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MobileUserService mobileUserService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login 账号
     * @return ResponseEntity
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
//        return ResponseUtil.wrapOrNotFound(
//                userService.getUserWithAuthoritiesByLogin(login)
//                        .map(UserDTO::new));

        User user = userService.getUserWithAuthoritiesByLogin(login);
        UserDTO userDTO = userMapper.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, null, HttpStatus.OK);
    }

    /**
     * post /user/save ：保存用户信息
     *
     * @param userDTO 用户信息模型
     * @return statusCode:成功0000，失败0100。
     * @throws URISyntaxException uri异常
     */
    @PostMapping("/user/save")
    @Timed
    public ResponseEntity<ResponseResult> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        ResponseResult response = userService.save(userDTO);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    /**
     * post /user/list ：获取用户page。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{login:"",firstName:"",activated:""}}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/list")
    @Timed
    public ResponseEntity<ResponseResult> getAllUsers(@RequestBody HashMap<String, Object> params) {
        HashMap<String, Object> response = userService.findAll(params);
        Page<UserListDTO> page = (Page<UserListDTO>) response.get("page");
        HashMap<String, Object> retMap = new HashMap<>();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user/list");
        retMap.put("link", headers.get(HttpHeaders.LINK).get(0));
        retMap.put("total_count", Integer.parseInt(headers.get("X-Total-Count").get(0)));
        retMap.put("list", page.getContent());
        ResponseResult json = new ResponseResult();
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * post /user/get/{id} ：获取用户信息
     *
     * @param id 用户id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/user/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> get(@PathVariable String id) {
        UserDTO userDTO = userService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(userDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * get /user/delete/{id} ：删除用户记录（可批量删除）
     *
     * @param id 用户id，多个id之间用“,”隔开。
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/user/delete/{id}")
    @Timed
    public ResponseEntity<ResponseResult> deleteUser(@PathVariable String id) {
        HashMap<String, Object> response = userService.deleteUser(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * get /user/resetPassword/{id} ：重置用户密码
     *
     * @param id 用户id
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/user/resetPassword/{id}")
    @Timed
    public ResponseEntity<ResponseResult> resetPassword(@PathVariable String id) {
        HashMap<String, Object> response = userService.resetPassword(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/editPassword ：修改用户密码
     *
     * @param params 参数：{login:"",oldPassword:"",newPassword1:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/editPassword")
    @Timed
    public ResponseEntity<ResponseResult> editPassword(@RequestBody HashMap<String, Object> params) {
        ResponseResult response = userService.editPassword(params);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    /**
     * post /user/layoutLog ：退出登陆的日志记录
     *
     * @param userDTO 用户信息
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/layoutLog")
    @Timed
    public ResponseEntity<ResponseResult> layoutLog(@RequestBody UserDTO userDTO) {
        sysOperateLogService.addLog(userDTO.getLogin(), userDTO.getFirstName(), "1", new Date(), "用户：" + userDTO.getLogin() + " 退出登陆");
        ResponseResult responseResult = new ResponseResult();
        responseResult.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(responseResult, null, HttpStatus.OK);
    }

    /**
     * post /user/enableSysUser ：启用、禁用用户
     *
     * @param params 参数：{userId:"",activated:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/enableSysUser")
    @Timed
    public ResponseEntity<ResponseResult> enableSysRole(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        HashMap<String, Object> response = userService.enableSysUser(params);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

   /* @GetMapping("/user/loadMyAccount")
    @Timed
    public ResponseEntity<ResponseResult> loadMyAccount() {
        log.debug("REST request to get a page of SysRoles");
        List<TlbAccountDTO> list = userService.loadMyAccount();
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(list);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
*/
   /* @PostMapping("/user/loadBelongAccount")
    @Timed
    public ResponseEntity<ResponseResult> loadBelongAccount(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of loadBelongAccount");
        HashMap<String, Object> retMap = userService.findAllAccount(params);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        json.setData(retMap);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
*/
    /**
     * post /user/createPowerUser ：系统内新建一个已认证用户
     *
     * @param mobileUserDTO 参数：{userId:"",activated:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/createPowerUser")
    @Timed
    public ResponseEntity<ResponseResult> createPowerUser(@RequestBody MobileUserDTO mobileUserDTO) {
        log.debug("REST request to createPowerUser {}",mobileUserDTO);
        HashMap<String, Object> response = userService.createPowerUser(mobileUserDTO);
        ResponseResult json = new ResponseResult();
        if(!ResponseResult.SUCCESS_CODE.equals(response.get("statusCode"))){
            json.setMsgCode(response.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/createPowerUser ：加载用户的信息
     *
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/user/loadInfo")
    @Timed
    public ResponseEntity<ResponseResult> loadInfo() {
        log.debug("REST request to createPowerUser {}");
        MobileUserDTO mobileUserDTO = userService.loadInfo();
        ResponseResult json = new ResponseResult();
        json.setData(mobileUserDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /user/createPowerUser ：加载power用户的信息
     *
     * @param params 参数：{userId:"",activated:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/loadPowerInfo")
    @Timed
    public ResponseEntity<ResponseResult> loadPowerInfo(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to loadPowerInfo {}",params);
        MobileUserDTO mobileUserDTO = userService.loadPowerInfo(params);
        ResponseResult json = new ResponseResult();
        json.setData(mobileUserDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     *
     * @param department 参数：{userId:"",activated:""}
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/user/findByDepartment/{department}")
    @Timed
    public ResponseEntity<ResponseResult> findByDepartment(@PathVariable String department) {
        log.debug("REST request to findByDepartment {}",department);
        List<User> list = userService.findByDepartment(department);
        ResponseResult json = new ResponseResult();
        HashMap<String,Object> result = new HashMap<>();
        result.put("list",list);
        json.setData(result);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 更新用户信息
     * @param mobileUserDTO 参数：{userId:"",activated:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/updateUserInfo")
    @Timed
    public ResponseEntity<ResponseResult> updateUserInfo(@RequestBody MobileUserDTO mobileUserDTO) {
        log.debug("REST request to updateUserInfo {}",mobileUserDTO);
        HashMap<String, Object> checkMap  = mobileUserService.updateUserInfo(mobileUserDTO);
        ResponseResult json = new ResponseResult();
        if(!ResponseResult.SUCCESS_CODE.equals(checkMap.get("statusCode"))){
            json.setMsgCode(checkMap.get("msg").toString());
            return new ResponseEntity<>(json, null, HttpStatus.OK);
        }
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 用户信息分页查询
     * @param params 参数：{userId:"",activated:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/mobileUserList")
    @Timed
    public ResponseEntity<ResponseResult> mobileUserList(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to mobileUserList {}",params);
        Page<MobileUserDTO> page = mobileUserService.pageList(params);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list", page.getContent());
        retMap.put("total", page.getTotalElements());
        retMap.put("totalPage", page.getTotalPages());
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 显示树状图
     * @return statusCode:成功0000，失败0100。
     */
    @GetMapping("/user/showUserChart/{userId}")
    @Timed
    public ResponseEntity<ResponseResult> showUserChart(@PathVariable String userId) {
        log.debug("REST request to showUserChart");
        UserChartDTO userChartDTO = userService.showUserChart(userId);
        ResponseResult json = new ResponseResult();
        json.setData(userChartDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * userComboList
     * @param params 参数：{userId:"",activated:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/user/userComboList")
    @Timed
    public ResponseEntity<ResponseResult> userComboList(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to userComboList {}",params);
        List<UserComboListDTO> list = userService.findUserComboList(params);
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("list",list);
        json.setData(retMap);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

}
