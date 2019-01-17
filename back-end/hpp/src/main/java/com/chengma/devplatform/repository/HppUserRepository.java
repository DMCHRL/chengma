package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.HppUser;
import com.chengma.devplatform.service.dto.HppUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HppUserRepository extends JpaRepository<HppUser,String> {

    public HppUser findByUserIdEquals(String userId);

    /**
     * 根据手机号查询开户
     * @param phone
     * @param status
     * @return
     */
    List<HppUser> findByPhoneEqualsAndStatusEquals(String phone,String status);

}
