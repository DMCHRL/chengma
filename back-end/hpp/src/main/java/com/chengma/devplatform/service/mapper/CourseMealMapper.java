package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */
import com.chengma.devplatform.domain.CourseMeal;
import com.chengma.devplatform.service.dto.CourseMealDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CourseMealMapper extends EntityMapper <CourseMealDTO,CourseMeal> {

}

