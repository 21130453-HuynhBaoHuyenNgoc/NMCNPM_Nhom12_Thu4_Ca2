package com.example.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    int categoryId;
    String categoryName;
    String categoryDescription;
    String categoryType;
    Category fatherCategory;

    List<Category> categoryList = new ArrayList<>();

    public Category(int categoryId, String categoryName, String categoryDescription, String categoryType, Category fatherCategory) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.categoryType = categoryType;
        this.fatherCategory = fatherCategory;
    }

    //tạo 1 bảng chứa các hạng mục
    public static List<String> getCategoryList() {
//    public static List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();

        Category cat1 = new Category(1, "Auto & Transport", "", "Chi", null);
        Category cat2 = new Category(2, "Bank", "", "Chi", null);
        Category cat3 = new Category(3, "Clothing", "", "Chi", null);
        Category cat4 = new Category(4, "Entertainment", "", "Chi", null);
        Category cat5 = new Category(5, "Food & Dining", "", "Chi", null);
        Category cat6 = new Category(6, "Gifts & Donations", "", "Chi", null);
        Category cat7 = new Category(7, "Health & Fitness", "", "Chi", null);
        Category cat8 = new Category(8, "Home", "", "Chi", null);
        Category cat9 = new Category(9, "Kids", "", "Chi", null);
        Category cat10 = new Category(10, "Personal", "", "Chi", null);
        Category cat11 = new Category(11, "Pets", "", "Chi", null);
        Category cat12 = new Category(12, "Utilities", "", "Chi", null);
        Category cat13 = new Category(13, "Lend", "", "Chi", null);
        Category cat14 = new Category(14, "Repayment", "", "Chi", null);

        Category cat20 = new Category(20, "Fuel", "", "Chi", cat1);
        Category cat21 = new Category(21, "Insurance", "", "Chi", cat1);
        Category cat22 = new Category(22, "Taxi", "", "Chi", cat1);

        Category cat23 = new Category(23, "Transfer Fee", "", "Chi", cat2);

        Category cat24 = new Category(24, "Accessories", "", "Chi", cat3);
        Category cat25 = new Category(25, "Clothes", "", "Chi", cat3);

        Category cat26 = new Category(26, "Music", "", "Chi", cat4);
        Category cat27 = new Category(27, "Travel", "", "Chi", cat4);

        Category cat28 = new Category(28, "Groceries", "", "Chi", cat5);
        Category cat29 = new Category(29, "Bars & Restaurant", "", "Chi", cat5);

        Category cat30 = new Category(30, "Charities", "", "Chi", cat6);
        Category cat31 = new Category(31, "Gifts", "", "Chi", cat6);

        Category cat32 = new Category(32, "Doctor", "", "Chi", cat7);
        Category cat33 = new Category(33, "Sports", "", "Chi", cat7);

        Category cat34 = new Category(34, "Home Services", "", "Chi", cat8);
        Category cat35 = new Category(35, "Rent", "", "Chi", cat8);

        Category cat36 = new Category(36, "Allowance", "", "Chi", cat9);
        Category cat37 = new Category(37, "Toys", "", "Chi", cat9);

        Category cat38 = new Category(38, "Education", "", "Chi", cat10);
        Category cat39 = new Category(39, "Hobbies", "", "Chi", cat10);

        Category cat15 = new Category(15, "Bonus", "", "Thu", null);
        Category cat16 = new Category(16, "Borrow", "", "Thu", null);
        Category cat17 = new Category(17, "Collecting Debts", "", "Thu", null);
        Category cat18 = new Category(18, "Interest", "", "Thu", null);
        Category cat19 = new Category(19, "Salary", "", "Thu", null);

        categoryList.add(cat1);
        categoryList.add(cat2);
        categoryList.add(cat3);
        categoryList.add(cat4);
        categoryList.add(cat5);
        categoryList.add(cat6);
        categoryList.add(cat7);
        categoryList.add(cat8);
        categoryList.add(cat9);
        categoryList.add(cat10);
        categoryList.add(cat11);
        categoryList.add(cat12);
        categoryList.add(cat13);
        categoryList.add(cat14);
        categoryList.add(cat15);
        categoryList.add(cat16);
        categoryList.add(cat17);
        categoryList.add(cat18);
        categoryList.add(cat19);
        categoryList.add(cat20);
        categoryList.add(cat21);
        categoryList.add(cat22);
        categoryList.add(cat23);
        categoryList.add(cat24);
        categoryList.add(cat25);
        categoryList.add(cat26);
        categoryList.add(cat27);
        categoryList.add(cat28);
        categoryList.add(cat29);
        categoryList.add(cat30);
        categoryList.add(cat31);
        categoryList.add(cat32);
        categoryList.add(cat33);
        categoryList.add(cat34);
        categoryList.add(cat35);
        categoryList.add(cat36);
        categoryList.add(cat37);
        categoryList.add(cat38);
        categoryList.add(cat39);

//        return categoryList;

        List<String> nameList = new ArrayList<>();

        for (Category cat : categoryList) {
            nameList.add(cat.getCategoryName());
        }

        return nameList;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Category getFatherCategory() {
        return fatherCategory;
    }

    public void setFatherCategory(Category fatherCategory) {
        this.fatherCategory = fatherCategory;
    }
}
