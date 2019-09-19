package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckOnly1FedAgencyRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_ONLY_1_FED_AGENCY.name();

	public CheckOnly1FedAgencyRules(ApplicationContext ctx)
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
		Boolean showError=false;
		
		/*
		 * B.R. 4.0001
		 *     If a Fire-Wild Fire Incident has a FED Agency with a four digit Accounting Code 
		 *     and the user attempts to add another Accounting Code for the FED Agency, 
		 *     the system must display a message indicating that a Fire Code was already
		 *     assigned to that Incident. 
		 *     [Message 0161]
		 *     
		 * 
		 * Development: Check for Event Type WF.
		 * 				Check that there is only 1 incident acct code with FED Agency.
		 */
		if(incidentEntity.getEventType().getEventTypeCode().equals("WF")){
			
			if(vo.getAccountCodeVo().getAgencyVo().getAgencyCd().equals("FED")){
				
				for(IncidentAccountCode iac : incidentEntity.getIncidentAccountCodes()){
					
					if(iac.getAccountCode().getAgency().getAgencyCode().equals("FED")){
						
						/*
						 * if user is not updating the same iac, show error
						 */
						if(iac.getId().compareTo(vo.getId())!=0){
							showError=true;
						}
					}
				}
				
			}
		}
		
		if(showError){
		
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentAccountingCodes"
											, "error.0161"
											, null
											, MessageTypeEnum.CRITICAL));

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
