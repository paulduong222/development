package gov.nwcg.isuite.core.rules.reports.customreports.delete;

import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CustomReportDeleteRuleFactory {

	public enum ObjectTypeEnum {
		CUSTOM_REPORT_FILTER,
		USER_VO
		}
	
	public enum RuleEnum {
		VALIDATE_OWNER_DELETION(10)
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
	 * 
	 * @param rule
	 * @param ctx
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
			                        ApplicationContext ctx, 
			                        CustomReportVo filter,
			                        UserVo userVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()) {
			case 10:
				ruleInstance=new ValidateOwnerDeletionRules(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(filter, ObjectTypeEnum.CUSTOM_REPORT_FILTER.name());
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
