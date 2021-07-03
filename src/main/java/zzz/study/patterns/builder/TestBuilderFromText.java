package zzz.study.patterns.builder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestBuilderFromText {

    /**
     * 对预定申请请求进行解析，并将审查结果写入相应文件中
     */

    public static void unforgiving(List strlist) throws IOException {

        BufferedWriter fileWriter = RWTool.getFileWriter("./src/builder/unforgiving_output.txt");
        fileWriter.write("UnforgivingBuilder: \n");
        fileWriter.write("---------------------------" + "\n");


        int i = 1;
        Iterator iter = strlist.iterator();
        while (iter.hasNext()) {

            String sample = (String) iter.next();

            try {
                fileWriter.write("R[" + i + "] ");
                fileWriter.write(sample);
                fileWriter.write("\n");

                ReservationBuilder unforgivingbuilder = new UnforgivingBuilder();
                ReservationParser unforgivingparser = new ReservationParser(unforgivingbuilder);
                unforgivingparser.parser(sample);
                Reservation r = unforgivingbuilder.build();
                fileWriter.write(r.toString());

            } catch (ReservationException e) {
                fileWriter.write(e.getMsg());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                fileWriter.write("\n\n");
            }

            i++;
            if (iter.hasNext()) continue;
        }

        fileWriter.close();
    }

    /**
     * 对预定申请请求进行解析，并将审查结果写入相应文件中
     */

    public static void forgiving(List strlist) throws IOException {

        BufferedWriter fileWriter = RWTool.getFileWriter("./src/builder/forgiving_output.txt");
        fileWriter.write("forgivingBuilder: \n");
        fileWriter.write("---------------------------" + "\n");


        int i = 1;
        Iterator iter = strlist.iterator();
        while (iter.hasNext()) {

            String sample = (String) iter.next();

            try {
                fileWriter.write("R[" + i + "] ");
                fileWriter.write(sample);
                fileWriter.write("\n");

                ReservationBuilder forgivingbuilder = new ForgivingBuilder();
                ReservationParser forgivingparser = new ReservationParser(forgivingbuilder);
                forgivingparser.parser(sample);
                Reservation r = forgivingbuilder.build();
                fileWriter.write(r.toString());

            } catch (ReservationException e) {
                fileWriter.write(e.getMsg());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                fileWriter.write("\n\n");
            }

            i++;
            if (iter.hasNext()) continue;
        }

        fileWriter.close();
    }

    /**
     * 从文件 reservation.txt 中获取预定申请信息，并存入一个字符串数组中
     */

    public static ArrayList<String> getReservation() throws IOException {

        ArrayList<String> strlist = new ArrayList<String>();

        BufferedReader fileReader = RWTool.getFileReader("./src/builder/reservation.txt");
        Pattern p = Pattern.compile("^R\\[[0-9]{1,}\\][\\s:]");

        String s = null;
        while ((s = fileReader.readLine()) != null) {
            Matcher m = p.matcher(s);
            if (m.find()) {
                String strTemp = m.replaceAll("");
                strlist.add(strTemp);
            }
        }

        fileReader.close();

        return strlist;
    }


    public static void main(String[] args) {

        try {

            // 从文件 reservation.txt 中获取预定申请信息，并存入一个字符串数组中  

            ArrayList<String> strlist = TestBuilderFromText.getReservation();

            // 解析请求并输出到相应文件中

            TestBuilderFromText.unforgiving(strlist);
            TestBuilderFromText.forgiving(strlist);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {

        }


    }

}
