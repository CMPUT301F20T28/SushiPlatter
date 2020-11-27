package com.example.a301pro;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This class set the meet up location for trading on the map
 */
public class SetMapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    private LatLng point = new LatLng(53.5,-113.5);
    private Button setLocation;
    private Marker meetUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_set);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.setMap);
        mapFragment.getMapAsync(this);

        setLocation = findViewById(R.id.setMeeting);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("location", meetUp.getPosition());
                Log.d("LOCATION", meetUp.getPosition().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /**
     * connect the google map and set a marker on the map
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

//        LatLng Edmonton = new LatLng(53.523664, -113.527104);
//        map.addMarker(new MarkerOptions().position(Edmonton).title("Edmonton"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(Edmonton));

        meetUp = map.addMarker(new MarkerOptions()
                .position(point)
                .title("Place Me at meet up point!")
                .draggable(true));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.moveCamera(CameraUpdateFactory.newLatLng(point));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                meetUp.setPosition(latLng);
            }
        });
    }
}
