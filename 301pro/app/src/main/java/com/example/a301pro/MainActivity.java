package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a301pro.Fragments.BorrowedFragment;
import com.example.a301pro.Fragments.MybookFragment;
import com.example.a301pro.Fragments.RequestFragment;
import com.example.a301pro.Fragments.ShareFragment;
import com.example.a301pro.Functionality.Login;
import com.example.a301pro.Utilities.GetUserFromDB;
import com.example.a301pro.View.ViewMessages;
import com.example.a301pro.View.ViewUserProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class builds the basic layout, and controls the functions of each divided fragments
 */
public class MainActivity extends AppCompatActivity {
    /**
     * controller of all fragments
     * @param savedInstanceState data of previous instance
     */
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        AppCompatAcitiviy : getSupportActionBar().hide();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MybookFragment()).commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_mybooks) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new MybookFragment()).commit();
                } else if (id == R.id.nav_shared) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ShareFragment()).commit();
                } else if (id == R.id.nav_borrowed) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new BorrowedFragment()).commit();
                } else if (id == R.id.nav_requests) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new RequestFragment()).commit();
                } else if(id == R.id.nav_messages){
                    Intent intent = new Intent(getApplicationContext(), ViewMessages.class);
                    intent.putExtra("userUID", GetUserFromDB.getUserID());
                    startActivity(intent);
                }
                return true;
            }
        });

        final TextView un = headerView.findViewById(R.id.un);
        final String userName = GetUserFromDB.getUsername();
        un.setText(userName);

        // allow user go to the profile page by clicking the head icon
        Button userInformation = headerView.findViewById(R.id.user_self_login);
        userInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ViewUserProfile.class);
                startActivity(intent);
            }
        });

        // allow user to logout current account and re-Login
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
            }
        });


    }

    /**
     * Controller of the fragment navigation bar
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_share:
                    selectedFragment = new ShareFragment();
                    break;
                case R.id.nav_mine:
                    selectedFragment = new MybookFragment();
                    break;
                case R.id.nav_bo:
                    selectedFragment = new BorrowedFragment();
                    break;
                case R.id.nav_request:
                    selectedFragment = new RequestFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }
    };




}