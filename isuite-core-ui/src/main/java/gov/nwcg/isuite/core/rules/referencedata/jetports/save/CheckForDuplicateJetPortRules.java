package gov.nwcg.isuite.core.rules.referencedata.jetports.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckForDuplicateJetPortRules extends AbstractSaveJetPortRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveJetPortRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_JET_PORT.name();

	public CheckForDuplicateJetPortRules(ApplicationContext ctx) {
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
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*E.	16.0005- Manage Jetport Codes Codes in the Enterprise
		Use Case ID: 16.0005	                Goal: Manage Reference Data for Jetport Codes
		Actors: Global Reference Data Manager	Category: Enterprise

		Business Requirements

			Add Jetport Codes
				
			3.	The system must prevent the user from adding duplicate, 
			    Standard Jetport Codes to the e-ISuite Enterprise System.
				
		*/
		
		int cnt=super.jetPortDao.getDuplicateCodeCount(super.jetPortVo);
		if(cnt>0){
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.jetPort",
							"error.0219",
							new String[]{"Jetport", super.jetPortVo.getCode().toUpperCase()},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
			
		}
		
		return _OK;
	}


	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
