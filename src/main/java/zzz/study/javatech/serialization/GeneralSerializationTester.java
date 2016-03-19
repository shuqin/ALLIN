package zzz.study.javatech.serialization;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GeneralSerializationTester {

    public static void main(String[] args) {

        ColorPoint cp = new ColorPoint(3, 7);
        GeneralSerialization ps = new GeneralSerialization();
        try {

            System.out.println(" --------------- store object into disk  -------------  ");
            ps.serializeToDisk(cp, "point.bin");

            System.out.println(" --------------- restore object from disk  -------------  ");
            ColorPoint point = (ColorPoint) ps.deSerizalize("point.bin");
            System.out.println(point);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
