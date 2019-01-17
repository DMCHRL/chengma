package com.suitong.devplatform.repository;

import com.suitong.devplatform.domain.SysOperateLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/8/24.
 */
@SuppressWarnings("unused")
@Repository
public interface SysOperateLogRepository extends JpaRepository<SysOperateLog, Long>{

}
