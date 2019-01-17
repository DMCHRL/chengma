package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppExamApply;
import com.chengma.devplatform.service.dto.HppExamApplyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppExamApplyMapper extends EntityMapper<HppExamApplyDTO, HppExamApply> {

}
