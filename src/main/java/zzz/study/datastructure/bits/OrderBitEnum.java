package zzz.study.datastructure.bits;

public enum OrderBitEnum {

    IS_PAIED(1, "is_paied", "是否已支付"),
    IS_REFUNDED(2, "is_refunded", "是否已退款"),
    IS_SECURED(4, "is_secured", "是否担保"),

    ;

    int value;
    String name;
    String desc;

    OrderBitEnum(int value, String name, String desc) {
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public static boolean isPaied(int bit) {
        return (bit & IS_PAIED.value) != 0;
    }

    public static boolean isRefunded(int bit) {
        return (bit & IS_REFUNDED.value) != 0;
    }

    public static boolean isSecured(int bit) {
        return (bit & IS_SECURED.value) != 0;
    }

    public static String desc(int bit) {
        StringBuilder sb = new StringBuilder();
        int len;

        if ((bit & IS_PAIED.value) != 0)        sb.append("[IS_PAIED]");
        if ((bit & IS_REFUNDED.value) != 0)     sb.append("[IS_REFUNDED]");
        if ((bit & IS_SECURED.value) != 0)       sb.append("[IS_SECURED]");

        if ((len = sb.length()) > 0)
            return sb.toString();
        return "";
    }

    static class OrderEnumBitTester {

        public static void main(String[]args) {
            int x = 3;
            System.out.println(isRefunded(x));
            System.out.println(desc(x));
        }
    }
}
