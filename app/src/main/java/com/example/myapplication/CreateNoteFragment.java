package com.example.myapplication;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNoteFragment extends Fragment {
    private EditText etAmount, etDescription;
    private Button btnSelectDate, btnAddTransaction, tvSelectCategory;
    private TextView tvDate;
    private ImageButton btnBack;
    private Spinner spin;
    String[] items = { "Thu tiền", "Chi tiền" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_note, container, false);
        // Kết nối các thành phần UI
        etAmount = view.findViewById(R.id.etAmount);
        etDescription = view.findViewById(R.id.etDescription);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        btnAddTransaction = view.findViewById(R.id.btnAddTransaction);
        tvSelectCategory = view.findViewById(R.id.tvSelectCategory);
        tvDate = view.findViewById(R.id.tvDate);
        btnBack = view.findViewById(R.id.btn_back);
        spin = view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.custom_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Thiết lập ArrayAdapter cho Spinner
        spin.setAdapter(adapter);

        // Đặt sự kiện cho các nút bấm
        btnBack.setOnClickListener(v -> getActivity().onBackPressed());
        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());
        btnAddTransaction.setOnClickListener(v -> createNewNote());
//        tvSelectCategory.setOnClickListener(v -> showCategorySelectionDialog());

        // Đặt ngày hiện tại cho tvDate
        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));

        return view;
    }
    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    private void createNewNote() {

    }
    }
