package gov.nwcg.isuite.core.rules.referencedata.itemcodes.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class CheckIfStandardItemCodeIsCurrentlyInUseRules extends AbstractDeleteItemCodeRule implements IRule {
	
	public static final String _RULE_NAME=
		ReferenceDataDeleteItemCodeRuleFactory.RuleEnum.CHECK_IF_STANDARD_ITEM_CODE_IS_CURRENTLY_IN_USE.name();
	
	public CheckIfStandardItemCodeIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*F.	16.0006 - Manage Standard Item Codes in the Enterprise
				Use Case ID: 16.0006	 				Goal: Manage Reference Data for Item Codes
				Actors: Global Reference Data Manager	Category: Enterprise and Site
		
				Business Requirements
		
				Delete Standard Item Codes
			
				1.	The system must allow the Global Reference Data Manager to 
				    delete Standard Item Codes that were not used in the system.
				2.	If a Standard Item Code was used in the system and the Global 
				    Reference Data Manager attempts to delete it, the system must
				    not delete the code and must allow the user to deactivate it instead.
		*/

		if(null != kindEntity) {
			
			if(kindEntity.isStandard()) {
				
				if(CollectionUtility.hasValue(kindEntity.getResourceKinds()) 
						|| CollectionUtility.hasValue(kindEntity.getResourceOthers())
						|| CollectionUtility.hasValue(kindEntity.getAssignments())) {
					
					PromptVo promptVo = new PromptVo();
					promptVo.setMessageProperty("action.0282");
					promptVo.setTitleProperty("text.itemCode"); 
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
		// TODO Auto-generated method stub

	}

}
