package gov.nwcg.isuite.core.rules.rossimport;

import gov.nwcg.isuite.core.vo.RossImportProcessOverheadGroupVo;
import gov.nwcg.isuite.core.vo.RossImportProcessVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.rossimport.MatchIncidentsWizardVo;
import gov.nwcg.isuite.framework.core.rules.IWizardRule;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class FinalConfirmationRules extends AbstractRossImportRule implements IWizardRule{
	private static final String _RULE_NAME="FINAL_CONFIRMATION";

	public FinalConfirmationRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.rules.rossimport.AbstractRossImportRule#validateProceedThisWizard(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public boolean validateProceedThisWizard(DialogueVo dialogueVo) throws Exception{
		RossImportProcessVo ripVo = (RossImportProcessVo)dialogueVo.getResultObject();

		if(MatchIncidentsWizardVo.hasEISuiteIncidentMatch(ripVo.getMatchIncidentsWizardVo())){
			if(CollectionUtility.hasValue(ripVo.getRossImportProcessResourceVos())){
				CourseOfActionVo coavo = dialogueVo.getCourseOfActionByName(MatchResourcesByReqNbrAndNameRules._RULE_NAME.toString());
				if(null != coavo && coavo.getCoaType() != CourseOfActionTypeEnum.NOACTION){
					/*
					 * Verify confirmMatches = true
					 */
					if(!ripVo.getMatchByRequestNumberAndNameWizardVo().getConfirmMatches()){
						dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
						dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"You must confirm matches before continuing."},MessageTypeEnum.CRITICAL));
						
						return false;
					}
				}
			}
		}
		
		/*
		 * Check if all overhead resource groups have been resolved
		 */
		for(RossImportProcessOverheadGroupVo vo : ripVo.getOverheadResourceGroupWizardVo().getOverheadGroupVos()){
			if(!vo.getResolved()){
				dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				dialogueVo.getCourseOfActionVo().setMessageVo(new MessageVo("text.rossImport","info.generic",new String[]{"All Overhead Resource Groups must be resolved before continuing."},MessageTypeEnum.CRITICAL));
					
				return false;
			}
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IWizardRule#syncData(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int syncData(DialogueVo dialogueVo) throws Exception {
		// create a navigate courseofaction
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		if(null != coaVo){
			coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
			coaVo.setNavigateVo(
					buildNavigateVo(
							RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
			dialogueVo.setCourseOfActionVo(coaVo);
		}else{
			return _FAIL;
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			/*
			 * Determine if we already have completed this rule
			 */
			if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){
				
				if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
					
					/*
					 * add rule to processedCoa's
					 */
					dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

					
				}else{

					if(this.validateProceedThisWizard(dialogueVo)){
						// create a navigate courseofaction
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.NAVIGATION);
						coaVo.setNavigateVo(
								buildNavigateVo(
										RossImportProcessRuleFactory.getRuleDestinationByName(_RULE_NAME)));
						dialogueVo.setCourseOfActionVo(coaVo);
					}else{
						return _FAIL;
					}
				}
			
			}else{
				/*
				 * Rule has been completed.
				 * continue, return OK
				 */
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
