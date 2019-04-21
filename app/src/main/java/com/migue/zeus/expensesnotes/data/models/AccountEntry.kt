package com.migue.zeus.expensesnotes.data.models


import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters.parseDate

import java.util.Date


@Entity(tableName = "AccountEntries",
        foreignKeys = [ForeignKey(entity = AccountEntryCategory::class, parentColumns = ["Id"], childColumns = ["AccountEntryCategoryId"])],
        indices = [Index("AccountEntryCategoryId")])
class AccountEntry : BaseEntity {
    override fun getReadableName(): String? {
        return name
    }
    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "AccountEntryCategoryId")
    var accountEntryCategoryId: Long? = null
    @ColumnInfo(name = "Date")
    var date: Date? = Date()
    @ColumnInfo(name = "Revenue")
    var revenue: Int? = -1

    constructor(parcel: Parcel) : super(parcel) {
        name = parcel.readString()
        accountEntryCategoryId = parcel.readValue(Long::class.java.classLoader) as? Long
        date = parcel.readValue(Date::class.java.classLoader) as? Date
        revenue = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    constructor()

    @Ignore
    constructor(name: String, date: Date?, accountEntryCategoryId: Long) {
        this.accountEntryCategoryId = accountEntryCategoryId
        this.name = name
        this.date = date
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(name)
        parcel.writeValue(accountEntryCategoryId)
        parcel.writeValue(date)
        parcel.writeValue(revenue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountEntry> {
        override fun createFromParcel(parcel: Parcel): AccountEntry {
            return AccountEntry(parcel)
        }

        override fun newArray(size: Int): Array<AccountEntry?> {
            return arrayOfNulls(size)
        }

        fun populateData(): Array<AccountEntry> {

            return arrayOf(

            )
        }
    }

}