package com.example.woratio;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.woratio.ui.panel.CasBasePanel;
import com.example.woratio.ui.panel.SystemBarPanel;
import com.example.woratio.utils.UiConsts;

/**
 * @filename com.example.woratio.App
 * @description 启动类
 * @author Felix
 * @date 2020/8/14 15:11
 */
public class App {
	private static final Logger logger = LoggerFactory.getLogger(App.class);

	private JFrame frame;
	public static JPanel mainPanelCenter;

	public static CasBasePanel casBasePanel;



	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				App window = new App();
				window.frame.setVisible(true);
				window.frame.setExtendedState( Frame.MAXIMIZED_BOTH );

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 构造，创建APP
	 */
	public App() {
		initialize();
	}

	/**
	 * 初始化frame内容
	 */
	private void initialize() {
		logger.info("==================AppInitStart");
		// 设置系统默认样式
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 初始化主窗口
		frame = new JFrame();
		frame.setBounds(UiConsts.MAIN_WINDOW_X, UiConsts.MAIN_WINDOW_Y, UiConsts.MAIN_WINDOW_WIDTH,
				UiConsts.MAIN_WINDOW_HEIGHT);
		frame.setTitle(UiConsts.APP_NAME);
		frame.setIconImage(UiConsts.IMAGE_ICON);
		frame.setBackground(UiConsts.MAIN_BACK_COLOR);

		JPanel mainPanel = new JPanel(true);
		mainPanel.setBackground(Color.white);
		mainPanel.setLayout(new BorderLayout());
		/** 初始化工具栏 */
		SystemBarPanel toolbar = new SystemBarPanel();
		mainPanel.add(toolbar, BorderLayout.WEST);
		/** CAS页面 */
		mainPanelCenter = new JPanel(true);
		mainPanelCenter.setLayout(new BorderLayout());
		casBasePanel = new CasBasePanel();
		mainPanelCenter.add(casBasePanel, BorderLayout.CENTER);
		mainPanel.add(mainPanelCenter, BorderLayout.CENTER);

		frame.add(mainPanel);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
