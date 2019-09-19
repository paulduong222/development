package gov.nwcg.isuite.core.rules.trainingspecialist.settings.savepriorityprogram;

import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.framework.core.rules.IRule;

import org.springframework.context.ApplicationContext;

public class PriorityProgramSaveRuleFactory {
	
	public enum ObjectTypeEnum {
		PRIORITY_PROGRAM_VO,
		PRIORITY_PROGRAM_DAO,
		PRIORITY_PROGRAM_ENTITY
	}
	
	public enum RuleEnum {
		VALIDATE_DATA(5)
		,EDIT_PRIORITY_PROGRAM(10)
		,DUPLICATE_PRIORITY_PROGRAM(15)
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
			PriorityProgramDao priorityProgramDao,
			PriorityProgram priorityProgram) throws Exception {
		
		IRule ruleInstance = null;

		switch(rule.getRuleIdx())
		{
			case 5:
				ruleInstance = new ValidateDataRules(ctx);
				break;
			case 10:
				ruleInstance = new EditPriorityProgramRules(ctx);
				break;
			case 15:
				ruleInstance = new DuplicatePriorityProgramRules(ctx);
				break;
		}

		if(null != ruleInstance){
			ruleInstance.setObject(priorityProgramVo, ObjectTypeEnum.PRIORITY_PROGRAM_VO.name());
			ruleInstance.setObject(priorityProgramDao, ObjectTypeEnum.PRIORITY_PROGRAM_DAO.name());
			ruleInstance.setObject(priorityProgram, ObjectTypeEnum.PRIORITY_PROGRAM_ENTITY.name());
		}

		return ruleInstance;
	}
	
}
