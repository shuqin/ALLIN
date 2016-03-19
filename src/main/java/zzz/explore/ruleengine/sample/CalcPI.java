package zzz.explore.ruleengine.sample;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.builder.ResourceType;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import zzz.explore.ruleengine.sample.helper.KnowledgeBaseHelper;
import zzz.explore.ruleengine.sample.model.PI;
import zzz.explore.ruleengine.sample.model.RuleResource;

public class CalcPI {
	
public static void main(String[] args) {
		
		try {
			// load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			// go !
			PI pi = new PI();
			pi.setIterStrategy(PI.IterStrategySetting.ballardMethod.getStrategy());
			ksession.insert(pi);
			ksession.fireAllRules();	
			System.out.println("PI: " + pi.getPi());
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}			
		
	}
		
	private static KnowledgeBase readKnowledgeBase() throws Exception {
		List<RuleResource> resources = new ArrayList<RuleResource>();
		resources.add(new RuleResource("zzz/explore/ruleengine/rules/CalcPI.drl", ResourceType.DRL));
		return KnowledgeBaseHelper.readKnowledgeBase(resources);
	}
}
