package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class ExpenseWithDetails {
    @Embedded
    var expense : Expense? = null
    @Relation(entity = ExpenseDetail::class, parentColumn = "Id", entityColumn = "ExpenseId")
    var expenseDetails : List<ExpenseDetail>? = null

    fun getTotal() : Double {
        var total : Double = 0.0
        for (expenseDetail in expenseDetails!!) {
            total += expenseDetail.value
        }
        return total
    }
}