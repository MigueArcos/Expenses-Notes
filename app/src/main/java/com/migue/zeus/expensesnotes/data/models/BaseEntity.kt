package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

abstract class BaseEntity : Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Long? = null
    @ColumnInfo(name = "IsUploaded")
    var isUploaded: Boolean = false

    constructor()

    constructor(parcel: Parcel)  {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        isUploaded = parcel.readByte() != 0.toByte()
    }

    abstract fun getReadableName() : String?

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeByte(if (isUploaded) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }
}