package gov.nwcg.isuite.core.rules.timepost.contractor;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class TimePostContractorRuleFactory {
	
	public enum ObjectTypeEnum {
		ASSIGNMENT_TIME_POST_VO
		,ASSIGNMENT_TIME_POST_SPECIAL_VO
		,INCIDENT_RESOURCE_ENTITY
		,ASSIGNMENT_TIME_ENTITY
		,TIME_POST_DAO
		,POST_TYPE
	}
	
	public enum RuleEnum {
		VALIDATE_PRIMARY_DATA(3)
		,VALIDATE_SPECIAL_DATA(5)
		,VALIDATE_GUARANTEE_DATA(7)
		,CHECK_RESOURCE_STATUS(10) //MJG: changed from 1 to accomodate HAS_PRIMARY_RATE
		,CHECK_POST_START_CHECKIN_DATE(15)
		,CHECK_POST_START_HIRED_DATE(17)
	    , HAS_PRIMARY_RATE(20) //MJG: added
		,DUPLICATE_INVOICED_TIME_POST(25)
		,CONTRACTOR_BOTH_VALIDATION(30)
		,CHECK_PRIMARY_POST_START_INCIDENT_DATE(40)
		,CHECK_PRIMARY_POST_END_INCIDENT_DATE(45)
		,CHECK_SPECIAL_POST_START_INCIDENT_DATE(50)
		,CHECK_PRIMARY_END_DATE_NOT_BEFORE_START_DATE(60)
		,CHECK_SPECIAL_END_DATE_NOT_BEFORE_START_DATE(70)
		,CONTRACTOR_PRIMARY_MAX_DAILY_DAYS_VALIDATION(80)
		,CONTRACTOR_SPECIAL_MAX_DAILY_DAYS_VALIDATION(90)
		,CONTRACTOR_PRIMARY_GUARANTEE_DUPLICATE(100)
		,CONTRACTOR_GUARANTEE_PRIMARY_DUPLICATE(110)
		,CONTRACTOR_PRIMARY_DAILY_DUPLICATE(120)
		,CONTRACTOR_PRIMARY_MILEAGE_DUPLICATE(130)
		,CONTRACTOR_PRIMARY_EACH_DUPLICATE(140)
		,CONTRACTOR_PRIMARY_HOURLY_OVERLAP(150)
		,CONTRACTOR_PRIMARY_HOURLY_DUPLICATE(160)
		,CONTRACTOR_SPECIAL_GUARANTEE_DUPLICATE(170)
		,CONTRACTOR_SPECIAL_DAILY_DUPLICATE(180)
		,CONTRACTOR_SPECIAL_MILEAGE_DUPLICATE(190)
		,CONTRACTOR_SPECIAL_EACH_DUPLICATE(200)
		,CONTRACTOR_SPECIAL_HOURLY_OVERLAP(210)
		,CONTRACTOR_SPECIAL_HOURLY_DUPLICATE(220)
		,EXECUTE_FINAL_ACTION(300)
		,CHECK_POST_START_ASSIGN_DATE(350)
		,CHECK_RUN_COST_AUTO(360)
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx
									, AssignmentTimePostVo primaryVo, AssignmentTimePostVo specialVo, AssignmentTime assignmentTimeEntity
									, IncidentResource irEntity, TimePostDao tpDao, String postType) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 3:
				ruleInstance = new ValidatePrimaryDataRule(ctx); 
				break;
			case 5:
				ruleInstance = new ValidateSpecialDataRule(ctx); 
				break;
			case 7:
				ruleInstance = new ValidateGuaranteeDataRule(ctx); 
				break;
			case 10: //MJG: changed from 1 to accomodate HAS_PRIMARY_RATE
				ruleInstance = new CheckResourceStatusRules(ctx); 
				break;
			case 15:
				ruleInstance = new CheckPostStartCheckInDateRules(ctx);
				break;
			case 17:
				ruleInstance = new CheckPostStartHiredDateRules(ctx);
				break;
			case 20: //MJG: added
				ruleInstance = new HasPrimaryRateRule(ctx);
				break;
			case 25: 
				ruleInstance = new DuplicateInvoicedTimePostRules(ctx);
				break;
			case 30:
				ruleInstance = new ContractorBothValidationRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckPrimaryStartIncidentDateRules(ctx);
				break;
			case 45:
				ruleInstance = new CheckPrimaryEndIncidentDateRules(ctx);
				break;
			case 50:
				ruleInstance = new CheckSpecialStartIncidentDateRules(ctx);
				break;
			case 60:
				ruleInstance = new ContractorPrimaryEndStartDateRules(ctx);
				break;
			case 70:
				ruleInstance = new ContractorSpecialEndStartDateRules(ctx);
				break;
			case 80:
				ruleInstance = new ContractorPrimaryMaxDailyDaysRules(ctx);
				break;
			case 90:
				ruleInstance = new ContractorSpecialMaxDailyDaysRules(ctx);
				break;
			case 100:
				ruleInstance = new ContractorPrimaryGuaranteeDuplicateRules(ctx);
				break;
			case 110:
				ruleInstance = new ContractorGuaranteePrimaryDuplicateRules(ctx);
				break;
			case 120:
				ruleInstance = new ContractorPrimaryDailyDuplicateRules(ctx);
				break;
			case 130:
				ruleInstance = new ContractorPrimaryMileageDuplicateRules(ctx);
				break;
			case 140:
				ruleInstance = new ContractorPrimaryEachDuplicateRules(ctx);
				break;
			case 150:
				ruleInstance = new ContractorPrimaryHourlyOverlapRules(ctx);
				break;
			case 160:
				ruleInstance = new ContractorPrimaryHourlyDuplicateRules(ctx);
				break;
			case 170:
				ruleInstance = new ContractorSpecialGuaranteeDuplicateRules(ctx);
				break;
			case 180:
				ruleInstance = new ContractorSpecialDailyDuplicateRules(ctx);
				break;
			case 190:
				ruleInstance = new ContractorSpecialMileageDuplicateRules(ctx);
				break;
			case 200:
				ruleInstance = new ContractorSpecialEachDuplicateRules(ctx);
				break;
			case 210:
				ruleInstance = new ContractorSpecialHourlyOverlapRules(ctx);
				break;
			case 220:
				ruleInstance = new ContractorSpecialHourlyDuplicateRules(ctx);
				break;
			case 300:
				ruleInstance = new ExecuteFinalActionRules(ctx);
				break;
			case 350:
				ruleInstance = new CheckPostStartAssignDateRules(ctx);
				break;
			case 360:
				ruleInstance = new CheckRunCostAutoRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(primaryVo, ObjectTypeEnum.ASSIGNMENT_TIME_POST_VO.name());
			ruleInstance.setObject(specialVo, ObjectTypeEnum.ASSIGNMENT_TIME_POST_SPECIAL_VO.name());
			ruleInstance.setObject(irEntity, ObjectTypeEnum.INCIDENT_RESOURCE_ENTITY.name());
			ruleInstance.setObject(assignmentTimeEntity, ObjectTypeEnum.ASSIGNMENT_TIME_ENTITY.name());
			ruleInstance.setObject(tpDao, ObjectTypeEnum.TIME_POST_DAO.name());
			ruleInstance.setObject(postType, ObjectTypeEnum.POST_TYPE.name());
		}
		
		return ruleInstance;
	}
	

	
}
