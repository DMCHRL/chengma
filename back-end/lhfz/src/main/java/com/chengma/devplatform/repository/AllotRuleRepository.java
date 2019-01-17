package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.AllotRule;
import com.chengma.devplatform.domain.FundSignal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface AllotRuleRepository extends JpaRepository<AllotRule, String> {

    List<AllotRule> findByFundSignalIdEquals(String fundSignalId);

    void deleteByFundSignalIdEquals(String fundSignalId);

}
