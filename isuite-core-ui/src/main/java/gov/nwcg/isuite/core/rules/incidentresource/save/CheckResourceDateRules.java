package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckResourceDateRules extends
		AbstractIncidentResourceSaveRule implements IRule {

	public static final String _RULE_NAME="RESOURCE_DATE_VALIDATION";
	
	public CheckResourceDateRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
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

		try {
			
			ArrayList<ErrorObject> errors = new ArrayList<ErrorObject>();
			ErrorObject error = null;
			
			// check checkin time has correct format
			if(StringUtility.hasValue(vo.getWorkPeriodVo().getCiCheckInDateVo().getTimeString())){
				error = DateTimeValidator.validateTimeField("Check-In Time", vo.getWorkPeriodVo().getCiCheckInDateVo().getTimeString());
				if(error!=null)errors.add(error);
			}
			
			// check if checkin time avail and no checkin date
			if(StringUtility.hasValue(vo.getWorkPeriodVo().getCiCheckInDateVo().getTimeString())){
				if(!DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
					error = new ErrorObject("info.generic","Cannot have Check-In Time without Check-In Date");
					errors.add(error);
				}
			}
			
			// check firstworkdate not before checkin date
			/*
			 * Commenting out checking firstWorkDate before CheckInDate
			 * per Defect #3273 CR ST138
			  if(DateUtil.hasValue(vo.getWorkPeriodVo().getCiCheckInDate())){
				if(DateUtil.hasValue(vo.getWorkPeriodVo().getCiFirstWorkDate())){
					error=DateTimeValidator.validateDate1NotBeforeDate2(
							 vo.getWorkPeriodVo().getCiFirstWorkDate()
							,vo.getWorkPeriodVo().getCiCheckInDate()
							, "First Work Date", "Check-In Date");
					if(error!=null)errors.add(error);
				}
			}*/
			
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
				//incidentStartDate=vo.getIncidentVo().getIncidentStartDate();
			}

			if(DateUtil.hasValue(incidentStartDate)){
				incidentStartDate = DateUtil.addMilitaryTimeToDate(incidentStartDate, "2359");
			}
			
			// check checkin date not before incident start date
			if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
				Date ciDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo());
				ciDate=DateUtil.addMilitaryTimeToDate(ciDate, "2359");
				error=DateTimeValidator.validateDate1NotBeforeDate2(
						ciDate
						,incidentStartDate
						, "Check-In Date", "Incident Start Date");
				if(error!=null){
					String msg = "The Check-In Date cannot be prior to the Incident Start Date. Please enter another date.";
					error = new ErrorObject("info.generic",msg);
					errors.add(error);
				}
			}
			
			// check actualrelease date not before checkin date
			if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
				Date ciDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo());
				ciDate=DateUtil.addMilitaryTimeToDate(ciDate, "2359");
				Date dmReleaseDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmReleaseDateVo());
				
				if(DateUtil.hasValue(dmReleaseDate)){
					dmReleaseDate=DateUtil.addMilitaryTimeToDate(dmReleaseDate, "2359");
					error=DateTimeValidator.validateDate1NotBeforeDate2(
							dmReleaseDate
							,ciDate
							, "Actual Release Date", "Check-In Date");
					if(error!=null)errors.add(error);
				}
			}
			
			// if status = D, actualreleasedate is required
			if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo()
					&& (vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode().equals("D")
							|| vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode().equals("R"))){

				Date dmReleaseDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmReleaseDateVo());
				if(!DateUtil.hasValue(dmReleaseDate)){
					error = new ErrorObject("info.generic","Actual Release Date is a required field.");
					errors.add(error);
				}
				
			}
			// check have actualrelease date but no checkin date
			/*
			if(!DateUtil.hasValue(vo.getWorkPeriodVo().getCiCheckInDate())){
				if(DateUtil.hasValue(vo.getWorkPeriodVo().getDmReleaseDate())){
					error = new ErrorObject("info.generic","Cannot have an Acutal Release Date without a Check-In Date");
					errors.add(error);
				}
			}
			*/
			
			// check tentdemob date not before checkin date
			if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
				Date ciDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo());
				ciDate=DateUtil.addMilitaryTimeToDate(ciDate, "2359");
				if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getDmTentativeReleaseDateVo())){
					Date releaseDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmTentativeReleaseDateVo());
					releaseDate=DateUtil.addMilitaryTimeToDate(releaseDate, "2359");
					error=DateTimeValidator.validateDate1NotBeforeDate2(
							releaseDate
							,ciDate
							, "Tentative Release Date", "Check-In Date");
					if(error!=null)errors.add(error);
				}
			}
			
			// check tent release time has correct format
			if(StringUtility.hasValue(vo.getWorkPeriodVo().getDmTentativeReleaseDateVo().getTimeString())){
				error = DateTimeValidator.validateTimeField("Tentative Release Time", vo.getWorkPeriodVo().getDmTentativeReleaseDateVo().getTimeString());
				if(error!=null)errors.add(error);
			}
			
			// check if tentrelease time avail and no tentreleasedate
			if(StringUtility.hasValue(vo.getWorkPeriodVo().getDmTentativeReleaseDateVo().getTimeString())){
				if(!DateTransferVo.hasDateString(vo.getWorkPeriodVo().getDmTentativeReleaseDateVo())){
					error = new ErrorObject("info.generic","Cannot have Tentative Release Time without Tentative Release Date");
					errors.add(error);
				}
			}

			// check est arrival date not before checkin date
			if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiTentativeArrivalDateVo())){
				if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
					Date ciDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo());
					ciDate=DateUtil.addMilitaryTimeToDate(ciDate, "2359");
					Date ciTentArrivalDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getCiTentativeArrivalDateVo());
					ciTentArrivalDate=DateUtil.addMilitaryTimeToDate(ciTentArrivalDate, "2359");
					error=DateTimeValidator.validateDate1NotBeforeDate2(
							ciTentArrivalDate
							,ciDate
							, "Estimated Arrival Date", "Check-In Date");
					if(error!=null)errors.add(error);
				}
			}
			
			if(errors.size() > 0) {
				CourseOfActionVo coa = buildValidationErrorCoaVo(errors);
				dialogueVo.setCourseOfActionVo(coa);
				return _FAIL;
			} else {
				return _OK;
			}
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
