package com.migue.zeus.expensesnotes.data.models


import android.arch.persistence.room.*
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters.parseDate

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Entity(tableName = "Expenses",
        foreignKeys = [ForeignKey(entity = ExpenseCategory::class, parentColumns = ["Id"], childColumns = ["ExpenseCategoryId"])],
        indices = [Index("ExpenseCategoryId")])
class Expense : BaseEntity {
    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "ExpenseCategoryId")
    var expenseCategoryId: Int? = null
    @ColumnInfo(name = "Date")
    var date: Date? = null

    constructor()

    @Ignore
    constructor(name: String, date: Date?, expenseCategoryId: Int) {
        this.expenseCategoryId = expenseCategoryId
        this.name = name
        this.date = date
    }

    companion object {

        fun populateData(): Array<Expense> {

            return arrayOf(
                    Expense("Expense a", parseDate("2018-12-01"), 1),
                    Expense("Expense b", parseDate("2018-12-01"), 1),
                    Expense("Expense c", parseDate("2018-12-05"), 1),
                    Expense("Expense d", parseDate("2018-12-07"), 1),
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