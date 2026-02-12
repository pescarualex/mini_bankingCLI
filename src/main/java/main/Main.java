package main;

import dao.AccountDAO;
import dao.BankDAO;
import dao.ClientDAO;
import exceptions.CounterExceededException;
import model.*;
import service.impl.AccountServiceImpl;
import service.impl.BankServiceImpl;
import service.impl.ClientServiceIImpl;
import utils.Utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static ClientServiceIImpl clientService = new ClientServiceIImpl();
    static BankServiceImpl bankService = new BankServiceImpl();
    static AccountServiceImpl accountService = new AccountServiceImpl();



    static Client client;
    static Account account;

    public static void main(String[] args) throws CounterExceededException, SQLException {
//        while(true){
//            System.out.println("\tWelcome!");
//            System.out.println("1. New client?\n" +
//                    "2. Have an account already.\n" +
//                    "0. Exit");
//
//            int choise = scanner.nextInt();
//            switch(choise){
//                case 1: createNewClient(); break;
//                case 2: alreadyExistingAccountMenu(); break;
//                case 0: System.exit(0); break;
//                default:
//                    System.out.println("Invalid option.");
//            }
//        }

//        Client client1 = new Client();
//        client1.setFirstName("Alex");
//        client1.setLastName("Pescaru");
//        client1.setCNP("1");
//        client1.setSeriesAndNumberOfCI("1");
//        client1.setUsername(client1.getFirstName(), client1.getLastName());
//        int client1_ID = ClientDAO.saveClient(client1);
//
//        Client client2 = new Client();
//        client2.setFirstName("Alexandru");
//        client2.setLastName("Pescaru");
//        client2.setCNP("2");
//        client2.setSeriesAndNumberOfCI("2");
//        client2.setUsername(client2.getFirstName(), client2.getLastName());
//        int client2_ID = ClientDAO.saveClient(client2);
//
//        Bank BT = new Bank("BT", "BTRL20", "BTRO", "Visa");
//          Bank BCR = new Bank("BCR", "BCR20", "BCRO", "Mastercard");
//          Bank ING = new Bank("ING", "ING30", "INGR", "Visa");
//          Bank BRD = new Bank("BRD", "BRD22", "BRDR", "Visa");
//
//          Account account = new Account();
//          account.setAmountOfMoney(0);
//          account.setClient_ID(client1_ID);
//        int account_ID = AccountDAO.saveAccount(account);
//
//        Account account2 = new Account();
//          account2.setAmountOfMoney(0);
//          account2.setClient_ID(client2_ID);
//        int account2_ID = AccountDAO.saveAccount(account2);
//
//        BT.setAccountID(account_ID);
//          BT.setClientID(client1_ID);
//
//          BCR.setAccountID(account2_ID);
//          BCR.setClientID(client2_ID);
//
//        BankDAO.saveBank(BT);
//        BankDAO.saveBank(BCR);

    }

//    public static void createNewClient() throws CounterExceededException {
//        System.out.println("To what bank do you want to create account?");
//        System.out.println("1. BT\n" +
//                "2. BCR\n" +
//                "3. ING\n" +
//                "4. BRD");
//        int choise = scanner.nextInt();
//
//        switch (choise){
//            case 1:
//                bankService.addBank(BT);
//                Utils.logEntry("Account created at bank: " + BT.getBankName());
//                client = clientService.createClient();
//                accountService.createAccount(BT.getID());
//                account = new Account(BT.getID());
//                account.setClient_ID(client.getId());
//                account.setAmountOfMoney(0L);
//                BTclients.add(client);
//                BT.setClients(BTclients);
//                BT.setAccountID(account.getID());
//                AccountDAO.saveAccount(account);
//                ClientDAO.saveClient(client);
//                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
//                        "added to bank: " + BT.getBankName());
//                break;
//            case 2:
//                bankService.addBank(BCR);
//                Utils.logEntry("Account created at bank: " + BCR.getBankName());
//                client = clientService.createClient();
//                accountService.createAccount(BCR.getID());
//                account = new Account(BCR.getID());
//                account.setClient_ID(client.getId());
//                account.setAmountOfMoney(0L);
//                BCRclients.add(client);
//                BCR.setClients(BCRclients);
//                BCR.setAccountID(account.getID());
//                AccountDAO.saveAccount(account);
//                ClientDAO.saveClient(client);
//                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
//                        "added to bank: " + BCR.getBankName());
//                break;
//            case 3:
//                bankService.addBank(ING);
//                Utils.logEntry("Account created at bank: " + ING.getBankName());
//                client = clientService.createClient();
//                accountService.createAccount(ING.getID());
//                account = new Account(ING.getID());
//                account.setClient_ID(client.getId());
//                account.setAmountOfMoney(0L);
//                INGclients.add(client);
//                ING.setClients(INGclients);
//                ING.setAccountID(account.getID());
//                AccountDAO.saveAccount(account);
//                ClientDAO.saveClient(client);
//                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
//                        "added to bank: " + ING.getBankName());
//                break;
//            case 4:
//                bankService.addBank(BRD);
//                Utils.logEntry("Account created at bank: " + BRD.getBankName());
//                client = clientService.createClient();
//                accountService.createAccount(BRD.getID());
//                account = new Account(BRD.getID());
//                account.setClient_ID(client.getId());
//                account.setAmountOfMoney(0L);
//                BRDclients.add(client);
//                BRD.setClients(BRDclients);
//                BRD.setAccountID(account.getID());
//                AccountDAO.saveAccount(account);
//                ClientDAO.saveClient(client);
//                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
//                        "added to bank: " + BRD.getBankName());
//                break;
//            default:
//                System.out.println("Invalid choice. Try again.");
//        }
//    }
//
//    public static void alreadyExistingAccountMenu() {
//        System.out.println("To continue, enter the pin code:");
//        boolean pinCodeValid = isPinCodeValid();
//
//        if(pinCodeValid) {
//            System.out.println("Welcome, " + client.getFirstName() + " " + client.getLastName());
//
//            System.out.println();
//            System.out.println("1. View IBAN");
//            System.out.println("2. View Card Information");
//            System.out.println("3. See current balance");
//            System.out.println("4. Deposit Money");
//            System.out.println("5. Withdraw Money");
//            System.out.println("6. Transfer Money");
//            System.out.println("0. Exit");
//
//            int choise = scanner.nextInt();
//
//            switch (choise) {
//                case 1:
//                    viewClientIban();
//                    break;
//                case 2:
//                    viewClientCardInformation();
//                    break;
//                case 3: seeCurrentBalance(); break;
//                case 4:
//                    depositMoney();
//                    break;
//                case 5:
//                    withdrawMoney();
//                    break;
//                case 6:
//                    transferMoney();
//                    break;
//                case 0:
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid choose. Try again.");
//            }
//        } else {
//            System.out.println("Pin Code invalid!");
//        }
//    }
//
//    private static void transferMoney() {
//
//    }
//
//
//
//    private static void seeCurrentBalance() {
//        long amountOfMoney = client.getAccount().getAmountOfMoney();
//        System.out.println("Your current balance is: " + amountOfMoney + " RON");
//    }
//
//    private static void withdrawMoney() {
//        System.out.println("Enter the amount of money do you want to withdraw:");
//        long money = scanner.nextLong();
//
//        if(client.getAccount().getAmountOfMoney() > 0 && client.getAccount().getAmountOfMoney() >= money){
//            long newAmountOfMoney = client.getAccount().getAmountOfMoney() - money;
//            client.getAccount().setAmountOfMoney(newAmountOfMoney);
//
//            System.out.println("Now, you have: " + client.getAccount().getAmountOfMoney() + " RON");
//        } else {
//            System.out.println("Sorry, you do not have that amount of money in your account.");
//        }
//    }
//
//    private static void depositMoney() {
//        System.out.println("Enter the amount of money do you want to deposit:");
//        long money = scanner.nextLong();
//
//        long newAmountOfMoney = client.getAccount().getAmountOfMoney() + money;
//        client.getAccount().setAmountOfMoney(newAmountOfMoney);
//
//        System.out.println("Now, you have: " + client.getAccount().getAmountOfMoney() + " RON");
//    }
//
//    private static void viewClientCardInformation() {
//        System.out.println("\nTo see the entire Card number, please enter the Pin Code:");
//        boolean pinCodeValid = isPinCodeValid();
//
//        if(pinCodeValid) {
//            String cardNumber = client.getAccount().getCard().getCardNumber();
//            String cvv = client.getAccount().getCard().getCVV();
//            LocalDate expirationDate = client.getAccount().getCard().getExpirationDate();
//
//            System.out.println("Card number: " + cardNumber + "\n" +
//                    "CVV: " + cvv + "\n" +
//                    "Expiration Date: " + expirationDate.toString());
//        } else {
//            System.out.println("Pin code invalid.");
//        }
//    }
//
//    public static void viewClientIban(){
//        String iban = client.getAccount().getIban().getIBAN();
//        char[] charArray = iban.toCharArray();
//
//        for (int i = 0; i < 8; i++){
//            System.out.print(charArray[i]);
//        }
//        for(int i = 8; i < charArray.length; i++){
//            System.out.print("*");
//        }
//
//        System.out.println("\nTo see the entire IBAN, please enter the Pin Code:");
//        boolean pinCodeValid = isPinCodeValid();
//
//        if(pinCodeValid){
//            System.out.println(iban);
//        } else {
//            System.out.println("Pin Code invalid.");
//        }
//    }
//
//    public static boolean isPinCodeValid(){
//        boolean isValid = false;
//        String pinCode = scanner.next();
//        scanner.nextLine();
//
//        if(!pinCode.equals(client.getAccount().getCard().getPinCode())){
//            System.out.println("Incorect Pin Code. Please try again!");
//            System.exit(0);
//        } else {
//            isValid = true;
//        }
//
//        return isValid;
//    }


}
