package com.vlazar83.sopronievangelikus;

import java.io.Serializable;
import java.util.Map;

public class Event implements Serializable {

    private String name;
    private String fullName;
    private Long eventDateAndTimeSeconds;
    private Integer eventDateAndTimeNanoSeconds;
    private Double latitude;
    private Double longitude;
    private String comments;
    private Boolean withCommunion;
    private String pastorName;
    private String typeOfEvent;
    private String eventImage;
    private String language;

    public Event(String name, String fullName, Long eventDateAndTimeSeconds, Integer eventDateAndTimeNanoSeconds, Double latitude, Double longitude, String comments, Boolean withCommunion, String pastorName, String typeOfEvent, String eventImage, String language) {
        this.name = name;
        this.fullName = fullName;
        this.eventDateAndTimeSeconds = eventDateAndTimeSeconds;
        this.eventDateAndTimeNanoSeconds = eventDateAndTimeNanoSeconds;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comments = comments;
        this.withCommunion = withCommunion;
        this.pastorName = pastorName;
        this.typeOfEvent = typeOfEvent;
        this.eventImage = eventImage;
        this.language = language;
    }

    public Event(Map map) {
        this((String)map.get("name"), (String)map.get("fullName"), (Long)map.get("eventDateAndTimeSeconds"),  (Integer)map.get("eventDateAndTimeNanoSeconds"), (Double)map.get("latitude"), (Double)map.get("longitude"), (String)map.get("comments"), (Boolean)map.get("withCommunion"), (String)map.get("pastorName"), (String)map.get("typeOfEvent"), (String)map.get("eventImage"), (String)map.get("language"));
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Event() {
    }

    public Long getEventDateAndTimeSeconds() {
        return eventDateAndTimeSeconds;
    }

    public void setEventDateAndTimeSeconds(Long eventDateAndTimeSeconds) {
        this.eventDateAndTimeSeconds = eventDateAndTimeSeconds;
    }

    public Integer getEventDateAndTimeNanoSeconds() {
        return eventDateAndTimeNanoSeconds;
    }

    public void setEventDateAndTimeNanoSeconds(Integer eventDateAndTimeNanoSeconds) {
        this.eventDateAndTimeNanoSeconds = eventDateAndTimeNanoSeconds;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getWithCommunion() {
        return withCommunion;
    }

    public void setWithCommunion(Boolean withCommunion) {
        this.withCommunion = withCommunion;
    }

    public String getPastorName() {
        return pastorName;
    }

    public void setPastorName(String pastorName) {
        this.pastorName = pastorName;
    }

    public String getTypeOfEvent() {
        return typeOfEvent;
    }

    public void setTypeOfEvent(String typeOfEvent) {
        this.typeOfEvent = typeOfEvent;
    }

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }
}
