package com.sctech.emailapp.service;

import com.sctech.emailapp.model.AuditLog;
import com.sctech.emailapp.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logEvent(String eventType, String description) {
        AuditLog auditLog = new AuditLog();
        auditLog.setEventType(eventType);
        auditLog.setDescription(description);
        auditLog.setEventTime(LocalDateTime.now());

        // Assuming you have Spring Security to get the authenticated user's name
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLog.setUserId(username);

        auditLogRepository.save(auditLog);
    }
}