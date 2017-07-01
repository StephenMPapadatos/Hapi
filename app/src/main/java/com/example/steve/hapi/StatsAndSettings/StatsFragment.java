package com.example.steve.hapi.StatsAndSettings;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.steve.hapi.Controllers.StatsController;
import com.example.steve.hapi.Controllers.UserController;
import com.example.steve.hapi.EmotionLog.EmotionLogAdapter;
import com.example.steve.hapi.EmotionLog.ViewEmotionCardActivity;
import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;
import com.example.steve.hapi.Utilities.NotificationScheduler;
import com.example.steve.hapi.Utilities.ObjectWrapperForBinder;
import com.example.steve.hapi.Utilities.TimePickerFragment;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;

public class StatsFragment extends Fragment {

    private static final int START_TIME = 0;
    private static final int END_TIME = 1;

    @BindView(R.id.spinner_emotions)
    protected Spinner mSpinnerEmotions;
    @BindView(R.id.spinner_ranges)
    protected Spinner mSpinnerRanges;
    @BindView(R.id.button_amounts)
    protected Button mButtonAmounts;
    @BindView(R.id.list_view)
    protected ListView mListView;
    @BindView(R.id.button_start)
    protected Button mStart;
    @BindView(R.id.button_end)
    protected Button mEnd;

    private List<String> mListAmounts;

    private StatsLogAdapter<Emotion> mStatsAdapter;

    private List<List<Emotion>> mEmotions;

    private List<Emotion> currentEmotions;

    public StatsFragment() {
    }

    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this, view);

        ArrayAdapter<CharSequence> adapterEmotions = ArrayAdapter.createFromResource(getContext(), R.array.emotions, android.R.layout.simple_spinner_item);
        adapterEmotions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerEmotions.setAdapter(adapterEmotions);

        ArrayAdapter<CharSequence> adapterRanges = ArrayAdapter.createFromResource(getContext(), R.array.ranges, android.R.layout.simple_spinner_item);
        adapterRanges.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRanges.setAdapter(adapterRanges);

        mListAmounts = Arrays.asList(getResources().getStringArray(R.array.amounts));
        mButtonAmounts.setText(mListAmounts.get(0));

        try {
            mEmotions = StatsController.getBest(mSpinnerEmotions.getSelectedItem().toString(), mSpinnerRanges.getSelectedItem().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mStatsAdapter = new StatsLogAdapter<>(getContext(), mEmotions);

        mListView.setAdapter(mStatsAdapter);

        int amount = UserController.getAmount();
        Date start = UserController.getStart();
        Date end = UserController.getEnd();

        mButtonAmounts.setText(""+amount);

        if(start != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            mStart.setText(hours + " : " + minutes);
        }
        if(end != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            mEnd.setText(hours + " : " + minutes);
        }

        return view;
    }

    @OnClick(R.id.button_amounts)
    public void onClickAmounts() {
        if (mListAmounts.indexOf(mButtonAmounts.getText()) < mListAmounts.size() - 1) {
            mButtonAmounts.setText(mListAmounts.get(mListAmounts.indexOf(mButtonAmounts.getText()) + 1));
        } else {
            mButtonAmounts.setText(mListAmounts.get(0));
        }
        UserController.setAmount(mListAmounts.indexOf(mButtonAmounts.getText()));
        setNotification();
    }

    @OnClick(R.id.button_start)
    public void clickStart() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(START_TIME);
        newFragment.setTimeSetListener(new TimePickerFragment.TimeSetListener() {
            @Override
            public void onTimeSet(int hour, int minute) {
                mStart.setText(hour + " : " + minute + " " + getPeriod(hour));
                setNotification();
            }
        });
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @OnClick(R.id.button_end)
    public void clickEnd() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(END_TIME);
        newFragment.setTimeSetListener(new TimePickerFragment.TimeSetListener() {
            @Override
            public void onTimeSet(int hour, int minute) {
                mEnd.setText(hour + " : " + minute + " " + getPeriod(hour));
                setNotification();
            }
        });
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public String getPeriod(int hour) {
        if (hour < 12) {
            return "AM";
        } else {
            return "PM";
        }
    }

    @OnItemSelected(R.id.spinner_emotions)
    public void onChangeEmotionType() {
        try {
            mEmotions = StatsController.getBest(mSpinnerEmotions.getSelectedItem().toString(), mSpinnerRanges.getSelectedItem().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mStatsAdapter = new StatsLogAdapter<>(getContext(), mEmotions);

        mListView.setAdapter(mStatsAdapter);
    }


    @OnItemSelected(R.id.spinner_ranges)
    public void onChangeRange() {
        try {
            mEmotions = StatsController.getBest(mSpinnerEmotions.getSelectedItem().toString(), mSpinnerRanges.getSelectedItem().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mStatsAdapter = new StatsLogAdapter<>(getContext(), mEmotions);

        mListView.setAdapter(mStatsAdapter);
    }

    @OnItemClick(R.id.list_view)
    public void clickEmotionRecord(int position) {
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

    public void setNotification(){
        Date start = UserController.getStart();
        Date end = UserController.getEnd();
        int amount = UserController.getAmount();
        if (start != null && end != null && amount != 0) {
            NotificationScheduler.scheduleNotification(getContext(), "What's Up?", start, end, amount);
        }
    }

}
