package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class TimePostCrewRuleFactory {
	
	public enum ObjectTypeEnum {
		ASSIGNMENT_TIME_POST_VO
		,ASSIGNMENT_TIME_ENTITIES
		,TIME_POST_DAO
		,EMPLOYMENT_TYPE
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATION_DATE(2)
		,VALIDATION_AD_RULES(5)
		,VALIDATION_FED_RULES(6)
		,VALIDATION_OTHER_RULES(7)
		,CHECK_POST_START_INCIDENT_DATE(10)
		,CHECK_POST_END_INCIDENT_DATE(15)
		,CHECK_RESOURCE_STATUSES(20)
		,CHECK_EMPLOYMENT_TYPES(30)
		,CHECK_UPDATE_SINGLE_ALL(35)
		,CHECK_STOP_TIME_RTN_TVL_ONLY(37)
		,DATE_OVERLAP(40)
		,DUPLICATE_INVOICED_TIME_POST(50)
		,DUPLICATE_TIME_POST(60)
		,EXECUTE_FINAL_ACTION_RULES(100)
		,CHECK_POST_START_ASSIGN_DATE(110)
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
	
	/**
	 * @param rule
	 * @param ctx
	 * @param vo
	 * @param entities
	 * @param tpDao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx
									, AssignmentTimePostVo vo
									, Collection<AssignmentTime> entities
									, TimePostDao tpDao) throws Exception {
		IRule ruleInstance = null;
		
		String empType="";
		for(AssignmentTime at : entities){
			if(vo.getAssignmentTimeId().compareTo(at.getId())==0)
				empType=vo.getEmploymentType().name();
		}
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 2:
				ruleInstance = new ValidationDateRules(ctx);
				break;
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
				ruleInstance = new CheckPostStartIncidentDateRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckPostEndIncidentDateRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckResourceStatusesRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckEmploymentTypesRules(ctx);
				break;
			case 35:
				ruleInstance = new CheckUpdateSingleAllRules(ctx);
				break;
			case 37:
				ruleInstance = new CheckStopTimeForReturnTravelOnlyRules(ctx);
				break;
			case 40:
				ruleInstance = new DateOverlapRules(ctx);
				break;
			case 50:
				ruleInstance = new DuplicateInvoicedTimePostRules(ctx);
				break;
			case 60:
				ruleInstance = new DuplicateTimePostRules(ctx);
				break;
			case 100:
				ruleInstance = new ExecuteFinalActionRules(ctx);
				break;
			case 110:
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
			ruleInstance.setObject(entities, ObjectTypeEnum.ASSIGNMENT_TIME_ENTITIES.name());
			ruleInstance.setObject(tpDao, ObjectTypeEnum.TIME_POST_DAO.name());
			ruleInstance.setObject(empType, ObjectTypeEnum.EMPLOYMENT_TYPE.name());
		}
		
		return ruleInstance;
	}
	

	
}
