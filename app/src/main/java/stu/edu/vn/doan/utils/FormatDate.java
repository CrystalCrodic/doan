package stu.edu.vn.doan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    static SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd/M/yyyy hh:mm aa");
    static SimpleDateFormat sdfDate = new SimpleDateFormat("dd/M/yyyy");
    static SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aa");

    public static String formatDateTime(Date date) {
        return sdfDateTime.format(date);
    }

    public static String formatDate(Date date) {
        return sdfDate.format(date);
    }

    public static String formatTime(Date date) {
        return sdfTime.format(date);
    }
}
