package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.FundSignal;
import com.chengma.devplatform.domain.MobileUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface FundSignalRepository extends JpaRepository<FundSignal, String> {

}
