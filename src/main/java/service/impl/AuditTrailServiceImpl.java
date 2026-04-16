package service.impl;

import dao.AuditTrailDAO;
import exceptions.AuditTrailNotFoundException;
import model.AuditTrail;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static utils.Utils.readInputInteger;

public class AuditTrailServiceImpl {
    public void auditTrailByClient(Connection connection) throws AuditTrailNotFoundException {
        boolean next = true;

        while(next) {
            System.out.println("Enter the client ID: ");
            int clientID = readInputInteger();

            List<AuditTrail> auditTrailByClientID = null;
            try {
                auditTrailByClientID = AuditTrailDAO.getAuditTrailByClientID(clientID, connection);
            } catch (SQLException e) {
                throw new AuditTrailNotFoundException("Audit trail not found for client.", e);
            }
            for (AuditTrail entry : auditTrailByClientID) {
                System.out.println(entry);
            }

            System.out.println("Do you want to continue?");
            String s = Utils.readInputString();
            if(s.equalsIgnoreCase("no")){
                next = false;
            }
        }
    }

    public void fullAuditTrail(Connection connection) throws AuditTrailNotFoundException {
        List<AuditTrail> allAuditTrail = null;
        try {
            allAuditTrail = AuditTrailDAO.getAllAuditTrail(connection);
        } catch (SQLException e) {
            throw new AuditTrailNotFoundException("Audit Trail not found.", e);
        }
        for(AuditTrail entry : allAuditTrail){
            System.out.println(entry);
        }
    }
}
