package gov.nwcg.isuite.core.rules.mbadmin.savepopup;

import gov.nwcg.isuite.core.vo.MessageBoardVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class SaveMessagePopUpRuleFactory {
	
	public enum ObjectTypeEnum {
		MESSAGE_BOARD_VO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(10)
		;

		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}

	}
	
	public static IRule getInstance(RuleEnum rule, MessageBoardVo vo, ApplicationContext ctx) throws Exception {
	
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 10:
				ruleInstance = new ValidateDataRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(vo, ObjectTypeEnum.MESSAGE_BOARD_VO.name());
		}

		return ruleInstance;
		
	}

}
