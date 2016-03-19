package zzz.study.javagui.maze;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * 迷宫面板： 按钮事件的观察者
 */
public class MazePanel extends JPanel implements Observer {
	
	private String title;
	private String text;
	private JTextArea infoArea;
	
	public MazePanel(String title) {
		this(title, "");
	}
	
	public MazePanel(String title, String text) {
		this.title = title;
		this.text = text;	
		infoArea = new JTextArea(text);
		infoArea.setFont(new Font("Dialog",Font.PLAIN, 16));
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(title));
	    add(infoArea, BorderLayout.CENTER);
	    add(new JScrollPane(infoArea)); 
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void update(Observable o, Object arg) {
		Maze m = (Maze)o;
		text = "" + m + "\n" + m.getSolution(); 	
		infoArea.setText(text);
	}
	
	
	

}
