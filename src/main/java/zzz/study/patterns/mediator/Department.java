package zzz.study.patterns.mediator;

import java.util.Set;

public class Department {

    private String id;
    private Mediator mediator;

    public Department(String id, Mediator mediator) {
        super();
        this.id = id;
        this.mediator = mediator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addStudent(Student s) {
        mediator.put(s, this);
    }

    public Set<Student> getStudents() {
        return mediator.getStudents(this);
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
        } else {
            return false;
        }
    }

    public String toString() {
        return "Department[" + id + "]";
    }


}
