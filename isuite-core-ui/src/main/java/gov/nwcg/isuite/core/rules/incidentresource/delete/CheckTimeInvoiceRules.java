package gov.nwcg.isuite.core.rules.incidentresource.delete;

import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckTimeInvoiceRules extends AbstractIncidentResourceDeleteRule implements IRule {
	public static final String _RULE_NAME="CHECK_TIME_INVOICES";

	public CheckTimeInvoiceRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

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
				
			
		} catch(Exception e){
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
		 * B.R. 5.0004
		 * 
		 * 1.	The system must allow a user to delete a Resource that does not have 
		 *      critical data associated with it, regardless of how the critical data 
		 *      was acquired 
		 *      (e.g., from another User, during a prior now-completed assignment, 
		 *      or a system action). 
		 *      Critical data is defined as data that is part of the financial or historical record. 
		 *      (e.g., Time Postings, Invoices, Injury/Illness Recordings, etc.)
		 *           
		 */
		
		/*
		 * Check for invoices
		 */
		TimePostDao timePostDao = (TimePostDao)context.getBean("timePostDao");
		
		int cnt = timePostDao.getResourcesInvoicedTimePostCount(incidentResourceIds);
		
		if(cnt > 0){
			String msg = "You cannot delete resource(s) that have time invoices.";
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentResources"
											, "info.generic"
											, new String[]{msg}
											, MessageTypeEnum.CRITICAL));

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
