package com.migue.zeus.expensesnotes.data.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.migue.zeus.expensesnotes.R;

@Entity(tableName = "Icons")
public class Icon {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String Name;
    private String path;

    public Icon() {
    }

    public Icon(String name, String path) {
        Name = name;
        this.path = path;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Icon[] populateData(final Context context) {
        String[] iconNames = context.getResources().getStringArray(R.array.icon_names);
        String[] iconPaths = context.getResources().getStringArray(R.array.icon_paths);
        Icon[] icons = new Icon[iconNames.length];
        for (int i = 0; i < iconNames.length; i++){
            icons[i] = new Icon(iconNames[i], iconPaths[i]);
        }
        return icons;
    }
}
