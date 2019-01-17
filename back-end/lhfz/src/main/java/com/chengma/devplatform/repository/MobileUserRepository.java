package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.MobileUser;
import com.chengma.devplatform.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface MobileUserRepository extends JpaRepository<MobileUser, String> {

    MobileUser findByMobileEquals(String mobile);

    MobileUser findByUserIdEquals(String userId);

    MobileUser findByUserIdEqualsAndPasswordEquals(String userId,String password);
}
