package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, String> {

    @Query(value = "select sm from SysMenu sm where sm.parentId=?1")
    List<SysMenu> getSysMenusByParentId(String parentId);

    @Modifying
    @Transactional
    @Query("update SysMenu sm set sm.visible = ?1 where sm.id = ?2")
    void enableSysMenu(String visible, String menuId);
}
