package gov.nwcg.isuite.core.rules.reports.customreports.importing;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckUserRolesRules extends AbstractCustomReportImportRule implements IRule {
	
	private static final String USER_NOT_PERMITTED = 
		"You do not have the proper user roles to import this report.";
	
	public CheckUserRolesRules(ApplicationContext ctx, String rname) {
		super(ctx, rname);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) {
			    return _FAIL;
			} 	
			
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
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		//does the user have the appropriate role
		if(!isViewAllowedForUser(super.reportVo.getCustomReportViewVo().getRoleVos(), super.userVo.getUserRoleVos())) {
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.customReports"
					, "info.generic"
					, new String[]{USER_NOT_PERMITTED}
					, MessageTypeEnum.CRITICAL));
			
		    coaVo.setIsDialogueEnding(Boolean.TRUE);
		    
		    dialogueVo.setCourseOfActionVo(coaVo);
		    
			return _FAIL;
		}
		
		return _OK;
	}
	
	/**
	 * @param viewRoles
	 * @param userRoles
	 * @return
	 */
	private Boolean isViewAllowedForUser(Collection<SystemRoleVo> viewRoles, Collection<SystemRoleVo> userRoles){
		if(null != viewRoles && null != userRoles) {
			for(SystemRoleVo roleVo : viewRoles){
				for(SystemRoleVo userRole : userRoles){
					if(roleVo.getRoleName().equalsIgnoreCase(userRole.getRoleName())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub
	}

}
