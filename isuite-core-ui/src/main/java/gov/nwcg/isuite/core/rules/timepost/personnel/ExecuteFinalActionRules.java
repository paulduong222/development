package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TimeUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

@SuppressWarnings("unchecked")
public class ExecuteFinalActionRules extends AbstractPersonnelRule implements IRule {
	public static final String _RULE_NAME="EXECUTE_FINAL_ACTION_RULES";

	private TimePostDao tpDao = null;
	private boolean splitPostingWasSaved=false;
	
	public ExecuteFinalActionRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			tpDao = (TimePostDao)context.getBean("timePostDao");

			dialogueVo.setResultObjectAlternate3(new ArrayList<AssignmentTimePostVo>());
		
			if(isNonContractor(assignmentTimeEntity)){

				Collection<AssignmentTimePostVo> vosToSave = new ArrayList<AssignmentTimePostVo>();
				Collection<AssignmentTimePostVo> vosToDelete = new ArrayList<AssignmentTimePostVo>();

				AssignmentTimePostVo atpvo=null;
				vo.setPrimaryPosting(true);

				if(StringUtility.hasValue(vo.getPostStartTime())){
					if(vo.getPostStartTime().equals("2400"))
						vo.setPostStartTime("2359");
				}
				if(StringUtility.hasValue(vo.getPostStopTime())){
					if(vo.getPostStopTime().equals("2400"))
						vo.setPostStopTime("2359");
				}
				
				if(hasDateOverlap(dialogueVo)){
					vosToSave.addAll(buildSplitPosting(vo,vosToDelete,dialogueVo));

				}else{
					Boolean isOverwriteDuplicate=this.isOverwriteDuplicate(dialogueVo);
					Boolean isOverwriteDuplicateSpecial=this.isOverwriteDuplicateSpecial(dialogueVo);
					
					// are we overwriting existing
					if(BooleanUtility.isTrue(isOverwriteDuplicate)){
						CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(DuplicateTimePostRules._RULE_NAME);
						if(null != coa){
							Collection<Object> ids = (Collection<Object>)coa.getStoredObject();
							int i=0;
							for(Object id : ids){
								Long lngId=TypeConverter.convertToLong(id);
								if(i > 0){
									// remove the rest
									AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
									tpDao.flushAndEvict(atp);
									vosToDelete.add(AssignmentTimePostVo.getInstance(atp, true));
									tpDao.delete(atp);
								}else{
									AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
									AssignmentTimePostVo atpvoTmp = AssignmentTimePostVo.getInstance(atp, true);
									
									atpvoTmp.setPostStartDate(vo.getPostStartDate());
									atpvoTmp.setPostStartTime(vo.getPostStartTime());
									
									
									//System.out.println(vo.getPostStopTime());
									if(!DateUtil.hasValue(vo.getPostStopDate())){
										// if the special is not cop,dayoff,guar, then set same as startdate
										if(null == vo.getSpecialPayVo()){
											atpvoTmp.setPostStopDate(vo.getPostStartDate());
										}else if(null != vo.getSpecialPayVo()){
											String cd=vo.getSpecialPayVo().getCode();
											if(cd.equals("COP") || cd.equals("GUAR") || cd.equals("DAYOFF") || cd.equals("DAY OFF")){
												atpvoTmp.setPostStopDate(null);
											}else{
												if(StringUtility.hasValue(vo.getPostStopTime()))
													atpvoTmp.setPostStopDate(vo.getPostStartDate());
												else
													atpvoTmp.setPostStopDate(null);
											}
										}
									}else
										atpvoTmp.setPostStopDate(vo.getPostStopDate());

									atpvoTmp.setPostStopTime(vo.getPostStopTime());
									atpvoTmp.setReturnTravelStartOnly(vo.getReturnTravelStartOnly());
									atpvoTmp.setSpecialPayVo(vo.getSpecialPayVo());
									atpvoTmp.setQuantity(vo.getQuantity());
									atpvoTmp.setAssignmentTimeId(atp.getAssignmentTimeId());
									atpvoTmp.setTraining(atp.getTraining());
									atpvoTmp.setIncidentAccountCodeVo(vo.getIncidentAccountCodeVo());
									
									vosToSave.add(atpvoTmp);
									tpDao.flushAndEvict(atp);
								}
								i++;
								
							}
							
						}
					}else if(BooleanUtility.isTrue(isOverwriteDuplicateSpecial)){
						CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(DuplicateTimePostSpecialRules._RULE_NAME);
						if(null != coa){
							Collection<Object> ids = (Collection<Object>)coa.getStoredObject();
							for(Object id : ids){
								Long lngId=TypeConverter.convertToLong(id);
								AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
								AssignmentTimePostVo atpvoTmp = AssignmentTimePostVo.getInstance(atp, true);
									
								atpvoTmp.setPostStopDate(vo.getPostStopDate());
								atpvoTmp.setPostStartTime(vo.getPostStartTime());
								atpvoTmp.setReturnTravelStartOnly(vo.getReturnTravelStartOnly());
								atpvoTmp.setSpecialPayVo(vo.getSpecialPayVo());
								atpvoTmp.setPostStopTime(vo.getPostStopTime());
								atpvoTmp.setQuantity(vo.getQuantity());
								atpvoTmp.setAssignmentTimeId(atp.getAssignmentTimeId());
								atpvoTmp.setTraining(atp.getTraining());
								atpvoTmp.setIncidentAccountCodeVo(vo.getIncidentAccountCodeVo());
									
								vosToSave.add(atpvoTmp);
								tpDao.flushAndEvict(atp);
								
							}
							
						}
					}else{
						// proceed normally
						atpvo = vo.clone();
						if(StringUtility.hasValue(atpvo.getPostStopTime()))
							atpvo.setPostStopDate(atpvo.getPostStartDate());
						else
							atpvo.setPostStopDate(null);
						vosToSave.add(atpvo);
					}
				}

				if(vosToSave.size()==0)
					vosToSave.add(vo);
				
				AssignmentTimePost firstEntity=saveTimePosts(vosToSave,dialogueVo);
				if(null != firstEntity)
					dialogueVo.setResultObject(AssignmentTimePostVo.getInstance(firstEntity, true));
				
				dialogueVo.setResultObjectAlternate2(vosToDelete);
			}
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	private Collection<AssignmentTimePostVo> buildSplitPosting(AssignmentTimePostVo vo, Collection<AssignmentTimePostVo> vosToDelete,DialogueVo dialogueVo) throws Exception{
		Collection<AssignmentTimePostVo> vosToSave = new ArrayList<AssignmentTimePostVo>();

		String originalStopTime=vo.getPostStopTime();
		Collection<Long> overwriteFirstIds = null;
		Collection<Long> overwriteSecondIds = null;

	    // check if we are overwriting?
		if(BooleanUtility.isTrue(this.isOverwriteDuplicate(dialogueVo))){
			CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(DuplicateTimePostRules._RULE_NAME);
			if(null != coa && null != coa.getStoredObject1()){
				Collection<Object> list = (Collection<Object>)coa.getStoredObject1();
				overwriteFirstIds=new ArrayList<Long>();
				for(Object o : list){
					overwriteFirstIds.add(TypeConverter.convertToLong(o));
				}
			}
			if(null != coa && null != coa.getStoredObject2()){
				Collection<Object> list = (Collection<Object>)coa.getStoredObject2();
				overwriteSecondIds=new ArrayList<Long>();
				for(Object o : list){
					overwriteSecondIds.add(TypeConverter.convertToLong(o));
				}
			}
		}else if(BooleanUtility.isTrue(this.isOverwriteDuplicateSpecial(dialogueVo))){
			CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(DuplicateTimePostSpecialRules._RULE_NAME);
			if(null != coa && null != coa.getStoredObject1()){
				Collection<Object> list = (Collection<Object>)coa.getStoredObject1();
				overwriteFirstIds=new ArrayList<Long>();
				for(Object o : list){
					overwriteFirstIds.add(TypeConverter.convertToLong(o));
				}
			}
			if(null != coa && null != coa.getStoredObject2()){
				Collection<Object> list = (Collection<Object>)coa.getStoredObject2();
				overwriteSecondIds=new ArrayList<Long>();
				for(Object o : list){
					overwriteSecondIds.add(TypeConverter.convertToLong(o));
				}
			}
		}
		
		/*
		 * Build first date
		 */
		AssignmentTimePostVo atpvo1=vo.clone();
		boolean firstDateSaved=false;
		
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
	    
	    if(CollectionUtility.hasValue(overwriteFirstIds)){
	    	int i=0;
		    for(Object id : overwriteFirstIds){
				Long lngId=TypeConverter.convertToLong(id);
				if(i>0){
					// remove the rest
					AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
					tpDao.flushAndEvict(atp);
					vosToDelete.add(AssignmentTimePostVo.getInstance(atp, true));
					tpDao.delete(atp);
				}else{
					AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
					AssignmentTimePostVo atpvoTmp = AssignmentTimePostVo.getInstance(atp, true);
					atpvoTmp.setPostStopDate(atpvo1.getPostStopDate());
					atpvoTmp.setPostStartDate(atpvo1.getPostStartDate());
					atpvoTmp.setPostStartTime(atpvo1.getPostStartTime());
					atpvoTmp.setReturnTravelStartOnly(atpvo1.getReturnTravelStartOnly());
					atpvoTmp.setSpecialPayVo(atpvo1.getSpecialPayVo());
					atpvoTmp.setPostStopTime(atpvo1.getPostStopTime());
					atpvoTmp.setQuantity(atpvo1.getQuantity());
					atpvoTmp.setAssignmentTimeId(atpvo1.getAssignmentTimeId());
					atpvoTmp.setTraining(atpvo1.getTraining());
					atpvoTmp.setIncidentAccountCodeVo(atpvo1.getIncidentAccountCodeVo());
	
				    vosToSave.add(atpvoTmp);
					tpDao.flushAndEvict(atp);
				}
				i++;
		    }
	    }else{
			AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, atpvo1, true);
			tpDao.save(entity);
			tpDao.flushAndEvict(entity);
			firstDateSaved=true;
			AssignmentTimePost atp = tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
			AssignmentTimePostVo atpvoTmp = AssignmentTimePostVo.getInstance(atp, true);
			tpDao.flushAndEvict(entity);
			
		    vosToSave.add(atpvoTmp);
		    //vosToSave.add(atpvo1);
	    }
	    	
	    /*
	     * Build second date				    
	     */
	    boolean secondDateSaved=false;
		AssignmentTimePostVo atpvo2=vo.clone();
		if(LongUtility.hasValue(atpvo2.getId()))
			atpvo2.setId(null);
		
		atpvo2.setPostStartDate(DateUtil.addDaysToDate(atpvo2.getPostStartDate(), 1));
		
		dateStart = DateUtil.toMilitaryDate(atpvo2.getPostStartDate(), "00:00");
		dateStop = DateUtil.toMilitaryDate(atpvo2.getPostStartDate(), DateUtil.formatMilitaryTime(originalStopTime));
		
		cal1=Calendar.getInstance();
		cal1.setTime(dateStart);

		cal2=Calendar.getInstance();
		cal2.setTime(dateStop);
		
		atpvo2.setPostStartDate(dateStart);
		atpvo2.setPostStartTime("0000");
		atpvo2.setPostStopDate(dateStop);
		atpvo2.setPostStopTime(originalStopTime);

	    diffSeconds = (cal2.getTimeInMillis() - cal1.getTimeInMillis())/1000;
	    diffMinutes = diffSeconds / 60;
	    diffHours = diffMinutes / 60;
	    minutesLeft = diffMinutes%60;

	    atpvo2.setQuantity(TimeUtility.toTimeQuantity(diffHours, minutesLeft));
		
	    if(CollectionUtility.hasValue(overwriteSecondIds)){
	    	int i=0;
		    for(Object id : overwriteSecondIds){
				Long lngId=TypeConverter.convertToLong(id);
				if(i > 0){
					// remove the rest
					AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
					tpDao.flushAndEvict(atp);
					vosToDelete.add(AssignmentTimePostVo.getInstance(atp, true));
					tpDao.delete(atp);
				}else {
					AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
					AssignmentTimePostVo atpvoTmp = AssignmentTimePostVo.getInstance(atp, true);
					atpvoTmp.setPostStopDate(atpvo2.getPostStopDate());
					atpvoTmp.setPostStartDate(atpvo2.getPostStartDate());
					atpvoTmp.setPostStartTime(atpvo2.getPostStartTime());
					atpvoTmp.setReturnTravelStartOnly(atpvo2.getReturnTravelStartOnly());
					atpvoTmp.setSpecialPayVo(atpvo2.getSpecialPayVo());
					atpvoTmp.setPostStopTime(atpvo2.getPostStopTime());
					atpvoTmp.setQuantity(atpvo2.getQuantity());
					atpvoTmp.setAssignmentTimeId(atpvo2.getAssignmentTimeId());
					atpvoTmp.setTraining(atpvo2.getTraining());
					atpvoTmp.setIncidentAccountCodeVo(atpvo2.getIncidentAccountCodeVo());

				    vosToSave.add(atpvoTmp);
					tpDao.flushAndEvict(atp);
				}
				i++;
		    }
	    }else{
			AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, atpvo2, true);
			tpDao.save(entity);
			AssignmentTimePost atp = tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
			AssignmentTimePostVo atpvoTmp = AssignmentTimePostVo.getInstance(atp, true);
			tpDao.flushAndEvict(entity);
			
			secondDateSaved=true;
		    vosToSave.add(atpvoTmp);
		    //vosToSave.add(atpvo2);
	    }

	    this.splitPostingWasSaved=firstDateSaved;
	    
		return vosToSave;
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

	private AssignmentTimePost saveTimePosts(Collection<AssignmentTimePostVo> vos,DialogueVo dialogueVo) throws Exception {
		AssignmentTimePost firstEntity=null;
		int x=0;

		if(this.splitPostingWasSaved==true){
			for(AssignmentTimePostVo v : vos){
				if(LongUtility.hasValue(v.getId())){
					AssignmentTimePost entity=tpDao.getById(v.getId(), AssignmentTimePostImpl.class);
					if(x==0)
						firstEntity=entity;
					((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
				}else{
					AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, v, true);
					
					tpDao.save(entity);
					
					if(x==0)
						firstEntity=entity;
					tpDao.flushAndEvict(entity);
					entity=tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
					((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
				}
				x++;
			}
		}else{
			for(AssignmentTimePostVo v : vos){
				AssignmentTimePost entity = AssignmentTimePostVo.toEntity(null, v, true);
					
				tpDao.save(entity);
					
				if(x==0)
					firstEntity=entity;
				tpDao.flushAndEvict(entity);
				entity=tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
			}
			x++;
		}
		return firstEntity;
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

	private Boolean isOverwriteDuplicate(DialogueVo dialogueVo) throws Exception {
		
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
	
	private Boolean isOverwriteDuplicateSpecial(DialogueVo dialogueVo) throws Exception {
		
		for(CourseOfActionVo coaVo : dialogueVo.getProcessedCourseOfActionVos()){
			if(coaVo.getCoaName().equals(DuplicateTimePostSpecialRules._RULE_NAME)){
				if(null != coaVo.getPromptVo() || null != coaVo.getCustomPromptVo()){
					if(coaVo.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private AssignmentTimePostVo splitTime(int idx,AssignmentTimePostVo vo, String strTime) throws Exception {
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
			//String elapsedTime=String.valueOf((diffHours > 0 ? diffHours : ""))+"."+String.valueOf(minutesLeft);
			//vo1.setQuantity(TypeConverter.convertToBigDecimal(elapsedTime));
			
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
	
}
