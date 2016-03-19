package zzz.study.patterns.adapter.byobject;

import javax.swing.table.AbstractTableModel;

/**
 *  适配 JTable 提供的接口
 */
public class RocketTable extends AbstractTableModel {

	 private Rocket[] rockets;
	 private String[] columnNames = new String[] {
			 "火箭名称", "价格", "远地点"
	 };
	 
	 
	 
	 public RocketTable(Rocket[] rockets) {
		this.rockets = rockets;
	}

	public int getColumnCount() {
		 return columnNames.length;
	 }
	
	public String getColumnName(int i) {
		 return columnNames[i];
	}
	 
	 public int getRowCount() {
		 return rockets.length;
	 }
	 
	 public Object getValueAt(int row, int col) {
		 
		 switch (col) {
		   case 0: return rockets[row].getName();
		   case 1: return rockets[row].getPrice();
		   case 2: return rockets[row].getApogee();
		   default: return null;
		 }
	 }
}
