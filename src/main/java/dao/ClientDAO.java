package dao;

import db.DatabaseConnection;
import model.Client;

import java.sql.*;

public class ClientDAO{
    public static int saveClient(Client client) throws SQLException {
        String sql = "INSERT INTO client " +
                "(firstName, lastName, CNP, seriesAndNumberOfCI, username) " +
                "VALUES (?,?,?,?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getCNP());
            stmt.setString(4, client.getSeriesAndNumberOfCI());
            stmt.setString(5, client.getUsername());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                client.setId(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }
}