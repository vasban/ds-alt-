package model;

import java.io.Serializable;
import java.util.UUID;

public class Request implements Serializable {

    private UUID id;
    private String action;
    private Accommodation accommodation;

    public Request(String action, Accommodation accommodation) {
        this.id = UUID.randomUUID();
        this.action = action;
        this.accommodation = accommodation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", accommodation=" + accommodation +
                '}';
    }
}
