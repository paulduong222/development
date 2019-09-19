package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import edu.emory.mathcs.backport.java.util.Collections;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.context.ApplicationContext;

public class CheckSameCodeAgencyRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_SAME_AGENCY_CODE.name();

	public CheckSameCodeAgencyRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

		
			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				
				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
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

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * B.R. 4.0001
		 * 	When the user attempts to add or change an Accounting Code 
		 *  to one that has the same Agency as an Accounting Code that 
		 *  already exists in the system, the system must display a message 
		 *  stating that the Accounting Code already exists. 
		 *  The user must indicate whether they want to use the existing Accounting Code. 
		 *  Yes and No buttons are available. 
		 *  [Message 0120] 
		 *  (Applies to Use Cases 4.0001 and 4.0002.)
		 *  
		 *  4.1	When the user selects Yes in response to message 0120, 
		 *  the system must assign the existing Accounting Code to the selected Incident. 
		 *  (Applies to Use Cases 4.0001 and 4.0002.)
		 *  
		 *  4.2	When the user selects No in response to message 0120, 
		 *  the system must not save the Accounting Code to the Incident. 
		 *  (Applies to Use Cases 4.0001 and 4.0003.)
		 * 
		 */
		
		// only do check when adding new code - trudi
		if(!LongUtility.hasValue(vo.getId())){
			Collection<IncidentAccountCode> iacs = dao.getByAgencyAndCode(
					vo.getAccountCodeVo().getAgencyVo().getId(), 
					vo.getAccountCodeVo().getAccountCode(), 
					incidentEntity.getId());
			
			//Changed to TreeSet because I was seeing duplicates in the ArrayList. -dbudge
			Set<String> otherIncidentsWithACAndAgency = new TreeSet<String>();
			for(IncidentAccountCode iac : iacs) {
				otherIncidentsWithACAndAgency.add(iac.getIncident().getIncidentName());
			}
			
			if(iacs.size() > 0) {
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				courseOfActionVo.setCoaName(_RULE_NAME);
				courseOfActionVo.setPromptVo(
						new PromptVo(
								"text.accountingFireCodes",
								"action.0120",
								new String[]{
										vo.getAccountCodeVo().getAccountCode().toUpperCase(), 
										vo.getAccountCodeVo().getAgencyVo().getAgencyCd(),
										otherIncidentsWithACAndAgency.toString(),
										incidentEntity.getIncidentName()},
								PromptVo._YES | PromptVo._NO));
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				return _FAIL;
			}
			
		}
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
		
		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			
			// add to processed
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
			// continue;
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){

			// cannot continue if prompt was cancel post
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incident", "text.abortProcess" , new String[]{"save incident account code"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
