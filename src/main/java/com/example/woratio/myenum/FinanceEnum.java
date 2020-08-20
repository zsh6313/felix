package com.example.woratio.myenum;

/**
 * @filename ResidentConsumptionEnum
 * @description 对外经济/金融-进出口总值/货币供应量枚举
 * @author Felix
 * @date 2020/8/18 16:59
 */
public enum FinanceEnum {
	/** 进出口总值累计值 */
	F0("进出口总值累计值"),

	/** 出口总值累计值 */
	F1("出口总值累计值"),

	/** 进出口差额累计值 */
	F2("进出口差额累计值"),

	/** 货币和准货币(M2)供应量期末值 */
	F3("货币和准货币(M2)供应量期末值"),

	/** 货币(M1)供应量期末值 */
	F4("货币(M1)供应量期末值");

	public static final String DESCRIBT = "金融类指数";

	private String value;

	FinanceEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
