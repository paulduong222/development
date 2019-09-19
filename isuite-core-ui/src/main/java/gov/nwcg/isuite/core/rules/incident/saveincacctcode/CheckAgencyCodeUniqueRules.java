package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckAgencyCodeUniqueRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_AGENCY_CODE_UNIQUE.name();

	public CheckAgencyCodeUniqueRules(ApplicationContext ctx)
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
		String errorMsg="";
		
		/*
		 * B.R. 4.0001
		 *    4.	If the user adds the same Accounting Code that exists for a different 
		 *          Agency and that Accounting Code was not previously added to that Incident, 
		 *          the system must treat that Accounting Code as if it were a new Accounting Code. 
		 *          (Applies to Use Cases 4.0001 and 4.0002.)  
		 * 
		 * Development: This rule is not easy to interpret.
		 *              Assumption is that the same Accounting Code/Agency has to be unique
		 *              to the incident?
		 */
		
		/*
		 * Loop through the incident accounting codes.
		 * 
		 * Check if incidentAccountingCodeVo being saved matches another 
		 * incident account code already added to the incident.
		 */
		for(IncidentAccountCode iac : incidentEntity.getIncidentAccountCodes()){
			
			// do the account codes match?
			if(iac.getAccountCode().getAccountCode().equalsIgnoreCase(vo.getAccountCodeVo().getAccountCode())){
				// do the agencies match?
				if(iac.getAccountCode().getAgencyId().compareTo(vo.getAccountCodeVo().getAgencyVo().getId())==0){
					
					// are we updating the same one or saving a duplicate?
					if(LongUtility.hasValue(vo.getId())){
						
						if(iac.getId().compareTo(vo.getId())==0){
							// updating the same record, proceed
						}else{
							/*
							 * User is updating an incident account code that
							 * matches the code/agency of a different incident
							 * account code already added to the incident.
							 * 
							 * Show error message.
							 */
							showError=true;
						}
					}else{
						/*
						 * User is adding an incident account code that
						 * matches the code/agency of a different incident
						 * account code already added to the incident.
						 * 
						 * Show error message.
						 */
						showError=true;
					}
				}
			}
			
		}
		
		if(showError){
			errorMsg="The system cannot save the incident accounting code.  " +
			 		 "The incident already has a record with the same accounting code and agency.";
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentAccountingCodes"
											, "error.800000"
											, new String[]{errorMsg}
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
