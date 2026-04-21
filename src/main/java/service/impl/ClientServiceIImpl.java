package service.impl;

import dao.BankDAO;
import dao.ClientDAO;
import enums.Role;
import enums.Status;
import exceptions.*;
import model.Account;
import model.Bank;
import model.Client;
import service.AccountService;
import service.CardService;
import service.ClientService;
import service.IBANService;
import utils.PasswordUtils;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static utils.Utils.readInputInteger;

public class ClientServiceIImpl implements ClientService {

    private final ClientDAO clientDAO;
    private final BankDAO bankDAO;
    private final AccountService accountService;
    private final CardService cardService;
    private final IBANService ibanService;

    public ClientServiceIImpl(ClientDAO clientDAO, BankDAO bankDAO,
                              AccountService accountService, CardService cardService, IBANService ibanService){
        this.clientDAO = clientDAO;
        this.bankDAO = bankDAO;
        this.accountService = accountService;
        this.cardService = cardService;
        this.ibanService = ibanService;
    }

    @Override
    public Client createClient(int bankID, Connection connection) throws ClientNotSavedException {
        Client client = new Client();

        System.out.println("Enter first name: ");
        client.setFirstName(Utils.readInputString());

        System.out.println("Enter last name: ");
        client.setLastName(Utils.readInputString());

        System.out.println("Enter CNP: ");
        client.setCNP(Utils.readInputString());

        System.out.println("Enter series and Number of CI: ");
        client.setSeriesAndNumberOfCI(Utils.readInputString());

        System.out.println("Set a username:");
        client.setUsername(Utils.readInputString());

        System.out.println("Set a password:");
        String rawPassword = Utils.readInputString();
        String hashedPassword = PasswordUtils.hash(rawPassword);
        client.setPassword(hashedPassword);

        client.setBankID(bankID);
        client.setRole(Role.CLIENT);
        client.setStatus(Status.PENDING);

        int clientIDFromDB = 0;
        try {
            clientIDFromDB = clientDAO.saveClient(client, connection);
        } catch (SQLException e) {
            throw new ClientNotSavedException("Client not saved.", e);
        }

        try {
            Utils.logEntry("First Name: " + client.getFirstName() +
                    ", Last Name: " + client.getLastName(), clientIDFromDB, connection);
        } catch (AuditTrailNotSavedException e) {
            System.out.println("Audit trail entry not saved for client creation.");
        }

        return client;
    }

    @Override
    public void register(Connection connection) throws CounterExceededException, BankNotFoundException {
        List<Bank> banks = null;
        try {
            banks = bankDAO.getAllBanks(connection);
        } catch (SQLException e) {
            throw new BankNotFoundException("bank not found.", e);
        }

        System.out.println("Choose a bank: (type the name)");
        for(int i = 0; i < banks.size(); i++){
            System.out.println((i+1) + ". " + banks.get(i).getBankName());
        }
        String option = Utils.readInputString();

        Client client;
        Account account;

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Bank bank = bankDAO.getBankByBankName(option, connection);
            if(bank == null){
                System.out.println("Bank not found.");
            } else {
                client = createClient(bank.getID(), connection);
                account = accountService.createAccount(client, connection);
                cardService.createCard(bank, account, connection);
                ibanService.createIban("RO", bank, account, connection);

                connection.commit();
            }
        } catch (SQLException | IBANNotSavedException | AuditTrailNotSavedException e){
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Transaction failed", e);
        } catch (AccountNotSavedException e) {
            throw new RuntimeException(e);
        } catch (ClientNotSavedException e) {
            throw new RuntimeException(e);
        } catch (CardNotSavedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pendingClients(Connection connection) {
        boolean continueApproving = true;
        while(continueApproving) {
            System.out.println("Here are all pending clients that needs approval:");
            List<Client> clientsWhereStatusPending = null;
            try {
                clientsWhereStatusPending = clientDAO.getClientsWhereStatusPending(connection);
            } catch (SQLException e){
                System.out.println("Error getting the clients with status Pending.");
            }

            if (clientsWhereStatusPending.isEmpty()) {
                System.out.println("No clients pending.");
                continueApproving = false;
            } else {
                for (Client client : clientsWhereStatusPending) {
                    System.out.println(client);
                }
                System.out.println("To approve a client, enter the client ID:");
                int clientID = readInputInteger();
                try {
                    clientDAO.updateClientStatus(clientID, Status.APPROVED, connection);
                } catch (SQLException e) {
                    System.out.println("Updating the status of the client failed.");
                }
                System.out.println("Client approved successfully!\n" +
                        "Do you want to continue?");
                String s = Utils.readInputString();
                if (s.equalsIgnoreCase("no")) {
                    continueApproving = false;
                }
            }
        }
    }


}
