package zzz.study.javagui.maze;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Maze extends Observable {

    // 存放所有方向, 使用该集合与某个位置已尝试方向的差集来获取其未尝试的方向
    private static final Set<Byte> allDirs = new HashSet<Byte>(Arrays.asList(new Byte[]{0, (byte) 1, (byte) 2, (byte) 3}));
    // 抵达迷宫最上边界时, 仅允许往东或往南走
    private static final byte[] firstRowAllowedDirs = new byte[]{(byte) 0, (byte) 1};
    // 抵达迷宫最下边界时, 仅允许往东或往北走
    private static final byte[] lastRowAllowedDirs = new byte[]{(byte) 0, (byte) 3};
    // 抵达迷宫最左边界时, 仅允许往东或往南走
    private static final byte[] firstColAllowedDirs = new byte[]{(byte) 0, (byte) 1};
    // 抵达迷宫最右边界时, 仅允许往南或往西走
    private static final byte[] lastColAllowedDirs = new byte[]{(byte) 1, (byte) 2};
    // 各方向出现的概率设置,
    private static final int P0 = 36;
    private static final int P1 = 36;
    private static final int P2 = 14;
    private static final int P3 = 14;
    // 定义迷宫大小：行数 rows 和列数 cols
    private final int rows;
    private final int cols;
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
            {0, 1},        // 正东 , move[0]  方向一
            {1, 0},        // 正南 , move[1]  方向二
            {0, -1},       // 正西 , move[2]  方向三
            {-1, 0},       // 正北 , move[3]  方向四
    };
    private DyStack<Position> stack;    // 使用栈存放迷宫通路路径
    private boolean isCreatedFinished;   // 迷宫是否创建完成
    private Random rand = new Random();

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        EXIT_ROW = rows - 1;
        EXIT_COL = cols - 1;
        mazeMatrix = new boolean[rows][cols];
        mark = new short[rows][cols];
    }

    /*
     * divide [1:rows-1] into n sectors
     */
    private static List<Integer> divideN(int rows, int n) {
        int each = rows / n;
        int start = 0;
        List<Integer> divs = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            divs.add(start + i * each);
        }
        divs.add(rows - 1);
        return divs;
    }

    private static List<int[][]> divideMaze(int rows, int cols, int n) {
        List<Integer> nrowParts = divideN(rows, n);
        List<Integer> ncolParts = divideN(cols, n);
        System.out.println("nrowParts: " + nrowParts);
        List<int[][]> results = new ArrayList<int[][]>();
        int rowsize = nrowParts.size();
        int colsize = ncolParts.size();
        for (int i = 0; i < rowsize - 1; i++) {
            for (int j = 0; j < colsize - 1; j++) {
                int[][] smallParts = new int[2][2];
                int startRow = nrowParts.get(i);
                int exitRow = (i == rowsize - 2) ? (nrowParts.get(i + 1)) : (nrowParts.get(i + 1) - 1);
                int startCol = ncolParts.get(j);
                int exitCol = (j == colsize - 2) ? (ncolParts.get(j + 1)) : (ncolParts.get(j + 1) - 1);
                smallParts[0][0] = startRow;
                smallParts[0][1] = startCol;
                smallParts[1][0] = exitRow;
                smallParts[1][1] = exitCol;
                System.out.println("div: " + startRow + " " + startCol + " " + exitRow + " " + exitCol);
                results.add(smallParts);
            }
        }
        return results;
    }

    private static List<Byte> createNnums(byte b, int num) {
        List<Byte> occurs = new ArrayList<Byte>();
        for (int i = 0; i < num; i++) {
            occurs.add(b);
        }
        return occurs;
    }

    /**
     * 迷宫求解：求解迷宫并设置解的表示
     */
    public void solve() {
        if (hasPath()) {
            setSolutionStr();
        } else {
            noSolution();
        }
    }

    /**
     * 迷宫矩阵的字符串表示
     */
    public String toString() {

        StringBuilder mazeBuf = new StringBuilder("\n");
        String mazeCell = "";
        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                mazeBuf.append("Entrance => ");
            } else {
                // the width of "Entrance => " is Equal to the width of 20 spaces.
                mazeBuf.append(indent(20));
            }
            mazeBuf.append('|');
            for (int j = 0; j < cols; j++) {
                if (mazeMatrix[i][j] == false) {
                    mazeCell = String.format("%4s", "IIII");
                } else {  // 存在通路
                    if (mark[i][j] == 1) {
                        mazeCell = String.format("%2s", "GG");
                    } else {
                        mazeCell = String.format("%5s", "");
                    }
                }
                mazeBuf.append(mazeCell);
            }
            if (i == rows - 1) {
                mazeBuf.append("| => Exit\n");
            } else {
                mazeBuf.append("|\n");
            }
        }
        mazeStr = mazeBuf.toString();
        return mazeStr;
    }

    /**
     * 监听按钮事件后发生改变，并通知观察者此变化的发生
     */
    public void change() {
        setChanged();
        notifyObservers();
    }

    public String getSolution() {
        return solution;
    }

    public boolean isCreatedFinished() {
        return isCreatedFinished;
    }

    /**
     * 将迷宫还原为初始状态
     */
    public void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mazeMatrix[i][j] = false;
            }
        }
        isCreatedFinished = false;
    }

    public void createMaze() {
        for (int i = 0; i <= EXIT_ROW; i++) {
            for (int j = 0; j <= EXIT_COL; j++) {   // 初始无通路
                mazeMatrix[i][j] = false;
            }
        }
        if (rows < 10 && cols < 10) {
            StackADT<Position> createPaths = new DyStack<Position>(rows + cols);
            createMaze(0, 0, EXIT_ROW, EXIT_COL, createPaths);
            isCreatedFinished = true;
            change();
        } else {
            StackADT<Position> createPaths = new DyStack<Position>(rows + cols);

            List<int[][]> smallParts = divideMaze(rows, cols, 4);
            for (int[][] parts : smallParts) {
                createMaze(parts[0][0], parts[0][1], parts[1][0], parts[1][1], createPaths);
                if (parts[0][1] != 0) {
                    // 衔接点打通, 保证总是有一条从入口到出口的通路
                    mazeMatrix[parts[0][0]][parts[0][1] - 1] = true;
                }
            }


            isCreatedFinished = true;
            change();
        }
    }

    /*
     * 生成迷宫， 采用 Recursive Backtracking. Refer to:
     * <a href="http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking"/>
     */
    public void createMaze(int startRow, int startCol, int exitRow, int exitCol, StackADT<Position> stackForCreate) {

        mazeMatrix[startRow][startCol] = true;

        int currRow = startRow;
        int currCol = startCol;
        byte nextdir = 0;                  // 当前可能选择的方向
        int nextRow = currRow;             // 下一个可能到达的相邻位置
        int nextCol = currCol;

        // 每个位置已经尝试过的方向，用于回溯时确定有效的下一个方向
        Map<Position, Set<Byte>> triedPaths = new HashMap<Position, Set<Byte>>();

        List<Byte> allDirsWalked = new ArrayList<Byte>();

        while (currRow != exitRow || currCol != exitCol) {

            do {
                nextdir = getNextRandDir(currRow, currCol, startRow, startCol, exitRow, exitCol, triedPaths);
                System.out.println("nextdir: " + nextdir);
                allDirsWalked.add(nextdir);
                while (nextdir == -1) {
                    Position p = stackForCreate.pop();
                    currRow = p.getRow();
                    currCol = p.getCol();
                    nextdir = getNextRandDir(currRow, currCol, startRow, startCol, exitRow, exitCol, triedPaths);
                    allDirsWalked.add(nextdir);
                    System.out.println("Back to: " + p);
                }

                nextRow = currRow + move[nextdir][0];             // 取得下一个可能到达的相邻位置
                nextCol = currCol + move[nextdir][1];
                addTriedPaths(currRow, currCol, nextdir, triedPaths);

                System.out.println(currRow + " " + currCol + " " + nextdir + " " + nextRow + " " + nextCol);

            } while (!checkBound(nextRow, nextCol, startRow, startCol, exitRow, exitCol));

            // 已尝试过的路径, 分两种情况： 所有方向都尝试过或仍有方向没有尝试过
            // 如果所有方向都尝试过, 那么需要回退到上一个位置再尝试
            if (mazeMatrix[nextRow][nextCol]) {
                if (hasAllPathTried(currRow, currCol, triedPaths)) {
                    Position p = stackForCreate.pop();
                    currRow = p.getRow();
                    currCol = p.getCol();
                    System.out.println("Back to: " + p);
                }
                continue;
            }

            mazeMatrix[nextRow][nextCol] = true;
            stackForCreate.push(new Position(currRow, currCol, nextdir));
            currRow = nextRow;
            currCol = nextCol;

            // 更新 UI 界面, 显示迷宫当前状态
            try {
                change();
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException ie) {
                System.err.println("pause between maze-creating steps interrupted");
            }
        }

        mazeMatrix[exitRow][exitCol] = true;
        statDirWalked(allDirsWalked);
    }

    /*
     * 当前位置的所有方向是否都已经尝试过
     */
    private boolean hasAllPathTried(int currRow, int currCol, Map<Position, Set<Byte>> triedPaths) {
        Set<Byte> triedDirs = triedPaths.get(new Position(currRow, currCol));
        if (triedDirs == null) {
            triedDirs = new HashSet<Byte>();
        }
        Set<Byte> allDirsCopy = new HashSet<Byte>(allDirs);
        allDirsCopy.removeAll(triedDirs);
        return allDirsCopy.isEmpty();
    }

    /*
     * 记录当前位置已经尝试过的方向, 避免后续走重复路子
     */
    private void addTriedPaths(int currRow, int currCol, byte nextdir, Map<Position, Set<Byte>> triedPaths) {
        Position currPos = new Position(currRow, currCol);
        Set<Byte> triedDirs = triedPaths.get(currPos);
        if (triedDirs == null) {
            triedDirs = new HashSet<Byte>();
        }
        triedDirs.add(nextdir);
        triedPaths.put(currPos, triedDirs);
    }

    /*
     * 获取下一个随机的方向, [0,1,2,3] , 若均已尝试, 返回 -1
     */
    private byte getNextRandDir(int currRow, int currCol,
                                int startRow, int startCol,
                                int exitRow, int exitCOl,
                                Map<Position, Set<Byte>> triedPaths) {
        Set<Byte> triedDirs = (Set<Byte>) triedPaths.get(new Position(currRow, currCol));
        if (triedDirs == null) {
            triedDirs = new HashSet<Byte>();
        }

        // 如果抵达迷宫边界, 则优先向出口方向走, 避免回走会形成环路, 破坏所有的墙
        if (reachUpBound(currRow, startRow, exitRow)) {
            if (triedDirs.contains(firstRowAllowedDirs[0]) && triedDirs.contains(firstRowAllowedDirs[1])) {
                return -1;
            }
            return firstRowAllowedDirs[rand.nextInt(2)];
        }

        if (reachLowBound(currRow, startRow, exitRow)) {
            if (triedDirs.contains(lastRowAllowedDirs[0]) && triedDirs.contains(lastRowAllowedDirs[1])) {
                return -1;
            }
            return lastRowAllowedDirs[rand.nextInt(2)];
        }

        if (reachLeftBound(currCol, startCol, exitCOl)) {
            if (triedDirs.contains(firstColAllowedDirs[0]) && triedDirs.contains(firstColAllowedDirs[1])) {
                return -1;
            }
            return firstColAllowedDirs[rand.nextInt(2)];
        }

        if (reachRightBound(currCol, startCol, exitCOl)) {
            if (triedDirs.contains(lastColAllowedDirs[0]) && triedDirs.contains(lastColAllowedDirs[1])) {
                return -1;
            }
            return lastColAllowedDirs[rand.nextInt(2)];
        }

        Set<Byte> allDirsCopy = new HashSet<Byte>(allDirs);
        allDirsCopy.removeAll(triedDirs);
        List<Byte> possibleDirs = getRandomDirs(allDirsCopy);
        Byte[] nonTriedDirs = possibleDirs.toArray(new Byte[possibleDirs.size()]);
        if (nonTriedDirs.length == 0) {
            return -1;
        } else {
            byte nextdir = nonTriedDirs[rand.nextInt(nonTriedDirs.length)];
            return nextdir;
        }
    }

    /*
     * 抵达迷宫上边界
     */
    private boolean reachUpBound(int currRow, int startRow, int exitRow) {
        if (startRow < exitRow) {
            return currRow == startRow;
        } else {
            return currRow == exitRow;
        }
    }

    /*
     * 抵达迷宫下边界
     */
    private boolean reachLowBound(int currRow, int startRow, int exitRow) {
        if (startRow > exitRow) {
            return currRow == startRow;
        } else {
            return currRow == exitRow;
        }
    }

    /*
     * 抵达迷宫左边界
     */
    private boolean reachLeftBound(int currCol, int startCol, int exitCol) {
        if (startCol < exitCol) {
            return currCol == startCol;
        } else {
            return currCol == exitCol;
        }
    }

    /*
     * 抵达迷宫右边界
     */
    private boolean reachRightBound(int currCol, int startCol, int exitCol) {
        if (startCol > exitCol) {
            return currCol == startCol;
        } else {
            return currCol == exitCol;
        }
    }

    /*
     * 统计随机选择的方向出现的比例
     */
    private void statDirWalked(List<Byte> allDirWalked) {
        int[] counts = new int[4];
        int backCount = 0;
        for (Byte b : allDirWalked) {
            if (b != -1) {
                counts[b] += 1;
            } else {
                backCount++;
            }
        }
        int total = allDirWalked.size();
        for (int i = 0; i < counts.length; i++) {
            System.out.printf("P[%d]=%g ", i, (double) counts[i] / total);
        }
        System.out.println("back count: " + backCount);
        System.out.println(allDirWalked);
    }

    /*
     * 扩展 nonTriedDirs 使得 0 (向前) , 1 (向下)　出现的概率更大一些, 减少回退的几率
     */
    private List<Byte> getRandomDirs(Set<Byte> nonTriedDirs) {

        List<Byte> selectDirs = new ArrayList<Byte>();
        if (nonTriedDirs.contains((byte) 0)) {
            selectDirs.addAll(createNnums((byte) 0, P0));
        }
        if (nonTriedDirs.contains((byte) 1)) {
            selectDirs.addAll(createNnums((byte) 1, P1));
        }
        if (nonTriedDirs.contains((byte) 2)) {
            selectDirs.addAll(createNnums((byte) 2, P2));
        }
        if (nonTriedDirs.contains((byte) 3)) {
            selectDirs.addAll(createNnums((byte) 3, P3));
        }
        return selectDirs;
    }

    private boolean checkBound(int row, int col,
                               int startRow, int startCol,
                               int exitRow, int exitCol) {
        boolean rowBound = false;
        if (startRow < exitRow) {
            rowBound = (row <= exitRow && row >= startRow);
        } else {
            rowBound = (row <= startRow && row >= exitRow);
        }
        boolean colBound = false;
        if (startCol < exitCol) {
            colBound = (col <= exitCol && col >= startCol);
        } else {
            colBound = (col <= startCol && col >= exitCol);
        }

        return rowBound && colBound;
    }

    /*
     * 求解迷宫路径
     */
    private boolean hasPath() {
        int row = 0, col = 0, dir = 0;           // 当前位置的行列位置坐标及搜索移动方向
        int nextRow, nextCol;              // 下一步移动要到达的位置坐标
        boolean found = false;
        Position position = new Position(0, 0, (byte) 0);       // 通道的临时存放点
        stack = new DyStack<Position>(rows + cols);            // 创建指定容量的空栈
        mark[0][0] = 1;
        stack.push(position);

        while (!stack.isEmpty() && !found) {
            try {
                position = stack.pop();                       // 如四个搜索方向的相邻位置都无通道，则出栈并退回到最近一次经过的位置
                row = position.getRow();
                col = position.getCol();
                dir = position.getDir();
                while (dir < 4 && !found) {
                    nextRow = row + move[dir][0];             // 取得下一个可能到达的相邻位置
                    nextCol = col + move[dir][1];

                    if (nextRow == EXIT_ROW && nextCol == EXIT_COL) {   // 找到出口点，即存在通路径
                        found = true;
                        position = new Position(row, col, (byte) ++dir);
                        stack.push(position);
                        position = new Position(EXIT_ROW, EXIT_COL, (byte) 1);
                        stack.push(position);
                    } else if (checkBound(nextRow, nextCol, 0, 0, EXIT_ROW, EXIT_COL)
                            && mazeMatrix[nextRow][nextCol] == true && mark[nextRow][nextCol] == 0) {
                        // 没有找到出口点，但当前搜索方向的相邻位置为通道，则前进到相邻位置，并在相邻位置依序按照前述四个方向进行搜索移动
                        mark[nextRow][nextCol] = 1;
                        position = new Position(row, col, (byte) ++dir);
                        stack.push(position);
                        row = nextRow;
                        col = nextCol;
                        dir = 0;
                    } else {
                        /* 没有找到出口点，且当前搜索方向的相邻位置为墙，或已搜索过，或超出迷宫边界，
                         * 则向当前位置的下一个搜索方向进行搜索移动  	 */
                        ++dir;
                    }
                }
            } catch (Exception e) {
                System.out.print("栈空！");
                e.printStackTrace();
            }
        }
        mark[EXIT_ROW][EXIT_COL] = 1;

        if (found)
            return true;
        else
            return false;

    }

    private void setSolutionStr() {
        solution = "\n所找到的通路路径为: \n" + stack + "\n\n";
        solution += "其中，(x,y,z)表示从坐标点(x,y)向z方向移动\n\n";
    }

    private void noSolution() {  // 迷宫无解的字符串表示
        solution = "迷宫无解！\n";
    }

    /*
     * 显示迷宫时，为美观起见， 缩进 n 个字符
     */
    private String indent(int n) {
        StringBuilder indentBuf = new StringBuilder();
        while (n-- > 0) {
            indentBuf.append(' ');
        }
        return indentBuf.toString();
    }

}

