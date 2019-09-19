package gov.nwcg.isuite.core.rules.referencedata.subcategorygroups.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.domain.impl.SubGroupCategoryImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class CheckIfSubGroupCategoryIsCurrentlyInUseRules extends AbstractDeleteSubGroupCategoryRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataDeleteSubGroupCategoryRuleFactory.RuleEnum.CHECK_IF_SUB_GROUP_CATEGORY_CODE_IS_CURRENTLY_IN_USE.name();
	
	public CheckIfSubGroupCategoryIsCurrentlyInUseRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
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
		/*H.	16.0008 - Manage Standard Cost Sub-Group Categories in the Enterprise
				Use Case ID: 16.0008	 				Goal: Manage Reference Data for Cost Sub-Group Category Codes
				Actors: Global Reference Data Manager	Category: Enterprise
		
				Business Requirements
		
				Delete Standard Cost Sub-Group Category Codes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete Standard Cost Sub-Group Category Codes that were not used in the system.
				2.	If a Standard Cost Sub-Group Category Code was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the code and must allow the user to deactivate it instead.
		*/
		
		SubGroupCategory subGroupCategory = super.subGroupCategoryDao.getById(super.subGroupCategoryVo.getId(), SubGroupCategoryImpl.class);
		
		if(CollectionUtility.hasValue(subGroupCategory.getKinds())) {
			PromptVo promptVo = new PromptVo();
			promptVo.setMessageProperty("action.0282");
			promptVo.setTitleProperty("text.subGroupCategory"); 
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
