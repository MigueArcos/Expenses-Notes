package com.migue.zeus.expensesnotes.infrastructure.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.migue.zeus.expensesnotes.data.models.Icon;

import java.util.List;

@Dao
public interface IconsDao extends BaseDao<Icon>{
    @Query("SELECT * FROM Icons")
    List<Icon> getAllIcons();

}
