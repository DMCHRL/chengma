package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, String> {

    @Modifying
    @Transactional
    @Query("update SysRole sr set sr.delFlag = ?1 where sr.id = ?2")
    void enableSysRole(String delFlag, String roleId);

    @Query(value = "select count(sr) from SysRole sr where sr.roleNo=?1")
    int checkRoleNo(String roleNo);
}
