package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.TlbUser;
import com.chengma.devplatform.service.dto.TlbUserDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbUserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbUserMapper extends EntityMapper <TlbUserDTO, TlbUser> {

    /*default TlbUser fromId(String id) {
        if (id == null) {
            return null;
        }
        TlbUser tlbUser = new TlbUser();
        tlbUser.setId(id);
        return tlbUser;
    }*/
}
