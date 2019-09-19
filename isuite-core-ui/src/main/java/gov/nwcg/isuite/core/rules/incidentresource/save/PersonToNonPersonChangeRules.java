package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class PersonToNonPersonChangeRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="PERSON_TO_NON_PERSON_CHANGE";
	private Boolean skipAddToDialogue=false;

	public PersonToNonPersonChangeRules(ApplicationContext ctx){
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
			if(skipAddToDialogue==false){
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			}
			
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
		 * Defect 3039
		 * 
		 * If the user changes from Person to Non-Person
		 * remove ad payment info if applicable.
		 * Note:  this rule suppliments to PersonTypeChange1Rule.
		 *  
		 */

		/*
		 * Check if user is saving an existing record
		 */
		if(LongUtility.hasValue(vo.getId())){

			// if changing from person to non-person
			if(BooleanUtility.isTrue(irEntity.getResource().isPerson())){
				if(BooleanUtility.isFalse(vo.getResourceVo().getPerson())){
					
					// is the emp type ad?
					if(null != super.vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAdPaymentInfoVo()
							&& LongUtility.hasValue(super.vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAdPaymentInfoVo().getId())){
						
						
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
						coaVo.setIsComplete(true);
						coaVo.setStoredObject(super.vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAdPaymentInfoVo().getId());
						dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
						this.skipAddToDialogue=true;
						
						return _OK;
					}
					
				}
			}
		}
		
		return _OK;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		/*
		 * Determine if we need to remove any time postings?
		 */
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType() == CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){

			Long id=(Long)(coa.getStoredObject());
			
			// delete adinfo record
			irDao.deleteAdPaymentInfo(id);
				
		}
		
	}

}
