package model;

import enums.Role;
import enums.Status;

public class Client extends User{
    private String CNP;
    private String seriesAndNumberOfCI;

    private int bankID;

    public Client(){
        setRole(Role.CLIENT);
        setStatus(Status.PENDING);
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

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    @Override
    public String toString() {
        return super.toString() + "Client{" +
                ", CNP='" + CNP + '\'' +
                ", seriesAndNumberOfCI='" + seriesAndNumberOfCI + '\'' +
                ", bankID=" + bankID + '\'' +
                '}';
    }
}


