package service.impl;

import model.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl {

    List<Bank> banks = new ArrayList<>();

    public void addBank(Bank bank){
        banks.add(bank);
    }

    public String getBankCode(String ID){
        String bankCode = "";
        for (Bank bank : banks) {
            if (bank.getID().equals(ID)){
                bankCode = bank.getBankCode();
                break;
            }
        }
        return bankCode;
    }






}
