package com.migue.zeus.expensesnotes.data.models


import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import java.util.Date


@Entity(
        tableName = "Accounts",
        foreignKeys = [ForeignKey(entity = Icon::class, parentColumns = ["Id"], childColumns = ["iconId"])],
        indices = [Index("iconId")])
class Account {
    @PrimaryKey
    var id: Int = 0
    var name: String? = null
    var value: Float? = null
    var creationDate: Date? = null
    var modificationDate: Date? = null
    var isActiveAccount: Boolean = false
    var iconId: Int = 0
}
