package gov.nwcg.isuite.core.rules.referencedata.unitids.delete;

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

public class CheckIfNonStandardUnitIdIsCurrentlyInUseRules extends AbstractDeleteUnitIdRule implements IRule {
	
	public static final String _RULE_NAME=
		ReferenceDataDeleteUnitIdRuleFactory.RuleEnum.CHECK_IF_NON_STANDARD_UNIT_ID_IS_CURRENTLY_IN_USE.name();
	
	public CheckIfNonStandardUnitIdIsCurrentlyInUseRules(ApplicationContext ctx) {
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
		/*K.	16.0013 - Manage Non-Standard Unit IDs
				Use Case ID: 16.0013	Goal: Manage Reference Data for Unit Identification Codes
				Actors: Data Steward	Category: Enterprise and Site
		
				Business Requirements
		
				Delete Unit ID Codes
		
				1.	The system must allow the Data Steward to delete 
				    Non-Standard Unit ID Codes that are not currently in use.
				2.	If the Non-Standard Unit ID Code is currently in use at the selected 
				    Incident/Incident Group when the Data Steward attempts to delete it, 
				    the system must not delete the Item Code.
		*/

		if(null != organizationEntity) {
			
			if(BooleanUtility.isFalse(organizationEntity.isStandard())) {
				
				if(CollectionUtility.hasValue(organizationEntity.getUsers())
						|| CollectionUtility.hasValue(organizationEntity.getResources())
						|| CollectionUtility.hasValue(organizationEntity.getIncidents())) {
					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.unitId",
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

	}

}
