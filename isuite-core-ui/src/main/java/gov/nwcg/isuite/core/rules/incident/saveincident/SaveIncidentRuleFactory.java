package gov.nwcg.isuite.core.rules.incident.saveincident;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.WorkAreaVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveIncidentRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_VO
		,INCIDENT_ENTITY
		,WORK_AREA_VO
		,INCIDENT_DAO
		,ORIGINAL_PDC_VO
		,NEW_PDC_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		, CHECK_START_AFTER_SYSTEM_DATE (7)
		, CHECK_END_BEFORE_START_DATE (8)
		,PROMPT_SAVE_DIFFERENT_WORK_AREA(10)
		,CHECK_DUPLICATE_NBR_YEAR(15)
		,SET_RESTRICTED_ON_NEW_INCIDENT(20)
		,CHECK_PDC_CHANGE(25)
		,CREATE_DEFAULT_SHIFTS(30)
		,CREATE_DEFAULT_IAP_SETTINGS(40)
		,SYNC_PRIORITY_PROGRAMS_WITH_GROUP(50)
		,RESET_RES_ACCRUALS(100)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
		
	}	
	
	public static IRule getInstance(RuleEnum rule, 
	                                ApplicationContext ctx, 
	                                IncidentVo vo,
	                                WorkAreaVo workAreaVo,
	                                Incident incidentEntity,
	                                IncidentDao dao,
	                                OrganizationVo originalPdc,
	                                OrganizationVo newPdc) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 7:
				ruleInstance = new CheckStartAfterSystemDateRules(ctx);
				break;
			case 8:
				ruleInstance = new CheckEndBeforeStartDateRules(ctx);
				break;
			case 10:
				//ruleInstance = new PromptSaveDifferentWorkAreaRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckDuplicateNbrYearRules(ctx);
				break;
			case 20:
				//ruleInstance = new SetRestrictedOnNewIncidentRules(ctx);
				break;
			case 25:
				ruleInstance = new CheckPdcChangeRules(ctx);
				break;
			case 30:
				ruleInstance = new CreateDefaultShiftRules(ctx);
				break;
			case 40:
				ruleInstance = new CreateDefaultIapSettingsRules(ctx);
				break;
			case 50:
				ruleInstance = new SyncPriorityProgramsWithGroupRules(ctx);
				break;
			case 100:
				ruleInstance = new ResetResourceAccrualRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.INCIDENT_VO.name());
			ruleInstance.setObject(incidentEntity, ObjectTypeEnum.INCIDENT_ENTITY.name());
			ruleInstance.setObject(workAreaVo, ObjectTypeEnum.WORK_AREA_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.INCIDENT_DAO.name());
			ruleInstance.setObject(originalPdc, ObjectTypeEnum.ORIGINAL_PDC_VO.name());
			ruleInstance.setObject(newPdc, ObjectTypeEnum.NEW_PDC_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
