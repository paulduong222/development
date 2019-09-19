package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class PersonTypeChange3Rules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="PERSON_TYPE_CHANGE3";

	public PersonTypeChange3Rules(ApplicationContext ctx){
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
		 * B.R. 6.0002
		 * 
		 * If the user changes the Person designation for a Resource assigned to an Incident 
		 * and one or more invoices exist for the Resource that were included in an Export file 
		 * for the Incident to which they are assigned, 
		 * the system must not change the Person designation and must display a message. 
		 * [Message 0133] (Applies to Use Cases 6.0006 and 6.0008.)
		 */

		/*
		 * Check if user is saving an existing record
		 */
		if(LongUtility.hasValue(vo.getId())){
			
			if(isPersonTypeChanged()){
				
				if(this.hasExportedInvoices(irEntity.getId())){
					
				}
			}
		}
		
		return _OK;
	}
	
	/**
	 * Returns whether or not the resource's person type is getting changed from:
	 *    person to non-person
	 *        or
	 *    non-person to person
	 *    
	 * @return
	 */
	private Boolean isPersonTypeChanged(){
		
		if(null == irEntity)
			return false;
		
		/*
		 * Are the person types different?
		 */
		if(irEntity.getResource().isPerson() != vo.getResourceVo().getPerson()){
			
			return true;
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
