package gov.nwcg.isuite.core.rules.costprojections.createprojections;

import java.util.Date;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckProjectionStartDateRule extends AbstractCreateProjectionRule implements IRule {
	public static final String _RULE_NAME=RunCreateProjectionRuleFactory.RuleEnum.CHECK_START_DATE.name();
	
	public CheckProjectionStartDateRule(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Check if the projection start date is early than incident start date
		 */
		Date projectionStartDate = projectionVo.getStartDate();
	     
		if(incident != null && projectionStartDate.before(incident.getIncidentStartDate())) {
			dialogueVo.setResultObjectAlternate4("The start date should be later than incident start date.");
			return _FAIL;
		}
		
		if(incidentGroup != null && projectionStartDate.before(incidentGroup.getCreatedDate())) {
			dialogueVo.setResultObjectAlternate4("The start date should be later than incident group start date.");
			return _FAIL;
		}
			
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
