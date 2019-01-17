package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TranInfo;
import com.chengma.devplatform.service.dto.TranInfoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TranInfoMapper extends EntityMapper<TranInfoDTO, TranInfo> {
    
  /*
    default TranInfo fromId(String id) {
        if (id == null) {
            return null;
        }
        TranInfo tranInfo = new TranInfo();
        tranInfo.setId(id);
        return tranInfo;
    }*/
}
