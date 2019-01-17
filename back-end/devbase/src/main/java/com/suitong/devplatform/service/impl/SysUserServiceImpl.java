package com.suitong.devplatform.service.impl;

import com.suitong.devplatform.common.page.PageCommon;
import com.suitong.devplatform.domain.SysUser;
import com.suitong.devplatform.domain.SysRole;
import com.suitong.devplatform.repository.SysUserRepository;
import com.suitong.devplatform.service.SysUserService;
import com.suitong.devplatform.service.dto.SysUserDTO;
import com.suitong.devplatform.service.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Service Implementation for managing SysUser.
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    private final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private final SysUserRepository sysUserRepository;

    private final SysUserMapper sysUserMapper;

    @Autowired
    private PageCommon pageCommon;

    /*@Autowired
    private SysRoleService sysRoleService;*/

    public SysUserServiceImpl(SysUserRepository sysUserRepository, SysUserMapper sysUserMapper) {
        this.sysUserRepository = sysUserRepository;
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * Save a SysUser.
     *
     * @param SysUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SysUserDTO save(SysUserDTO SysUserDTO) {
        log.debug("Request to save SysUser : {}", SysUserDTO);
        SysUser SysUser = sysUserMapper.toEntity(SysUserDTO);
        SysUser = sysUserRepository.save(SysUser);
        return sysUserMapper.toDto(SysUser);
    }

    /**
     *  Get all the sysUsers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysUserDTO> findAll(HashMap<String,Object> params) {
        log.debug("Request to get all sysUsers");
        int pageNumber = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));;
        int pageSize = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>)params.get("formParams");
        //String username = formParams.get("username") == null ? "" : formParams.get("username").toString();

        String column = "select su.c_username username,su.c_telephone telephone";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_user su ");
        cond.append(" where 1=1 ");
        /*if(StringUtils.isNotBlank(username)){
            cond.append(" and su.c_username like ");
            cond.append("'%" + username + "%'");
        }*/
        Page<SysUserDTO> page = pageCommon.execPage(column,cond.toString(),pageNumber,pageSize,SysUserDTO.class);
        return page;
    }

    /**
     *  Get one SysUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysUserDTO findOne(Long id) {
        log.debug("Request to get SysUser : {}", id);
        SysUser SysUser = sysUserRepository.findOne(id);
        return sysUserMapper.toDto(SysUser);
    }

    /**
     *  Delete the  SysUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysUser : {}", id);
        sysUserRepository.delete(id);
    }

    @Override
    public Map<String, Collection<SysRole>> getSysUserRoles(HashMap<String, Object> params){
        Long userId = params.get("userId") == null ? null : Long.valueOf(params.get("userId").toString());
       /* Collection assignedRoleList = sysRoleService.getAssignedRoles(userId);
        Collection<SysRole> assignedRoleObjects = new ArrayList<SysRole>();
        constructRoleObjects(assignedRoleList, assignedRoleObjects);

        Collection unassignedRoleList = sysRoleService.getALLUnassignedRoles(userId);
        Collection<SysRole> unassignedRoleObjects = new ArrayList<SysRole>();
        constructRoleObjects(unassignedRoleList, unassignedRoleObjects);

        Map<String, Collection<SysRole>> userRoles = new HashMap<String, Collection<SysRole>>();
        userRoles.put("assignedRoleObjects", assignedRoleObjects);
        userRoles.put("unassignedRoleObjects", unassignedRoleObjects);*/
        return null;
    }

    private void constructRoleObjects(Collection roleList, Collection<SysRole> roleObjects) {
        if (roleList != null) {
            for (Iterator it = roleList.iterator(); it.hasNext();) {
                Object[] obj = (Object[]) it.next();
                Long id = (Long) obj[0];
                String roleName = (String) obj[1];

                SysRole role = new SysRole();
                role.setId(id);
                role.setRoleName(roleName);

                roleObjects.add(role);
            }
        }
    }
}
