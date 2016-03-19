package zzz.study.patterns.mediator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Mediator {
	
	private Map<Student, Department> map;
	
	public Mediator() {
		if (map == null)
			map = new HashMap<Student, Department>();
	}
	
	public void put(Student s, Department d) {
		map.put(s, d);
	}
	
	public Department getDepartment(Student s) {
		
		if (map.containsKey(s)) {
			return (Department)map.get(s);
		}
		else
			return null;
	}
	
	public Set<Student> getStudents(Department d) {
		
		Set<Student> students = new HashSet<Student>();
		Set allStudents = map.keySet();
		Iterator iter = allStudents.iterator();
		while (iter.hasNext()) {
			Student s = (Student)iter.next();
			if ((map.get(s)).equals(d))
				students.add(s);
		}
		return students;
		
	}
	
	public String toString() {
		
		String  mapContent = "";
		Set all = map.keySet();
		Iterator it = all.iterator();
		while (it.hasNext()) {
			Student s = (Student) it.next();
			mapContent += s;
			mapContent += " ---> ";
			mapContent += map.get(s);
			mapContent += "\n";
		}
		return mapContent;
	}

}
