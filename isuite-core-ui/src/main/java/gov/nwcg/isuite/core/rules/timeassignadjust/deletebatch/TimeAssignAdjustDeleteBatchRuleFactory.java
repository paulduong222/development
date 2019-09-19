package gov.nwcg.isuite.core.rules.timeassignadjust.deletebatch;

import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class TimeAssignAdjustDeleteBatchRuleFactory {

	public enum ObjectTypeEnum {
		TIME_ASSIGN_ADJUST_VO
		,TIME_ASSIGN_ADJUST_ENTITY
		,TIME_ASSIGN_ADJUST_DAO
		,CREWIDS
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,PROMPT_DELETE(5)
		,CHECK_ADJUSTMENT_NOT_INVOICED(10)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, TimeAssignAdjustVo vo, TimeAssignAdjust entity, Collection<Long> crewIds,TimeAssignAdjustDao dao) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				//ruleInstance = new PromptDeleteRules(ctx);
				break;
			case 10:
				ruleInstance = new CheckAdjustmentNotInvoicedRules(ctx);
				break;
			case 100:
				ruleInstance = new CheckRunCostAutoRules(ctx);
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.TIME_ASSIGN_ADJUST_VO.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.TIME_ASSIGN_ADJUST_DAO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.TIME_ASSIGN_ADJUST_ENTITY.name());
			ruleInstance.setObject(crewIds, ObjectTypeEnum.CREWIDS.name());
		}
		
		return ruleInstance;
	}
	

	
}
