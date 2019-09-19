package gov.nwcg.isuite.core.rules.costprojections.createprojections;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.CostProjectionDao;
import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class RunCreateProjectionRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_PROJECTION_DAO
		,PROJECTION_VO
		,PROJECTION_DAO
		,INCIDENT
		,INCIDENTGROUP
		,TODAY
//		,PROJECTION_START_DATE_STRING
//		,PROJECTION_DAYS_RANGE_STRING
//		,PRJECTION_NAME_STRING
//		,INCIDENT_ID
//		,INCIDENT_GROUP_ID
	}
	
	public enum RuleEnum {
		CHECK_IF_RUNCOST(10)
		,CHECK_START_DATE(20)
		,CHECK_DAYS_RANGE(30)
		,CHECK_PROJECTION_NAME(40)
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
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, CostProjectionDao dao, ProjectionVo vo, 
			Incident incident, IncidentGroup incidentGroup, String today) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckAlreadyRunCostRule(ctx);
				break;
			case 20:
				ruleInstance = new CheckProjectionStartDateRule(ctx);
				break;
			case 30:
				ruleInstance = new CheckProjectionDaysRangeRule(ctx);
				break;
			case 40:
				ruleInstance = new CheckProjectionNameRule(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo,ObjectTypeEnum.PROJECTION_VO.name());
			ruleInstance.setObject(dao,ObjectTypeEnum.COST_PROJECTION_DAO.name());
			ruleInstance.setObject(incident, ObjectTypeEnum.INCIDENT.name());
			ruleInstance.setObject(incidentGroup, ObjectTypeEnum.INCIDENTGROUP.name());
			ruleInstance.setObject(today, ObjectTypeEnum.TODAY.name());
		}
		
		return ruleInstance;
	}
}
