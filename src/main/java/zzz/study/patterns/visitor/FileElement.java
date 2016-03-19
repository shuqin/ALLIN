package zzz.study.patterns.visitor;

import java.io.File;

public class FileElement implements Element {
	
	private File file;
	
	public FileElement(File file) {
		this.file = file;
	}

	public void accept(Visitor v) {
		
		v.visitFile(file);
	}


}
