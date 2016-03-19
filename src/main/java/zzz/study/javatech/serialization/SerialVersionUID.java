/**
 * 演示 serialVersionUID 的作用
 */

package zzz.study.javatech.serialization;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SerialVersionUID {

    public static void main(String[] args) {
        System.out.println("---------- 测试 SerialVersionUID 在常规序列化中的作用 ----------");
        testGeneSerialization();
        System.out.println("---------- 测试 SerialVersionUID 在常规序列化中的作用 ----------");

        System.out.println("---------- 测试 SerialVersionUID 在XML序列化中的作用 ----------");
        testXMLSerialization();
        System.out.println("---------- 测试 SerialVersionUID 在XML序列化中的作用 ----------");
    }

    public static void testGeneSerialization() {

        GeneralSerialization gs = new GeneralSerialization();

//		geneSerialization(gs);
        // 运行上述代码生成序列化文件 point.bin 后，注释掉上述代码； 在 Point 类添加 字段 y 或不作改动 , 然后分别运行下面的代码 
        System.out.println("after modifying class Point, for instance, adding another field y.");
        // 添加字段 y 后读取原来的序列化文件 point.bin 会产生异常 InvalidClassException
        geneDeserialization(gs);
    }

    public static void testXMLSerialization() {
        XMLSerialization xmls = new XMLSerialization();

//	    xmlSerialization(xmls);
        // 运行上述代码生成序列化文件 point.xml 后，注释掉上述代码； 在 Point 类添加 字段 y 或不作改动 , 然后分别运行下面的代码 
        System.out.println("after modifying class Point, for instance, adding another field y.");
        // 添加字段 y 后仍然能够正常读取 point.xml， 这说明 XML 序列化不依赖于 serialVersionUID 来识别序列化文件.
        xmlDeserialization(xmls);
    }

    public static void geneSerialization(GeneralSerialization gs) {
        Point2 point = new Point2(8);
        System.out.println(" --------------- 序列化： 将对象读取到磁盘上  -------------  ");
        try {
            gs.serializeToDisk(point, "./src/javatech/serialization/point2.bin");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void geneDeserialization(GeneralSerialization gs) {
        System.out.println(" --------------- 反序列化： 从磁盘读取对象  -------------  ");
        Point2 point2;
        try {
            point2 = (Point2) gs.deSerizalize("./src/javatech/serialization/point2.bin");
            System.out.println(point2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void xmlSerialization(XMLSerialization xmls) {
        Point2 point = new Point2(8);

        System.out.println(" --------------- XML序列化： 将对象读取到磁盘上  -------------  ");
        try {
            xmls.serializeToDisk(point, "./src/javatech/serialization/point2.xml");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void xmlDeserialization(XMLSerialization xmls) {
        System.out.println(" --------------- XML反序列化： 从磁盘读取对象  -------------  ");
        Point2 point = null;
        try {
            point = (Point2) xmls.deSerizalize("./src/javatech/serialization/point2.xml");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(point);
    }

}
