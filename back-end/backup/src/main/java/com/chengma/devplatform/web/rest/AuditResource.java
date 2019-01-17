package com.chengma.devplatform.web.rest;

import com.chengma.devplatform.service.AuditEventService;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for getting the audit events.
 *
 * @author administrator
 */
@RestController
@RequestMapping("/management/audits")
public class AuditResource {

    private final AuditEventService auditEventService;

    public AuditResource(AuditEventService auditEventService) {
        this.auditEventService = auditEventService;
    }

    /**
     * GET  /audits/:id : 根据id获取AuditEvent。
     *
     * @param id 要获取的实体的id
     * @return 状态：200，请求成功并返回auditevent；404：未找到接口。
     */
    @GetMapping("/{id:.+}")
    public ResponseEntity<AuditEvent> get(@PathVariable String id) {
        AuditEvent auditEvent = auditEventService.find(id);
        return new ResponseEntity<>(auditEvent, null, HttpStatus.OK);
    }
}
