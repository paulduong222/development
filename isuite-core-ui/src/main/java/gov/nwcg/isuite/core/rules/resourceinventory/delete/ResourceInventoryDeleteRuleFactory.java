package gov.nwcg.isuite.core.rules.resourceinventory.delete;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ResourceInventoryDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		RESOURCE_ENTITY
	}
	
	public enum RuleEnum {
		;
	
		private int ruleIdx=-1;
	
		RuleEnum(int idx){
			ruleIdx=idx;
		}
	
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, Resource resource) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				break;
			
			case 10:
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(resource, ObjectTypeEnum.RESOURCE_ENTITY.name());
		}
		
		return ruleInstance;
	}

}
