package gov.nwcg.isuite.core.rules.iap.saveposition;

import gov.nwcg.isuite.core.vo.IapPositionVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PositionSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_POSITION_VO
	}
	
	public enum RuleEnum {
		CHECK_PERMISSIONS(0)
		,VALIDATE_DATA(5)
		,CHECK_UNIQUE_POSITION(20)
		,CHECK_MAX_204_POSITIONS(30)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapPositionVo vo) throws Exception {
		IRule ruleInstance = null;
		
		switch(rule.getRuleIdx())
		{
			case 0:
				ruleInstance = new CheckPermissionsRules(ctx);
				break;
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 20:
				ruleInstance = new CheckUniquePositionRules(ctx);
				break;
			case 30:
				ruleInstance = new CheckMax204PositionsRules(ctx);
				break;
		}
		
		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.IAP_POSITION_VO.name());
		}
		
		return ruleInstance;
	}

}
