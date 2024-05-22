package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateNote extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    TextView selection;
    Spinner spin;
    String[] items = { "Thu tiền", "Chi tiền" };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_create_note);
         spin =  findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        // set a clickable right push-button comboBox to show items
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        // provide a particular design for the drop-down lines
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        // associate GUI spinner and adapter
        spin.setAdapter(aa);
    }
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }
    

    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
