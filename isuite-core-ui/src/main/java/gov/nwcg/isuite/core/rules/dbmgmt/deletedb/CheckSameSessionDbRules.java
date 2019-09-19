package gov.nwcg.isuite.core.rules.dbmgmt.deletedb;

import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckSameSessionDbRules extends AbstractDbMgmtDeleteRule implements IRule {
	public static final String _RULE_NAME = DbMgmtDeleteRuleFactory.RuleEnum.CHECK_SAME_SESSION_DB.name();

	public CheckSameSessionDbRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Check if database being removed is same database as the one the user is currently logged into.
		 */
		try{
			UserSessionVo sessionVo=(UserSessionVo)context.getBean("userSessionVo");
			String sessionDb=sessionVo.getSiteDatabaseName().toLowerCase();
			
			if(super.vo.getDatabaseName().toLowerCase().equals(sessionDb)){
				String msg="Cannot delete your session database.";
				
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.database"
											  ,"validationerror"
											  ,"error.800000"
											  , new String[]{msg}));	
				return _FAIL;
			}
		}catch(Exception e){
			throw e;
		}finally {
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
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
