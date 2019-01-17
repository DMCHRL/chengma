package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.domain.WxOrder;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.dto.WxOrderDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WxOrderMapper extends EntityMapper<WxOrderDTO, WxOrder> {
    
    
   /* default BindBank fromId(String id) {
        if (id == null) {
            return null;
        }
        BindBank bindBank = new BindBank();
        bindBank.setId(id);
        return bindBank;
    }*/
}
