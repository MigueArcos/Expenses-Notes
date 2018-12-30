package com.migue.zeus.expensesnotes.data.models


import android.arch.persistence.room.*
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters.parseDate

import java.util.Date


@Entity(
        tableName = "Accounts",
        foreignKeys = [ForeignKey(entity = Icon::class, parentColumns = ["Id"], childColumns = ["IconId"])],
        indices = [Index("IconId")])
class Account : BaseEntity {
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
    var iconId: Int? = null

    constructor()
    
    @Ignore
    constructor(name: String?, value: Double?, activeAccount: Int, iconId: Int?) : super() {
        this.name = name
        this.value = value
        this.activeAccount = activeAccount
        this.iconId = iconId
    }

    companion object {

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
