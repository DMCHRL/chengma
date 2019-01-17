package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.AllotRule;
import com.chengma.devplatform.domain.ExpectRiskProfit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface ExpectRiskProfitRepository extends JpaRepository<ExpectRiskProfit, String> {

    List<ExpectRiskProfit> findByFundSignalIdEquals(String fundSignalId);

    void deleteByFundSignalIdEquals(String fundSignalId);

}
