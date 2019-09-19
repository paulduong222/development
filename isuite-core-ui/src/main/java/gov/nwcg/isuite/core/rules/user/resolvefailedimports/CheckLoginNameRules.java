package gov.nwcg.isuite.core.rules.user.resolvefailedimports;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckLoginNameRules extends AbstractFailedImportsRule implements IRule {

	public CheckLoginNameRules(ApplicationContext ctx, String rName) {
		super(ctx, rName);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
			.add(super.buildNoActionCoaVo(ruleName,true));

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}

	/**
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		if(null != userDao.getByLoginName(validUserVo.getLoginName())){
			uifEntity.setFailureReason("User with loginname " + validUserVo.getLoginName() + " already exists.");
			uifDao.save(uifEntity);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(ruleName);
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
			coaVo.getErrorObjectVos().add(new ErrorObject("error.0219", "loginname", validUserVo.getLoginName()));
			coaVo.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
