package main;

import model.*;
import service.impl.BankServiceImpl;
import service.impl.ClientServiceIImpl;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    ClientServiceIImpl clientService = new ClientServiceIImpl();
    static BankServiceImpl bankService = new BankServiceImpl();

    final static Bank BT = new Bank("BT", "BTRL20", "BTRO", "Visa");
    final static Bank BCR = new Bank("BCR", "BCR20", "BCRO", "Mastercard");
    final static Bank ING = new Bank("ING", "ING30", "INGR", "Visa");
    final static Bank BRD = new Bank("BRD", "BRD22", "BRDR", "Visa");



    public static void main(String[] args) {
        while(true){
            System.out.println("\tWelcome!");
            System.out.println("1. New client?\n" +
                    "2. Have and account already.");
            int choise = scanner.nextInt();
            switch(choise){
                case 1: createNewClient(); break;
                case 2: alreadyExistingAccountMenu(); break;
            }
        }
    }

    public static void createNewClient(){
        System.out.println("To what bank do you want to create account?");
        System.out.println("1. BT\n" +
                "2. BCR\n" +
                "3. ING\n" +
                "4. BRD");
        int choise = scanner.nextInt();

        switch (choise){
            case 1:
                bankService.addBank(BT);
                break;
            case 2:
                bankService.addBank(BCR);
                break;
            case 3:
                bankService.addBank(ING);
                break;
            case 4:
                bankService.addBank(BRD);
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


}
