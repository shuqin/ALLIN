package zzz.study.patterns.interpretation;

public class InterpreterTester {

    private static Interpreter ip = new Interpreter();

    public static void main(String[] args) {
        testInterpretLine();
        printDiamond();
        printCharH();
        printCharC();
    }

    public static void printDiamond() {
        String figureNote = "2b 1x 2b \n 1b 1x 1b 1x 1b \n 1x 3b 1x \n 1b 1x 1b 1x 1b \n 2b 1x 2b \n";
        ip.interpretFigure(figureNote);
    }

    public static void printCharH() {
        String figureNote = "2l 1x 3b 1x \n 5x \n 2l 1x 3b 1x\n";
        ip.interpretFigure(figureNote);
    }

    public static void printCharC() {
        String figureNote = "1b 5x \n 3l 1x \n 1b 5x \n";
        ip.interpretFigure(figureNote);
    }

    public static void testInterpretLine() {
        String exString1 = "3l \n 3L \n 1l 4b \n 1l 3x \n 3l 2b 2x \n ";
        String exString2 = "3l 5b 2x 3b\n  3l 5x 3b 5x \n";
        String exString3 = "03l 23x 4b \n 3l 023x 43b \n 3l 23x 043b \n 5b  \n 3x \n  5b 3x \n 3x 5b \n";
        String totalString = exString1 + exString2 + exString3;
        String[] multLines = totalString.split("\n");

        for (String line : multLines) {
            try {
                ip.interpretLine(line, true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

}
