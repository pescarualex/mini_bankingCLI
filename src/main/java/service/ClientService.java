package service;

import exceptions.*;
import model.Client;

import java.sql.Connection;

public interface ClientService {
    Client createClient(int bankId, Connection connection) throws ClientNotSavedException;
    void register(Connection connection) throws CounterExceededException, BankNotFoundException;
    void pendingClients(Connection connection) throws AuditTrailNotSavedException;
}
