package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;


@Dao
public interface BaseDao<DataModel> {

    @Insert
    abstract void insert(DataModel... data);

    @Update
    abstract void update(DataModel... data);

    @Delete
    abstract void delete(DataModel... data);

}
