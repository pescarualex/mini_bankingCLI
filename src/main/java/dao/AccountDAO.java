package dao;

import db.DatabaseConnection;
import enums.Status;
import model.Account;

import java.sql.*;

public class AccountDAO {
    public static int saveAccount(Account account, Connection connection) throws SQLException {
        String sql = "INSERT INTO account " +
                "(client_id, amountOfMoney) " +
                "VALUES (?,?)";

        try( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, account.getClient_ID());
            stmt.setDouble(2, account.getAmountOfMoney());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                account.setID(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }


    public static Account getAccountByClientID(int clientID, Connection connection) throws SQLException {
        String sql = "SELECT id, client_ID, amountOfMoney FROM account WHERE client_ID = ?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, clientID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                Account account = new Account();
                account.setID(resultSet.getInt("id"));
                account.setClient_ID(resultSet.getInt("client_ID"));
                account.setAmountOfMoney(resultSet.getLong("amountOfMoney"));

                return account;
            }
        }

        return null;
    }

    public static void updateAmmountOfMoney(int clientId, long ammountOfMoney, Connection connection) throws SQLException{
        String sql = "UPDATE account SET amountOfMoney = ? WHERE client_id = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, ammountOfMoney);   //
            stmt.setInt(2, clientId);    //
            stmt.executeUpdate();
        }
    }
}
