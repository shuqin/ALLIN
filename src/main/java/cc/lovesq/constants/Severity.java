package cc.lovesq.constants;

/**
 * @Description 事件危急程度
 * @Date 2021/5/20 10:26 下午
 * @Created by qinshu
 */
public enum Severity {

    low(1, "低危"),
    mid(2, "中危"),
    high(3, "高危"),
    severe(4, "危急");

    int value;
    String desc;

    Severity(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getSeverityDesc(String value) {
        for (Severity s: Severity.values()) {
            if (String.valueOf(s.getValue()).equals(value)) {
                return s.getDesc();
            }
        }
        return "NoMatch";
    }

    public static String getSeverityDesc(int value) {
        for (Severity s: Severity.values()) {
            if (s.getValue() == value) {
                return s.getDesc();
            }
        }
        return "NoMatch";
    }
}
