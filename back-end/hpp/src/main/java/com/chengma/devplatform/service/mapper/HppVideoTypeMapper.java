package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppVideo;
import com.chengma.devplatform.domain.HppVideoType;
import com.chengma.devplatform.service.dto.HppVideoDTO;
import com.chengma.devplatform.service.dto.HppVideoTypeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppVideoTypeMapper extends EntityMapper<HppVideoTypeDTO, HppVideoType> {

}
