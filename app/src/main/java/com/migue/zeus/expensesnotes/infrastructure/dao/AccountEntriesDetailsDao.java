package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.migue.zeus.expensesnotes.data.models.AccountEntryDetail;

import java.util.List;

@Dao
public interface AccountEntriesDetailsDao extends BaseDao<AccountEntryDetail>{

    @Query("SELECT * FROM AccountEntriesDetails")
    List<AccountEntryDetail> getExpensesDeatils();
}
