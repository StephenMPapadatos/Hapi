package com.example.steve.hapi.Controllers;

import com.example.steve.hapi.Types.Emotion;
import com.example.steve.hapi.Types.RealmEmotion;
import com.example.steve.hapi.Types.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by steve on 2017-05-14.
 */

public class UserController {

    public static RealmConfiguration getRealmConfiguration(){
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        return realmConfiguration;
    }

    public static User getUser(){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        User user = null;
        if (realm.where(User.class).findFirst() != null) {
            user = realm.where(User.class).findFirst();
        }
        realm.close();
        return user;
    }
    public static void setName(String name){
        Realm realm = Realm.getInstance(getRealmConfiguration());
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
        Realm realm = Realm.getInstance(getRealmConfiguration());
        String name = null;
        if (realm.where(User.class).findFirst() != null) {
            name = realm.where(User.class).findFirst().getName();
        }
        realm.close();
        return name;
    }

    public static void addEmotion(RealmEmotion e){
        Realm realm = Realm.getInstance(getRealmConfiguration());
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
        Realm realm = Realm.getInstance(getRealmConfiguration());
        List<Emotion> emotions = new ArrayList<>();
        if (realm.where(User.class).findFirst() != null) {
            for(RealmEmotion e: realm.where(User.class).findFirst().getEmotions()){
                Emotion emotion = new Emotion(e.getEmotion(), e.getDate());
                emotions.add(emotion);
            }
        }
        realm.close();
        return emotions;
    }
}
