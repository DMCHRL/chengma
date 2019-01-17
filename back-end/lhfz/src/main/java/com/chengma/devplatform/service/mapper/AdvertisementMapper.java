package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.Advertisement;
import com.chengma.devplatform.service.dto.AdvertisementDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AdvertisementMapper extends EntityMapper<AdvertisementDTO,Advertisement> {

}
