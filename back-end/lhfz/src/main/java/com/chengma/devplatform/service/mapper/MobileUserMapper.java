package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */

import com.chengma.devplatform.domain.MobileUser;
import com.chengma.devplatform.service.dto.MobileUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface MobileUserMapper extends EntityMapper<MobileUserDTO,MobileUser> {

}

