package zzz.explore.ruleengine.sample.model;

public class RuleFlowConnection {

    private int fromId;
    private int toId;

    public RuleFlowConnection(int fromId, int toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public int getFromId() {
        return fromId;
    }

    public int getToId() {
        return toId;
    }

    public String desc() {
        return "<connection from=\"" + fromId + "\" to=\"" + toId + "\"></connection>";
    }

    public String toString() {
        return "(" + fromId + ", " + toId + ")";
    }

}
