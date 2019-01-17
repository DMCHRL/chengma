package com.chengma.devplatform.repository;


import com.chengma.devplatform.domain.TlbAccountTrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface TlbAccountTradeRepository extends JpaRepository<TlbAccountTrade,String> {

}
