package gov.nwcg.isuite.core.rules.dbmgmt.savedb;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckDuplicateNameRules extends AbstractDbMgmtSaveRule implements IRule {
	public static final String _RULE_NAME = DbMgmtSaveRuleFactory.RuleEnum.CHECK_DUPLICATE_DB_NAME.name();

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
			Collection<String> dbnames = dao.getDbNames((null != super.entity ? super.entity.getId() : null));
			//Collection<DbAvail> entities = dao.getAll();
			((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");

			/*
			for(DbAvail entity : entities){
				if(entity.getName().equalsIgnoreCase(super.vo.getDatabaseName())){
					// ignore if same id
					if(LongUtility.hasValue(super.vo.getId()) && super.vo.getId().compareTo(entity.getId())==0){
					}else{
						// duplicate name
						isDuplicate=true;
					}
				}
				dao.flushAndEvict(entity);
			}
			*/
			
			for(String name : dbnames){
				if(name.equalsIgnoreCase(super.vo.getDatabaseName())){
					// duplicate name
					isDuplicate=true;
				}
			}
			
			if(isDuplicate==true){
				String msg="The name "+vo.getDatabaseName()+" already exists. Please assign a different name to this database.";
								
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
