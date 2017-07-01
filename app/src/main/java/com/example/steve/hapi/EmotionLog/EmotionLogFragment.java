package com.example.steve.hapi.EmotionLog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.steve.hapi.Controllers.UserController;
import com.example.steve.hapi.Utilities.ObjectWrapperForBinder;
import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.realm.Realm;

public class EmotionLogFragment extends Fragment {

    @BindView(R.id.listview)
    protected ListView mListView;

    private EmotionLogAdapter<Emotion> mAdapter;

    private List<Emotion> mEmotions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emotion_log, container, false);
        ButterKnife.bind(this, view);
        Realm.init(getContext());

        mEmotions = UserController.getEmotions();
        mAdapter = new EmotionLogAdapter<>(getContext(), mEmotions);

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

    @OnItemClick(R.id.listview)
    public void clickEmotionRecord(AdapterView<?> parent, int position) {
        final Intent intent = new Intent(getContext(), ViewEmotionCardActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putBinder("Emotion", new ObjectWrapperForBinder(mEmotions.get(position)));
        startActivity(intent.putExtras(bundle));
    }

}
