package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */

import com.chengma.devplatform.domain.Asset;
import com.chengma.devplatform.service.dto.AssetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AssetMapper extends EntityMapper<AssetDTO,Asset> {

}

