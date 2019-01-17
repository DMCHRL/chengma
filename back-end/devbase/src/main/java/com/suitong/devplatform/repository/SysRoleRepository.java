package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


/**
 * Spring Data JPA repository for the SysRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole,Long> {

    @Modifying
    @Transactional
    @Query("update SysRole sr set sr.delFlag = ?1 where sr.id = ?2")
    void enableSysRole(String delFlag,Long roleId);

    @Query(value="select count(sr) from SysRole sr where sr.roleNo=?1")
    int checkRoleNo(String roleNo);

    /*@Query("select sr.id, sr.roleName from SysRole sr, SysAdminRole sar where sr.id = sar.sysRoleId and sar.sysAdminId = ?1")
    Collection getAssignedRoles(Long userId);

    @Query("select sr.id, sr.roleName from SysRole sr where sr.id not in ( select sar.sysRoleId from SysAdminRole sar where sar.sysAdminId = ?1)")
    Collection getALLUnassignedRoles(Long userId);*/
    
}
