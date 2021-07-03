package zzz.study.foundations.string;

public class OutputFormat {

    public static void main(String[] args) {
        System.out.format("%-6s %-5s %-5s %-3s\n", "我", "是", "重要", "的");
        System.out.format("%-6s %-5s %-5s %-3s\n", "你们", "是", "我的", "朋友");
        System.out.format("%-6s %-5s %-5s %-3s\n", "他们", "很", "讨厌", "令人");

        System.out.println("******************************* ");

        System.out.format("%-6s %-5s %-5.5s %-3s\n", "I", "am", "important", "indeed");
        System.out.format("%-6s %-5s %-5.5s %-3s\n", "you", "are", "my", "friends");
        System.out.format("%-6s %-5s %-5.5s %-3s\n", "they", "are", "very", "noisy");

        System.out.println("******************************* ");

        System.out.format("%s\n", "这是中文字符");
        System.out.format("%s\n", "These are English Characters");
        System.out.format("%s\n", "一个汉字和一个英文字符都被认为是一个字符，但字符宽度却不一样");
        System.out.format("%s\n", "format 只能根据字符个数进行对齐，而不能根据字符宽度对齐。");
        System.out.format("%-6.6s %-5s %-5.5s %-3.5s\n", "中文字符宽度", "是", "1.5-2", "英文字符宽度");

        System.out.println("******************************* ");

        System.out.printf("%f %5.2f %7.3f\n", Math.PI, Math.PI, Math.PI);
        System.out.printf("%f %5.2f %7.3f\n", Math.E, Math.E, Math.E);
        System.out.printf("%f %5.2f %7.3f\n", 2.50, 2.50, 2.50);


    }

}
