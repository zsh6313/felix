package com.example.woratio.ui.panel;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.woratio.App;
import com.example.woratio.bean.ExcelRowBean;
import com.example.woratio.data.DataHandler;
import com.example.woratio.excel.ExcelHandler;
import com.example.woratio.ui.component.ComponentFactory;
import com.example.woratio.ui.component.MyIconButton;
import com.example.woratio.utils.PropertyUtil;
import com.example.woratio.utils.UiConsts;

/**
 * 来源数据库面板
 *
 * @author Bob
 */
public class CasRebuildModelPanel extends JPanel {

    private static final long serialVersionUID = 1L;

	private JPanel	macroPanel;
	private JPanel	woratioInitPanel;
	private JPanel	financialPanel;

	private static MyIconButton buttonCreate;

	private static JComboBox<String> createTypeComboBox; 	//创建模型方式 手动 自动
	private static JComboBox<String> fieldNumberComboBox;	//因子数量  暂时固定为6个
	private static JComboBox<String> composeTypeComboBox;  //因子组合方式 顺序 随机

    private static JTextField textFieldMacroDataChoose;
    private static JTextField textFieldWoratioInitData;
    private static JTextField textFieldfinancialDataData;

    private static MyIconButton buttonMacroChoose;
    private static MyIconButton buttonWoratioInitChoose;
    private static MyIconButton buttonfinancialDataChoose;

    private Map<String, List> macroDataField;
    private List <String> woratioInitField;

    private ExcelHandler excelHandler = new ExcelHandler();
    private DataHandler dataHandler = new DataHandler();

    private static final Logger logger = LoggerFactory.getLogger(CasRebuildModelPanel.class);

    /**
     * 构造
     */
    public CasRebuildModelPanel() {
		initialize();
        addComponent();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};  //设置了总共有一列
		gridBagLayout.rowHeights = new int[]{0, 0, 0};  //设置了总共有2行
		gridBagLayout.columnWeights = new double[]{1.0};  //设置了列的宽度为容器宽度
		gridBagLayout.rowWeights = new double[]{0.01,0.02,0.8};  //第一行的高度占了容器的2份，第二行的高度占了容器的8份
		this.setLayout(gridBagLayout);
	}

    /**
     * 添加组件
     */
    private void addComponent() {
		GridBagConstraints constrainst1 = new GridBagConstraints();
		constrainst1.insets = new Insets(0, 0, 5, 0);
		constrainst1.fill = GridBagConstraints.BOTH;
		constrainst1.gridx = 0;
		constrainst1.gridy = 0;

		GridBagConstraints constrainst2 = new GridBagConstraints();
		constrainst2.insets = new Insets(0, 0, 5, 0);
		constrainst2.fill = GridBagConstraints.BOTH;
		constrainst2.gridx = 0;
		constrainst2.gridy = 1;

		GridBagConstraints constrainst3 = new GridBagConstraints();
		constrainst3.insets = new Insets(0, 0, 5, 0);
		constrainst3.fill = GridBagConstraints.BOTH;
		constrainst3.gridx = 0;
		constrainst3.gridy = 2;

        this.add(getNorthPanel(), constrainst1);
        this.add(getCenterPanel(), constrainst2);
		JPanel jPanel = new JPanel();
		jPanel.setBackground(Color.WHITE);
		this.add(jPanel,constrainst3);

    }


    /**
     * 上部面板
     *
     * @return
     */
    private JPanel getNorthPanel() {
        // 中间面板
        JPanel northPanel = new JPanel();
		northPanel.setBackground(UiConsts.MAIN_BACK_COLOR);
		northPanel.setLayout(new BorderLayout());

        //各个选择数据的面板
		macroPanel = new JPanel();
        macroPanel.setBackground(UiConsts.MAIN_BACK_COLOR);
		macroPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		woratioInitPanel = new JPanel();
		woratioInitPanel.setBackground(UiConsts.MAIN_BACK_COLOR);
		woratioInitPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		financialPanel = new JPanel();
		financialPanel.setBackground(UiConsts.MAIN_BACK_COLOR);
        financialPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));


        // 初始化组件
        JLabel labelMacro = ComponentFactory.getJlabel("ds.ui.rebuild.macro");
        JLabel labelWoratioInit = ComponentFactory.getJlabel("ds.ui.rebuild.woratioInit");
        JLabel labelFinancial = ComponentFactory.getJlabel("ds.ui.rebuild.financial");
        textFieldMacroDataChoose = ComponentFactory.getJTextField();
		textFieldMacroDataChoose.setEnabled(false);
		textFieldWoratioInitData = ComponentFactory.getJTextField();
		textFieldWoratioInitData.setEnabled(false);
		textFieldfinancialDataData = ComponentFactory.getJTextField();
		textFieldfinancialDataData.setEnabled(false);
		buttonMacroChoose = new MyIconButton(UiConsts.ICON_CHOOSEFILE, UiConsts.ICON_CHOOSEFILE_ENABLE,
                UiConsts.ICON_CHOOSEFILE_ENABLE, "");
		buttonWoratioInitChoose = new MyIconButton(UiConsts.ICON_CHOOSEFILE, UiConsts.ICON_CHOOSEFILE_ENABLE,
                UiConsts.ICON_CHOOSEFILE_ENABLE, "");
		buttonfinancialDataChoose = new MyIconButton(UiConsts.ICON_CHOOSEFILE, UiConsts.ICON_CHOOSEFILE_ENABLE,
                UiConsts.ICON_CHOOSEFILE_ENABLE, "");

        // 边框距离
        labelMacro.setBorder(new EmptyBorder(0, 25, 0, 0));
        labelWoratioInit.setBorder(new EmptyBorder(0, 25, 0, 0));
        labelFinancial.setBorder(new EmptyBorder(0, 25, 0, 0));

        // 组合元素
        macroPanel.add(labelMacro);
        macroPanel.add(textFieldMacroDataChoose);
        macroPanel.add(buttonMacroChoose);

        woratioInitPanel.add(labelWoratioInit);
        woratioInitPanel.add(textFieldWoratioInitData);
        woratioInitPanel.add(buttonWoratioInitChoose);

        financialPanel.add(labelFinancial);
        financialPanel.add(textFieldfinancialDataData);
        financialPanel.add(buttonfinancialDataChoose);

		macroPanel.setPreferredSize(new Dimension(1000,50));
		woratioInitPanel.setPreferredSize(new Dimension(1000,50));
		financialPanel.setPreferredSize(new Dimension(1000,50));

		northPanel.add(macroPanel, BorderLayout.NORTH);
		northPanel.add(woratioInitPanel, BorderLayout.CENTER);
		northPanel.add(financialPanel,BorderLayout.SOUTH);
        return northPanel;
    }

    /**
     * 中间面板
     *
     * @return
     */
    private JPanel getCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBackground(UiConsts.MAIN_BACK_COLOR);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		//选择下拉框
		JLabel createTypeLabel = ComponentFactory.getJlabel("ds.ui.rebuild.createTypeLabel");
		createTypeLabel.setPreferredSize(new Dimension(160, 30));
		createTypeLabel.setBorder( new EmptyBorder(0,60,0,0));

		JLabel fieldNumberLabel = ComponentFactory.getJlabel("ds.ui.rebuild.fieldNumberLabel");
		fieldNumberLabel.setPreferredSize(new Dimension(130, 30));
		fieldNumberLabel.setBorder( new EmptyBorder(0,60,0,0));


		JLabel composeTypeLabel = ComponentFactory.getJlabel("ds.ui.rebuild.composeTypeLabel");
		composeTypeLabel.setPreferredSize(new Dimension(160, 30));
		composeTypeLabel.setBorder( new EmptyBorder(0,60,0,0));

		createTypeComboBox = new JComboBox<>();
		createTypeComboBox.setFont(UiConsts.FONT_NORMAL);
		createTypeComboBox.setPreferredSize(new Dimension(80, 22));
		createTypeComboBox.addItem("自动");
		createTypeComboBox.addItem("手动");
		createTypeComboBox.setEditable(false);

		fieldNumberComboBox = new JComboBox<>();
		fieldNumberComboBox.setFont(UiConsts.FONT_NORMAL);
		fieldNumberComboBox.setPreferredSize(new Dimension(60, 22));
		fieldNumberComboBox.addItem("6");
		fieldNumberComboBox.setEditable(false);

		composeTypeComboBox = new JComboBox<>();
		composeTypeComboBox.setFont(UiConsts.FONT_NORMAL);
		composeTypeComboBox.setPreferredSize(new Dimension(120, 22));
		composeTypeComboBox.addItem("顺序组合");
		composeTypeComboBox.addItem("随机组合");
		composeTypeComboBox.setEditable(false);

		//开始按钮
		buttonCreate = new MyIconButton(UiConsts.ICON_START, UiConsts.ICON_START_ENABLE,
                UiConsts.ICON_START_DISABLE, "");
//		buttonCreate.setEnabled(false);
		buttonCreate.setBorder( new EmptyBorder(0,150,0,0));
		//分隔符
		JSeparator sep = new JSeparator(SwingConstants.CENTER);
		sep.setPreferredSize(new Dimension(this.getWidth(),1));
		sep.setBackground(new Color(255,255,255));

		JSeparator sep1 = new JSeparator(SwingConstants.CENTER);
		sep1.setPreferredSize(new Dimension(this.getWidth(),1));
		sep1.setBackground(new Color(255,255,255));

		buttonPanel.add(createTypeLabel);
		buttonPanel.add(createTypeComboBox);
		buttonPanel.add(fieldNumberLabel);
		buttonPanel.add(fieldNumberComboBox);
		buttonPanel.add(composeTypeLabel);
		buttonPanel.add(composeTypeComboBox);
		buttonPanel.add(buttonCreate);

//		centerPanel.add(sep, BorderLayout.NORTH);
		centerPanel.add(buttonPanel, BorderLayout.CENTER);
//		centerPanel.add(sep1, BorderLayout.SOUTH);

        return centerPanel;
    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {
    	//宏观数据表格选择
        buttonMacroChoose.addActionListener(e -> {
            try {
                JFileChooser jfc = new JFileChooser();// 文件选择器
                jfc.setFileSelectionMode(0);
                jfc.setFileFilter(new FileNameExtensionFilter("表格(*.xlsx, *.xls)", "xlsx", "xls"));
                int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
                if (state == 1) {
                    return;// 撤销则返回
                } else {
                    File f = jfc.getSelectedFile();// f为选择到的文件
                    textFieldMacroDataChoose.setText(f.getAbsolutePath());
					excelHandler.saveMacroExcel(f);
                    this.macroDataField  = DataHandler.getMacroDataField();
					excelHandler.readMacroData();
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(App.casBasePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
                logger.error("Write to xml file error" + e1.toString());
				e1.printStackTrace();

			}

        });

		//坏账初始数据表格选择
		buttonWoratioInitChoose.addActionListener(e -> {
			try {
				JFileChooser jfc = new JFileChooser();// 文件选择器
				jfc.setFileSelectionMode(0);
				jfc.setFileFilter(new FileNameExtensionFilter("表格(*.xlsx, *.xls)", "xlsx", "xls"));
				int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
				if (state == 1) {
					return;// 撤销则返回
				} else {
					File f = jfc.getSelectedFile();// f为选择到的文件
					textFieldWoratioInitData.setText(f.getAbsolutePath());
					excelHandler.saveWoratioInitExcel(f);
					this.woratioInitField  =  dataHandler.getWoratioInitField();
					excelHandler.readWoratioInitData();
				}

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(App.casBasePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
						JOptionPane.ERROR_MESSAGE);
				logger.error("Write to xml file error" + e1.toString());
				e1.printStackTrace();
			}

		});

		//财务数据表格选择
		buttonfinancialDataChoose.addActionListener(e -> {
			try {
				JFileChooser jfc = new JFileChooser();// 文件选择器
				jfc.setFileSelectionMode(0);
				jfc.setFileFilter(new FileNameExtensionFilter("表格(*.xlsx, *.xls)", "xlsx", "xls"));
				int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
				if (state == 1) {
					return;// 撤销则返回
				} else {
					File f = jfc.getSelectedFile();// f为选择到的文件
					textFieldfinancialDataData.setText(f.getAbsolutePath());
					excelHandler.saveFinancialExcel(f);
					excelHandler.readMacroData();
				}

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(App.casBasePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
						JOptionPane.ERROR_MESSAGE);
				logger.error("Write to xml file error" + e1.toString());
				e1.printStackTrace();

			}

		});


		buttonCreate.addActionListener(e -> {
            try {
            	// 获取宏观数据
				List<ExcelRowBean> macroData = dataHandler.getMacroData();
				// 获取坏账初始数据

				// 获取财务数据

			} catch (Exception e1) {
                JOptionPane.showMessageDialog(App.casBasePanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(), PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
                logger.error("Write to xml file error" + e1.toString());
            }

        });
    }

	private void activeMacroDataField() {
		macroPanel.add(createChooseMacroPanel());
		macroPanel.updateUI();
	}

	private JPanel createChooseMacroPanel() {
		JPanel chooseMacroPanel = new JPanel();
		chooseMacroPanel.setLayout(new GridLayout(4,3));
		Set<String> strings = macroDataField.keySet();
		return chooseMacroPanel;
	}

}