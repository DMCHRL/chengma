package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbAccountBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbAccountBackRepository extends JpaRepository<TlbAccountBack,String> {
}
