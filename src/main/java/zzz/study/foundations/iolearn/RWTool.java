package zzz.study.foundations.iolearn;

import java.io.*;

public class RWTool {

  private RWTool() {
  }

  /**
   * getStdin: 获取由标准输入构造的字符缓冲输入流
   *
   * @return 用于从标准输入【通常是键盘】中读取数据的标准读对象
   */
  public static BufferedReader getStdin() throws UnsupportedEncodingException {
    return new BufferedReader(new InputStreamReader(System.in));
  }

  /**
   * getTextFileReader: 获取由指定文本文件构造的字符缓冲输入流
   *
   * @param filename 指定文本文件的路径
   * @return 用于从参数指定的文本文件中读取数据的文件读对象
   */
  public static BufferedReader getTextFileReader(String filename)
      throws FileNotFoundException {
    return new BufferedReader(new FileReader(new File(filename).getAbsoluteFile()));
  }

  /**
   * getCHTextFileReader: 获取由指定文本文件构造的缓冲输入流［含中文］
   *
   * @param filename 指定文件的路径
   * @return 用于从参数指定的文件中读取数据的文件读对象
   */
  public static BufferedReader getCHTextFileReader(String filename)
      throws FileNotFoundException, UnsupportedEncodingException {
    FileInputStream fis = new FileInputStream(new File(filename).getAbsoluteFile());
    return new BufferedReader(new InputStreamReader(fis, "utf-8"));
  }

  /**
   * getGeneralFileReader: 获取由指定任意类型的文件构造的缓冲输入流
   *
   * @param filename 指定文件路径
   * @return 用于从参数指定的任意类型的文件中读取数据的文件读对象
   */
  public static BufferedInputStream getGeneralFileReader(String filename)
      throws FileNotFoundException, IOException {
    return new BufferedInputStream(
        new FileInputStream(new File(filename).getAbsoluteFile()));
  }


  /**
   * getTextFileWriter: 获取由指定文本文件构造的字符缓冲输出流
   *
   * @param filename 指定文本文件的路径
   * @return 用于向参数指定的文本文件写入数据的文件写对象
   */
  public static BufferedWriter getTextFileWriter(String filename) throws IOException {
    return new BufferedWriter(new FileWriter(new File(filename).getAbsoluteFile()));
  }

  /**
   * 重定向标准输出到指定文件
   *
   * @param filename 指定重定向文件的路径
   */
  public static void outRedirToFile(String filename) throws FileNotFoundException {
    PrintStream output =
        new PrintStream(new BufferedOutputStream(
            new FileOutputStream(filename)));
    System.setOut(output);
  }

  public static String readFromSource(String filename) {
    try {
      InputStream is = RWTool.class.getResourceAsStream(filename);
      byte[] bytes = new byte[4096];
      int num = 0;
      String text = "";
      while((num=is.read(bytes))>0){
        text=new String(bytes,0,num);
      }
      return text;
    } catch (Exception ex) {
      throw new RuntimeException(ex.getCause());
    }

  }


}
