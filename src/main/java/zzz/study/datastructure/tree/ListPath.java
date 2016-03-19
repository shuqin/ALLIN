package zzz.study.datastructure.tree;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ListPath implements Path {
    List<Integer> path = new ArrayList<>();

    public ListPath(int i) {
        this.path.add(i);
    }

    public ListPath(List list) {
        this.path.addAll(list);
    }

    @Override
    public void append(Integer i) {
        path.add(i);
    }

    @Override
    public Long getValue() {
        StringBuilder s = new StringBuilder();
        path.forEach(e -> {
            s.append(e);
        });
        return Long.parseLong(s.reverse().toString());
    }

    public String toString() {
        return StringUtils.join(path.toArray(), "");
    }
}
