package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppMsgNotice;
import com.chengma.devplatform.service.dto.HppMsgNoticeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppMsgNoticeMapper extends EntityMapper<HppMsgNoticeDTO, HppMsgNotice> {

}
