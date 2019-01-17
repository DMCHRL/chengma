package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.common.util.TreeCreator;
import com.suitong.devplatform.domain.*;
import com.suitong.devplatform.repository.*;
import com.suitong.devplatform.service.*;
import com.suitong.devplatform.service.dto.*;
import com.suitong.devplatform.service.mapper.SysRoleComponentMapper;
import com.suitong.devplatform.service.mapper.SysRoleFormMapper;
import com.suitong.devplatform.service.mapper.SysRoleMapper;
import com.suitong.devplatform.service.mapper.SysRoleMenuMapper;
import com.suitong.devplatform.web.rest.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Service Implementation for managing SysRole.
 */
@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

    private final Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    private final SysRoleRepository sysRoleRepository;

    private final SysRoleMapper sysRoleMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuRepository sysRoleMenuRepository;
    @Autowired
    private SysRoleFormRepository sysRoleFormRepository;
    @Autowired
    private SysRoleComponentRepository sysRoleComponentRepository;
    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysRoleFormMapper sysRoleFormMapper;
    @Autowired
    private SysRoleComponentMapper sysRoleComponentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private SysComponentService sysComponentService;
    @Autowired
    private SysFormService sysFormService;
    @Autowired
    private DBService dbService;

    public SysRoleServiceImpl(SysRoleRepository sysRoleRepository, SysRoleMapper sysRoleMapper) {
        this.sysRoleRepository = sysRoleRepository;
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * Save a sysRole.
     *
     * @param sysRoleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HashMap<String,Object> save(SysRoleDTO sysRoleDTO) {
        log.debug("Request to save SysRole : {}", sysRoleDTO);
        SysRole sysRole = sysRoleMapper.toEntity(sysRoleDTO);
        HashMap<String,Object> response = new HashMap<>();
        try {
            sysRole = sysRoleRepository.save(sysRole);
            User user = userService.getUserWithAuthorities();
            if (sysRoleDTO.getId() != null) {
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "修改角色：" + sysRoleDTO.getRoleName());
            } else {
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "新增角色：" + sysRoleDTO.getRoleName());
            }
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    /**
     *  Get all the sysRoles.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseResult findAll(HashMap<String,Object> params) {
        log.debug("Request to get all SysRoles");
        int pageNumber = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));;
        int pageSize = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>)params.get("formParams");
        String roleName = formParams.get("roleName") == null ? "" : formParams.get("roleName").toString();
        String roleNo = formParams.get("roleNo") == null ? "" : formParams.get("roleNo").toString();
        String delFlag = formParams.get("delFlag") == null ? "" : formParams.get("delFlag").toString();
        String description = formParams.get("description") == null ? "" : formParams.get("description").toString();

        String column = "select sr.id,sr.c_role_no role_no,sr.c_role_name role_name,sr.c_del_flag del_flag,sr.c_description description  ";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_role sr ");
        cond.append(" where 1=1 ");
        cond.append(" and sr.c_role_no <> '1001' ");
        if(StringUtils.isNotBlank(roleName)){
            cond.append(" and sr.c_role_name like ");
            cond.append("'%" + roleName + "%'");
        }
        if(StringUtils.isNotBlank(roleNo)){
            cond.append(" and sr.c_role_no like ");
            cond.append("'%" + roleNo + "%'");
        }
        if(StringUtils.isNotBlank(delFlag)){
            cond.append(" and sr.c_del_flag like ");
            cond.append("'%" + delFlag + "%'");
        }
        if(StringUtils.isNotBlank(description)){
            cond.append(" and sr.c_description like ");
            cond.append("'%" + description + "%'");
        }
        HashMap<String, Object> response = new HashMap<String,Object>();
        ResponseResult json = new ResponseResult();
        try {
            Page<SysRoleDTO> page = pageCommon.execPage(column,cond.toString(),pageNumber,pageSize,SysRoleDTO.class);
            response.put("list", page.getContent());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sys-role/getAllSysRoles");
            response.put("link", headers.get(HttpHeaders.LINK).get(0));
            response.put("total_count", headers.get("X-Total-Count").get(0));
            json.setData(response);
            json.setStatusCode(ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            json.setStatusCode(ResponseResult.FAIL_CODE);
        }
        return json;
    }

    /**
     *  Get one sysRole by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysRoleDTO findOne(Long id) {
        log.debug("Request to get SysRole : {}", id);
        SysRole sysRole = sysRoleRepository.findOne(id);
        return sysRoleMapper.toDto(sysRole);
    }

    /**
     *  Delete the  sysRole by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysRole : {}", id);
        SysRoleDTO sysRoleDTO = findOne(id);
        sysRoleRepository.delete(id);
        User user = userService.getUserWithAuthorities();
        sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "删除角色：" + sysRoleDTO.getRoleName());
    }

    //获取该角色授权的菜单
    public List<SysRoleMenuDTO> queryAllCheckMenuByRole(String roleIds) {
        StringBuilder column = new StringBuilder();
        column.append("select srm.sys_menu_id sys_menu_id");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_role_menu srm where 1=1 ");
        cond.append(" and srm.sys_role_id in (" + roleIds + ")");
        cond.append(" group by srm.sys_menu_id");
        return baseDao.findListBySql(column.toString(),cond.toString(), SysRoleMenuDTO.class);
    }

    //给角色分配权限，获取所有的菜单、表单、按钮集合，和已经被授权的菜单、表单、按钮，如果已授权则返回属性checked:true
    @Override
    public HashMap<String, Object> allocatePrivilegeMenuTree(HashMap<String, Object> params){
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            Long roleId = params.get("roleId") == null ? null : Long.valueOf(params.get("roleId").toString());
            params.put("visible","1");
            List<MenuTreeNode> menuTreeList = sysMenuService.getMenuTreeList(params);
            List<SysRoleMenuDTO> menuList = this.queryAllCheckMenuByRole(roleId.toString());
            for(MenuTreeNode node : menuTreeList) {
                for(SysRoleMenuDTO menu : menuList) {
                    if(node.getId().equals(menu.getSysMenuId())) {
                        node.setChecked(true);
                        break;
                    }
                }
            }
            menuTreeList = TreeCreator.constructTree(menuTreeList, Long.valueOf(0));
            response.put("menuList", menuTreeList);

            List<SysMenuDTO> sysMenuList = sysMenuService.getAllSysMenu(params);
            List<SysFormDTO> sysFormList = sysFormService.queryFormPage(null,"1");
            List<ListObject> formList = new ArrayList<ListObject>();
            List<ListObject> componentList = new ArrayList<ListObject>();

            if(sysMenuList!=null && sysMenuList.size()>0){
                for(SysMenuDTO menu : sysMenuList){
                    ListObject formObject = new ListObject();
                    formObject.setId(menu.getId());
                    List<SysFormDTO> formDTOList = this.getFormsByRoleAndMenu(roleId.toString(),menu.getId().toString());
                    formObject.setList(formDTOList);
                    formList.add(formObject);
                }
            }
            if(sysFormList!=null && sysFormList.size()>0){
                for(SysFormDTO form : sysFormList){
                    ListObject componentObject = new ListObject();
                    componentObject.setId(form.getId());
                    List<SysComponentDTO> componentDTOList = this.getComponentsByRoleAndForm(roleId.toString(),form.getId());
                    componentObject.setList(componentDTOList);
                    componentList.add(componentObject);
                }
            }
            response.put("formList", formList);
            response.put("componentList", componentList);
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    //根据角色以及菜单id，获取该菜单下已授权给角色的表单页面
    @Override
    public List<SysRoleFormDTO> queryFormPrivilege(Long roleId, Long menuId){
        StringBuilder column = new StringBuilder();
        column.append("select srf.sys_form_id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_role_form srf where srf.sys_role_id = " + roleId);
        cond.append(" and exists (select sf.id from t_sys_form sf where sf.i_menu_id = " + menuId);
        cond.append(" and sf.id = srf.sys_form_id)");
        return baseDao.findListBySql(column.toString(),cond.toString(), SysRoleFormDTO.class);
    }
    //根据角色以及菜单id，获取该菜单下已授权给角色的表单页面
    public List<SysRoleFormDTO> queryFormPrivilegebyRoles(String roleIds, String menuIds){
        StringBuilder column = new StringBuilder();
        column.append("select srf.sys_form_id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_role_form srf where srf.sys_role_id in ("  + roleIds +")");
        cond.append(" and exists (select sf.id from t_sys_form sf where sf.i_menu_id in (" + menuIds + ")");
        cond.append(" and sf.id = srf.sys_form_id)");
        cond.append(" group by srf.sys_form_id");
        return baseDao.findListBySql(column.toString(),cond.toString(), SysRoleFormDTO.class);
    }

    //角色和菜单id，获取所有表单以及被勾选的表单
    @Override
    public List<SysFormDTO> getFormsByRoleAndMenu(String roleIds, String menuIds){
        List<SysFormDTO> formList = sysFormService.queryFormPage(menuIds,"1");
        List<SysRoleFormDTO> checkedformIdlist = this.queryFormPrivilegebyRoles(roleIds, menuIds);
        for(SysFormDTO form : formList) {
            for(SysRoleFormDTO roleFormDTO : checkedformIdlist) {
                if(form.getId().equals(roleFormDTO.getSysFormId())) {
                    form.setChecked(true);
                    break;
                }
            }
        }
        return formList;
    }

    //根据角色，表单id，获取他下面所授权的控件
    @Override
    public List<SysRoleComponentDTO> queryComponentPrivilege(Long roleId, Long formId){
        StringBuilder column = new StringBuilder();
        column.append("select src.sys_component_id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_role_component src where src.sys_role_id = " + roleId);
        cond.append(" and exists (select sc.id from t_sys_component sc where sc.i_form_id = " + formId);
        cond.append(" and sc.id = src.sys_component_id)");
        return baseDao.findListBySql(column.toString(),cond.toString(), SysRoleComponentDTO.class);
    }
    //根据角色，表单id，获取他下面所授权的控件
    public List<SysRoleComponentDTO> queryComponentPrivilegeByRoles(String roleIds, Long formId){
        StringBuilder column = new StringBuilder();
        column.append("select src.sys_component_id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_role_component src where src.sys_role_id in (" + roleIds + ")");
        cond.append(" and exists (select sc.id from t_sys_component sc where sc.i_form_id = " + formId);
        cond.append(" and sc.id = src.sys_component_id)");
        cond.append(" group by src.sys_component_id ");
        return baseDao.findListBySql(column.toString(),cond.toString(), SysRoleComponentDTO.class);
    }

    //获取所有控件以及被勾选的控件按钮
    @Override
    public List<SysComponentDTO> getComponentsByRoleAndForm(String roleIds, Long formId){
        List<SysComponentDTO> componentList = sysComponentService.queryComponentPage(formId,"1");
        List<SysRoleComponentDTO> checkedComponentIdlist = this.queryComponentPrivilegeByRoles(roleIds, formId);
        for(SysComponentDTO component : componentList) {
            for(SysRoleComponentDTO roleComponentDTO : checkedComponentIdlist) {
                if(component.getId().equals(roleComponentDTO.getSysComponentId())) {
                    component.setChecked(true);
                    break;
                }
            }
        }
        return componentList;
    }

    //删除角色时，同时删除所有相关的权限信息
    @Override
    public void deletePrivilegeByRole(Long roleId){
        sysRoleMenuRepository.deleteMenuPrivilegeByRole(roleId);
        sysRoleFormRepository.deleteFormPrivilegeByRole(roleId);
        sysRoleComponentRepository.deleteComponentPrivilegeByRole(roleId);
        sysUserRoleRepository.deleteUserPrivilegeByRole(roleId);
    }
    //给角色重新授权时，先删除已授权的信息
    public void deleteExistingPrivilegeByRole(Long roleId){
        sysRoleMenuRepository.deleteMenuPrivilegeByRole(roleId);
        sysRoleFormRepository.deleteFormPrivilegeByRole(roleId);
        sysRoleComponentRepository.deleteComponentPrivilegeByRole(roleId);
    }

    //根据页面选中的菜单，表单，控件，保存用户权限
    @Override
    public HashMap<String,Object> saveRolePrivilege(HashMap<String, Object> params){
        HashMap<String,Object> response = new HashMap<>();
        try {
            List menuList = params.get("menuList") == null ? null : (ArrayList<Object>)params.get("menuList");
            List formList = params.get("formList") == null ? null : (ArrayList<Object>)params.get("formList");
            List componentList = params.get("componentList") == null ? null : (ArrayList<Object>)params.get("componentList");
            Long roleId = params.get("roleId") == null ? null : Long.valueOf(params.get("roleId").toString());
            //保存角色权限之前，先删除旧的权限
            this.deleteExistingPrivilegeByRole(roleId);

            if(menuList !=null && menuList.size()>0){
                SysRoleMenuDTO sysRoleMenuDTO = new SysRoleMenuDTO();
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                for(Object o : menuList){
                    sysRoleMenuDTO.setSysMenuId(Long.valueOf(o.toString()));
                    sysRoleMenuDTO.setSysRoleId(roleId);
                    sysRoleMenu = sysRoleMenuMapper.toEntity(sysRoleMenuDTO);
                    sysRoleMenuRepository.save(sysRoleMenu);
                }
            }

            if(formList !=null && formList.size()>0){
                SysRoleForm sysRoleForm = new SysRoleForm();
                SysRoleFormDTO sysRoleFormDTO = new SysRoleFormDTO();
                for(Object o : formList){
                    sysRoleFormDTO.setSysFormId(Long.valueOf(o.toString()));
                    sysRoleFormDTO.setSysRoleId(roleId);
                    sysRoleForm = sysRoleFormMapper.toEntity(sysRoleFormDTO);
                    sysRoleFormRepository.save(sysRoleForm);
                }
            }

            if(componentList !=null && componentList.size()>0){
                SysRoleComponent sysRoleComponent = new SysRoleComponent();
                SysRoleComponentDTO sysRoleComponentDTO = new SysRoleComponentDTO();
                for(Object o : componentList){
                    sysRoleComponentDTO.setSysComponentId(Long.valueOf(o.toString()));
                    sysRoleComponentDTO.setSysRoleId(roleId);
                    sysRoleComponent = sysRoleComponentMapper.toEntity(sysRoleComponentDTO);
                    sysRoleComponentRepository.save(sysRoleComponent);
                }
            }

            User user = userService.getUserWithAuthorities();
            SysRoleDTO sysRoleDTO = findOne(roleId);
            sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "对角色：" + sysRoleDTO.getRoleName() + " 进行菜单授权");

            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    //启用禁用角色
    @Override
    public HashMap<String,Object> enableSysRole(HashMap<String, Object> params){
        Long roleId = params.get("roleId") == null ? null : Long.valueOf(params.get("roleId").toString());
        String delFlag = params.get("delFlag") == null ? null :params.get("delFlag").toString();
        HashMap<String,Object> response = new HashMap<>();
        try {
            sysRoleRepository.enableSysRole(delFlag,roleId);
            User user = userService.getUserWithAuthorities();
            SysRoleDTO sysRoleDTO = findOne(roleId);
            if("1".equals(delFlag)){
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "启用角色：" + sysRoleDTO.getRoleName());
            }else{
                sysOperateLogService.addLog(user.getLogin(), user.getFirstName(), "2", new Date(), "禁用角色：" + sysRoleDTO.getRoleName());
            }
            response.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            response.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return response;
    }

    //获取当前登录用户，被授权的表单和控件
    @Override
    public HashMap<String, Object> getFormRightList(HashMap<String, Object> params){
        HashMap<String,Object> map = new HashMap<String,Object>();
        try {
            String menuIds = params.get("menuId") == null ? null : params.get("menuId").toString();
            User user = userService.getUserWithAuthorities();
            List<SysUserRoleDTO> roleIdList = sysUserRoleService.getRoleIdsByUser(user.getId());
            List<SysFormDTO> formList = new ArrayList<>();
            List<ListObject> componentList = new ArrayList<ListObject>();

            List<SysFormDTO> tempformList = null;
            if(roleIdList != null && !roleIdList.isEmpty()){
                StringBuffer roleIds = new StringBuffer();

                for(SysUserRoleDTO sysUserRoleDTO : roleIdList){
                    Long roleId =sysUserRoleDTO.getSysRolesId();
                    if("".equals(roleIds.toString())){
                        roleIds.append(roleId);
                    }else{
                        roleIds.append("," +roleId);
                    }
                }
                tempformList = this.getFormsByRoleAndMenu(roleIds.toString(),menuIds);
                if(tempformList!=null && !tempformList.isEmpty()){
                    for(SysFormDTO form : tempformList){
                        formList.add(form);
                        ListObject componentObject = new ListObject();
                        componentObject.setId(form.getId());
                        List<SysComponentDTO> componentDTOList = this.getComponentsByRoleAndForm(roleIds.toString(),form.getId());
                        componentObject.setList(componentDTOList);
                        componentObject.setEnglishName(form.getEnglishName());
                        componentList.add(componentObject);
                    }
                }
            }
            map.put("formList",formList);
            map.put("componentList",componentList);
            map.put("statusCode", ResponseResult.SUCCESS_CODE);
        }catch (Exception e){
            map.put("statusCode", ResponseResult.FAIL_CODE);
        }
        return map;
    }
    //获取菜单树形结构
    @Override
    public List<MenuTreeNode> getMenuRightList(HashMap<String,Object> params){
        List<MenuTreeNode> menuTreeList = sysMenuService.getMenuTreeList(params);
        User user = userService.getUserWithAuthorities();
        List<SysUserRoleDTO> roleIdList = sysUserRoleService.getRoleIdsByUser(user.getId());
        List<SysRoleMenuDTO> menuList = null;
        if(roleIdList != null && roleIdList.size()>0) {
            StringBuffer roleIds = new StringBuffer();

            for(SysUserRoleDTO sysUserRoleDTO : roleIdList){
                Long roleId =sysUserRoleDTO.getSysRolesId();
                if(roleIds.toString().equals("")){
                    roleIds.append(roleId);
                }else{
                    roleIds.append("," +roleId);
                }
            }

            menuList = this.queryAllCheckMenuByRole(roleIds.toString());
        }
        if(menuList != null && menuList.size()>0){
            for(MenuTreeNode node : menuTreeList) {
                for(SysRoleMenuDTO menu : menuList) {
                    if(node.getId().equals(menu.getSysMenuId())) {
                        node.setChecked(true);
                        break;
                    }
                }
            }
        }
        return menuTreeList;
    }
    //判断是否存在重复角色编号
    @Override
    public int checkRoleNo(HashMap<String, Object> params){
        String roleNo = params.get("roleNo") == null ? "" : params.get("roleNo").toString();
        int count = 0;
        if(StringUtils.isNotBlank(roleNo)){
            count = sysRoleRepository.checkRoleNo(roleNo);
        }
        return count;
    }

    @Override
    public SysRole querySysRoleByNo(String roleNo) {
        StringBuffer sql = new StringBuffer();
        sql.append("select r.* from t_sys_role r where r.c_role_no = '").append(roleNo).append("'");
        List<SysRole> list = baseDao.findListBySql(sql.toString(), "", SysRole.class);
        if(list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
