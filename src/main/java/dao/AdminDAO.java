package dao;

import enums.Role;
import enums.Status;
import model.Admin;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    public int saveAdmin(Admin admin, Connection connection) throws SQLException {
        String sql = "INSERT INTO admin " +
                "(firstName, lastName, username, password, role, status) " +
                "VALUES (?,?,?,?,?,?)";

        try( PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, admin.getFirstName());
            stmt.setString(2, admin.getLastName());
            stmt.setString(3, admin.getUsername());
            stmt.setString(4, admin.getPassword());
            stmt.setString(5, admin.getRole().name());
            stmt.setString(6, admin.getStatus().name());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                admin.setId(generatedId);
                return generatedId;
            }
        }
        throw new SQLException("Failed to retrieve generated ID.");
    }

    public Admin getAdminByUsername(String username, Connection connection) throws SQLException {
        String sql = "SELECT id, firstName, lastName, username, password, role, status FROM admin WHERE username=?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, username);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setFirstName(resultSet.getString("firstName"));
                admin.setLastName(resultSet.getString("lastName"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
                admin.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));

                return admin;
            }
        }
        return null;
    }

    public List<Admin> getAllAdmins(Connection connection) throws SQLException {
        String sql = "SELECT id, firstName, lastName, " +
                "username, password, role, status " +
                "FROM admin WHERE role = ?";

        List<Admin> admins = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "ADMIN");

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Admin admin = new Admin();
                admin.setId(resultSet.getInt("id"));
                admin.setFirstName(resultSet.getString("firstName"));
                admin.setLastName(resultSet.getString("lastName"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setRole(Role.valueOf(resultSet.getString("role").toUpperCase()));
                admin.setStatus(Status.valueOf(resultSet.getString("status").toUpperCase()));

                admins.add(admin);
            }
        }
        return admins;
    }
}
