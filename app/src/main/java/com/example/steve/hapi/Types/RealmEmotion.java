package com.example.steve.hapi.Types;

import android.location.Location;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by steve on 2017-05-14.
 */

public class RealmEmotion extends RealmObject {
    @PrimaryKey
    private String date;
    private String emotion;
    private String note;
    private double longitude;
    private double latitude;

    public RealmEmotion(){}

    public RealmEmotion(Emotion e){
        emotion = e.getName();
        date = e.getDate();
        note = e.getNote();
        if(e.getLocation() != null){
            longitude = e.getLocation().getLongitude();
            latitude = e.getLocation().getLatitude();
        }
    }

    public String getEmotion(){
        return emotion;
    }

    public String getDate(){
        return date;
    }

    public String getNote() { return note; }

    public double getLongitude() {return longitude; }

    public double getLatitude() { return latitude; }
}
