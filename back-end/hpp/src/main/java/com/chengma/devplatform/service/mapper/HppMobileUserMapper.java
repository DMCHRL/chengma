package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppMobileUser;
import com.chengma.devplatform.domain.HppMobileValidate;
import com.chengma.devplatform.service.dto.HppMobileUserDTO;
import com.chengma.devplatform.service.dto.HppMobileValidateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity MobileValidate and its DTO MobileValidateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HppMobileUserMapper extends EntityMapper <HppMobileUserDTO, HppMobileUser> {
    
}
