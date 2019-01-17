package com.chengma.devplatform.repository;

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
public interface UserRepository extends JpaRepository<User, String> {

    User findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(Date dateTime);

    User findOneByResetKey(String resetKey);

    User findOneByEmail(String email);

    User findOneByLogin(String login);

    User findOneByMobile(String mobile);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesById(String id);

    @EntityGraph(attributePaths = "authorities")
    User findOneWithAuthoritiesByLogin(String login);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

    @Query(value = "select u from User u where (u.login = ?1 or u.email = ?2) and c_del_flag = '1' ")
    List<User> duplicateLogin(String login, String email);

    int countUserByParentIdEqualsAndIsProxy(String userId, String isProxy);
}
