package zzz.study.javatech.serialization;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class XMLSerialization {

    public void serializeToDisk(Object obj, String file) throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file));
        encoder.writeObject(obj);
        encoder.close();
    }

    public Object deSerizalize(String file) throws FileNotFoundException {
        XMLDecoder decoder = new XMLDecoder(new FileInputStream(file));
        Object obj = decoder.readObject();
        decoder.close();
        return obj;

    }

}

/*
 * Note:
 *
 * XML Java Bean 序列化：
 *
 * 关键类： java.beans.XMLEncoder , java.beans.XMLDecoder
 *
 * 适用场合：
 * ①  更佳地序列化 Java bean 组件 和 swing 组件；
 * ②  输出结果是可读性比较好的 XML 文件格式，可供开发人员阅读和编辑。
 * ③  可以修改数据成员的内部结构的实现，只要保留原有bean属性及setter/getter签名，
 *    就不影响序列化和反序列化；可以解决版本兼容的问题，提供长期持久化的能力。
 *
 * 不足：
 * ①    输出的 XML 格式仍然特定于Java 应用程序，序列化数据 可以但不容易为其它非Java应用程序解析和读取。
 * ②   每个需要持久化的数据成员都必须有一个 Java bean 属性。
 * ③    一般用户可能难以读懂输出结果，主要供开发人员和高级用户使用。
 *
 * 版本兼容的意思是： 原有序列化 API 序列化的实例无法被新的序列化API所访问，这就导致了原有序列化实例失效。
 *
 *
 */
