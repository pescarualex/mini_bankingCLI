package dao;

import db.DatabaseConnection;
import model.Account;
import model.Client;

import java.sql.*;

public class AccountDAO {
    public static int saveAccount(Account account) throws SQLException {
        String sql = "INSERT INTO account " +
                "(client_id, amountOfMoney) " +
                "VALUES (?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, account.getClient_ID());
            stmt.setString(2, String.valueOf(account.getAmountOfMoney()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                account.setID(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }
}
