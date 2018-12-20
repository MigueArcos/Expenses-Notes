package com.migue.zeus.expensesnotes.infrastructure.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.migue.zeus.expensesnotes.data.models.Expense;

import java.util.Date;
import java.util.List;


@Dao
public interface ExpensesDao extends BaseDao<Expense>{

    @Query("SELECT CreationDate FROM Expenses GROUP BY CreationDate")
    List<Date> getExpensesDates();

    @Query("SELECT * FROM Expenses WHERE CreationDate = :creationDate")
    List<Expense> getExpensesByDate(Date creationDate);
}
