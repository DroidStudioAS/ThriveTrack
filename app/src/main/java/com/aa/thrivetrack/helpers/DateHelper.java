package com.aa.thrivetrack.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static String buildTodaysDate(){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //Log.i("formated date", String.valueOf(date));
        return date;
    }
}
