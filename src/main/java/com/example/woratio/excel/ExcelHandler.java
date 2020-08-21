package com.example.woratio.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;
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

	private DataHandler dataHandler = new DataHandler();

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

	public List<ExcelRowBean> loadMacroData() throws IOException, InvalidFormatException {
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
			Map<String, List> macroDataField = dataHandler.getMacroDataField();
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
					Map<MonthBean, Object> dataMap = new HashMap<>();
					for (int j = 3; j < 4 + dataLength; j++) {
						MonthBean monthBean = latestMonth.subMonth(j - 3);
						dataMap.put(monthBean, row.getCell(j)== null ? "" : row.getCell(j).toString());
					}
					macroExcelBean.setName(rowName);
					macroExcelBean.setDataMap(dataMap);
					result.add(macroExcelBean);
				}
			}
		} else {
			System.out.println("找不到指定的文件");
		}
		dataHandler.setMacroData(result);
		return result;
	}
	
	/**
	 * 功能描述 读取坏账初始数据
	 * @param
	 * @return void
	 * @author Felix
	 * @date 2020/8/20 16:12
	 */
	public List<ExcelRowBean> loadWoratioInitData() throws IOException, InvalidFormatException {
		//读取本地历史数据
		List<ExcelRowBean> result = new ArrayList<>();
		if (woratioInitExcel.isFile() && woratioInitExcel.exists()) {   //判断文件是否存在
			int i = woratioInitExcel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = woratioInitExcel.getName().substring(0, i);
			split[1] = woratioInitExcel.getName().substring(i + 1, woratioInitExcel.getName().length());
			XSSFWorkbook wb;
			wb = new XSSFWorkbook(woratioInitExcel);
			//开始解析
			XSSFSheet sheet = wb.getSheetAt(0);
			//获取数据
			XSSFRow row = sheet.getRow(1);
			result = unionLocalWoratioInitData(row);
		}
		dataHandler.setWoratioInitData(result);
		return result;
	}

	/**
	 * 功能描述 读取财务表格数据
	 * @param
	 * @return java.util.List<com.example.woratio.bean.ExcelRowBean>
	 * @author Felix
	 * @date 2020/8/21 14:57
	 */
	public ExcelRowBean loadFinancialData(String system) throws IOException, InvalidFormatException {
		ExcelRowBean financialBean = null;
		Map<MonthBean,Object> dataMap = new HashMap<>();
		if (financialExcel.isFile() && financialExcel.exists()) {   //判断文件是否存在
			int i = financialExcel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = financialExcel.getName().substring(0,i);
			split[1] = financialExcel.getName().substring(i + 1,financialExcel.getName().length());
			XSSFWorkbook wb;
			wb = new XSSFWorkbook(financialExcel);
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			//开始解析
			XSSFSheet sheet = null;
			if ("cas".equals(system)) {
				sheet = wb.getSheetAt(0);
			} else if ("ttf".equals(system)){
				sheet = wb.getSheetAt(1);
			}
			MonthBean monthBean = new MonthBean(2016,6);
			MonthBean currentBean = null;
			for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
				//begin row
				currentBean = DateUtils.getMouthBean(sheet.getRow(j).getCell(0).toString());
				if (sheet.getRow(j).getCell(0) != null
						&& DateUtils.getMouthBean(sheet.getRow(j).getCell(0).toString()).subtractMonth(monthBean) >= 0){
					dataMap.put(currentBean, getCellRealValue(evaluator, sheet.getRow(j).getCell(3)));
				}
			}
			financialBean = new ExcelRowBean();
			financialBean.setDataMap(dataMap);
			financialBean.setName("wo_ratio");
		}
		dataHandler.setFinancialData(financialBean, system);
		return financialBean;
	}


	/**
	 * 功能描述 整合本地坏账初始数据的历史数据
	 * @param
	 * @return void
	 * @author Felix
	 * @date 2020/8/20 16:12
	 */
	private List<ExcelRowBean> unionLocalWoratioInitData(XSSFRow newRow) throws IOException, InvalidFormatException {
		//读取本地历史数据
		if  (localWoratioInitExcel == null) {
			localWoratioInitExcel = ResourceUtils.getFile("classpath:data/woratioInitHistory.xlsx");
//			localWoratioInitExcel = new File("C:/Users/Administrator/Desktop/dev/坏账预测/2020年7月/woratioInitHistory.xlsx");
		}
		List<ExcelRowBean> result = new ArrayList<>();
		if (result != null) {
		}
		if (localWoratioInitExcel.isFile() && localWoratioInitExcel.exists()) {   //判断文件是否存在
			int i = localWoratioInitExcel.getName().lastIndexOf(".");
			String[] split = new String[2];
			split[0] = localWoratioInitExcel.getName().substring(0, i);
			split[1] = localWoratioInitExcel.getName().substring(i + 1);
			XSSFWorkbook wb;
			wb = new XSSFWorkbook(localWoratioInitExcel);
			//开始解析
			XSSFSheet sheet = wb.getSheetAt(0);
			int lastRealRowNum = 0; //有值的行
			for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
				XSSFCell fristCell = sheet.getRow(j).getCell(0);
				if (fristCell != null && !StringUtils.isEmpty(fristCell)) {
					lastRealRowNum = j;
				}
			}
			//插入一行
			sheet.shiftRows(lastRealRowNum + 1, lastRealRowNum + 1,1);
			XSSFRow blankRow = sheet.createRow(lastRealRowNum + 1);
			createWoratioInitExcelCell(blankRow, newRow);
			FileOutputStream out = new FileOutputStream(ResourceUtils.getFile("classpath:data/woratioInitNew.xlsx"));
			wb.write(out);
			out.close();
			wb.close();

			wb = new XSSFWorkbook(localWoratioInitExcel);
			sheet = wb.getSheetAt(0);
			Row headRow = sheet.getRow(0);
			for (int j = 1; j < headRow.getLastCellNum(); j++) {
				ExcelRowBean excelRowBean = new ExcelRowBean();
				excelRowBean.setName(headRow.getCell(j) != null ? headRow.getCell(j).toString() : "");
				excelRowBean.setDataMap(new HashMap<>());
				result.add(excelRowBean);
			}
			for (int j = 1; j < sheet.getLastRowNum(); j++) {
				Row row = sheet.getRow(j);
				String dateStr = row.getCell(0) == null ? "" : row.getCell(0).toString();
				MonthBean mouthBean = DateUtils.getMouthBean(dateStr);
				for (int k = 1; k < row.getLastCellNum(); k++) {
					if (!StringUtils.isEmpty(row.getCell(k))) {
						result.get(k-1).getDataMap().put(mouthBean.copy(),row.getCell(k).toString());
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



	/**
	 * 创建要插入的行中单元格
	 * @param blankRow
	 * @param insertRow
	 * @return
	 */
	private void createWoratioInitExcelCell(XSSFRow blankRow, XSSFRow insertRow) {
		for (int i = 0; i < insertRow.getLastCellNum(); i++) {
			blankRow.createCell(i).setCellValue(insertRow.getCell(i).toString());
		}
	}


	private Object getCellRealValue(FormulaEvaluator evaluator, XSSFCell cell) {
		Object value= "";
		switch (cell.getCellTypeEnum()) {
		case BLANK:
			value =  "";
			break;
		case BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case ERROR:
			value = cell.getErrorCellString();
			break;
		case FORMULA:
			CellValue cellValue = evaluator.evaluate(cell);
			boolean isNumeric = cellValue.getCellTypeEnum() == CellType.NUMERIC;
			value = (isNumeric) ? cellValue.getNumberValue() : cellValue.getStringValue();
			if (isNumeric && value.toString().equals("0.0")) {
				value = cell.getNumericCellValue();
			}
			break;
		case NUMERIC:
			value =  cell.getNumericCellValue();
			break;
		case STRING:
			value = cell.getStringCellValue();
			break;
		default:
			break;
		}
		return value;
	}
}
