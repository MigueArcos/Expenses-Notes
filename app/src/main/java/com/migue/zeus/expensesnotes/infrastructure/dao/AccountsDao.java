package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.migue.zeus.expensesnotes.data.models.Account;

import java.util.List;


@Dao
public interface AccountsDao {
    @Query("SELECT * FROM Accounts")
    List<Account> getAllAccounts();
    @Insert
    void insert(Account... accounts);

    @Update
    void update(Account... accounts);

    @Delete
    void delete(Account... accounts);
}
