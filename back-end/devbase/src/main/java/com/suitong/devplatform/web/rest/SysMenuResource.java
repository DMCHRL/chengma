package com.suitong.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.util.TreeCreator;
import com.suitong.devplatform.service.SysComponentService;
import com.suitong.devplatform.service.SysMenuService;
import com.suitong.devplatform.service.SysRoleService;
import com.suitong.devplatform.service.dto.MenuTreeNode;
import com.suitong.devplatform.service.dto.SysComponentDTO;
import com.suitong.devplatform.service.dto.SysFormDTO;
import com.suitong.devplatform.service.dto.SysMenuDTO;
import com.suitong.devplatform.service.impl.SysRoleServiceImpl;
import com.suitong.devplatform.web.rest.util.HeaderUtil;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SysMenu.
 */
@RestController
@RequestMapping("/api")
public class SysMenuResource {

    private final Logger log = LoggerFactory.getLogger(SysMenuResource.class);

    private static final String ENTITY_NAME = "sysMenu";

    private final SysMenuService sysMenuService;
    @Autowired
    private SysRoleService sysRoleService;

    public SysMenuResource(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    /**
     * POST  /sys-menus : Create a new sysMenu.
     *
     * @param sysMenuDTO the sysMenuDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysMenuDTO, or with status 400 (Bad Request) if the sysMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-menu/createSysMenu")
    @Timed
    public ResponseEntity<ResponseResult> createSysMenu(@RequestBody SysMenuDTO sysMenuDTO) throws URISyntaxException {
        log.debug("REST request to save SysMenu : {}", sysMenuDTO);
        if (sysMenuDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysMenu cannot already have an ID")).body(null);
        }
        if(sysMenuDTO.getIcon() == null){
            sysMenuDTO.setIcon("glyphicon-book");
        }
        HashMap<String,Object> response = sysMenuService.save(sysMenuDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * PUT  /sys-menus : Updates an existing sysMenu.
     *
     * @param sysMenuDTO the sysMenuDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysMenuDTO,
     * or with status 400 (Bad Request) if the sysMenuDTO is not valid,
     * or with status 500 (Internal Server Error) if the sysMenuDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-menu/updateSysMenu")
    @Timed
    public ResponseEntity<ResponseResult> updateSysMenu(@RequestBody SysMenuDTO sysMenuDTO) throws URISyntaxException {
        log.debug("REST request to update SysMenu : {}", sysMenuDTO);
        if (sysMenuDTO.getId() == null) {
            return createSysMenu(sysMenuDTO);
        }
        if(sysMenuDTO.getIcon() == null){
            sysMenuDTO.setIcon("glyphicon-book");
        }
        HashMap<String,Object> response = sysMenuService.save(sysMenuDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 根据选中的菜单，获取他下面的子菜单
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysMenus in body
     */
    @PostMapping("/sys-menu/getAllSysMenus")
    @Timed
    public ResponseEntity<ResponseResult> getAllSysMenus(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = sysMenuService.findAll(params);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * GET  /sys-menus/:id : get the "id" sysMenu.
     *
     * @param id the id of the sysMenuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysMenuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys-menu/getSysMenu/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysMenu(@PathVariable Long id) {
        log.debug("REST request to get SysMenu : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            SysMenuDTO sysMenuDTO = sysMenuService.findOne(id);
            json.setData(sysMenuDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * DELETE  /sys-menus/:id : delete the "id" sysMenu.
     *
     * @param ids the id of the sysMenuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/sys-menu/deleteSysMenu/")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysMenu(@RequestBody Long[] ids) {
        log.debug("REST request to delete SysMenu : {}");
        ResponseResult json = new ResponseResult();
        try {
            for(Long id : ids){
                //删除菜单时，同时删除该与菜单相关的角色信息
                sysMenuService.deleteSysRoleMenu(id);
                sysMenuService.delete(id);
                //如果删除的是父级菜单，则应该级联删除他的子菜单
                sysMenuService.deleteChildrenMenu(id);
                sysMenuService.deleteFormComByMenuId(id);
            }
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 启用、禁用菜单
     * @return the ResponseEntity with status 200 (OK) and the list of sysRoles in body
     */
    @PostMapping("/sys-menu/enableSysMenu")
    @Timed
    public ResponseEntity<ResponseResult> enableSysMenu(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a page of SysRoles");
        HashMap<String, Object> response = sysMenuService.enableSysMenu(params);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取菜单树
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysMenus in body
     */
    @PostMapping("/sys-menu/getMenuTreeList")
    @Timed
    public ResponseEntity<ResponseResult> getMenuTreeList(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = new ResponseResult();
        try {
            List<MenuTreeNode> list = sysMenuService.getMenuTreeList(params);
            list = TreeCreator.constructTree(list, Long.valueOf(0));
            HashMap<String, Object> response = new HashMap<>();
            response.put("list", list);
            json.setData(response);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * 获取菜单树
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysMenus in body
     */
    @PostMapping("/sys-menu/getMenuRightList")
    @Timed
    public ResponseEntity<ResponseResult> getMenuRightList(@RequestBody HashMap<String,Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = new ResponseResult();
        try {
            params.put("visible","1");
            List<MenuTreeNode> list = sysRoleService.getMenuRightList(params);
            HashMap<String, Object> response = new HashMap<>();
            if(list != null && !list.isEmpty()) {
                StringBuffer menuIds = new StringBuffer();

                for (MenuTreeNode menuTreeNode : list) {
                    if(menuTreeNode.getChecked() == true){
                        Long menuId = menuTreeNode.getId();
                        if (menuIds.toString().equals("")) {
                            menuIds.append(menuId);
                        } else {
                            menuIds.append("," + menuId);
                        }
                    }
                }
                params.put("menuId",menuIds);
                response = sysRoleService.getFormRightList(params);
            }
            list = TreeCreator.constructTree(list, Long.valueOf(0));

            response.put("menuList", list);
            json.setData(response);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
