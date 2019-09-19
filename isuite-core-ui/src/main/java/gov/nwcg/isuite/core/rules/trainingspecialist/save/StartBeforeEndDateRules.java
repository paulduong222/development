package gov.nwcg.isuite.core.rules.trainingspecialist.save;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class StartBeforeEndDateRules extends AbstractSaveResourceTrainingRule
		implements IRule {
	
	public static final String _RULE_NAME=ResourceTrainingSaveRuleFactory.RuleEnum.START_END_DATES.name();
	
	public StartBeforeEndDateRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		if(null != super.resourceTrainingVo) {
			Date resVoStartDate = DateTransferVo.getDate(super.resourceTrainingVo.getStartDateTransferVo());
			Date resVoEndDate = DateTransferVo.getDate(super.resourceTrainingVo.getEndDateTransferVo());
			
			if(null != resVoEndDate) {
				if(resVoEndDate.before(resVoStartDate)) {
					String errMsg = "The assignment start date must be before the assignment end date.";
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.traineeAssignment"
													, "info.generic"
													, new String[]{errMsg}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}
		}
		
		
		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub
	}

}
