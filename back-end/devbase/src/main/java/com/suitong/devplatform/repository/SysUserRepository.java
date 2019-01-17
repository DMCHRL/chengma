package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    
}
