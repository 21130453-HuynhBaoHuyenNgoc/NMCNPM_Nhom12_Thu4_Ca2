package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReportchartMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportchart_main);

        EditText textBatdau = (EditText) findViewById(R.id.ngayBatdau);
        EditText textKetthuc = (EditText) findViewById(R.id.ngayketthuc);
        Button buttonBatdau = (Button) findViewById(R.id.buttonBatdau);
        Button buttonKetthuc = (Button) findViewById(R.id.buttonKetthuc);

        buttonBatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}