package com.example.myapplication;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// tao fragment Barchart
public class FinancialOverviewFragment extends Fragment {
    // khai bao ra cac edittext va cac button tuong ung voi fragment_financial_overview
    EditText edit_startDate, edit_endDate, edit_tienthu, edit_tienchi;
    Button btt_StartDate, btt_endDate;
    DatabaseHelper databaseHelper;
    SimpleDateFormat simpleDateFormat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_financial_overview, container, false);
        // tham chieu den cac budget trong layout
        edit_startDate = view.findViewById(R.id.edit_rep_start);
        edit_endDate = view.findViewById(R.id.edit_rep_end);
//        btt_StartDate = view.findViewById(R.id.btt_rep_start);
//        btt_endDate = view.findViewById(R.id.btt_rep_end);
        edit_tienthu = view.findViewById(R.id.edit_tienthu);
        edit_tienchi = view.findViewById(R.id.edit_tienchi);

        databaseHelper = new DatabaseHelper(getContext());
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // 1. Nguoi dung chon thoi gian bat dau
        btt_StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2. He thong hien thi datepicker cho nguoi dung chon thoi gian bat dau
                showDatepicker(edit_startDate);
            }
        });

        // 3. Nguoi dung chon thoi gian ket thuc
        btt_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 4. He thong hien thi datepicker cho nguoi dung chon thoi gian ket thuc
                showDatepicker(edit_endDate);
            }
        });

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    updateFinancialOverview();
                }
            }
        };

        edit_startDate.setOnFocusChangeListener(onFocusChangeListener);
        edit_endDate.setOnFocusChangeListener(onFocusChangeListener);

        return view;
    }

    private void updateFinancialOverview() {
        calculateTotalIncome();
        calculateTotalExpense();
    }

    // phuong thuc hien thi datepickerdialog
    private void showDatepicker(final EditText date) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // He thong cap nhat ngay
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        updateFinancialOverview();
                    }
                }, year, month, day);
        // hien thi datepicker dialog
        datePickerDialog.show();
    }

    // phuong thuc tinh tong tien thu
    private void calculateTotalIncome() {
        String startDate = edit_startDate.getText().toString();
        String endDate = edit_endDate.getText().toString();

        // neu thoi gian bat dau va ket thuc chua duoc chon
        if (startDate.isEmpty() || endDate.isEmpty()) {
            // hien thi thong bao Vui long chon ngay
            Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            long st_Date = simpleDateFormat.parse(startDate).getTime();
            long e_Date = simpleDateFormat.parse(endDate).getTime();

            // rang buoc ngay bat dau truoc ngay ket thuc
            if (st_Date > e_Date) {
                // hien thi thong bao Thời gian không hợp lệ
                Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_LONG).show();
                return;
            }

            // thuc hien truy van
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            // truy van tong thu
            String sqlThu = "SELECT SUM(" + DatabaseHelper.COLUMN_AMOUNT + ") FROM " + DatabaseHelper.TABLE_CREATE_NOTE +
                    " WHERE " + DatabaseHelper.COLUMN_TYPE + " = ? AND " + DatabaseHelper.COLUMN_DATE + " BETWEEN ? AND ?";
            // Dung Cursor
            Cursor cursorThu = db.rawQuery(sqlThu, new String[]{"Thu tiền", startDate, endDate});
            int totalThu = 0;

            // di chuyen con tro den vi tri dau tien trong hang
            if (cursorThu.moveToFirst() && !cursorThu.isNull(0)) {
                // lay gia tri tu cot dau tien la cot COLUMN_AMOUNT
                totalThu = cursorThu.getInt(0);
            }
            cursorThu.close();

            // setText cho edit_thu
            edit_tienthu.setText(addCommas(String.valueOf(totalThu)));

        } catch (Exception e) {
            Toast.makeText(getContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_LONG).show();
        }
    }
    // phuong thuc tinh tong tien chi
    private void calculateTotalExpense() {
        String startDate = edit_startDate.getText().toString();
        String endDate = edit_endDate.getText().toString();

        // neu thoi gian bat dau va ket thuc chua duoc chon
        if (startDate.isEmpty() || endDate.isEmpty()) {
            // hien thi thong bao Vui long chon ngay
            Toast.makeText(getContext(), "Vui lòng chọn ngày", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            long st_Date = simpleDateFormat.parse(startDate).getTime();
            long e_Date = simpleDateFormat.parse(endDate).getTime();

            // rang buoc ngay bat dau truoc ngay ket thuc
            if (st_Date > e_Date) {
                // hien thi thong bao Thời gian không hợp lệ
                Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_LONG).show();
                return;
            }

            // thuc hien truy van
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            // truy van tong thu
            String sqlChi = "SELECT SUM(" + DatabaseHelper.COLUMN_AMOUNT + ") FROM " + DatabaseHelper.TABLE_CREATE_NOTE +
                    " WHERE " + DatabaseHelper.COLUMN_TYPE + " = ? AND " + DatabaseHelper.COLUMN_DATE + " BETWEEN ? AND ?";
            // Dung Cursor
            Cursor cursorChi = db.rawQuery(sqlChi, new String[]{"Chi tiền", startDate, endDate});
            int totalChi = 0;

            // di chuyen con tro den vi tri dau tien trong hang
            if (cursorChi.moveToFirst() && !cursorChi.isNull(0)) {
                // lay gia tri tu cot dau tien la cot COLUMN_AMOUNT
                totalChi = cursorChi.getInt(0);
            }
            cursorChi.close();

            // setText cho edit_thu
            edit_tienchi.setText(addCommas(String.valueOf(totalChi)));

        } catch (Exception e) {
            Toast.makeText(getContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_LONG).show();
        }
    }
    //doi dinh dang tien them dau phay vao
    public static String addCommas(String number) {
        // Chuyển chuỗi thành số nguyên
        long num = Long.parseLong(number);

        // Định dạng số với dấu phẩy phân cách hàng nghìn
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(num);
    }
}
