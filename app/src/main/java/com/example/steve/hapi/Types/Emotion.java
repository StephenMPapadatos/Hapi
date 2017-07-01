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

    public Emotion(String name) {
        this(name, null, null, null);
    }

    public Emotion(String name, String date) {
        this(name, date, null, null);

    }

    public Emotion(String name, String date, String note) {
        this(name, date, note, null);
    }

    public Emotion(String name, String date, String note,  Location location) {
        mEmotionName = name;
        mEmotionDate = date;
        mEmotionLocation = location;
        mEmotionNote = note;
    }

    public Emotion(RealmEmotion e){
        mEmotionName = e.getEmotion();
        mEmotionDate = e.getDate();
        Location location = new Location("Dummy");
        location.setLatitude(e.getLatitude());
        location.setLongitude(e.getLongitude());
        mEmotionLocation = location;
        mEmotionNote = e.getNote();
    }

    public String getName() {
        return mEmotionName;
    }

    public void setName(String name) {
        this.mEmotionName = name;
    }

    public String getDate() {
        return mEmotionDate;
    }

    public void setDate(String date) {
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
