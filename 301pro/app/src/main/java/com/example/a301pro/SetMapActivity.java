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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
    protected FirebaseFirestore db;

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

//                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
//                        pendDataList.clear();
//                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
//                            String bookID = doc.getId();
//                            String imageId = (String) doc.getData().get("imageId") ;
//                            String ISBN = (String) doc.getData().get("ISBN");
//                            String bookName= (String) doc.getData().get("book_name");
//                            String description = (String) doc.getData().get("des");
//                            String status = (String) doc.getData().get("status");
//                            String requestSender = (String) doc.getData().get("requestFrom");
//
//                            pendDataList.add((new Request(bookID,imageId,ISBN,bookName,
//                                    description,status,requestSender)));
//                        }
//                        pendAdapter.notifyDataSetChanged();
//                    }
//                });


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
