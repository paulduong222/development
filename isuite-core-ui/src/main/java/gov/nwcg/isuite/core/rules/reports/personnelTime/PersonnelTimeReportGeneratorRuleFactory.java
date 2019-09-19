package gov.nwcg.isuite.core.rules.reports.personnelTime;

import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PersonnelTimeReportGeneratorRuleFactory {

	public enum ObjectTypeEnum {
		PERSONNEL_TIME_REPORT_FILTER
		}
	
	public enum RuleEnum {
		PRSNL_RESOURCE_SELECTED(5)
		,PRSNL_START_END_DATE(10)
		,PRSNL_INC_OR_GRP_SELECTED(15)
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
			                        PersonnelTimeReportFilter filter) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance=new ResourceSelectedRule(ctx, rule.name());
				break;
			case 10:
				ruleInstance=new StartDateBeforeEndDateRule(ctx, rule.name());
				break;
			case 15:
				ruleInstance=new IncidentOrGroupSelectedRule(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(filter, ObjectTypeEnum.PERSONNEL_TIME_REPORT_FILTER.name());
		}
		
		return ruleInstance;
	}
	

	
}
