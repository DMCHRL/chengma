package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/18.
 */
import com.chengma.devplatform.domain.Notice;
import com.chengma.devplatform.service.dto.NoticeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface NoticeMapper extends EntityMapper<NoticeDTO, Notice> {

}

