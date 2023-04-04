package me.bunnykick.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tools {

    public static String getDbString(String str) {
        return "'" + str + "'";
    }

    public static Date cloneDate(Date date) {
        if(date == null) {
            return null;
        }
        return new Date(date.getTime());
    }

    public static String getDBDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "'" + format.format(date) + "'";
    }

    public static String getDay(Date date) {
        DateFormat formatter = new SimpleDateFormat("EEEE", Locale.GERMAN);
        return formatter.format(date);
    }

    public static String getDateData(Date date) {
        SimpleDateFormat contentFormat = new SimpleDateFormat("dd HH:mm");
        return contentFormat.format(date);
    }

}
