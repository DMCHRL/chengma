package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysRoleForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysRoleForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleFormRepository extends JpaRepository<SysRoleForm, String> {

    @Modifying
    @Transactional
    @Query(value = "delete from SysRoleForm srf where srf.sysRoleId=?1 ")
    void deleteFormPrivilegeByRole(String roleId);

    @Modifying
    @Transactional
    @Query(value = "delete from SysRoleForm srf where srf.sysFormId=?1 ")
    void deleteSysRoleForm(String formId);

	@Query(value="select count(srf) from SysRoleForm srf where srf.sysRoleId=?1 and srf.sysFormId=?2")
    int checkRoleFormExists(String adminRoleId, String formId);
}
