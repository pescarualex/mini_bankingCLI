package service.impl;

import model.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl {

    private final static List<Bank> BANKS = new ArrayList<>();

    public void addBank(Bank bank){
        BANKS.add(bank);
    }

    public String getBankCode(String bankID){
        String bankCode = "";
        for (Bank bank : BANKS) {
            if (bankID.equals(bank.getID())) {
                bankCode = bank.getBankCode();
                break;
            } else {
                System.out.println("No bank with ID: " + bankID + " found.");
            }
        }
        return bankCode;
    }

    public String getPaymentNetwork(String bankID){
        String paymentNetwork = "";
        for (Bank bank : BANKS) {
            if (bankID.equals(bank.getID())) {
                paymentNetwork = bank.getPaymentNetwork();
                break;
            } else {
                System.out.println("No bank with ID: " + bankID + " found.");
            }
        }
        return paymentNetwork;
    }






}
