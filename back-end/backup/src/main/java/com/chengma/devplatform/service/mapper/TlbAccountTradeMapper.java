package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TlbAccountTrade;
import com.chengma.devplatform.service.dto.TlbAccountTradeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TlbAccountTradeMapper extends EntityMapper<TlbAccountTradeDTO, TlbAccountTrade> {

}
