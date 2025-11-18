package model;

public class Client {
    private String firstName;
    private String lastName;
    private String CNP;
    private String seriesAndNumberOfCI;
    private String username;
    private Account account;

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

    public String getUsername(){
        return username;
    }

    public void setUsername(String firstName, String lastName){
        this.username = firstName + " " + lastName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}


