package com.migue.zeus.expensesnotes.data.models


import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters.parseDate

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Entity(tableName = "Expenses",
        foreignKeys = [ForeignKey(entity = ExpenseCategory::class, parentColumns = ["Id"], childColumns = ["ExpenseCategoryId"])],
        indices = [Index("ExpenseCategoryId")])
class Expense : BaseEntity {
    override fun getReadableName(): String? {
        return name
    }
    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "ExpenseCategoryId")
    var expenseCategoryId: Long? = null
    @ColumnInfo(name = "Date")
    var date: Date? = Date()

    constructor(parcel: Parcel) : super(parcel) {
        name = parcel.readString()
        expenseCategoryId = parcel.readValue(Long::class.java.classLoader) as? Long
        date = parcel.readValue(Date::class.java.classLoader) as? Date
    }

    constructor()

    @Ignore
    constructor(name: String, date: Date?, expenseCategoryId: Long) {
        this.expenseCategoryId = expenseCategoryId
        this.name = name
        this.date = date
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(name)
        parcel.writeValue(expenseCategoryId)
        parcel.writeValue(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }

        fun populateData(): Array<Expense> {

            return arrayOf(
                    Expense("Expense a", parseDate("2018-12-01"), 1),
                    Expense("Expense b", parseDate("2018-12-01"), 2),
                    Expense("Expense c", parseDate("2018-12-05"), 3),
                    Expense("Expense d", parseDate("2018-12-07"), 4),
                    Expense("Expense e", parseDate("2018-12-07"), 1),
                    Expense("Expense f", parseDate("2018-12-10"), 1),
                    Expense("Expense g", parseDate("2018-12-10"), 1),
                    Expense("Expense h", parseDate("2018-12-10"), 1),
                    Expense("Expense i", parseDate("2018-12-10"), 1),
                    Expense("Expense j", parseDate("2018-12-10"), 1),
                    Expense("Expense k", parseDate("2018-12-12"), 1),
                    Expense("Expense l", parseDate("2018-12-12"), 1),
                    Expense("Expense m", parseDate("2018-12-15"), 1),
                    Expense("Expense n", parseDate("2018-12-15"), 1),
                    Expense("Expense o", parseDate("2018-12-16"), 1),
                    Expense("Expense p", parseDate("2018-12-16"), 1),
                    Expense("Expense q", parseDate("2018-12-17"), 1),
                    Expense("Expense r", parseDate("2018-12-18"), 1),
                    Expense("Expense s", parseDate("2018-12-18"), 1),
                    Expense("Expense t", parseDate("2018-12-18"), 1),
                    Expense("Expense u", parseDate("2018-12-18"), 1),
                    Expense("Expense v", parseDate("2018-12-18"), 1),
                    Expense("Expense w", parseDate("2018-12-19"), 1),
                    Expense("Expense x", parseDate("2018-12-22"), 1),
                    Expense("Expense y", parseDate("2018-12-26"), 1),
                    Expense("Expense z", parseDate("2018-12-29"), 1)
            )
        }
    }

}