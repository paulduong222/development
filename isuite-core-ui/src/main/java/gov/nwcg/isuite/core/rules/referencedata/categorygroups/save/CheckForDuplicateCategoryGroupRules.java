package gov.nwcg.isuite.core.rules.referencedata.categorygroups.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckForDuplicateCategoryGroupRules extends AbstractSaveCategoryGroupRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveCategoryGroupRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_CATEGORY_GROUP.name();

	public CheckForDuplicateCategoryGroupRules(ApplicationContext ctx) {
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
		/*G.	16.0007- Manage Group Category Codes in the Enterprise
		Use Case ID: 16.0007	                Goal: Manage Reference Data for Group Category Codes
		Actors: Global Reference Data Manager	Category: Enterprise

		Business Requirements

			Add Group Category Codes
			
			3.	The system must prevent the user from adding duplicate, 
			    Standard Cost Group Categories to the e-ISuite Enterprise System.
		
		*/

		if(groupCategoryVo.getStandard()) {
			int cnt=super.groupCategoryDao.getDuplicateCodeCount(super.groupCategoryVo.getCode().toUpperCase(),	super.groupCategoryVo.getId());
			if(cnt>0){
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.groupCategory",
								"error.0219",
								new String[]{"Cost Group Category Code", super.groupCategoryVo.getCode().toUpperCase()},
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
