package com.example.woratio.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.woratio.bean.MonthBean;

public class DateUtils {
    /**
     * 功能描述 年月获取 支持excel表格天数、2020-08格式、2020年8月格式
     * @param dateStr
     * @return com.example.woratio.bean.MonthBean
     * @author Felix
     * @date 2020/8/20 14:34
     */
    public static MonthBean getMouthBean(String dateStr) {
        MonthBean monthBean = new MonthBean();
        if (dateStr.startsWith("19")
                || dateStr.startsWith("20")
                || dateStr.startsWith("21")
                || dateStr.startsWith("22")
                || dateStr.startsWith("23")) {
            if (dateStr.contains("-")) {
                String[] split = dateStr.split("-");
                monthBean.setYear(Integer.valueOf(split[0]));
                monthBean.setMouth(Integer.valueOf(split[1]));
            } else {
                int yearIndex = dateStr.indexOf("年");
                monthBean.setYear(Integer.valueOf(dateStr.substring(0, yearIndex)));
                int monthIndex = dateStr.indexOf("月");
                monthBean.setMouth(Integer.valueOf(dateStr.substring(yearIndex+1, monthIndex)));
            }
        } else {
            int daysFrom1900 = Double.valueOf(dateStr).intValue();
            Calendar c = new GregorianCalendar(1900,0,-1);
            c.add(Calendar.DAY_OF_MONTH,daysFrom1900);  //42770是距离1900年1月1日的天数
            monthBean.setYear(c.get(Calendar.YEAR));
            monthBean.setMouth(c.get(Calendar.MONTH) + 1);
        }
        return monthBean;
    }

    public static int getMonthDifference(MonthBean latestMonth, MonthBean earlyMonth) {
        return (latestMonth.getYear() - earlyMonth.getYear())*12 + latestMonth.getMouth() - earlyMonth.getMouth();
    }
}
