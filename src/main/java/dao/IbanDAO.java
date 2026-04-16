package dao;

import model.IBAN;

import java.sql.*;

public class IbanDAO {

    public static int saveIBAN(IBAN iban, Connection connection) throws SQLException {
        String sql = "INSERT INTO iban " +
                "(IBAN, accountId) " +
                "VALUES (?,?)";

        try( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, iban.getIBAN());
            stmt.setInt(2, iban.getAccountId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                iban.setId(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        }
        throw new SQLException("Failed to retrieve generated ID.");
    }


    public static IBAN getIbanByAccountID(int accountID, Connection connection) throws SQLException {
        String sql = "SELECT id, IBAN, accountId" +
                " FROM iban WHERE accountId = ?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, accountID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                IBAN iban = new IBAN();
                iban.setId(resultSet.getInt("id"));
                iban.setIBAN(resultSet.getString("IBAN"));
                iban.setAccountId(resultSet.getInt("accountId"));

                return iban;
            }
        }
        return null;
    }
}
