package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.domain.SysUserRole;
import com.suitong.devplatform.service.SysUserRoleService;
import com.suitong.devplatform.service.dto.SysRoleDTO;
import com.suitong.devplatform.service.dto.UserDTO;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
@RestController
@RequestMapping("/api")
public class SysUserRoleResource {

    private final Logger log = LoggerFactory.getLogger(SysUserRole.class);

    private static final String ENTITY_NAME = "sysUserRole";

    private final SysUserRoleService sysUserRoleService;

    public SysUserRoleResource(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }

    @PostMapping("/sysUserRole/getAllUsers")
    @Timed
    public ResponseEntity<ResponseResult> getAllUsers(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a page of UserDTO");
        Page<UserDTO> page = sysUserRoleService.findAll(params);
        HashMap<String, Object> response = new HashMap<>();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-userRole/getAllUsers");
        response.put("list", page.getContent());
        response.put("total_count", headers.get("X-Total-Count").get(0));
        response.put("link", headers.get(HttpHeaders.LINK).get(0));
        response.put("page_number", (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString())));
        response.put("page_size", (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString())));
        ResponseResult json = new ResponseResult();
        json.setData(response);
        json.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    @PostMapping("/sysUserRole/saveSysUserRole")
    @Timed
    public ResponseEntity<ResponseResult> saveSysUserRole(@RequestBody HashMap<String,Object> params) {
        ResponseResult responseResult = new ResponseResult();
        sysUserRoleService.saveOrDeleteUserRole(params);
        responseResult.setStatusCode(ResponseResult.SUCCESS_CODE);
        responseResult.setMsgCode("保存成功");
        return new ResponseEntity<>(responseResult, null, HttpStatus.OK);
    }

    @PostMapping("/sysUserRole/saveUserRole")
    @Timed
    public ResponseEntity<ResponseResult> saveUserRole(@RequestBody HashMap<String,Object> params) {
        ResponseResult responseResult = new ResponseResult();
        sysUserRoleService.saveUserRole(params);
        responseResult.setStatusCode(ResponseResult.SUCCESS_CODE);
        responseResult.setMsgCode("保存成功");
        return new ResponseEntity<>(responseResult, null, HttpStatus.OK);
    }

    @PostMapping("/sysUserRole/getUserRole")
    @Timed
    public ResponseEntity<ResponseResult> getUserRole(@RequestBody HashMap<String,Object> params) {
        ResponseResult responseResult = new ResponseResult();
        Long userId = params.get("userId") == null? null :  Long.parseLong(params.get("userId").toString());
        HashMap<String, Object> data = new HashMap<>();
        List<SysRoleDTO> userRoleList = sysUserRoleService.getRoleByUserId(userId, "Y");//用户拥有的角色
        List<SysRoleDTO> roleList = sysUserRoleService.getRoleByUserId(userId, "N");//用户没有的角色
        data.put("userRoleList", userRoleList);
        data.put("roleList", roleList);
        responseResult.setData(data);
        responseResult.setStatusCode(ResponseResult.SUCCESS_CODE);
        return new ResponseEntity<>(responseResult, null, HttpStatus.OK);
    }
}
