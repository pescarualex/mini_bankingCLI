package dao;

import db.DatabaseConnection;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDAO{
    public void saveClient(Client client){
        String sql = "INSERT INTO clients " +
                "(firstName, lastName, CNP, serialAndNumberOfCI, username, account) " +
                "VALUES (?,?,?,?,?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, client.getFirstName());
            stmt.setString(1, client.getLastName());
            stmt.setString(1, client.getCNP());
            stmt.setString(1, client.getSeriesAndNumberOfCI());
            stmt.setString(1, client.getUsername());
            stmt.setString(1, client.getAccount());
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}