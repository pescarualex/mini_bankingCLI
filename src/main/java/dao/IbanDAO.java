package dao;

import db.DatabaseConnection;
import model.Card;
import model.IBAN;

import java.sql.*;
import java.time.LocalDate;

public class IbanDAO {

    public static int saveIBAN(IBAN iban) throws SQLException {
        String sql = "INSERT INTO iban " +
                "(IBAN, account_id) " +
                "VALUES (?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, iban.getIBAN());
            stmt.setInt(2, iban.getAccount_id());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                iban.setId(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }


    public static IBAN getIbanByAccountID(int accountID) throws SQLException {
        String sql = "SELECT id, IBAN, account_id" +
                " FROM iban WHERE account_ID = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, accountID);

            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                IBAN iban = new IBAN();
                iban.setId(resultSet.getInt("id"));
                iban.setIBAN(resultSet.getString("IBAN"));
                iban.setAccount_id(resultSet.getInt("account_id"));

                return iban;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
