package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysMenu;
import com.suitong.devplatform.domain.TlbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbUserRepository extends JpaRepository<TlbUser,Long> {
}
