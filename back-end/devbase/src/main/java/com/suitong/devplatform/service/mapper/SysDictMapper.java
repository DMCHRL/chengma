package com.suitong.devplatform.service.mapper;


import com.suitong.devplatform.domain.SysDict;
import com.suitong.devplatform.service.dto.SysDictDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysDict and its DTO SysDictDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysDictMapper extends EntityMapper <SysDictDTO, SysDict> {
    
    
    default SysDict fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysDict sysDict = new SysDict();
        sysDict.setId(id);
        return sysDict;
    }
}
