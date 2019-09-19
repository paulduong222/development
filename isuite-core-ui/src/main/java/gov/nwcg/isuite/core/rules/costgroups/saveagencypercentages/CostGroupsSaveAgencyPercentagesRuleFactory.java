package gov.nwcg.isuite.core.rules.costgroups.saveagencypercentages;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CostGroupsSaveAgencyPercentagesRuleFactory {
	
	public enum ObjectTypeEnum {
		COST_GROUP_AGENCY_PERCENTAGE_VOS
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,CHECK_TOTAL_PERCENTAGE(5)
		,CHECK_DATE(10)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, Collection<CostGroupAgencyDaySharePercentageVo> vos) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new CheckTotalPercentageRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vos, ObjectTypeEnum.COST_GROUP_AGENCY_PERCENTAGE_VOS.name());
		}
		
		return ruleInstance;
	}

}
