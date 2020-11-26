package com.example.a301pro;

import androidx.fragment.app.FragmentActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SetMapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    private LatLng point = new LatLng(53.5,-113.5);
    private Button setLocation;
    private Marker meetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_set);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.setMap);
        mapFragment.getMapAsync(this);

        setLocation = findViewById(R.id.setMeeting);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("location", meetup.getPosition());
                Log.d("LOCATION", meetup.getPosition().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
//
//
//        LatLng Edmonton = new LatLng(53.523664, -113.527104);
//        map.addMarker(new MarkerOptions().position(Edmonton).title("Edmonton"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(Edmonton));

        meetup = map.addMarker(new MarkerOptions()
                .position(point)
                .title("Place Me at meetup point!")
                .draggable(true));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.moveCamera(CameraUpdateFactory.newLatLng(point));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                meetup.setPosition(latLng);
            }
        });


    }
}
