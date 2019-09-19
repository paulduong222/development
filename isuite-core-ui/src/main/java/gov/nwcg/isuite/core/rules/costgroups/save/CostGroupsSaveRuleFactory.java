package gov.nwcg.isuite.core.rules.costgroups.save;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CostGroupsSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_GROUP_VO
		,COST_GROUP_DAO
		,COST_GROUP_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(5)
		,CHECK_UNIQUE_COST_GROUP(10)
		,CHECK_INCIDENT_START_DATE(15)
		,CHECK_DEFAULT_TOTAL_PERCENTAGE(20)
		,SYNC_AGENCY_DATES(30);
		
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
	 * @param dao
	 * @return
	 * @throws Exception
	 */
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, CostGroupVo vo, CostGroup entity, CostGroupDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckUniqueCostGroupRules(ctx);
				break;
			case 15:
				ruleInstance = new CheckIncidentStartDateRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckDefaultTotalPercentageRules(ctx);
				break;
			case 30:
				ruleInstance = new SyncAgencyDatesRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.COST_GROUP_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.COST_GROUP_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.COST_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
}
