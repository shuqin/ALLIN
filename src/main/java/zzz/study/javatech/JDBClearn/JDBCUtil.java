package zzz.study.javatech.JDBClearn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

public class JDBCUtil {
	
	private static Connection conn;
	
	private static final String mysqlDriver = "com.mysql.jdbc.Driver";
	private static final String mysqlURL = "jdbc:mysql://localhost:3306/course";
	private static final String username = "course6";
	private static final String password = "course6";
	
	private JDBCUtil() { }
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {	
		
		Class.forName(mysqlDriver);
		conn = DriverManager.getConnection(mysqlURL, username, password);
		return conn;		
	}
	
	public static void printResult(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		int colNum = rsmd.getColumnCount();
		
		while(rs.next()) {
			for (int i = 0; i < colNum; i++) 
                System.out.printf(rs.getString(i+1) + " ");
			System.out.println();
		}
	}
	
	public static List<DBRecord> toRecords(ResultSet rs) throws SQLException 
	{
		List<DBRecord> records = new ArrayList<DBRecord>();
		
		ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
		int colNum = rsmd.getColumnCount();
		
		while(rs.next()) {
			String[] datas = new String[colNum];	
			for (int i = 0; i < colNum; i++) 
                datas[i] = rs.getString(i+1);
			records.add(new DBRecord(datas));
		}
		rs.close();
		conn.close();
		return records;
	}
	
	public static ResultSet getResultSet(String sqlString) throws SQLException, ClassNotFoundException
	{
		Connection conn = getConnection();
		Statement stm = (Statement) conn.createStatement();		
		ResultSet rs = stm.executeQuery(sqlString);
		return rs;
	}
	
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		String sqlString = "SELECT stuName, cName, cTime, sch_term " +
                           "FROM students, courses, stus_cours " +
                           "WHERE students.id = stu_id AND " +
                           "      courses.id = cour_id; ";
		ResultSet rs = getResultSet(sqlString);
		printResult(rs);
		rs.close();
		conn.close();
	}


}
