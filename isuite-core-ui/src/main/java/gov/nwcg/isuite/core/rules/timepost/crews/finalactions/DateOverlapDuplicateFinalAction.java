package gov.nwcg.isuite.core.rules.timepost.crews.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.rules.timepost.crews.DateOverlapRules;
import gov.nwcg.isuite.core.rules.timepost.crews.DuplicateTimePostRules;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TimeUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

/*
 * This class handles the situation where the time post being saved
 * is both a date overlap and a duplicate.
 */
public class DateOverlapDuplicateFinalAction extends AbstractCrewFinalAction implements ICrewFinalAction{
	public Collection<AssignmentTime> entities=new ArrayList<AssignmentTime>();

	public DateOverlapDuplicateFinalAction(ApplicationContext ctx, int ccount){
		super(ctx);
		super.crewCount=ccount;
	} 

	public int saveCrewPosting(AssignmentTime atimeEntity,AssignmentTimePostVo vo,DialogueVo dialogueVo) throws ServiceException {
		
		try{
			CourseOfActionVo dateOverlapCoa = super.getCoa(DateOverlapRules._RULE_NAME, dialogueVo);
			CourseOfActionVo duplicateTimeCoa = super.getCoa(DuplicateTimePostRules._RULE_NAME, dialogueVo);

			Collection<AssignmentTimePostVo> vosToSave = new ArrayList<AssignmentTimePostVo>();
			Collection<AssignmentTimePostVo> vosToDelete = new ArrayList<AssignmentTimePostVo>();

			dialogueVo.setResultObjectAlternate2(vosToDelete);
			
			// are we editing a posting that is now an overlap?
			Long atpId=0L;
			Object atpid = (Object)dateOverlapCoa.getStoredObject();
			if(null != atpid)
				atpId = TypeConverter.convertToLong(atpid);

			
			HashMap<String, Collection<Object>> crewMap = (HashMap<String, Collection<Object>>)duplicateTimeCoa.getStoredObject();
			
			for(AssignmentTime atEntity : entities){
				String atId = String.valueOf(atEntity.getId());
				
				// atp's for crew member with atid = atEntity.getId
				Collection<Object> crewMemberids=null;
				Collection<Long> crewMemberLongIds=null;
				if(null != crewMap){
					crewMemberids=(Collection<Object>)crewMap.get(atId);
					crewMemberLongIds=LongUtility.convertToLongs(crewMemberids);
				}

				// create the postings
				if(!CollectionUtility.hasValue(crewMemberids)){
					// if crewMemberids is empty, this crew member has nothing to overwrite
					// proceed with new time postings
					ICrewFinalAction finalAction = new DateOverlapFinalAction(context,super.crewCount);
					finalAction.setOriginalVo(originalVo);
					finalAction.setTimePostDao(tpDao);
					int result =finalAction.saveCrewPosting(atEntity,vo,dialogueVo);
					
				}else{
					// this crew member has a time post that needs updated/deleted
					buildCrewMemberSplitPostings(atEntity,vo,dialogueVo);
					
					if(LongUtility.hasValue(atpId)){
						if(!crewMemberLongIds.contains(atpId))
							crewMemberLongIds.add(atpId);
						atpId=0L;
					}
					
					if(CollectionUtility.hasValue( crewMemberLongIds)){
						for(Long fid :  crewMemberLongIds){
							Long lngId=TypeConverter.convertToLong(fid);
							AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
							AssignmentTimePostVo deletevo = AssignmentTimePostVo.getInstance(atp, true);
							tpDao.flushAndEvict(atp);
							tpDao.delete(atp);
							((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate2()).add(deletevo);
						}
					}
				
				}
			
				
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return AbstractRule._OK;
	}
	
	private void buildCrewMemberSplitPostings(AssignmentTime atEntity,AssignmentTimePostVo vo, DialogueVo dialogueVo) throws ServiceException, Exception {
		String originalStopTime=vo.getPostStopTime();
		
		/*
		 * Build first date
		 */
		if(1==1){
			Collection<AssignmentTimePostVo> vosToSaveSplit = new ArrayList<AssignmentTimePostVo>();
			AssignmentTimePostVo atpvo1=vo.clone();
			Date dte=DateUtil.toDate(vo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			atpvo1.setId(null);
			atpvo1.setPrimaryPosting(true);
			
			Date dateStart = DateUtil.toMilitaryDate(dte, DateUtil.formatMilitaryTime(atpvo1.getPostStartTime()));
			Date dateStop = DateUtil.toMilitaryDate(dte, DateUtil.formatMilitaryTime("2359"));
			
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

		    vosToSaveSplit.add(atpvo1);
			saveTimePosts(vosToSaveSplit,dialogueVo);
		}
	    
	    /*
	     * Build second date				    
	     */
		if(1==1){
			Collection<AssignmentTimePostVo> vosToSaveSplit = new ArrayList<AssignmentTimePostVo>();
			Date dte=DateUtil.toDate(vo.getPostStartDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			AssignmentTimePostVo atpvo2=vo.clone();
			atpvo2.setId(null);
			atpvo2.setPrimaryPosting(true);
			dte = DateUtil.addDaysToDate(dte, 1);
			
			Date dateStart = DateUtil.toMilitaryDate(dte, "00:00");
			Date dateStop = DateUtil.toMilitaryDate(dte, DateUtil.formatMilitaryTime(originalStopTime));
			
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(dateStart);

			Calendar cal2=Calendar.getInstance();
			cal2.setTime(dateStop);
			
			atpvo2.setPostStartDate(dateStart);
			atpvo2.setPostStartTime("0000");
			atpvo2.setPostStopDate(dateStop);
			atpvo2.setPostStopTime(originalStopTime);

		    long diffSeconds = (cal2.getTimeInMillis() - cal1.getTimeInMillis())/1000;
		    long diffMinutes = diffSeconds / 60;
		    long diffHours = diffMinutes / 60;
		    long minutesLeft = diffMinutes%60;

		    atpvo2.setQuantity(TimeUtility.toTimeQuantity(diffHours, minutesLeft));

		    super.verifySpecialCodes(atEntity, atpvo2);
		    super.verifyRateInfo(atEntity, atpvo2);
			
			// if atEntity is for the original vo, do not override kindcode
			if(vo.getAssignmentTimeId().compareTo(atEntity.getId()) != 0)
				super.verifyItemCode(atEntity, atpvo2);
			
			atpvo2.setAssignmentTimeId(atEntity.getId());
			
	    	vosToSaveSplit.add(atpvo2);
			saveTimePosts(vosToSaveSplit,dialogueVo);
		}
		
		//saveTimePosts(vosToSaveSplit,dialogueVo);

	}

	
}
