package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */
import com.chengma.devplatform.domain.HppCourse;
import com.chengma.devplatform.domain.HppExam;
import com.chengma.devplatform.service.dto.HppCourseDTO;
import com.chengma.devplatform.service.dto.HppExamDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppExamMapper extends EntityMapper <HppExamDTO,HppExam> {

}

