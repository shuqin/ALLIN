package zzz.study.patterns.composite.expression;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum Op {

  isnull("isnull"),
  notnull("notnull"),
  eq("="),
  neq("!="),
  in("IN"),
  contains("HAS"),
  notcontains("NCT"),
  ;

  String symbo;

  Op(String symbo) {
    this.symbo = symbo;
  }

  public String getSymbo() {
    return symbo;
  }

  public static Op get(String name) {
    for (Op op: Op.values()) {
      if (Objects.equals(op.symbo, name)) {
        return op;
      }
    }
    return null;
  }

  public static Set<String> getAllOps() {
    return Arrays.stream(Op.values()).map(Op::getSymbo).collect(Collectors.toSet());
  }
}
