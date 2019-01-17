package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.TlbAccountData;
import com.chengma.devplatform.domain.TlbAccountTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface TlbAccountDataRepository extends JpaRepository<TlbAccountData,String> {

    void deleteByAccountEquals(String account);
}
