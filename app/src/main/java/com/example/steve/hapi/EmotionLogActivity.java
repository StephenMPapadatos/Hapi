package com.example.steve.hapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import butterknife.ButterKnife;
import io.realm.Realm;

public class EmotionLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_log);
        ButterKnife.bind(this);
        Realm.init(getApplicationContext());

    }

}
