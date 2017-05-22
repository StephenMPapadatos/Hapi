package com.example.steve.hapi.ChooseEmotion;


import android.app.Activity;
import android.os.Bundle;

import com.example.steve.hapi.Controllers.UserController;
import com.example.steve.hapi.ObjectWrapperForBinder;
import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;
import com.example.steve.hapi.Types.RealmEmotion;

import java.text.DateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class EmotionCardActivity extends Activity {

    protected Emotion mEmotion;

    public EmotionCardActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_card);
        ButterKnife.bind(this);
        Realm.init(this);

        mEmotion = (Emotion) ((ObjectWrapperForBinder) getIntent().getExtras().getBinder("Emotion")).getData();
    }

    @OnClick(R.id.button_submit)
    public void onSubmit() {
        RealmEmotion e = new RealmEmotion(mEmotion);
        UserController.addEmotion(e);
        finish();
    }

    @OnClick(R.id.button_cancel)
    public void onCancel() {
        finish();
    }

}
