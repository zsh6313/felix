package com.example.woratio.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.example.woratio.bean.ExcelRowBean;
import com.example.woratio.myenum.FinanceEnum;
import com.example.woratio.myenum.ManufactureEnum;
import com.example.woratio.myenum.NonManufactureEnum;
import com.example.woratio.myenum.ProducerPurchaseEnum;
import com.example.woratio.myenum.ResidentConsumptionEnum;

/**
 * @filename DataHandler
 * @description
 * @author Felix
 * @date 2020/8/19 11:40
 */
public class DataHandler {
	private static Map<String, List> macroDataField;
	private static List<String> woratioInitField;

	private static Map<String, Class> macroDataFieldEnum;

	private static List<ExcelRowBean> macroData;

	private static List<ExcelRowBean> woratioInitData;
	private static Map<String,ExcelRowBean> financialDataMap;

	public Map<String, List> getMacroDataField() {
		if (DataHandler.macroDataField != null) {
			return macroDataField;
		}
		macroDataField = new HashMap<>();

		ManufactureEnum[] var1 = ManufactureEnum.values();
		List manufactureFieldList = new ArrayList();
		for (ManufactureEnum manufactureEnum : var1) {
			manufactureFieldList.add(manufactureEnum.getValue());
		}
		macroDataField.put(ManufactureEnum.DESCRIBT, manufactureFieldList);

		ResidentConsumptionEnum[] var2 = ResidentConsumptionEnum.values();
		List residentConsumptionFieldList = new ArrayList();
		for (ResidentConsumptionEnum residentConsumptionEnum : var2) {
			residentConsumptionFieldList.add(residentConsumptionEnum.getValue());
		}
		macroDataField.put(ResidentConsumptionEnum.DESCRIBT, residentConsumptionFieldList);

		FinanceEnum[] var3 = FinanceEnum.values();
		List financeFieldList = new ArrayList();
		for (FinanceEnum financeEnum : var3) {
			financeFieldList.add(financeEnum.getValue());
		}
		macroDataField.put(FinanceEnum.DESCRIBT, financeFieldList);

		ProducerPurchaseEnum[] var4 = ProducerPurchaseEnum.values();
		List producerPurchaseFieldList = new ArrayList();
		for (ProducerPurchaseEnum producerPurchaseEnum : var4) {
			producerPurchaseFieldList.add(producerPurchaseEnum.getValue());
		}
		macroDataField.put(ProducerPurchaseEnum.DESCRIBT, producerPurchaseFieldList);

		NonManufactureEnum[] var5 = NonManufactureEnum.values();
		List nonManufactureFieldList = new ArrayList();
		for (NonManufactureEnum nonManufactureEnum : var5) {
			nonManufactureFieldList.add(nonManufactureEnum.getValue());
		}
		macroDataField.put(NonManufactureEnum.DESCRIBT, nonManufactureFieldList);
		return macroDataField;
	}

	public static Map<String, Class> getMacroDataFieldEnum() {
		if (DataHandler.macroDataFieldEnum != null) {
			return macroDataFieldEnum;
		}
		macroDataFieldEnum = new HashMap<>();

		ManufactureEnum[] var1 = ManufactureEnum.values();
		for (ManufactureEnum manufactureEnum : var1) {
			macroDataFieldEnum.put(manufactureEnum.getValue(), ManufactureEnum.class);
		}

		ResidentConsumptionEnum[] var2 = ResidentConsumptionEnum.values();
		for (ResidentConsumptionEnum residentConsumptionEnum : var2) {
			macroDataFieldEnum.put(residentConsumptionEnum.getValue(), ResidentConsumptionEnum.class);
		}

		FinanceEnum[] var3 = FinanceEnum.values();
		for (FinanceEnum financeEnum : var3) {
			macroDataFieldEnum.put(financeEnum.getValue(), FinanceEnum.class);

		}

		ProducerPurchaseEnum[] var4 = ProducerPurchaseEnum.values();
		for (ProducerPurchaseEnum producerPurchaseEnum : var4) {
			macroDataFieldEnum.put(producerPurchaseEnum.getValue(), ProducerPurchaseEnum.class);
		}

		NonManufactureEnum[] var5 = NonManufactureEnum.values();
		for (NonManufactureEnum nonManufactureEnum : var5) {
			macroDataFieldEnum.put(nonManufactureEnum.getValue(), NonManufactureEnum.class);
		}

		return macroDataFieldEnum;
	}

	public  void setMacroData(List<ExcelRowBean> macroData) {
		DataHandler.macroData = macroData;
	}

	public  void setWoratioInitData(List<ExcelRowBean> woratioInitData) {
		DataHandler.woratioInitData = woratioInitData;
	}

	public  void setFinancialData(ExcelRowBean financialData, String system) {
		financialDataMap.put(system, financialData);
	}


	public List<ExcelRowBean> getMacroData() throws IOException, InvalidFormatException {
		return macroData;
	}

	public List<ExcelRowBean> getWoratioInitData() throws IOException, InvalidFormatException {
		return woratioInitData;
	}

	public ExcelRowBean getFinancialData(String system) throws IOException, InvalidFormatException {
		return financialDataMap.get(system);
	}

	public List<String> getWoratioInitField() {
		if (DataHandler.woratioInitField != null) {
			return woratioInitField;
		}
		woratioInitField = new ArrayList<>();
		ManufactureEnum[] var = ManufactureEnum.values();
		for (ManufactureEnum manufactureEnum : var) {
			woratioInitField.add(manufactureEnum.getValue());
		}
		return woratioInitField;
	}
}
