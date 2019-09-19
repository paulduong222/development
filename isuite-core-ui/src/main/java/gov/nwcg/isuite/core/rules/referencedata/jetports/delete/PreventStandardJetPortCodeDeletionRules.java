package gov.nwcg.isuite.core.rules.referencedata.jetports.delete;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import org.springframework.context.ApplicationContext;

public class PreventStandardJetPortCodeDeletionRules extends AbstractDeleteJetPortRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataDeleteJetPortRuleFactory.RuleEnum.PREVENT_STANDARD_JET_PORT_CODE_DELETION.name();

	public PreventStandardJetPortCodeDeletionRules(ApplicationContext ctx) {
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
		/*E.	16.0005- Manage Jetport Codes
		Use Case ID: 16.0005	Goal: Manage Reference Data for Jetport Codes
		Actors: Data Steward	Category: Enterprise and Site

		Business Requirements
		
		Delete Jetport Codes
		
		3.	The system must prevent a user from deleting Standard Jetport Codes.*/
		
		if(null != jetPortEntity){
			
			if(BooleanUtility.isTrue(jetPortEntity.isStandard())) {
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				courseOfActionVo.setMessageVo(
						new MessageVo(
								"text.jetPortCode",
								"error.0282",
								new String[]{"Jet Port Codes"},
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
