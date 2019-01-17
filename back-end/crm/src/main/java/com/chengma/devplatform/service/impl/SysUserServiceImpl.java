package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.domain.SysRole;
import com.chengma.devplatform.domain.SysUser;
import com.chengma.devplatform.repository.SysUserRepository;
import com.chengma.devplatform.service.SysUserService;
import com.chengma.devplatform.service.dto.SysUserDTO;
import com.chengma.devplatform.service.mapper.SysUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Service Implementation for managing SysUser.
 *
 * @author administrator
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    private final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private final SysUserRepository sysUserRepository;

    private final SysUserMapper sysUserMapper;

    @Autowired
    private PageCommon pageCommon;

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
     * 获取所有的账户
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{}}
     * @return Page
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SysUserDTO> findAll(HashMap<String, Object> params) {
        log.debug("Request to get all sysUsers");
        int pageNumber = (params.get("page_number") == null ? 1 : Integer.valueOf(params.get("page_number").toString()));
        ;
        int pageSize = (params.get("page_size") == null ? 10 : Integer.valueOf(params.get("page_size").toString()));
        HashMap<String, Object> formParams = (HashMap<String, Object>) params.get("formParams");

        String column = "select su.c_username username,su.c_telephone telephone";
        StringBuffer cond = new StringBuffer();
        cond.append(" from t_sys_user su ");
        cond.append(" where 1=1 ");
        Page<SysUserDTO> page = pageCommon.execPage(column, cond.toString(), pageNumber, pageSize, SysUserDTO.class);
        return page;
    }

    /**
     * Get one SysUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysUserDTO findOne(String id) {
        log.debug("Request to get SysUser : {}", id);
        SysUser SysUser = sysUserRepository.findOne(id);
        return sysUserMapper.toDto(SysUser);
    }

    /**
     * Delete the  SysUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SysUser : {}", id);
        sysUserRepository.delete(id);
    }

    /**
     * 获取用户角色
     *
     * @param params 参数：{userId:""}
     * @return Map
     */
    @Override
    public Map<String, Collection<SysRole>> getSysUserRoles(HashMap<String, Object> params) {
        String userId = params.get("userId") == null ? "" : params.get("userId").toString();
        return null;
    }

    private void constructRoleObjects(Collection roleList, Collection<SysRole> roleObjects) {
        if (roleList != null) {
            for (Iterator it = roleList.iterator(); it.hasNext(); ) {
                Object[] obj = (Object[]) it.next();
                String id = obj[0].toString();
                String roleName = (String) obj[1];

                SysRole role = new SysRole();
                role.setId(id);
                role.setRoleName(roleName);

                roleObjects.add(role);
            }
        }
    }
}
