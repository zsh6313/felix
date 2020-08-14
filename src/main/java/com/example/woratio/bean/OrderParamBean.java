package com.example.woratio.bean;

/**
 * @filename OrderParamBean
 * @description
 * @author Felix
 * @date 2020/1/13 11:31
 */
public class OrderParamBean {
	private int unionValue;
	private int firstParam = 0;
	private int secondParam = 0;
	private int thirdParam = 0;

	public OrderParamBean() {
	}
	public OrderParamBean(boolean random){
		if (random) {
			setUnionValue((int) (Math.random() * 48 + 16), -1);
		}
	}

	public OrderParamBean(int firstParam, int secondParam, int thirdParam) {
		this.firstParam = firstParam;
		this.secondParam = secondParam;
		this.thirdParam = thirdParam;
	}

	public OrderParamBean(int value) {
		setUnionValue((int) (value), -1);
	}

	public OrderParamBean(OrderParamBean bean) {
		this.unionValue = bean.getUnionValue();
		this.firstParam = bean.getFirstParam();
		this.secondParam = bean.getSecondParam();
		this.thirdParam = bean.getThirdParam();
	}

	public int getFirstParam() {
		return firstParam;
	}

	public void setFirstParam(int firstParam) {
		this.firstParam = firstParam;
	}

	public int getSecondParam() {
		return secondParam;
	}

	public void setSecondParam(int secondParam) {
		this.secondParam = secondParam;
	}

	public int getThirdParam() {
		return thirdParam;
	}

	public void setThirdParam(int thirdParam) {
		this.thirdParam = thirdParam;
	}

	public int getUnionValue() {
		return firstParam*16 + secondParam*4 + thirdParam;
	}

	public void setUnionValue(int unionValue, int lockFirstParam) {
		if (unionValue < 0 ) {
			if (lockFirstParam == -1) {
				this.unionValue = 1*16;
			} else {
				this.unionValue = lockFirstParam*16;
			}
		} else {
			this.unionValue = unionValue;
		}
		if (lockFirstParam != -1) {
			firstParam = lockFirstParam;
		} else {
			firstParam = this.unionValue/16;
		}
		if (firstParam == 0) {
			firstParam = 1;
		}
		secondParam = this.unionValue%16/4;
//		if (secondParam == 0) {
//			secondParam = 1;
//		}
		thirdParam = this.unionValue%16%4;
//		if (thirdParam == 0) {
//			thirdParam = 1;
//		}
	}
	public String toString() {
		return "(" + firstParam + "," + secondParam + "," + thirdParam + ")";
	}
}
