package gov.nwcg.isuite.core.rules.referencedata.agencygroups.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class PreventStandardAgencyGroupChangeRules extends AbstractSaveAgencyGroupRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveAgencyGroupRuleFactory.RuleEnum.PREVENT_STANDARD_AGENCY_GROUP_CHANGE.name();

	public PreventStandardAgencyGroupChangeRules(ApplicationContext ctx) {
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
		/*C.	16.0003- Manage Agency Group Codes
		Use Case ID: 16.0003	Goal: Manage Reference Data Agency Group Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
			Edit Agency Group Codes
			
			2.	The system must prevent a user from 
				changing Standard Agency Group Code data.*/

		if(null != agencyGroupEntity) {
			if(agencyGroupEntity.isStandard() == true) {
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.agencyGroup",
								"Standard Agency Group data cannot be changed.",
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
