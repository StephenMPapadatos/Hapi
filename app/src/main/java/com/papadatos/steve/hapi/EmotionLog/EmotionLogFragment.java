package com.papadatos.steve.hapi.EmotionLog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.papadatos.steve.hapi.ChooseEmotion.EmotionCardActivity;
import com.papadatos.steve.hapi.Controllers.StatsController;
import com.papadatos.steve.hapi.Controllers.UserController;
import com.papadatos.steve.hapi.R;
import com.papadatos.steve.hapi.StatsAndSettings.StatsFragment;
import com.papadatos.steve.hapi.StatsAndSettings.StatsLogAdapter;
import com.papadatos.steve.hapi.Types.Emotion;
import com.papadatos.steve.hapi.Utilities.MaterialSpinner.materialspinner.MaterialSpinner;
import com.papadatos.steve.hapi.Utilities.ObjectWrapperForBinder;
import com.papadatos.steve.hapi.Utilities.boommenu.BoomButtons.OnBMClickListener;
import com.papadatos.steve.hapi.Utilities.boommenu.BoomButtons.SimpleCircleButton;
import com.papadatos.steve.hapi.Utilities.boommenu.BoomMenuButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.realm.Realm;

import static com.papadatos.steve.hapi.Utilities.EmotionImages.thumbIds;

public class EmotionLogFragment extends FragmentActivity {

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

    private List<Emotion> currentEmotions;

    private List<List<Emotion>> mEmotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emotion_log);
        ButterKnife.bind(this);
        Realm.init(this);

        currentEmotions = UserController.getEmotions(ALL);
        mAdapter = new EmotionLogAdapter<>(this, currentEmotions);

        mEmotionsSpinner.setSelectedIndex(0);
        mRangeSpinner.setSelectedIndex(0);
        mSortSpinner.setSelectedIndex(0);
        mListView.setAdapter(mAdapter);

        initBmb();

        initializeSpinner(mEmotionsSpinner, Arrays.asList(getResources().getStringArray(R.array.emotions)));
        initializeSpinner(mRangeSpinner, Arrays.asList(getResources().getStringArray(R.array.ranges)));
        initializeSpinner(mSortSpinner, Arrays.asList(getResources().getStringArray(R.array.sort)));

    }

    @OnClick(R.id.settings)
    public void onClickSettings() {
        Fragment newFragment = new StatsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void initializeSpinner(MaterialSpinner spinner, List<String> contents) {
        spinner.setItems(contents);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                onChangeEmotionType();
            }
        });
    }

    public static final String ALL = "all";
    public static final String BEST = "Best";

    public void onChangeEmotionType() {
        String emotion = mEmotionsSpinner.getText().toString();
        String range = mRangeSpinner.getText().toString();
        String sort = mSortSpinner.getText().toString();

        if (range.equals(ALL)) {
            currentEmotions = UserController.getEmotions(emotion);
            mAdapter = new EmotionLogAdapter<>(this, currentEmotions);
            mSortSpinner.setSelectedIndex(0);
            mListView.setAdapter(mAdapter);
        } else {
            try {
                mEmotions = StatsController.getBest(emotion, range);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            StatsLogAdapter<Emotion> mStatsAdapter = new StatsLogAdapter<>(this, mEmotions, range);
            mListView.setAdapter(mStatsAdapter);

            if (sort.equals(BEST)) {
                Collections.sort(mEmotions, new Comparator<List>() {
                    public int compare(List a1, List a2) {
                        return a2.size() - a1.size();
                    }
                });
            }
        }
    }

    @OnItemClick(R.id.listview)
    public void clickEmotionRecord(int position) {
        if (mListView.getAdapter() instanceof StatsLogAdapter) {
            EmotionLogAdapter<Emotion> mEmotionsAdapter = new EmotionLogAdapter<>(this, mEmotions.get(position));
            mListView.setAdapter(mEmotionsAdapter);
            currentEmotions = mEmotions.get(position);
        } else {
            final Intent intent = new Intent(this, ViewEmotionCardActivity.class);
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

                            final Intent intent = new Intent(EmotionLogFragment.this, EmotionCardActivity.class);
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
            currentEmotions.clear();
            currentEmotions = UserController.getEmotions(ALL);
            mAdapter = new EmotionLogAdapter<>(this, currentEmotions);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

}
