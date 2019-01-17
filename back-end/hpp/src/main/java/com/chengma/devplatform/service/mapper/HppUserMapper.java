package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.HppUser;
import com.chengma.devplatform.service.dto.HppUserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called HppUserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HppUserMapper extends EntityMapper<HppUserDTO, HppUser> {

}
