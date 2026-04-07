package dao;

import db.DatabaseConnection;
import enums.Status;
import model.Account;

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


    public static Account getAccountByClientID(int clientID) throws SQLException {
        String sql = "SELECT id, client_ID, amountOfMoney FROM account WHERE client_ID = ?";

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, clientID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                Account account = new Account();
                account.setID(resultSet.getInt("id"));
                account.setClient_ID(resultSet.getInt("client_ID"));
                account.setAmountOfMoney(resultSet.getLong("amountOfMoney"));

                return account;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public static void updateAmmountOfMoney(int clientId, long ammountOfMoney) {
        String sql = "UPDATE account SET amountOfMoney = ? WHERE client_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, ammountOfMoney);   //
            stmt.setInt(2, clientId);    //
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
