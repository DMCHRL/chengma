package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */

import com.chengma.devplatform.domain.Asset;
import com.chengma.devplatform.domain.AssetDetail;
import com.chengma.devplatform.service.dto.AssetDTO;
import com.chengma.devplatform.service.dto.AssetDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AssetDetailMapper extends EntityMapper<AssetDetailDTO,AssetDetail> {

}

