package com.papadatos.steve.hapi.Types;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by steve on 2017-05-14.
 */

public class User extends RealmObject {
    private String name;
    private Date start;
    private Date end;
    private int amount;
    private RealmList<RealmEmotion> emotions;

    public void addEmotion(RealmEmotion e){
        emotions.add(e);
    }

    public RealmList<RealmEmotion> getEmotions(){
        return emotions;
    }



    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
