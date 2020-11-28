package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback {

//    private LatLng point;
    private Button setLocation;
    private String Book_id;
    protected FirebaseFirestore db;
    private LatLng point2;
    private GeoPoint geoPoint;
    private double lat;
    private double lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_set);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        point = getIntent().getParcelableExtra("point");
        Intent intent = getIntent();
        Book_id = intent.getStringExtra("BOOKID");
        setLocation = findViewById(R.id.setMeeting);
        setLocation.setEnabled(false);
        setLocation.setVisibility(View.GONE);

        SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.setMap);
        myMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap map){

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Users")
                .document(getUserID())
                .collection("Borrowed")
                .document(Book_id);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                geoPoint = documentSnapshot.getGeoPoint("location");
                lat = geoPoint.getLatitude();
                lng = geoPoint.getLongitude();
                //point2 = new LatLng(lat,lng);
                Marker meetup = map.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,lng))
                        .title("Meetup here!")
                        .draggable(false));
                map.moveCamera(CameraUpdateFactory.zoomTo(15));
                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
            }
        });


    }

    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
