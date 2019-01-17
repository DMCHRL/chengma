package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.FundSignal;
import com.chengma.devplatform.domain.FundSignalData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface FundSignalDataRepository extends JpaRepository<FundSignalData, String> {

    FundSignalData findByFundSignalIdEquals(String fundSignalId);
}
