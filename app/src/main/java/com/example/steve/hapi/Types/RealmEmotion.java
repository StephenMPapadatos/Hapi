package com.example.steve.hapi.Types;

import io.realm.RealmObject;

/**
 * Created by steve on 2017-05-14.
 */

public class RealmEmotion extends RealmObject {
    private String emotion;
    private String date;

    public RealmEmotion(){}

    public RealmEmotion(Emotion e){
        emotion = e.getName();
        date = e.getDate();
    }

    public String getEmotion(){
        return emotion;
    }

    public String getDate(){
        return date;
    }
}
