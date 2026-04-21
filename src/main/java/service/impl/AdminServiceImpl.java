package service.impl;

import dao.AdminDAO;
import enums.Role;
import enums.Status;
import exceptions.AdminNotFoundException;
import exceptions.AuditTrailNotSavedException;
import model.Admin;
import model.Client;
import service.AdminService;
import utils.PasswordUtils;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {

    private final AdminDAO adminDAO;

    public AdminServiceImpl(AdminDAO adminDAO){
        this.adminDAO = adminDAO;
    }

    @Override
    public void createAdmin(Connection connection) throws AdminNotFoundException {
        Admin admin = new Admin();

        System.out.println("Enter first name: ");
        admin.setFirstName(Utils.readInputString());

        System.out.println("Enter last name: ");
        admin.setLastName(Utils.readInputString());

        System.out.println("Set a username:");
        admin.setUsername(Utils.readInputString());

        System.out.println("Set a password:");
        String rawPassword = Utils.readInputString();
        String hashedPassword = PasswordUtils.hash(rawPassword);
        admin.setPassword(hashedPassword);

        admin.setRole(Role.ADMIN);
        admin.setStatus(Status.APPROVED);

        int adminID = 0;
        try {
            adminID = adminDAO.saveAdmin(admin, connection);
        } catch (SQLException e) {
            throw new AdminNotFoundException("Admin not saved.", e);
        }

        System.out.println("Admin created successfully!");

        try {
            Utils.logEntry("First Name: " + admin.getFirstName() +
                    ", Last Name: " + admin.getLastName(), adminID, connection);
        } catch (AuditTrailNotSavedException e) {
            System.out.println("Audit trail entry not saved for admin creation.");
        }
    }
}
