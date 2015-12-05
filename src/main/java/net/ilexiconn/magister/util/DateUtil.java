package net.ilexiconn.magister.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static Date stringToDate(String date) throws ParseException {
        if (date == null) {
            throw new ParseException("String parameter was null", 0);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSSSSSS");
        format.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        return format.parse(date.replace("T", "-").replace("Z", ""));
    }
}
