package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/18.
 */

import com.chengma.devplatform.domain.NoticeSign;
import com.chengma.devplatform.service.dto.NoticeSignDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface NoticeSignMapper extends EntityMapper<NoticeSignDTO, NoticeSign> {

}

