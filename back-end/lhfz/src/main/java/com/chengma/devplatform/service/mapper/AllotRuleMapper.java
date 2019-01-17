package com.chengma.devplatform.service.mapper;

/**
 * Created by Administrator on 2018/9/14.
 */

import com.chengma.devplatform.domain.AllotRule;
import com.chengma.devplatform.service.dto.AllotRuleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface AllotRuleMapper extends EntityMapper<AllotRuleDTO,AllotRule> {

}

