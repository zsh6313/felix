package com.example.woratio.exception;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @filename WoServiceException
 * @description 统一异常处理常量类
 * @author cailu
 * @date 2019/8/8 11:19
 */
public class WoServiceException extends RuntimeException {

	private static final long serialVersionUID = 852443720359797737L;

	/**错误信息集*/
	private static Map<String, String> errMsgMap = new HashMap<String, String>();

	/**错误代码*/
	private String code;

	/***
	 * 静态块
	 */
	static {
		errMsgMap.put(WoException.ERR_100000, "系统异常");
	}

	/**
	 * 服务自定义异常构造方法
	 * @param code 异常代码
	 * @author 王承
	 * @date 2019/4/17 13:42
	 */
	public WoServiceException(String code) {
		super(errMsgMap.get(code) == null ? ("未知代码:" + code) : errMsgMap.get(code));
		this.code = code;
	}

	/**
	 * 服务自定义异常构造方法
	 * @param code 异常代码
	 * @param message 异常信息
	 * @author 王承
	 * @date 2019/4/17 13:42
	 */
	public WoServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * 服务自定义异常构造方法
	 * @param code 异常代码
	 * @param args 变量
	 * @return
	 * @author 王承
	 * @date 2019/4/17 13:43
	 */
	public WoServiceException(String code, Object[] args) {
		super(errMsgMap.get(code) == null ? ("未知代码:" + code) : (MessageFormat.format(errMsgMap.get(code), args)));
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}