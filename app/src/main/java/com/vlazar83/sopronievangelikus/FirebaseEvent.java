package com.vlazar83.sopronievangelikus;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class FirebaseEvent {

    private String name;
    private String fullName;
    private Timestamp eventDateAndTime;
    private GeoPoint location;
    private String comments;
    private Boolean withCommunion;
    private String pastorName;
    private String typeOfEvent;

    public FirebaseEvent(String name, String fullName, Timestamp eventDateAndTime, GeoPoint location, String comments, Boolean withCommunion, String pastorName, String typeOfEvent) {
        this.name = name;
        this.fullName = fullName;
        this.eventDateAndTime = eventDateAndTime;
        this.location = location;
        this.comments = comments;
        this.withCommunion = withCommunion;
        this.pastorName = pastorName;
        this.typeOfEvent = typeOfEvent;
    }
}
