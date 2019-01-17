package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TlbAccountData;
import com.chengma.devplatform.service.dto.TlbAccountDataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TlbAccountDataMapper extends EntityMapper<TlbAccountDataDTO, TlbAccountData> {

}
