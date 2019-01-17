package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysRoleMenu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysRoleMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu,Long> {

    @Modifying
    @Transactional
    @Query(value="delete from SysRoleMenu srm where srm.sysRole.id=?1 ")
    void deleteMenuPrivilegeByRole(Long roleId);

    @Modifying
    @Transactional
    @Query(value="delete from SysRoleMenu srm where srm.sysMenu.id=?1 ")
    void deleteSysRoleMenu(Long menuId);
}
