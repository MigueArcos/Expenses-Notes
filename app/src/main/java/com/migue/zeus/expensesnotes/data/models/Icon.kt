package com.migue.zeus.expensesnotes.data.models


import android.arch.persistence.room.*
import android.content.Context

import com.migue.zeus.expensesnotes.R

@Entity(tableName = "Icons")
class Icon {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Long? = null
    @ColumnInfo(name = "Name")
    var name: String? = null
    @ColumnInfo(name = "Path")
    var path: String? = null

    constructor()
    @Ignore
    constructor(name: String, path: String) {
        this.name = name
        this.path = path
    }

    companion object {

        fun populateData(context: Context): Array<Icon?> {
            val iconNames = context.resources.getStringArray(R.array.icon_names)
            val iconPaths = context.resources.getStringArray(R.array.icon_paths)
            val icons = arrayOfNulls<Icon>(iconNames.size)
            for (i in iconNames.indices) {
                icons[i] = Icon(iconNames[i], iconPaths[i])
            }
            return icons
        }
    }
}
