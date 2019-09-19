package gov.nwcg.isuite.core.rules.timepost.contractor.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TimeUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class BaseFinalAction {
	protected TimePostDao tpDao;
	protected AssignmentTimePostVo vo;
	protected AssignmentTimePostVo specialVo;
	protected IncidentResource incidentResourceEntity;
	protected AssignmentTimePost parentEntity;
	protected AssignmentTime assignmentTimeEntity;
	protected Collection<AssignmentTimePostVo> vosUpdated= new ArrayList<AssignmentTimePostVo>();
	
	/**
	 * @param vos
	 * @throws Exception
	 */
	protected void saveTimePosts(Collection<AssignmentTimePostVo> vos) throws Exception {

		for(AssignmentTimePostVo v : vos){
			AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, v, true);
			
			tpDao.save(entity);
			tpDao.flushAndEvict(entity);
		}
		
	}

	/**
	 * Returns whether or not there is additionalActionNeeded
	 * for a particular rule.
	 * 
	 * @param ruleName
	 * @param dialogueVo
	 * @return
	 */
	protected Boolean checkForAdditionalActionNeeded(String ruleName,DialogueVo dialogueVo){
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);
		if(null != coaVo && coaVo.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)
			return true;
		else
			return false;
	}
	
	protected CourseOfActionVo getCoa(String ruleName,DialogueVo dialogueVo){
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);
		if(null != coaVo && coaVo.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)
			return coaVo;
		else
			return null;
	}

	/**
	 * Returns whether or not the exclusive special time posting needs to be split.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	protected Boolean hasExclusiveSpecialDateOverlap(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName("TODO");

		if(null != coaVo){
			if(null != coaVo.getPromptVo()){
				if(coaVo.getPromptVo().getPromptResult()==PromptVo._YES){
					return true;
				}
			}
		}
		
		return false;
	}

	protected AssignmentTimePostVo splitTime(int idx,AssignmentTimePostVo vo, String strTime) throws Exception {
		AssignmentTimePostVo vo1=vo.clone();
		
		if(idx==0){
			Date dateStart = DateUtil.toMilitaryDate(vo.getPostStartDate(), DateUtil.formatMilitaryTime(vo.getPostStartTime()));
			Date dateStop = DateUtil.toMilitaryDate(vo.getPostStartDate(), DateUtil.formatMilitaryTime(strTime));
			
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(dateStart);

			Calendar cal2=Calendar.getInstance();
			cal2.setTime(dateStop);

		    long diffSeconds = (cal2.getTimeInMillis() - cal1.getTimeInMillis())/1000;
		    long diffMinutes = diffSeconds / 60;
		    long diffHours = diffMinutes / 60;
		    long minutesLeft = diffMinutes%60;

		    vo1.setPostStartDate(dateStart);
		    vo1.setPostStopDate(dateStop);
		    vo1.setPostStopTime("2359");
		    vo1.setQuantity(TimeUtility.toTimeQuantity(diffHours, minutesLeft));
			
		}else{
			vo1.setPostStartDate(DateUtil.addDaysToDate(vo.getPostStartDate(), 1));
			Date dateStart = DateUtil.toMilitaryDate(vo1.getPostStartDate(), "00:00");
			
			Date dateStop = DateUtil.toMilitaryDate(vo1.getPostStartDate(), DateUtil.formatMilitaryTime(strTime));
			
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(dateStart);

			Calendar cal2=Calendar.getInstance();
			cal2.setTime(dateStop);
			
			vo1.setPostStartDate(dateStart);
			vo1.setPostStartTime("0000");
			vo1.setPostStopDate(dateStop);
			vo1.setPostStopTime(strTime);

		    long diffSeconds = (cal2.getTimeInMillis() - cal1.getTimeInMillis())/1000;
		    long diffMinutes = diffSeconds / 60;
		    long diffHours = diffMinutes / 60;
		    long minutesLeft = diffMinutes%60;

		    vo1.setQuantity(TimeUtility.toTimeQuantity(diffHours, minutesLeft));
			
		}
		
		return vo1;
	}

	protected BigDecimal calculateRateAmount(String unitOfMeasure,BigDecimal quantity, BigDecimal rateAmount) throws Exception {
		BigDecimal amount = new BigDecimal("0");
		
		amount = quantity.multiply(rateAmount);
		
		return amount;
	}
}
