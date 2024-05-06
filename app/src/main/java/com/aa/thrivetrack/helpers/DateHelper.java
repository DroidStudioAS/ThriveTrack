package com.aa.thrivetrack.helpers;

import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DateHelper {
    public static String buildTodaysDate(){
       return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static ArrayList<String> datesInRange(String startDateString, String endDateString){
        ArrayList<String> datesInRange = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        datesInRange.add(String.valueOf(startDate));

        LocalDate currentDate = startDate.plusDays(1);
        while (currentDate.isBefore(endDate)){
            datesInRange.add(String.valueOf(currentDate));
            currentDate = currentDate.plusDays(1);
        }

        datesInRange.add(String.valueOf(endDate));

        for(String x : datesInRange){
            Log.i("date", x);
        }

        return datesInRange;
    }
}
