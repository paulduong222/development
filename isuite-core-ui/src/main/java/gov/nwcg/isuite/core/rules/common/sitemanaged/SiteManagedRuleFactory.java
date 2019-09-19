package gov.nwcg.isuite.core.rules.common.sitemanaged;

import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SiteManagedRuleFactory {

	public enum ObjectTypeEnum {
		ID
		,ID_TYPE
	}
	
	public enum RuleEnum {
		IS_INCIDENT_LOCKED_BY_INC_ID(10)
		,IS_INCIDENT_LOCKED_BY_INC_GRP_ID(20)
		,IS_INCIDENT_LOCKED_BY_RES_ID(30)
		,IS_INCIDENT_LOCKED_BY_ASSIGNMENT_ID(40)
		,IS_INCIDENT_LOCKED_BY_ASSIGNMENT_TIME_ID(50)
		,IS_INCIDENT_LOCKED_BY_CONTRACTOR_ID(60)
		,IS_INCIDENT_LOCKED_BY_COSTGROUP_ID(70)
		,IS_INCIDENT_LOCKED_BY_RES_OTHER_ID(80)
		,IS_INCIDENT_LOCKED_BY_RESOURCE_ID(90)
		,IS_INCIDENT_LOCKED_BY_IAPPLAN_ID(100)
		,IS_INCIDENT_LOCKED_BY_TIME_INVOICE_ID(110)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}	
	
	public static IRule getInstance(
			RuleEnum rule, 
			ApplicationContext ctx, 
			Long id,
			String idType) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckLockedByIncIdRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckLockedByIncGroupIdRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckLockedByIncResIdRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckLockedByAssignmentIdRules(ctx);
				break;
			case 50:
				ruleInstance = new CheckLockedByAssignmentTimeIdRules(ctx);
				break;
			case 60:
				ruleInstance = new CheckLockedByContractorIdRules(ctx);
				break;
			case 70:
				ruleInstance = new CheckLockedByCostGroupIdRules(ctx);
				break;
			case 80:
				ruleInstance = new CheckLockedByResOtherIdRules(ctx);
				break;
			case 90:
				ruleInstance = new CheckLockedByResourceIdRules(ctx);
				break;
			case 100:
				ruleInstance = new CheckLockedByIapPlanIdRules(ctx);
				break;
			case 110:
				ruleInstance = new CheckLockedByTimeInvoiceIdRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(id, ObjectTypeEnum.ID.name());
			ruleInstance.setObject(idType, ObjectTypeEnum.ID_TYPE.name());
		}
		
		return ruleInstance;
	}
	

	
}
