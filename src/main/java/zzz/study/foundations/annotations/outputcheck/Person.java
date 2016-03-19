package zzz.study.foundations.annotations.outputcheck;

import java.util.Date;

import zzz.study.utils.DateUtil;

public class Person {
	
	@OutputCheck
	protected final String idcard;
	
	@OutputCheck
	protected String name;
	
	@OutputCheck
	protected final Date   birthday;
	
	@OutputCheck
	protected String type;
	
	private String mobile;
	private String email;
	
	public Person(String idcard, String name, Date birthday, String type) {
		 this.idcard = idcard;
		 this.name = name;
		 this.birthday = birthday;
		 this.type = type;
	}

	public String getIdcard() {
		return idcard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBirthString(String pattern) {
		return DateUtil.toFormattedDate(birthday, pattern);
	}
	
	public String getBirthString() {
		return DateUtil.toFormattedDate(birthday, "yyyy-MM-dd");
	}
	
	public String toString()
	{	
		// 忘记为新添加的必要字段[type]更新输出，造成不同步
		return "Person: \n" + "idcard: " + idcard + "\t" 
		                  + "name: "   + name   + "\t"
		                  + "birthday: " + getBirthString()  + "\t";
	}

}
