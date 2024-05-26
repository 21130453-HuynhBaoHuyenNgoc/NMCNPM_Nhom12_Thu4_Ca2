package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.dao.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//tao fragment Barchart
public class FinancialOverviewFragment extends Fragment {
    //khai bao ra cac edittext va cac button tuong ung voi fragment_financial_overview
    EditText edit_startDate, edit_endDate, edit_tienthu, edit_tienchi;
    Button btt_StartDate, btt_endDate;
    DatabaseHelper databaseHelper;
    SimpleDateFormat simpleDateFormat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_financial_overview, container, false);
       //tham chieu den cac budget trong layout
        edit_startDate = view.findViewById(R.id.edit_rep_start);
        edit_endDate = view.findViewById(R.id.edit_rep_end);
        btt_StartDate = view.findViewById(R.id.btt_rep_start);
        btt_endDate = view.findViewById(R.id.btt_rep_end);

        databaseHelper = new DatabaseHelper(getContext());
        simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());

        //1. Nguoi dung chon thoi gian bat dau
        btt_StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2. He thong hien thi datepicker cho nguoi dung chon thoi gian bat dau
                showDatepicker(edit_startDate);
            }
        });

        //3. Nguoi dung chon thoi gian ket thuc
        btt_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //4.He thong hien thi datepicker cho nguoi dung chon thoi gian ket thuc
                showDatepicker(edit_endDate);
                calculateIncome();

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
                        //He thong cap nhat ngay
                        date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, year, month, day);
        //hien thi datepicker dialog
        datePickerDialog.show();
    }
//rang buoc ngay bat dau phai truoc ngay ket thuc

    //phuong thuc tinh tong tien thu
    public void calculateIncome(){
        String startDate = edit_startDate.getText().toString();
        String endDate = edit_endDate.getText().toString();
        //neu thoi gian bat dau va ket thuc chua duoc chon
        if(startDate.isEmpty() || endDate.isEmpty()){
            //hien thi thong bao Vui long chon ngay
            Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            Long st_Date = simpleDateFormat.parse(startDate).getTime();
            Long e_Date = simpleDateFormat.parse(endDate).getTime();

            //rang buoc ngay bat dau truoc ngay ket thuc
            if(st_Date > e_Date){
                //hien thi thong bao Thời gian không hợp lệ
                Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_LONG).show();
                return;
            }

        }catch (Exception e){

        }
    }

}