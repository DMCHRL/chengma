package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysRoleComponent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysRoleComponent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleComponentRepository extends JpaRepository<SysRoleComponent,Long> {

    @Modifying
    @Transactional
    @Query(value="delete from SysRoleComponent src where src.sysRole.id=?1 ")
    void deleteComponentPrivilegeByRole(Long roleId);

    @Modifying
    @Transactional
    @Query(value="delete from SysRoleComponent src where src.sysComponent.id=?1 ")
    void deleteSysRoleComponent(Long componentId);
}
