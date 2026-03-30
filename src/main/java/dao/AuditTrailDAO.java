package dao;

import db.DatabaseConnection;
import model.AuditTrail;

import java.sql.*;
import java.time.LocalDate;

public class AuditTrailDAO {
    public static int saveAuditTrail(AuditTrail auditTrail) throws SQLException {
        String sql = "INSERT INTO auditTrail " +
                "(entry, timestamp, clientID) " +
                "VALUES (?,?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, auditTrail.getEntry());
            stmt.setString(2, auditTrail.getTimestamp().toString());
            stmt.setInt(3, auditTrail.getClientID());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                auditTrail.setId(generatedId);   // actualizezi obiectul
                return generatedId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new SQLException("Failed to retrieve generated ID.");
    }


    public static AuditTrail getAuditTrailByClientID(int clientID) throws SQLException {
        String sql = "SELECT id, entry, timestamp, clientID FROM auditTrail WHERE client_ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientID);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                AuditTrail auditTrail = new AuditTrail();
                auditTrail.setId(resultSet.getInt("id"));
                auditTrail.setEntry(resultSet.getString("entry"));
                auditTrail.setTimestamp(LocalDate.parse(resultSet.getString("timestamp")));
                auditTrail.setClientID(resultSet.getInt("clientID"));

                return auditTrail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static AuditTrail getAllAuditTrail() throws SQLException {
        String sql = "SELECT * FROM auditTrail";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                AuditTrail auditTrail = new AuditTrail();
                auditTrail.setId(resultSet.getInt("id"));
                auditTrail.setEntry(resultSet.getString("entry"));
                auditTrail.setTimestamp(LocalDate.parse(resultSet.getString("timestamp")));
                auditTrail.setClientID(resultSet.getInt("clientID"));

                return auditTrail;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
