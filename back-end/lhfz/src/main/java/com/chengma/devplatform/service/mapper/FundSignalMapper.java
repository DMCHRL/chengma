package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */

import com.chengma.devplatform.domain.FundSignal;
import com.chengma.devplatform.service.dto.FundSignalDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface FundSignalMapper extends EntityMapper<FundSignalDTO,FundSignal> {

}

