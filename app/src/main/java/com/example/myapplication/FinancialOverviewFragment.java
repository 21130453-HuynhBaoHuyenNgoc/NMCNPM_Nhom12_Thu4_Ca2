package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

//tao fragment Barchart
public class FinancialOverviewFragment extends Fragment {
    //khai bao ra cac edittext va cac button tuong ung voi fragment_financial_overview
    EditText edit_startDate, edit_endDate, edit_tienthu, edit_tienchi;
    Button btt_StartDate, btt_endDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_financial_overview, container, false);
        edit_startDate = view.findViewById(R.id.edit_rep_start);
        edit_endDate = view.findViewById(R.id.edit_rep_end);
        btt_StartDate = view.findViewById(R.id.btt_rep_start);
        btt_endDate = view.findViewById(R.id.btt_rep_end);

        //chon thoi gian bat dau
        btt_StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hien thi thoi gian bat dau khi nguoi dung chon
                showDatepicker(edit_startDate);
            }
        });

        //chon thoi gian ket thuc
        btt_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatepicker(edit_endDate);
            }
        });

       return view;
    }

    // phuong thuc hien thi datepickerdialog
    private void showDatepicker(EditText date) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, year, month, day);
        //hien thi datepicker dialog
        datePickerDialog.show();
    }
//rang buoc ngay bat dau phai truoc ngay ket thuc

}