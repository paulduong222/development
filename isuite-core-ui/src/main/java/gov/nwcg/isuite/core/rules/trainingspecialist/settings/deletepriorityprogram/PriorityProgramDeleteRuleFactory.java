package gov.nwcg.isuite.core.rules.trainingspecialist.settings.deletepriorityprogram;

import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PriorityProgramDeleteRuleFactory {
	
	public enum ObjectTypeEnum {
		PRIORITY_PROGRAM_VO,
		PRIORITY_PROGRAM_DAO,
		PRIORITY_PROGRAM_ENTITY
	}
	
	public enum RuleEnum {
		IS_CODE_CURRENTLY_IN_USE(5)
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
			PriorityProgram entity,
			PriorityProgramDao priorityProgramDao) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new IsPriorityProgramCurrentlyInUseRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(priorityProgramVo, ObjectTypeEnum.PRIORITY_PROGRAM_VO.name());
			ruleInstance.setObject(priorityProgramDao, ObjectTypeEnum.PRIORITY_PROGRAM_DAO.name());
			ruleInstance.setObject(priorityProgramDao, ObjectTypeEnum.PRIORITY_PROGRAM_ENTITY.name());
		}

		return ruleInstance;
	}

}
