package gov.nwcg.isuite.core.rules.referencedata.adrates.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.core.domain.impl.RateClassRateImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class CheckIfADRateIsCurrentlyInUseRules extends AbstractDeleteADRateRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataDeleteADRateRuleFactory.RuleEnum.CHECK_IF_AD_RATE_IS_CURRENTLY_IN_USE.name();
	
	public CheckIfADRateIsCurrentlyInUseRules(ApplicationContext ctx) {
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
	
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*I.	16.0009 - Manage AD Classes in the Enterprise
				Use Case ID: 16.0009	 				Goal: Manage Reference Data for AD Classes
				Actors: Global Reference Data Manager	Category: Enterprise
		
				Business Requirements
		
				Delete AD Classes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete AD Classes that were not used in the system
				2.	If an AD Class was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the AD Class and must allow the user to deactivate it instead.
		*/

		RateClassRate rateClassRate = super.rateClassRateDao.getById(super.rateClassRateVo.getId(), RateClassRateImpl.class);
		
		if(CollectionUtility.hasValue(rateClassRate.getAssignmentTimePosts())
				|| CollectionUtility.hasValue(rateClassRate.getAdPaymentInfos())
				|| (super.rateClassRateDao.isTrainingRate(rateClassRate.getId()))) {
			
			PromptVo promptVo = new PromptVo();
			promptVo.setMessageProperty("action.0282");
			promptVo.setTitleProperty("text.adClass"); 
		    promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
			
		    CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
		    coa.setPromptVo(promptVo);
		    
		    dialogueVo.setCourseOfActionVo(coa);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
