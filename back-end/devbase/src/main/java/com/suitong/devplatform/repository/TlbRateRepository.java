package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.TlbRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbRateRepository extends JpaRepository<TlbRate,Long> {
}
