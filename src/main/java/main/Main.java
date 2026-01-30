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

    static Client client;

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
                client = clientService.createClient(BT.getID());
                BTclients.add(client);
                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
                        "added to bank: " + BT.getBankName());
                break;
            case 2:
                bankService.addBank(BCR);
                Utils.logEntry("Account created at bank: " + BCR.getBankName());
                client = clientService.createClient(BCR.getID());
                BCRclients.add(client);
                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
                        "added to bank: " + BCR.getBankName());
                break;
            case 3:
                bankService.addBank(ING);
                Utils.logEntry("Account created at bank: " + ING.getBankName());
                client = clientService.createClient(ING.getID());
                INGclients.add(client);
                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
                        "added to bank: " + ING.getBankName());
                break;
            case 4:
                bankService.addBank(BRD);
                Utils.logEntry("Account created at bank: " + BRD.getBankName());
                client = clientService.createClient(BRD.getID());
                BRDclients.add(client);
                Utils.logEntry("Client: " + client.getFirstName() + " " + client.getLastName() +
                        "added to bank: " + BRD.getBankName());
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    public static void alreadyExistingAccountMenu() {
        System.out.println("To continue, enter the pin code:");
        boolean pinCodeValid = isPinCodeValid();

        if(pinCodeValid) {
            System.out.println("Welcome, " + client.getFirstName() + " " + client.getLastName());

            System.out.println();
            System.out.println("1. View IBAN");
            System.out.println("2. View Card Information");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("0. Exit");

            int choise = scanner.nextInt();

            switch (choise) {
                case 1:
                    viewClientIban();
                    break;
                case 2:
                    viewClientCardInformation();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    transferMoney();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Invalid choose. Try again.");
            }
        } else {
            System.out.println("Pin Code invalid!");
        }
    }

    private static void transferMoney() {
    }

    private static void withdrawMoney() {
        
    }

    private static void depositMoney() {
        
    }

    private static void viewClientCardInformation() {
        
    }


    public static void viewClientIban(){
        String iban = client.getAccount().getIban().getIBAN();
        char[] charArray = iban.toCharArray();

        for (int i = 0; i < 8; i++){
            System.out.print(charArray[i]);
        }
        for(int i = 8; i < charArray.length; i++){
            System.out.print("*");
        }

        System.out.println("\nTo see the entire IBAN, please enter the Pin Code:");
        boolean pinCodeValid = isPinCodeValid();

        if(pinCodeValid){
            System.out.println(iban);
        } else {
            System.out.println("Pin Code invalid.");
        }
    }

    public static boolean isPinCodeValid(){
        boolean isValid = false;
        String pinCode = scanner.next();
        scanner.nextLine();

        if(!pinCode.equals(client.getAccount().getCard().getPinCode())){
            System.out.println("Incorect Pin Code. Please try again!");
            System.exit(0);
        } else {
            isValid = true;
        }

        return isValid;
    }


}
