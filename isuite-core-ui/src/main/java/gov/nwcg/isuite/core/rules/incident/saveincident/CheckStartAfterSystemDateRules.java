package gov.nwcg.isuite.core.rules.incident.saveincident;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;

public class CheckStartAfterSystemDateRules extends AbstractIncidentSaveRule implements IRule{
	public static final String _RULE_NAME=SaveIncidentRuleFactory.RuleEnum.CHECK_START_AFTER_SYSTEM_DATE.name();

	public CheckStartAfterSystemDateRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				
				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{
				
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
				
			}
				
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
			
			Date sysDate=EISuiteCalendar.getCalendarDate(getUserSessionDbName());			
			sysDate = DateUtil.addMilitaryTimeToDate(sysDate, "0000");
			
			if ( incStartDate.after(sysDate)){
				/*
				 * Prompt user inc start date after system date
				 */
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.incident"
												,"text.incidentStartSystemDate"
												,null
												,PromptVo._YES ));
				coaVo.getPromptVo().setYesLabel("OK");
				dialogueVo.setCourseOfActionVo(coaVo);

				return _FAIL;
			
			}
		}

		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
	
		dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
