package zzz.explore.ruleengine.sample.model;

import org.drools.builder.ResourceType;

public class RuleResource {
	
	private String ruleResourceFile;
	private ResourceType resType;
	
	public RuleResource(String ruleResourceFile, ResourceType resType) {
		this.ruleResourceFile = ruleResourceFile;
		this.resType = resType;
	}
	
	public String getRuleResourceFile() {
		return ruleResourceFile;
	}
	public void setRuleResourceFile(String ruleResourceFile) {
		this.ruleResourceFile = ruleResourceFile;
	}
	public ResourceType getResType() {
		return resType;
	}
	public void setResType(ResourceType resType) {
		this.resType = resType;
	}

}
