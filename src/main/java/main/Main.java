package main;

import model.*;
import service.impl.ClientServiceIImpl;
import utils.Utils;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static Bank BT = new Bank("BT", "BTRL20", "BTRO", "Visa");
    static Bank BCR = new Bank("BCR", "BCR20", "BCRO", "Mastercard");
    static Bank ING = new Bank("ING", "ING30", "INGR", "Visa");
    static Bank BRD = new Bank("BRD", "BRD22", "BRDR", "Visa");

    ClientServiceIImpl clientService = new ClientServiceIImpl();

    public static void main(String[] args) {
        Utils.addBank(BT);
        Utils.addBank(BCR);
        Utils.addBank(ING);
        Utils.addBank(BRD);

        System.out.println("\tWelcome!");
        System.out.println("1. New client?\n" +
                "2. Have and account already.");
        int choise = scanner.nextInt();
        switch(choise){
            case 1: createNewClient(); break;
            case 2: alreadyExistingAccountMenu(); break;
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

        switch(choise){
            case 1: viewClientIban(); break;
            case 2: viewClientCardInformation(); break;
            case 3: depositMoney(); break;
            case 4: withdrawMoney(); break;
            case 5: transferMoney(); break;
            default:
                System.out.println("Invalid choose. Try again.");
        }
    }


}
