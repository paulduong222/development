package gov.nwcg.isuite.core.rules.reports.shiftsInExcess;

import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class ShiftsInExcessReportGeneratorRuleFactory {

	public enum ObjectTypeEnum {
		SHIFTS_REPORT_FILTER
		}
	
	public enum RuleEnum {
		SHIFTS_START_END_DATE(10)
		,SHIFTS_INC_OR_GRP_SELECTED(15)
		,SHIFTS_STANDARD_HOURS(20)
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
			                        ShiftsInExcessOfStandardHoursReportFilter filter) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance=new StartDateBeforeEndDateRule(ctx, rule.name());
				break;
			case 15:
				ruleInstance=new IncidentOrGroupSelectedRule(ctx, rule.name());
				break;
			case 20:
				ruleInstance=new StandardHoursSpecifiedRule(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(filter, ObjectTypeEnum.SHIFTS_REPORT_FILTER.name());
		}
		
		return ruleInstance;
	}
	

	
}
