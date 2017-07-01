package com.example.steve.hapi.EmotionLog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.steve.hapi.ChooseEmotion.EmotionCardActivity;
import com.example.steve.hapi.Controllers.StatsController;
import com.example.steve.hapi.Controllers.UserController;
import com.example.steve.hapi.StatsAndSettings.StatsLogAdapter;
import com.example.steve.hapi.Utilities.MaterialSpinner.materialspinner.MaterialSpinner;
import com.example.steve.hapi.Utilities.ObjectWrapperForBinder;
import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;
import com.example.steve.hapi.Utilities.boommenu.BoomButtons.OnBMClickListener;
import com.example.steve.hapi.Utilities.boommenu.BoomButtons.SimpleCircleButton;
import com.example.steve.hapi.Utilities.boommenu.BoomMenuButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import io.realm.Realm;

import static com.example.steve.hapi.Utilities.EmotionImages.thumbIds;

public class EmotionLogFragment extends Fragment {

    public static final int LOG_EMOTION = 1;

    @BindView(R.id.listview)
    protected ListView mListView;
    @BindView(R.id.bmb)
    protected BoomMenuButton bmb;
    @BindView(R.id.spinner_emotions)
    protected MaterialSpinner mEmotionsSpinner;
    @BindView(R.id.spinner_ranges)
    protected MaterialSpinner mRangeSpinner;
    @BindView(R.id.spinner_sort)
    protected MaterialSpinner mSortSpinner;


    private EmotionLogAdapter<Emotion> mAdapter;
    private StatsLogAdapter<Emotion> mStatsAdapter;

    private List<Emotion> currentEmotions;

    private List<List<Emotion>> mEmotions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emotion_log, container, false);
        ButterKnife.bind(this, view);
        Realm.init(getContext());

        currentEmotions = UserController.getEmotions();
        Collections.reverse(currentEmotions);
        mAdapter = new EmotionLogAdapter<>(getContext(), currentEmotions);

        mListView.setAdapter(mAdapter);

        initBmb();

        initializeSpinner(mEmotionsSpinner, Arrays.asList(getResources().getStringArray(R.array.emotions)));
        initializeSpinner(mRangeSpinner, Arrays.asList(getResources().getStringArray(R.array.ranges)));
        initializeSpinner(mSortSpinner, Arrays.asList(getResources().getStringArray(R.array.sort)));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void initializeSpinner(MaterialSpinner spinner, List<String> contents){
        spinner.setItems(contents);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                onChangeEmotionType();
            }
        });
    }

    public static final String ALL = "All";

    public void onChangeEmotionType() {
        String emotion = mEmotionsSpinner.getText().toString();
        String range = mRangeSpinner.getText().toString();
        if(emotion.equals(ALL)){
            currentEmotions = UserController.getEmotions();
            Collections.reverse(currentEmotions);
            mAdapter = new EmotionLogAdapter<>(getContext(), currentEmotions);

            mListView.setAdapter(mAdapter);
        } else {
            try {
                mEmotions = StatsController.getBest(emotion, range);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mStatsAdapter = new StatsLogAdapter<>(getContext(), mEmotions);
            mListView.setAdapter(mStatsAdapter);
        }
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
        if (mListView.getAdapter() instanceof StatsLogAdapter) {
            EmotionLogAdapter<Emotion> mEmotionsAdapter = new EmotionLogAdapter<>(getContext(), mEmotions.get(position));
            mListView.setAdapter(mEmotionsAdapter);
            currentEmotions = mEmotions.get(position);
        } else {
            final Intent intent = new Intent(getContext(), ViewEmotionCardActivity.class);
            final Bundle bundle = new Bundle();
            bundle.putBinder("Emotion", new ObjectWrapperForBinder(currentEmotions.get(position)));
            startActivity(intent.putExtras(bundle));
        }
    }

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= thumbIds.length) imageResourceIndex = 0;
        return thumbIds[imageResourceIndex++];
    }

    private BoomMenuButton initBmb() {
        assert bmb != null;
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int position) {
                            String mEmotion = getResources().getResourceEntryName(thumbIds[position]);
                            Object emotion = new Emotion(mEmotion, DateFormat.getDateTimeInstance().format(new Date()));

                            final Intent intent = new Intent(getContext(), EmotionCardActivity.class);
                            final Bundle bundle = new Bundle();
                            bundle.putBinder("Emotion", new ObjectWrapperForBinder(emotion));
                            startActivityForResult(intent.putExtras(bundle), LOG_EMOTION);
                        }
                    }).normalImageRes(getImageResource());
            bmb.addBuilder(builder);
        }
        return bmb;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOG_EMOTION) {
            ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);
            pager.getAdapter().notifyDataSetChanged();
        }
    }

}
