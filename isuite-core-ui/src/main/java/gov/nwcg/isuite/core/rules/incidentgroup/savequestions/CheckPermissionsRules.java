package gov.nwcg.isuite.core.rules.incidentgroup.savequestions;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckPermissionsRules extends AbstractIncidentGroupSaveQuestionsRule implements IRule{
	
	public CheckPermissionsRules(ApplicationContext ctx, String rName)
	{
		super(ctx,rName);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
			

			/*
			 * This rule will be implemented when user roles are revisited in the future.
			 * Skip rule.
			 */
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(ruleName, true));
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
