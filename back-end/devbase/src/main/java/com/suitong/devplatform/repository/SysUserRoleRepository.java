package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysUserRole;
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
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long>{

    @Query(value="select sur from SysUserRole sur where sur.user.id = ?1")
    List<SysUserRole> getRoleIdsByUser(Long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from SysUserRole sur where sur.user.id = ?1")
    void deleteUserRoleByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value="delete from SysUserRole suc where suc.sysRole.id=?1 ")
    void deleteUserPrivilegeByRole(Long roleId);

    /*@Query(value = "delete from SysUserRole sur where sur.user.id = ?1")
    void deleteUserRoleByUserId(Long userId);*/

}
