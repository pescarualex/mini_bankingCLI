package dao;

import model.Account;

import java.sql.*;

public class AccountDAO {
    public int saveAccount(Account account, Connection connection) throws SQLException {
        String sql = "INSERT INTO account " +
                "(clientId, amountOfMoney) " +
                "VALUES (?,?)";

        try( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1, account.getClientId());
            stmt.setDouble(2, account.getAmountOfMoney());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                account.setId(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }


    public Account getAccountByClientID(int clientID, Connection connection) throws SQLException {
        String sql = "SELECT id, clientId, amountOfMoney FROM account WHERE clientId = ?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, clientID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setClientId(resultSet.getInt("clientId"));
                account.setAmountOfMoney(resultSet.getLong("amountOfMoney"));

                return account;
            }
        }

        return null;
    }

    public void updateAmountOfMoney(int clientId, long amountOfMoney, Connection connection) throws SQLException{
        String sql = "UPDATE account SET amountOfMoney = ? WHERE clientId = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, amountOfMoney);
            stmt.setInt(2, clientId);
            stmt.executeUpdate();
        }
    }
}
