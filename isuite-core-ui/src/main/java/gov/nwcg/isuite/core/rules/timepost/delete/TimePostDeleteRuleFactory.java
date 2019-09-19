package gov.nwcg.isuite.core.rules.timepost.delete;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class TimePostDeleteRuleFactory {

	public enum ObjectTypeEnum {
		ASSIGNMENT_TIME_POST_ENTITY
		,TIME_POST_DAO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_NOT_ALREADY_INVOICED(5)
		,CHECK_RUN_COST_AUTO(50)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, AssignmentTimePost entity, TimePostDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckNotAlreadyInvoicedRules(ctx);
				break;
			case 10:
				break;
			case 50:
				ruleInstance = new CheckRunCostAutoRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(dao, ObjectTypeEnum.TIME_POST_DAO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.ASSIGNMENT_TIME_POST_ENTITY.name());
		}
		
		return ruleInstance;
	}
	

	
}
