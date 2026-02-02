package dao;

import db.DatabaseConnection;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDAO{
    public static void saveClient(Client client){
        String sql = "INSERT INTO clients " +
                "( id, firstName, lastName, CNP, serialAndNumberOfCI, username) " +
                "VALUES (?,?,?,?,?,?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, client.getId());
            stmt.setString(2, client.getFirstName());
            stmt.setString(3, client.getLastName());
            stmt.setString(4, client.getCNP());
            stmt.setString(5, client.getSeriesAndNumberOfCI());
            stmt.setString(6, client.getUsername());
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}