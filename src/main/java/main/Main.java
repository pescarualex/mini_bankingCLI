package main;

import dao.*;
import enums.Role;
import enums.Status;
import exceptions.CounterExceededException;
import model.*;
import service.impl.*;
import utils.Utils;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws CounterExceededException, SQLException {

        Bank bt = BankDAO.getBankByBankName("BT");
        System.out.println(bt);


        while(true){
            System.out.println("Welcome!\n" +
                    "1. Register\n" +
                    "2. Login");

            int option = Utils.readInputInteger();

            switch (option){
                case 1: register(); break;
                case 2: login(); break;
            }
        }


    }

    private static void register() {
    }

    private static void login() {
        
    }
}
