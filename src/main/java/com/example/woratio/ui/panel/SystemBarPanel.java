package com.example.woratio.ui.panel;

import java.awt.*;

import javax.swing.*;

import com.example.woratio.App;
import com.example.woratio.ui.component.MyIconButton;
import com.example.woratio.utils.PropertyUtil;
import com.example.woratio.utils.UiConsts;

/**
 * 工具栏面板
 *
 * @author Bob
 */
public class SystemBarPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonCAS;
    private static MyIconButton buttonTTF;

    /**
     * 构造
     */
    public SystemBarPanel() {
        initialize();
        addButtion();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        Dimension preferredSize = new Dimension(60, UiConsts.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
    }

    /**
     * 添加工具按钮
     */
    private void addButtion() {

        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2, -2, -4));
        buttonCAS = new MyIconButton(UiConsts.ICON_CASBASE_ENABLE, UiConsts.ICON_CASBASE_ENABLE,
                UiConsts.ICON_CASBASE, PropertyUtil.getProperty("ds.ui.cas.title"));
        buttonTTF = new MyIconButton(UiConsts.ICON_TTFBASE, UiConsts.ICON_TTFBASE_ENABLE,
                UiConsts.ICON_TTFBASE, PropertyUtil.getProperty("ds.ui.ttf.title"));

        panelUp.add(buttonCAS);
        panelUp.add(buttonTTF);

        this.add(panelUp);

    }

    /**
     * 为各按钮添加事件动作监听
     */
    private void addListener() {
        buttonCAS.addActionListener(e -> {

            buttonCAS.setIcon(UiConsts.ICON_CASBASE_ENABLE);
            buttonTTF.setIcon(UiConsts.ICON_TTFBASE);

            App.mainPanelCenter.removeAll();

//            TTFPanelFrom.setContent();
            App.mainPanelCenter.add(App.casBasePanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonTTF.addActionListener(e -> {

            buttonCAS.setIcon(UiConsts.ICON_CASBASE);
            buttonTTF.setIcon(UiConsts.ICON_TTFBASE_ENABLE);

            App.mainPanelCenter.removeAll();

//            TTFPanelFrom.setContent();
            App.mainPanelCenter.add(App.casBasePanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });
    }
}
