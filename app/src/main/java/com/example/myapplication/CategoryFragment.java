package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.model.Category;

import java.util.List;

public class CategoryFragment extends Fragment {
    ListView listView;
    ImageButton backButton;

    ArrayAdapter<String> categoryArrayAdapter;
    List<String> categoryList;

//    ArrayAdapter<Category> categoryArrayAdapter;
//    List<Category> categoryList = Category.getCategoryList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        backButton = view.findViewById(R.id.back_btn);
        listView = view.findViewById(R.id.listview);

        categoryList = Category.getCategoryList();

        categoryArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryList);
        listView.setAdapter(categoryArrayAdapter);
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        categoryList = Category.getCategoryList();
        Log.d("CategoryFragment", "Category List: " + categoryList);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}