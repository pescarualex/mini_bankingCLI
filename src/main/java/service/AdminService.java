package service;

import exceptions.AdminNotFoundException;

import java.sql.Connection;

public interface AdminService {
    void createAdmin(Connection connection) throws AdminNotFoundException;
}
