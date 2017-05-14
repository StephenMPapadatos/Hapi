package com.example.steve.hapi;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by steve on 2017-05-14.
 */

public class UserController {

    public static User getUser(){
        Realm realm = Realm.getDefaultInstance();
        User user = null;
        if (realm.where(User.class).findFirst() != null) {
            user = realm.where(User.class).findFirst();
        }
        realm.close();
        return user;
    }
    public static void setName(String name){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User user = realm.where(User.class).findFirst();
        if (user == null) {
            user = realm.createObject(User.class);
        }
        user.setName(name);
        realm.commitTransaction();
        realm.close();
    }

    public static String getName(){
        Realm realm = Realm.getDefaultInstance();
        String name = null;
        if (realm.where(User.class).findFirst() != null) {
            name = realm.where(User.class).findFirst().getName();
        }
        realm.close();
        return name;
    }

    public static void addEmotion(RealmEmotion e){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User user = realm.where(User.class).findFirst();
        if (user == null) {
            user = realm.createObject(User.class);
        }
        user.addEmotion(e);
        realm.commitTransaction();
        realm.close();
    }

    public static List<Emotion> getEmotions(){
        Realm realm = Realm.getDefaultInstance();
        List<Emotion> emotions = new ArrayList<>();
        if (realm.where(User.class).findFirst() != null) {
            for(RealmEmotion e: realm.where(User.class).findFirst().getEmotions()){
                Emotion emotion = new Emotion(e.getEmotion());
                emotions.add(emotion);
            }
        }
        realm.close();
        return emotions;
    }
}
