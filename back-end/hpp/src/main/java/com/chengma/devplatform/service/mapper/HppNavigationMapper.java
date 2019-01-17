package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.domain.HppNavigation;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.HppNavigationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppNavigationMapper extends EntityMapper <HppNavigationDTO, HppNavigation> {

}
