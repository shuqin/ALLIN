package zzz.study.javatech.JDBClearn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FormattedLetter {
	
	/**
	 * 用数据库中每条记录的数据项依序替换模板中的模板数据。
	 * @param record 数据库中的一条记录
	 * @param tpl 指定模板文件接口
	 * @return 替换后的格式化信函
	 */
	public String formattedLetter(DBRecord record, Template tpl) 
	{
		String text = tpl.getText();
		for (int i=0; i < record.dataTermNum(); i++) 
		    text = text.replaceAll("\\$" + (i+1), record.getDataTerm(i));
		return text;
	}
	
	/**
	 * 批量生成格式化信函
	 */
	public List<String> batFormattedLetter(List<DBRecord> records, Template tpl)
	{
		List<String> letters = new ArrayList<String>();
		for (DBRecord r: records) {
			letters.add(formattedLetter(r, tpl));
		}
		return letters;
	}
	
	/**
	 * printList: 打印列表内容
	 */
	public static <T> void printList(List<T> list)
	{
		System.out.println("List: [");
		for (T t: list) {
			System.out.println(t);
		}
		System.out.println("]");
	}
	
	public static void test(String sqlStmt, Template tpl) throws SQLException, ClassNotFoundException
	{
		ResultSet rs = JDBCUtil.getResultSet(sqlStmt);
		List<DBRecord> records = JDBCUtil.toRecords(rs);
		List<String> letters = new FormattedLetter().batFormattedLetter(records , tpl);
		System.out.println("Formatted letters: ");
		printList(letters);
	}
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		String courseInfoSql = "SELECT cName, cTime FROM courses";
		test(courseInfoSql, new CourseInfoTemplate());
		
		String courseChosenString = "SELECT stuName, cName, cTime, sch_term " +
        				   "FROM students, courses, stus_cours " +
        				   "WHERE students.id = stu_id AND " +
        				   "      courses.id = cour_id; ";
		test(courseChosenString, new CourseChosenTemplate());	
	}
	
	

}
