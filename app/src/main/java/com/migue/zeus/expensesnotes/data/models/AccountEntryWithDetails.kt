package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import android.os.Parcel
import android.os.Parcelable

class AccountEntryWithDetails : Parcelable{
    @Embedded
    var accountEntry : AccountEntry? = null
    @Relation(entity = AccountEntryDetail::class, parentColumn = "Id", entityColumn = "AccountEntryId")
    var accountEntryDetails : List<AccountEntryDetail>? = null

    constructor(parcel: Parcel) : this() {
        accountEntry = parcel.readParcelable(AccountEntry::class.java.classLoader)
        accountEntryDetails = parcel.createTypedArrayList(AccountEntryDetail)
    }
    constructor()
    fun getTotal() : Double {
        var total : Double = 0.0
        for (accountEntryDetail in accountEntryDetails!!) {
            total += accountEntryDetail.value
        }
        return total
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(accountEntry, flags)
        parcel.writeTypedList(accountEntryDetails)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AccountEntryWithDetails> {
        override fun createFromParcel(parcel: Parcel): AccountEntryWithDetails {
            return AccountEntryWithDetails(parcel)
        }

        override fun newArray(size: Int): Array<AccountEntryWithDetails?> {
            return arrayOfNulls(size)
        }
    }


}