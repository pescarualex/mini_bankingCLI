package model;

import enums.Role;
import enums.Status;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String CNP;
    private String seriesAndNumberOfCI;
    private String username;
    private String password;
    private Role role;
    private Status status;
    private int bankID;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getSeriesAndNumberOfCI() {
        return seriesAndNumberOfCI;
    }

    public void setSeriesAndNumberOfCI(String seriesAndNumberOfCI) {
        this.seriesAndNumberOfCI = seriesAndNumberOfCI;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CNP='" + CNP + '\'' +
                ", seriesAndNumberOfCI='" + seriesAndNumberOfCI + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ". role=" + role.name() + '\'' +
                ", status=" + status.name() + '\'' +
                ", bankID=" + bankID + '\'' +
                '}';
    }
}


