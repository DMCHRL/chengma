package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TlbFundApply;
import com.chengma.devplatform.service.dto.TlbFundApplyDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbFundApplyMapper extends EntityMapper<TlbFundApplyDTO, TlbFundApply> {
    
    
   /* default TlbFundApply fromId(String id) {
        if (id == null) {
            return null;
        }
        TlbFundApply bindBank = new TlbFundApply();
        bindBank.setId(id);
        return bindBank;
    }*/
}
