package model;

import java.time.LocalDate;

public class AuditTrail {

    private int id;
    private String entry;
    private LocalDate timestamp;
    private int clientID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return "AuditTrail{" +
                "id=" + id +
                ", entry='" + entry + '\'' +
                ", timestamp=" + timestamp +
                ", clientID=" + clientID +
                '}';
    }
}
