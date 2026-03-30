package main;

import dao.*;
import exceptions.CounterExceededException;
import model.*;
import service.impl.*;
import utils.Utils;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws CounterExceededException, SQLException {

        ClientServiceIImpl clientServiceImpl = new ClientServiceIImpl();
        Client client = clientServiceImpl.createClient();
        AccountServiceImpl.createAccount(client);
        List<AuditTrail> auditTrailByClientID = AuditTrailDAO.getAuditTrailByClientID(client.getId());
        for (AuditTrail auditTrail : auditTrailByClientID){
            System.out.println(auditTrail);
        }
        System.out.println("::::::::::");
        List<AuditTrail> allAuditTrail = AuditTrailDAO.getAllAuditTrail();
        for (AuditTrail auditTrail : allAuditTrail){
            System.out.println(auditTrail);
        }


    }

    private static void loginAsClient() {
    }

    private static void loginAsAdministrator() {
        
    }
}
