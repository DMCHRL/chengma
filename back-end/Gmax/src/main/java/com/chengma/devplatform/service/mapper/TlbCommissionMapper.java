package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TlbCommission;
import com.chengma.devplatform.service.dto.TlbCommissionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbCommissionMapper extends EntityMapper<TlbCommissionDTO, TlbCommission> {
    
    
   /* default BindBank fromId(String id) {
        if (id == null) {
            return null;
        }
        BindBank bindBank = new BindBank();
        bindBank.setId(id);
        return bindBank;
    }*/
}
