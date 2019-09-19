package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckEmploymentTypesRules extends AbstractCrewRule implements IRule{
	public static final String _RULE_NAME=TimePostCrewRuleFactory.RuleEnum.CHECK_EMPLOYMENT_TYPES.name();

	public CheckEmploymentTypesRules(ApplicationContext ctx)
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
	
		/*
		 *   If the user attempts to post time for a person Resource that does not have 
		 *   an Employment Type defined, the system must not post time and must display 
		 *   a message. 
		 *   
		 *   [Message 0171]
		 */
		Collection<String> failedNames = new ArrayList<String>();
		
		for(AssignmentTime atEntity : super.entities){
			Boolean entityFailed=false;

			if(null == atEntity || null == atEntity.getEmploymentType())
				entityFailed=true;

			if(entityFailed){
				WorkPeriod wp = atEntity.getAssignment().getWorkPeriods().iterator().next();
				Resource resource = wp.getIncidentResource().getResource();
				
				failedNames.add(resource.getFirstName()+ " " + resource.getLastName());
			}
		}
		
		if(CollectionUtility.hasValue(failedNames)){
			/*
			 * This is an error, 
			 * this scenario is not allowed.
			 * 
			 * Build Error message 
			 */
			String names=CollectionUtility.toCommaDelimitedString(failedNames);
			
			dialogueVo.setCourseOfActionVo(
					super.buildErrorCoaVo("text.time"
										  ,"validationerror"
										  ,"error.0171a"
										  ,new String[]{names}));	
			return _FAIL;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
