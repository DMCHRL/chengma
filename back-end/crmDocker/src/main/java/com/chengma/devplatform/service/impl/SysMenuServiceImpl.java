package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.SysForm;
import com.chengma.devplatform.domain.SysMenu;
import com.chengma.devplatform.domain.SysRoleMenu;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.SysFormRepository;
import com.chengma.devplatform.repository.SysMenuRepository;
import com.chengma.devplatform.repository.SysRoleMenuRepository;
import com.chengma.devplatform.service.SysFormService;
import com.chengma.devplatform.service.SysMenuService;
import com.chengma.devplatform.service.SysOperateLogService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.MenuTreeNode;
import com.chengma.devplatform.service.dto.SysMenuDTO;
import com.chengma.devplatform.service.dto.SysRoleMenuDTO;
import com.chengma.devplatform.service.mapper.SysMenuMapper;
import com.chengma.devplatform.service.mapper.SysRoleMenuMapper;
import com.chengma.devplatform.web.rest.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Service Implementation for managing SysMenu.
 *
 * @author administrator
 */
@Service
@Transactional
public class SysMenuServiceImpl implements SysMenuService {

    private final Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    private final SysMenuRepository sysMenuRepository;

    private final SysMenuMapper sysMenuMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private SysRoleMenuRepository sysRoleMenuRepository;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    UserService userService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private SysFormService sysFormService;
    @Autowired
    private SysFormRepository sysFormRepository;

    public SysMenuServiceImpl(SysMenuRepository sysMenuRepository, SysMenuMapper sysMenuMapper) {
        this.sysMenuRepository = sysMenuRepository;
        this.sysMenuMapper = sysMenuMapper;
    }

    /**
     * Save a sysMenu.
     *
     * @param sysMenuDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HashMap<String, Object> save(SysMenuDTO sysMenuDTO) {
        log.debug("Request to save SysMenu : {}", sysMenuDTO);
        HashMap<String, Object> response = new HashMap<>();
        try {
            SysMenu sysMenu = sysMenuMapper.toEntity(sysMenuDTO);
            sysMenu = sysMenuRepository.save(sysMenu);
            if(sysMenu != null){
                String adminRoleId = DevplatformConstants.ADMIN_ROLE_ID;
                int count = sysRoleMenuRepository.checkRoleMenuExists(adminRoleId ,sysMenu.getId());
                if(count < 1){
                    SysRoleMenuDTO sysRoleMenuDTO = new SysRoleMenuDTO();
                    sysRoleMenuDTO.setSysMenuId(sysMenu.getId());
                    sysRoleMenuDTO.setSysRoleId(adminRoleId);
                    SysRoleMenu sysRoleMenu = sysRoleMenuMapper.toEntity(sysRoleMenuDTO);
                    sysRoleMenuRepository.save(sysRoleMenu);
                }
            }
            User user = userService.getUserWithAuthorities();
            if (sysMenuDTO.getId() != null) {
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "修改菜单：" + sysMenuDTO.getMenuName());
            } else {
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "新增菜单：" + sysMenuDTO.getMenuName());
            }
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    /**
     * Get all the sysMenus.
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{queryTerm:"",parentId:""}}
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseResult findAll(HashMap<String, Object> params) {
        log.debug("Request to get all SysMenus");
        int pageNumber = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int pageSize = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = params.get("formParams") == null ? null : (HashMap<String, Object>) params.get("formParams");
        String menuName = (formParams.get("queryTerm") == null ? "" : formParams.get("queryTerm").toString());
        String parentId = (formParams.get("parentId") == null ? "" : formParams.get("parentId").toString());

        String column = "select sm.c_id,sm.c_menu_no menu_no,sm.c_menu_name menu_name,sm.c_parent_id parent_id,";
        column += "sm.c_english_name english_name,sm.n_sort sort,sm.c_visible visible,sm.c_url url,sm.c_icon icon,c_lang_switch lang_switch,";
        column += "(SELECT s.c_menu_name FROM t_sys_menu s WHERE s.c_id= sm.c_parent_id ) parent_name ";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_menu sm ");
        cond.append(" where 1=1 ");
        if (StringUtils.isNotBlank(parentId)) {
            cond.append(" and sm.c_parent_id = '" + parentId + "' ");
        }
        if (StringUtils.isNotBlank(menuName)) {
            cond.append(" and sm.c_menu_name like '%" + menuName + "%'");
        }
        cond.append(" order by sm.n_sort ");
        HashMap<String, Object> response = new HashMap<>();
        ResponseResult json = new ResponseResult();
        try {
            Page<SysMenuDTO> page = pageCommon.execPage(column, cond.toString(), pageNumber, pageSize, SysMenuDTO.class);
            response.put("list", page.getContent());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-menu/getAllSysMenus");
            response.put("link", headers.get(HttpHeaders.LINK).get(0));
            response.put("total_count", headers.get("X-Total-Count").get(0));
            json.setData(response);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return json;
    }

    /**
     * 获取菜单列表
     *
     * @param params 参数：{}
     * @return List
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysMenuDTO> getAllSysMenu(HashMap<String, Object> params) {
        log.debug("Request to get all SysMenus");
        String column = "select sm.c_id,sm.c_menu_no menu_no,sm.c_menu_name menu_name,sm.c_parent_id parent_id,";
        column += "sm.c_english_name english_name,sm.n_sort sort,sm.c_visible visible,sm.c_url url,sm.c_icon icon,c_lang_switch lang_switch,";
        column += "(SELECT s.c_menu_name FROM t_sys_menu s WHERE s.c_id= sm.c_parent_id ) parent_name ";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_menu sm ");
        cond.append(" where 1=1 and sm.c_visible = '1' ");
        cond.append(" order by sm.n_sort ");
        List<SysMenuDTO> sysMenuList = baseDao.findListBySql(column, cond.toString(), SysMenuDTO.class);
        return sysMenuList;
    }

    /**
     * Get one sysMenu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysMenuDTO findOne(String id) {
        log.debug("Request to get SysMenu : {}", id);
        StringBuilder column = new StringBuilder();
        column.append("select sm.c_id,sm.c_parent_id, sm.c_menu_name,sm.c_url, sm.c_menu_no,");
        column.append(" sm.c_icon icon,sm.c_english_name,sm.n_sort,sm.c_visible,c_lang_switch lang_switch, ");
        column.append(" (SELECT s.c_menu_name FROM t_sys_menu s WHERE s.c_id= sm.c_parent_id ) parent_name ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_menu sm where 1 = 1");
        cond.append(" and sm.c_id = '" + id + "' ");
        List<SysMenuDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysMenuDTO.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Delete the  sysMenu by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SysMenu : {}", id);
        SysMenuDTO sysMenuDTO = findOne(id);
        sysMenuRepository.delete(id);

        User user = userService.getUserWithAuthorities();
        sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "删除菜单：" + sysMenuDTO.getMenuName());
    }

    /**
     * 获取树状菜单
     *
     * @param params 参数：{visible:"",menuName:"",parentId:""}
     * @return List
     */
    @Override
    public List<MenuTreeNode> getMenuTreeList(HashMap<String, Object> params) {
        String visible = (params.get("visible") == null ? "" : params.get("visible").toString());
        String menuName = (params.get("menuName") == null ? "" : params.get("menuName").toString());
        String parentId = (params.get("parentId") == null ? "" : params.get("parentId").toString());
        StringBuilder column = new StringBuilder();
        column.append("select sm.c_id id, sm.c_parent_id parent_id, sm.c_menu_name menu_name,sm.c_url url, sm.c_menu_no menu_no,");
        column.append(" sm.c_icon icon,sm.c_english_name english_name,sm.n_sort sort,sm.c_visible visible,c_lang_switch lang_switch, ");
        column.append(" (SELECT s.c_menu_name FROM t_sys_menu s WHERE s.c_id= sm.c_parent_id ) parent_name ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_menu sm where 1 = 1");
        if (StringUtils.isNotBlank(visible)) {
            cond.append(" and sm.c_visible = '" + visible + "' ");
        }
        if (StringUtils.isNotBlank(parentId)) {
            cond.append(" and sm.c_parent_id = '" + parentId + "' ");
        }
        if (StringUtils.isNotBlank(menuName)) {
            cond.append(" and sm.c_menu_name like '%" + menuName + "%'");
        }
        cond.append(" order by sm.n_sort ");
        List<MenuTreeNode> list = baseDao.findListBySql(column.toString(), cond.toString(), MenuTreeNode.class);
        return list;
    }

    /**
     * 删除角色菜单
     * 删除菜单时，同时删除该与菜单相关的角色信息
     *
     * @param menuId 菜单id
     */
    @Override
    public void deleteSysRoleMenu(String menuId) {
        sysRoleMenuRepository.deleteSysRoleMenu(menuId);
    }

    /**
     * 删除子菜单
     * 如果删除的是父级菜单，则应该级联删除他的子菜单,同时删除与子菜单相关的角色授权
     *
     * @param parentId 菜单id
     */
    @Override
    public void deleteChildrenMenu(String parentId) {
        List<SysMenu> childrenList = sysMenuRepository.getSysMenusByParentId(parentId);
        for (SysMenu childrenMenu : childrenList) {
            List<SysMenu> grandsonList = sysMenuRepository.getSysMenusByParentId(childrenMenu.getId());
            for (SysMenu grandsonMenu : grandsonList) {
                sysMenuRepository.delete(grandsonMenu.getId());
                sysRoleMenuRepository.deleteSysRoleMenu(grandsonMenu.getId());
            }
            sysMenuRepository.delete(childrenMenu.getId());

            User user = userService.getUserWithAuthorities();
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "删除菜单：" + childrenMenu.getMenuName());

            sysRoleMenuRepository.deleteSysRoleMenu(childrenMenu.getId());
        }
    }

    /**
     * 启用、禁用菜单
     *
     * @param params 参数：{menuId:"",visible:""}
     * @return HashMap
     */
    @Override
    public HashMap<String, Object> enableSysMenu(HashMap<String, Object> params) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            String menuId = params.get("menuId") == null ? "" : params.get("menuId").toString();
            String visible = params.get("visible") == null ? "" : params.get("visible").toString();
            sysMenuRepository.enableSysMenu(visible, menuId);

            User user = userService.getUserWithAuthorities();

            SysMenuDTO sysMenuDTO = findOne(menuId);
            if ("1".equals(visible)) {
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "启用菜单：" + sysMenuDTO.getMenuName());
            } else {
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "禁用菜单：" + sysMenuDTO.getMenuName());
            }
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            log.error(e.getMessage());
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    /**
     * 删除表单和组件
     *
     * @param menuId 菜单id
     */
    @Override
    public void deleteFormComByMenuId(String menuId) {
        List<SysForm> formList = sysFormRepository.getFormListByMenuId(menuId);
        if (formList != null) {
            for (SysForm form : formList) {
                sysFormService.deleteSysRoleForm(form.getId());
                sysFormService.delete(form.getId());
            }
        }
    }

    @Override
    public List<SysMenuDTO> findMenusByRoleId(String roleId) {
        return sysMenuMapper.toDto(sysMenuRepository.findMenusByRoleId(roleId));
    }
}
