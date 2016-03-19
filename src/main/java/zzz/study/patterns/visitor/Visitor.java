package zzz.study.patterns.visitor;

import java.io.File;

public interface Visitor {
	
	void visitFile(File file);
	void visitDir(File dir);

}
