package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class NonContractorNonPersonRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="NON_CONTRACTOR_NON_PERSON";

	public NonContractorNonPersonRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

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
				
		} catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * B.R. 6.0001
		 * 
		 *   1.	If the user selects a Non-Contractor, Non-Person Resource 
		 *      and attempts to save Time data to that Resource, the system 
		 *      must not allow the user to add the Time data and must display 
		 *      a message to the user. 
		 *      [Message 0269]
		 */
		if(!vo.getResourceVo().isPerson() && !vo.getResourceVo().isContracted()) {
			
			if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()) {

				/*
				 * Check if PersonTypeChange1Rules has additionalactionneeded,
				 * if yes, disregard showing this message since the user has
				 * selected yes to reset the the resource's time data 
				 */
				if(super.hasCourseOfActionAdditionalAction(dialogueVo, PersonTypeChange1Rules._RULE_NAME)){
					// do nothing, the data will get reset
				}else{
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo(
									"text.incidentResources"
									,"validationerror"
									,"error.0269"
									,null));	
					
					return _FAIL;
				}
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
