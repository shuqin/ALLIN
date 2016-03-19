package zzz.study.patterns.interpretation;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static zzz.study.utils.PrintUtil.printNChars;

public class Interpreter {

    private static final String PART1_REGEX = "(([1-9]|[1-9][0-9]+)[lL])?";
    private static final String PART2_REGEX = "((([1-9]|[1-9][0-9]+)[BbXx]\\s*)*)";
    private static final String LINE_PATTERN_REGEX = "\\s*" + PART1_REGEX + "\\s*" + PART2_REGEX + "\\s*";

    private static final Pattern LINE_PATTERN = Pattern.compile(LINE_PATTERN_REGEX);
    private static final Pattern PART_PATTERN = Pattern.compile("([1-9]|[1-9][0-9]+)([bBxX])");

    /**
     * interpretFigure: ���͸�ͼ�α�ǣ������� \n �� \r �����Ķ���б��
     */
    public void interpretFigure(String figureNote) {
        System.out.println("Ҫ��ӡ��ͼ��Ϊ�� ");
        for (String lineNote : figureNote.split("\\s*[\r\n]\\s*")) {
            interpretLine(lineNote, false);
        }
    }

    /**
     * ���͸��е�ͼ�α��
     *
     * @param lineNote ���е�ͼ�α��
     */
    public void interpretLine(String lineNote, boolean tipFlag) {
        if (tipFlag) {
            System.out.println("�б��Ϊ�� " + lineNote);
        }
        Matcher matcher = LINE_PATTERN.matcher(lineNote);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("���Ϸ���ͼ�α�ǣ����飡");
        }
        int lineNum = 0;
        if (matcher.group(1) == null) {
            lineNum = 1;
        } else {
            lineNum = Integer.parseInt(matcher.group(2));
        }
        String lineInfo = matcher.group(3);
        for (int i = 0; i < lineNum; i++) {
            printLine(lineInfo);
            System.out.println();
        }
    }

    /**
     * ��ݸ���б����Ϣ��ӡ��
     *
     * @param lineInfo �б����Ϣ
     */
    public void printLine(String lineInfo) {
        if (lineInfo == null || lineInfo.equals("")) {
            return;
        }
        String partNote = "";
        Matcher matcher = PART_PATTERN.matcher("");
        String flag = null;
        int num = 0;
        Scanner scanner = new Scanner(lineInfo);
        while (scanner.hasNext(PART_PATTERN)) {
            partNote = scanner.next();
            matcher.reset(partNote);
            if (matcher.matches()) {
                num = Integer.parseInt(matcher.group(1));
                flag = matcher.group(2);
                if (flag.equals("x") || flag.equals("X")) {
                    printNChars('x', num);
                } else {
                    printNChars(' ', num);
                }
            }
        }

    }


}
