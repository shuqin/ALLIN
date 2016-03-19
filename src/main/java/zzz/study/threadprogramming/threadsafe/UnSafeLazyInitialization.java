package zzz.study.threadprogramming.threadsafe;

public class UnSafeLazyInitialization {
	
	    private ExpensiveObject instance = null;

	    public ExpensiveObject getInstance() {
	        if (instance == null)
	            instance = new ExpensiveObject();
	        return instance;
	    }


}

class ExpensiveObject {
	
}
