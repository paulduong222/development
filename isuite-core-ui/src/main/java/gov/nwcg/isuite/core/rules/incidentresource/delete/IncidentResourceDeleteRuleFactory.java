package gov.nwcg.isuite.core.rules.incidentresource.delete;

import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class IncidentResourceDeleteRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_RESOURCE_IDS
		,INCIDENT_RESOURCE_GRID_VO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,PROMPT_REMOVE_PARENT_ASSOC(3)
		//,CHECK_TIME_POSTINGS(5)
		//,CHECK_TIME_ADJUSTMENTS(10)
		//,PROMPT_PARENT_RESOURCE(30)
		,CHECK_TIME_INVOICES(40)
		,CHECK_SUBORDINATE_TIME_INVOICES(50)
		,CHECK_TIME_COST_RECORDS(60)
		,CHECK_SUBORDINATE_CRITICAL_DATA(70)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static RuleEnum getRuleEnum(int ruleIdx) {
		
		return null;
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, Collection<Long> incidentResourceIds, IncidentResourceGridVo gridVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 3:
				ruleInstance = new PromptRemoveParentAssocRules(ctx);
				break;
			case 5:
				//ruleInstance = new CheckTimePostingRules(ctx);
				break;
			case 10:
				//ruleInstance = new CheckTimeAdjustmentRules(ctx);
				break;
			case 30:
				//ruleInstance = new PromptParentResourceRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckTimeInvoiceRules(ctx);
				break;
			case 50:
				ruleInstance = new CheckSubordinateTimeInvoiceRules(ctx);
				break;
			case 60:
				ruleInstance = new CheckTimeCostRecordsRules(ctx);
				break;
			case 70:
				//ruleInstance = new CheckSubordinateCriticalDataRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(incidentResourceIds, ObjectTypeEnum.INCIDENT_RESOURCE_IDS.name());
			ruleInstance.setObject(gridVo, ObjectTypeEnum.INCIDENT_RESOURCE_GRID_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
