package gov.nwcg.isuite.core.rules.trainingspecialist.settings.save;

import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PriorityProgramSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		PRIORITY_PROGRAM_VO,
		PRIORITY_PROGRAM_DAO
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		,DUPLICATE_PRIORITY_PROGRAM(10)
		;
		
		private int ruleIdx=-1;

		RuleEnum(int idx){
			ruleIdx=idx;
		}

		public int getRuleIdx(){
			return ruleIdx;
		}
	}
	
	public static IRule getInstance(
			RuleEnum rule, 
			ApplicationContext ctx,
			PriorityProgramVo priorityProgramVo,
			PriorityProgramDao priorityProgramDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new DuplicatePriorityProgramRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(priorityProgramVo, ObjectTypeEnum.PRIORITY_PROGRAM_VO.name());
			ruleInstance.setObject(priorityProgramDao, ObjectTypeEnum.PRIORITY_PROGRAM_DAO.name());
		}

		return ruleInstance;
	}
	

}
