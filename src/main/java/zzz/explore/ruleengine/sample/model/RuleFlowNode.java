package zzz.explore.ruleengine.sample.model;

public class RuleFlowNode {

    private int id;
    private String type;
    private String name;
    private String ruleGroupName;

    public RuleFlowNode(int id, String type, String name, String ruleFlowName) {

        this.id = id;
        this.type = type;
        this.name = name;
        this.ruleGroupName = ruleFlowName;
    }

    public String toString() {
        String basic = "<" + type + " " + "id=\"" + id + "\" " +
                "name=\"" + name + "\" ";
        String addon = null;
        if (ruleGroupName == null) {
            addon = "";
        } else {
            addon = "ruleFlowGroup=\"" + ruleGroupName + "\"";
        }
        return basic + addon + "/>";
    }


}
