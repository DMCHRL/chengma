package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.SysUserRole;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.SysUserRoleRepository;
import com.chengma.devplatform.service.SysOperateLogService;
import com.chengma.devplatform.service.SysRoleService;
import com.chengma.devplatform.service.SysUserRoleService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.SysRoleDTO;
import com.chengma.devplatform.service.dto.SysUserRoleDTO;
import com.chengma.devplatform.service.dto.UserDTO;
import com.chengma.devplatform.service.mapper.SysUserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/8/29.
 *
 * @author administrator
 */
@Service
@Transactional
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private final Logger log = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    private final SysUserRoleRepository sysUserRoleRepository;

    private final SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private SysOperateLogService sysOperateLogService;
    @Autowired
    private SysRoleService sysRoleService;

    public SysUserRoleServiceImpl(SysUserRoleRepository sysUserRoleRepository, SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRoleRepository = sysUserRoleRepository;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    /**
     * 获取用户列表
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{roleId:"",userName:""}}
     * @return Page
     */
    @Override
    public Page<UserDTO> findAll(HashMap<String, Object> params) {
        int pageNumber = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        int pageSize = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));

        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");
        String roleId = formParams.get("roleId") == null ? "" : formParams.get("roleId").toString();
        String userName = formParams.get("userName") == null ? "" : formParams.get("userName").toString();

        String column = "select ju.id as id, ju.first_name as first_name, ju.c_department as department," +
                "s.c_sys_users_id as sys_users_Id";
        StringBuffer cond = new StringBuffer();
        cond.append(" from jhi_user ju");
        cond.append(" left join (select sur.* from t_sys_user_role sur where sur.c_sys_roles_id = '" + roleId + "') s on s.c_sys_users_id = ju.id");
        cond.append(" where 1=1");
        cond.append(" and c_del_flag = '1'");
        cond.append(" and activated = '1'");
        if (StringUtils.isNotBlank(userName)) {
            cond.append(" and ju.first_name like ");
            cond.append("'%" + userName + "%'");
        }
        Page<UserDTO> page = pageCommon.execPage(column, cond.toString(), pageNumber, pageSize, UserDTO.class);
        return page;
    }

    /**
     * 保存用户角色关系
     *
     * @param params 参数：{sysRolesId:"",addSysUsersId:{},delSysUsersId:{}}
     */
    @Override
    public void saveOrDeleteUserRole(HashMap<String, Object> params) {

        String sysRolesId = params.get("sysRolesId") == null ? "" : params.get("sysRolesId").toString();
        List<String> addSysUsersId = params.get("addSysUsersId") == null ? null : (List<String>) params.get("addSysUsersId");
        List<String> delSysUsersId = params.get("delSysUsersId") == null ? null : (List<String>) params.get("delSysUsersId");
        if (sysRolesId != null) {
            if (delSysUsersId != null) {
                for (int i = 0; i < delSysUsersId.size(); i++) {
                    String delId = delSysUsersId.get(i);
                    if (delId == null) {
                        continue;
                    }
                    SysUserRoleDTO sysUserRoleDTO = this.findSysUserRoleByUserIdAndRoleId(delId, sysRolesId);
                    if (sysUserRoleDTO != null) {
                        sysUserRoleRepository.delete(sysUserRoleDTO.getId());
                    }
                }
            }

            if (addSysUsersId != null) {
                for (int i = 0; i < addSysUsersId.size(); i++) {
                    String addId = addSysUsersId.get(i);
                    if (addId == null) {
                        continue;
                    }
                    SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
                    sysUserRoleDTO.setSysRolesId(sysRolesId);
                    sysUserRoleDTO.setSysUsersId(addId);
                    SysUserRole sysUserRole = sysUserRoleMapper.toEntity(sysUserRoleDTO);
                    sysUserRoleRepository.save(sysUserRole);
                }
            }
            SysRoleDTO sysRoleDTO = sysRoleService.findOne(sysRolesId);
            User currentUser = userService.getUserWithAuthorities();//当前用户
            sysOperateLogService.addLog(currentUser.getLogin(), currentUser.getFirstName(), "2", new Date(), "对角色：" + sysRoleDTO.getRoleName() + " 进行分配用户");
        }


    }

    /**
     * 查找用户角色关系信息
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return SysUserRoleDTO
     */
    @Override
    public SysUserRoleDTO findSysUserRoleByUserIdAndRoleId(String userId, String roleId) {

        String column = "select sur.c_id as id, sur.c_sys_users_id as sys_users_id, sur.c_sys_roles_id as sys_roles_id";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_user_role sur");
        cond.append(" where 1=1");
        cond.append(" and sur.c_sys_users_id = '" + userId + "' ");
        cond.append(" and sur.c_sys_roles_id = '" + roleId + "' ");
        List<SysUserRoleDTO> list = baseDao.findListBySql(column, cond.toString(), SysUserRoleDTO.class);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据用户查找用户角色关系信息
     *
     * @param userId 用户id
     * @return List
     */
    @Override
    public List<SysUserRoleDTO> getRoleIdsByUser(String userId) {
        StringBuffer column = new StringBuffer();
        column.append("select sur.c_id id,sur.c_sys_roles_id sys_roles_id from t_sys_user_role sur ");
        column.append(" left join t_sys_role sr on sur.c_sys_roles_id=sr.c_id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" where sur.c_sys_users_id = '" + userId + "' ");
        cond.append(" and sr.c_del_flag=1");//角色禁用后，则不能看到相关的权限   20170914
        List<SysUserRoleDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysUserRoleDTO.class);
        return list;
    }

    /**
     * 根据用户查找角色列表
     *
     * @param userId 用户id
     * @param type   类型：Y用户属于的角色，N用户不属于的角色
     * @return List
     */
    @Override
    public List<SysRoleDTO> getRoleByUserId(String userId, String type) {
        StringBuffer column = new StringBuffer();
        StringBuffer cond = new StringBuffer();
        column.append("select sr.c_id id,sr.c_role_name as role_name,sr.c_role_no as role_no");
        cond.append(" from t_sys_role sr");
        cond.append(" left join (select sur.* from t_sys_user_role sur where sur.c_sys_users_id = '" + userId + "') s on sr.c_id = s.c_sys_roles_id");
        cond.append(" where 1=1 and sr.c_del_flag=1 ");
        if ("Y".equals(type)) {//用户已选择类型
            cond.append(" and s.c_id is not null");
        } else if ("N".equals(type)) {
            cond.append(" and s.c_id is null");
        }
        List<SysRoleDTO> list = baseDao.findListBySql(column.toString(), cond.toString(), SysRoleDTO.class);
        return list;
    }

    /**
     * 保存用户角色关系
     *
     * @param params 参数：{userId:"",roleIds:{}}
     */
    @Override
    public void saveUserRole(HashMap<String, Object> params) {
        String userId = params.get("userId") == null ? "" : params.get("userId").toString();
        List<String> roleIds = params.get("roleIds") == null ? null : (List<String>) params.get("roleIds");
        if (userId != null) {
            //删除所有已选择的
            sysUserRoleRepository.deleteUserRoleByUserId(userId);
            if (roleIds != null) {
                //重新添加
                for (int i = 0; i < roleIds.size(); i++) {
                    String roleId = roleIds.get(i);
                    SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO();
                    sysUserRoleDTO.setSysUsersId(userId);
                    sysUserRoleDTO.setSysRolesId(roleId);
                    SysUserRole sysUserRole = sysUserRoleMapper.toEntity(sysUserRoleDTO);
                    sysUserRoleRepository.save(sysUserRole);
                }

                UserDTO userDTO = userService.findOne(userId);
                User currentUser = userService.getUserWithAuthorities();//当前用户
                sysOperateLogService.addLog(currentUser.getLogin(), currentUser.getFirstName(), "2", new Date(), "对用户：" + userDTO.getLogin() + " 进行选择角色");
            }
        }
    }

    public void deleteUserRoleByUserId(Long userId) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from t_sys_user_role where c_sys_users_id = '" + userId + "' ");
        jdbcTemplate.execute(sql.toString());
    }
}
