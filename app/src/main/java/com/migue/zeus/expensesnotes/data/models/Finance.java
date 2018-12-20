package com.migue.zeus.expensesnotes.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.migue.zeus.expensesnotes.R;

@Entity(tableName = "Finances")
public class Finance {
    public static final int iconId = R.drawable.app_logo;
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private double Value;
    private int IsActive;
    private String Name;

    public Finance(String name, double value, int isActive) {
        this.Value = value;
        this.IsActive = isActive;
        this.Name = name;
    }

    public Finance() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        this.Value = value;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        this.IsActive = isActive;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public static Finance[] populateData() {
        return new Finance[]{
                new Finance("Caja", 3200, 1),
                new Finance("Bancos", 2700, 1),
                new Finance("Deuda Tia", 2500, 1),
                new Finance("Deuda Mi Pa", 1000, 1),
                new Finance("Acreedores", 1222, -1),
                new Finance("Cuentas por pagar", 1234, -1),
                new Finance("Algo de Amazon", 954, -1)
        };
    }
}
