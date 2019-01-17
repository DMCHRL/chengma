package com.chengma.devplatform.service;

import com.chengma.devplatform.config.audit.AuditEventConverter;
import com.chengma.devplatform.domain.PersistentAuditEvent;
import com.chengma.devplatform.repository.PersistenceAuditEventRepository;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing audit events.
 * <p>
 * This is the default implementation to support SpringBoot Actuator AuditEventRepository
 * </p>
 */
@Service
@Transactional
public class AuditEventService {

    private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    private final AuditEventConverter auditEventConverter;

    public AuditEventService(
            PersistenceAuditEventRepository persistenceAuditEventRepository,
            AuditEventConverter auditEventConverter) {

        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    public AuditEvent find(String id) {
        PersistentAuditEvent pa = persistenceAuditEventRepository.findOne(id);
        if (null != pa) {
            return auditEventConverter.convertToAuditEvent(pa);
        } else {
            return null;
        }
    }
}
