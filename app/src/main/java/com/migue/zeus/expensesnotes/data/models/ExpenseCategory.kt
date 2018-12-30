package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.*
import android.content.Context

import com.migue.zeus.expensesnotes.R

@Entity(
        tableName = "ExpensesCategories",
        foreignKeys = [ForeignKey(entity = Icon::class, parentColumns = ["Id"], childColumns = ["IconId"])],
        indices = [Index("IconId")])
class ExpenseCategory : BaseEntity {

    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "IconId")
    var iconId: Int? = null

    constructor()

    @Ignore
    constructor(name: String, iconId: Int) {
        this.name = name
        this.iconId = iconId
    }
    @Ignore
    constructor(name: String) {
        this.name = name
    }

    companion object {

        fun populateData(context: Context): Array<ExpenseCategory?> {
            val rawCategories = context.resources.getStringArray(R.array.expenses_categories)
            val categories = arrayOfNulls<ExpenseCategory>(rawCategories.size)
            var i = 0
            //Type type = new TypeToken<Map<String, String>>(){}.getType();
            //Map<String, String> myMap = gson.fromJson("{'k1':'apple','k2':'orange'}", type);
            for (rawCategory in rawCategories) {
                categories[i] = ExpenseCategory(rawCategory)
                i++
            }
            return categories
        }
    }
}

