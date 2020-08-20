package com.example.woratio.myenum;

/**
 * @filename WoratioInitEnum
 * @description 坏账初始数据枚举
 * @author Felix
 * @date 2020/8/20 15:28
 */
public enum WoratioInitEnum {
	W0("总账户数"),

	W1("平均贷款额度"),

	W2("平均期数"),

	W3("客户平均年龄"),

	W4("客户平均工作年限"),

	W5("客户平均工资"),

	W6("客户平均居住年限"),

	W7("生意：受薪"),

	W8("有楼：无楼"),

	W9("本地户口：非本地户口"),

	W10("男：女"),

	W11("已婚：单身"),

	W12("大专及以上：大专以下");

	public static final String DESCRIBT = "坏账初始数据";

	private String value;

	WoratioInitEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
