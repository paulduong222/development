package gov.nwcg.isuite.core.rules.dailycost.save;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
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

public class CheckActivityDateRules extends AbstractSaveDailyCostRule implements IRule {
	public static final String _RULE_NAME=SaveDailyCostRuleFactory.RuleEnum.CHECK_ACTIVITY_DATE.name();

	public CheckActivityDateRules(ApplicationContext ctx) {
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
		 * Page 69 in e-ISuite Manage Costs-Daily Costs Use Case.pdf
		 * 
		 * Activity Date  - Cannot be before Incident Start Date
		 * 				  - Cannot be greater than System Date
		 * 
		 */
		Date activityDate = DateTransferVo.getDate(irdcVo.getActivityDateVo());
		//Date activityDate = super.irdcVo.getActivityDate();
		//Date systemDate = Calendar.getInstance().getTime();
		Date systemDate=EISuiteCalendar.getCalendarDate(getUserSessionDbName());
		Date incStartDate = null;

		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		IncidentResourceOtherDao iroDao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");

		if(null != irdcVo.getIncidentResourceVo()){
			IncidentResource irEntity = irDao.getById(super.irdcVo.getIncidentResourceVo().getId(), IncidentResourceImpl.class);
			incStartDate = irEntity.getIncident().getIncidentStartDate();
			irDao.flushAndEvict(irEntity);
		}else if(null != irdcVo.getIncidentResourceOtherVo()){
			IncidentResourceOther iroEntity = iroDao.getById(super.irdcVo.getIncidentResourceOtherVo().getId(), IncidentResourceOtherImpl.class);
			incStartDate = iroEntity.getIncident().getIncidentStartDate();
			iroDao.flushAndEvict(iroEntity);
		}
		
		// check activity date not before incident start date
		/*  turn off rule since resource check-in can be before inc start date (inc groups)
		if(DateUtil.hasValue(activityDate) && DateUtil.hasValue(incStartDate)){
			DateUtil.addMilitaryTimeToDate(activityDate, "2359");
			
			if(activityDate.before(incStartDate)){

				String errorMsg="The Activity Date cannot be prior to the Incident Start Date. Please enter another date.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost"
												, "info.generic"
												, new String[]{errorMsg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
		}
		*/
		
		// check activity date not after system date
		if(DateUtil.hasValue(activityDate) && DateUtil.hasValue(systemDate)){
			DateUtil.addMilitaryTimeToDate(systemDate, "2359");
			
			if(activityDate.after(systemDate)){

				String errorMsg="The Activity Date cannot be after the System Date. Please enter another date.";
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.dailyCost"
												, "info.generic"
												, new String[]{errorMsg}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
								
				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
		}
		
		return _OK;
		
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
