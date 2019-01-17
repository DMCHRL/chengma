package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface BankInfoMapper extends EntityMapper <BankInfoDTO, BankInfo> {

}
