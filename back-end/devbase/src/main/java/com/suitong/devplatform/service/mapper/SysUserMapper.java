package com.suitong.devplatform.service.mapper;

import com.suitong.devplatform.domain.SysUser;
import com.suitong.devplatform.service.dto.SysUserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysUser and its DTO SysUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysUserMapper extends EntityMapper <SysUserDTO, SysUser> {
    
    
    default SysUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        return sysUser;
    }
}
