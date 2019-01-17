package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.TlbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbUserRepository extends JpaRepository<TlbUser,String> {

    TlbUser findTlbUserByUserId(String userId);

}
