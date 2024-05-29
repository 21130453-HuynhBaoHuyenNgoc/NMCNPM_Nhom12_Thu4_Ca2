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
        btt_StartDate = view.findViewById(R.id.btt_rep_start);
        btt_endDate = view.findViewById(R.id.btt_rep_end);
        edit_tienthu = view.findViewById(R.id.edit_tienthu);
        edit_tienchi = view.findViewById(R.id.edit_tienchi);

        databaseHelper = new DatabaseHelper(getContext());
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // 6.1. Người dùng nhấp vào button Từ ngày
        btt_StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 6.5.Hệ thống hiển thị ngày được chọn ở editText Ngày bắt đầu
                showDatepicker(edit_startDate);
            }
        });

        // 6.6. Người dùng nhấp vào button Đến ngày
        btt_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 6.10. Hệ thống hiển thị ngày được chọn ở editText Ngày kết thúc
                showDatepicker(edit_endDate);
            }
        });

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //6.16. Hệ thống hiển thị số tiền ở Mức thu và Mức chi
                    updateFinancialOverview();
                }
            }
        };

        edit_startDate.setOnFocusChangeListener(onFocusChangeListener);
        edit_endDate.setOnFocusChangeListener(onFocusChangeListener);

        return view;
    }

    // Phương thức hiển thị số tiền ở Mức thu và Mức chi
    private void updateFinancialOverview() {
        calculateTotalIncome();
        calculateTotalExpense();
    }

    // 6.2. và 6.7. Hệ thống hiển thị datepicker dialog
    private void showDatepicker(final EditText date) {
        //lấy ngày thang nam hien tai
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        //6.2. và 6.7 Mặc định là ngày tháng năm hiện tại
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                //6.3.và 6.8. Người dùng chọn ngày
                //6.4.và 6.9. Người dùng nhấn OK
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //editText duoc cap nhat ngay thang nam tu thoi gian nguoi dung chon
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        updateFinancialOverview();
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    //phuong thuc tinh tong chi
    private void calculateTotalIncome() {
        String startDate = edit_startDate.getText().toString();
        String endDate = edit_endDate.getText().toString();

        // Alternative flow <Chưa chọn thời gian bắt đầu>
        if (startDate.isEmpty()) {
            // hien thi thong bao Vui long chon ngay
            Toast.makeText(getContext(), "Vui lòng chọn ngày bắt đầu", Toast.LENGTH_LONG).show();
            return;
        }
        //Alternative flow <Chưa chọn thời gian kết thúc>
        if(endDate.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng chọn ngày kết thúc", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            long st_Date = simpleDateFormat.parse(startDate).getTime();
            long e_Date = simpleDateFormat.parse(endDate).getTime();

            // 6.11. Hệ thống kiểm tra thời gian bắt đầu có trước thời gian kết thúc không
            if (st_Date > e_Date) {
                // Alternative flow <Thời gian không hợp lệ>
                Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_LONG).show();
                return;
            }

            // thuc hien truy van
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            // 6.12.  Hệ thống lấy từ cơ sở dữ liệu tổng số tiền thu ở thời gian người dùng chọn
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
            //6.13. Hệ thống cập nhật số tiền cho Mức thu
            edit_tienthu.setText(addCommas(String.valueOf(totalThu)));

        } catch (Exception e) {
            Toast.makeText(getContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_LONG).show();
        }
    }

    //phuong thuc tinh tong chi
    private void calculateTotalExpense() {
        String startDate = edit_startDate.getText().toString();
        String endDate = edit_endDate.getText().toString();

        // Alternative flow <Chưa chọn thời gian bắt đầu>
        if (startDate.isEmpty()) {
            // hien thi thong bao Vui long chon ngay
            Toast.makeText(getContext(), "Vui lòng chọn ngày bắt đầu", Toast.LENGTH_LONG).show();
            return;
        }
        // Alternative flow <Chưa chọn thời gian kết thúc>
        if(endDate.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng chọn ngày kết thúc", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            long st_Date = simpleDateFormat.parse(startDate).getTime();
            long e_Date = simpleDateFormat.parse(endDate).getTime();

            // 6.11. Hệ thống kiểm tra thời gian bắt đầu có trước thời gian kết thúc không
            if (st_Date > e_Date) {
                // Alternative flow <Thời gian không hợp lệ>
                Toast.makeText(getContext(), "Thời gian không hợp lệ", Toast.LENGTH_LONG).show();
                return;
            }

            // thuc hien truy van
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            // 6.14. Hệ thống lấy từ cơ sở dữ liệu tổng số tiền chi ở thời gian người dùng chọn
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
            // 6.15. Hệ thống cập nhật số tiền cho Mức chi
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
