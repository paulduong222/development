package gov.nwcg.isuite.core.rules.financial.OF288V2;

import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class OF288InvoiceGeneratorRuleFactory2 {

	public enum ObjectTypeEnum {
		NEW_INCIDENT_RESOURCE_TIME_DATA_VO
		,DATES
		,TIME_REPORT_FILTER
		,TIME_POST_DATA_VOS
		,TIME_ADJUST_DATA_VOS
		}
	
	public enum RuleEnum {
		INVC_HAS_TIME_POSTS(0)
		,INVC_HAS_ADJMT_POSTS(5)
		,AD_HAS_ECI(8)
		,INVC_HAS_MISSING_DATES_PROMPT(10)
		,INVC_HAS_OUTSTANDING_SUPPLIES(15)
		,INVC_HAS_EMPLOYEE_TYPE(20)
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
			                        Collection<IncidentResourceTimeDataVo> irTimeDataVos,
			  					  	Collection<Date> dates,
			  					  	TimeReportFilter filter,
			  		  			  Collection<IncidentResourceTimePostDataVo> timePostDataVos,
					  			  Collection<IncidentResourceTimeAdustDataVo> timeAdjustDataVos) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance=new HasTimePostingRule(ctx, rule.name());
				break;
			case 5:
				ruleInstance=new HasTimeAdjustmentPostingRule(ctx, rule.name());
				break;
			case 8:
				ruleInstance=new HasEciRule(ctx, rule.name());
				break;
			case 10:
				ruleInstance=new HasMissingDatesRule(ctx, rule.name());
				break;
			case 15:
				ruleInstance=new HasOutstandingIssuedSuppliesRule(ctx, rule.name());
				break;
			case 20:
				ruleInstance=new HasEmployeeTypeRule(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(irTimeDataVos, ObjectTypeEnum.NEW_INCIDENT_RESOURCE_TIME_DATA_VO.name());
			ruleInstance.setObject(dates, ObjectTypeEnum.DATES.name());
			ruleInstance.setObject(filter, ObjectTypeEnum.TIME_REPORT_FILTER.name());
			ruleInstance.setObject(timePostDataVos, ObjectTypeEnum.TIME_POST_DATA_VOS.name());
			ruleInstance.setObject(timeAdjustDataVos, ObjectTypeEnum.TIME_ADJUST_DATA_VOS.name());
		}
		
		return ruleInstance;
	}
	

	
}
