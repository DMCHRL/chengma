package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.domain.HppNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HppNewsRepository extends JpaRepository<HppNews,String> {

}
