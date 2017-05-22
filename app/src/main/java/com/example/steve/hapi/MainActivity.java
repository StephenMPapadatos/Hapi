package com.example.steve.hapi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.steve.hapi.ChooseEmotion.ChooseEmotionFragment;
import com.example.steve.hapi.ChooseEmotion.StatusFragment;
import com.example.steve.hapi.EmotionLog.EmotionLogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MainActivity extends FragmentActivity {

    public enum HapiFragmentName {
        STATUS, CHOOSE, LOG
    }

    @BindView(R.id.pager)
    protected ViewPager mViewPager;

    private FragmentStatePagerAdapter mHapiPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);

        mHapiPagerAdapter = new HapiPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mHapiPagerAdapter);
        mViewPager.setCurrentItem(HapiFragmentName.CHOOSE.ordinal());
    }

    public class HapiPagerAdapter extends FragmentStatePagerAdapter {

        public HapiPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            switch (MainActivity.HapiFragmentName.values()[position]) {
                case STATUS:
                    return StatusFragment.newInstance();
                case CHOOSE:
                    return ChooseEmotionFragment.newInstance();
                case LOG:
                    return EmotionLogFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
