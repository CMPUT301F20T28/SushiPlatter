package com.example.a301pro;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.a301pro.Utilities.GetUserFromDB;
import com.example.a301pro.Utilities.SendMessage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

/**
 * This class set the meet up location for trading on the map
 */
public class SetMapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    private LatLng point = new LatLng(53.5,-113.5);
    private Button setLocation;
    private Marker meetUp;
    private String Book_id;
    private String borrower;
    protected FirebaseFirestore db;
    public String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_set);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.setMap);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Book_id = intent.getStringExtra("BOOKID");
        borrower = intent.getStringExtra("BORROWER");
        setLocation = findViewById(R.id.setMeeting);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Users")
                        .document(GetUserFromDB.getUserID())
                        .collection("Request");

                LatLng test = meetUp.getPosition();
                double lat = test.latitude;
                double lng = test.longitude;

                final GeoPoint point1 = new GeoPoint(lat,lng);

                collectionReference.document(Book_id).update("location",point1);
                new SendMessage(GetUserFromDB.getUsername(), borrower, GetUserFromDB.getUsername().toString() + " has updated the location where you can pick up the book!");

                DocumentReference collectionReference2 = db.collection("userDict").document(borrower);
                collectionReference2.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        temp = documentSnapshot.getString("UID").toString();
                        final CollectionReference collectionReference1 = db.collection("Users")
                                .document(temp)
                                .collection("Borrowed");
                        collectionReference1.document(Book_id).update("location",point1);
                    }
                });

                finish();
            }
        });

    }

    /**
     * connect the google map and set a marker on the map
     * @param googleMap connect to google map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
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
