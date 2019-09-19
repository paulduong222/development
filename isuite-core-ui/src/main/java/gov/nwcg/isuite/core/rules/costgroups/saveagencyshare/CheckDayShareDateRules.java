package gov.nwcg.isuite.core.rules.costgroups.saveagencyshare;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.impl.CostGroupImpl;
import gov.nwcg.isuite.core.persistence.CostGroupDao;
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

public class CheckDayShareDateRules extends AbstractSaveAgencyShareRule implements IRule {
	public static final String _RULE_NAME=SaveAgencyShareRuleFactory.RuleEnum.CHECK_DAY_SHARE_DATE.name();
	
	public CheckDayShareDateRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
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
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			/*
			 * This rule will be implemented when user roles are revisited in the future.
			 * Skip rule.
			 */
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(_RULE_NAME, true));
				
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
		 * e-ISuite Cost Rates and Groups Use Case.
		 * pg: 10
		 *  The system must require the user to enter a Date for an Agency Percentage 
		 *  that is the same as or prior to the current system date but is not prior 
		 *  to the Incident Start Date.
		 */
		//Date systemDate=Calendar.getInstance().getTime();	
		Date systemDate=EISuiteCalendar.getCalendarDate(getUserSessionDbName());
		systemDate=DateUtil.addMilitaryTimeToDate(systemDate, "2359");
		
		Date agencyPctDate=super.vo.getAgencyShareDate();
		agencyPctDate=DateUtil.addMilitaryTimeToDate(agencyPctDate, "2359");
	
		CostGroupDao cgDao = (CostGroupDao)context.getBean("costGroupDao");
		CostGroup cg =  cgDao.getById(super.vo.getCostGroupVo().getId(), CostGroupImpl.class);
		Date incStartDate=null;
		if(null != cg){
			incStartDate=cg.getIncident().getIncidentStartDate();
			cgDao.flushAndEvict(cg);
		}
		
		if(agencyPctDate.after(systemDate)){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.costGroups"
					, "error.0310"
					, null
					, MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
					
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		if(agencyPctDate.before(incStartDate)){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.costGroups"
					, "error.0311"
					, null
					, MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
					
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
