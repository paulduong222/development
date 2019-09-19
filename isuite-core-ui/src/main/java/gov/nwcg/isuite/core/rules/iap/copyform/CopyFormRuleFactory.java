package gov.nwcg.isuite.core.rules.iap.copyform;

import gov.nwcg.isuite.core.vo.IapCopyVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class CopyFormRuleFactory {
	
	public enum ObjectTypeEnum {
		IAP_COPY_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		, DUPLICATE_204_BRANCH_DIVISION(20)
		;
		
		private int ruleIdx=-1;
		
		RuleEnum(int idx){
			ruleIdx=idx;
		}
		
		public int getRuleIdx(){
			return ruleIdx;
		}
	}
		
	public static IRule getInstance(RuleEnum rule, ApplicationContext ctx, IapCopyVo vo) throws Exception {
			IRule ruleInstance = null;
			
			switch(rule.getRuleIdx())
			{
				case 10:
					ruleInstance = new ValidateDataRules(ctx);
					break;
				case 20:
					ruleInstance = new Duplicate204BranchDivisionRules(ctx);
					break;
			}
			
			if(null != ruleInstance){
				ruleInstance.setObject(vo, ObjectTypeEnum.IAP_COPY_VO.name());
			}
			
			return ruleInstance;
	}

}
