package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Data JPA repository for the SysRoleMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, String> {

    @Modifying
    @Transactional
    @Query(value = "delete from SysRoleMenu srm where srm.sysRoleId=?1 ")
    void deleteMenuPrivilegeByRole(String roleId);

    @Modifying
    @Transactional
    @Query(value = "delete from SysRoleMenu srm where srm.sysMenuId=?1 ")
    void deleteSysRoleMenu(String menuId);
	
	@Query(value="select count(srm) from SysRoleMenu srm where srm.sysRoleId=?1 and srm.sysMenuId=?2")
    int checkRoleMenuExists(String adminRoleId, String MenuId);
}
