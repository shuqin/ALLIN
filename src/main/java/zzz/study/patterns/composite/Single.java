package zzz.study.patterns.composite;

public class Single implements Element {
	
	private String id;
	
	public Single(String id) {
		this.id = id;
	}
	
	public String getElementId() {
		return this.id;
	}
	
	public int getCount() {
		return 1;
	}

}
