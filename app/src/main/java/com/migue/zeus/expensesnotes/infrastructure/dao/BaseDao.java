package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;


@Dao
public interface BaseDao<DataModel> {

    @Insert
    long[] insert(DataModel... data);

    @Insert
    long insert(DataModel data);

    @Update
    void update(DataModel... data);

    @Update
    void update(DataModel data);


    @Delete
    void delete(DataModel... data);

}
