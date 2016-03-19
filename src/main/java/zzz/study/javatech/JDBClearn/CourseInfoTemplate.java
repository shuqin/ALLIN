package zzz.study.javatech.JDBClearn;

public class CourseInfoTemplate implements Template {
	
	private String text = "同学们请注意： 本学期开设的课程有 $1, 共 $2 学时，请勿错过!";
	
	public String getText()
	{
		return text;
	}

}
