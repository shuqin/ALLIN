package zzz.study.foundations.iolearn;

import java.io.*;

import static zzz.study.foundations.iolearn.RWTool.*;

public class RWToolTest {

    private RWToolTest() {
    }

    /*
     * ���Դӱ�׼�����ж�ȡ���
     *
     */
    public static void testReadFromStdin() throws IOException {

        String s = "";
        BufferedReader stdin = getStdin();
        while ((s = stdin.readLine()) != null && s.length() != 0) {
            System.out.println(s);
        }
    }

    /*
     * ���Դ�ָ���ı��ļ��ж�ȡ���
     *
     */
    public static void testReadFromTextFile(String name)
            throws FileNotFoundException, IOException {

        String s = "";
        BufferedReader fileReader = getTextFileReader(name);
        while ((s = fileReader.readLine()) != null) {
            System.out.println(s);
        }
        if (fileReader != null)
            fileReader.close();
    }

    /*
     * ���Դ�ָ���κ��ļ��ж�ȡ���
     *
     */
    public static void testReadFromCHTextFile(String name)
            throws FileNotFoundException, IOException {

        String s = "";
        BufferedReader fileReader = getCHTextFileReader(name);
        while ((s = fileReader.readLine()) != null) {
            System.out.println(s);
        }
        if (fileReader != null)
            fileReader.close();
    }

    /*
     * ���Դ��������͵��ļ��ж�ȡ���
     */
    public static void testReadFromFile(String name) throws FileNotFoundException, IOException {

        BufferedInputStream bis = getGeneralFileReader(name);
        byte[] buffer = new byte[bis.available()];
        while (bis.read(buffer) != -1) {
            ;
        }
        System.out.println(new String(buffer, "utf-8"));
    }

    /*
     * ���Խ����д��ָ���ļ�
     *
     */

    public static void testWriterToFile(String name) throws IOException {

        BufferedWriter fileWriter = getTextFileWriter(name);
        for (int i = 0; i < 1000000; i++) {
            fileWriter.write(Integer.toString(i));
            fileWriter.newLine();
        }
        System.out.println("Write over.");
        if (fileWriter != null)
            fileWriter.close();
    }

    public static void menu() {
        System.out.println("�����뵥�����ֲ����س������ ");
        System.out.println("1) ���Դӱ�׼�����ж�ȡ���;  2) ���Դ�ָ���ı��ļ��ж�ȡ���");
        System.out.println("3) ���Դ�ָ�����������ļ��ж�ȡ���;  4) ���Խ����д���ı��ļ�");
        System.out.println("0) �˳�����;");
    }

    public static void back() {
        System.out.println("*********** �˳���ǰ����. ************");
        System.out.println();
        menu();
    }

    public static void main(String[] args) {

        try {

            int i = 0;
            String s = "";
            //PrintStream console = System.out; 
            //outRedirToFile("./src/foundations/iolearn/output");

            menu();

            BufferedReader stdin = getStdin();
            while ((s = stdin.readLine()) != null) {
                if (s.matches("^\\s*[0-9]\\s*$")) {
                    i = Integer.parseInt(s.trim());
                    if (i == 0) {
                        System.out.println("�����Ѿ��˳�����.");
                        break;
                    }
                } else {
                    System.out.println("�����뵥������");
                    continue;
                }

                switch (i) {

                    case 1:
                        System.out.println("���Դӱ�׼�����ж�ȡ��ݣ�");
                        testReadFromStdin();
                        back();
                        break;

                    case 2:
                        System.out.println("���Դ�ָ���ı��ļ��ж�ȡ��ݣ�");
                        System.out.println("************* �ļ��� " + ".project ****************");
                        testReadFromTextFile(".project");
                        System.out.println("************* �ļ��� " + "/home/shuqin1984/pv/ϣ�������׵����� ****************");
                        testReadFromCHTextFile("/home/shuqin1984/pv/ϣ�������׵�����");
                        back();
                        break;

                    case 3:
                        System.out.println("���Դ�ָ�����������ļ��ж�ȡ��ݣ�");
                        System.out.println("************* �ļ��� " + ".project ****************");
                        testReadFromFile(".project");
                        System.out.println("************* �ļ��� " + "/home/shuqin1984/pv/ϣ�������׵����� ****************");
                        testReadFromFile("/home/shuqin1984/pv/ϣ�������׵�����");
                        System.out.println("************* �ļ��� " + "/home/shuqin1984/pv/ϣ�������׵�����.odt ****************");
                        testReadFromFile("/home/shuqin1984/pv/ϣ�������׵�����.odt");

                        back();
                        break;

                    case 4:
                        System.out.println("���Խ����д��ָ���ļ���");
                        testWriterToFile("./src/foundations/iolearn/bigfile.txt");
                        back();
                        break;

                    default:
                        System.out.println("not found this option.");
                        back();
                        break;
                }
            }
            //System.setOut(console);
        } catch (FileNotFoundException e) {
            System.err.println("û���ҵ�ָ���ļ���");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
