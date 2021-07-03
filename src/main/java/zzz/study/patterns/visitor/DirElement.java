package zzz.study.patterns.visitor;

import java.io.File;

public class DirElement implements Element {

    private File root;

    public DirElement(File root) {
        this.root = root;
    }

    public void accept(Visitor v) {

        v.visitDir(root);

    }

}
