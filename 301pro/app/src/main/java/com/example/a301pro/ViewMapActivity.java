package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private LatLng point;
    private Button setLocation;
    private String Book_id;
    protected FirebaseFirestore db;

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
                .collection("Requested");

//        collectionReference.document(Book_id).update("location",meetup.getPosition());

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
//                pendDataList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
//                    String bookID = doc.getId();
//                    String imageId = (String) doc.getData().get("imageId") ;
//                    String ISBN = (String) doc.getData().get("ISBN");
//                    String bookName= (String) doc.getData().get("book_name");
//                    String description = (String) doc.getData().get("des");
//                    String status = (String) doc.getData().get("status");
//                    String requestSender = (String) doc.getData().get("requestFrom");
                    String location = (String) doc.getData().get("location");


//                    pendDataList.add((new Request(bookID,imageId,ISBN,bookName,
//                            description,status,requestSender,location)));
                }
//                pendAdapter.notifyDataSetChanged();
            }
        });


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
                .position(point)
                .title("Meetup here!")
                .draggable(false));
        map.moveCamera(CameraUpdateFactory.zoomTo(15));
        map.moveCamera(CameraUpdateFactory.newLatLng(point));
    }

    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
