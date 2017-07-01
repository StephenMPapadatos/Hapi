package com.example.steve.hapi.EmotionLog;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.steve.hapi.Utilities.ObjectWrapperForBinder;
import com.example.steve.hapi.R;
import com.example.steve.hapi.Types.Emotion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ViewEmotionCardActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int DEFAULT_ZOOM = 15;

    @BindView(R.id.emotion_note)
    protected TextView mNote;

    private Location mLocation;

    private Emotion mEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_emotion_card);
        ButterKnife.bind(this);
        Realm.init(this);

        mEmotion = (Emotion) ((ObjectWrapperForBinder) getIntent().getExtras().getBinder("Emotion")).getData();
        mNote.setText(mEmotion.getNote());

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.button_confirm)
    public void onClickOk(){
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mLocation = mEmotion.getLocation();
        LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
    }
}
