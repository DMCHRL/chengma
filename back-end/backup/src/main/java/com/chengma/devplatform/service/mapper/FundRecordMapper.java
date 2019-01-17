package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */

import com.chengma.devplatform.domain.FundRecord;
import com.chengma.devplatform.service.dto.FundRecordDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface FundRecordMapper extends EntityMapper<FundRecordDTO,FundRecord> {

}

