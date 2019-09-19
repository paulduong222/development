package gov.nwcg.isuite.core.rules.user.resolvefailedimports;

import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import org.springframework.context.ApplicationContext;

public class CheckPrivUserRules extends AbstractFailedImportsRule implements IRule {

	public CheckPrivUserRules(ApplicationContext ctx, String rName) {
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
		
		/*
		 * if user is a priv user, verify loginName starts with ad.
		 * 
		 * if user is not a priv user, verify loginName does not start with ad.
		 * 
		 */
		Boolean isPrivUser = false;
		
		for(SystemRoleVo srvo : validUserVo.getUserRoleVos()){
			if(BooleanUtility.isTrue(srvo.getPrivilegedRole()))
			{
				isPrivUser=true;
				break;
			}
		}
		
		if(isPrivUser==true){
			// verify starts with ad
			if(!validUserVo.getLoginName().startsWith("ad.")){
				uifEntity.setFailureReason("Privileged User login name must start with ad. ");
				uifDao.save(uifEntity);
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(ruleName);
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
				coaVo.getErrorObjectVos().add(new ErrorObject("info.generic", new String[]{(validUserVo.getLoginName())+"Privileged User login names must start with ad."} ));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
				
			}
		}else{
			// verify does not start with ad
			if(validUserVo.getLoginName().startsWith("ad.")){
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(ruleName);
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
				coaVo.getErrorObjectVos().add(new ErrorObject("info.generic", new String[]{(validUserVo.getLoginName())+"No-Privileged User login names cannot start with ad."} ));
				coaVo.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
				
			}
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
