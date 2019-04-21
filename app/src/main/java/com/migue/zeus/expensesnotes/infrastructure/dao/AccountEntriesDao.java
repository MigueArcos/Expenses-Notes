package com.migue.zeus.expensesnotes.infrastructure.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.migue.zeus.expensesnotes.data.models.AccountEntry;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;

import java.util.Date;
import java.util.List;


@Dao
public interface AccountEntriesDao extends BaseDao<AccountEntry>{

    @Query("SELECT DISTINCT Date FROM AccountEntries WHERE Revenue = :revenue ORDER BY Date DESC")
    List<Date> getAccountEntriesDates(int revenue);

    @Query("SELECT * FROM AccountEntries WHERE Id = :id")
    AccountEntryWithDetails getAccountEntryById(int id);

    @Query("SELECT * FROM AccountEntries WHERE Date = :date AND Revenue = :revenue")
    List<AccountEntryWithDetails> getAccountEntriesByDate(Date date, int revenue);
}
