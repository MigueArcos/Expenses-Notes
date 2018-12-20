package com.migue.zeus.expensesnotes.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.migue.zeus.expensesnotes.R;

import java.lang.reflect.Type;
import java.util.Map;

@Entity(tableName = "ExpensesCategories")
public class ExpenseCategory {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Name;
    private int IconId;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String name, int iconId) {
        Name = name;
        this.IconId = iconId;
    }
    public ExpenseCategory(String name) {
        Name = name;
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public int getIconId() {
        return IconId;
    }

    public void setIconId(int iconId) {
        IconId = iconId;
    }

    public static ExpenseCategory[] populateData(final Context context) {
        String[] rawCategories = context.getResources().getStringArray(R.array.expenses_categories);
        ExpenseCategory[] categories = new ExpenseCategory[rawCategories.length];
        int i = 0;
        //Type type = new TypeToken<Map<String, String>>(){}.getType();
        //Map<String, String> myMap = gson.fromJson("{'k1':'apple','k2':'orange'}", type);
        for (String rawCategory : rawCategories){
            categories[i] = new ExpenseCategory(rawCategory);
            i++;
        }
        return categories;
    }
}

