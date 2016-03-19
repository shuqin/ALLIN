package zzz.explore.ruleengine.sample;

import org.drools.KnowledgeBase;
import org.drools.builder.ResourceType;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import zzz.explore.ruleengine.sample.helper.KnowledgeBaseHelper;
import zzz.explore.ruleengine.sample.model.Employ;
import zzz.explore.ruleengine.sample.model.RuleResource;

import java.util.ArrayList;
import java.util.List;

public class EmployTest {

    public static void main(String[] args) {

        try {
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
            // go !
            Employ emp = new Employ();
            emp.setEduInfo("master");
            emp.setResume("tech");
            emp.setAnnualExam("good");
            emp.setAwardPunish("award");
            ksession.insert(emp);
            ksession.startProcess("myrules.salary");
            ksession.fireAllRules();
            System.out.println("Basic Salary: " + emp.getBasicSalary());
            System.out.println("Duty Salary: " + emp.getDutySalary());
            System.out.println("Bonus      : " + emp.getBonus());
            System.out.println("rate       : " + emp.getPercent());
            System.out.printf("Total Salary: %.0f", emp.getTotalSalary());
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
        List<RuleResource> resources = new ArrayList<RuleResource>();
        resources.add(new RuleResource("zzz/explore/ruleengine/rules/EduInfoRule.drl", ResourceType.DRL));
        resources.add(new RuleResource("zzz/explore/ruleengine/rules/ResumeRule.drl", ResourceType.DRL));
        resources.add(new RuleResource("zzz/explore/ruleengine/rules/BonusRule.drl", ResourceType.DRL));
        resources.add(new RuleResource("zzz/explore/ruleengine/rules/AwardPunish.drl", ResourceType.DRL));
        resources.add(new RuleResource("zzz/explore/ruleengine/rules/TotalRule.drl", ResourceType.DRL));
        resources.add(new RuleResource("zzz/explore/ruleengine/rules/salary.rf", ResourceType.DRF));
        return KnowledgeBaseHelper.readKnowledgeBase(resources);
    }

}
