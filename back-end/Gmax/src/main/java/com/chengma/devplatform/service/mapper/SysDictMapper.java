package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.SysDict;
import com.chengma.devplatform.service.dto.SysDictDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysDict and its DTO SysDictDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysDictMapper extends EntityMapper <SysDictDTO, SysDict> {
    
//    default SysDict fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysDict sysDict = new SysDict();
//        sysDict.setId(id);
//        return sysDict;
//    }
}
