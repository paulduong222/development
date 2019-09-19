package gov.nwcg.isuite.core.rules.referencedata.unitids.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckForDuplicateUnitIdRules extends AbstractSaveUnitIdRule implements IRule {

	public static final String _RULE_NAME=ReferenceDataSaveUnitIdRuleFactory.RuleEnum.CHECK_FOR_DUPLICATE_UNIT_ID.name();

	public CheckForDuplicateUnitIdRules(ApplicationContext ctx) {
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
		/*D.	16.0004- Manage Unit ID Codes in the Enterprise
		Use Case ID: 16.0004	                Goal: Manage Reference Data for Unit Identification Codes
		Actors: Global Reference Data Manager	Category: Enterprise
		
		Business Requirements

			Add a Unit ID

			3.	The system must prevent the user from adding duplicate, 
			    Standard Unit IDs to the e-ISuite Enterprise System.
		*/
		
		int cnt=super.organizationDao.getDuplicateCodeCount(super.organizationVo);
		if(cnt>0){
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.unitID",
							"error.0219",
							new String[]{"Unit ID", super.organizationVo.getUnitCode().toUpperCase()},
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
			return _FAIL;
			
		}
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		// TODO Auto-generated method stub

	}

}
