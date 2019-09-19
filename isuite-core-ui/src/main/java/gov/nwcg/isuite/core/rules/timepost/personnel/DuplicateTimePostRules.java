package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.DuplicatePostingVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class DuplicateTimePostRules extends AbstractPersonnelRule implements IRule {
	public static final String _RULE_NAME="DUPLICATE_TIME_POST";

	public DuplicateTimePostRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{

			/*
			 * if rule check completed,
			 * return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 *	If the current posting for a Resource overlaps a previous posting, 
		 *  the system must display an action message confirming that the current 
		 *  posting should replace the previous posting. Yes and No buttons are available. 
		 *  [Message 0139] (Applies to Use Cases 6.0011 and 6.0012.)
		 */
		if(null != vo){
			
			/*
			 * Only fed,ad, and other resources require start and stop times.
			 * 
			 * Determine if the new start and stop times overlap 
			 * an existing time posting time period.
			 */
			if(null != incidentResourceEntity && incidentResourceEntity.getId() > 0){
							
				Date startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
				Date stopTime = null;
				if(BooleanUtility.isFalse(vo.getReturnTravelStartOnly())){
					stopTime=DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
				}
				
				if(BooleanUtility.isTrue(super.hasOverlap(dialogueVo))){
					stopTime = DateUtil.addMilitaryTimeToDate(DateUtil.addDays(vo.getPostStartDate(),1), vo.getPostStopTime());
				}
				
				if(null != startTime && null != stopTime){
					int dupCount = tpDao.getDuplicateTimePostCount(vo.getId(),incidentResourceEntity.getId(), false, startTime, stopTime);
								
					if(dupCount > 0){
						CourseOfActionVo coaVo = new CourseOfActionVo();
						String dupDates="";
						
						Collection<Long> ids = tpDao.getDuplicateTimePostIds(vo.getId(),incidentResourceEntity.getId(), false, startTime,stopTime);
						dupDates=DateUtil.toDateString(startTime, DateUtil.MM_SLASH_DD_SLASH_YYYY);
						
						if(BooleanUtility.isTrue(super.hasOverlap(dialogueVo))){
							// determine if first,second or both  is the overlap
							Date firstStartTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
							Date firstStopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), "2359");
							dupCount = tpDao.getDuplicateTimePostCount(vo.getId(),incidentResourceEntity.getId(), false, firstStartTime, firstStopTime);
							if(dupCount > 0){
								Collection<Long> firstIds = tpDao.getDuplicateTimePostIds(vo.getId(),incidentResourceEntity.getId(), false, firstStartTime, firstStopTime);
								coaVo.setStoredObject1(firstIds);
								dupDates=DateUtil.toDateString(firstStartTime, DateUtil.MM_SLASH_DD_SLASH_YYYY);
							}else
								dupDates="";
							
							Date secondStartTime = DateUtil.addMilitaryTimeToDate(DateUtil.addDays(vo.getPostStartDate(),1), "0000");
							Date secondStopTime = DateUtil.addMilitaryTimeToDate(DateUtil.addDays(vo.getPostStartDate(),1), vo.getPostStopTime());
							dupCount = tpDao.getDuplicateTimePostCount(vo.getId(),incidentResourceEntity.getId(), false, secondStartTime, secondStopTime);
							if(dupCount > 0){
								Collection<Long> secondIds = tpDao.getDuplicateTimePostIds(vo.getId(),incidentResourceEntity.getId(), false, secondStartTime, secondStopTime);
								coaVo.setStoredObject2(secondIds);
								dupDates=(StringUtility.hasValue(dupDates) ? dupDates + ", " : "") + DateUtil.toDateString(secondStartTime, DateUtil.MM_SLASH_DD_SLASH_YYYY);
							}
							
						}
						
						String dupTimes="";
						Collection<DuplicatePostingVo> dupVos = new ArrayList<DuplicatePostingVo>();
						for(Long longId : ids){
							AssignmentTimePost atp = tpDao.getById(longId, AssignmentTimePostImpl.class);
							DuplicatePostingVo dpVo = new DuplicatePostingVo();
							dpVo.setPostDate(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
							dpVo.setResourceName("");
							dpVo.setStartTime(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.HH_MM));
							if(DateUtil.hasValue(atp.getPostStopDate())){
								dpVo.setStopTime(DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM));
								if(StringUtility.hasValue(dpVo.getStopTime())){
									if(dpVo.getStopTime().equals("23:59"))
										dpVo.setStopTime("24:00");
								}
							}else
								dpVo.setStopTime("");

							if(null != atp.getSpecialPay()){
								String code=atp.getSpecialPay().getCode();
								if(code.equals("GUAR") || code.equals("COP") || code.equals("DAYOFF") || code.equals("DAY OFF")){
									dpVo.setStartTime("");
									dpVo.setStopTime("");
								}else if(code.equals("TVL")){
									if(dpVo.getStopTime().equals("00:00"))
										dpVo.setStopTime("");
								}
							}
							dupVos.add(dpVo);
							if(StringUtility.hasValue(dupTimes)){
								//dupTimes=dupTimes+"\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								//	+ " to " + DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
							}else{
								//dupTimes="Time postings:\n"+DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)
								//	+ " to " + DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
							}
							tpDao.flushAndEvict(atp);
						}

						// if editing a posting, and this id not in ids, need to add it as the first id so the remaining 
						// timeposts that are supposed to be getting updated will actually get deleted since this time posting
						// is replacing them
						if(LongUtility.hasValue(super.vo.getId())){
							Collection<Long> newids = new ArrayList<Long>();
							newids.add(vo.getId());
							for(Long id : ids){
								Boolean bAdd=false;
								for(Long nid : newids){
									if(nid.compareTo(id)!=0)
										bAdd=true;
								}
								if(bAdd==true)
									newids.add(id);
							}
							ids=newids;
						}
						
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
						//coaVo.setPromptVo(new PromptVo("text.time","action.0139",new String[]{dupDates},PromptVo._YES | PromptVo._NO));
						//coaVo.setPromptVo(new PromptVo("text.time","action.0139.a",new String[]{dupDates, dupTimes},PromptVo._YES | PromptVo._NO));
						coaVo.setCustomPromptVo(new CustomPromptVo("PERSONNELDUPLIST","text.time" ,"action.0139crew",dupVos));
						coaVo.setStoredObject(ids);
						
						dialogueVo.setCourseOfActionVo(coaVo);
						return _FAIL;
					}
				}else{
					if(null==stopTime && BooleanUtility.isTrue(vo.getReturnTravelStartOnly())){
						// skip
					}else{
						dialogueVo.setCourseOfActionVo(
								super.buildErrorCoaVo("text.time"
													,"validationerror"
													,"error.null"
													,new String[]{"Start and Stop Times"}));	
						return _FAIL;
					}
				}
						
			}
		}
		
		return _OK;

	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {

		// check prompt result
		if(getCustomPromptResult(dialogueVo) == PromptVo._YES) {
			// add to processed
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.time", "text.abortProcess" , new String[]{"post time"}, MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}

		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa){
			if(getPromptResult(dialogueVo) == PromptVo._YES) {
				
				/* 
				 * Delete the original time posts that this new time post
				 * replaces.
				 */
				TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
				Date startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
				Date stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
				
				/*
				Collection<Object> ids = (Collection<Object>)coa.getStoredObject();
				for(Object o : ids){
					Long id = TypeConverter.convertToLong(o);
					tpDao.deleteById(id);
				}
				*/
				//tpDao.deleteDuplicateTimePosts(incidentResourceEntity.getId(), startTime, stopTime);
				
			}
		}
	}

	
}
