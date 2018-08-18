package cc.lovesq.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtils {

  /***************************************************************************
   * @param <T>
   * @param fileName EXCEL文件名称
   * @param listTitle EXCEL文件第一行列标题集合
   * @param listContent EXCEL文件正文数据集合
   * @return
   * @throws Exception
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T> void exportExcel(String fileName, String[] Title, List<T> contents,
                                     WritingOneRowHandler writingHandler,
                                     HttpServletResponse response)
      throws Exception {

    OutputStream os = null;
    os = response.getOutputStream();
    response.reset();
    response.setHeader("Content-disposition",
                       "attachment; filename=" + new String(fileName.getBytes("GB2312"),
                                                            "ISO8859-1"));
    response.setContentType("application/msexcel");// 定义输出类型

    /** **********创建工作表 ************ */
    WritableWorkbook workbook = Workbook.createWorkbook(os);

    /** **********创建工作表************ */

    WritableSheet sheet = workbook.createSheet("虚机信息列表", 0);

    /** **********设置纵横打印（默认为纵打）、打印纸***************** */
    jxl.SheetSettings sheetset = sheet.getSettings();
    sheetset.setProtected(false);

    /** ************设置单元格字体************** */
    WritableFont NormalFont = new WritableFont(WritableFont.COURIER, 10);
    WritableFont BoldFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.BOLD);

    /** ************以下设置三种单元格样式，灵活备用************ */
    // 用于标题居中
    WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
    wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
    wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
    wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
    wcf_center.setWrap(false); // 文字是否换行

    // 用于正文居左
    WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
    wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
    wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
    wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
    wcf_left.setWrap(true); // 文字是否换行

    /** ***************以下是EXCEL第一行列标题********************* */
    for (int col = 0; col < Title.length; col++) {
      sheet.addCell(new Label(col, 0, Title[col], wcf_center));
    }

    /** ***************以下是EXCEL正文数据********************* */
    int rowNo = 1;
    for (T obj : contents) {
      writingHandler.write(obj, rowNo, sheet, wcf_left);
      rowNo++;
    }
    /** **********将以上缓存中的内容写到EXCEL文件中******** */
    workbook.write();
    /** *********关闭文件************* */
    workbook.close();
  }

  @SuppressWarnings("unchecked")
  public static <T> boolean writeExcel(File file, String[] Title, List<T> contents,
                                       @SuppressWarnings("rawtypes") WritingOneRowHandler writingHandler) {

    try {

      /** **********创建工作表 ************ */
      WritableWorkbook workbook = Workbook.createWorkbook(file);

      /** **********创建工作表************ */

      WritableSheet sheet = workbook.createSheet("虚机信息列表", 0);

      /** **********设置纵横打印（默认为纵打）、打印纸***************** */
      jxl.SheetSettings sheetset = sheet.getSettings();
      sheetset.setProtected(false);

      /** ************设置单元格字体************** */
      WritableFont NormalFont = new WritableFont(WritableFont.COURIER, 10);
      WritableFont BoldFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.BOLD);

      /** ************以下设置三种单元格样式，灵活备用************ */
      // 用于标题居中
      WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
      wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
      wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
      wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
      wcf_center.setWrap(false); // 文字是否换行

      // 用于正文居左
      WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
      wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条
      wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
      wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐
      wcf_left.setWrap(true); // 文字是否换行

      /** ***************以下是EXCEL第一行列标题********************* */
      for (int col = 0; col < Title.length; col++) {
        sheet.addCell(new Label(col, 0, Title[col], wcf_center));
      }

      /** ***************以下是EXCEL正文数据********************* */
      int rowNo = 1;
      for (T obj : contents) {
        writingHandler.write(obj, rowNo, sheet, wcf_left);
        rowNo++;
      }
      /** **********将以上缓存中的内容写到EXCEL文件中******** */
      workbook.write();
      /** *********关闭文件************* */
      workbook.close();

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static <T> String listToString(List<T> objList) {
    if (objList == null || objList.size() == 0) {
      return "";
    }
    StringBuilder resultBuf = new StringBuilder();
    for (T obj : objList) {
      resultBuf.append(obj);
      resultBuf.append('\n');
    }
    return resultBuf.toString();
  }

  public interface WritingOneRowHandler<T> {

    public void write(T row, int rowNo, WritableSheet sheet, WritableCellFormat wcf_left)
        throws Exception;
  }
}

