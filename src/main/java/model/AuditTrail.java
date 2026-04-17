package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuditTrail {

    private int id;
    private String entry;
    private LocalDateTime timestamp;
    private int clientId;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "AuditTrail{" +
                "id=" + id +
                ", entry='" + entry + '\'' +
                ", timestamp=" + timestamp +
                ", clientID=" + clientId +
                '}';
    }
}
