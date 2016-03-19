package zzz.explore.ruleengine.sample.helper;

import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.io.ResourceFactory;

import zzz.explore.ruleengine.sample.model.RuleResource;

public class KnowledgeBaseHelper {
	
	private KnowledgeBaseHelper() {}
	
	public static KnowledgeBase readKnowledgeBase(List<RuleResource> resources) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		for (RuleResource res: resources) {
			try {
				kbuilder.add(ResourceFactory.newClassPathResource(res.getRuleResourceFile()), res.getResType());
			} catch (Exception ex) {
				kbuilder.add(ResourceFactory.newFileResource(res.getRuleResourceFile()), res.getResType());
			}
		}
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}

}
