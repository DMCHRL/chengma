package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.HppNews;
import com.chengma.devplatform.domain.HppNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HppNoticeRepository extends JpaRepository<HppNotice,String> {

    HppNotice findByIdEqualsAndSendFlagEquals(String id,String sendFlag);
}
