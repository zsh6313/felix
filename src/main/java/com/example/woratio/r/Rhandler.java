package com.example.woratio.r;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

import com.example.woratio.bean.OrderParamBean;
import com.example.woratio.exception.WoException;
import com.example.woratio.exception.WoServiceException;

/**
 * @filename Rhandler
 * @description
 * @author Felix
 * @date 2020/1/10 10:34
 */
public class Rhandler {
	private static final String rFilePath = "C:\\Users\\WH02090\\Desktop\\坏账预测\\2020年7月\\代码\\ttf.Rmd";
	private LinkedHashMap<String, OrderParamBean> orderParamMap = new LinkedHashMap<>();
	private List<String> paramNameList = new ArrayList<>();
	private static List<OrderParamBean> orderFixedParam = new ArrayList<>();
	private int paramPosition;
	private Rengine re = null;
	private static TextConsole textConsole = new TextConsole();
	private boolean loadExcelCodePorcess = false;//第1-5部分代码是否执行
	List<Object> codeParagraphs = new ArrayList<>();
	Map<String, List<String>> codeParagraphMap = new LinkedHashMap<>();
	static {
		orderFixedParam.add(new OrderParamBean(1,1,3));
		orderFixedParam.add(new OrderParamBean(1,0,4));
		orderFixedParam.add(new OrderParamBean(1,2,4));
	}
	public REXP processRScript(Boolean isRandom) {
		//1.加载代码
		if (re == null) {
			re = new Rengine(null, false, textConsole);
		}
		REXP rexp = null;
		try {
			if (CollectionUtils.isEmpty(codeParagraphs)) {
				File rFile = new File(rFilePath);
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rFile), "UTF-8"));
				String s = null;
				boolean isAdd = false;
				String prefix = "```{com.example.woratio.r}";//每段代码前缀
				String suffix = "```";//每段代码后缀
				List<String> codeLines = new ArrayList<>();
				while ((s = br.readLine()) != null) {
					s = s.trim();
					if (s.length() == 0) {
						continue;
					}
					if (!isAdd) {
						if (s.equals(prefix)) {
							isAdd = true;
							continue;
						}
					} else {
						if (s.equals(suffix)) {
							isAdd = false;
							List<String> codeLines1 = new ArrayList<>(codeLines);
							codeParagraphs.add(codeLines1);
							codeLines.clear();
							continue;
						}
						if (!s.startsWith("##") ) {
							if (s.contains("order") && paramPosition == 0) {
								paramPosition = codeParagraphs.size();
							}
							codeLines.add(s);
						}
					}
				}
				//2.代码分段
				List<String> paramParagraph = (List<String>) codeParagraphs.get(paramPosition);
				//代码头拼接
				List<String> codeHeadList = new ArrayList<>();
				codeHeadList.add(paramParagraph.get(0));
				codeHeadList.add(paramParagraph.get(1));
				codeParagraphMap.put("codeHead", codeHeadList);
				//参数代码块拼接
				for (int i = 0; i < (paramParagraph.size() - 2) / 8; i++) {
					List<String> paramCodeList = new ArrayList<>();
					String mapKey = null;
					for (int j = 0; j < 8; j++) {
						String currentLine = paramParagraph.get(i * 8 + j + 2);
						if (j == 0) {
							mapKey = (currentLine.split(" "))[0];
							paramNameList.add(mapKey);
						}
						paramCodeList.add(paramParagraph.get(i * 8 + j + 2));
					}
					codeParagraphMap.put(mapKey, paramCodeList);
				}
			}
			//3.格式化
			for (Map.Entry<String, List<String>> entry : codeParagraphMap.entrySet()) {
				List<String> paramCodeList = entry.getValue();
				for (int i = 0; i < paramCodeList.size(); i++) {
					String codeLine = paramCodeList.get(i);
					if (paramCodeList.get(i).contains("differences")) {
						paramCodeList.set(i, codeLine.replaceAll("differences=[0-9]", "differences={" + 0 + "}"));
					}
					if (codeLine.contains("order")) {
						paramCodeList.set(i,
								codeLine.replaceAll("[0-9],[0-9],[0-9]", "{" + 0 + "},{" + 1 + "},{" + 2 + "}"));
					}
				}
			}
			//4.设置参数
			if (orderParamMap.isEmpty() || isRandom) {
				for (String paramName : paramNameList) {
					if (paramName.equals("贷款余额")) {
						OrderParamBean orderParamBean = new OrderParamBean();
						orderParamBean.setFirstParam(3);
						orderParamBean.setSecondParam(1);
						orderParamBean.setThirdParam(1);
						orderParamMap.put(paramName, orderParamBean);
					} else {
						OrderParamBean orderParamBean = new OrderParamBean(isRandom);
						orderParamMap.put(paramName, orderParamBean);
					}
				}
			}
//			orderParamMap.put("全国居民消费医疗保健类居民消费价格指数",new OrderParamBean(3,3,1));
//			int com.example.woratio.r = (int)(Math.random() * 2);
//			orderParamMap.put("非制造业从业人员指数",new OrderParamBean(3,1,1));
//			orderParamMap.put("工业生产者购进农副产品类购进价格指数",new OrderParamBean(2,0,3));
//			orderParamMap.put("非制造业供应商配送时间指数",new OrderParamBean(1,3,3));
//			orderParamMap.put("进出口总值进出口差额累计值千美元",new OrderParamBean(2,2,3));
			orderParamMap.put("贷款余额",new OrderParamBean(3,1,1));
//			if (com.example.woratio.r == 1) {
//				orderParamMap.put("非制造业从业人员指数",new OrderParamBean(1,0,4));
//				orderParamMap.put("制造业供应商配送时间指数",new OrderParamBean(3,2,2));
//			} else {
//				orderParamMap.put("非制造业从业人员指数",new OrderParamBean(1,1,3));
//			}
//			orderParamMap.put("客户平均年龄",new OrderParamBean(3,1,1));
			for (Map.Entry<String, List<String>> entry : codeParagraphMap.entrySet()) {
				OrderParamBean orderParamBean = orderParamMap.get(entry.getKey());
				List<String> lines = entry.getValue();
				for (int i = 0; i < lines.size(); i++) {
					if (lines.get(i).contains("differences")) {
						String format = MessageFormat.format(lines.get(i), orderParamBean.getFirstParam());
						lines.set(i, format);
					}
					if (lines.get(i).contains("order")) {
						String format = MessageFormat
								.format(lines.get(i), orderParamBean.getFirstParam(), orderParamBean.getSecondParam(),
										orderParamBean.getThirdParam());
						lines.set(i, format);
					}
				}
			}
			codeParagraphs.set(paramPosition, codeParagraphMap);
			//5.执行代码
			int i = 0;
			if (loadExcelCodePorcess) {
				i = 5;
			}
			for (; i < codeParagraphs.size(); i++) {
				if (loadExcelCodePorcess && i > 5 && i < 10) {
					continue;
				}
				long before = System.currentTimeMillis();
				if (codeParagraphs.get(i) instanceof List) {
					List<String> codeParagraph = (List<String>) codeParagraphs.get(i);
					for (String codeLine : codeParagraph) {
						re.eval(codeLine);
 						if (textConsole.getCurrentTest()!= null && textConsole.getCurrentTest().toString().toLowerCase().contains("error")) {
							textConsole.rFlushConsole(re);
							throw new WoServiceException(WoException.ERR_100000);
						}
						textConsole.rFlushConsole(re);
					}
				}
				if (codeParagraphs.get(i) instanceof Map) {
					Map<String, List<String>> map = (Map<String, List<String>>) codeParagraphs.get(i);
					for (Map.Entry<String, List<String>> entry : map.entrySet()) {
						List<String> codeParagraph = entry.getValue();
						for (int j = 0; j < codeParagraph.size(); j++) {
							re.eval(codeParagraph.get(j));
							if (!textConsole.getCurrentTest().toString().isEmpty() && textConsole.getCurrentTest().toString().toLowerCase().contains("error")) {
								if (!textConsole.getCurrentTest().toString().contains("could not find function")) {
									textConsole.rFlushConsole(re);
									throw new WoServiceException(WoException.ERR_100000);
								}
							}
							textConsole.rFlushConsole(re);
						}
					}
				}
				long after = System.currentTimeMillis();
//				System.out.println("第" + i + "段R代码执行时间" + (after - before + "ms"));
				loadExcelCodePorcess = true;
			}
			rexp =  re.eval("data_output");
		} catch (WoServiceException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			re.end();
		}
		return rexp;
	}

	/**
	 * 功能描述 参数自增
	 * @param lockFirstParam 是否锁定第一个参数 -1表示不锁定
	 * @return void
	 * @author Felix
	 * @date 2020/1/13 15:32
	 */
	public boolean increaseParam(int lockFirstParam) {
		//反向遍历
		ListIterator<Map.Entry<String, OrderParamBean>> i = new ArrayList<>(
				orderParamMap.entrySet()).listIterator(orderParamMap.size());
		int maxParam = 0;
		if (lockFirstParam != -1) {
			maxParam = (lockFirstParam + 1) * 16 - 1;
		} else {
			maxParam = 63;
		}
		int count = 0;
		while (i.hasPrevious()) {
			Map.Entry<String, OrderParamBean> entry = i.previous();
			if (count == 0) {
				count++;
				continue;
			}
			count++;
			OrderParamBean orderParamBean = entry.getValue();
			int unionValue = orderParamBean.getUnionValue();
			if (unionValue == maxParam) {
				if (count == orderParamMap.size()) {
					return true;
				}
				orderParamBean.setUnionValue(-1, lockFirstParam);
				continue;
			}
			orderParamBean.setUnionValue(++unionValue, lockFirstParam);
			break;
		}
		return false;
	}

	public LinkedHashMap<String, OrderParamBean> getOrderParamMap() {
		return orderParamMap;
	}
}

/**
 * @filename TextConsole
 * @description R控制台对象
 * @author Felix
 * @date 2020/1/10 10:34
 */
class TextConsole implements RMainLoopCallbacks {
	private StringBuilder currentTest = new StringBuilder();

	public StringBuilder getCurrentTest() {
		return currentTest;
	}

	@Override
	public void rWriteConsole(Rengine re, String text, int oType) {
		if (text.toLowerCase().contains("error")) {
			currentTest.append(text);
		}
	}

	@Override
	public void rBusy(Rengine re, int which) {
		System.out.println("rBusy(" + which + ")");
	}

	@Override
	public String rReadConsole(Rengine re, String prompt, int addToHistory) {
		System.out.print(prompt);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s = br.readLine();
			return (s == null || s.length() == 0) ? s : s + "\n";
		} catch (Exception e) {
			System.out.println("jriReadConsole com.example.woratio.exception: " + e.getMessage());
		}
		return null;
	}

	@Override
	public void rShowMessage(Rengine re, String message) {
		System.out.println("rShowMessage \"" + message + "\"");
	}

	@Override
	public String rChooseFile(Rengine re, int newFile) {
		FileDialog fd = new FileDialog(new Frame(), (newFile == 0) ? "Select a file" : "Select a new file",
				(newFile == 0) ? FileDialog.LOAD : FileDialog.SAVE);
		fd.show();
		String res = null;
		if (fd.getDirectory() != null)
			res = fd.getDirectory();
		if (fd.getFile() != null)
			res = (res == null) ? fd.getFile() : (res + fd.getFile());
		return res;
	}

	@Override
	public void rFlushConsole(Rengine re) {
		currentTest = new StringBuilder();
	}

	@Override
	public void rLoadHistory(Rengine re, String filename) {
	}

	@Override
	public void rSaveHistory(Rengine re, String filename) {
	}
}