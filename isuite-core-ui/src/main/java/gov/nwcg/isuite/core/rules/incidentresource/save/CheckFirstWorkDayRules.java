package gov.nwcg.isuite.core.rules.incidentresource.save;

import java.util.Date;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
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

public class CheckFirstWorkDayRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.CHECK_FIRST_WORK_DAY.name();

	public CheckFirstWorkDayRules(ApplicationContext ctx)
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
		 * CR 121 - superceded by cr 138
		 * 2.	The system must not check the First Work Day against 
		 * 		either the Check-In Date or the Mobilization Date.
		 * 
		 * 4.	When the user saves the data, the system must validate the First Work Day against the Incident Start Date. 
				If the date is prior to the incident start date, the system must not save 
				the data and display a message to the user.

				Message 0279 - The First Work Day cannot be prior to 
				the Incident Start Date. Please enter another date. 

		 */
		/*
		 * CR 138
		 *  If the First Work Day is prior to the Incident Start Date, the system must display a message to the user indicating that the date is prior to the Incident Start Date and validating that the user wants to save the resource with that date.
			Message 0339 - The First Work Day is prior to the Incident Start Date. Do you want to continue? [Yes] [No]
			When the user selects Yes, the system must save the record with the First Work Day prior to the Incident Start Date.
			When the user selects No, the system must not save the record.
		 */
		Date incStartDate=null;
		if(AbstractVo.hasValue(vo.getIncidentVo())){
			if(DateTransferVo.hasDateString(vo.getIncidentVo().getIncStartDateTransferVo())){
				incStartDate=DateTransferVo.getDate(vo.getIncidentVo().getIncStartDateTransferVo());
			} else {
				// get start date from database
				IncidentDao incDao = (IncidentDao)super.context.getBean("incidentDao");
				Incident inc = incDao.getById(vo.getIncidentVo().getId());
				incDao.flushAndEvict(inc);
				if ( inc != null) {
					incStartDate = inc.getIncidentStartDate();
				}
			}
			if(null != incStartDate)
				incStartDate=DateUtil.addMilitaryTimeToDate(incStartDate, "2359");
		}
		
		Date firstWorkDate=null;
		if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiFirstWorkDateVo())){
			firstWorkDate=DateTransferVo.getDate(super.vo.getWorkPeriodVo().getCiFirstWorkDateVo());
		}

		if(DateUtil.hasValue(incStartDate) && DateUtil.hasValue(firstWorkDate)){

			firstWorkDate=DateUtil.addMilitaryTimeToDate(firstWorkDate, "2359");
			
			if(LongUtility.hasValue(super.vo.getId())){
				if(super.irEntity != null){
					if(null != irEntity.getWorkPeriod().getCIFirstWorkDate()){
						Date entityFirstWorkDate=DateUtil.addMilitaryTimeToDate(irEntity.getWorkPeriod().getCIFirstWorkDate(), "2359");
						if(!DateUtil.isSameDate(entityFirstWorkDate,firstWorkDate)){
							if(firstWorkDate.before(incStartDate)){
								CourseOfActionVo coaVo = new CourseOfActionVo();
								coaVo.setCoaName(_RULE_NAME);
								coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
								coaVo.setPromptVo(new PromptVo("text.incidentResources"
													,"action.0339"
													,new String[]{}
													,PromptVo._YES | PromptVo._NO));
								
								dialogueVo.setCourseOfActionVo(coaVo);
							
								return _FAIL;
							}
						}
					}else{
						if(firstWorkDate.before(incStartDate)){
							CourseOfActionVo coaVo = new CourseOfActionVo();
							coaVo.setCoaName(_RULE_NAME);
							coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
							coaVo.setPromptVo(new PromptVo("text.incidentResources"
												,"action.0339"
												,new String[]{}
												,PromptVo._YES | PromptVo._NO));
							
							dialogueVo.setCourseOfActionVo(coaVo);
						
							return _FAIL;
						}
					}
				}
			}else{
				if(firstWorkDate.before(incStartDate)){
	
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
					coaVo.setPromptVo(new PromptVo("text.incidentResources"
										,"action.0339"
										,new String[]{}
										,PromptVo._YES | PromptVo._NO));
					
					dialogueVo.setCourseOfActionVo(coaVo);
				
					return _FAIL;
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
