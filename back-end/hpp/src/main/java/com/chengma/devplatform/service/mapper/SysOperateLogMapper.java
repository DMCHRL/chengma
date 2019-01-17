package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.SysOperateLog;
import com.chengma.devplatform.service.dto.SysOperateLogDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysOperateLog and its DTO SysOperateLogDTO.
 */
@Mapper(componentModel = "spring", uses = {SysOperateLog.class,})
public interface SysOperateLogMapper extends EntityMapper<SysOperateLogDTO, SysOperateLog> {

//    default SysOperateLog fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysOperateLog sysOperateLog = new SysOperateLog();
//        sysOperateLog.setId(id);
//        return sysOperateLog;
//    }
}
