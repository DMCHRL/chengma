package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.HppNews;
import com.chengma.devplatform.domain.HppProtocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HppProtocolRepository extends JpaRepository<HppProtocol,String> {

    public HppProtocol findHppProtocolByTypeEqualsAndDelFlagEquals(String type, String delFlag);

    public HppProtocol findHppProtocolByTypeEquals(String type);
}
