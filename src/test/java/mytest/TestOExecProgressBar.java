package mytest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//import net.miginfocom.swing.MigLayout;

/**
 *
 * @author panghui 2013-6-28
 * 消息中心倒计时进度条
 */
public class TestOExecProgressBar implements ActionListener, ChangeListener  {

	JFrame frame = null;
	JProgressBar progressbar;
	JLabel label;
	Timer timer;
	JButton b;
	JButton s;

	public TestOExecProgressBar() {
		frame = new JFrame("安装");
		frame.setBounds(100, 100, 1000, 130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPanel = frame.getContentPane();

		label = new JLabel("", JLabel.CENTER);
		progressbar = new JProgressBar();
		progressbar.setOrientation(JProgressBar.HORIZONTAL);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
		progressbar.setValue(0);
		//progressbar.setStringPainted(true);//设置进度条上边是否显示 百分比
		progressbar.addChangeListener(this);// 实现进度条值改变的动态监听
		progressbar.setBounds(0, 20, 300, 50);
		progressbar.setBorderPainted(false);
		progressbar.setForeground(new Color(55,165,228));
		progressbar.setBackground(new Color(218,218,218));

//		JPanel panel = new JPanel(new MigLayout("wrap 2","[grow,100%]","[grow,50%] [grow,50%]"));
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255,255,255));
		panel.setPreferredSize(new Dimension(400,50));
		b = new JButton("安装");
		b.setForeground(Color.blue);
		b.addActionListener(this);
		s = new JButton("停止");
		s.setForeground(Color.blue);
		s.addActionListener(this);
		panel.add(b);
		panel.add(s);
		panel.add(progressbar,"growx,h 4!,span 2");
		timer = new Timer(4, this);
		contentPanel.add(panel, BorderLayout.NORTH);
		contentPanel.add(label, BorderLayout.SOUTH);
		frame.setVisible(true);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b) {
			timer.start();
		}
		if (e.getSource() == s) {
			timer.stop();
		}
		if (e.getSource() == timer) {
			int value = progressbar.getValue();
			if (value < 100) {
				progressbar.setValue(++value);
			} else if(value == 100) {
				progressbar.setValue(0);
			}
		}

	}

	@Override
	public void stateChanged(ChangeEvent e1) {
		int value = progressbar.getValue();
		if (e1.getSource() == progressbar) {
			label.setText("目前已完成进度：" + Integer.toString(value) + "%");
			label.setForeground(Color.blue);
		}
	}

	public static void main(String[] args) {
		TestOExecProgressBar app = new TestOExecProgressBar();
	}
}