package zzz.study.patterns.dutychain.simple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static void main(String[] args) {

        Dispatcher dispatch = new Dispatcher();
        dispatch.addParser(new AlphaNumParser());
        dispatch.addParser(new DateParser());

        String teststr = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            while ((teststr = in.readLine()) != null) {
                if (dispatch.handle(teststr)) {
                    System.out.println("Request handled.");
                } else {
                    System.err.println("Can't handle it.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
