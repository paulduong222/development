package gov.nwcg.isuite.core.rules.reports.customreports.generate;

import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CustomReportGeneratorRuleFactory {

	public enum ObjectTypeEnum {
		CUSTOM_REPORT_FILTER
		}
	
	public enum RuleEnum {
		VALIDATE_DATA_RULES(5)
		,CUSTOM_REPORT_PAGE_WIDTH(10)
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
	 * @param irs
	 * @param dates
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
			                        ApplicationContext ctx, 
			                        CustomReportVo filter) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx()){
			case 5:
				ruleInstance=new ValidateDataRules(ctx, rule.name());
				break;
			case 10:
				ruleInstance=new MaxAllowedTotalColumnWidthRule(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(filter, ObjectTypeEnum.CUSTOM_REPORT_FILTER.name());
		}
		
		return ruleInstance;
	}
	

	
}
