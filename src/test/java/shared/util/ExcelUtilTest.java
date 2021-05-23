package shared.util;

import jxl.write.Label;
import org.junit.Test;
import shared.utils.ExcelUtil;

import java.io.File;
import java.util.Arrays;

/**
 * @Description 写入 Excel 测试
 * @Date 2021/5/23 1:23 下午
 * @Created by qinshu
 */
public class ExcelUtilTest {

    @Test
    public void testWriteExcel() {
        ExcelUtil.writeExcel(new File("/tmp/test.xls"),
                Arrays.asList("field", "mean"),
                Arrays.asList(new ReportField("name", "name"), new ReportField("status", "status")),
                (row, rowNo, sheet, wcf) -> {
                    sheet.addCell(new Label(0, rowNo, row.getField(), wcf));
                    sheet.addCell(new Label(1, rowNo, row.getMean(), wcf));
                }
        );

    }
}

class ReportField {
    private String field;
    private String mean;

    public ReportField(String field, String mean) {
        this.field = field;
        this.mean = mean;
    }

    public String getField() {
        return field;
    }

    public String getMean() {
        return mean;
    }
}
