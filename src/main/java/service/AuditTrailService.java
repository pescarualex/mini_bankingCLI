package service;

import exceptions.AuditTrailNotFoundException;

import java.sql.Connection;

public interface AuditTrailService {
    void auditTrailByClient(Connection connection) throws AuditTrailNotFoundException;
    void fullAuditTrail(Connection connection) throws AuditTrailNotFoundException;
}
