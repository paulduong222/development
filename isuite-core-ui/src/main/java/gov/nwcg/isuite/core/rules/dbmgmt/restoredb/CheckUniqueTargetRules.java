package gov.nwcg.isuite.core.rules.dbmgmt.restoredb;

import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckUniqueTargetRules extends AbstractDbMgmtRestoreRule implements IRule {
	public static final String _RULE_NAME = DbMgmtRestoreRuleFactory.RuleEnum.CHECK_UNIQUE_TARGETDB.name();

	public CheckUniqueTargetRules(ApplicationContext ctx) {
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

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
			}else{
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
				
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._OK) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
			//coaVo.setMessageVo(new MessageVo("text.incidentResource", "text.abortProcess" , new String[]{"save resource"}, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		
		// verify the targetDbName is unique, append numeric suffix if duplicate is found
		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");
		DbAvailDao dao = (DbAvailDao)context.getBean("dbAvailDao");
		Collection<String> dbNames = dao.getDbNames();
		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
		Boolean bContinue=true;
		String newTargetDbName=super.targetDbName.trim().toLowerCase();
		int cnt=0;
		while(bContinue){
			Boolean bFound=false;
			for(String s : dbNames){
				if(s.equalsIgnoreCase(newTargetDbName)){
					bFound=true;
					cnt++;
					newTargetDbName=super.targetDbName.trim().toLowerCase()+"_"+cnt;
				}
			}
			if(bFound==false)
				bContinue=false;
		}

		if(!newTargetDbName.equalsIgnoreCase(targetDbName)){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.database"
								,"info.0320"
								,new String[]{super.targetDbName,newTargetDbName}
								,PromptVo._OK | PromptVo._CANCEL));
			
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObjectAlternate4(newTargetDbName);
		
			return _FAIL;
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
