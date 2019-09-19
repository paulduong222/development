package gov.nwcg.isuite.core.rules.incident.saveincident;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;

public class CheckEndBeforeStartDateRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.CHECK_END_BEFORE_START_DATE.name();

	public CheckEndBeforeStartDateRules(ApplicationContext ctx)
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		Date incStartDate=null;
		if(DateTransferVo.hasDateString(vo.getIncStartDateTransferVo())){
			incStartDate=DateTransferVo.getDate(vo.getIncStartDateTransferVo());
			incStartDate = DateUtil.addMilitaryTimeToDate(incStartDate, "0000");

			Date incEndDate=null;
			if(DateTransferVo.hasDateString(vo.getIncEndDateTransferVo())){
				incEndDate=DateTransferVo.getDate(vo.getIncEndDateTransferVo());
				incEndDate = DateUtil.addMilitaryTimeToDate(incEndDate, "0000");
				
				if(incStartDate.after(incEndDate)) {
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.incident"
													, "text.incidentStartEndDate"
													, null
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
					dialogueVo.setCourseOfActionVo(coaVo);
					
					return _FAIL;
					
				}
			}

		}

		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
