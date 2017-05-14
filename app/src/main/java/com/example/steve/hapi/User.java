package com.example.steve.hapi;

import java.util.List;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by steve on 2017-05-14.
 */

public class User extends RealmObject {
    private String name;
    private RealmList<RealmEmotion> emotions;

    public void addEmotion(RealmEmotion e){
        emotions.add(e);
    }

    public List<RealmEmotion> getEmotions(){
        return emotions;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }
}
