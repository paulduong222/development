package gov.nwcg.isuite.core.rules.iap.savetask;

import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.core.vo.IapAircraftTaskVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveTaskRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_AIRCRAFT_TASK_VO
		,IAP_AIRCRAFT_TASK_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapAircraftTaskVo vo, IapAircraftTask entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateTaskDataRules(ctx);
				break;		
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_AIRCRAFT_TASK_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.IAP_AIRCRAFT_TASK_ENTITY.name());
		}
		
		return ruleInstance;
	}

}

