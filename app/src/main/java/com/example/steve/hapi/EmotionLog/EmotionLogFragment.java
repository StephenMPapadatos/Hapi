package com.example.steve.hapi.EmotionLog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.steve.hapi.ChooseEmotion.ChooseEmotionFragment;
import com.example.steve.hapi.Controllers.UserController;
import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class EmotionLogFragment extends Fragment {

    @BindView(R.id.listview)
    protected ListView mListView;

    EmotionLogAdapter<Emotion> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emotion_log, container, false);
        ButterKnife.bind(this, view);
        Realm.init(getContext());

        List<Emotion> emotions = UserController.getEmotions();
        mAdapter = new EmotionLogAdapter<>(getContext(), emotions);

        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public static EmotionLogFragment newInstance() {
        EmotionLogFragment fragment = new EmotionLogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
