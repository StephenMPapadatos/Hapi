package com.papadatos.steve.hapi.ChooseEmotion;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.papadatos.steve.hapi.Controllers.UserController;
import com.papadatos.steve.hapi.R;
import com.papadatos.steve.hapi.Types.Emotion;
import com.papadatos.steve.hapi.Types.RealmEmotion;
import com.papadatos.steve.hapi.Utilities.ObjectWrapperForBinder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class EmotionCardActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = EmotionCardActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;

    private boolean mLocationPermissionGranted;

    @BindView(R.id.emotion_note)
    protected TextView mNote;
    private GoogleMap mMap;

    private Location mLocation;

    private Emotion mEmotion;

    private GoogleApiClient mGoogleApiClient;

    public EmotionCardActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_card);
        ButterKnife.bind(this);
        Realm.init(this);

        mEmotion = (Emotion) ((ObjectWrapperForBinder) getIntent().getExtras().getBinder("Emotion")).getData();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }

    @OnClick(R.id.button_submit)
    public void onSubmit() {
        mEmotion.setNote(mNote.getText().toString());
        mEmotion.setLocation(mLocation);
        RealmEmotion e = new RealmEmotion(mEmotion);

        if (getCallingActivity() != null) {
            UserController.addEmotion(e);
        } else {
            UserController.editEmotion(e);
        }

        finish();
    }

    @OnClick(R.id.button_cancel)
    public void onCancel() {
        finish();
    }

    private void getDeviceLocation() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        if (mLocationPermissionGranted) {
            mLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

//            Bitmap bitmap = getBitmap(this, thumbIds[Emotions.valueOf(mEmotion.getName().toUpperCase()).ordinal()]);
//            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
//            mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
//                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap1)));
        }

        if (mLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLocation.getLatitude(),
                            mLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

//    private static Bitmap getBitmap(Context context, int drawableId) {
//        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
//        if (drawable instanceof BitmapDrawable) {
//            return BitmapFactory.decodeResource(context.getResources(), drawableId);
//        } else if (drawable instanceof VectorDrawable) {
//            return getBitmap((VectorDrawable) drawable);
//        } else {
//            throw new IllegalArgumentException("unsupported drawable type");
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
//        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
//                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        vectorDrawable.draw(canvas);
//        return bitmap;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                return null;
            }
        });

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
