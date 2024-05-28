package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.dao.DatabaseHelper;
import com.example.myapplication.model.Category;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link CreateNoteFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class CreateNoteFragment extends Fragment implements CategoryFragment.OnCategorySelectedListener{
    private EditText etAmount, etDescription;
    private Button btnSelectDate, btnSave, btnSelectCategory;
    private TextView tvDate;
    private ImageButton btnBack;
    private Spinner spin;
    String[] items = {"Thu tiền", "Chi tiền"};
    private DatabaseHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_note, container, false);
        // Kết nối các thành phần UI
        etAmount = view.findViewById(R.id.etAmount);
        etDescription = view.findViewById(R.id.etDescription);
        btnSelectDate = view.findViewById(R.id.btnSelectDate);
        btnSave = view.findViewById(R.id.btnSave);
        btnSelectCategory = view.findViewById(R.id.btnSelectCategory);
        tvDate = view.findViewById(R.id.tvDate);
        btnBack = view.findViewById(R.id.btn_back);
        spin = view.findViewById(R.id.spinner);

        dbHelper = new DatabaseHelper(getContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), R.layout.custom_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Thiết lập ArrayAdapter cho Spinner
        spin.setAdapter(adapter);

        btnBack.setOnClickListener(v -> getActivity().onBackPressed());
        // 4. Hiển thị hộp thoại chọn ngày khi nhấn
        btnSelectDate.setOnClickListener(v -> showDatePickerDialog());
        // 7. Xử lý khi nhấn nút lưu
        btnSave.setOnClickListener(v -> createNewNote());
        // 6. Xử lý nhấn chọn hạng mục
        btnSelectCategory.setOnClickListener(v -> showCategorySelectionDialog());

        // 5. Đặt ngày hiện tại cho tvDate
        tvDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));

        return view;
    }

    private void showCategorySelectionDialog() {
        CategoryFragment categoryFragment = new CategoryFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_category, categoryFragment);
        transaction.addToBackStack("Category");
        transaction.commit();
    }

    // 4. Phương thức hiển thị hộp thoại chọn ngày
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

    // 8. Phương thức tạo ghi chú mới
    private void createNewNote() {
        // Lấy giá trị từ các trường nhập liệu
        String amountText = etAmount.getText().toString().trim();
        int amount = Integer.parseInt(amountText);
        String description = etDescription.getText().toString().trim();
        String date = tvDate.getText().toString().trim();
        String type = spin.getSelectedItem().toString();
        // 9. Kiểm tra thông tin
        if (amountText.isEmpty()) {
            //10.1 Hiển thị thông báo "Vui lòng nhập lại"
            Toast.makeText(getContext(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show(); // Kiểm tra người dùng nhập số tiền chưa
            return; // Dừng lại nếu trường số tiền trống
        }
        String selectedCategory = btnSelectCategory.getText().toString();
        if (selectedCategory.equals("Chọn hạng mục")) { // Kiểm tra xem người dùng đã chọn hạng mục chưa
            // 10.1 Hiển thị thông báo "Vui lòng nhập lại"
            Toast.makeText(getContext(), "Vui lòng chọn hạng mục", Toast.LENGTH_SHORT).show();
            return; // Dừng lại nếu chưa chọn hạng mục
        }
        // 10. Mở kết nối cơ sở dữ liệu để ghi dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_AMOUNT, amount);
        values.put(DatabaseHelper.COLUMN_TYPE, type);
        values.put(DatabaseHelper.COLUMN_CATEGORY, selectedCategory);
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_DATE, date);

        long newRowId = db.insert(DatabaseHelper.TABLE_CREATE_NOTE, null, values);
        if (newRowId == -1) {
            Toast.makeText(getContext(), "Lỗi khi lưu giao dịch", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("CreateNoteFragment", "Dữ liệu mới đã được lưu: ID = " + newRowId);
            Log.d("CreateNoteFragment", "Số tiền: " + amount);
            Log.d("CreateNoteFragment", "Hạng mục: " + selectedCategory);
            Log.d("CreateNoteFragment", "Loại: " + type);
            Log.d("CreateNoteFragment", "Mô tả: " + description);
            Log.d("CreateNoteFragment", "Ngày: " + date);
            // 11. Hiển thị thông báo "Lưu thành công"
            Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
            etAmount.setText("");  // Xóa trường số tiền
            etDescription.setText(""); // Xóa trường chú thích

            // Đặt lại giá trị mặc định cho ngày và loại
            tvDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
            spin.setSelection(0); // Chọn lại mục đầu tiên trong Spinner (Thu tiền)
            btnSelectCategory.setText("Chọn hạng mục");

        }

    }

    @Override
    public void onCategorySelected(Category category) {
        // Xử lý hạng mục đã chọn
        Toast.makeText(getContext(), "Category selected: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
        // Quay lại CreateNoteFragment
        getParentFragmentManager().popBackStack();
        // Cập nhật UI hoặc lưu trữ giá trị hạng mục đã chọn
        btnSelectCategory.setText(category.getCategoryName());
    }
}
