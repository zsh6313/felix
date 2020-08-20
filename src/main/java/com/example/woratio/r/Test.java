package com.example.woratio.r;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @filename Test
 * @description
 * @author Felix
 * @date 2020/1/10 16:02
 */
public class Test {
	private static final int MIN_PROGRESS = 0;
	private static final int MAX_PROGRESS = 100;

	private static int currentProgress = MIN_PROGRESS;
	public static void main(String[] args) {
		JFrame jf = new JFrame("测试窗口");
		jf.setSize(250, 250);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		// 创建一个进度条
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setOrientation(JProgressBar.HORIZONTAL);
		progressBar.setBounds(0, 20, 100, 1);
		progressBar.setBorderPainted(false);
		progressBar.setForeground(new Color(55,165,228));
		progressBar.setBackground(new Color(218,218,218));
		// 设置进度的 最小值 和 最大值
		progressBar.setMinimum(MIN_PROGRESS);
		progressBar.setMaximum(MAX_PROGRESS);

		// 设置当前进度值
		progressBar.setValue(currentProgress);

		// 绘制百分比文本（进度条中间显示的百分数）
		progressBar.setStringPainted(true);

		// 添加进度改变通知
		progressBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("当前进度值: " + progressBar.getValue() + "; " +
						"进度百分比: " + progressBar.getPercentComplete());
			}
		});

		// 添加到内容面板
		panel.add(progressBar,"growx,h 4!,span 2");

		jf.setContentPane(panel);
		jf.setVisible(true);

		// 模拟延时操作进度, 每隔 0.5 秒更新进度
		new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentProgress++;
				if (currentProgress > MAX_PROGRESS) {
					currentProgress = MIN_PROGRESS;
				}
				progressBar.setValue(currentProgress);
			}
		}).start();
	}
}
