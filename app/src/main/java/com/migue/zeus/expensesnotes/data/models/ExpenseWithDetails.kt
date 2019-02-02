package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import android.os.Parcel
import android.os.Parcelable

class ExpenseWithDetails() : Parcelable{
    @Embedded
    var expense : Expense? = null
    @Relation(entity = ExpenseDetail::class, parentColumn = "Id", entityColumn = "ExpenseId")
    var expenseDetails : List<ExpenseDetail>? = null

    constructor(parcel: Parcel) : this() {
        expense = parcel.readParcelable(Expense::class.java.classLoader)
        expenseDetails = parcel.createTypedArrayList(ExpenseDetail)
    }

    fun getTotal() : Double {
        var total : Double = 0.0
        for (expenseDetail in expenseDetails!!) {
            total += expenseDetail.value
        }
        return total
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(expense, flags)
        parcel.writeTypedList(expenseDetails)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseWithDetails> {
        override fun createFromParcel(parcel: Parcel): ExpenseWithDetails {
            return ExpenseWithDetails(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseWithDetails?> {
            return arrayOfNulls(size)
        }
    }
}