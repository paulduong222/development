package gov.nwcg.isuite.core.rules.incidentgroup.addincident;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupAddIncidentRuleFactory {

	public enum ObjectTypeEnum {
		NEW_IG_VO
		,INCIDENT_GROUP_DAO
		,ORIGINAL_IG_VO
		}
	
	public enum RuleEnum {
		IG_INCIDENT_CHECK_PERMISSIONS(0)
		,IG_INCIDENT_PROMPT_APPLY_QUESTIONS(5)
		,IG_INCIDENT_NO_DUPLICATE_INCIDENTS(10)
		,IG_INCIDENT_CHECK_INCIDENT_IS_RESTRICTED(15)
		,IG_INCIDENT_CHECK_NOT_ALREADY_ASSIGNED(20)
		,IG_PROMPT_REMOVE_INCIDENT(25)
		,IG_ADD_INCIDENT(30)
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
	 * @param vo
	 * @param incidentVo
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, 
			                        ApplicationContext ctx, 
			                        IncidentGroupVo newVo, 
			                        IncidentGroupVo originalVo,
			                        IncidentGroupDao igDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance=new CheckPermissionsRules(ctx, rule.name());
				break;
			case 5:
				ruleInstance=new PromptApplySettingsToGroupRules(ctx, rule.name());
				break;
			case 10:
				ruleInstance=new NoDuplicateIncidentsRules(ctx, rule.name());
				break;
			case 15:
				ruleInstance=new CheckIncidentIsRestrictedRules(ctx, rule.name());
				break;
			case 20:
				ruleInstance=new CheckIncidentNotAlreadyAssignedRules(ctx, rule.name());
				break;
			case 25:
				ruleInstance=new PromptRemoveIncidentRules(ctx, rule.name());
				break;
			case 30:
				ruleInstance=new AddIncidentRules(ctx, rule.name());
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(newVo, ObjectTypeEnum.NEW_IG_VO.name());
			ruleInstance.setObject(igDao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
			ruleInstance.setObject(originalVo, ObjectTypeEnum.ORIGINAL_IG_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
