package com.example.woratio.bean;

import java.util.List;
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
	private List<Map<MonthBean,Object>> dataList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<MonthBean, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<MonthBean, Object>> dataList) {
		this.dataList = dataList;
	}
}
