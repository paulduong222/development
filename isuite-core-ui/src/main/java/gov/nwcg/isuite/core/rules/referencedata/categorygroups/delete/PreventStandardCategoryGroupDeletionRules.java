package gov.nwcg.isuite.core.rules.referencedata.categorygroups.delete;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import org.springframework.context.ApplicationContext;

public class PreventStandardCategoryGroupDeletionRules extends AbstractDeleteCategoryGroupRule implements IRule {

	public static final String _RULE_NAME=
		ReferenceDataDeleteCategoryGroupRuleFactory.RuleEnum.PREVENT_STANDARD_CATEGORY_GROUP_DELETION.name();

	public PreventStandardCategoryGroupDeletionRules(ApplicationContext ctx) {
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
		/*G.	16.0007- Manage Group Category Codes
		Use Case ID: 16.0007	Goal: Manage Reference Data for Group Category Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
		Delete Group Category Codes
		
		3.	The system must prevent a user from 
			deleting Standard Group Category Code.*/

		if(null != groupCategoryEntity) {
			
			if(BooleanUtility.isTrue(groupCategoryEntity.isStandard())) {
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.groupCategory",
								"error.0282",
								new String[]{"Group Category Codes"},
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
