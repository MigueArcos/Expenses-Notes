package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "AccountEntriesDetails",
        foreignKeys = [
            ForeignKey(entity = AccountEntry::class, parentColumns = ["Id"], childColumns = ["AccountEntryId"]),
            ForeignKey(entity = Account::class, parentColumns = ["Id"], childColumns = ["AccountId"])
        ],
        indices = [
            Index("AccountEntryId"),
            Index("AccountId")
        ])
class AccountEntryDetail : BaseEntity {
    override fun getReadableName(): String? {
        return null
    }
    @ColumnInfo(name = "AccountEntryId")
    var accountEntryId: Long? = null
    @ColumnInfo(name = "AccountId")
    var accountId : Long? = null
    @ColumnInfo(name = "Value")
    var value : Double = 0.0

    constructor(parcel: Parcel) : super(parcel) {
        accountEntryId = parcel.readValue(Long::class.java.classLoader) as? Long
        accountId = parcel.readValue(Long::class.java.classLoader) as? Long
        value = parcel.readDouble()
    }

    constructor()

    @Ignore
    constructor(accountEntryId: Long, accountId: Long, value: Double) {
        this.accountEntryId = accountEntryId
        this.accountId = accountId
        this.value = value
        var a = 0
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeValue(accountEntryId)
        parcel.writeValue(accountId)
        parcel.writeDouble(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountEntryDetail> {
        override fun createFromParcel(parcel: Parcel): AccountEntryDetail {
            return AccountEntryDetail(parcel)
        }

        override fun newArray(size: Int): Array<AccountEntryDetail?> {
            return arrayOfNulls(size)
        }

        fun populateData(): Array<AccountEntryDetail> {

            return arrayOf(

            )
        }
    }
}