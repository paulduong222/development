package gov.nwcg.isuite.core.rules.usergroup.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class UserGroupSaveIsGroupOwnerRule extends AbstractUserGroupSaveRule implements IRule {
	/*  
	 *      User can not save User Group for a different Group Owner.
	 */
	
	public UserGroupSaveIsGroupOwnerRule(ApplicationContext ctx, String rname) {
		super(ctx, rname);
	}
	
	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try {
			/*
		     * if rule check has been completed, return
		     */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
		
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) 
				return _FAIL;
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
			 
		} catch (Exception e) {
			throw new ServiceException(e);
		}
				
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		if(entity != null && entity.getId() > 0) {
			if (super.getUserSessionVo().getUserId().compareTo(entity.getGroupOwnerId()) != 0) {
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("ServiceException");
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
				coaVo.setIsDialogueEnding(true);
				
				ErrorObject errorObject = new ErrorObject("error.saveUserGroupWithDifferentOwner"); 
				coaVo.getErrorObjectVos().add(errorObject);
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
