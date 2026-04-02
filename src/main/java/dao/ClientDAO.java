package dao;

import db.DatabaseConnection;
import enums.Role;
import enums.Status;
import model.Client;

import java.sql.*;

public class ClientDAO{
    public static int saveClient(Client client) throws SQLException {
        String sql = "INSERT INTO client " +
                "(firstName, lastName, CNP, seriesAndNumberOfCI, username, role, status, bankID) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getCNP());
            stmt.setString(4, client.getSeriesAndNumberOfCI());
            stmt.setString(5, client.getUsername());
            stmt.setString(6, client.getRole().name());
            stmt.setString(7, client.getStatus().name());
            stmt.setInt(8, client.getBankID());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                client.setId(generatedId);
                return generatedId;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }

    public static Client getClientByID(int clientID) throws SQLException {
        String sql = "SELECT id, firstName, lastName, CNP, seriesAndNumberOfCI, username, role, status, bankID FROM client WHERE id=?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, clientID);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                client.setCNP(resultSet.getString("CNP"));
                client.setSeriesAndNumberOfCI(resultSet.getString("seriesAndNumberOfCI"));
                client.setUsername(resultSet.getString("username"));
                client.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
                client.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
                client.setBankID(resultSet.getInt("bankID"));

                return client;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        //if no client, return null
        return null;
    }

}