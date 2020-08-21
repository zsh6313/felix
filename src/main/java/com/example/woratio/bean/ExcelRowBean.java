package com.example.woratio.bean;

import java.util.Map;

/**
 * @filename ExcelRowBean
 * @description 宏观数据行的bean
 * @author Felix
 * @date 2020/8/19 17:47
 */
public class ExcelRowBean {
	/** 数据名称 */
	private String name;

	/** 数据信息 */
	private Map<MonthBean,Object> dataMap;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<MonthBean, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<MonthBean, Object> dataMap) {
		this.dataMap = dataMap;
	}
}
