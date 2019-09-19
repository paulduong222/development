package gov.nwcg.isuite.core.rules.incidentgroup.delete;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupDeleteRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_GROUP_ID
		,INCIDENT_GROUP_DAO
		,INCIDENT_GROUP_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_IF_PRIV_USER_CAN_DELETE(5)
		,CHECK_IF_DATA_STEWARD_USER_CAN_DELETE(10)
		,DISPLAY_MESSAGE_INCIDENTS(15)
		,DISPLAY_MESSAGE_INCIDENTS2(30)
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
	 * @param incidentGroupId
	 * @param incidentGroupDao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, Long incidentGroupId, IncidentGroupDao incidentGroupDao,IncidentGroup entity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckIfPrivUserCanDeleteRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckIfDataStewardUserCanDeleteRules(ctx);
				break;
			case 15:
				// this rule replaced by DisplayIncidentMessage2Rules defect 4446
				//ruleInstance = new DisplayIncidentMessageRules(ctx);
				break;
			case 30:
				ruleInstance = new DisplayIncidentMessage2Rules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(incidentGroupId, ObjectTypeEnum.INCIDENT_GROUP_ID.name());
			ruleInstance.setObject(incidentGroupDao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
