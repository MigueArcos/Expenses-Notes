package com.migue.zeus.expensesnotes.data.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.migue.zeus.expensesnotes.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Entity(
        tableName = "Expenses",
        foreignKeys = {
                @ForeignKey(entity = Icon.class, parentColumns = "Id", childColumns = "IconId"),
                @ForeignKey(entity = ExpenseCategory.class, parentColumns = "Id", childColumns = "ExpenseCategoryId")
        },
        indices = {
                @Index("IconId"),
                @Index("ExpenseCategoryId")
        })
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Name;
    private double Value;
    private Date CreationDate;
    private Date ModificationDate;
    private int ExpenseCategoryId;
    private int IconId;

    public Expense(String name, double value, Date creationDate, int expenseCategoryId) {
        Name = name;
        Value = value;
        CreationDate = creationDate;
        ExpenseCategoryId = expenseCategoryId;
        IconId = 1;
    }

    public Expense() {
    }

    public int getIconId() {
        return IconId;
    }

    public void setIconId(int iconId) {
        IconId = iconId;
    }

    public int getExpenseCategoryId() {
        return ExpenseCategoryId;
    }

    public void setExpenseCategoryId(int ExpenseCategoryId) {
        this.ExpenseCategoryId = ExpenseCategoryId;
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

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public Date getModificationDate() {
        return ModificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        ModificationDate = modificationDate;
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Expense[] populateData() {

        return new Expense[]{
                new Expense("Expense a", 367.0, parseDate("2018-12-01"), 1),
                new Expense("Expense b", 367.0, parseDate("2018-12-01"), 1),
                new Expense("Expense c", 367.0, parseDate("2018-12-05"), 1),
                new Expense("Expense d", 367.0, parseDate("2018-12-07"), 1),
                new Expense("Expense e", 367.0, parseDate("2018-12-07"), 1),
                new Expense("Expense f", 367.0, parseDate("2018-12-10"), 1),
                new Expense("Expense g", 367.0, parseDate("2018-12-10"), 1),
                new Expense("Expense h", 367.0, parseDate("2018-12-10"), 1),
                new Expense("Expense i", 367.0, parseDate("2018-12-10"), 1),
                new Expense("Expense j", 367.0, parseDate("2018-12-10"), 1),
                new Expense("Expense k", 367.0, parseDate("2018-12-12"), 1),
                new Expense("Expense l", 367.0, parseDate("2018-12-12"), 1),
                new Expense("Expense m", 367.0, parseDate("2018-12-15"), 1),
                new Expense("Expense n", 367.0, parseDate("2018-12-15"), 1),
                new Expense("Expense o", 367.0, parseDate("2018-12-16"), 1),
                new Expense("Expense p", 367.0, parseDate("2018-12-16"), 1),
                new Expense("Expense q", 367.0, parseDate("2018-12-17"), 1),
                new Expense("Expense r", 367.0, parseDate("2018-12-18"), 1),
                new Expense("Expense s", 367.0, parseDate("2018-12-18"), 1),
                new Expense("Expense t", 367.0, parseDate("2018-12-18"), 1),
                new Expense("Expense u", 367.0, parseDate("2018-12-18"), 1),
                new Expense("Expense v", 367.0, parseDate("2018-12-18"), 1),
                new Expense("Expense w", 367.0, parseDate("2018-12-19"), 1),
                new Expense("Expense x", 367.0, parseDate("2018-12-22"), 1),
                new Expense("Expense y", 367.0, parseDate("2018-12-26"), 1),
                new Expense("Expense z", 367.0, parseDate("2018-12-29"), 1),
        };
    }
}