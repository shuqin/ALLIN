package zzz.study.patterns.builder;

import java.util.Calendar;

public class ReservationParser {

    private ReservationBuilder builder;

    public ReservationParser(ReservationBuilder builder) {
        this.builder = builder;
    }

    public void parser(String s) throws Exception {

        String[] tokens = s.split(",\\s*");

        for (int i = 0; i < tokens.length; i += 2) {
            String type = tokens[i];
            String value = tokens[i + 1];

            if ("date".compareToIgnoreCase(type) == 0) {

                Calendar now = Calendar.getInstance();
                String date = value + ", " + now.get(Calendar.YEAR);
                builder.setDate(date);

            } else if ("headcount".compareToIgnoreCase(type) == 0) {
                builder.setHeadcount(Integer.parseInt(value));
            } else if ("city".compareToIgnoreCase(type) == 0) {
                builder.setCity(value.trim());
            } else if ("yuanPerHead".compareToIgnoreCase(type) == 0) {
                builder.setYuanPerHead(Double.parseDouble(value));
            } else if ("hasSite".compareToIgnoreCase(type) == 0) {
                builder.setHasSite(value.equalsIgnoreCase("true"));
            }
        }
    }
}
