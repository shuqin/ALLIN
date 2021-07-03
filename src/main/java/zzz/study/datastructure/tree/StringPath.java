package zzz.study.datastructure.tree;

import java.util.List;

public class StringPath implements Path {
    StringBuilder s = new StringBuilder();

    public StringPath() {
    }

    public StringPath(Integer i) {
        s.append(i);
    }

    public StringPath(List list) {
        list.forEach(e -> {
            s.append(e);
        });
    }

    public StringPath(String str) {
        this.s = new StringBuilder(str);
    }

    public Long getValue() {
        return Long.parseLong(s.reverse().toString());
    }

    public void append(Integer i) {
        s.append(i);
    }

    public String toString() {
        return s.reverse().toString();
    }
}
