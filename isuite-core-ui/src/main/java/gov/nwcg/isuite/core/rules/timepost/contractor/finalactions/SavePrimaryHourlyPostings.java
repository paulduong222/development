package gov.nwcg.isuite.core.rules.timepost.contractor.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.timepost.contractor.ContractorPrimaryHourlyDuplicateRules;
import gov.nwcg.isuite.core.rules.timepost.contractor.ContractorPrimaryHourlyOverlapRules;
import gov.nwcg.isuite.core.rules.timepost.contractor.ContractorSpecialHourlyDuplicateRules;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@SuppressWarnings("unchecked")
public class SavePrimaryHourlyPostings extends BaseFinalAction {
	private Boolean needSplitPosting=false;
	private Boolean needOverwrite=false;
	private String originalStartTime;
	private String originalStopTime;
	private TimePostQueryFilterImpl filter = new TimePostQueryFilterImpl();
	
	public SavePrimaryHourlyPostings(TimePostDao dao
										,AssignmentTimePostVo atpvo
										,IncidentResource irEntity){
		super.tpDao=dao;
		super.vo=atpvo;
		super.incidentResourceEntity=irEntity;
	}
	
	/**
	 * @param dialogueVo
	 * @throws Exception
	 */
	public void save(AssignmentTimePost entity,DialogueVo dialogueVo) throws Exception {

		if(LongUtility.hasValue(vo.getId()))
			entity = tpDao.getById(vo.getId(), AssignmentTimePostImpl.class);

		/*
		 * Set flag if we need to split posting into 2 time postings.
		 */
		needSplitPosting = 
			checkForAdditionalActionNeeded(ContractorPrimaryHourlyOverlapRules._RULE_NAME,dialogueVo);
		
		/*
		 * Set flag if we need to overwrite existing records.
		 */
		needOverwrite = 
			checkForAdditionalActionNeeded(ContractorPrimaryHourlyDuplicateRules._RULE_NAME,dialogueVo);
		
		/*
		 * Get the original start and stop times
		 */
		originalStartTime=vo.getPostStartTime();
		originalStopTime=vo.getPostStopTime();

		/*
		 * Init default timepostQueryfilter
		 */
		filter.setUnitOfMeasure("HOURLY");
		if(LongUtility.hasValue(vo.getId()))
			filter.setExcludeAssignmentTimePostId(vo.getId());
		filter.setIncidentResourceId(incidentResourceEntity.getId());
		filter.setOnlyPrimary(true);
		filter.setExcludeGuarantee(true);
		filter.setCompareTime(true);
		filter.setPostType("PRIMARY");

		// init resultalternate3 to new collection
		if(null == dialogueVo.getResultObjectAlternate3())
			dialogueVo.setResultObjectAlternate3(new ArrayList<AssignmentTimePostVo>()) ;
		
		if(null != entity){
			/*
			 * We are updating an existing time posting.
			 */
			if(needSplitPosting){
				//splitExistingTimePosting(entity,dialogueVo);
				splitTimePosting(dialogueVo);
				
			}else{
				
				if(needOverwrite){

					/*
					 * If entity is not null and the needOverwrite flag is true
					 * then this means the user is updating an existing time post and
					 * the hourly time interval being saved is a time interval
					 * duplicate of an existing time post.
					 * 
					 * TODO : Determine how to handle this situation? We can deal with a overwriting
					 *        an existing time posting when saving a new time post, but when
					 *        updating an existing one that is a duplicate of other existing posting(s), 
					 *        and the user has indicated to 'OVERWRITE' when prompted from the rule check,
					 *        do we:
					 *         1) save this entity, and update all other existing ones also?
					 *         2) not save this entity, and update all other existing ones?
					 *         3) save this entity, and delete all other existing ones? 
					 */
					Collection<AssignmentTimePostVo> vosToSave = new ArrayList<AssignmentTimePostVo>();
					Collection<AssignmentTimePostVo> vosToDelete = new ArrayList<AssignmentTimePostVo>();
					
					CourseOfActionVo coa = super.getCoa(ContractorPrimaryHourlyDuplicateRules._RULE_NAME,dialogueVo);
					Collection<Object> ids = (Collection<Object>)coa.getStoredObject();
					int i=0;
					for(Object id : ids){
						Long lngId=TypeConverter.convertToLong(id);
						if(i>0){
							// remove the rest
							AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
							tpDao.flushAndEvict(atp);
							vosToDelete.add(AssignmentTimePostVo.getInstance(atp, true));
							tpDao.delete(atp);
						}else{
							AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
							tpDao.flushAndEvict(atp);
							Date start = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
							atp.setPostStartDate(start);
							Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStopDate(), vo.getPostStopTime());
							atp.setPostStopDate(stop);
							atp.setQuantity(vo.getQuantity());
							if(null != vo.getIncidentAccountCodeVo()){
								IncidentAccountCode iac = new IncidentAccountCodeImpl();
								iac.setId(vo.getIncidentAccountCodeVo().getId());
								iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
								atp.setIncidentAccountCode(iac);
							}
							tpDao.save(atp);
							tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
							tpDao.flushAndEvict(atp.getIncidentAccountCode());
							tpDao.flushAndEvict(atp);
							((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atp, true));
							
							vosToSave.add(vo);
						}
						i++;
					}
					dialogueVo.setResultObjectAlternate2(vosToDelete);
					
				}else{
					/*
					 * Proceed with update
					 */
					AssignmentTimePostVo atpvo=vo.clone();
					
					atpvo.setUnitOfMeasure(vo.getRefContractorRateVo().getUnitOfMeasure());
					atpvo.setRateAmount(vo.getRefContractorRateVo().getRateAmount());
					atpvo.setPostStartDate(DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), originalStartTime));
					atpvo.setPostStopDate(DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), originalStopTime));
					if(null != vo.getIncidentAccountCodeVo()){
						IncidentAccountCode iac = new IncidentAccountCodeImpl();
						iac.setId(vo.getIncidentAccountCodeVo().getId());
						iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
						entity.setIncidentAccountCode(iac);
					}

					saveHourlyTimePosting(entity,atpvo,dialogueVo);
					
				}
				
			}
			
		}else{
			/*
			 * User is saving a new time posting.
			 */
			if(needSplitPosting){
				/*
				 * New time posting needs to be split into 2 postings.
				 * 
				 */
				splitTimePosting(dialogueVo);
				
			}else{
				/*
				 * No split needed here, the hours for the time posting
				 * fall onto a single date.
				 */
				
				if(needOverwrite){
					/*
					 * If entity is null, 
					 *    needSplitPosting is false,
					 *    and the needOverwrite flag is true ......
					 *    
					 * then the user is trying to save a new time posting that
					 * has a duplicate time interval of an existing 'HOURLY' time posting, 
					 * and the user answered overwrite when prompted from the rule check.
					 * 
					 * In this scenario, we do not create a new time posting.
					 * Instead we update all existing records
					 * that match the time interval 
					 * and that match unit of measure = 'HOURLY'
					 * and that match primary flag = true
					 * and that match guarantee flag = false
					 * 
					 */
					Collection<AssignmentTimePostVo> vosToSave = new ArrayList<AssignmentTimePostVo>();
					Collection<AssignmentTimePostVo> vosToDelete = new ArrayList<AssignmentTimePostVo>();
					
					CourseOfActionVo coa = super.getCoa(ContractorPrimaryHourlyDuplicateRules._RULE_NAME,dialogueVo);
					Collection<Object> ids = (Collection<Object>)coa.getStoredObject();
					int i=0;
					for(Object id : ids){
						Long lngId=TypeConverter.convertToLong(id);
						if(i>0){
							// remove the rest
							AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
							tpDao.flushAndEvict(atp);
							vosToDelete.add(AssignmentTimePostVo.getInstance(atp, true));
							tpDao.delete(atp);
						}else{
							AssignmentTimePost atp = tpDao.getById(lngId, AssignmentTimePostImpl.class);
							tpDao.flushAndEvict(atp);
							Date start = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
							atp.setPostStartDate(start);
							Date stop =null;
							if(null != vo.getPostStopDate())
								stop = DateUtil.addMilitaryTimeToDate(vo.getPostStopDate(), vo.getPostStopTime());
							else
								stop = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
							atp.setPostStopDate(stop);
							atp.setIsHalfRate(vo.getIsHalfRate());
							atp.setQuantity(vo.getQuantity());
							if(null != vo.getIncidentAccountCodeVo()){
								IncidentAccountCode iac = new IncidentAccountCodeImpl();
								iac.setId(vo.getIncidentAccountCodeVo().getId());
								iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
								atp.setIncidentAccountCode(iac);
							}
							tpDao.save(atp);
							tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
							tpDao.flushAndEvict(atp.getIncidentAccountCode());
							tpDao.flushAndEvict(atp);
							vosToSave.add(vo);
							((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atp, true));
						}
						i++;
					}
					/*
					Date startDate = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), originalStartTime);
					Date stopDate = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), originalStopTime);
					filter.setStartDate(startDate);
					filter.setStopDate(stopDate);
					
					Collection<AssignmentTimePost> entities = tpDao.getByFilter(filter);
					for(AssignmentTimePost atp : entities){
						atp.setPostStartDate(startDate);
						atp.setPostStopDate(stopDate);
						atp.setQuantity(vo.getQuantity());
						tpDao.save(atp);
						tpDao.flushAndEvict(atp);
					}
					*/
					dialogueVo.setResultObjectAlternate2(vosToDelete);
				}else{
					/*
					 * Proceed with saving new, no split, no overwrite.
					 */
					AssignmentTimePostVo atpvo=vo.clone();
					atpvo.setPostStartDate(DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), originalStartTime));
					atpvo.setPostStopDate(DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), originalStopTime));
					atpvo.setUnitOfMeasure(vo.getRefContractorRateVo().getUnitOfMeasure());
					atpvo.setRateAmount(vo.getRefContractorRateVo().getRateAmount());

					saveHourlyTimePosting(null,atpvo,dialogueVo);	
					
				}
			}
		}

	}

	/**
	 * Splits new time posting.
	 * 
	 * @throws Exception
	 */
	private void splitTimePosting(DialogueVo dialogueVo) throws Exception {
		/*
		 * 1st date hourly time posting 
		 */
		AssignmentTimePostVo vo1=splitTime(0, vo, "2359");
		vo1.setUnitOfMeasure(vo1.getRefContractorRateVo().getUnitOfMeasure());
		vo1.setRateAmount(vo1.getRefContractorRateVo().getRateAmount());

		if(needOverwrite){
			// check firstdupids , coa.storedObject2
			CourseOfActionVo coa =dialogueVo.getCourseOfActionByName(ContractorPrimaryHourlyDuplicateRules._RULE_NAME);
			if(null != coa && null != coa.getStoredObject2()){
				Collection<Object> firstDupIds=(Collection<Object>)coa.getStoredObject2();
				if(CollectionUtility.hasValue(firstDupIds)){
					int i=0;
					for(Object dupid : firstDupIds){
						Long dupId = TypeConverter.convertToLong(dupid);
						if(i>0){
							// delete it
							AssignmentTimePost atp = tpDao.getById(dupId, AssignmentTimePostImpl.class);
							AssignmentTimePostVo voDelete=new AssignmentTimePostVo();
							voDelete.setId(atp.getId());
							tpDao.flushAndEvict(atp);
							tpDao.delete(atp);
							((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate2()).add(voDelete);
						}else{
							AssignmentTimePost entity = tpDao.getById(dupId, AssignmentTimePostImpl.class);

							if(!LongUtility.hasValue(vo.getId())){
								entity.setPostStartDate(vo1.getPostStartDate());
								entity.setPostStopDate(vo1.getPostStopDate());
								entity.setIsHalfRate(vo1.getIsHalfRate());
								entity.setQuantity(vo1.getQuantity());
								
								if(null != vo.getIncidentAccountCodeVo()){
									IncidentAccountCode iac = new IncidentAccountCodeImpl();
									iac.setId(vo.getIncidentAccountCodeVo().getId());
									iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
									entity.setIncidentAccountCode(iac);
								}
								tpDao.save(entity);
								tpDao.flushAndEvict(entity);
								entity = tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
								((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
							}else{
								entity.setPostStartDate(vo1.getPostStartDate());
								entity.setPostStopDate(vo1.getPostStopDate());
								entity.setIsHalfRate(vo1.getIsHalfRate());
								entity.setQuantity(vo1.getQuantity());
								
								if(null != vo.getIncidentAccountCodeVo()){
									IncidentAccountCode iac = new IncidentAccountCodeImpl();
									iac.setId(vo.getIncidentAccountCodeVo().getId());
									iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
									entity.setIncidentAccountCode(iac);
								}
								tpDao.save(entity);
								tpDao.flushAndEvict(entity);
								entity = tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
								((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
							}
							
						}
						i++;
					}
				}else{
					needOverwrite=false;
					saveHourlyTimePosting(null,vo1,dialogueVo);
					needOverwrite=true;
				}
			}
		}else{
			if(LongUtility.hasValue(vo.getId())){
				AssignmentTimePost entity =tpDao.getById(vo.getId(), AssignmentTimePostImpl.class);
				entity = AssignmentTimePostVo.toEntity(entity, vo1, true);
				tpDao.save(entity);

				tpDao.flushAndEvict(entity);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
			}else
				saveHourlyTimePosting(null,vo1,dialogueVo);
		}

		// 2nd date hourly time posting
		AssignmentTimePostVo vo2=splitTime(1, vo, originalStopTime);
		vo2.setUnitOfMeasure(vo2.getRefContractorRateVo().getUnitOfMeasure());
		vo2.setRateAmount(vo2.getRefContractorRateVo().getRateAmount());
		
		if(needOverwrite){
			// check seconddupids , coa.storedObject3
			CourseOfActionVo coa =dialogueVo.getCourseOfActionByName(ContractorPrimaryHourlyDuplicateRules._RULE_NAME);
			if(null != coa && null != coa.getStoredObject3()){
				Collection<Object> secondDupIds=(Collection<Object>)coa.getStoredObject3();
				if(CollectionUtility.hasValue(secondDupIds)){
					int i=0;
					for(Object dupid: secondDupIds){
						Long dupId = TypeConverter.convertToLong(dupid);
						if(i>0){
							// delete it
							AssignmentTimePost atp = tpDao.getById(dupId, AssignmentTimePostImpl.class);
							AssignmentTimePostVo voDelete=new AssignmentTimePostVo();
							voDelete.setId(atp.getId());
							tpDao.flushAndEvict(atp);
							tpDao.delete(atp);
							((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate2()).add(voDelete);
						}else{
							AssignmentTimePost entity = tpDao.getById(dupId, AssignmentTimePostImpl.class);
							entity.setPostStartDate(vo2.getPostStartDate());
							entity.setPostStopDate(vo2.getPostStopDate());
							entity.setIsHalfRate(vo2.getIsHalfRate());
							entity.setQuantity(vo2.getQuantity());
							
							if(null != vo.getIncidentAccountCodeVo()){
								IncidentAccountCode iac = new IncidentAccountCodeImpl();
								iac.setId(vo.getIncidentAccountCodeVo().getId());
								iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
								entity.setIncidentAccountCode(iac);
							}
							
							tpDao.save(entity);
							tpDao.flushAndEvict(entity);
							entity = tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
							((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
						}
						i++;
					}
				}else{
					needOverwrite=false;
					saveHourlyTimePosting(null,vo2,dialogueVo);
					needOverwrite=true;
				}
			}
		}else{
			vo2.setId(null);
			AssignmentTimePost atpEntity = AssignmentTimePostVo.toEntity(null, vo2, true);
			
			tpDao.save(atpEntity);
			tpDao.flushAndEvict(atpEntity);
			parentEntity = tpDao.getById(atpEntity.getId(), AssignmentTimePostImpl.class);
			((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atpEntity, true));
		}
	}

	private void saveHourlyTimePosting(AssignmentTimePost atpEntity,AssignmentTimePostVo atpvo, DialogueVo dialogueVo) throws Exception {

		if(needOverwrite){
			/*
			 * Determine if this hourly time posting 
			 * is a time interval duplicate.
			 */
			filter.setStartDate(atpvo.getPostStartDate());
			filter.setStartDate(atpvo.getPostStopDate());

			if(null!=atpEntity)
				filter.setAssignmentTimePostId(null);
			
			Collection<AssignmentTimePost> entities = tpDao.getByFilter(filter);

			if(CollectionUtility.hasValue(entities)){
				/*
				 * Save each one with the atpvo.postStart/postStop dates
				 */
				for(AssignmentTimePost atp : entities){
					atp.setPostStartDate(atpvo.getPostStartDate());
					atp.setPostStopDate(atpvo.getPostStopDate());
					atp.setIsHalfRate(atpvo.getIsHalfRate());
					atp.setQuantity(atpvo.getQuantity());
					if(null != vo.getIncidentAccountCodeVo()){
						IncidentAccountCode iac = new IncidentAccountCodeImpl();
						iac.setId(vo.getIncidentAccountCodeVo().getId());
						iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
						atp.setIncidentAccountCode(iac);
					}
					tpDao.save(atp);
					tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
					tpDao.flushAndEvict(atp.getIncidentAccountCode());
					tpDao.flushAndEvict(atp);
					((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atp, true));
				}
			}else{
				/*
				 * No matches found, atpvo is not a time interval duplicate
				 */
				atpEntity = AssignmentTimePostVo.toEntity(atpEntity, atpvo, true);
				tpDao.save(atpEntity);
				tpDao.flushAndEvict(atpEntity);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atpEntity, true));
				
			}
			
		}else{
			/*
			 * No overwrite needed.
			 * 
			 * Save the hourly time posting
			 */
			if(null != atpEntity){
				Date start = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
				atpEntity.setPostStartDate(start);

				Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStopDate(), vo.getPostStopTime());
				atpEntity.setPostStopDate(stop);
				
				atpEntity.setQuantity(vo.getQuantity());
				atpEntity.setIsHalfRate(vo.getIsHalfRate());
				if(null != vo.getIncidentAccountCodeVo()){
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(vo.getIncidentAccountCodeVo().getId());
					iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
					atpEntity.setIncidentAccountCode(iac);
				}

				tpDao.save(atpEntity);
				tpDao.flushAndEvict(atpEntity);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atpEntity, true));
			}else{
				atpEntity = AssignmentTimePostVo.toEntity(atpEntity, atpvo, true);
				atpEntity.setContractorPostType("PRIMARY");
				if(null != vo.getIncidentAccountCodeVo()){
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(vo.getIncidentAccountCodeVo().getId());
					iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
					atpEntity.setIncidentAccountCode(iac);
				}
				
				tpDao.save(atpEntity);
				tpDao.flushAndEvict(atpEntity);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atpEntity, true));
				
			}
			
			tpDao.flushAndEvict(atpEntity);
			atpEntity=tpDao.getById(atpEntity.getId(), AssignmentTimePostImpl.class);
			((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atpEntity, true));
			
		}
	}
	
}
