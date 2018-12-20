package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.migue.zeus.expensesnotes.data.models.ExpenseCategory;

import java.util.List;

@Dao
public interface ExpensesCategoriesDao {
    @Query("SELECT * FROM ExpensesCategories")
    List<ExpenseCategory> getAllExpenseCategories();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExpenseCategory... expenseCategories);

    @Update
    void update(ExpenseCategory... expenseCategories);

    @Delete
    void delete(ExpenseCategory... expenseCategories);
}