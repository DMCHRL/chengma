package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppVideoMapper extends EntityMapper<HppVideoDTO, HppVideo> {

}
