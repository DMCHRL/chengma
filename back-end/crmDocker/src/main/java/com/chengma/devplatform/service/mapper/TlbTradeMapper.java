package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.domain.TlbTrade;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.dto.TlbTradeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbTradeMapper extends EntityMapper<TlbTradeDTO, TlbTrade> {
    
    
   /* default BindBank fromId(String id) {
        if (id == null) {
            return null;
        }
        BindBank bindBank = new BindBank();
        bindBank.setId(id);
        return bindBank;
    }*/
}
