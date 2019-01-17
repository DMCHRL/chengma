package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TlbAccountControl;
import com.chengma.devplatform.service.dto.TlbAccountControlDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbAccountControlMapper extends EntityMapper<TlbAccountControlDTO, TlbAccountControl> {
    
    
   /* default BindBank fromId(String id) {
        if (id == null) {
            return null;
        }
        BindBank bindBank = new BindBank();
        bindBank.setId(id);
        return bindBank;
    }*/
}
