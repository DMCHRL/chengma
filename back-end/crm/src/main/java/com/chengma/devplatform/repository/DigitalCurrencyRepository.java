package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.domain.DigitalCurrency;
import com.chengma.devplatform.domain.Receivables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DigitalCurrencyRepository extends JpaRepository<DigitalCurrency,String> {
    DigitalCurrency findByFlagEquals(String flag);
}
