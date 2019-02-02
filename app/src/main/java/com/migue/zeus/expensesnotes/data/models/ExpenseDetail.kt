package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "ExpensesDetails",
        foreignKeys = [
            ForeignKey(entity = Expense::class, parentColumns = ["Id"], childColumns = ["ExpenseId"]),
            ForeignKey(entity = Account::class, parentColumns = ["Id"], childColumns = ["AccountId"])
        ],
        indices = [
            Index("ExpenseId"),
            Index("AccountId")
        ])
class ExpenseDetail : BaseEntity, Parcelable {
    override fun getReadableName(): String? {
        return null
    }
    @ColumnInfo(name = "ExpenseId")
    var expenseId: Long? = null
    @ColumnInfo(name = "AccountId")
    var accountId : Long? = null
    @ColumnInfo(name = "Value")
    var value : Double = 0.0

    constructor(parcel: Parcel) : super(parcel) {
        expenseId = parcel.readValue(Long::class.java.classLoader) as? Long
        accountId = parcel.readValue(Long::class.java.classLoader) as? Long
        value = parcel.readDouble()
    }

    constructor()

    @Ignore
    constructor(expenseId: Long, accountId: Long, value: Double) {
        this.expenseId = expenseId
        this.accountId = accountId
        this.value = value
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeValue(expenseId)
        parcel.writeValue(accountId)
        parcel.writeDouble(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseDetail> {
        override fun createFromParcel(parcel: Parcel): ExpenseDetail {
            return ExpenseDetail(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseDetail?> {
            return arrayOfNulls(size)
        }

        fun populateData(): Array<ExpenseDetail> {

            return arrayOf(
                    ExpenseDetail(1, 1, 54.5),
                    ExpenseDetail(1, 2, 10.0),
                    ExpenseDetail(1, 3, 11.5),
                    ExpenseDetail(2, 1, 24.5),
                    ExpenseDetail(2, 2, 12.5),
                    ExpenseDetail(3, 1, 46.0),
                    ExpenseDetail(4, 1, 324.5),
                    ExpenseDetail(5, 1, 324.5),
                    ExpenseDetail(6, 1, 324.5),
                    ExpenseDetail(7, 1, 324.5),
                    ExpenseDetail(8, 1, 324.5),
                    ExpenseDetail(9, 1, 324.5),
                    ExpenseDetail(10, 1, 324.5),
                    ExpenseDetail(11, 1, 324.5),
                    ExpenseDetail(12, 1, 324.5),
                    ExpenseDetail(13, 1, 324.5),
                    ExpenseDetail(14, 1, 324.5),
                    ExpenseDetail(15, 1, 324.5),
                    ExpenseDetail(16, 1, 324.5),
                    ExpenseDetail(17, 1, 324.5),
                    ExpenseDetail(18, 1, 324.5),
                    ExpenseDetail(19, 1, 324.5),
                    ExpenseDetail(20, 1, 324.5),
                    ExpenseDetail(21, 1, 324.5),
                    ExpenseDetail(22, 1, 324.5),
                    ExpenseDetail(23, 1, 324.5),
                    ExpenseDetail(24, 1, 324.5),
                    ExpenseDetail(25, 1, 324.5),
                    ExpenseDetail(26, 1, 324.5)
            )
        }
    }
}