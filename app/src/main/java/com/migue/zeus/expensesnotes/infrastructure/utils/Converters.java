package com.migue.zeus.expensesnotes.infrastructure.utils;


import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static Date parseDate(String date) {
        try{
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date);
        }catch(ParseException e){
            return null;
        }
    }
}
