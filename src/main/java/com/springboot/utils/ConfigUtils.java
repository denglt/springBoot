/**
 *
 */
package com.springboot.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ConfigUtils {

    private static DateFormat dateFormat;

    public static DateFormat getDateFormat() {
        if (dateFormat == null) {
            synchronized (ConfigUtils.class) {
                if (dateFormat == null) {
                    String fmt = getDatePattern();
                    dateFormat = new SimpleDateFormat(fmt);
                    dateFormat.setLenient(false);
                }
            }

        }
        return dateFormat;
    }

    public static String getDatePattern() {
        return "yyyy-MM-dd HH:mm:ss";
    }


}
