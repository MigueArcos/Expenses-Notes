package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.migue.zeus.expensesnotes.data.models.Finance;

import java.util.List;

@Dao
public interface FinancesDao extends BaseDao<Finance> {

    @Query("SELECT * FROM Finances")
    List<Finance> getAllFinances();
    @Query("SELECT SUM(Value*IsActive) FROM Finances")
    double getTotal();
}
