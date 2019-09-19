package gov.nwcg.isuite.core.rules.referencedata._209codes.delete;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.domain.impl.Sit209Impl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckIf209CodeIsCurrentlyInUseRules extends AbstractDelete209CodeRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataDelete209CodeRuleFactory.RuleEnum.CHECK_209_CODE_CURRENTLY_IN_USE.name();

	public CheckIf209CodeIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*A.	16.0001 - Manage Standard 209 Codes in the Enterprise
				Use Case ID: 16.0001	 				Goal: Manage Reference Data Standard 209 Codes
				Actors: Global Reference Data Manager	Category: Enterprise
		
				Business Requirements
		
				Delete Standard 209 Codes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete Standard 209 Codes that were not used in the system.
				2.	If a Standard 209 Code was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the code and must allow the user to deactivate it instead.
		*/

		Sit209 sit209 = super.sit209Dao.getById(super.sit209Vo.getId(), Sit209Impl.class);
		
		if(CollectionUtility.hasValue(sit209.getKinds())) {
			PromptVo promptVo = new PromptVo();
			promptVo.setMessageProperty("action.0282");
			promptVo.setTitleProperty("text.209Codes"); 
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
