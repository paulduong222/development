package gov.nwcg.isuite.core.rules.reports.customreports.sqlbuilder;

import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CustomReportSQLBuilderRuleFactory {
	
	public enum ObjectTypeEnum {
		CUSTOM_REPORT_VO
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, CustomReportVo reportVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			
		}
		if(null != ruleInstance){
			ruleInstance.setObject(reportVo, ObjectTypeEnum.CUSTOM_REPORT_VO.name());
		}
		
		return ruleInstance;
	}
	

}
