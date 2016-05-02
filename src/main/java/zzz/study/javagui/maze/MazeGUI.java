package zzz.study.javagui.maze;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;

/**
 * 迷宫程序的主界面
 * @author shuqin1984
 */
public class MazeGUI extends JFrame  {
	
	private JTextField inputRow;               // 用户输入行数
	private JTextField inputCol;               // 用户输入列数
	
	private JPanel mazePanel;                  // 显示迷宫的面板
	
	public MazeGUI() {
		super("程序演示：模拟走迷宫");
	}

	private final Font font = new Font("Monospaced",Font.PLAIN, 14);
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MazeGUI app = new MazeGUI();
				app.launch();
			}
		});
	}

	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys();
			 keys.hasMoreElements(); ) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}

	/**
	 * 启动应用程序
	 */
	public void launch()
	{	
		JFrame f = new MazeGUI();
		f.setBounds(100, 100, 800, 600);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		InitGlobalFont(font);

		Container contentPane = f.getContentPane();
		contentPane.setLayout(new BorderLayout());
			
		JPanel inputPanel = createInputPanel();	
		contentPane.add(inputPanel, BorderLayout.NORTH);	
		
		mazePanel = new MazePanel("显示迷宫和迷宫的解", font);
		contentPane.add(mazePanel, BorderLayout.CENTER);
	        		
		f.setContentPane(contentPane);
	}
	
	/**
	 * 创建并返回输入面板
	 */
    public JPanel createInputPanel() {	
		
		JPanel inputPanel = new JPanel(new FlowLayout());
		inputPanel.setBorder(new TitledBorder("用户输入信息提示"));
		
		JLabel labelInfo = new JLabel("请输入迷宫大小：",null,SwingConstants.LEFT);

		JLabel labelRow = new JLabel("行");
		JLabel labelCol = new JLabel("列");
		JLabel labelSpace = new JLabel("  ");
		if (inputRow == null)
			inputRow = new JTextField(3);	
		if (inputCol == null)
			inputCol = new JTextField(3);
		
		inputPanel.add(labelInfo);
		inputPanel.add(inputRow);
		inputPanel.add(labelRow);
		inputPanel.add(inputCol);
		inputPanel.add(labelCol);
		inputPanel.add(labelSpace);	
		
		JButton button = new JButton("生成迷宫");
		inputPanel.add(button);
		
		Model m = new Model();
		m.setGUI(this);
		button.addActionListener(m);
		
		return inputPanel;

	}


	public JTextField getInputRow() {
		return inputRow;
	}


	public JTextField getInputCol() {
		return inputCol;
	}


	public JPanel getMazePanel() {
		return mazePanel;
	}
    
}
