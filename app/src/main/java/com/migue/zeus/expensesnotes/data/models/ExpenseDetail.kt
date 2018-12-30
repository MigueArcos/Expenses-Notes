package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.*

@Entity(tableName = "ExpensesDetails",
        foreignKeys = [
            ForeignKey(entity = Expense::class, parentColumns = ["Id"], childColumns = ["ExpenseId"]),
            ForeignKey(entity = Account::class, parentColumns = ["Id"], childColumns = ["AccountId"])
        ],
        indices = [
            Index("ExpenseId"),
            Index("AccountId")
        ])
class ExpenseDetail : BaseEntity {
    @ColumnInfo(name = "ExpenseId")
    var expenseId: Int? = null
    @ColumnInfo(name = "AccountId")
    var accountId : Int? = null
    @ColumnInfo(name = "Value")
    var value : Double = 0.0
    
    constructor()

    @Ignore
    constructor(expenseId: Int, accountId: Int, value: Double) {
        this.expenseId = expenseId
        this.accountId = accountId
        this.value = value
    }

    companion object {

        fun populateData(): Array<ExpenseDetail> {

            return arrayOf(
                    ExpenseDetail(1, 1, 324.5),
                    ExpenseDetail(2, 1, 324.5),
                    ExpenseDetail(3, 1, 324.5),
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