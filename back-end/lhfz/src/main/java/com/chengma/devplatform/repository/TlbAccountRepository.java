package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the TranInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbAccountRepository extends JpaRepository<TlbAccount,String> {

    TlbAccount findOneByAccountEquals(String account);

    TlbAccount findOneByAccountEqualsAndMt4PasswordEquals(String account,String password);

}
