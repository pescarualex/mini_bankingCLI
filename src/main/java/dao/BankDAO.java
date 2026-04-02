package dao;

import db.DatabaseConnection;
import model.Bank;

import java.sql.*;

public class BankDAO{
    public static int saveBank(Bank bank) throws SQLException {
        String sql = "INSERT INTO bank " +
                "(bankName, bankSwift, paymentNetwork, bankCode) " +
                "VALUES (?,?,?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, bank.getBankName());
            stmt.setString(2, bank.getBankSwift());
            stmt.setString(3, bank.getPaymentNetwork());
            stmt.setString(4, bank.getBankCode());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                bank.setID(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }

    public static Bank getBankByClientID(int client_ID){
        String sql = "SELECT ID, bankName, bankSwift, paymentNetwork, bankCode, account_ID, client_ID " +
                "from bank WHERE client_ID = ?";

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Bank getBankByBankName(String bankName){
        String sql = "SELECT ID, bankName, bankSwift, paymentNetwork, bankCode, account_ID, client_ID " +
                "from bank WHERE bankName = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}