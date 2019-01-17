package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.AllotRule;
import com.chengma.devplatform.domain.FundRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface FundRecordRepository extends JpaRepository<FundRecord, String> {

    List<FundRecord> findByUserIdEquals(String userId);

    List<FundRecord> findByFundSignalIdEquals(String fundSignalId);

}
