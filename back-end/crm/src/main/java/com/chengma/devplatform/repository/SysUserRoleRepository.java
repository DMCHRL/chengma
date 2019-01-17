package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
@SuppressWarnings("unused")
@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, String> {

    @Query(value = "select sur from SysUserRole sur where sur.sysUsersId = ?1")
    List<SysUserRole> getRoleIdsByUser(String userId);

    @Modifying
    @Transactional
    @Query(value = "delete from SysUserRole sur where sur.sysUsersId = ?1")
    void deleteUserRoleByUserId(String userId);

    @Modifying
    @Transactional
    @Query(value = "delete from SysUserRole suc where suc.sysRolesId=?1 ")
    void deleteUserPrivilegeByRole(String roleId);
}
