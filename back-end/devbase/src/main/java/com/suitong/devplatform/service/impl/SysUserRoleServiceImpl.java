package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.dao.BaseDao;
import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.domain.SysUserRole;
import com.suitong.devplatform.domain.User;
import com.suitong.devplatform.repository.SysUserRoleRepository;
import com.suitong.devplatform.service.SysOperateLogService;
import com.suitong.devplatform.service.SysRoleService;
import com.suitong.devplatform.service.SysUserRoleService;
import com.suitong.devplatform.service.UserService;
import com.suitong.devplatform.service.dto.SysRoleDTO;
import com.suitong.devplatform.service.dto.SysUserRoleDTO;
import com.suitong.devplatform.service.dto.UserDTO;
import com.suitong.devplatform.service.mapper.SysUserRoleMapper;
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

    @Override
    public Page<UserDTO> findAll(HashMap<String,Object> params){
        int pageNumber = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));;
        int pageSize = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>)params.get("formParams");
        String roleId = formParams.get("roleId") == null ? "" : formParams.get("roleId").toString();
        String userName = formParams.get("userName") == null ? "" : formParams.get("userName").toString();
        String column = "select ju.id as id, ju.first_name as first_name, ju.c_department as department," +
                "s.sys_users_id as sys_users_Id";
        StringBuffer cond = new StringBuffer();
        cond.append(" from jhi_user ju");
        cond.append(" left join (select sur.* from t_sys_user_role sur where sur.sys_roles_id = '"+ roleId +"') s on s.sys_users_id = ju.id");
        cond.append(" where 1=1");
        cond.append(" and c_del_flag = '1'");
        cond.append(" and activated = '1'");
        if(StringUtils.isNotBlank(userName)){
            cond.append(" and ju.first_name like ");
            cond.append("'%" + userName + "%'");
        }
        Page<UserDTO> page = pageCommon.execPage(column, cond.toString(),pageNumber, pageSize, UserDTO.class);
        return page;
    }

    @Override
    public void saveOrDeleteUserRole(HashMap<String,Object> params){

        Long sysRolesId = params.get("sysRolesId") == null? null : Long.parseLong(params.get("sysRolesId").toString());
        List<Integer> addSysUsersId = params.get("addSysUsersId") == null? null : (List<Integer>)params.get("addSysUsersId");
        List<Integer> delSysUsersId = params.get("delSysUsersId") == null? null : (List<Integer>)params.get("delSysUsersId");
        if(sysRolesId != null){
            if(delSysUsersId != null){
                for(int i=0;i<delSysUsersId.size();i++){
                    Long delId = delSysUsersId.get(i).longValue();
                    if(delId == null){
                        continue;
                    }
                    SysUserRoleDTO sysUserRoleDTO = this.findSysUserRoleByUserIdAndRoleId(delId, sysRolesId);
                    if(sysUserRoleDTO != null){
                        sysUserRoleRepository.delete(sysUserRoleDTO.getId());
                    }
                }
            }

            if(addSysUsersId != null){
                for(int i=0;i<addSysUsersId.size();i++){
                    Long addId = addSysUsersId.get(i).longValue();
                    if(addId == null){
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

    @Override
    public SysUserRoleDTO findSysUserRoleByUserIdAndRoleId(Long userId, Long roleId){

        String column = "select sur.id as id, sur.sys_users_id as sys_users_id, sur.sys_roles_id as sys_roles_id";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_user_role sur");
        cond.append(" where 1=1");
        cond.append(" and sur.sys_users_id = '" + userId + "'");
        cond.append(" and sur.sys_roles_id = '" + roleId + "'");
        List<SysUserRoleDTO> list  = baseDao.findListBySql(column, cond.toString(), SysUserRoleDTO.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
    @Override
    public List<SysUserRoleDTO> getRoleIdsByUser(Long userId){
        //List<SysUserRole> list  = sysUserRoleRepository.getRoleIdsByUser(userId);
        StringBuffer column = new StringBuffer();
        column.append("select sur.id,sur.sys_roles_id from t_sys_user_role sur ");
        column.append(" left join t_sys_role sr on sur.sys_roles_id=sr.id ");
        StringBuffer cond = new StringBuffer();
        cond.append(" where sur.sys_users_id =" + userId);
        cond.append(" and sr.c_del_flag=1" );//角色禁用后，则不能看到相关的权限   20170914
        List<SysUserRoleDTO> list = baseDao.findListBySql(column.toString(),cond.toString(),SysUserRoleDTO.class);
        return list;
    }

    @Override
    public List<SysRoleDTO> getRoleByUserId(Long userId, String type){
        StringBuffer column = new StringBuffer();
        StringBuffer cond = new StringBuffer();
        column.append("select sr.id,sr.c_role_name as role_name,sr.c_role_no as role_no");
        cond.append(" from t_sys_role sr");
        cond.append(" left join (select sur.* from t_sys_user_role sur where sur.sys_users_id = '"+ userId +"') s on sr.id = s.sys_roles_id");
        cond.append(" where 1=1 and sr.c_del_flag=1 ");
        if("Y".equals(type)){//用户已选择类型
            cond.append(" and s.id is not null");
        }else if ("N".equals(type)){
            cond.append(" and s.id is null");
        }
        List<SysRoleDTO> list = baseDao.findListBySql(column.toString(),cond.toString(),SysRoleDTO.class);
        return list;
    }

    @Override
    public void saveUserRole(HashMap<String,Object> params){
        Long userId = params.get("userId") == null? null : Long.parseLong(params.get("userId").toString());
        List<Integer> roleIds = params.get("roleIds") == null? null : (List<Integer>)params.get("roleIds");
        if(userId != null){
            //删除所有已选择的
            sysUserRoleRepository.deleteUserRoleByUserId(userId);
            //this.deleteUserRoleByUserId(userId);
            if(roleIds != null){
                //重新添加
                for(int i=0;i<roleIds.size();i++){
                    Long roleId = roleIds.get(i).longValue();
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

    public void deleteUserRoleByUserId(Long userId){
        StringBuffer sql = new StringBuffer();
        sql.append("delete from t_sys_user_role where sys_users_id = '"+ userId +"'");
        jdbcTemplate.execute(sql.toString());
    }
}
