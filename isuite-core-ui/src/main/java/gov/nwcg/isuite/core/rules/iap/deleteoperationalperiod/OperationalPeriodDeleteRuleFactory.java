package gov.nwcg.isuite.core.rules.iap.deleteoperationalperiod;

import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.persistence.IncidentShiftDao;
import gov.nwcg.isuite.core.vo.IncidentShiftVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class OperationalPeriodDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		OPERATIONAL_PERIOD_VO
		,OPERATIONAL_PERIOD_DAO
		,OPERATIONAL_PERIOD_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_IF_OPERATIONAL_PERIOD_IS_CURRENTLY_IN_USE(10);
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IncidentShiftVo vo, IncidentShift entity, IncidentShiftDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckIfOperationalPeriodIsCurrentlyInUseRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.OPERATIONAL_PERIOD_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.OPERATIONAL_PERIOD_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.OPERATIONAL_PERIOD_DAO.name());
		}
		
		return ruleInstance;
	}

}
