package com.hbj.learning.other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hbj
 * @date 2020/1/16 14:00
 */
public class DateFormatBug {
    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse("2020-1-1 13:12:12");
        System.out.println(date);
        String dateStr = simpleDateFormat.format(date);
        System.out.println(dateStr);
    }
}
