package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CreateNoteFragment createNoteFragment;

    //Khai bao BarChartFragment de chuyen Fragment
    FinancialOverviewFragment financialOverviewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNoteFragment = new CreateNoteFragment();
        //tao doi tuong barChartFragment
        financialOverviewFragment = new FinancialOverviewFragment();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menu_add) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, createNoteFragment).commit();
                }
                //nêú itemId = menuChart thì chuyen qua fragment cua barchart
                else if (item.getItemId() == R.id.menu_chart) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, financialOverviewFragment).commit();
                }
                return true;
            }
        });
//        bottomNavigationView.setSelectedItemId(R.id.menu_chat);
    }
}