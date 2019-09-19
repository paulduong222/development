package gov.nwcg.isuite.core.rules.reports.customreports.importing;

import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CustomReportImportRuleFactory {

	public enum ObjectTypeEnum {
		CUSTOM_REPORT_VO,
		USER_VO
		}
	
	public enum RuleEnum {
		CHECK_VIEW_NOT_NULL(5)
		,VALIDATE_USER_ROLES(10)
		,VALIDATE_NUMBER_OF_COLUMNS(20)
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
		
		switch(rule.getRuleIdx()){
			case 5:
				ruleInstance=new CheckViewNotNull(ctx, rule.name());
				break;
			case 10:
				ruleInstance=new CheckUserRolesRules(ctx, rule.name());
				break;
			case 20:
				ruleInstance=new NumberOfColumnsRules(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(filter, ObjectTypeEnum.CUSTOM_REPORT_VO.name());
			ruleInstance.setObject(userVo, ObjectTypeEnum.USER_VO.name());
		}
		
		return ruleInstance;
	}
}
