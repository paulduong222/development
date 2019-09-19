package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentGroupSaveRuleFactory {

	public enum ObjectTypeEnum {
		NEW_INCIDENT_GROUP_VO
		,ORIGINAL_ENTITY_VO
		,INCIDENT_GROUP_DAO
		,INCIDENT_GROUP_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE(5)
		,UNIQUE_NAME_CHECK(10)
		,CHECK_PRIMARY_INCIDENT(12)
		,CHECK_DS_ADDED_TO_GROUP(15)
		//,IG_PROMPT_REMOVE_INCIDENT(20)
		//,IG_INCIDENT_PROMPT_APPLY_QUESTIONS(25)
		,CHECK_CROSS_TIME_POSTINGS(30)
		,CHECK_CROSS_TIME_POSTINGS_INVERSE(33)
		,CHECK_CROSS_COST_RECORDS(36)
		,CHECK_CROSS_COST_RECORDS_INVERSE(39)
		//,CHECK_REF_DATA_CONFLICTS(50)
		,CREATE_DEFAULT_IAP_SETTINGS(60)
		,MOVE_IAP_MASTER_FREQUENCY_LIST(70)
		,SYNC_COST_ACCRUALS(100)
		,CHECK_ADD_INCIDENT_SYNC(120)
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
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentGroupVo newVo, IncidentGroupVo originalVo, IncidentGroup entity,IncidentGroupDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx, rule.name());
				break;
			case 5:
				ruleInstance = new IncidentGroupValidationRules(ctx, rule.name()); 
				break;
			case 10:
				ruleInstance = new UniqueNameRules(ctx, rule.name());
				break;
			case 12:
				ruleInstance = new CheckPrimaryIncidentRules(ctx, rule.name());
				break;
			case 15:
				ruleInstance = new CheckDSAddedToIncGrpAccessList(ctx, rule.name());
				break;
			case 20:
				ruleInstance=new PromptRemoveIncidentRules(ctx, rule.name());
				break;
			case 25:
				ruleInstance=new PromptApplySettingsToGroupRules(ctx, rule.name());
				break;
			case 30:
				ruleInstance=new CheckCrossTimePostingsRules(ctx, rule.name());
				break;
			case 33:
				ruleInstance=new CheckCrossTimePostingsInverseRules(ctx, rule.name());
				break;
			case 36:
				ruleInstance=new CheckCrossCostRecordsRules(ctx, rule.name());
				break;
			case 39:
				ruleInstance=new CheckCrossCostRecordsInverseRules(ctx, rule.name());
				break;
			case 50:
				ruleInstance=new CheckAddIncidentReferenceDataConflictsRules(ctx, rule.name());
				break;
			case 60:
				ruleInstance=new CreateDefaultIapSettingsRules(ctx);
				break;
			case 70:
				ruleInstance = new MoveIAPMasterFrequenceyListRules(ctx);
				break;
			case 100:
				ruleInstance = new CheckAddIncidentAccrualRules(ctx,rule.name());
				break;
			case 120:
				ruleInstance = new CheckAddIncidentSyncRules(ctx,rule.name());
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(newVo, ObjectTypeEnum.NEW_INCIDENT_GROUP_VO.name());
			ruleInstance.setObject(originalVo, ObjectTypeEnum.ORIGINAL_ENTITY_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.INCIDENT_GROUP_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
