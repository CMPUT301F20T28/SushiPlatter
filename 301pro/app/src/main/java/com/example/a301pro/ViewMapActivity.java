package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback {

//    private LatLng point;
    private Button setLocation;
    private String Book_id;
    protected FirebaseFirestore db;
    private  LatLng point2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_set);


//        point = getIntent().getParcelableExtra("point");
        Intent intent = getIntent();
        Book_id = intent.getStringExtra("BOOKID");

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users")
                .document(getUserID())
                .collection("Request");

//        collectionReference.document(Book_id).update("location",meetup.getPosition());

//        GeoPoint geoPoint = collectionReference.document(Book_id).getGeoPoint("position");


        DocumentReference docRef = db.collection("Users")
                .document(getUserID())
                .collection("Request")
                .document(Book_id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    GeoPoint geoPoint = document.getGeoPoint("position");
                    double lat = geoPoint.getLatitude();
                    double lng = geoPoint.getLongitude();
                    point2 = new LatLng(lat,lng);

                }
            }
        });

//        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
////
//                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
////                    String bookID = doc.getId();
////                    String imageId = (String) doc.getData().get("imageId") ;
////                    String ISBN = (String) doc.getData().get("ISBN");
////                    String bookName= (String) doc.getData().get("book_name");
////                    String description = (String) doc.getData().get("des");
////                    String status = (String) doc.getData().get("status");
////                    String requestSender = (String) doc.getData().get("requestFrom");
////                    GeoPoint location = (GeoPoint) doc.getData().get("location");
//                    GeoPoint location = doc.getGeoPoint("location");
//
//                    double lat = location.getLatitude();
//                    double lng = location.getLongitude();
//                    point3 = new LatLng(lat,lng);
//
//
//                }
//
//            }
//        });

        setLocation = findViewById(R.id.setMeeting);
        setLocation.setEnabled(false);
        setLocation.setVisibility(View.GONE);

        SupportMapFragment myMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.setMap);
        myMapFragment.getMapAsync(this);
        ;
    }

    @Override
    public void onMapReady(GoogleMap map){
        final Marker meetup = map.addMarker(new MarkerOptions()
                .position(point2)
                .title("Meetup here!")
                .draggable(false));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.moveCamera(CameraUpdateFactory.newLatLng(point2));
    }

    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
