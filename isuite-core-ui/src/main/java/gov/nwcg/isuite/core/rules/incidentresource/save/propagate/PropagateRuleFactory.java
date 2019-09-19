package gov.nwcg.isuite.core.rules.incidentresource.save.propagate;

import gov.nwcg.isuite.core.rules.data.ResourceData;
import gov.nwcg.isuite.core.vo.PropagateFieldPromptVo;

import java.util.Collection;

public class PropagateRuleFactory {

	public enum RuleEnum {
		INCIDENT_RULE(0)
		,ACCOUNTING_CODE_RULE(10)
		,ASSIGNMENT_STATUS(20)
		,UNIT_ID(30)
		,AGENCY(40)
		,CHECK_IN_DATE(50)
		,CHECK_IN_TIME(60)
		,ACTUAL_RELEASE_DATE(70)
		,ACTUAL_RELEASE_TIME(80)
		,DEMOB_CITY(90)
		,DEMOB_STATE(100)
		,JETPORT(110)
		,MOB_TRAVEL_METHOD(120)
		,MOB_DATE(130)
		,FIRST_WORK_DAY(140)
		,LENGTH_OF_ASSIGNMENT(150)
		,TENTATIVE_RELEASE_DATE(160)
		,TENTATIVE_RELEASE_TIME(170)
		,DEMOB_TRAVEL_METHOD(175)
		,DEPART_FROM_JETPORT(180)
		,TRAVEL_TIME(190)
		,TENTATIVE_ARRIVAL_DATE(200)
		,TENTATIVE_ARRIVAL_TIME(210)
		,PAYMENT_AGENCY(220)
		,ASSIGN_DATE(230)
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
	
	public static AbstractPropagateRule getInstance(RuleEnum rule
													, Boolean isParentStrikeTeam
													, ResourceData origParentData
													, ResourceData newParentData
													, Collection<ResourceData> childrenResourceData
													, Collection<PropagateFieldPromptVo> propagateFieldPromptVos
													,Long unknownUnitId) throws Exception {

		AbstractPropagateRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new IncidentFieldRule();
				break;
			case 10:
				ruleInstance = new AccountingCodeFieldRule();
				break;
			case 20:
				ruleInstance = new AssignmentStatusFieldRule();
				//System.out.println(newParentData.status.toString());
				break;
			case 30:
				ruleInstance = new UnitIDFieldRule();
				break;
			case 40:
				ruleInstance = new AgencyFieldRule();
				break;
			case 50:
				ruleInstance = new CheckInDateFieldRule();
				break;
			case 60:
				ruleInstance = new CheckInTimeFieldRule();
				break;
			case 70:
				ruleInstance = new ActualReleaseDateFieldRule();
				break;
			case 80:
				ruleInstance = new ActualReleaseTimeFieldRule();
				break;
			case 90:
				ruleInstance = new DemobCityFieldRule();
				break;
			case 100:
				ruleInstance = new DemobStateFieldRule();
				break;
			case 110:
				ruleInstance = new JetportFieldRule();
				break;
			case 120:
				ruleInstance = new MobilizationTravelMethodFieldRule();
				break;
			case 130:
				ruleInstance = new MobilizationDateFieldRule();
				break;
			case 140:
				ruleInstance = new FirstWorkDayFieldRule();
				break;
			case 150:
				ruleInstance = new LengthOfAssignmentFieldRule();
				break;
			case 160:
				ruleInstance = new TentativeReleaseDateFieldRule();
				break;
			case 170:
				ruleInstance = new TentativeReleaseTimeFieldRule();
				break;
			case 175:
				ruleInstance = new DemobTravelMethodFieldRule();
				break;
			case 180:
				ruleInstance = new DepartJetportFieldRule();
				break;
			case 190:
				ruleInstance = new TravelTimeFieldRule();
				break;
			case 200:
				ruleInstance = new TentativeArrivalDateFieldRule();
				break;
			case 210:
				ruleInstance = new TentativeArrivalTimeFieldRule();
				break;
			case 220:
				ruleInstance = new PaymentAgencyFieldRule();
				break;
			case 230:
				ruleInstance = new AssignDateFieldRule();
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.isParentStrikeTeam=isParentStrikeTeam;
			ruleInstance.origParentResourceData=origParentData;
			ruleInstance.newParentResourceData=newParentData;
			ruleInstance.childrenResourceData=childrenResourceData;
			ruleInstance.propagateFieldPromptVos=propagateFieldPromptVos;
			ruleInstance.unknownUnitId=unknownUnitId;
		}
		
		return ruleInstance;
	}
	

	
}
