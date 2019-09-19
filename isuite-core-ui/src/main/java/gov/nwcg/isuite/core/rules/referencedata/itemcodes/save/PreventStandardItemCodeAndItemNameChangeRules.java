package gov.nwcg.isuite.core.rules.referencedata.itemcodes.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class PreventStandardItemCodeAndItemNameChangeRules extends AbstractSaveItemCodeRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveItemCodeRuleFactory.RuleEnum.PREVENT_STANDARD_ITEM_CODE_AND_ITEM_NAME_CHANGE.name();

	public PreventStandardItemCodeAndItemNameChangeRules(ApplicationContext ctx) {
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
		/*F.	16.0006- Manage Item Codes
		Use Case ID: 16.0006	Goal: Manage Reference Data for Item Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
			Edit an Item Code
			
			2.	When a user edits a Standard Item Code, 
				the system must allow the user to change 
				all data, except the Item Code and the Item Name.*/

		if(null != kindEntity) {
			if(kindEntity.isStandard()) {
				if(!(super.kindVo.getCode().equalsIgnoreCase(super.kindEntity.getCode())) ||
						!(super.kindVo.getDescription().equalsIgnoreCase(super.kindEntity.getDescription()))){
							CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
							courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
							courseOfActionVo.setMessageVo(
									new MessageVo(
											"text.itemCode",
											"The code and name cannot be changed for a standard Item code",
											null,
											MessageTypeEnum.CRITICAL));
							courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
							
							dialogueVo.setCourseOfActionVo(courseOfActionVo);
							
							return _FAIL;
				}
			}
		}
		
		
		return _OK;
	}


	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
