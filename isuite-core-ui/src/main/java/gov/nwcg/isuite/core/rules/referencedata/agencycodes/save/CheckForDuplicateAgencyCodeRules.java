package gov.nwcg.isuite.core.rules.referencedata.agencycodes.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckForDuplicateAgencyCodeRules extends AbstractSaveAgencyCodeRule implements IRule {

	public static final String _RULE_NAME=
		ReferenceDataSaveAgencyCodeRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_AGENCY_CODE.name();

	public CheckForDuplicateAgencyCodeRules(ApplicationContext ctx) {
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
		/*	B. 16.0002- Manage Standard Agency Codes in Enterprise
			Use Case ID: 16.0002	Goal: Manage Reference Data for Standard Agency Codes
			Actors: Global Reference Data Manager	Category: Enterprise

			Business Requirements

				Add Standard Agency Codes
			
					3.	The system must prevent the user from adding duplicate, 
					    Standard Agency Codes to the e-ISuite Enterprise System.
		*/

		int cnt = super.agencyDao.getDuplicateCodeCount(super.agencyVo);
		if(cnt>0){
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.agency",
							"error.0219",
							new String[]{"Agency", super.agencyVo.getAgencyCd().toUpperCase()},
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
