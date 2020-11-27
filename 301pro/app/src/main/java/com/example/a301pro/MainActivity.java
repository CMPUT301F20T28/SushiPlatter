package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        //隐藏title
        AppCompatAcitiviy:getSupportActionBar().hide();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MybookFragment()).commit();
        //StatusBarCompat.setStatusBarColor(this,R.color.menuBackground);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        final TextView un = (TextView) headerView.findViewById(R.id.un);
        final String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
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
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
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

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }

    };

}