package zzz.study.patterns.adapter.byclass;


public class NewClass extends ExistingClass
        implements RequiredInterface {

    public void requiredMethod() {
        usefulMethod();
    }
}
