package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class TimePostPersonnelRuleFactory {
	
	public enum ObjectTypeEnum {
		ASSIGNMENT_TIME_POST_VO
		,INCIDENT_RESOURCE_ENTITY
		,ASSIGNMENT_TIME_ENTITY
		,TIME_POST_DAO
		,EMPLOYMENT_TYPE
	}
	
	public enum RuleEnum {
		VALIDATION_AD_RULES(5)
		,VALIDATION_FED_RULES(6)
		,VALIDATION_OTHER_RULES(7)
		,CHECK_RESOURCE_STATUS(10)
		,CHECK_NONCONTRACTOR_NONPERSON(15)
		,CHECK_POST_START_INCIDENT_DATE(20)
		,CHECK_POST_END_INCIDENT_DATE(21)
		,CHECK_POST_START_CHECKIN_DATE(23)
		,TIME_POST_DATE_OVERLAP_RULES(30)
		,CHECK_STOP_TIME_RTN_TVL_ONLY(40)
		,DUPLICATE_INVOICED_TIME_POST_RULES(50)
		,DUPLICATE_TIME_POST_RULES(60)
		,DUPLICATE_TIME_POST_RULES_SPECIAL(65)
		,EXECUTE_FINAL_ACTION(100)
		,CHECK_POST_START_ASSIGN_DATE(120)
		,CHECK_PARENT_GEN_COST_FLAG(125)
		,CHECK_CHILD_GEN_COST_FLAG(127)
		,CHECK_RUN_COST_AUTO(130)
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, AssignmentTimePostVo vo,AssignmentTime assignmentTimeEntity, TimePostDao tpDao, String empType) throws Exception {
		IRule ruleInstance = null;
		
		Long incidentResourceId = tpDao.getIncidentResourceId(vo.getAssignmentTimeId());
		
		IncidentResourceDao irDao = (IncidentResourceDao)ctx.getBean("incidentResourceDao");
		IncidentResource irEntity = irDao.getById(incidentResourceId, IncidentResourceImpl.class);
		
		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidationAdRules(ctx);
				break;
			case 6:
				ruleInstance = new ValidationFedRules(ctx);
				break;
			case 7:
				ruleInstance = new ValidationOtherRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckResourceStatusRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckNonContractorNonPersonRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckPostStartIncidentDateRules(ctx);
				break;
			case 21:
				ruleInstance = new CheckPostEndIncidentDateRules(ctx);
				break;

			case 23:
				ruleInstance = new CheckPostStartCheckInDateRules(ctx);
				break;
			case 30:
				ruleInstance = new DateOverlapRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckStopTimeForReturnTravelOnlyRules(ctx);
				break;
			case 50:
				ruleInstance = new DuplicateInvoicedTimePostRules(ctx);
				break;
			case 60:
				ruleInstance = new DuplicateTimePostRules(ctx);
				break;
			case 65:
				ruleInstance = new DuplicateTimePostSpecialRules(ctx);
				break;
			case 100:
				ruleInstance = new ExecuteFinalActionRules(ctx);
				break;
			case 120:
				ruleInstance = new CheckPostStartAssignDateRules(ctx);
				break;
			case 125:
				ruleInstance = new CheckParentGenCostsFlagRules(ctx);
				break;
			case 127:
				ruleInstance = new CheckChildGenCostsFlagRules(ctx);
				break;
			case 130:
				ruleInstance = new CheckRunCostAutoRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.ASSIGNMENT_TIME_POST_VO.name());
			ruleInstance.setObject(irEntity, ObjectTypeEnum.INCIDENT_RESOURCE_ENTITY.name());
			ruleInstance.setObject(assignmentTimeEntity, ObjectTypeEnum.ASSIGNMENT_TIME_ENTITY.name());
			ruleInstance.setObject(tpDao, ObjectTypeEnum.TIME_POST_DAO.name());
			ruleInstance.setObject(empType, ObjectTypeEnum.EMPLOYMENT_TYPE.name());
		}
		
		return ruleInstance;
	}
	

	
}
