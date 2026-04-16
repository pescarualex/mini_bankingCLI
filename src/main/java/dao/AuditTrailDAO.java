package dao;

import db.DatabaseConnection;
import model.AuditTrail;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuditTrailDAO {
    public static int saveAuditTrail(AuditTrail auditTrail, Connection connection) throws SQLException {
        String sql = "INSERT INTO audittrail " +
                "(entry, timestamp, clientID) " +
                "VALUES (?,?,?)";

        try ( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
        }
        throw new SQLException("Failed to retrieve generated ID.");
    }


    public static List<AuditTrail> getAuditTrailByClientID(int clientID, Connection connection) throws SQLException {
        String sql = "SELECT id, entry, timestamp, clientID FROM audittrail WHERE clientID = ?";

        List<AuditTrail> auditTrailEntries = new ArrayList<>();

        try ( PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, clientID);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                AuditTrail auditTrail = new AuditTrail();
                auditTrail.setId(resultSet.getInt("id"));
                auditTrail.setEntry(resultSet.getString("entry"));
                auditTrail.setTimestamp(LocalDate.parse(resultSet.getString("timestamp")));
                auditTrail.setClientID(resultSet.getInt("clientID"));
                auditTrailEntries.add(auditTrail);
            }
        }
        return auditTrailEntries;
    }

    public static List<AuditTrail> getAllAuditTrail(Connection connection) throws SQLException {
        String sql = "SELECT id, entry, timestamp, clientID FROM audittrail";

        List<AuditTrail> auditTrailEntries = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                AuditTrail auditTrail = new AuditTrail();
                auditTrail.setId(resultSet.getInt("id"));
                auditTrail.setEntry(resultSet.getString("entry"));
                auditTrail.setTimestamp(LocalDate.parse(resultSet.getString("timestamp")));
                auditTrail.setClientID(resultSet.getInt("clientID"));
                auditTrailEntries.add(auditTrail);
            }
        }
        return auditTrailEntries;
    }

}
