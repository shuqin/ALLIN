package zzz.study.patterns.visitor;

import java.io.File;

public class Client {

    public static void main(String[] args) {

        Visitor visitor = new ActualVisitor();

        Element file = new FileElement(new File("./pom.xml"));
        file.accept(visitor);

        System.out.println(" ------------------------------ ");

        Element dir = new DirElement(new File("./src/main/java/patterns/"));
        dir.accept(visitor);
    }

}
