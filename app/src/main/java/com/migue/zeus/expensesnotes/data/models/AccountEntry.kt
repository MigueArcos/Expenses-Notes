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
                    AccountEntry("AccountEntry a", parseDate("2018-12-01"), 1),
                    AccountEntry("AccountEntry b", parseDate("2018-12-01"), 2),
                    AccountEntry("AccountEntry c", parseDate("2018-12-05"), 3),
                    AccountEntry("AccountEntry d", parseDate("2018-12-07"), 4),
                    AccountEntry("AccountEntry e", parseDate("2018-12-07"), 1),
                    AccountEntry("AccountEntry f", parseDate("2018-12-10"), 1),
                    AccountEntry("AccountEntry g", parseDate("2018-12-10"), 1),
                    AccountEntry("AccountEntry h", parseDate("2018-12-10"), 1),
                    AccountEntry("AccountEntry i", parseDate("2018-12-10"), 1),
                    AccountEntry("AccountEntry j", parseDate("2018-12-10"), 1),
                    AccountEntry("AccountEntry k", parseDate("2018-12-12"), 1),
                    AccountEntry("AccountEntry l", parseDate("2018-12-12"), 1),
                    AccountEntry("AccountEntry m", parseDate("2018-12-15"), 1),
                    AccountEntry("AccountEntry n", parseDate("2018-12-15"), 1),
                    AccountEntry("AccountEntry o", parseDate("2018-12-16"), 1),
                    AccountEntry("AccountEntry p", parseDate("2018-12-16"), 1),
                    AccountEntry("AccountEntry q", parseDate("2018-12-17"), 1),
                    AccountEntry("AccountEntry r", parseDate("2018-12-18"), 1),
                    AccountEntry("AccountEntry s", parseDate("2018-12-18"), 1),
                    AccountEntry("AccountEntry t", parseDate("2018-12-18"), 1),
                    AccountEntry("AccountEntry u", parseDate("2018-12-18"), 1),
                    AccountEntry("AccountEntry v", parseDate("2018-12-18"), 1),
                    AccountEntry("AccountEntry w", parseDate("2018-12-19"), 1),
                    AccountEntry("AccountEntry x", parseDate("2018-12-22"), 1),
                    AccountEntry("AccountEntry y", parseDate("2018-12-26"), 1),
                    AccountEntry("AccountEntry z", parseDate("2018-12-29"), 1)
            )
        }
    }

}