package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.githang.statusbar.StatusBarTools.getStatusBarHeight;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏title
        AppCompatAcitiviy:getSupportActionBar().hide();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new mybookfragment()).commit();
        //StatusBarCompat.setStatusBarColor(this,R.color.menuBackground);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.nav_share:
                    selectedFragment = new Sharefragment();
                    break;
                case R.id.nav_mine:
                    selectedFragment = new mybookfragment();
                    break;
                case R.id.nav_bo:
                    selectedFragment = new borrowed_fragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }

    };

}