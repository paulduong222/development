package gov.nwcg.isuite.core.rules.dbmgmt.savedb;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckAdditionalBackupFolderRules extends AbstractDbMgmtSaveRule implements IRule {
	public static final String _RULE_NAME = DbMgmtSaveRuleFactory.RuleEnum.CHECK_ADDITIONAL_BACKUP_FOLDER.name();

	public CheckAdditionalBackupFolderRules(ApplicationContext ctx) {
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
		 * Validate the additional backup destination folder is a valid folder
		 */
		try{
			if(BooleanUtility.isTrue(vo.getIsAutoBackup())){
				if(StringUtility.hasValue(vo.getAdditionalBackupDestination())){
					if(BooleanUtility.isFalse(FileUtil.isDir(vo.getAdditionalBackupDestination()))){
						String msg="The additional backup destination is an invalid server directory.";
						
						dialogueVo.setCourseOfActionVo(
								super.buildErrorCoaVo("text.database"
													  ,"validationerror"
													  ,"error.800000"
													  , new String[]{msg}));	
						return _FAIL;
					}
				}
			}
				
		}catch(Exception e){
			throw e;
		}finally {
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
