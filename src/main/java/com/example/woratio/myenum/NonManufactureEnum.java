package com.example.woratio.myenum;

/**
 * @filename ResidentConsumptionEnum
 * @description 采购经理指数-非制造业枚举
 * @author Felix
 * @date 2020/8/18 16:59
 */
public enum NonManufactureEnum {
	/** 销售价格指数 */
	N0("销售价格指数"),

	/** 从业人员指数 */
	N1("非制造业-从业人员指数"),

	/** 供应商配送时间指数 */
	N2("非制造业-供应商配送时间指数");


	public static final String DESCRIBT = "非制造业指数";

	private String value;

	NonManufactureEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
