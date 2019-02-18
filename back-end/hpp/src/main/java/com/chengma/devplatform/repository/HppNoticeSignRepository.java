package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.HppNotice;
import com.chengma.devplatform.domain.HppNoticeSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HppNoticeSignRepository extends JpaRepository<HppNoticeSign,String> {

    //HppNoticeSign findByNoticeIdEqualsAndMobileEquals(String noticeId,String mobile);

    /**
     * 根据消息Id删除消息记录表
     * @param noticeId
     */
    void deleteAllByNoticeIdEquals(String noticeId);
}
