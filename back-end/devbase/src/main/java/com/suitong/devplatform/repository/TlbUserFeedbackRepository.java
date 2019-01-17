package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.TlbUser;
import com.suitong.devplatform.domain.TlbUserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TlbUserFeedbackRepository extends JpaRepository<TlbUserFeedback,Long> {
}
