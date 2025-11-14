package main;

import model.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Client> BTclients = new ArrayList<>();

    static List<Bank> banks = new ArrayList<>();

    static Bank BT = new Bank("Banca Transilvania", "BTRL20", "BTRO", "Visa");
    static Bank BCR = new Bank("BCR", "BCR20", "BCRO", "Mastercard");
    static Bank ING = new Bank("ING", "ING30", "INGR", "Visa");
    static Bank BRD = new Bank("BRD", "BRD22", "BRDR", "Visa");



    public static void main(String[] args) {
        addBank(BT);
        addBank(BCR);
        addBank(ING);
        addBank(BRD);

        printMenu();
    }


    public static void printMenu(){
        System.out.println("Welcome!");
        System.out.println("1. Create Account");
        System.out.println("2. View account details");
        System.out.println("3. View Logs");

        int optionSelected;
        optionSelected = scanner.nextInt();

        switch(optionSelected){
            case 1:
                createAccount();
                break;
            case 2:
                viewAccountDetails();
                break;
            case 3:
                Utils.getLogs();
                continueOrExit();
                break;
        }
    }

    public static void createAccount(){
        System.out.println("You can create an account for the following banks: \n" +
                "1. Banca Transilvania,\n" +
                "2. BCR,\n" +
                "3. ING,\n" +
                "4. BRD\n" +
                "Please choose one of the banks: ");
        int option = scanner.nextInt();

        switch(option){
            case 1:
                Utils.logEntry("Created account at bank: " + BT.getBankName());
                Client clientBT = createClient(BT.getBankName(), BT.getBankCode(),
                        BT.getPaymentNetwork(), "222111");
                BTclients.add(clientBT);
                BT.setClients(BTclients);
                System.out.println("Your account has been created!\n");
                continueOrExit();
                break;
            case 2:
                Utils.logEntry("Created account at bank: " + BCR.getBankName());
                createClient(BCR.getBankName(), BCR.getBankCode(),
                        BCR.getPaymentNetwork(), "555111");
                continueOrExit();
                break;
            case 3:
                Utils.logEntry("Created account at bank: " + ING.getBankName());
                createClient(ING.getBankName(), ING.getBankCode(),
                        ING.getPaymentNetwork(), "222222");
                continueOrExit();
            case 4:
                Utils.logEntry("Created account at bank: " + BRD.getBankName());
                createClient(BRD.getBankName(), BRD.getBankCode(),
                        BRD.getPaymentNetwork(), "222333");
                continueOrExit();
                break;
            default:
                System.out.println("You didn't entered a valid option. Please try again.");
        }
    }


    public static Client createClient(String bankName, String bankCode,
                                      String paymentNetworkDigit,
                                      String bankIdentificationNumber){
        Client client = new Client();
        System.out.println("Now, we needs some details about you.");
        System.out.print("Please enter your first name: ");
        scanner.nextLine();
        String firstName = scanner.nextLine().trim();
        if(firstName.isEmpty()){
            System.out.println("First Name cannot be empty, please try again.");
            firstName = scanner.nextLine().trim();
        }
        client.setFirstName(firstName);

        System.out.print("Please enter your last name: ");
        String lastName = scanner.nextLine().trim();
        if(lastName.isEmpty()){
            System.out.println("Last Name cannot be empty, please try again.");
            lastName = scanner.nextLine().trim();
        }
        client.setLastName(lastName);

        client.setUsername(client.getFirstName(), client.getLastName());


        Account account = new Account();
        IBAN IBAN = new IBAN(bankCode);

        //pnD stands for paymentNetworkDigit
        String pnD = "";
        if (paymentNetworkDigit.equalsIgnoreCase("Visa")) {
            pnD = "2";
        } else if(paymentNetworkDigit.equalsIgnoreCase("Mastercard")){
            pnD = "5";
        }

        Card card = new Card(pnD, bankIdentificationNumber);
        account.setIban(IBAN);
        account.setCard(card);
        client.setAccount(account);

        Utils.logEntry("Created client for bank: " + bankName + ", with the following: \n" +
                "-> " + client.getFirstName() + " " + client.getLastName() +"\n" +
                "-> Account ID: " + account.getID() + ", created for: " + client.getUsername() + ", " +
                "with IBAN: " + client.getAccount().getIban().getIBAN() + ", and Card no.: " +
                client.getAccount().getCard().getCardNumber());

        return client;
    }




    public static void viewAccountDetails(){
        System.out.print("In order to verify your identity, please enter your account ID: ");
        String accountID = scanner.next().trim();
        scanner.nextLine();

        for(Bank bank : banks){
            for(Client client : bank.getClients()){
                if(client.getAccount().getID() == Integer.parseInt(accountID)){
                    System.out.println("Account ID verified. Welcome " + client.getUsername());
                    System.out.println("Here are the account informations: \n" +
                            "-> Account ID: " + client.getAccount().getID() + "\n" +
                            "-> IBAN: " + client.getAccount().getIban().getIBAN() + "\n" +
                            "-> Card Number: " + client.getAccount().getCard().getCardNumber() + "\n" +
                            "-> Card PIN code: " + client.getAccount().getCard().getPinCode() + "\n" +
                            "-> Card CVV: " + client.getAccount().getCard().getCVV() + "\n" +
                            "-> Card Expiration Date: " + client.getAccount().getCard().getExpirationDate());
                    continueOrExit();
                }
            }
        }
    }





    public static void continueOrExit(){
        System.out.print("Do you want to continue? (Y/N): ");
        String continueOption = "";
        continueOption = scanner.next();
        scanner.nextLine();
        if(continueOption.isEmpty()){
            System.out.println("You don't have entered a valid option. Please try again.");
            continueOption = scanner.nextLine();
        }

        if(continueOption.equalsIgnoreCase("Y")){
            printMenu();
        } else if(continueOption.equalsIgnoreCase("N")) {
            System.out.println("Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Sorry, that is not a valid option. Please try again.");
            continueOrExit();
        }
    }


    public static void addBank(Bank bank){
        banks.add(bank);
    }





}
