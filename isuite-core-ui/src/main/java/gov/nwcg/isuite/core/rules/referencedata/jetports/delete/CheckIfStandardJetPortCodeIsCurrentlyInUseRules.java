package gov.nwcg.isuite.core.rules.referencedata.jetports.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class CheckIfStandardJetPortCodeIsCurrentlyInUseRules extends AbstractDeleteJetPortRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataDeleteJetPortRuleFactory.RuleEnum.CHECK_IF_STANDARD_JET_PORT_CODE_IS_CURRENTLY_IN_USE.name();
	
	public CheckIfStandardJetPortCodeIsCurrentlyInUseRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	@Override
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
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*E.	16.0005 - Manage Standard Jetport Codes in Enterprise
				Use Case ID: 16.0005	Goal: Manage Reference Data for Jetport Codes
				Actors: Data Steward	Category: Enterprise and Site
		
				Business Requirements
		
				Delete Standard Jetport Codes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete Standard Jetport Codes that were not used in the system.
				2.	If a Standard Jetport Code was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the code and must allow the user to deactivate it instead.
		*/

		if(null != jetPortEntity) {
			
			if(jetPortEntity.isStandard()) {
				
				if(CollectionUtility.hasValue(jetPortEntity.getWorkPeriods()) || CollectionUtility.hasValue(jetPortEntity.getAirTravels())) {
					PromptVo promptVo = new PromptVo();
					promptVo.setMessageProperty("action.0282");
					promptVo.setTitleProperty("text.jetPort"); 
				    promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
					
					CourseOfActionVo coa = new CourseOfActionVo();
					coa.setCoaName(ruleName);
				    coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
				    coa.setPromptVo(promptVo);
				    
				    dialogueVo.setCourseOfActionVo(coa);
					
					return _FAIL;
				}
			}
		}
		
		return _OK;
	}


	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
