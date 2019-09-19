package gov.nwcg.isuite.core.rules.timepost.crews.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.TimeUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

/*
 * This class handles the situation where the time post being saved
 * is a date overlap.
 */
public class DateOverlapFinalAction extends AbstractCrewFinalAction implements ICrewFinalAction{

	public DateOverlapFinalAction(ApplicationContext ctx, int ccount){
		super(ctx);
		super.crewCount=ccount;
	} 
	
	public int saveCrewPosting(AssignmentTime atEntity,AssignmentTimePostVo vo,DialogueVo dialogueVo) throws Exception {
		Collection<AssignmentTimePostVo> vosToSave2 = new ArrayList<AssignmentTimePostVo>();
		
		String originalStopTime=vo.getPostStopTime();
		
		/*
		 * Build first date
		 */
		AssignmentTimePostVo atpvo1=vo.clone();
		atpvo1.setId(null);
		atpvo1.setPrimaryPosting(true);
		
		Date dateStart = DateUtil.toMilitaryDate(atpvo1.getPostStartDate(), DateUtil.formatMilitaryTime(atpvo1.getPostStartTime()));
		Date dateStop = DateUtil.toMilitaryDate(atpvo1.getPostStartDate(), DateUtil.formatMilitaryTime("2359"));
		
		Calendar cal1=Calendar.getInstance();
		cal1.setTime(dateStart);

		Calendar cal2=Calendar.getInstance();
		cal2.setTime(dateStop);

	    long diffSeconds = (cal2.getTimeInMillis() - cal1.getTimeInMillis())/1000;
	    long diffMinutes = diffSeconds / 60;
	    long diffHours = diffMinutes / 60;
	    long minutesLeft = diffMinutes%60;

	    atpvo1.setPostStartDate(dateStart);
	    atpvo1.setPostStopDate(dateStop);
	    atpvo1.setPostStopTime("2359");
	    atpvo1.setQuantity(TimeUtility.toTimeQuantity(diffHours, minutesLeft));

	    super.verifySpecialCodes(atEntity, atpvo1);
		super.verifyRateInfo(atEntity, atpvo1);
	    
		// if atEntity is for the original vo, do not override kindcode
		if(vo.getAssignmentTimeId().compareTo(atEntity.getId()) != 0)
			super.verifyItemCode(atEntity, atpvo1);
		
		atpvo1.setAssignmentTimeId(atEntity.getId());

		vosToSave2.add(atpvo1);
		saveTimePosts(vosToSave2,dialogueVo);
		vosToSave2 = new ArrayList<AssignmentTimePostVo>();
		
	    /*
	     * Build second date				    
	     */
		AssignmentTimePostVo atpvo2=vo.clone();
		atpvo2.setId(null);
		atpvo2.setPrimaryPosting(true);
		Date dt2=new Date();
		dt2=DateUtil.addDaysToDate(atpvo2.getPostStartDate(), 1);
		atpvo2.setPostStartDate(dt2);

		Date dateStart2 = DateUtil.toMilitaryDate(atpvo2.getPostStartDate(), "00:00");
		Date dateStop2 = DateUtil.toMilitaryDate(atpvo2.getPostStartDate(), DateUtil.formatMilitaryTime(originalStopTime));
		
		cal1=Calendar.getInstance();
		cal1.setTime(dateStart2);

		cal2=Calendar.getInstance();
		cal2.setTime(dateStop2);
		
		atpvo2.setPostStartDate(dateStart2);
		atpvo2.setPostStartTime("0000");
		atpvo2.setPostStopDate(dateStop2);
		atpvo2.setPostStopTime(originalStopTime);

	    diffSeconds = (cal2.getTimeInMillis() - cal1.getTimeInMillis())/1000;
	    diffMinutes = diffSeconds / 60;
	    diffHours = diffMinutes / 60;
	    minutesLeft = diffMinutes%60;

	    atpvo2.setQuantity(TimeUtility.toTimeQuantity(diffHours, minutesLeft));

	    super.verifySpecialCodes(atEntity, atpvo2);
	    super.verifyRateInfo(atEntity, atpvo2);
		
		// if atEntity is for the original vo, do not override kindcode
		if(vo.getAssignmentTimeId().compareTo(atEntity.getId()) != 0)
			super.verifyItemCode(atEntity, atpvo2);
		
		atpvo2.setAssignmentTimeId(atEntity.getId());
		
		vosToSave2.add(atpvo2);
		
		saveTimePosts(vosToSave2,dialogueVo);
		
		return AbstractRule._OK;
	}
	
}
