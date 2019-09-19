package gov.nwcg.isuite.core.rules.referencedata.itemcodes.delete;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class CheckIfNonStandardItemCodeIsCurrentlyInUseRules extends AbstractDeleteItemCodeRule implements IRule {
	
	public static final String _RULE_NAME=
		ReferenceDataDeleteItemCodeRuleFactory.RuleEnum.CHECK_IF_NON_STANDARD_ITEM_CODE_IS_CURRENTLY_IN_USE.name();
	
	public CheckIfNonStandardItemCodeIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*M.	16.0015 - Manage Non-Standard Item Codes
				Use Case ID: 16.0015	Goal: Manage Reference Data for Item Codes
				Actors: Data Steward	Category: Enterprise and Site
		
				Business Requirements
		
				Delete Item Codes
		
				1.	The system must allow the Data Steward to delete 
				    Non-Standard Item Codes that are not currently in use.
				2.	If the Non-Standard Item Code is currently in use at the selected 
				    Incident/Incident Group when the Data Steward attempts to delete it, 
				    the system must not delete the Item Code.
		*/

		if(null != kindEntity) {
			
			if(BooleanUtility.isFalse(kindEntity.isStandard())) {
				
				if(CollectionUtility.hasValue(kindEntity.getResourceKinds()) 
						|| CollectionUtility.hasValue(kindEntity.getResourceOthers())
						|| CollectionUtility.hasValue(kindEntity.getAssignments())) {
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.itemCode",
									"error.0337",
									null,
									MessageTypeEnum.CRITICAL));
					courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
					
					dialogueVo.	setCourseOfActionVo(courseOfActionVo);
					
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
