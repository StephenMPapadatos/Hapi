package com.example.steve.hapi.Controllers;

import android.location.Location;

import com.example.steve.hapi.Types.Emotion;
import com.example.steve.hapi.Types.RealmEmotion;
import com.example.steve.hapi.Types.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

    public static int getAmount(){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        int amount = 0;
        if (realm.where(User.class).findFirst() != null) {
            amount = realm.where(User.class).findFirst().getAmount();
        }
        realm.close();
        return amount;
    }

    public static void setAmount(int amount){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        realm.beginTransaction();
        User user = realm.where(User.class).findFirst();
        if (user == null) {
            user = realm.createObject(User.class);
        }
        user.setAmount(amount);
        realm.commitTransaction();
        realm.close();
    }

    public static Date getEnd(){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        Date date = null;
        if (realm.where(User.class).findFirst() != null) {
            date = realm.where(User.class).findFirst().getEnd();
        }
        realm.close();
        return date;
    }

    public static void setEnd(Date date){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        realm.beginTransaction();
        User user = realm.where(User.class).findFirst();
        if (user == null) {
            user = realm.createObject(User.class);
        }
        user.setEnd(date);
        realm.commitTransaction();
        realm.close();
    }

    public static Date getStart(){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        Date date = null;
        if (realm.where(User.class).findFirst() != null) {
            date = realm.where(User.class).findFirst().getStart();
        }
        realm.close();
        return date;
    }

    public static void setStart(Date date){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        realm.beginTransaction();
        User user = realm.where(User.class).findFirst();
        if (user == null) {
            user = realm.createObject(User.class);
        }
        user.setStart(date);
        realm.commitTransaction();
        realm.close();
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
                emotions.add(new Emotion(e));
            }
        }

        realm.close();
        return emotions;
    }

    public static void editEmotion(RealmEmotion e){
        Realm realm = Realm.getInstance(getRealmConfiguration());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(e);
        realm.commitTransaction();
    }
}
