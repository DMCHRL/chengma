package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysRoleComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysRoleComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleComponentRepository extends JpaRepository<SysRoleComponent, String> {

    @Modifying
    @Transactional
    @Query(value = "delete from SysRoleComponent src where src.sysRoleId=?1 ")
    void deleteComponentPrivilegeByRole(String roleId);

    @Modifying
    @Transactional
    @Query(value = "delete from SysRoleComponent src where src.sysComponentId=?1 ")
    void deleteSysRoleComponent(String componentId);
	
	@Query(value="select count(src) from SysRoleComponent src where src.sysRoleId=?1 and src.sysComponentId=?2")
    int checkRoleComponentExists(String adminRoleId, String componentId);
}
