package dao;

import db.DatabaseConnection;
import model.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankDAO{
    public int saveBank(Bank bank, Connection connection) throws SQLException {
        String sql = "INSERT INTO bank " +
                "(bankName, bankSwift, paymentNetwork, bankCode) " +
                "VALUES (?,?,?,?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, bank.getBankName());
            stmt.setString(2, bank.getBankSwift());
            stmt.setString(3, bank.getPaymentNetwork());
            stmt.setString(4, bank.getBankCode());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                bank.setID(generatedId);
                return generatedId;
            }
        }
        throw new SQLException("Failed to retrieve generated ID.");
    }

    public Bank getBankByClientID(int client_ID, Connection connection) throws SQLException {
        String sql = "SELECT id, bankName, bankSwift, paymentNetwork, bankCode " +
                "FROM bank WHERE client_ID = ?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, client_ID);


            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Bank bank = new Bank();
                bank.setID(resultSet.getInt("id"));
                bank.setBankName(resultSet.getString("bankName"));
                bank.setBankSwift(resultSet.getString("bankSwift"));
                bank.setPaymentNetwork(resultSet.getString("paymentNetwork"));
                bank.setBankCode(resultSet.getString("bankCode"));

                return bank;
            }
        }
        return null;
    }

    public Bank getBankByBankName(String bankName, Connection connection) throws SQLException {
        String sql = "SELECT id, bankName, bankSwift, paymentNetwork, bankCode " +
                "FROM bank WHERE bankName = ?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, bankName);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Bank bank = new Bank();
                bank.setID(resultSet.getInt("id"));
                bank.setBankName(resultSet.getString("bankName"));
                bank.setBankSwift(resultSet.getString("bankSwift"));
                bank.setPaymentNetwork(resultSet.getString("paymentNetwork"));
                bank.setBankCode(resultSet.getString("bankCode"));

                return bank;
            }
        }
        return null;
    }

    public List<Bank> getAllBanks(Connection connection) throws SQLException {
        String sql = "SELECT id, bankName, bankSwift, paymentNetwork, bankCode FROM bank";

        List<Bank> banks = new ArrayList<>();

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Bank bank = new Bank();
                bank.setID(resultSet.getInt("id"));
                bank.setBankName(resultSet.getString("bankName"));
                bank.setBankSwift(resultSet.getString("bankSwift"));
                bank.setPaymentNetwork(resultSet.getString("paymentNetwork"));
                bank.setBankCode(resultSet.getString("bankCode"));
                banks.add(bank);
            }
        }
        return banks;
    }
}