package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.model.Category;

import java.util.List;

public class CategoryFragment extends Fragment {
    //    ListView listView;
    ListView expenseListView;
    ListView incomeListView;
    ImageButton backButton;
    Button expenseButton;
    Button incomeButton;

    ArrayAdapter<Category> expenseArrayAdapter;
    List<Category> expenseCategory = Category.getExpenseCategory();
    ArrayAdapter<Category> incomeArrayAdapter;
    List<Category> incomeCategory = Category.getIncomeCategory();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        backButton = view.findViewById(R.id.back_btn);

//      listView = view.findViewById(R.id.listview);
//      categoryList = Category.getCategoryList();
//      categoryArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryList);
//      listView.setAdapter(categoryArrayAdapter);

        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        expenseButton = view.findViewById(R.id.expense_btn);
        incomeButton = view.findViewById(R.id.income_btn);

        expenseListView = view.findViewById(R.id.expense_listview);
        incomeListView = view.findViewById(R.id.income_listview);

        expenseArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, expenseCategory);
        expenseListView.setAdapter(expenseArrayAdapter);

        incomeArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, incomeCategory);
        incomeListView.setAdapter(incomeArrayAdapter);

        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = expenseArrayAdapter.getItem(position);
                handleCategorySelection(selectedCategory);
            }
        });

        incomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category selectedCategory = incomeArrayAdapter.getItem(position);
                handleCategorySelection(selectedCategory);
            }
        });

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListView("expense");
            }
        });

        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListView("income");
            }
        });

        // Show the expense list view by default
        showListView("expense");

        return view;
    }

    private void showListView(String type) {
        if (type.equals("expense")) {
            expenseListView.setVisibility(View.VISIBLE);
            incomeListView.setVisibility(View.GONE);
        } else if (type.equals("income")) {
            incomeListView.setVisibility(View.VISIBLE);
            expenseListView.setVisibility(View.GONE);
        }
    }

    private void handleCategorySelection(Category category) {
        Toast.makeText(getActivity(), "Selected: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}