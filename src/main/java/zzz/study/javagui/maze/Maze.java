package zzz.study.javagui.maze;

import java.util.Observable;
import java.util.Random;

public class Maze extends Observable {

	// 定义迷宫大小：行数 rows 和列数 cols 
	private int rows;
	private int cols;
	
	// 定义迷宫出口点位置： 行坐标 EXIT_ROW 和 列坐标 EXIT_COL
	private final int EXIT_ROW;
	private final int EXIT_COL;
	
	// 定义迷宫矩阵mazeMatrix 和 标记数组 mark
	private boolean[][] mazeMatrix;   // true: 可通行；  false: 不可通行
	private short[][] mark;
	
	private String mazeStr = "";      // 迷宫的字符串表示    
	private String solution = "";     // 迷宫的解的字符串表示
	
	// 定义移动方向表 
	private byte[][] move = {
			{0,1},        // 正东 , move[0]  方向一
            {1,0},        // 正南 , move[1]  方向二
            {0,-1},       // 正西 , move[2]  方向三
            {-1,0},       // 正北 , move[3]  方向四
	};
	
	private DyStack<Position> stack;    // 使用栈存放迷宫通路路径
	
	private Random rand = new Random();
	
	public Maze(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		EXIT_ROW = rows-1;
		EXIT_COL = cols-1;
		mazeMatrix = new boolean[rows][cols];   
		createMaze();
	}
	
	/**
	 * 迷宫求解：求解迷宫并设置解的表示
	 */
	public void solve()
	{
		if (hasPath()) {
			setSolutionStr();
		}
		else {
			noSolution();
		}
	}
	
	/**
	 *  迷宫矩阵的字符串表示
	 *  
	 */
    public String toString() { 
		
		StringBuilder mazeBuf = new StringBuilder("\n");
		String mazeCell = "";
		for (int i = 0; i < rows; i++) {
			   mazeBuf.append(indent(10));
		       mazeBuf.append('|');
		       for (int j = 0; j < cols; j++) {
		    	    mazeCell = String.format("%3s", (mazeMatrix[i][j] == true ? "": "[ ]"));
		            mazeBuf.append(mazeCell);
		       } 
		       mazeBuf.append("|\n");
	    }  
		mazeStr = mazeBuf.toString();
		return mazeStr;
	}
	
    
    /**
     * 监听按钮事件后发生改变，并通知观察者此变化的发生
     */
	public void change()
	{
		setChanged();
		notifyObservers();
	}

	public String getSolution() {
		return solution;
	}
	
	/**
	 * 生成迷宫， 采用简单的随机赋值法(无解的可能性高)
	 */
	private void createMaze()
	{
		 for (int i = 0; i < rows; i++) 
		       for (int j = 0; j < cols; j++) {
		            mazeMatrix[i][j] = rand.nextBoolean();
		       }
	     mazeMatrix[0][0] = true;
	     mazeMatrix[EXIT_ROW][EXIT_COL] = true;   
	}
	
	/* 
	 * 生成迷宫， 采用 Recursive Backtracking. Refer to: 
	 * <a href="http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking"/>
	 */
	private void createMaze2()
	{	   
		// unfinished.  
	}

	// 从 (i,j) 选择一个邻接的坐标
	private Position adjacent(Position p)
	{
		
		return null;
	}
	


	private boolean  checkBound(int row, int col)
	{
	     if ((row < rows && row > -1) && (col < cols && col > -1))
	         return true;
	     else
	         return false;
	}
	
    
	private boolean hasPath()
	{
	      int row=0, col=0, dir=0;           // 当前位置的行列位置坐标及搜索移动方向 
	      int nextRow, nextCol;              // 下一步移动要到达的位置坐标 
	      boolean found = false;
	      Position position = new Position(0,0,(byte)0);       // 通道的临时存放点
	      stack = new DyStack<Position>(rows+cols);            // 创建指定容量的空栈
	      mark = new short[rows][cols];
	      mark[0][0] = 1;
	      stack.push(position);
	      
	      while (!stack.isEmpty() && !found) {
		     try {  
		          position = stack.pop();                       // 如四个搜索方向的相邻位置都无通道，则出栈并退回到最近一次经过的位置  
		    	  row = position.getRow(); col = position.getCol();
		          dir = position.getDir();
		          while (dir < 4 && !found) {
		              nextRow = row + move[dir][0];             // 取得下一个可能到达的相邻位置             
		              nextCol = col + move[dir][1];

		              if (nextRow == EXIT_ROW && nextCol == EXIT_COL) {   // 找到出口点，即存在通路径 
		                 found = true;
		                 position = new Position(row,col,(byte)++dir);
		                 stack.push(position);
		                 position = new Position(EXIT_ROW,EXIT_COL,(byte)1);
		                 stack.push(position);
		              }   
		              else if (checkBound(nextRow, nextCol) && mazeMatrix[nextRow][nextCol]==true && mark[nextRow][nextCol]==0) { 
		                   // 没有找到出口点，但当前搜索方向的相邻位置为通道，则前进到相邻位置，并在相邻位置依序按照前述四个方向进行搜索移动  
		            	  mark[nextRow][nextCol] = 1;
		                  position = new Position(row,col,(byte)++dir);
		                  stack.push(position);
		                  row = nextRow; col = nextCol;
		                  dir = 0;
		              }
		              else { 
		            	  /* 没有找到出口点，且当前搜索方向的相邻位置为墙，或已搜索过，或超出迷宫边界，
		            	   * 则向当前位置的下一个搜索方向进行搜索移动  	 */
		            	   ++dir;  
		              }          
		           }   
		    }
		 	catch(Exception e) {
		 		System.out.print("栈空！");
		 		e.printStackTrace();
		 	}  
	      } 
          
	      if (found) 
	    	  return true;
	      else 
	    	  return false;      
	      
	}
	
	private void setSolutionStr()   
	{
		solution = "\n所找到的通路路径为: \n" + stack + "\n\n";	
		solution += "其中，(x,y,z)表示从坐标点(x,y)向z方向移动\n\n"; 
	}
	
	private void noSolution() {  // 迷宫无解的字符串表示
	     solution = "迷宫无解！\n";
	}
	
	// 显示迷宫时，为美观起见， 缩进 n 个字符
    private String indent(int n)
    {
    	StringBuilder indentBuf = new StringBuilder();
    	while (n-- > 0) {
    		indentBuf.append(' ');
    	}
    	return indentBuf.toString();
    }
	
}
