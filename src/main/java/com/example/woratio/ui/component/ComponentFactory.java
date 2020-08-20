package com.example.woratio.ui.component;

import javax.swing.*;

import com.example.woratio.utils.PropertyUtil;
import com.example.woratio.utils.UiConsts;

/**
 * @filename ComponentFactory
 * @description 组件工厂
 * @author Felix
 * @date 2020/8/19 16:34
 */
public class ComponentFactory {

	public static JLabel getJlabel(String configName) {
		JLabel label = new JLabel(PropertyUtil.getProperty(configName));
		label.setFont(UiConsts.FONT_NORMAL);
		label.setPreferredSize(UiConsts.LABLE_SIZE_ITEM);
		return label;
	}

	public static JTextField getJTextField() {
		JTextField jTextField = new JTextField();
		jTextField.setFont(UiConsts.FONT_NORMAL);
		jTextField.setPreferredSize(UiConsts.TEXT_FIELD_SIZE_ITEM);
		return jTextField;
	}

}
