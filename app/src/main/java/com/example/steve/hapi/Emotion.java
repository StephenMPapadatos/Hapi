package com.example.steve.hapi;

/**
 * Created by steve on 2017-05-14.
 */

public class Emotion {
    private String emotionName;

    public Emotion(String name){
        emotionName = name;
    }

    public String getName(){
        return emotionName;
    }
}
