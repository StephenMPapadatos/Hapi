package com.example.steve.hapi.ChooseEmotion;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.steve.hapi.ObjectWrapperForBinder;
import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.realm.Realm;

public class ChooseEmotionFragment extends Fragment {

    @BindView(R.id.choose_emotion_gridview)
    protected GridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_emotion, container, false);
        ButterKnife.bind(this, view);
        Realm.init(getContext());

        loadEmotions();

        return view;
    }

    public static ChooseEmotionFragment newInstance() {
        ChooseEmotionFragment fragment = new ChooseEmotionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadEmotions() {
        mGridView.setAdapter(new EmotionAdapter(getContext()));
    }

    @OnItemClick(R.id.choose_emotion_gridview)
    public void clickEmotion(AdapterView<?> parent, int position) {
        String mEmotion = getResources().getResourceEntryName(EmotionAdapter.mThumbIds[position]);
        Object emotion = new Emotion(mEmotion, DateFormat.getDateTimeInstance().format(new Date()));

        final Intent intent = new Intent(getContext(), EmotionCardActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putBinder("Emotion", new ObjectWrapperForBinder(emotion));
        startActivityForResult(intent.putExtras(bundle), "LOG_EMOTION");
    }
}
