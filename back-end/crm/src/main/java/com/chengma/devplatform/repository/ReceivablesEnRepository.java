package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.Receivables;
import com.chengma.devplatform.domain.ReceivablesEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceivablesEnRepository extends JpaRepository<ReceivablesEn,String> {

    ReceivablesEn findByFlagEquals(String flag);
}
