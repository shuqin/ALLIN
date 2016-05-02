package zzz.study.javagui.maze;

/*
 *  迷宫中的通道位置模型：
 *  row: 所处位置的行坐标 
 *  col: 所处位置的列坐标
 *  dir: 将要进行的搜索方向: 正东 1; 正南 2; 正西3;  正北  4;
 */

public class Position {
	
	private int   row;   
	private int   col;
	private byte  dir;
	
	public Position() {
	    row = 0;
	    col = 0;
	    dir = 0;
	}

	public Position(int row, int col, byte dir) {
		this.row = row;
		this.col = col;
		this.dir = dir;
	}

	public Position(int row, int col) {
		this(row, col, 0);
	}

	public Position(int row, int col, int dir) {
		this(row, col, (byte)dir);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public short getDir() {
		return dir;
	}
	
	public String toString() {
		String dirStr = "";
		switch (dir) {
		  case 1:  dirStr = "正东"; break;
		  case 2:  dirStr = "正南"; break;
		  case 3:  dirStr = "正西"; break;
		  case 4:  dirStr = "正北"; break;
		  default: dirStr = "UNKNOWN"; break;
		}
		return "(" + row + "," + col + "," + dirStr + ")";
	}
	
	public boolean equals(Object obj)
	{
		if (obj == this) { return true; }
		if (obj instanceof Position) {
			Position p = (Position) obj;
			if (p.row == this.row && p.col == this.col && p.dir == this.dir) {
				return true;
			}
		}
		return false;
	}
	
	public int hashCode()
	{
		int result = 17;
		result = result * 31 + row;
		result = result * 31 + col;
		result = result * 31 + dir;
		return result;
	}

}
