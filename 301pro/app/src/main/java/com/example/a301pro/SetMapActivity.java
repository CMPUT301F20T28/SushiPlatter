package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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

        //get bookID
        Intent intent = getIntent();
        Book_id = intent.getStringExtra("BOOKID");
        borrower = intent.getStringExtra("BORROWER");
        //transform LatLng to GeoPoint format
//        LatLng test = meetUp.getPosition();
//        double lat = test.latitude;
//        double lng = test.longitude;
//
//        final GeoPoint point1 = new GeoPoint(lat,lng);


        setLocation = findViewById(R.id.setMeeting);
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent();
                //intent.putExtra("location", meetUp.getPosition());
                //Log.d("LOCATION", meetUp.getPosition().toString());
                //setResult(RESULT_OK, intent);

                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Users")
                        .document(getUserID())
                        .collection("Request");
//                collectionReference.document(Book_id).update("location",meetUp.getPosition());

                LatLng test = meetUp.getPosition();
                double lat = test.latitude;
                double lng = test.longitude;

                final GeoPoint point1 = new GeoPoint(lat,lng);

                collectionReference.document(Book_id).update("location",point1);

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
    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
