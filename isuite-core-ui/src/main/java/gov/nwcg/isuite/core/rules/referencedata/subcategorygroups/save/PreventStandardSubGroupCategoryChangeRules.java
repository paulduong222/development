package gov.nwcg.isuite.core.rules.referencedata.subcategorygroups.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class PreventStandardSubGroupCategoryChangeRules extends AbstractSaveSubGroupCategoryRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveSubGroupCategoryRuleFactory.RuleEnum.PREVENT_STANDARD_SUB_GROUP_CATEGORY_CHANGE.name();
	
	public PreventStandardSubGroupCategoryChangeRules(ApplicationContext ctx) {
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
		/*H.	16.0008 - Manage Sub-Group Category Codes
		Use Case ID: 16.0008	Goal: Manage Reference Data for Sub-Group Category Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
			Edit Sub-Group Category Codes
			
			2.	The system must prevent a user from 
				changing Standard Sub-Group Category Code data.*/

		if(null != subGroupCategoryEntity) {
			if(subGroupCategoryEntity.isStandard()) {
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.subGroupCategory",
								"Standard Sub-Group Category data cannot be changed.",
								null,
								MessageTypeEnum.CRITICAL));
				courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
				
				dialogueVo.setCourseOfActionVo(courseOfActionVo);
				
				return _FAIL;
			}
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
