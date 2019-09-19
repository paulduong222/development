package gov.nwcg.isuite.core.rules.incidentresourceother.save;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckIROAssignDateRules extends AbstractSaveIRORule implements IRule {
	public static final String _RULE_NAME = SaveIRORuleFactory.RuleEnum.CHECK_ASSIGN_DATE.name();

	public CheckIROAssignDateRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
				.add(super.buildNoActionCoaVo(ruleName,true));
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * Assign Date, if it is entered by the user, cannot be before Incident Start Date
		 */
		Date assignDate = null;
		if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
			assignDate=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
		}
		
		// Rule check should happen only if an assign date has been entered by the user
		if(DateUtil.hasValue(assignDate)){
			Date incStartDate = null;
	
			IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
			
			// Retrieve incident date
			if(null != vo.getId() && vo.getId()!= 0L){ // IRO exists as it was saved earlier
				IncidentResourceOther iroEntity = iroDao.getById(vo.getId(), IncidentResourceOtherImpl.class);
				incStartDate = iroEntity.getIncident().getIncidentStartDate();
				iroDao.flushAndEvict(iroEntity);
			}else{ // IRO does not yet exist in the db, and is being saved for the first time
				if(DateTransferVo.hasDateString(vo.getIncidentVo().getIncStartDateTransferVo())){
					incStartDate=DateTransferVo.getDate(vo.getIncidentVo().getIncStartDateTransferVo());
				}
				//incStartDate = vo.getIncidentVo().getIncidentStartDate();
				
				//Safety check. This should typically not be reached.
				if(null == incStartDate) {
					throw new ServiceException("INCIDENT START DATE NOT FOUND.");
				}
			}
			
			// check assign date is not before incident start date
			if(DateUtil.hasValue(assignDate) && DateUtil.hasValue(incStartDate)){
				assignDate=DateUtil.addMilitaryTimeToDate(assignDate, "2359");
				incStartDate=DateUtil.addMilitaryTimeToDate(incStartDate, "2359");
				
				if(assignDate.before(incStartDate)){
	
					String errorMsg="The Assign Date cannot be prior to the Incident Start Date. Please enter another date.";
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.otherCost"
													, "info.0135"
													, new String[]{}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
									
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
				
				// check assign date not after system date
				Date sysDate=EISuiteCalendar.getCalendarDate(getUserSessionDbName());
				sysDate=DateUtil.addMilitaryTimeToDate(sysDate, "2359");
				if(assignDate.after(sysDate)){
					
					String msg="The assign date is after the system date.";
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.otherCost"
													, "info.generic"
													, new String[]{msg}
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
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
