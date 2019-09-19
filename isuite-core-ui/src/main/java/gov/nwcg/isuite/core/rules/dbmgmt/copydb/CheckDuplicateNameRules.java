package gov.nwcg.isuite.core.rules.dbmgmt.copydb;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckDuplicateNameRules extends AbstractDbMgmtCopyRule implements IRule {
	public static final String _RULE_NAME = DbMgmtCopyRuleFactory.RuleEnum.CHECK_DUPLICATE_DB_NAME.name();

	public CheckDuplicateNameRules(ApplicationContext ctx) {
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
		 * The system must prevent the user from naming the new database the same as an existing database 
		 * on the e-ISuite Remote Site Server.
		 */
		DbAvailDao dao=(DbAvailDao)context.getBean("dbAvailDao");
		Boolean isDuplicate=false;
		
		try{
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
			Collection<DbAvail> entities = dao.getAll();
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");

			for(DbAvail entity : entities){
				if(entity.getName().equalsIgnoreCase(super.targetVo.getDatabaseName())){
					// duplicate name
					isDuplicate=true;
				}
				dao.flushAndEvict(entity);
			}
			
			if(isDuplicate==true){
				String msg="The name "+targetVo.getDatabaseName()+" already exists. Please assign a different name to this database.";
								
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.time"
											  ,"validationerror"
											  ,"error.800000"
											  , new String[]{msg}));	
				return _FAIL;
				
			}
		}catch(Exception e){
			throw e;
		}finally{
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
