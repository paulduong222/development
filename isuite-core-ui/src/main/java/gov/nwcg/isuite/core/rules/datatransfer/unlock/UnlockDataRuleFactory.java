package gov.nwcg.isuite.core.rules.datatransfer.unlock;

import gov.nwcg.isuite.core.vo.DataTransferVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class UnlockDataRuleFactory {

	public enum ObjectTypeEnum {
		DATA_TRANSFER_VO
	}
	
	public enum RuleEnum {
		CHECK_BY_INCIDENT_ID(10)
		,CHECK_BY_INCIDENT_GROUP_ID(20)
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
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx,DataTransferVo dataTransferVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckByIncidentRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckByIncidentGroupRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(dataTransferVo, ObjectTypeEnum.DATA_TRANSFER_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
