package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

open class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Int? = null
    @ColumnInfo(name = "IsUploaded")
    var isUploaded: Boolean = false
}