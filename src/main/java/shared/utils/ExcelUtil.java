package shared.utils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class ExcelUtil {

  private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

  public static <T> boolean writeExcel(File file, List<String> titles, List<T> contents,
                                       WritingOneRowHandler<T> writingHandler) {

    try {

      // 创建工作表: 设置纵横打印（默认为纵打）、打印纸
      WritableWorkbook workbook = Workbook.createWorkbook(file);
      WritableSheet sheet = workbook.createSheet("第一张表", 0);
      jxl.SheetSettings sheetset = sheet.getSettings();
      sheetset.setProtected(false);

      // 创建单元格格式
      WritableCellFormat wcfCenterFormat = createCellCenterFormat();
      WritableCellFormat wcfLeftFormat = createCellLeftFormat();


      // 写入标题行
      for (int col = 0; col < titles.size(); col++) {
        sheet.addCell(new Label(col, 0, titles.get(col), wcfCenterFormat));
      }

      // 写入正文数据
      int rowNo = 1;
      for (T obj : contents) {
        writingHandler.write(obj, rowNo, sheet, wcfLeftFormat);
        rowNo++;
      }

      // 刷新缓存并关闭文件
      workbook.write();
      workbook.close();

    } catch (Exception e) {
      logger.error("excel-writing-error: " + e.getMessage(), e);
      return false;
    }
    return true;
  }

  /*
   * 单元格格式：标题居中
   */
  private static WritableCellFormat createCellCenterFormat() throws WriteException {

    WritableFont BoldFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.BOLD);
    WritableCellFormat wcfCenterFormat = new WritableCellFormat(BoldFont);

    wcfCenterFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
    wcfCenterFormat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
    wcfCenterFormat.setAlignment(Alignment.CENTRE); // 文字水平对齐
    wcfCenterFormat.setWrap(false); // 文字是否换行
    return wcfCenterFormat;
  }

  /*
   * 单元格格式：正文居左
   */
  private static WritableCellFormat createCellLeftFormat() throws WriteException {
    WritableFont NormalFont = new WritableFont(WritableFont.COURIER, 10);
    WritableCellFormat wcfLeftFormat = new WritableCellFormat(NormalFont);

    wcfLeftFormat.setBorder(Border.NONE, BorderLineStyle.THIN);
    wcfLeftFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
    wcfLeftFormat.setAlignment(Alignment.LEFT);
    wcfLeftFormat.setWrap(true);
    return wcfLeftFormat;
  }

  public interface WritingOneRowHandler<T> {

    void write(T row, int rowNo, WritableSheet sheet, WritableCellFormat wcf)
        throws Exception;
  }
}

