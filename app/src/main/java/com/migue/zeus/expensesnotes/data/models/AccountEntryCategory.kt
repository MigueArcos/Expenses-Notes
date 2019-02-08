package com.migue.zeus.expensesnotes.data.models

import android.arch.persistence.room.*
import android.content.Context
import android.os.Parcel
import android.os.Parcelable

import com.migue.zeus.expensesnotes.R

@Entity(
        tableName = "AccountEntriesCategories",
        foreignKeys = [ForeignKey(entity = Icon::class, parentColumns = ["Id"], childColumns = ["IconId"])],
        indices = [Index("IconId")])
class AccountEntryCategory : BaseEntity {
    override fun getReadableName(): String? {
        return name
    }
    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "IconId")
    var iconId: Long? = null
    @ColumnInfo(name = "IsRevenue")
    var isRevenue: Boolean? = false

    constructor(parcel: Parcel) : super(parcel) {
        name = parcel.readString()
        iconId = parcel.readValue(Long::class.java.classLoader) as? Long
        isRevenue = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
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

    companion object CREATOR : Parcelable.Creator<AccountEntryCategory> {
        override fun createFromParcel(parcel: Parcel): AccountEntryCategory {
            return AccountEntryCategory(parcel)
        }

        override fun newArray(size: Int): Array<AccountEntryCategory?> {
            return arrayOfNulls(size)
        }

        fun populateData(context: Context): Array<AccountEntryCategory?> {
            val rawCategories = context.resources.getStringArray(R.array.account_entries_categories)
            val categories = arrayOfNulls<AccountEntryCategory>(rawCategories.size)
            //Type type = new TypeToken<Map<String, String>>(){}.getType();
            //Map<String, String> myMap = gson.fromJson("{'k1':'apple','k2':'orange'}", type);
            for ((i, rawCategory) in rawCategories.withIndex()) {
                categories[i] = AccountEntryCategory(rawCategory, 1)
            }
            return categories
        }
    }

}

