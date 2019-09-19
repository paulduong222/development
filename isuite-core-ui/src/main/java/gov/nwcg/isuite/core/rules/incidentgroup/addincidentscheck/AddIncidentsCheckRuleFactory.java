package gov.nwcg.isuite.core.rules.incidentgroup.addincidentscheck;

import java.util.Collection;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class AddIncidentsCheckRuleFactory {

	public enum ObjectTypeEnum {
		INCIDENT_GROUP_VO
		,INCIDENT_GROUP_DAO
		,INCIDENT_GRID_VOS
		}
	
	public enum RuleEnum {
		CHECK_REF_DATA_CONFLICTS(10)
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
			                        IncidentGroupVo incidentGroupVo, 
			                        Collection<IncidentGridVo> incGridVos,
			                        IncidentGroupDao igDao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckRefDataConflictsRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(incidentGroupVo, ObjectTypeEnum.INCIDENT_GROUP_VO.name());
			ruleInstance.setObject(igDao, ObjectTypeEnum.INCIDENT_GROUP_DAO.name());
			ruleInstance.setObject(incGridVos,ObjectTypeEnum.INCIDENT_GRID_VOS.name());
		}
		
		return ruleInstance;
	}
	

	
}
