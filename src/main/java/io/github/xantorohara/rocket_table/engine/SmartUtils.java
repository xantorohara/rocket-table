package io.github.xantorohara.rocket_table.engine;

import lombok.Synchronized;
import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class SmartUtils {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

    @Synchronized
    public static String toString(Date value) {
        long time = value.getTime();
        if (time % (24 * 60 * 60 * 1000) == 0) {
            return DATE_FORMAT.format(value);
        } else if (time % 1000 == 0) {
            return DATETIME_FORMAT.format(value);
        } else {
            return TIMESTAMP_FORMAT.format(value);
        }
    }
}
