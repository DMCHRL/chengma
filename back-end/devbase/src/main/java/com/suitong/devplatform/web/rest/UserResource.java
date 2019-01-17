package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.config.Constants;
import com.suitong.devplatform.domain.User;
import com.suitong.devplatform.repository.UserRepository;
import com.suitong.devplatform.security.AuthoritiesConstants;
import com.suitong.devplatform.service.MailService;
import com.suitong.devplatform.service.SysOperateLogService;
import com.suitong.devplatform.service.UserService;
import com.suitong.devplatform.service.dto.UserDTO;
import com.suitong.devplatform.web.rest.util.HeaderUtil;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import com.suitong.devplatform.web.rest.vm.ManagedUserVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * <p>
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
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
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "user";

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserService userService;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private SysOperateLogService sysOperateLogService;


    public UserResource(UserRepository userRepository, MailService mailService,
                        UserService userService) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userService = userService;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     * </p>
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        if (managedUserVM.getId() != null) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                    .body(null);
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                    .body(null);
        } else if (userRepository.findOneByEmail(managedUserVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                    .body(null);
        } else {
            User newUser = userService.createUser(managedUserVM);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                    .headers(HeaderUtil.createAlert("userManagement.created", newUser.getLogin()))
                    .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        Optional<User> existingUser = userRepository.findOneByEmail(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<UserDTO> updatedUser = userService.updateUser(managedUserVM);

        return ResponseUtil.wrapOrNotFound(updatedUser,
                HeaderUtil.createAlert("userManagement.updated", managedUserVM.getLogin()));
    }

    /**
     * GET  /users : get all users.
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<ResponseResult> getAllUsers(@ApiParam Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        ResponseResult json = new ResponseResult();
        json.setData(page.getContent());
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
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
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
                userService.getUserWithAuthoritiesByLogin(login)
                        .map(UserDTO::new));
    }

    /**
     * save
     */
    @PostMapping("/user/save")
    @Timed
    public ResponseEntity<ResponseResult> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        ResponseResult response = userService.save(userDTO);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    /**
     * list
     */
    @PostMapping("/user/list")
    @Timed
    public ResponseEntity<ResponseResult> getAllUsers(@RequestBody HashMap<String, Object> params) {
        HashMap<String, Object> response = userService.findAll(params);
        Page<UserDTO> page = (Page<UserDTO>) response.get("page");
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actUser/list");
        response.put("link", headers.get(HttpHeaders.LINK).get(0));
        response.put("total_count", headers.get("X-Total-Count").get(0));
        response.put("list", page.getContent());
        ResponseResult json = new ResponseResult();
        json.setData(response);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, headers, HttpStatus.OK);
    }

    /**
     * get
     */
    @GetMapping("/user/get/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.findOne(id);
        ResponseResult json = new ResponseResult();
        json.setData(userDTO);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * delete
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
     * resetPassword
     */
    @GetMapping("/user/resetPassword/{id}")
    @Timed
    public ResponseEntity<ResponseResult> resetPassword(@PathVariable Long id) {
        HashMap<String, Object> response = userService.resetPassword(id);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * editPassword
     */
    @PostMapping("/user/editPassword")
    @Timed
    public ResponseEntity<ResponseResult> editPassword(@RequestBody HashMap<String, Object> params) {
        ResponseResult response = userService.editPassword(params);
        return new ResponseEntity<>(response, null, HttpStatus.OK);
    }

    /**
     * userRegister
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
     * 启用、禁用用户
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysRoles in body
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

}
