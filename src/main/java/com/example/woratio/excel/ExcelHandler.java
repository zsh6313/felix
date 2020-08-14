package com.example.woratio.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @filename ExcelHandler
 * @description
 * @author Felix
 * @date 2020/1/10 10:34
 */
public class ExcelHandler {
	private final static String WO_RATIO_RESULT_EXCEL = "C:\\Users\\WH02090\\Desktop\\CAS新版2.0.xlsx";

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
}
