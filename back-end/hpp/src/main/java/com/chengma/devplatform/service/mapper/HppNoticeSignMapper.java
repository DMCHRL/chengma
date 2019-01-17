package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/18.
 */
import com.chengma.devplatform.domain.HppNotice;
import com.chengma.devplatform.domain.HppNoticeSign;
import com.chengma.devplatform.service.dto.HppNoticeDTO;
import com.chengma.devplatform.service.dto.HppNoticeSignDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppNoticeSignMapper extends EntityMapper <HppNoticeSignDTO, HppNoticeSign> {

}

