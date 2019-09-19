package gov.nwcg.isuite.core.rules.timeassignadjust.savebatch;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class TimeAssignAdjustSaveBatchRuleFactory {

	public enum ObjectTypeEnum {
		TIME_ASSIGN_ADJUST_VO
		,TIME_ASSIGN_ADJUST_ENTITY
		,TIME_ASSIGN_ADJUST_DAO
		,ASSIGNMENT_ENTITY
		,INCIDENT_RESOURCE_VO
		,CREWIDS
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_RESOURCE_EMP_TYPE(3)
		,VALIDATE_DATA(5)
		,CHECK_RESOURCE_STATUS(8)
		,CHECK_FOR_EXISTING_TIME_POSTINGS_BATCH(10)
		,CHECK_DATE_NOT_ALREADY_INVOICED(15)
		,CHECK_ADJUSTMENT_DATE_BATCH(20)
		,CHECK_POST_START_HIRED_DATE(25)
		,CHECK_POST_START_INCIDENT_DATE(30)
		,CHECK_COST_AUTORUN(100)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentResourceVo irvo,
								TimeAssignAdjustVo vo, TimeAssignAdjust entity, Assignment assignmentEntity
								, Collection<Long> crewIds, TimeAssignAdjustDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 3:
				//ruleInstance = new CheckResourceEmploymentTypeRules(ctx);
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 8:
				//ruleInstance = new CheckResourceStatusRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckForExistingTimePostingsRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckDateNotAlreadyInvoicedRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckAdjustmentDateRules(ctx);
				break;
			case 25:
				//ruleInstance = new CheckPostStartHiredDateRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckPostStartIncidentDateRules(ctx);
				break;
			case 100:
				//ruleInstance = new CheckRunCostAutoRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(crewIds, ObjectTypeEnum.CREWIDS.name());
			ruleInstance.setObject(vo, ObjectTypeEnum.TIME_ASSIGN_ADJUST_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.TIME_ASSIGN_ADJUST_DAO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.TIME_ASSIGN_ADJUST_ENTITY.name());
			ruleInstance.setObject(assignmentEntity, ObjectTypeEnum.ASSIGNMENT_ENTITY.name());
			ruleInstance.setObject(irvo, ObjectTypeEnum.INCIDENT_RESOURCE_VO.name());
		}
		
		return ruleInstance;
	}
	

	
}
