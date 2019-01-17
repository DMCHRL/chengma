package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.UnionPayInfo;
import com.chengma.devplatform.service.dto.UnionPayInfoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnionPayInfoMapper extends EntityMapper<UnionPayInfoDTO, UnionPayInfo> {
    
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
