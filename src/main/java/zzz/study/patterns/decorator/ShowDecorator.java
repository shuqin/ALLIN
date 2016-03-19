package zzz.study.patterns.decorator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Java IO 库是装饰器模式的典型应用
 *
 */
public class ShowDecorator {
	
	public static void main(String[] args) 
	     throws IOException {
		  
		FileWriter file = new FileWriter("sample.txt");
		BufferedWriter writer = new BufferedWriter(file);
		writer.write("I am an Excellent boy.");
		writer.newLine();
		writer.close();
	}

}
