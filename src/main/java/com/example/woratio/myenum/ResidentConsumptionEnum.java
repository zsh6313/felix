package com.example.woratio.myenum;

/**
 * @filename ResidentConsumptionEnum
 * @description 价格指数-居民消费指数枚举
 * @author Felix
 * @date 2020/8/18 16:59
 */
public enum  ResidentConsumptionEnum {
	R0("医疗保健类居民消费价格指数"),

	R1("交通和通信类居民消费价格指数"),

	R2("居住类居民消费价格指数"),

	R3("居民消费价格指数"),

	R4("畜肉类居民消费价格指数");

	public static final String DESCRIBT = "居民消费指数";

	private String value;

	ResidentConsumptionEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
