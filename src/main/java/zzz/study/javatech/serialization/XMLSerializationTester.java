package zzz.study.javatech.serialization;

import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLSerializationTester {

    public static void main(String[] args) {

//		ColorPointBean cpbean = new ColorPointBean(0,0);
        ColorPointBean cpbean = new ColorPointBean(7, 7);
//		ColorPointBean cpbean = new ColorPointBean();
//		cpbean.setX(5);
//		cpbean.setY(5);
//		cpbean.setColor(Color.gray);
        XMLSerialization xmls = new XMLSerialization();
        try {

            System.out.println(" --------------- store object into disk  -------------  ");
            xmls.serializeToDisk(cpbean, "point.xml");

            System.out.println(" --------------- restore object from disk  -------------  ");
            ColorPointBean point = (ColorPointBean) xmls.deSerizalize("point.xml");
            System.out.println(point);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
