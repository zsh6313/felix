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
        if (dateStr.contains("-")) {
            if (dateStr.indexOf("-") == 2) {
				String[] split = dateStr.split("-");
				monthBean.setYear(Integer.valueOf(split[2]));
				monthBean.setMouth(chineseNumber2Int(split[2].substring(0,split[2].length())));
			} else if (dateStr.indexOf("-") == 4) {
                String[] split = dateStr.split("-");
                monthBean.setYear(Integer.valueOf(split[0]));
                monthBean.setMouth(Integer.valueOf(split[1]));
            }
        } else  if (dateStr.contains("年") && dateStr.contains("月")){
            int yearIndex = dateStr.indexOf("年");
            monthBean.setYear(Integer.valueOf(dateStr.substring(0, yearIndex)));
            int monthIndex = dateStr.indexOf("月");
            monthBean.setMouth(Integer.valueOf(dateStr.substring(yearIndex+1, monthIndex)));
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

	private static int chineseNumber2Int(String chineseNumber){
		int result = 0;
		int temp = 1;//存放一个单位的数字如：十万
		int count = 0;//判断是否有chArr
		char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
		char[] chArr = new char[]{'十','百','千','万','亿'};
		for (int i = 0; i < chineseNumber.length(); i++) {
			boolean b = true;//判断是否是chArr
			char c = chineseNumber.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
				if (c == cnArr[j]) {
					if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
						result += temp;
						temp = 1;
						count = 0;
					}
					// 下标+1，就是对应的值
					temp = j + 1;
					b = false;
					break;
				}
			}
			if(b){//单位{'十','百','千','万','亿'}
				for (int j = 0; j < chArr.length; j++) {
					if (c == chArr[j]) {
						switch (j) {
						case 0:
							temp *= 10;
							break;
						case 1:
							temp *= 100;
							break;
						case 2:
							temp *= 1000;
							break;
						case 3:
							temp *= 10000;
							break;
						case 4:
							temp *= 100000000;
							break;
						default:
							break;
						}
						count++;
					}
				}
			}
			if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
				result += temp;
			}
		}
		return result;
	}
}
