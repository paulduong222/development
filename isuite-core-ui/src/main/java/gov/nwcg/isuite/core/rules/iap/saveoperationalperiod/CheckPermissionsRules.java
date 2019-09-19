package gov.nwcg.isuite.core.rules.iap.saveoperationalperiod;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

public class CheckPermissionsRules extends AbstractOperationalPeriodSaveRule implements IRule {
	public static final String _RULE_NAME=OperationalPeriodSaveRuleFactory.RuleEnum.CHECK_PERMISSIONS.name();
	
	public CheckPermissionsRules(ApplicationContext ctx)
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
			 * This rule will be implemented when user roles are revisited in the future.
			 * Skip rule.
			 */
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME, true));
				
		}catch(Exception e){
			throw new ServiceException(e);
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
