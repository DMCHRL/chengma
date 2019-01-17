package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */
import com.chengma.devplatform.domain.HppCourseMeal;
import com.chengma.devplatform.service.dto.HppCourseMealDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppCourseMealMapper extends EntityMapper <HppCourseMealDTO,HppCourseMeal> {

}

