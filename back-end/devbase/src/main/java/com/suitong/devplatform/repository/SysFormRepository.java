package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysForm;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the SysForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysFormRepository extends JpaRepository<SysForm,Long> {

    @Query(value="select count(sf) from SysForm sf where sf.englishName=?1")
    int checkFormEnglishName(String formEnglishName);

    @Query(value="select sf from SysForm sf where sf.menuId=?1")
    List<SysForm> getFormListByMenuId(Long menuId);
}
