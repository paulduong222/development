package gov.nwcg.isuite.core.rules.timepost.contractor.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.filter.impl.TimePostQueryFilterImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.timepost.contractor.ContractorPrimaryDailyDuplicateRules;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

@SuppressWarnings("unchecked")
public class SavePrimaryDailyPostings extends BaseFinalAction {
	private Boolean needOverwrite;
	private Collection<Long> duplicateIds = new ArrayList<Long>();
	private String duplicateDates="";
	
	private TimePostQueryFilterImpl tpqFilter;
	
	public SavePrimaryDailyPostings(TimePostDao dao
									, AssignmentTimePostVo atpvo
									, IncidentResource incidentResourceEntity
									, AssignmentTime atEntity){
		super.tpDao=dao;
		super.vo=atpvo;
		super.incidentResourceEntity = incidentResourceEntity;
		super.assignmentTimeEntity = atEntity;
	}
	
	/**
	 * Saves the primary daily time postings.
	 * 
	 * @param dialogueVo
	 * @throws Exception
	 */
	public void save(AssignmentTimePost entity,DialogueVo dialogueVo) throws Exception {

		if(LongUtility.hasValue(vo.getId()))
			entity = tpDao.getById(vo.getId(), AssignmentTimePostImpl.class);
		
		/*
		 * Set flag if we need to overwrite existing records.
		 */
		needOverwrite = 
			checkForAdditionalActionNeeded(ContractorPrimaryDailyDuplicateRules._RULE_NAME,dialogueVo);
		

		if(needOverwrite==true){
			CourseOfActionVo coaVo = super.getCoa(ContractorPrimaryDailyDuplicateRules._RULE_NAME, dialogueVo);
			if(null != coaVo){
				Collection<Object> ids = (Collection<Object>)coaVo.getStoredObject();
				duplicateIds=LongUtility.convertToLongs(ids);
				duplicateDates=(String)coaVo.getStoredObject1();
			}
			
			// update the ones that need overwritten
			for(Long atpId : duplicateIds){
				AssignmentTimePost atp = tpDao.getById(atpId, AssignmentTimePostImpl.class);
				if(null != vo.getIncidentAccountCodeVo()){
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(vo.getIncidentAccountCodeVo().getId());
					iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
					atp.setIncidentAccountCode(iac);
				}
				atp.setIsHalfRate(vo.getIsHalfRate());
				//atp.setQuantity(vo.getQuantity());
				atp.setQuantity(BigDecimal.valueOf(1.0));
				tpDao.save(atp);
				tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
				tpDao.flushAndEvict(atp.getIncidentAccountCode());
				tpDao.flushAndEvict(atp);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atp, true));
			}
			
		}
		

		if(LongUtility.hasValue(vo.getId())){
			// update existing record
			entity = AssignmentTimePostVo.toEntity(entity, vo, true);
			tpDao.save(entity);
			tpDao.flushAndEvict(entity.getIncidentAccountCode().getAccountCode());
			tpDao.flushAndEvict(entity.getIncidentAccountCode());
			tpDao.flushAndEvict(entity);
			entity = tpDao.getById(vo.getId(), AssignmentTimePostImpl.class);
			((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
			
		}else{
			/*
			 * Determine how many days to post
			 */
			int diffDays = (int)DateUtil.diffDays(vo.getPostStartDate(), vo.getPostStopDate());
			
			Date startDate=vo.getPostStartDate();
			for(int x=0;x<(diffDays+1);x++){
				AssignmentTimePostVo atpvo=vo.clone();
				if(x>0)
					atpvo.setPostStartDate(DateUtil.addDays(startDate, x));

				//for daily, start/stop date must be the same
				atpvo.setPostStopDate(atpvo.getPostStartDate());
				
				entity=null;  // need to reset to null
				entity = AssignmentTimePostVo.toEntity(entity, atpvo, true);
				
				if(null != vo.getIncidentAccountCodeVo()){
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(vo.getIncidentAccountCodeVo().getId());
					iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
					entity.setIncidentAccountCode(iac);
				}
				entity.setContractorPostType("PRIMARY");
				entity.setQuantity(new BigDecimal(1));
				
				Boolean isDuplicateDate=false;
				if(needOverwrite){
					// check if this date is in duplicateDates
					if(this.hasDuplicateDate(atpvo.getPostStartDate())==true){
						isDuplicateDate=true;
					}
				}
				
				// only create records for non duplicates
				if(isDuplicateDate==false){
					// create the record
					tpDao.save(entity);
					tpDao.flushAndEvict(entity.getIncidentAccountCode().getAccountCode());
					tpDao.flushAndEvict(entity.getIncidentAccountCode());
					tpDao.flushAndEvict(entity);
					entity=tpDao.getById(entity.getId(), AssignmentTimePostImpl.class);
					((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
				}
				
			}
			
		}
		
	}

	private Boolean hasDuplicateDate(Date dt) throws Exception {
		StringTokenizer tokens = new StringTokenizer(this.duplicateDates,",");
		while(tokens.hasMoreTokens()){
			String sdate = (String)tokens.nextToken();
			Date tokenDate=DateUtil.toDate(sdate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
			
			if(DateUtil.isSameDate(tokenDate, dt)){
				return true;
			}
		}
		return false;
	}
	
}
