package service.impl;

import model.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl {

    private final static List<Bank> BANKS = new ArrayList<>();

    public void addBank(Bank bank){
        BANKS.add(bank);
    }

    public String getBankCode(String ID){
        String bankCode = "";
        for (Bank bank : BANKS) {
            if (ID.equals(bank.getID())) {
                bankCode = bank.getBankCode();
                break;
            } else {
                System.out.println("No bank with ID: " + ID + " found.");
            }
        }
        return bankCode;
    }






}
