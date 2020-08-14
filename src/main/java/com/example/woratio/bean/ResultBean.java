package com.example.woratio.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * @filename ResultBean
 * @description
 * @author Felix
 * @date 2020/1/14 13:37
 */
public class ResultBean implements Serializable {
	private static final long serialVersionUID = -4509914679787320916L;
	private Map<String,OrderParamBean> orderParamBean;
	private Double diffierence;

	public Map<String, OrderParamBean> getOrderParamBean() {
		return orderParamBean;
	}

	public void setOrderParamBean(Map<String, OrderParamBean> orderParamBean) {
		this.orderParamBean = orderParamBean;
	}

	public Double getDiffierence() {
		return diffierence;
	}

	public void setDiffierence(Double diffierence) {
		this.diffierence = diffierence;
	}
}
