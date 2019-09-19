package gov.nwcg.isuite.core.rules.resourceinventory.save;

import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ResourceInventorySaveRuleFactory {
	
	public enum ObjectTypeEnum {
		RESOURCE_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	/**
	 * @param rule
	 * @param ctx
	 * @param resourceVo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, ResourceVo resourceVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(resourceVo, ObjectTypeEnum.RESOURCE_VO.name());
		}
		
		return ruleInstance;
	}

}
