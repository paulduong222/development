package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class IncidentResourceSaveRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_RESOURCE_VO
		,WORK_AREA_ID
		,RESOURCE_DATA
		,INCIDENT_RESOURCE_DAO
		,INCIDENT_RESOURCE_ENTITY
	}
	
	/*
	 * Always keep MATCHING_RESOURCE_RULES last
	 */
	public enum RuleEnum {
		COMMON_DATA(0)
		,CHECK_IN_DATA(10)
		,CHECK_UNKNOWN_ITEM_CODE(11)
		,CHECK_FIRST_WORK_DAY(12)
		//,CHECK_ASSIGN_DATE(13)
		,CHECK_FIVE_DAY_RELEASE_DATE(14)
		,CHECK_FIVE_DAY_DEMOB_DATE(16)
		,CHECK_FIVE_DAY_ESTARRIVAL_DATE(17)
		,CHECK_MOB_DATE(18)
		,CHECK_FIVE_DAY_CHECKIN_DATE(19)
		,DEMOB_DATA(20)
		,TIME_DATA(30)
		,COST_DATA(35)
		,COST_DATA_GENERATE_COSTS(37)
		,CHECK_PERMISSIONS(40)
		,CHECK_REQUEST_NUMBER_FORMAT(50)
		,DUPLICATE_REQUEST_NUMBER(60)
		,CHECK_STATUS_CHANGE(70)
		,CHECK_RESOURCE_DATE(80)
		,CHECK_PROPAGATE_PARENT_DATA(90)
		,CHECK_PROPAGATE_FIELD_PROMPT(93)
		,CHECK_LEADER_TYPE_CHANGE(100)
		,CHECK_UNIQUE_VIN_NAME(110)
		,PERSON_TYPE_CHANGE1(120)
		,PERSON_TYPE_CHANGE2(130)
		,PERSON_TYPE_CHANGE3(140)
		,PERSON_TO_NON_PERSON_CHANGE(145)
		,CONTRACTOR_EMP_TYPE_CHANGE1(150)
		,CONTRACTOR_EMP_TYPE_CHANGE1a(152)
		,CONTRACTOR_EMP_TYPE_CHANGE2(160)
		,CONTRACTOR_EMP_TYPE_CHANGE3(170)
		,OF288_EMP_TYPE_CHANGE1(174)
		,CHECK_OTHER_RATE_OVER_HUNDRED(180)
		,NON_CONTRACTOR_NON_PERSON(190)
		//,PROPAGATE_ACTUALS_ONLY(250)
		,GENERATE_DEFAULT_ACCRUAL_CODE(260)
		//,SUBORDINATE_ASSIGN_DATE(270)
		,MODIFY_ASSIGN_DATE(275)
		,CLEAR_RON(280)
		,COST_ACCRUAL_CHANGE(285)
		,COST_ACCRUAL_CHANGE2(290)
		,CHECK_RUN_COST_AUTO(300);
		
		//MJG, 03/23/2012: deprecated, per Dan
//		,CHECK_MATCHING_RESOURCE(45)
		
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
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentResourceVo vo
			, IncidentResourceDao irDao, IncidentResource irEntity) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CommonDataResourceSaveRule(ctx);
				break;
			case 10:
				ruleInstance = new CheckInResourceSaveRule(ctx);
				break;
			case 11:
				ruleInstance = new CheckUnknownItemCodeRules(ctx);
				break;
			case 12:		
				ruleInstance = new CheckFirstWorkDayRules(ctx);
				break;
			case 13:
				//ruleInstance = new CheckAssignDateRules(ctx);
				break;				
			case 14:
				ruleInstance = new CheckFiveDayReleaseDateRules(ctx);
				break;
			case 16:
				//ruleInstance = new CheckFiveDayDemobDateRules(ctx);
				break;
			case 17:
				ruleInstance = new CheckFiveDayEstArrivalDateRules(ctx);
				break;
			case 18:
				ruleInstance = new CheckMobDateRules(ctx);
				break;
			case 19:
				ruleInstance = new CheckFiveDayCheckinDateRules(ctx);
				break;
			case 20:
				ruleInstance = new DemobResourceSaveRule(ctx);
				break;
			case 30:
				ruleInstance = new TimeDataResourceSaveRule(ctx);
				break;
			case 35:
				ruleInstance = new CostDataResourceSaveRule(ctx);
				break;
			case 37:
				// Defect 4365 - Discussed with Donna, turn off rule for now
				// will determine how to handle later
				//ruleInstance = new CostDataGenerateCostRule(ctx);
				break;
			case 40:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 50:
				ruleInstance = new CheckRequestNumberFormatRules(ctx);
				break;
			case 60:
				ruleInstance = new DuplicateRequestNumberRules(ctx);
				break;
			case 70:
				ruleInstance = new CheckStatusChangeRules(ctx);
				break;
			case 80:
				ruleInstance = new CheckResourceDateRules(ctx);
				break;
			case 90:
				ruleInstance = new PropagateParentDataRules(ctx);
				break;
			case 93:
				ruleInstance = new PropagateFieldPromptRules(ctx);
				break;
			case 100:
				ruleInstance = new LeaderTypeRules(ctx);
				break;
			case 110:
				ruleInstance = new UniqueVinNameRules(ctx);
				break;
			case 120:
				ruleInstance = new PersonTypeChange1Rules(ctx);
				break;
			case 130:
				ruleInstance = new PersonTypeChange2Rules(ctx);
				break;
			case 140:
				ruleInstance = new PersonTypeChange3Rules(ctx);
				break;
			case 145:
				ruleInstance = new PersonToNonPersonChangeRules(ctx);
				break;
			case 150:
				ruleInstance = new ContractorEmpTypeChange1Rules(ctx);
				break;
			case 152:
				ruleInstance = new ContractorEmpTypeChange1aRules(ctx);
				break;
			case 160:
				ruleInstance = new ContractorEmpTypeChange2Rules(ctx);
				break;
			case 170:
				ruleInstance = new ContractorEmpTypeChange3Rules(ctx);
				break;
			case 174:
				ruleInstance = new OF288EmpTypeChange1Rules(ctx);
				break;
			case 180:
				ruleInstance = new OtherRateRules(ctx);
				break;
			case 190:
				ruleInstance = new NonContractorNonPersonRules(ctx);
				break;
			case 250:
				//ruleInstance = new PropagateActualsOnlyRules(ctx);
				break;
			case 260:
				//ruleInstance = new DefaultAccrualCodeRules(ctx);
				break;
			case 270:
				//ruleInstance = new SubordinateAssignDateRules(ctx);
				break;
			case 275:
				ruleInstance = new ModifyAssignDateRules(ctx);
				break;				
			case 280:
				ruleInstance = new ClearRONRules(ctx);
				break;
			case 285:
				ruleInstance = new CostAccrualChangeRules(ctx);
				break;
			case 290:
				ruleInstance = new CostAccrualChangeRules2(ctx);
				break;
			case 300:
				ruleInstance = new CheckRunCostAutoRules(ctx);
				break;
				
//			MJG, 03/23/2012: deprecated, per Dan
//			case 45:
//				ruleInstance = new MatchingResourcesRules(ctx);
//				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.INCIDENT_RESOURCE_VO.name());
			//MJG, 03/23/2012: work areas removed
//			ruleInstance.setObject(workAreaId, ObjectTypeEnum.WORK_AREA_ID.name());
			ruleInstance.setObject(irDao, ObjectTypeEnum.INCIDENT_RESOURCE_DAO.name());
			ruleInstance.setObject(irEntity, ObjectTypeEnum.INCIDENT_RESOURCE_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
