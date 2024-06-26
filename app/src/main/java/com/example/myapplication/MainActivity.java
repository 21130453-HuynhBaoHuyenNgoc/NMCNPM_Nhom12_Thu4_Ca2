package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dao.DatabaseHelper;
import com.example.myapplication.model.Category;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnCategorySelectedListener {

    BottomNavigationView bottomNavigationView;
    CreateNoteFragment createNoteFragment;
    DatabaseHelper dbHelper;
    //tao doi tuong FinancialOverViewFragment
    FinancialOverviewFragment financialOverviewFragment;

    CategoryFragment categoryFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Tạo cơ sở dữ liệu
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        createNoteFragment = new CreateNoteFragment();
        //tao doi tuong barChartFragment
        financialOverviewFragment = new FinancialOverviewFragment();
        categoryFragment = new CategoryFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menu_add) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, createNoteFragment).commit();
                }
                // Nếu itemId = menuChart thì chuyển qua fragment của thu chi
                else if (item.getItemId() == R.id.menu_thuchi) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, financialOverviewFragment).commit(); // Thêm dòng này để hiển thị fragment FinancialOverviewFragment
                }
                else if (item.getItemId() == R.id.fragment_category) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, categoryFragment).commit();
                }
                return true;
            }
        });
    }

    @Override
    public void onCategorySelected(Category category) {
        // Chuyển tiếp callback tới fragment CreateNoteFragment
        CreateNoteFragment createNoteFragment = (CreateNoteFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout);
        if (createNoteFragment != null) {
            createNoteFragment.onCategorySelected(category);
        }

        // Quay lại fragment CreateNoteFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, createNoteFragment).commit();
    }
}