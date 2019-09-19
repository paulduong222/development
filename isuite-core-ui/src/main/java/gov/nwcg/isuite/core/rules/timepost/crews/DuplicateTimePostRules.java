package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ResourceDao;
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
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;

public class DuplicateTimePostRules extends AbstractCrewRule implements IRule {
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
		 * Determine if the new start and stop times overlap 
		 * an existing time posting time period for any of the crew members.
		 */
		Collection<String> failedNames = new ArrayList<String>();
		Date startTime=null;
		Date stopTime=null;
		CourseOfActionVo coaVo = new CourseOfActionVo();
		
		IncidentResourceDao irDao = (IncidentResourceDao)super.context.getBean("incidentResourceDao");
		ResourceDao resourceDao = (ResourceDao)super.context.getBean("resourceDao");

		Collection<DuplicatePostingVo> dupVos = new ArrayList<DuplicatePostingVo>();
		
		HashMap<String, Collection<Long>> crewMap = new HashMap<String,Collection<Long>>();
		HashMap<String, Collection<Long>> crewMapFirstIds = new HashMap<String,Collection<Long>>();
		HashMap<String, Collection<Long>> crewMapSecondIds = new HashMap<String,Collection<Long>>();
		
		for(AssignmentTime atEntity : super.entities){
			if(null != atEntity){
				WorkPeriod wp = atEntity.getAssignment().getWorkPeriods().iterator().next();

				Collection<Long> crewMemberAtpIds= new ArrayList<Long>();

				irDao.flushAndEvict(wp.getIncidentResource());
				IncidentResource irEntity = irDao.getById(wp.getIncidentResourceId(), IncidentResourceImpl.class);
				resourceDao.flushAndEvict(irEntity.getResource());
				Resource resource = resourceDao.getById(irEntity.getResourceId(),ResourceImpl.class);

				startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
				if(BooleanUtility.isTrue(super.hasOverlap(dialogueVo))){
					stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), "2359");
				}else{
					if(BooleanUtility.isFalse(vo.getReturnTravelStartOnly()))
						stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
				}
				
				if(null != startTime && null != stopTime){
					int dupCount=tpDao.getDuplicateTimePostCount(vo.getId(),wp.getIncidentResourceId(), false, startTime, stopTime);
					int dupCount2 = 0;
					Collection<Long> ids = new ArrayList<Long>();
					
					if(dupCount > 0)
						 ids = tpDao.getDuplicateTimePostIds(vo.getId(),irEntity.getId(), false, startTime,stopTime);
					
					if(BooleanUtility.isTrue(super.hasOverlap(dialogueVo))){
						// check next day duplicates
						Date nextDate=DateUtil.addDays(startTime, 1);
						startTime=DateUtil.addMilitaryTimeToDate(nextDate, "0000");
						stopTime=DateUtil.addMilitaryTimeToDate(nextDate, vo.getPostStopTime());
						dupCount2=tpDao.getDuplicateTimePostCount(vo.getId(),wp.getIncidentResourceId(), false, startTime, stopTime);
						
						if(dupCount2 > 0){
							dupCount=dupCount+dupCount2;
							Collection<Long> ids2 = new ArrayList<Long>();
							ids2 = tpDao.getDuplicateTimePostIds(vo.getId(),irEntity.getId(), false, startTime,stopTime);
							ids.addAll(ids2);
						}
					}
								
					if(dupCount > 0){
						
						crewMemberAtpIds.addAll(ids);
						
						String dupTimes="";
						for(Long longId : ids){
							AssignmentTimePost atp = tpDao.getById(longId, AssignmentTimePostImpl.class);
							DuplicatePostingVo dpVo = new DuplicatePostingVo();
							dpVo.setPostDate(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
							dpVo.setResourceName(resource.getFirstName() + " " + resource.getLastName());
							dpVo.setStartTime(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.HH_MM));
							if(DateUtil.hasValue(atp.getPostStopDate())){
								dpVo.setStopTime(DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM));
								if(StringUtility.hasValue(dpVo.getStopTime())){
									if(dpVo.getStopTime().equals("23:59"))
										dpVo.setStopTime("24:00");
								}
							}else{
								dpVo.setStopTime("");
							}
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
							tpDao.flushAndEvict(atp);
							if(StringUtility.hasValue(dupTimes)){
								dupTimes=dupTimes+"\n"+resource.getFirstName()+ " " + resource.getLastName() + "   " +DateUtil.toDateString(atp.getPostStartDate(), DateUtil.HH_MM)
									+ " to " ; //+ DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
							}else{
								dupTimes=resource.getFirstName()+ " " + resource.getLastName() + "    " + DateUtil.toDateString(atp.getPostStartDate(), DateUtil.HH_MM)
									+ " to "; // + DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM);
							}
						}
						
						failedNames.add("\n" + dupTimes);
					}
				
					crewMap.put(String.valueOf(atEntity.getId()), crewMemberAtpIds);
				
					/*
					if(BooleanUtility.isTrue(super.hasOverlap(dialogueVo))){
						// determine if first,second or both  is the overlap
						Date firstStartTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
						Date firstStopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), "2359");
						dupCount = tpDao.getDuplicateTimePostCount(vo.getId(),irEntity.getId(), false, firstStartTime, firstStopTime);
						if(dupCount > 0){
							Collection<Long> firstIds = tpDao.getDuplicateTimePostIds(vo.getId(),irEntity.getId(), false, firstStartTime, firstStopTime);
							crewMapFirstIds.put(String.valueOf(atEntity.getId()), firstIds);
						}						
						Date secondStartTime = DateUtil.addMilitaryTimeToDate(DateUtil.addDays(vo.getPostStartDate(),1), "0000");
						Date secondStopTime = DateUtil.addMilitaryTimeToDate(DateUtil.addDays(vo.getPostStartDate(),1), vo.getPostStopTime());
						dupCount = tpDao.getDuplicateTimePostCount(vo.getId(),irEntity.getId(), false, secondStartTime, secondStopTime);
						if(dupCount > 0){
							Collection<Long> secondIds = tpDao.getDuplicateTimePostIds(vo.getId(),irEntity.getId(), false, secondStartTime, secondStopTime);
							crewMapSecondIds.put(String.valueOf(atEntity.getId()), secondIds);
						}
						
					}*/
					
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
			
			// include current atp being editted
			if(LongUtility.hasValue(vo.getId())){
				Collection<Long> crewMemberAtpIds=new ArrayList<Long>();
				if(crewMap.containsKey(String.valueOf(vo.getAssignmentTimeId()))){
					crewMemberAtpIds=crewMap.get(String.valueOf(vo.getAssignmentTimeId()));
				}
				if(!crewMemberAtpIds.contains(vo.getId())){
					crewMemberAtpIds.add(vo.getId());
					crewMap.put(String.valueOf(vo.getAssignmentTimeId()), crewMemberAtpIds);
				}
			}
		}
		
		if(CollectionUtility.hasValue(failedNames)){
			String names=CollectionUtility.toCommaDelimitedString(failedNames);
			
			// add one being editted in list if applicable
			if(LongUtility.hasValue(super.vo.getId())){
				AssignmentTimePost atp = tpDao.getById(vo.getId(), AssignmentTimePostImpl.class);
				WorkPeriod wp = atp.getAssignmentTime().getAssignment().getWorkPeriods().iterator().next();
				irDao.flushAndEvict(wp.getIncidentResource());
				IncidentResource irEntity = irDao.getById(wp.getIncidentResourceId(), IncidentResourceImpl.class);
				resourceDao.flushAndEvict(irEntity.getResource());
				Resource resource = resourceDao.getById(irEntity.getResourceId(),ResourceImpl.class);

				tpDao.flushAndEvict(atp);
				
				DuplicatePostingVo dpVo = new DuplicatePostingVo();
				dpVo.setPostDate(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
				dpVo.setResourceName(resource.getFirstName() + " " + resource.getLastName());
				dpVo.setStartTime(DateUtil.toDateString(atp.getPostStartDate(), DateUtil.HH_MM));
				if(DateUtil.hasValue(atp.getPostStopDate()))
					dpVo.setStopTime(DateUtil.toDateString(atp.getPostStopDate(), DateUtil.HH_MM));
				else
					dpVo.setStopTime("");
				dupVos.add(dpVo);
			}
			
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
			coaVo.setCustomPromptVo(new CustomPromptVo("CREWDUPLIST","text.time" ,"action.0139crew",dupVos));
			//coaVo.setPromptVo(new PromptVo("text.time","action.0139crew",new String[]{DateUtil.toDateString(startTime), names},PromptVo._YES | PromptVo._NO));
			coaVo.setStoredObject(crewMap);
			coaVo.setStoredObject1(crewMapFirstIds);
			coaVo.setStoredObject1(crewMapSecondIds);
			dialogueVo.setCourseOfActionVo(coaVo);
								
			return _FAIL;
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

				//tpDao.deleteDuplicateTimePosts(incidentResourceEntity.getId(), startTime, stopTime);
				
			}
		}
	}
	
}
