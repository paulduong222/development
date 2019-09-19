package gov.nwcg.isuite.core.rules.dbmgmt.restoredb;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckMaxDBCountRules extends AbstractDbMgmtRestoreRule implements IRule {
	public static final String _RULE_NAME = DbMgmtRestoreRuleFactory.RuleEnum.CHECK_MAX_DB_COUNT.name();

	public CheckMaxDBCountRules(ApplicationContext ctx) {
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
		 * The system must limit the number of databases that can be active in an e-ISuite Site system to five.
		 */
		try{
			DbAvailDao dao=(DbAvailDao)context.getBean("dbAvailDao");
			Boolean isExceedMax=false;

			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
			Collection<DbAvail> entities = dao.getAll();
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");

			if(CollectionUtility.hasValue(entities) && (entities.size()+1)>5)
				isExceedMax=true;

			if(isExceedMax==true){
				String msg="There can only be five active databases in the e-ISuite System. You must remove an existing database from the system before restoring a database.";

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
