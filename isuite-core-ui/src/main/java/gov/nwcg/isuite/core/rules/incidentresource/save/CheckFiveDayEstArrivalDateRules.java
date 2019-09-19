package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckFiveDayEstArrivalDateRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.CHECK_FIVE_DAY_ESTARRIVAL_DATE.name();

	public CheckFiveDayEstArrivalDateRules(ApplicationContext ctx)
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

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
	
		/*
		 * CR 129
		 * Add/Edit Resources/Common Data - Estimated Date of Arrival
		 * If the date the user entered is more than five days after
		 * to the system date, display a message notifying the user that 
		 * the date is five days greater than the system date and asking 
		 * if the user would still like to save this date.
				Message 0299 - The date you entered is more than five days 
				prior to the system date. 
				Do you want to save this date? [Yes] [No]
		 */
		if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getDmTentativeArrivalDateVo())){
			Date chkDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmTentativeArrivalDateVo());
			Date sysDate=EISuiteCalendar.getCalendarDate(getUserSessionDbName());
			
			if(chkDate.after(sysDate)){
				long diffDays=DateUtil.diffDays(sysDate,chkDate);
				
				// if dates (entity/vo) are equal, skip message
				Date entityDate=null;
				long diffDays2=-1;
				if((super.irEntity != null)){
					if(DateUtil.hasValue(irEntity.getWorkPeriod().getDmTentativeArrivalDate())){
						entityDate=irEntity.getWorkPeriod().getDmTentativeArrivalDate();
						diffDays2=DateUtil.diffDays(chkDate, entityDate);
					}
				}
				if(diffDays2 != 0){
					if(diffDays > 5){
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
						coaVo.setPromptVo(new PromptVo("text.incidentResources"
											,"info.0299.a"
											,new String[]{"Estimated Arrival Date ("+DateUtil.toDateString(chkDate)+")"}
											,PromptVo._YES | PromptVo._NO));
						
						dialogueVo.setCourseOfActionVo(coaVo);
					
						return _FAIL;
						
					}
				}
			}
			
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// continue
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.NOACTION);
			//coaVo.setMessageVo(new MessageVo("text.incidentResource", "text.abortProcess" , new String[]{"save resource"}, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
	
			return _FAIL;
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
