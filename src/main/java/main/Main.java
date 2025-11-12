package main;

import model.*;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Client> BTclients = new ArrayList<>();
    static Bank BT = new Bank("Banca Transilvania", "BTRL20", "BTRO", "Visa");
    static Bank BCR = new Bank("BCR", "BCR20", "BCRO", "Mastercard");
    static Bank ING = new Bank("ING", "ING30", "INGR", "Visa");
    static Bank BRD = new Bank("BRD", "BRD22", "BRDR", "Visa");


    public static void main(String[] args) {
        printMenu();
    }


    public static void printMenu(){
        System.out.println("Welcome!\n");
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
//                viewAccountDetails();
            case 3:
                Utils.getLogs();
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
                Utils.logEntry("Created bank: " + BT.getBankName());
                Client clientBT = createClient(BT.getBankCode(),
                        BT.getPaymentNetwork(), "222111");
                BTclients.add(clientBT);
                BT.setClients(BTclients);
                System.out.println("Your account has been created!");
                System.out.println("Do you want to continue? (Y/N)");
                String continueOption = scanner.nextLine();
                if(continueOption.equalsIgnoreCase("Y")){
                    printMenu();
                } else {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                break;
            case 2:
                Utils.logEntry("Created bank: " + BCR.getBankName());
                createClient(BCR.getBankCode(),
                        BCR.getPaymentNetwork(), "555111");
                printMenu();
                break;
            case 3:
                Utils.logEntry("Created bank: " + ING.getBankName());
                createClient(ING.getBankCode(),
                        ING.getPaymentNetwork(), "222222");
                printMenu();
            case 4:
                Utils.logEntry("Created bank: " + BRD.getBankName());
                createClient(BRD.getBankCode(),
                        BRD.getPaymentNetwork(), "222333");
                printMenu();
                break;
            default:
                System.out.println("You didn't entered a valid option. Please try again.");

        }

    }


    public static Client createClient(String bankCode,
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
        if (paymentNetworkDigit.equals("Visa")) {
            pnD = "2";
        } else if(paymentNetworkDigit.equals("Mastercard")){
            pnD = "5";
        }

        Card card = new Card(pnD, bankIdentificationNumber);
        account.setIban(IBAN);
        account.setCard(card);
        client.setAccount(account);

        Utils.logEntry("Created client: " + client.getFirstName() + " " + client.getLastName());
        Utils.logEntry("Account created for: " + client.getUsername() + ", " +
                "with IBAN: " + client.getAccount().getIban().getIBAN() + ", and Card no.: " +
                client.getAccount().getCard().getCardNumber());

        return client;
    }





}
