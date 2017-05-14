package com.example.steve.hapi;

import io.realm.RealmObject;

/**
 * Created by steve on 2017-05-14.
 */

public class RealmEmotion extends RealmObject {
    private String emotion;

    public RealmEmotion(){}

    public RealmEmotion(String e){
        emotion = e;
    }

    public String getEmotion(){
        return emotion;
    }
}
