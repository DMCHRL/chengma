package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysRoleForm;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysRoleForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleFormRepository extends JpaRepository<SysRoleForm,Long> {

    @Modifying
    @Transactional
    @Query(value="delete from SysRoleForm srf where srf.sysRole.id=?1 ")
    void deleteFormPrivilegeByRole(Long roleId);

    @Modifying
    @Transactional
    @Query(value="delete from SysRoleForm srf where srf.sysForm.id=?1 ")
    void deleteSysRoleForm(Long formId);
    
}
