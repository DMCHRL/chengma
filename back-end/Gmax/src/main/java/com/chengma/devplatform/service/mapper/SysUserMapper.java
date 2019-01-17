package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.SysUser;
import com.chengma.devplatform.service.dto.SysUserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysUser and its DTO SysUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysUserMapper extends EntityMapper <SysUserDTO, SysUser> {
    
//    default SysUser fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysUser sysUser = new SysUser();
//        sysUser.setId(id);
//        return sysUser;
//    }
}
