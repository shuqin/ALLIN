package zzz.study.javatech.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GeneralSerialization {

	public void serializeToDisk(Object obj, String file) throws FileNotFoundException, IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(obj);
		out.close();
	}
	
	public Object deSerizalize(String file) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Object obj = in.readObject();
		in.close();
		return obj;
		
	}
}

/*
 * Note:
 * 
 * Java 对象序列化
 * 
 * 关键类： java.io.ObjectInputStream , java.io.ObjectOutputStream
 * 
 * 适用场合： 适用于任何 Java 对象图的序列化，通用的序列化方案。
 * 
 * 不足： 
 * ①   输出为可读性差的二进制文件格式，很难为人所读懂，无法进行编辑；
 * ②   对某些依赖于底层实现的组件，存在版本兼容和可移植性问题。
 * ③  只有基于 Java 的应用程序可以访问序列化数据。
 * 
 * 
 */
