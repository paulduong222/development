package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckNonContractorNonPersonRules extends AbstractPersonnelRule implements IRule{
	public static final String _RULE_NAME=TimePostPersonnelRuleFactory.RuleEnum.CHECK_NONCONTRACTOR_NONPERSON.name();

	public CheckNonContractorNonPersonRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		/**
		 * The system must prevent the user from adding Time data to a 
		 * Non-Contracted, Non-Person Resource (e.g., equipment).	  
		 * 
		 */
		if(null != assignmentTimeEntity.getEmploymentType()
				&& assignmentTimeEntity.getEmploymentType() != EmploymentTypeEnum.CONTRACTOR){
				
				if(!incidentResourceEntity.getResource().isPerson()){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.time"
												  ,"validationerror"
												  ,"error.900015"
												  ,null));	
					return _FAIL;
				}
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
