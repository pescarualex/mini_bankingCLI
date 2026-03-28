package model;

public class Client {
    private int id = 0;
    private String firstName;
    private String lastName;
    private String CNP;
    private String seriesAndNumberOfCI;
    private String username;

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


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CNP='" + CNP + '\'' +
                ", seriesAndNumberOfCI='" + seriesAndNumberOfCI + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}


