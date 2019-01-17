package com.chengma.devplatform.repository;

import com.chengma.devplatform.domain.NoticeSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SysMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticeSignRepository extends JpaRepository<NoticeSign,String> {

    NoticeSign findByNoticeIdEqualsAndMobileEquals(String noticeId, String mobile);

    /**
     * 根据消息Id删除消息记录表
     * @param noticeId
     */
    void deleteAllByNoticeIdEquals(String noticeId);
}
