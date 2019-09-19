package gov.nwcg.isuite.core.rules.costgroups.saveagencyshare;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
import gov.nwcg.isuite.core.rules.costgroups.save.CheckPermissionsRules;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDayShareVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveAgencyShareRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_GROUP_VO
		,COST_GROUP_DAO
		,COST_GROUP_DAY_SHARE_ENTITY
		,COST_GROUP_DAY_SHARE_VO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(10)
		,CHECK_DAY_SHARE_DATE(20)
		,CHECK_UNIQUE_DATE(30)
		,CHECK_TOTAL_PERCENTAGE(40)
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
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, CostGroupAgencyDayShareVo vo, CostGroupAgencyDayShare entity, CostGroupDao dao, DialogueVo dialogueVo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckDayShareDateRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckUniqueDateRules(ctx);
				break;
			case 40:
				ruleInstance = new CheckTotalPercentageRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.COST_GROUP_DAY_SHARE_VO.name());
			ruleInstance.setObject(entity, ObjectTypeEnum.COST_GROUP_DAY_SHARE_ENTITY.name());
			ruleInstance.setObject(dao, ObjectTypeEnum.COST_GROUP_DAO.name());
		}
		
		return ruleInstance;
	}
}
