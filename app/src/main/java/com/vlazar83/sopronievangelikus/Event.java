package com.vlazar83.sopronievangelikus;

import java.io.Serializable;

public class Event implements Serializable {

    private String name;
    private String fullName;
    private String eventDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Event(String name, String fullName, String eventDate) {
        this.name = name;
        this.fullName = fullName;
        this.eventDate = eventDate;
    }
}
