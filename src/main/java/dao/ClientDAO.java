package dao;

import db.DatabaseConnection;
import enums.Role;
import enums.Status;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO{
    public static int saveClient(Client client, Connection connection) throws SQLException {
        String sql = "INSERT INTO client " +
                "(firstName, lastName, CNP, seriesAndNumberOfCI, username, password, role, status, bankID) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getCNP());
            stmt.setString(4, client.getSeriesAndNumberOfCI());
            stmt.setString(5, client.getUsername());
            stmt.setString(6, client.getPassword());
            stmt.setString(7, client.getRole().name());
            stmt.setString(8, client.getStatus().name());
            stmt.setInt(9, client.getBankID());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                client.setId(generatedId);
                return generatedId;
            }
        }
        throw new SQLException("Failed to retrieve generated ID.");
    }

    public static Client getClientByID(int clientID, Connection connection) throws SQLException {
        String sql = "SELECT id, firstName, lastName, CNP, seriesAndNumberOfCI, username, password, role, status, bankID FROM client WHERE id=?";

        try( PreparedStatement stmt = connection.prepareStatement(sql)){
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
                client.setPassword(resultSet.getString("password"));
                client.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
                client.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
                client.setBankID(resultSet.getInt("bankID"));

                return client;
            }
        }
        return null;
    }

    public static Client getClientByUsername(String username, Connection connection) throws SQLException {
        String sql = "SELECT id, firstName, lastName, CNP, seriesAndNumberOfCI, username, password, role, status, bankID FROM client WHERE username=?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, username);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                client.setCNP(resultSet.getString("CNP"));
                client.setSeriesAndNumberOfCI(resultSet.getString("seriesAndNumberOfCI"));
                client.setUsername(resultSet.getString("username"));
                client.setPassword(resultSet.getString("password"));
                client.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
                client.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
                client.setBankID(resultSet.getInt("bankID"));

                return client;
            }
        }
        return null;
    }


    public static void updateClientStatus(int clientId, Status status, Connection connection) throws SQLException{
        String sql = "UPDATE client SET status = ? WHERE id = ?";

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, status.name());   //
            stmt.setInt(2, clientId);    //
            stmt.executeUpdate();
        }
    }

    public static void updateClientRole(int clientId, Role role, Connection connection) throws SQLException {
        String sql = "UPDATE client SET role = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role.name());   //
            stmt.setInt(2, clientId);    //
            stmt.executeUpdate();
        }
    }


    public static List<Client> getClientsWhereStatusPending(Connection connection) throws SQLException{
        String sql = "SELECT id, firstName, lastName, CNP, seriesAndNumberOfCI, " +
                "username, password, role, status, bankID " +
                "FROM client WHERE status = ?";

        List<Client> pendingClients = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "PENDING");

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                client.setCNP(resultSet.getString("CNP"));
                client.setSeriesAndNumberOfCI(resultSet.getString("seriesAndNumberOfCI"));
                client.setUsername(resultSet.getString("username"));
                client.setPassword(resultSet.getString("password"));
                client.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
                client.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));
                client.setBankID(resultSet.getInt("bankID"));

                pendingClients.add(client);
            }
        }
        return pendingClients;
    }



}