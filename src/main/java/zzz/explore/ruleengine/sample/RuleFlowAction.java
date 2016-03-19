package zzz.explore.ruleengine.sample;

import zzz.explore.ruleengine.sample.helper.RuleFlowHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RuleFlowAction {

    public static void main(String[] args) {
        List<String> rfgNames = new ArrayList<String>();
        rfgNames.add("basic_salary");
        rfgNames.add("duty_salary");
        rfgNames.add("bonus_salary");
        rfgNames.add("award_punish");
        rfgNames.add("sum_salary");
        RuleFlowHelper rfh = new RuleFlowHelper("salary", rfgNames);
        rfh.createRFXmlFile(new File("./rules/saflow.xml").getAbsoluteFile());
        EmployTest2.main(args);
    }

}
