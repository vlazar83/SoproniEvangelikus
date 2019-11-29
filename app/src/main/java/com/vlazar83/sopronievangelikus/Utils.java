package com.vlazar83.sopronievangelikus;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;

public class Utils {

    public static Event convertDocumentDataToEventData(QueryDocumentSnapshot document){

        HashMap tempMap = new HashMap(document.getData());
        Timestamp timestamp = (Timestamp) tempMap.get("eventDateAndTime");
        GeoPoint location = (GeoPoint) tempMap.get("location");

        Long eventDateAndTimeSeconds = timestamp.getSeconds();
        Integer eventDateAndTimeNanoSeconds = timestamp.getNanoseconds();

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        tempMap.remove("eventDateAndTime");
        tempMap.put("eventDateAndTimeSeconds", eventDateAndTimeSeconds);
        tempMap.put("eventDateAndTimeNanoSeconds", eventDateAndTimeNanoSeconds);

        tempMap.remove("location");
        tempMap.put("latitude", latitude);
        tempMap.put("longitude", longitude);

        return new Event(tempMap);

    }

}
