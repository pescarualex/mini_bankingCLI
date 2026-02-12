package dao;

import db.DatabaseConnection;
import model.Bank;

import java.sql.*;

public class BankDAO{
    public static int saveBank(Bank bank) throws SQLException {
        String sql = "INSERT INTO bank " +
                "(bankName, bankSwift, paymentNetwork, bankCode, client_ID, account_ID) " +
                "VALUES (?,?,?,?,?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, bank.getBankName());
            stmt.setString(2, bank.getBankSwift());
            stmt.setString(3, bank.getPaymentNetwork());
            stmt.setString(4, bank.getBankCode());
            stmt.setInt(5, bank.getClientID());
            stmt.setInt(6, bank.getAccountID());

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

    public static void getPaymentNetworkFromBank(Bank bank){
        String sql = "SELECT paymentNetwork from banking WHERE client_ID =?";

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, bank.getBankName());
            stmt.setInt(2, bank.getClientID());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}