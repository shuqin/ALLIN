package zzz.study.patterns.mediator;

public class Student {
	
	private String id;
	private Mediator mediator;
	
	public Student(String id, Mediator mediator) {
		this.id = id;
		this.mediator = mediator;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void  setDepartment(Department d) {
		mediator.put(this, d);
	}
	
	public Department getDepartment() {
		return mediator.getDepartment(this);
	}
	
	public String toString() {
		return "Student[" + id + "]"; 
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		
		if (obj.getClass() != Student.class) {
			return false;
		}	

		if (this.equals(obj)) {
			return true;
		}
		else {
			return false;
		}

		
	}
	

}
