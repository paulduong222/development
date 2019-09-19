package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.rules.timepost.crews.finalactions.DateOverlapDuplicateFinalAction;
import gov.nwcg.isuite.core.rules.timepost.crews.finalactions.DateOverlapFinalAction;
import gov.nwcg.isuite.core.rules.timepost.crews.finalactions.DuplicateFinalActionCrew;
import gov.nwcg.isuite.core.rules.timepost.crews.finalactions.FinalAction;
import gov.nwcg.isuite.core.rules.timepost.crews.finalactions.ICrewFinalAction;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.springframework.context.ApplicationContext;

public class ExecuteFinalActionRules extends AbstractCrewRule implements IRule {
	public static final String _RULE_NAME="EXECUTE_FINAL_ACTION_RULES";

	public ExecuteFinalActionRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			Long primAssgnTimeId=0L;
			if(null != dialogueVo.getResultObjectAlternate4()){
				primAssgnTimeId=(Long)dialogueVo.getResultObjectAlternate4();
			}
			
			AssignmentTimePost firstEntity=null;
			AssignmentTimePostVo origVo=null;

			Collection<AssignmentTimePostVo> vosToSave = new ArrayList<AssignmentTimePostVo>();
			Collection<AssignmentTimePostVo> vosToDelete = new ArrayList<AssignmentTimePostVo>();
			
			dialogueVo.setResultObjectAlternate3(new ArrayList<AssignmentTimePostVo>());
			AssignmentTimePost origAtp=null;
			
			if(LongUtility.hasValue(vo.getId())){
				// get original vo
				origAtp=this.tpDao.getById(vo.getId(), AssignmentTimePostImpl.class);
				origVo =AssignmentTimePostVo.getInstance(origAtp, true);
				tpDao.flushAndEvict(origAtp);
			}
			
			ICrewFinalAction finalAction = null;

			int crewCount=0;
			if(CollectionUtility.hasValue(entities)){
				crewCount=entities.size();
			}

			if(hasDateOverlap(dialogueVo)){
				if(hasDateDuplicate(dialogueVo)){
					finalAction = new DateOverlapDuplicateFinalAction(context,crewCount);
					finalAction.setOriginalVo(origVo);
					finalAction.setTimePostDao(tpDao);
					((DateOverlapDuplicateFinalAction)finalAction).entities=entities;
					finalAction.saveCrewPosting(null, vo, dialogueVo);
					
					return _OK;
				}else{
					String dtStartDate=vo.getPostStartDateVo().getDateString();
					String dtStartTime=vo.getPostStartDateVo().getTimeString();
					String dtStopDate=vo.getPostStopDateVo().getDateString();
					String dtStopTime=vo.getPostStopDateVo().getTimeString();
					
					DateTransferVo origStartDtVo=new DateTransferVo();
					DateTransferVo origStopDtVo=new DateTransferVo();
					
					for(AssignmentTime atEntity : entities){
						if(isNonContractor(atEntity)){
							finalAction = new DateOverlapFinalAction(context, crewCount);
							
							if(null != finalAction){
								
								finalAction.setOriginalVo(origVo);
								finalAction.setTimePostDao(tpDao);
								int result =finalAction.saveCrewPosting(atEntity,vo,dialogueVo);
								
								if(result == _FAIL){
									// TODO: return fail
								}
								
								if(null == firstEntity){
									firstEntity=finalAction.getFirstEntity();
								}

								origStartDtVo.setDateString(dtStartDate);
								origStartDtVo.setTimeString(dtStartTime);
								origStopDtVo.setDateString(dtStopDate);
								origStopDtVo.setTimeString(dtStopTime);
								vo.setPostStartDateVo(origStartDtVo);
								vo.setPostStopDateVo(origStopDtVo);
							}
							
						}
					}
					
					if(null != origVo){
						AssignmentTimePost atp = tpDao.getById(origVo.getId(), AssignmentTimePostImpl.class);
						AssignmentTimePostVo atpvo = AssignmentTimePostVo.getInstance(atp, true);
						tpDao.flushAndEvict(atp);
						tpDao.delete(atp);
						vosToDelete.add(atpvo);
						dialogueVo.setResultObjectAlternate2(vosToDelete);
					}
					
					if(null != firstEntity)
						dialogueVo.setResultObject(AssignmentTimePostVo.getInstance(firstEntity, true));
					
					return _OK;
				}
			}
			
			if(hasDateDuplicate(dialogueVo)){
				HashMap<String,Long> processedAtIds=new HashMap<String,Long>();
				
				finalAction = new DuplicateFinalActionCrew(context,crewCount);
				CourseOfActionVo coa = this.getCoa(DuplicateTimePostRules._RULE_NAME,dialogueVo);
				if(null != coa){
					HashMap<String, Collection<Object>> crewMap = (HashMap<String, Collection<Object>>)coa.getStoredObject();
					Set<String> keyset = crewMap.keySet();
					Iterator<String> iter = keyset.iterator();
					
					while(iter.hasNext()){
						String k = (String)iter.next();
						Collection<Object> crewMemberids=(Collection<Object>)crewMap.get(k);
						int i=0;
						Collection<Long> cmIds = new ArrayList<Long>();
						for(Object o : crewMemberids){
							Long cmid = TypeConverter.convertToLong(o);
							cmIds.add(cmid);
						}
						// crewMemberids is atp id's
						for(Long id : cmIds){
							AssignmentTimePost atp = tpDao.getById(id, AssignmentTimePostImpl.class);
							tpDao.flushAndEvict(atp);
							
							if(i > 0){
								// delete the rest
								vosToDelete.add(AssignmentTimePostVo.getInstance(atp, true));
								tpDao.delete(atp);
							}else{
								finalAction.setOriginalVo(origVo);
								finalAction.setTimePostDao(tpDao);
								((DuplicateFinalActionCrew)finalAction).atpId=id;
								int result =finalAction.saveCrewPosting(atp.getAssignmentTime(),vo,dialogueVo);
								
								if(!processedAtIds.containsKey(String.valueOf(atp.getAssignmentTime().getId()))){
									processedAtIds.put(String.valueOf(atp.getAssignmentTime().getId()), atp.getAssignmentTime().getId());
								}
							}
							
							i++;
						}
						
					}

					int cnt=1;
					
					// create any inserts if not already created
					for(AssignmentTime atEntity : entities){
						if(isNonContractor(atEntity)){
							if(!processedAtIds.containsKey(String.valueOf(atEntity.getId()))){
								finalAction = new FinalAction(context,crewCount);
								if(LongUtility.hasValue(primAssgnTimeId)){
									if(primAssgnTimeId.compareTo(atEntity.getId())==0)
										finalAction.setMemberIndex(1);
									else
										finalAction.setMemberIndex(2);
								}
								
								//finalAction.setMemberIndex(cnt);
								
								if(null != finalAction){
									finalAction.setOriginalVo(origVo);
									finalAction.setTimePostDao(tpDao);
									int result =finalAction.saveCrewPosting(atEntity,vo,dialogueVo);
								}
								
							}
						}
						cnt++;
					}

					
					// update vo if editting primary record
					if(null != vo && LongUtility.hasValue(vo.getId())){
						//int result =finalAction.saveCrewPosting(origAtp.getAssignmentTime(),vo,dialogueVo);
					}
					
					finalAction=null;
				}
				
				dialogueVo.setResultObjectAlternate2(vosToDelete);
				return _OK;
			}
			
			int cnt=1;
			for(AssignmentTime atEntity : entities){
				
				if(isNonContractor(atEntity)){

					finalAction = new FinalAction(context, crewCount);

					if(null != finalAction){
						finalAction.setOriginalVo(origVo);
						finalAction.setTimePostDao(tpDao);
						if(primAssgnTimeId.compareTo(atEntity.getId())==0)
							finalAction.setMemberIndex(1);
						else
							finalAction.setMemberIndex(2);
						//finalAction.setMemberIndex(cnt);
						int result =finalAction.saveCrewPosting(atEntity,vo,dialogueVo);
						
						if(result == _FAIL){
							// TODO: return fail
						}
						
						if(null == firstEntity){
							firstEntity=finalAction.getFirstEntity();
						}
					}
					
				}
				cnt++;
			}
			
			if(null != firstEntity)
				dialogueVo.setResultObject(AssignmentTimePostVo.getInstance(firstEntity, true));
			
			dialogueVo.setResultObjectAlternate2(vosToDelete);
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	

	/**
	 * @param entity
	 * @return
	 */
	private Boolean isNonContractor(AssignmentTime entity){
		if(null != entity){
			if(entity.getEmploymentType() != EmploymentTypeEnum.CONTRACTOR)
				return true;
			else return false;
		}else
			return false;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

	/**
	 * Returns whether or not the time posting needs to be split.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private Boolean hasDateOverlap(DialogueVo dialogueVo) throws Exception {
		
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(DateOverlapRules._RULE_NAME)){
				if(null != coaVo.getPromptVo()){
					if(coaVo.getPromptVo().getPromptResult()==PromptVo._YES){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Returns whether or not the time posting is a duplicate.
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private Boolean hasDateDuplicate(DialogueVo dialogueVo) throws Exception {
		
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(DuplicateTimePostRules._RULE_NAME)){
				if(null != coaVo.getPromptVo() || null != coaVo.getCustomPromptVo()){
					if(coaVo.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private CourseOfActionVo getCoa(String name, DialogueVo dialogueVo) throws Exception {
		
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(name)){
				return coaVo;
			}
		}
		
		return null;
	}

	
	private Boolean hasDateDuplicateEntity(AssignmentTime atEntity) throws Exception {
		Date startTime=null;
		
		IncidentResourceDao irDao = (IncidentResourceDao)super.context.getBean("incidentResourceDao");
		
		WorkPeriod wp = atEntity.getAssignment().getWorkPeriods().iterator().next();

		irDao.flushAndEvict(wp.getIncidentResource());

		startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
		Date stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
					
		if(null != startTime && null != stopTime){
			int dupCount = tpDao.getDuplicateTimePostCount(vo.getId(),wp.getIncidentResourceId(), false, startTime, stopTime);
						
			if(dupCount > 0)
				return true;
		}
		
		return false;
		
	}
}
