package gov.nwcg.isuite.core.rules.workarea.delete;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class WorkAreaDeleteRuleFactory {

	public enum ObjectTypeEnum {
		WORK_AREA_ID
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_SHARED_OUT(5)
		,CREATE_USER_MESSAGES(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, Long workAreaId) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				break;
			case 10:
				break;
			case 15:
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(workAreaId, ObjectTypeEnum.WORK_AREA_ID.name());
		}
		
		return ruleInstance;
	}
	

	
}
