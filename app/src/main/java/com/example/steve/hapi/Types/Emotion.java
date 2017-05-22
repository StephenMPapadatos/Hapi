package com.example.steve.hapi.Types;

import android.location.Location;

/**
 * Created by steve on 2017-05-14.
 */

public class Emotion {
    private String mEmotionName;
    private String mEmotionDate;
    private String mEmotionNote;
    private Location mEmotionLocation;

    public Emotion(String name){
        this(name, null, null, null);
    }

    public Emotion(String name, String date){
        this(name, date, null, null);

    }

    public Emotion(String name, String date, Location location){
        this(name, date, location, null);
    }

    public Emotion(String name, String date, Location location, String note){
        mEmotionName = name;
        mEmotionDate = date;
        mEmotionLocation = location;
        mEmotionNote = note;
    }

    public String getName(){
        return mEmotionName;
    }

    public void setName(String name){
        this.mEmotionName = name;
    }

    public String getDate() {
        return mEmotionDate;
    }

    public void setDate(String date){
        this.mEmotionDate = date;
    }

    public String getNote() {
        return mEmotionNote;
    }

    public void setNote(String note) {
        this.mEmotionNote = note;
    }

    public Location getLocation() {
        return mEmotionLocation;
    }

    public void setLocation(Location location) {
        this.mEmotionLocation = location;
    }
}
