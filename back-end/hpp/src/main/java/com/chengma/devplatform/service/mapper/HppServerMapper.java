package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.domain.HppServer;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.HppServerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppServerMapper extends EntityMapper <HppServerDTO, HppServer> {

}
