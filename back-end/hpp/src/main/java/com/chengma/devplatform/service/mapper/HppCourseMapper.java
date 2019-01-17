package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */
import com.chengma.devplatform.domain.HppCourse;
import com.chengma.devplatform.service.dto.HppCourseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppCourseMapper extends EntityMapper <HppCourseDTO,HppCourse> {

}

