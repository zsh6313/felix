package com.example.woratio.bean;

/**
 * @filename MonthBean
 * @description
 * @author Felix
 * @date 2020/8/19 17:51
 */
public class MonthBean {
	private int year;
	private int mouth;

	public MonthBean() {
	}

	public MonthBean(int year, int mouth) {
		this.year = year;
		this.mouth = mouth;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMouth() {
		return mouth;
	}

	public void setMouth(int mouth) {
		this.mouth = mouth;
	}

	public MonthBean subMonth(int subNumber) {
		MonthBean monthBean = new MonthBean();
		if (mouth - subNumber > 0) {
			monthBean.setMouth(mouth - subNumber);
			monthBean.setYear(year);
		} else {
			if (subNumber > 12) {
				int i = subNumber % 12;
				monthBean.setMouth(mouth - i);
				monthBean.setYear(year - subNumber /12);
			} else {
				monthBean.setYear(year - 1);
				monthBean.setMouth(12 - subNumber + mouth);
			}
		}
		return monthBean;
	}

	public MonthBean copy() {
		return new MonthBean(this.year, this.mouth);
	}
}
