package com.example.woratio.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import com.example.woratio.bean.ExcelRowBean;
import com.example.woratio.bean.MonthBean;
import com.example.woratio.data.DataHandler;
import com.example.woratio.utils.DateUtils;

/**
 * @filename ExcelHandler
 * @description
 * @author Felix
 * @date 2020/1/10 10:34
 */
public class ExcelHandler {
	private final static String WO_RATIO_RESULT_EXCEL = "C:\\Users\\WH02090\\Desktop\\CAS新版2.0.xlsx";
	private static File macroExcel;
	private static File woratioInitExcel;
	private static File localWoratioInitExcel;
	private static File financialExcel;

	public List<Double> getLikelyDate() throws Exception {
		File excel = new File(WO_RATIO_RESULT_EXCEL);
		List<Double> valueList = new ArrayList<>();
		if (excel.isFile() && excel.exists()) {   //判断文件是否存在
			int i = excel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = excel.getName().substring(0,i);
			split[1] = excel.getName().substring(i + 1,excel.getName().length());
			Workbook wb;
			wb = new XSSFWorkbook(excel);

			//开始解析
			Sheet sheet = wb.getSheetAt(0);
			int lastRowIndex = sheet.getLastRowNum();
			for(int rIndex = lastRowIndex - 11; rIndex <= lastRowIndex; rIndex++) {   //遍历行
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					Cell cell = row.getCell(2);
					if (cell != null) {
						valueList.add(Double.valueOf(cell.toString()));
					}
				}
			}
			int a = 0;
		} else {
			System.out.println("找不到指定的文件");
		}
		return valueList;
	}
	public  List<Double> getActualDate() throws Exception {
		File excel = new File(WO_RATIO_RESULT_EXCEL);
		List<Double> valueList = new ArrayList<>();
		if (excel.isFile() && excel.exists()) {   //判断文件是否存在
			int i = excel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = excel.getName().substring(0,i);
			split[1] = excel.getName().substring(i + 1,excel.getName().length());
			Workbook wb;
			wb = new XSSFWorkbook(excel);

			//开始解析
			Sheet sheet = wb.getSheetAt(0);
			int lastRowIndex = sheet.getLastRowNum();
			for(int rIndex = lastRowIndex - 23; rIndex <= lastRowIndex - 12; rIndex++) {   //遍历行
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					Cell cell = row.getCell(1);
					if (cell != null) {
						valueList.add(Double.valueOf(cell.toString()));
					}
				}
			}
			int a = 0;
		} else {
			System.out.println("找不到指定的文件");
		}
		return valueList;
	}

	public List<ExcelRowBean> readMacroData() throws IOException, InvalidFormatException {
		List<ExcelRowBean> result = new ArrayList<>();
		if (macroExcel.isFile() && macroExcel.exists()) {   //判断文件是否存在
			int i = macroExcel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = macroExcel.getName().substring(0,i);
			split[1] = macroExcel.getName().substring(i + 1,macroExcel.getName().length());
			Workbook wb;
			wb = new XSSFWorkbook(macroExcel);
			//开始解析
			Sheet sheet = wb.getSheetAt(0);
			//获取最近一个月
			MonthBean latestMonth = new MonthBean();
			Row fristRow = sheet.getRow(0);
			for (int j = 0; j < fristRow.getLastCellNum(); j++) {
				String dateStr = fristRow.getCell(j) == null ? "" : fristRow.getCell(j).toString();
				if (dateStr.startsWith("19")
				|| dateStr.startsWith("20")
				|| dateStr.startsWith("21")) {
					latestMonth = DateUtils.getMouthBean(dateStr);
					break;
				}
			}
			//获取宏观数据的长度，根据最近一个月到2016年6月的月分差获得
			int dataLength = DateUtils.getMonthDifference(latestMonth, new MonthBean(2016, 6));
			//获取符合条件的宏观数据名称
			Set<String> macroDataFieldList = new LinkedHashSet<>();
			Map<String, List> macroDataField = DataHandler.getMacroDataField();
			for (Map.Entry<String, List> entry : macroDataField.entrySet()) {
				macroDataFieldList.addAll(entry.getValue());
			}
			//获取宏观数据参数
			for(int rIndex = 1; rIndex <= sheet.getLastRowNum(); rIndex++) {
				Row row = sheet.getRow(rIndex);
				Cell rowNameHead= row.getCell(1);
				Cell rowNameTail= row.getCell(2);
				int i1 = rowNameTail.toString().lastIndexOf("(");
				String rowName = "";
				if (("制造业".equals(rowNameHead.toString()) || "非制造业".equals(rowNameHead.toString()))
						&& ("从业人员指数".equals(rowNameTail.toString().substring(0,i1))
						|| "供应商配送时间指数".equals(rowNameTail.toString().substring(0,i1))
						|| ("新订单指数".equals(rowNameTail.toString().substring(0,i1))
						&& "非制造业".equals(rowNameHead.toString())))) {
					rowName = rowNameHead + "-" +rowNameTail.toString().substring(0,i1);
				} else  {
					rowName = rowNameTail.toString().substring(0,i1);
				}
				if (macroDataFieldList.contains(rowName)) {		//校验符合条件的宏观数据
					ExcelRowBean macroExcelBean = new ExcelRowBean();
					List<Map<MonthBean, Object>> dataList = new ArrayList<>();
					for (int j = 3; j < 4 + dataLength; j++) {
						Map<MonthBean, Object> cellData = new HashMap<>();
						MonthBean monthBean = latestMonth.subMonth(j - 3);
						cellData.put(monthBean, row.getCell(j)== null ? "" : row.getCell(j).toString());
						dataList.add(cellData);
					}
					macroExcelBean.setName(rowName);
					macroExcelBean.setDataList(dataList);
					result.add(macroExcelBean);
				}
			}
		} else {
			System.out.println("找不到指定的文件");
		}
		return result;
	}
	
	/**
	 * 功能描述 读取坏账初始数据
	 * @param
	 * @return void
	 * @author Felix
	 * @date 2020/8/20 16:12
	 */
	public List<ExcelRowBean> readWoratioInitData() throws IOException, InvalidFormatException {
		//读取本地历史数据
		List<ExcelRowBean> LocalData = readLocalWoratioInitData();
		List<ExcelRowBean> result = new ArrayList<>();
		if (result != null) {
		}
		if (woratioInitExcel.isFile() && woratioInitExcel.exists()) {   //判断文件是否存在
			int i = woratioInitExcel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = woratioInitExcel.getName().substring(0, i);
			split[1] = woratioInitExcel.getName().substring(i + 1, woratioInitExcel.getName().length());
			Workbook wb;
			wb = new XSSFWorkbook(woratioInitExcel);
			//开始解析
			Sheet sheet = wb.getSheetAt(0);
		}
		return result;
	}

	/**
	 * 功能描述 读取本地坏账初始数据的历史数据
	 * @param
	 * @return void
	 * @author Felix
	 * @date 2020/8/20 16:12
	 */
	private List<ExcelRowBean> readLocalWoratioInitData() throws IOException, InvalidFormatException {
		//读取本地历史数据
		if  (localWoratioInitExcel == null) {
			ClassPathResource classPathResource = new ClassPathResource("/data/woratioInitHistory.xlsx");
			localWoratioInitExcel = classPathResource.getFile();
		}
		List<ExcelRowBean> result = new ArrayList<>();
		if (result != null) {
		}
		if (localWoratioInitExcel.isFile() && localWoratioInitExcel.exists()) {   //判断文件是否存在
			int i = localWoratioInitExcel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = localWoratioInitExcel.getName().substring(0, i);
			split[1] = localWoratioInitExcel.getName().substring(i + 1, localWoratioInitExcel.getName().length());
			Workbook wb;
			wb = new XSSFWorkbook(localWoratioInitExcel);
			//开始解析
			Sheet sheet = wb.getSheetAt(0);
			Row headRow = sheet.getRow(0);
			for (int j = 1; j < headRow.getLastCellNum(); j++) {
				ExcelRowBean excelRowBean = new ExcelRowBean();
				excelRowBean.setName(headRow.getCell(j) != null ? headRow.getCell(j).toString() : "");
				excelRowBean.setDataList(new ArrayList<>());
				result.add(excelRowBean);
			}
			for (int j = 1; j < sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				String dateStr = row.getCell(0).toString();
				MonthBean mouthBean = DateUtils.getMouthBean(dateStr);
				for (int k = 1; k < row.getLastCellNum(); k++) {
					if (!StringUtils.isEmpty(row.getCell(k))) {
						Map<MonthBean,Object> cellBean = new HashMap<>();
						cellBean.put(mouthBean.copy(), row.getCell(k).toString());
						result.get(k-1).getDataList().add(cellBean);
					}
				}
			}
		}
		return result;
	}

	public void saveMacroExcel(File f) {
		macroExcel = f;
	}

	public void saveWoratioInitExcel(File f) {
		woratioInitExcel = f;
	}

	public void saveFinancialExcel(File f) {
		financialExcel = f;
	}


}
