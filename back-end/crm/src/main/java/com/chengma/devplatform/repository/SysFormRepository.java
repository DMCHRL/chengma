package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.SysForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the SysForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysFormRepository extends JpaRepository<SysForm, String> {

    @Query(value = "select count(sf) from SysForm sf where sf.englishName=?1")
    int checkFormEnglishName(String formEnglishName);

    @Query(value = "select sf from SysForm sf where sf.menuId=?1")
    List<SysForm> getFormListByMenuId(String menuId);
}
