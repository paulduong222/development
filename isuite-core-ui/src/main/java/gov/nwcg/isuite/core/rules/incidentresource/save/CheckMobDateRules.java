package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckMobDateRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.CHECK_MOB_DATE.name();

	public CheckMobDateRules(ApplicationContext ctx)
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
		 * CR 121
		 * 1.	The system must not check the Mobilization Date against 
		 * 		the Check-In Date.
		 * 
		 * 3.	When the user saves the data, the system must 
		 * 		validate the Mobilization Date against the Incident Start 
		 * 		Date. 
				If the 
					a.	When the user Mobilization Date is prior to the 
						Incident Start Date, the system must display a message 
						validating that the user really wants to save a 
						Mobilization Date that is prior to the Incident 
						Start Date. 

						Message 0278 - The Mobilization Date is prior to 
						the Incident Start Date. Do you want to continue? 
						
						[Yes] [No]selects Yes in response to message 0278, the system must save the Mobilization Date for the Resource, even though it is prior to the Incident Start Date.
					
					b.	When the user selects No in response to message 0278, 
					the system must not save the data and allow the user to 
					change the Mobilization Date
		 */

		// resolve the earliest incident start date
		IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
		Long igId=igDao.getIncidentGroupIdByIncidentId(vo.getIncidentVo().getId());
		Date incidentStartDate=null;
		if(LongUtility.hasValue(igId)){
			incidentStartDate=igDao.getEarliestIncidentStartDate(igId);
		}else{
			if(DateTransferVo.hasDateString(vo.getIncidentVo().getIncStartDateTransferVo())){
				incidentStartDate=DateTransferVo.getDate(vo.getIncidentVo().getIncStartDateTransferVo());
			}
		}

		if(DateUtil.hasValue(incidentStartDate)){
			incidentStartDate = DateUtil.addMilitaryTimeToDate(incidentStartDate, "2359");
		}
		
		if(null != vo.getWorkPeriodVo().getCiResourceMobilizationVo()){
			Date mobDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiResourceMobilizationVo().getStartDateVo());
			
			if(DateUtil.hasValue(mobDate)){
				mobDate=DateUtil.addMilitaryTimeToDate(mobDate, "2359");
				if(AbstractVo.hasValue(vo.getIncidentVo())){
					//Date incStartDate=vo.getIncidentVo().getIncidentStartDate();
					if(DateUtil.hasValue(incidentStartDate)){
						
						if(mobDate.before(incidentStartDate)){
							CourseOfActionVo coaVo = new CourseOfActionVo();
							coaVo.setCoaName(_RULE_NAME);
							coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
							coaVo.setPromptVo(new PromptVo("text.incidentResources"
												,"info.generic"
												,new String[]{"The Mobilization Date is prior to the Incident Start Date. \n\nDo you want to continue?"}
												,PromptVo._YES | PromptVo._NO));
								
							dialogueVo.setCourseOfActionVo(coaVo);
						
							return _FAIL;
						}
						
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
			coaVo.setIsDialogueEnding(true);
			//coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
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
