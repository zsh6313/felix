package com.example.woratio.myenum;

/**
 * @filename ResidentConsumptionEnum
 * @description 价格指数-工业生产者购进枚举
 * @author Felix
 * @date 2020/8/18 16:59
 */
public enum ProducerPurchaseEnum {
	/** 燃料、动力类购进价格指数 */
	P0("燃料、动力类购进价格指数"),

	/** 黑色金属材料类购进价格指数 */
	P1("黑色金属材料类购进价格指数"),

	/** 有色金属材料和电线类购进价格指数 */
	P2("有色金属材料和电线类购进价格指数"),

	/** 化工原料类购进价格指数 */
	P3("化工原料类购进价格指数"),

	/** 其它工业原材料及半成品类购进价格指数 */
	P4("其它工业原材料及半成品类购进价格指数"),

	/** 纺织原料类购进价格指数 */
	P5("纺织原料类购进价格指数"),

	/** 工业生产者出厂价格指数 */
	P6("工业生产者出厂价格指数");

	public static final String DESCRIBT = "工业生产者指数";


	private String value;

	ProducerPurchaseEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
