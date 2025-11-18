package main;

import model.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Client> BTclients = new ArrayList<>();
    static List<Client> BCRclients = new ArrayList<>();
    static List<Client> INGclients = new ArrayList<>();
    static List<Client> BRDclients = new ArrayList<>();

    static Bank BT = new Bank("Banca Transilvania", "BTRL20", "BTRO", "Visa");
    static Bank BCR = new Bank("BCR", "BCR20", "BCRO", "Mastercard");
    static Bank ING = new Bank("ING", "ING30", "INGR", "Visa");
    static Bank BRD = new Bank("BRD", "BRD22", "BRDR", "Visa");

    public static void main(String[] args) {
        Utils.addBank(BT);
        Utils.addBank(BCR);
        Utils.addBank(ING);
        Utils.addBank(BRD);

        System.out.println("\tWelcome!");
        while (true) {
            printMenu();
        }

    }

    public static void printMenu(){
        System.out.println();
        System.out.println("1. Create Account");
        System.out.println("2. View account details");
        System.out.println("3. View Logs");
        System.out.println("4. Exit");

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
                break;
            case 4:
                System.exit(0);
        }
    }

    public static void createAccount(){
        System.out.println("You can create an account for the following banks: \n" +
                "1. Banca Transilvania,\n" +
                "2. BCR,\n" +
                "3. ING,\n" +
                "4. BRD\n" +
                "Please choose one of the banks: ");

        System.out.print("-> ");
        String option = scanner.next();
        scanner.nextLine();

        if(!option.matches("^[0-9]*$")){
            System.out.println("Incorrect selection. Please enter an option from the menu.");
            System.out.print("-> ");
            option = scanner.next();
            scanner.nextLine();
        }

        switch(option){
            case "1":
                Utils.logEntry("Created account at bank: " + BT.getBankName());
                Client clientBT = createClient(BT.getBankName(), BT.getBankCode(),
                        BT.getPaymentNetwork(), "222111");
                BTclients.add(clientBT);
                BT.setClients(BTclients);
                System.out.println("Your account has been created with ID: " +
                        clientBT.getAccount().getID());
                break;
            case "2":
                Utils.logEntry("Created account at bank: " + BCR.getBankName());
                Client clientBCR = createClient(BCR.getBankName(), BCR.getBankCode(),
                        BCR.getPaymentNetwork(), "555111");
                BCRclients.add(clientBCR);
                BCR.setClients(BCRclients);
                System.out.println("Your account has been created with ID: " +
                        clientBCR.getAccount().getID());
                break;
            case "3":
                Utils.logEntry("Created account at bank: " + ING.getBankName());
                Client clientING = createClient(ING.getBankName(), ING.getBankCode(),
                        ING.getPaymentNetwork(), "222222");
                INGclients.add(clientING);
                ING.setClients(INGclients);
                System.out.println("Your account has been created with ID: " +
                        clientING.getAccount().getID());
                break;
            case "4":
                Utils.logEntry("Created account at bank: " + BRD.getBankName());
                Client clientBRD = createClient(BRD.getBankName(), BRD.getBankCode(),
                        BRD.getPaymentNetwork(), "222333");
                BRDclients.add(clientBRD);
                BRD.setClients(BRDclients);
                System.out.println("Your account has been created with ID: " +
                        clientBRD.getAccount().getID());
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

        System.out.print("Please enter the CNP code: ");
        String CNP = scanner.nextLine().trim();
        if(CNP.isEmpty()){
            System.out.println("CNP cannot be empty, please try again.");
            CNP = scanner.nextLine().trim();
        }
        client.setCNP(CNP);

        System.out.print("Please enter the series and number of CI: ");
        String seriesAndNumber = scanner.nextLine().trim();
        if(seriesAndNumber.isEmpty()){
            System.out.println("Series and number cannot be empty, please try again.");
            seriesAndNumber = scanner.nextLine().trim();
        }
        client.setSeriesAndNumberOfCI(seriesAndNumber);

        client.setUsername(client.getFirstName(), client.getLastName());

        Account account = new Account(bankCode);
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
                "\t-> First Name: " + client.getFirstName() + ", Last Name: " + client.getLastName() +"\n" +
                "\t-> Account ID: " + account.getID() + ", created for: " + client.getUsername() + ", " +
                "with IBAN: " + client.getAccount().getIban().getIBAN() + ", and Card no.: " +
                client.getAccount().getCard().getCardNumber());

        return client;
    }

    public static void viewAccountDetails(){
        System.out.print("In order to verify your identity, please enter your account ID: ");
        String accountID = scanner.next().trim();
        scanner.nextLine();

            for(Bank bank : Utils.banks){
                if(bank.getClients() != null){
                    for(Client client : bank.getClients()){
                        if(!accountID.equals(client.getAccount().getID())){
                            System.out.println("No account ID found. Please try again.");
                            break;
                        }

                        if(client.getAccount().getID().equals(accountID)){
                            System.out.println("\tAccount ID verified. Welcome " + client.getUsername());
                            System.out.println("\tHere are the account informations: \n" +
                                    "\t\t-> Account ID: " + client.getAccount().getID() + "\n" +
                                    "\t\t-> IBAN: " + client.getAccount().getIban().getIBAN() + "\n" +
                                    "\t\t-> Card Number: " + client.getAccount().getCard().getCardNumber() + "\n" +
                                    "\t\t-> Card PIN code: " + client.getAccount().getCard().getPinCode() + "\n" +
                                    "\t\t-> Card CVV: " + client.getAccount().getCard().getCVV() + "\n" +
                                    "\t\t-> Card Expiration Date: " + client.getAccount().getCard().getExpirationDate());
                            break;
                        }
                    }
                }
            }
    }







}
