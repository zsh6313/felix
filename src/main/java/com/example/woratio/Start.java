package com.example.woratio;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RVector;

import com.example.woratio.bean.OrderParamBean;
import com.example.woratio.bean.ResultBean;
import com.example.woratio.excel.ExcelHandler;
import com.example.woratio.exception.WoServiceException;
import com.example.woratio.r.Rhandler;
import com.example.woratio.utils.WoratioUtils;

/**
 * @filename com.example.woratio.Start
 * @description
 * @author Felix
 * @date 2020/1/10 16:55
 */
public class Start {
	private static int runCount = 0;

	public static void aaa(String[] args) throws Exception {
		List<ResultBean> resultBeanList = new ArrayList<>();
		Rhandler rhandler = new Rhandler();
		ExcelHandler excelHandler = new ExcelHandler();
		int lockFirstParam = -1;
		long start = System.currentTimeMillis();
		boolean isRandom = true;
		while (true) {
			long after = System.currentTimeMillis();
//			System.out.println("耗时：" + (after - start + "ms"));
//			start = after;
			//运行R代码
			REXP rexp = null;
			try {
				rexp = rhandler.processRScript(isRandom);
			} catch (WoServiceException e) {
				//若捕获R语言运行异常，则递增参数
				System.out.println("第" + ++runCount + "次运行，出现异常，继续运行");
//				boolean isFinish = rhandler.increaseParam(lockFirstParam);
//				if (isFinish) {
//					break;
//				}
				continue;
			}
			//获取excel结果
			if (rexp == null) {
				continue;
			}
			RVector content = (RVector) rexp.getContent();
			double[] actualDatesArr = (double[])((REXP) content.get(1)).getContent();
			double[] likelyDatesArr = (double[])((REXP) content.get(2)).getContent();
			List<Double> likelyDates = new ArrayList<>();
			List<Double> actualDates = new ArrayList<>();
			int length = actualDatesArr.length;

			boolean continueOutFor = false;
			double minValue = 1;
			for (int i = 0; i < actualDatesArr.length; i++) {
				if (i >= length - 24 && i < length - 12) {
					actualDates.add(actualDatesArr[i]);
				}
				if (i >= length - 12) {
//					if ((i == length - 12 ) && likelyDatesArr[i] < 0.015) {
//						continueOutFor = true;
//						break;
//					}
//					if (likelyDatesArr[length - 12] < likelyDatesArr[length - 13]
//							|| likelyDatesArr[length - 12] < likelyDatesArr[length - 11] ) {
//						continueOutFor = true;
//						break;
//					}
//					if (likelyDatesArr[length - 1] < likelyDatesArr[length - 2]) {
//						continueOutFor = true;
//						break;
//					}
//					if (likelyDatesArr[i] < minValue) {
//						minValue = likelyDatesArr[i];
//					}
					likelyDates.add(likelyDatesArr[i]);
				}
			}
			if (continueOutFor) {
				System.out.println("第" + ++runCount + "次运行，波动不符合要求，继续运行。");
				continue;
			}
//			if (likelyDates.get(11) > likelyDates.get(10)) {
//				continue;
//			}
			Double likelyDate = 0.0;
			Double actualDate = 0.0;
			for (Double d : likelyDates) {
				likelyDate += d;
			}
			for (Double d : actualDates) {
				actualDate += d;
			}
			//差异值是否符合要求
			Double bestData = WoratioUtils.BEST_DATA_TTF;
			Double worstData = WoratioUtils.Worst_DATA;
//			Double bestDataTTF = WoratioUtils.BEST_DATA_TTF;
//			Double diffierence = (likelyDate * 0.8 + worstData* 0.15 + bestData * 0.05  - actualDate) / actualDate;
			Double diffierence = (likelyDate * 0.95  + bestData * 0.05  - actualDate) / actualDate;
//			Double diffierence = (likelyDate * 0.75 + bestDataTTF * 0.15 - actualDate) / actualDate;
//			for (Map.Entry<String, OrderParamBean> entry : rhandler.getOrderParamMap().entrySet()) {
//				System.out.println(entry.getKey() + ": order=" + entry.getValue().toString());
//			}
			if (-3 < diffierence * 100 && diffierence * 100 < 2) {
				//若符合要求，记录参数对应结果
				ResultBean resultBean = new ResultBean();
				//深拷贝
				Map<String, OrderParamBean> destMap = new LinkedHashMap<>();
				Map<String, OrderParamBean> orderParamMap = rhandler.getOrderParamMap();
				for (Map.Entry<String, OrderParamBean> entry : orderParamMap.entrySet()) {
					OrderParamBean value = entry.getValue();
					OrderParamBean destBean = new OrderParamBean(value);
					destMap.put(entry.getKey(), destBean);
				}
				resultBean.setOrderParamBean(destMap);
				resultBean.setDiffierence(diffierence);
				resultBeanList.add(resultBean);
				System.out.println("第" + ++runCount + "次运行，符合要求，详情已写入文件========================================");
				FileOutputStream fs = new FileOutputStream(new File("C:/Users/WH02090/Desktop/坏账预测/2020年7月/符合要求的结果.txt"), true);
				fs.write(String.valueOf(diffierence*100).getBytes("UTF-8"));
				fs.write(("minvalue=" + minValue).getBytes("UTF-8"));
				for (Map.Entry<String, OrderParamBean> entry : rhandler.getOrderParamMap().entrySet()) {
					String s = entry.getKey() + ": order=" + entry.getValue().toString()+"\r\n";
					fs.write(s.getBytes("UTF-8"));
					System.out.println(entry.getKey() + ": order=" + entry.getValue().toString());
				}
				fs.close();
			} else {
				System.out.println("第" + ++runCount + "次运行，差异值不符合要求，继续运行。");

			}
			//若非随机模式，则递增参数
			if (!isRandom) {
				boolean isFinish = rhandler.increaseParam(lockFirstParam);
				if (isFinish) {
					Collections.sort(resultBeanList, new Comparator<ResultBean>() {
						@Override
						public int compare(ResultBean o1, ResultBean o2) {
							return new Double(Math.abs(o1.getDiffierence())).compareTo(new Double(Math.abs(o2.getDiffierence())));
						}
					});
					long finish = System.currentTimeMillis();
					System.out.println("耗时:" + (finish - start + "ms"));
					break;
				}
			}
		}
	}
}
