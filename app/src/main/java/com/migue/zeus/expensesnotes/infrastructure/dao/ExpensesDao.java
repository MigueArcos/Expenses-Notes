package com.migue.zeus.expensesnotes.infrastructure.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;

import java.util.Date;
import java.util.List;


@Dao
public interface ExpensesDao extends BaseDao<Expense>{

    @Query("SELECT Date FROM Expenses GROUP BY Date")
    List<Date> getExpensesDates();

    @Query("SELECT * FROM Expenses WHERE Date = :date")
    List<ExpenseWithDetails> getExpensesByDate(Date date);
}
