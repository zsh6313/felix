package com.example.woratio.myenum;

/**
 * @filename ResidentConsumptionEnum
 * @description 采购经理指数-制造业枚举
 * @author Felix
 * @date 2020/8/18 16:59
 */
public enum ManufactureEnum {
	/** 制造业采购经理指数 */
	M0("制造业采购经理指数"),

	/** 新订单指数 */
	M1("新订单指数"),

	/** 产成品库存指数 */
	M2("产成品库存指数"),

	/** 进口指数 */
	M3("进口指数"),

	/** 出厂价格指数 */
	M4("出厂价格指数"),

	/** 主要原材料购进价格指数 */
	M5("主要原材料购进价格指数"),

	/** 从业人员指数 */
	M6("制造业-从业人员指数"),

	/** 供应商配送时间指数 */
	M7("制造业-供应商配送时间指数"),

	/** 生产经营活动预期指数 */
	M8("生产经营活动预期指数");

	public static final String DESCRIBT = "制造业指数";

	private String value;

	ManufactureEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
