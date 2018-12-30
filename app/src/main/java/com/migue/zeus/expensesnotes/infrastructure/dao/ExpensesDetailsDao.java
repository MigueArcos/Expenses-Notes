package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.migue.zeus.expensesnotes.data.models.ExpenseDetail;

import java.util.List;

@Dao
public interface ExpensesDetailsDao extends BaseDao<ExpenseDetail>{

    @Query("SELECT * FROM ExpensesDetails")
    List<ExpenseDetail> getExpensesDeatils();
}
