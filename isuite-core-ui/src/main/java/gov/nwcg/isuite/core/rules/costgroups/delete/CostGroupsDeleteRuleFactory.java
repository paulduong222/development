package gov.nwcg.isuite.core.rules.costgroups.delete;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.rules.costgroups.save.CheckPermissionsRules;
import gov.nwcg.isuite.core.vo.CostGroupVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CostGroupsDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_GROUP_VO
		,COST_GROUP_DAO
		,COST_GROUP_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_FOR_ASSIGNED_ACTIVE_RESOURCES(10)
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
			case 10:
				ruleInstance = new CheckForAssignedActiveResources(ctx);
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
