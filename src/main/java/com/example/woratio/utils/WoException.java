package com.example.woratio.utils;

import java.io.Serializable;

/**
 * @filename WoException
 * @description 统一异常处理常量类
 * @author 王承
 * @date 2019/5/21 17:16
 */
public class WoException implements Serializable {
	private static final long serialVersionUID = 7600370594395821989L;

	/**
	 * 私有构造方法
	 */
	private WoException() {
	}

	/**系统异常*/
	public static final String ERR_100000 = "100000";
}
