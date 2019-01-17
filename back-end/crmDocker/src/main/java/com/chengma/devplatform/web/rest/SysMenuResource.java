package com.chengma.devplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.util.TreeCreator;
import com.chengma.devplatform.service.SysMenuService;
import com.chengma.devplatform.service.SysRoleService;
import com.chengma.devplatform.service.dto.MenuTreeNode;
import com.chengma.devplatform.service.dto.SysMenuDTO;
import com.chengma.devplatform.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing SysMenu.
 *
 * @author administrator
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
     * POST  /sys-menu/createSysMenu : Create a new sysMenu.
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
        if (sysMenuDTO.getIcon() == null) {
            sysMenuDTO.setIcon("glyphicon-book");
        }
        HashMap<String, Object> response = sysMenuService.save(sysMenuDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * PUT  /sys-menu/updateSysMenu : Updates an existing sysMenu.
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
        if (sysMenuDTO.getIcon() == null) {
            sysMenuDTO.setIcon("glyphicon-book");
        }
        HashMap<String, Object> response = sysMenuService.save(sysMenuDTO);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-menu/getAllSysMenus ：根据选中的菜单，获取他下面的子菜单。
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{queryTerm:"",parentId:""}}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-menu/getAllSysMenus")
    @Timed
    public ResponseEntity<ResponseResult> getAllSysMenus(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = sysMenuService.findAll(params);
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * GET  /sys-menu/getSysMenu/{id} : get the "id" sysMenu.
     *
     * @param id the id of the sysMenuDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysMenuDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sys-menu/getSysMenu/{id}")
    @Timed
    public ResponseEntity<ResponseResult> getSysMenu(@PathVariable String id) {
        log.debug("REST request to get SysMenu : {}", id);
        ResponseResult json = new ResponseResult();
        try {
            SysMenuDTO sysMenuDTO = sysMenuService.findOne(id);
            json.setData(sysMenuDTO);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post  /sys-menu/deleteSysMenu/ : delete the "id" sysMenu.
     *
     * @param ids the id of the sysMenuDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/sys-menu/deleteSysMenu/")
    @Timed
    public ResponseEntity<ResponseResult> deleteSysMenu(@RequestBody String[] ids) {
        log.debug("REST request to delete SysMenu : {}");
        ResponseResult json = new ResponseResult();
        try {
            for (String id : ids) {
                //删除菜单时，同时删除该与菜单相关的角色信息
                sysMenuService.deleteSysRoleMenu(id);
                sysMenuService.delete(id);
                //如果删除的是父级菜单，则应该级联删除他的子菜单
                sysMenuService.deleteChildrenMenu(id);
                sysMenuService.deleteFormComByMenuId(id);
            }
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-menu/enableSysMenu ：启用、禁用菜单。
     *
     * @param params 参数：{menuId:"",visible:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-menu/enableSysMenu")
    @Timed
    public ResponseEntity<ResponseResult> enableSysMenu(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysRoles");
        HashMap<String, Object> response = sysMenuService.enableSysMenu(params);
        ResponseResult json = new ResponseResult();
        json.setStatusCode(response.get("statusCode") == null ? "" : response.get("statusCode").toString());
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-menu/getMenuTreeList ：获取菜单树。
     *
     * @param params 参数：{visible:"",menuName:"",parentId:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-menu/getMenuTreeList")
    @Timed
    public ResponseEntity<ResponseResult> getMenuTreeList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = new ResponseResult();
        try {
            List<MenuTreeNode> list = sysMenuService.getMenuTreeList(params);
            list = TreeCreator.constructTree(list, "0");
            HashMap<String, Object> response = new HashMap<>();
            response.put("list", list);
            json.setData(response);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }

    /**
     * post /sys-menu/getMenuRightList ：获取菜单树。
     *
     * @param params 参数：{visible:"",menuName:"",parentId:""}
     * @return statusCode:成功0000，失败0100。
     */
    @PostMapping("/sys-menu/getMenuRightList")
    @Timed
    public ResponseEntity<ResponseResult> getMenuRightList(@RequestBody HashMap<String, Object> params) {
        log.debug("REST request to get a page of SysMenus");
        ResponseResult json = new ResponseResult();
        try {
            params.put("visible", "1");
            List<MenuTreeNode> list = sysRoleService.getMenuRightList(params);
            HashMap<String, Object> response = new HashMap<>();
            if (list != null && !list.isEmpty()) {
                StringBuffer menuIds = new StringBuffer();

                for (MenuTreeNode menuTreeNode : list) {
                    if (menuTreeNode.getChecked()) {
                        String menuId = menuTreeNode.getId();
                        if ("".equals(menuIds.toString())) {
                            menuIds.append(menuId);
                        } else {
                            menuIds.append("," + menuId);
                        }
                    }
                }
                params.put("menuId", menuIds);
                response = sysRoleService.getFormRightList(params);
            }
            list = TreeCreator.constructTree(list, "0");

            response.put("menuList", list);
            json.setData(response);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return new ResponseEntity<>(json, null, HttpStatus.OK);
    }
}
