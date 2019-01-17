package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticeRepository extends JpaRepository<Notice,String> {

    Notice findByIdEqualsAndSendFlagEquals(String id, String sendFlag);
}
