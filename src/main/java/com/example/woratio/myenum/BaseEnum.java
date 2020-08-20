package com.example.woratio.myenum;

import java.util.Map;

/**
 * @filename BaseEnum
 * @description 宏观数据基础枚举
 * @author Felix
 * @date 2020/8/19 17:55
 */
public enum BaseEnum {
	V1("制造业指数"),
	V2("金融类指数"),
	V3("居民消费指数"),
	V4("工业生产者指数");

	private String value;

	BaseEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public Map<String, Class> getEnum(String EnumValue) {
		return null;
	}
}
