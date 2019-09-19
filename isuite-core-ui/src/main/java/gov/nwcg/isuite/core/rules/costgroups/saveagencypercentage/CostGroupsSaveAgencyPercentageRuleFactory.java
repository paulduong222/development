package gov.nwcg.isuite.core.rules.costgroups.saveagencypercentage;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CostGroupsSaveAgencyPercentageRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_GROUP_AGENCY_DAY_SHARE_PERCENTAGE_VO
		,COST_GROUP_AGENCY_DAY_SHARE_ENTITY
		,COST_GROUP_AGENCY_DAY_SHARE_PCT_ENTITY
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(10)
		,VALIDATE_DATA(15)
		,CHECK_AGENCY_PERCENTAGE_DATE(20)
		,CHECK_DUPLICATE_AGENCY(25)
		,CHECK_TOTAL_PERCENTAGE(30)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule
							, ApplicationContext ctx
							, CostGroupAgencyDaySharePercentageVo vo
							, CostGroupAgencyDayShare dayShareEntity
							, CostGroupAgencyDaySharePercentage daySharePctEntity
							, DialogueVo dialogueVo) throws Exception {
		
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 15:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckAgencyPercentageDateRules(ctx);
				break;
			case 25:
				ruleInstance = new CheckDuplicateAgencyRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckTotalPercentageRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_PERCENTAGE_VO.name());
			ruleInstance.setObject(dayShareEntity, ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_ENTITY.name());
			ruleInstance.setObject(daySharePctEntity, ObjectTypeEnum.COST_GROUP_AGENCY_DAY_SHARE_PCT_ENTITY.name());
		}
		
		return ruleInstance;
	}

}
