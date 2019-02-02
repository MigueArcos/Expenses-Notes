package com.migue.zeus.expensesnotes.data.models


import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters.parseDate

import java.util.Date


@Entity(
        tableName = "Accounts",
        foreignKeys = [ForeignKey(entity = Icon::class, parentColumns = ["Id"], childColumns = ["IconId"])],
        indices = [Index("IconId")])
class Account : BaseEntity {
    override fun getReadableName(): String? {
        return name
    }

    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "Value")
    var value: Double? = null
    @ColumnInfo(name = "CreationDate")
    var creationDate: Date? = Date()
    @ColumnInfo(name = "ModificationDate")
    var modificationDate: Date? = Date()
    @ColumnInfo(name = "ActiveAccount")
    var activeAccount: Int = 1
    @ColumnInfo(name = "IconId")
    var iconId: Long? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        value = parcel.readValue(Double::class.java.classLoader) as? Double
        activeAccount = parcel.readInt()
        iconId = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    constructor()
    
    @Ignore
    constructor(name: String?, value: Double?, activeAccount: Int, iconId: Long?) : super() {
        this.name = name
        this.value = value
        this.activeAccount = activeAccount
        this.iconId = iconId
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(name)
        parcel.writeValue(value)
        parcel.writeInt(activeAccount)
        parcel.writeValue(iconId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Account> {
        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }

        fun populateData(): Array<Account> {

            return arrayOf(
                    Account("Caja", 3200.0, 1, 1),
                    Account("Bancos", 2700.0, 1, 1),
                    Account("Deuda Tia", 2500.0, 1, 1),
                    Account("Deuda Mi Pa", 1000.0, 1, 1),
                    Account("Acreedores", 1222.0, -1, 1),
                    Account("Cuentas por pagar", 1234.0, -1, 1),
                    Account("Algo de Amazon", 954.0, -1, 1)
            )
        }
    }
}
