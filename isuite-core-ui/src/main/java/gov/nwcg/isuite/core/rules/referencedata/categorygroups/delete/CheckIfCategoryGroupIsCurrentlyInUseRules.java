package gov.nwcg.isuite.core.rules.referencedata.categorygroups.delete;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.domain.impl.GroupCategoryImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckIfCategoryGroupIsCurrentlyInUseRules extends AbstractDeleteCategoryGroupRule implements IRule {

	public static final String _RULE_NAME=
		ReferenceDataDeleteCategoryGroupRuleFactory.RuleEnum.CHECK_IF_CATEGORY_GROUP_IS_CURRENTLY_IN_USE.name();

	public CheckIfCategoryGroupIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*G.	16.0007 - Manage Standard Cost Group Categories in the Enterprise
				Use Case ID: 16.0007	 				Goal: Manage Reference Data for Cost Group Category Codes
				Actors: Global Reference Data Manager	Category: Enterprise
		
				Business Requirements
		
				Delete Standard Cost Group Codes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete Standard Cost Group Codes that were not used in the system.
				2.	If a Standard Cost Group Code was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the code and must allow the user to deactivate it instead.
		*/
		
		GroupCategory groupCategory = super.groupCategoryDao.getById(super.groupCategoryVo.getId(),	GroupCategoryImpl.class);
		
		if(CollectionUtility.hasValue(groupCategory.getKinds())) {
			PromptVo promptVo = new PromptVo();
			promptVo.setMessageProperty("action.0282");
			promptVo.setTitleProperty("text.groupCategory"); 
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
