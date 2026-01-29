package main;

import exceptions.CounterExceededException;
import model.*;
import service.impl.BankServiceImpl;
import service.impl.ClientServiceIImpl;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static ClientServiceIImpl clientService = new ClientServiceIImpl();
    static BankServiceImpl bankService = new BankServiceImpl();

    final static Bank BT = new Bank("BT", "BTRL20", "BTRO", "Visa");
    final static Bank BCR = new Bank("BCR", "BCR20", "BCRO", "Mastercard");
    final static Bank ING = new Bank("ING", "ING30", "INGR", "Visa");
    final static Bank BRD = new Bank("BRD", "BRD22", "BRDR", "Visa");

    static List<Client> BTclients = new ArrayList<>();
    static List<Client> BCRclients = new ArrayList<>();
    static List<Client> INGclients = new ArrayList<>();
    static List<Client> BRDclients = new ArrayList<>();

    public static void main(String[] args) throws CounterExceededException {
        while(true){
            System.out.println("\tWelcome!");
            System.out.println("1. New client?\n" +
                    "2. Have an account already.");
            int choise = scanner.nextInt();
            switch(choise){
                case 1: createNewClient(); break;
                case 2: alreadyExistingAccountMenu(); break;
            }
        }
    }

    public static void createNewClient() throws CounterExceededException {
        System.out.println("To what bank do you want to create account?");
        System.out.println("1. BT\n" +
                "2. BCR\n" +
                "3. ING\n" +
                "4. BRD");
        int choise = scanner.nextInt();

        switch (choise){
            case 1:
                bankService.addBank(BT);
                Utils.logEntry("Account created at bank: " + BT.getBankName());
                Client clientBT = clientService.createClient(BT.getID());
                BTclients.add(clientBT);
                Utils.logEntry("Client: " + clientBT.getFirstName() + " " + clientBT.getLastName() +
                        "added to bank: " + BT.getBankName());
                break;
            case 2:
                bankService.addBank(BCR);
                Utils.logEntry("Account created at bank: " + BCR.getBankName());
                Client clientBCR = clientService.createClient(BCR.getID());
                BCRclients.add(clientBCR);
                Utils.logEntry("Client: " + clientBCR.getFirstName() + " " + clientBCR.getLastName() +
                        "added to bank: " + BCR.getBankName());
                break;
            case 3:
                bankService.addBank(ING);
                Utils.logEntry("Account created at bank: " + ING.getBankName());
                Client clientING = clientService.createClient(ING.getID());
                INGclients.add(clientING);
                Utils.logEntry("Client: " + clientING.getFirstName() + " " + clientING.getLastName() +
                        "added to bank: " + ING.getBankName());
                break;
            case 4:
                bankService.addBank(BRD);
                Utils.logEntry("Account created at bank: " + BRD.getBankName());
                Client clientBRD = clientService.createClient(BRD.getID());
                BRDclients.add(clientBRD);
                Utils.logEntry("Client: " + clientBRD.getFirstName() + " " + clientBRD.getLastName() +
                        "added to bank: " + BRD.getBankName());
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    public static void alreadyExistingAccountMenu() {


        System.out.println();
        System.out.println("1. View IBAN");
        System.out.println("2. View Card Information");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");

        int choise = scanner.nextInt();

//        switch(choise){
//            case 1: viewClientIban(); break;
//            case 2: viewClientCardInformation(); break;
//            case 3: depositMoney(); break;
//            case 4: withdrawMoney(); break;
//            case 5: transferMoney(); break;
//            default:
//                System.out.println("Invalid choose. Try again.");
//        }
    }

    public boolean isPinCodeValid(){

    }


}
