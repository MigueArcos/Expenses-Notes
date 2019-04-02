package com.migue.zeus.expensesnotes.infrastructure.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyUtils {
    public static final String GLOBAL_LOG_TAG = "Log Tag";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy\' a las \'hh:mm a";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String formatCurrency(double number){
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        int decimalPart = (int) ((number - (int) number) * 100);
        if (decimalPart == 0) currencyFormatter.setMinimumFractionDigits(0);
        return currencyFormatter.format(number);
    }

    public static String formatDate(Date date) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return formatter.format(date);
    }

    public static void fromStringDate(String date, Calendar calendar){
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static Date toDate(String date){
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String formatCurrentDate(String dateFormatId) {
        DateFormat formatter = new SimpleDateFormat(dateFormatId, Locale.US);
        return formatter.format(new Date());
    }

    public static String getTime12HoursFormat(String time24Hrs) {
        int hour = Integer.parseInt(time24Hrs.substring(0, 2));
        boolean isPm = hour > 11;
        return (isPm ? String.valueOf((hour + 11) % 12 + 1) : time24Hrs).concat(isPm ? "p.m" : "a.m");
    }

    public static String getTime12HoursFormat(long timeMillis, String format) {
        Date date = new Date(timeMillis);
        return new SimpleDateFormat(format, Locale.US).format(date);
    }

    public static String getTime12HoursFormat(long timeMillis) {
        Date date = new Date(timeMillis);
        return new SimpleDateFormat(DATE_TIME_FORMAT, Locale.US).format(date);
    }

    public static void changeStatusBarColor(Activity activity, int colorResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(colorResId));
        }
    }
}
