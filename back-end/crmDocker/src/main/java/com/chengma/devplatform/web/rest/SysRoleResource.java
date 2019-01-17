package com.chengma.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.SysRoleService;
import com.chengma.devplatform.service.dto.SysRoleDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;

/**
 * REST controller for managing SysRole.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/api")
public class SysRoleResource {

    private final Logger log = LoggerFactory.getLogger(SysRoleResource.class);

    private static final String ENTITY_NAME = "sysRole";

    private final SysRoleService sysRoleService;

    public SysRoleResource(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * POST  /sys-role/createSysRole : Create a new sysRole.
     *
     * @param sysRoleDTO the sysRoleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysRoleDTO, or with status 400 (Bad Request) if the sysRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-role/createSysRole")
    @Timed
    public ResponseEntity<ResponseResult> createSysRole(@RequestBody SysRoleDTO sysRoleDTO) throws URISyntaxException {
        log.debug("REST request to save SysRole : {}", sysRoleDTO);
        if (sysRoleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysRole cannot already have an ID")).body(null);
        }
        sysRoleDTO.setCreateAt(new Date());
        HashMap<String, Object> response = sysRoleService.save(sysRoleDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * PUT  /sys-role/updateSysRole : Updates an existing sysRole.
     *
     * @param sysRoleDTO the sysRoleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysRoleDTO,
     * or with status 400 (Bad Request) if the sysRoleDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysRoleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-role/updateSysRole")
    @Timed
    public ResponseEntity<ResponseResult> updateSysRole(@RequestBody SysRoleDTO sysRoleDTO) throws URISyntaxException {
        log.debug("REST request to update SysRole : {}", sysRoleDTO);
        if (sysRoleDTO.getId() == null) {
            return createSysRole(sysRoleDTO);
        }
        sysRoleDTO.setLastUpdateAt(new Date());
        HashMap<String, Object> response = sysRoleService.save(sysRoleDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/getAllSysRoles ：获取角色page。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{roleName:"",roleNo:"",delFlag:"",description:""}}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/getAllSysRoles")
    @Timed
    public ResponseEntity<ResponseResult> getAllSysRoles(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        ResponseResult json = sysRoleService.findAll(params);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * GET  /sys-role/getSysRole/{id} : get the "id" sysRole.
     *
     * @param id 角色id
     * @return the ResponseEntity with status 200 (OK) and with body the sysRoleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys-role/getSysRole/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysRole(@PathVariable String id) {
        ResponseResult json = new ResponseResult();
        try {
            SysRoleDTO sysRoleDTO = sysRoleService.findOne(id);
            json.setData(sysRoleDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post  /sys-role/deleteSysRole : delete the "id" sysRole.
     *
     * @param ids the id of the sysRoleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/sys-role/deleteSysRole")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysRole(@RequestBody String[] ids) {
        log.debug("REST request to delete SysRole : {}");
        ResponseResult json = new ResponseResult();
        try {
            for (String id : ids) {
                sysRoleService.deletePrivilegeByRole(id);
                sysRoleService.delete(id);
            }
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/allocatePrivilegeMenuTree ：分配角色页面，获取该角色授权菜单。
     *
     * @param params 参数：{roleId:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/allocatePrivilegeMenuTree")
    @Timed
    public ResponseEntity<ResponseResult> allocatePrivilegeMenuTree(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        ResponseResult json = new ResponseResult();
        HashMap<String, Object> response = sysRoleService.allocatePrivilegeMenuTree(params);
        json.setData(response);
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/getFormsByRoleAndMenu ：分配角色页面，获取某菜单包含的表单页面。
     *
     * @param params 参数：{}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/getFormsByRoleAndMenu")
    @Timed
    public ResponseEntity<ResponseResult> getFormsByRoleAndMenu(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/getComponentsByRoleAndForm ：分配角色页面，获取表单下的控件按钮。
     *
     * @param params 参数：{}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/getComponentsByRoleAndForm")
    @Timed
    public ResponseEntity<ResponseResult> getComponentsByRoleAndForm(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        ResponseResult json = new ResponseResult();
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/saveRolePrivilege ：根据页面选中的菜单，表单，控件，保存用户权限
     *
     * @param params 参数：{menuList：{},formList:{},componentList:{},roleId:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/saveRolePrivilege")
    @Timed
    public ResponseEntity<ResponseResult> saveRolePrivilege(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        HashMap<String, Object> response = sysRoleService.saveRolePrivilege(params);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/enableSysRole ：启用、禁用角色。
     *
     * @param params 参数：{roleId:"",delFlag:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/enableSysRole")
    @Timed
    public ResponseEntity<ResponseResult> enableSysRole(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        HashMap<String, Object> response = sysRoleService.enableSysRole(params);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/getFormRightList ：通过登录用户以及所选菜单，得到授权显示页面和按钮。
     *
     * @param params 参数：{menuId:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/getFormRightList")
    @Timed
    public ResponseEntity<ResponseResult> getFormRightList(@RequestBody HashMap<String, Object> params) {
        HashMap<String, Object> response = new HashMap<>();
        response = sysRoleService.getFormRightList(params);
        ResponseResult json = new ResponseResult();
        json.setData(response);
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-role/checkRoleNo ：判断角色编号是否已经存在。
     *
     * @param params 参数：{roleNo:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-role/checkRoleNo")
    @Timed
    public ResponseEntity<ResponseResult> checkRoleNo(@RequestBody HashMap<String, Object> params) {
        ResponseResult json = new ResponseResult();
        try {
            int count = sysRoleService.checkRoleNo(params);
            json.setData(count);
            if (count > 0) {
                json.setMsgCode(DevplatformConstants.REPEAT_ROLENO);
            }
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
