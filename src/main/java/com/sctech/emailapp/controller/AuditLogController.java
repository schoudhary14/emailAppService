package com.sctech.emailapp.controller;

import com.sctech.emailapp.model.AuditLog;
import com.sctech.emailapp.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }
}