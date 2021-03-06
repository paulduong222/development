package gov.nwcg.isuite.core.rules.referencedata.subcategorygroups.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class CheckForDuplicateSubGroupCategoryRules extends AbstractSaveSubGroupCategoryRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveSubGroupCategoryRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_SUB_GROUP_CATEGORY.name();
	
	public CheckForDuplicateSubGroupCategoryRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
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
		/*H.	16.0008- Manage Sub-Group Category Codes in the Enterprise
		Use Case ID: 16.0008	                Goal: Manage Reference Data for Sub-Group Category Codes
		Actors: Global Reference Data Manager	Category: Enterprise

		Business Requirements

			Add Sub-Group Category Codes
			
			3.	The system must prevent the user from adding duplicate, 
			    Standard Cost Sub-Group Categories to the e-ISuite Enterprise System.
		
		*/

		if(subGroupCategoryVo.getStandard()) {
			int cnt=super.subGroupCategoryDao.getDuplicateCodeCount(super.subGroupCategoryVo.getCode().toUpperCase(),	super.subGroupCategoryVo.getId());
			if(cnt>0){
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.subGroupCategory",
								"error.0219",
								new String[]{"Cost Sub-Group Category", super.subGroupCategoryVo.getCode().toUpperCase()},
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
