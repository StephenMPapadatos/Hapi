package com.example.steve.hapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.realm.Realm;

public class ChooseEmotionActivity extends AppCompatActivity {

    @BindView(R.id.choose_emotion_gridview)
    protected GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_emotion);
        ButterKnife.bind(this);
        Realm.init(this);
        loadEmotions();
    }

    public void loadEmotions(){
        mGridView.setAdapter(new EmotionAdapter(this));
    }

    @OnItemClick(R.id.choose_emotion_gridview)
    public void clickEmotion(AdapterView<?> parent, int position){
        RealmEmotion e = new RealmEmotion(getResources().getResourceEntryName(EmotionAdapter.mThumbIds[position]));
        UserController.addEmotion(e);
        List<Emotion> emotions = UserController.getEmotions();
        System.out.println("====================");
        for(Emotion em: emotions){
            System.out.println("e = " + em.getName());
        }
    }
}
