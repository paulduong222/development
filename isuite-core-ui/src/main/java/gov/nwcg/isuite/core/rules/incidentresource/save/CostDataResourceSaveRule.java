package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.EISuiteCalendar;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CostDataResourceSaveRule extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.COST_DATA.name();
	
	public CostDataResourceSaveRule(ApplicationContext ctx) {
		
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try {
			
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
				
		} catch(Exception e) {
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
		/*
		 * When the Assign Date is prior to the Incident Start Date, 
		 * the system must not save the assign date and must display a message. [Message 0135]
		 */
		
		// check assignDate not before incident start date
		Date incStartDate=null;
		if(AbstractVo.hasValue(vo.getIncidentVo())){
			// if incident is part of incident group, then use earliest incident start date
			IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
			Incident inc = incDao.getById(vo.getIncidentVo().getId());
			if(CollectionUtility.hasValue(inc.getIncidentGroups())){
				Long incidentGroupId=(inc.getIncidentGroups().iterator().next()).getId();
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				incStartDate=igDao.getEarliestIncidentStartDate(incidentGroupId);
			}else
				if(DateTransferVo.hasDateString(vo.getIncidentVo().getIncStartDateTransferVo())){
					incStartDate=DateTransferVo.getDate(vo.getIncidentVo().getIncStartDateTransferVo());
				}
				//incStartDate=vo.getIncidentVo().getIncidentStartDate();
		}
		
		// check assignDate not after system date
		Date sysDate=EISuiteCalendar.getCalendarDate(getUserSessionDbName());
		
		if(null != vo.getCostDataVo()){
			Date assignDate=null;
			if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
				assignDate=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
			}
			if(DateUtil.hasValue(incStartDate) && DateUtil.hasValue(assignDate)){

				assignDate=DateUtil.addMilitaryTimeToDate(assignDate, "2359");
				
				if(assignDate.before(incStartDate)){

					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_MSG_FINISHED);
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					coaVo.setMessageVo(new MessageVo("text.incidentResources"
													, "info.0135"
													, new String[]{}
													, MessageTypeEnum.CRITICAL));
					coaVo.setIsDialogueEnding(Boolean.TRUE);
									
					dialogueVo.setCourseOfActionVo(coaVo);
					return _FAIL;
				}
			}
			
			// check assignDate not after system date
			/* defect 3709 removed checking assign date after system date
			if(DateUtil.hasValue(sysDate) && DateUtil.hasValue(assignDate)){

				DateUtil.addMilitaryTimeToDate(assignDate, "2359");
	
				if(assignDate.after(DateUtil.addMilitaryTimeToDate(sysDate, "2359"))){

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
			*/
		}
		
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
