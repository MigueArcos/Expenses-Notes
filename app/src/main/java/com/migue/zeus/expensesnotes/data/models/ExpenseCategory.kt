package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.*
import android.content.Context
import android.os.Parcel
import android.os.Parcelable

import com.migue.zeus.expensesnotes.R

@Entity(
        tableName = "ExpensesCategories",
        foreignKeys = [ForeignKey(entity = Icon::class, parentColumns = ["Id"], childColumns = ["IconId"])],
        indices = [Index("IconId")])
class ExpenseCategory : BaseEntity {
    override fun getReadableName(): String? {
        return name
    }
    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "IconId")
    var iconId: Long? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        iconId = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    constructor()

    @Ignore
    constructor(name: String, iconId: Long) {
        this.name = name
        this.iconId = iconId
    }
    @Ignore
    constructor(name: String) {
        this.name = name
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(name)
        parcel.writeValue(iconId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseCategory> {
        override fun createFromParcel(parcel: Parcel): ExpenseCategory {
            return ExpenseCategory(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseCategory?> {
            return arrayOfNulls(size)
        }

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

