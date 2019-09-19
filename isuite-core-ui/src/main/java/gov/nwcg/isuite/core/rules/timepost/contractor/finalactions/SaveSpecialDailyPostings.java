package gov.nwcg.isuite.core.rules.timepost.contractor.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.timepost.contractor.ContractorSpecialDailyDuplicateRules;
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
public class SaveSpecialDailyPostings extends BaseFinalAction {
	private Boolean needOverwrite=false;
	private Collection<Long> duplicateIds = new ArrayList<Long>();
	private String duplicateDates="";
	
	public SaveSpecialDailyPostings(TimePostDao dao
									, AssignmentTimePostVo atpvo
									, IncidentResource incidentResourceEntity){
		super.tpDao=dao;
		super.specialVo=atpvo;
		super.incidentResourceEntity = incidentResourceEntity;
	}
	
	/**
	 * Saves the special daily time postings.
	 * 
	 * @param dialogueVo
	 * @throws Exception
	 */
	public void save(AssignmentTimePost entity, DialogueVo dialogueVo) throws Exception {

		if((null==entity) && LongUtility.hasValue(specialVo.getId()))
			entity = tpDao.getById(specialVo.getId(), AssignmentTimePostImpl.class);
		
		/*
		 * Set flag if we need to overwrite existing records.
		 */
		needOverwrite = 
			checkForAdditionalActionNeeded(ContractorSpecialDailyDuplicateRules._RULE_NAME,dialogueVo);

		if(needOverwrite==true){
			CourseOfActionVo coaVo = super.getCoa(ContractorSpecialDailyDuplicateRules._RULE_NAME, dialogueVo);
			if(null != coaVo){
				Collection<Object> ids = (Collection<Object>)coaVo.getStoredObject();
				duplicateIds=LongUtility.convertToLongs(ids);
				duplicateDates=(String)coaVo.getStoredObject1();
			}
			
			// update the ones that need overwritten
			for(Long atpId : duplicateIds){
				AssignmentTimePost atp = tpDao.getById(atpId, AssignmentTimePostImpl.class);
				if(null != specialVo.getIncidentAccountCodeVo()){
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(specialVo.getIncidentAccountCodeVo().getId());
					iac.setAccountCode(AccountCodeVo.toEntity(null, specialVo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
					atp.setIncidentAccountCode(iac);
				}
				atp.setIsHalfRate(specialVo.getIsHalfRate());
				atp.setQuantity(specialVo.getQuantity());
				tpDao.save(atp);
				tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
				tpDao.flushAndEvict(atp.getIncidentAccountCode());
				tpDao.flushAndEvict(atp);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atp, true));
			}
			
		}

		
		if(LongUtility.hasValue(specialVo.getId())){
			// update existing record
			entity = AssignmentTimePostVo.toEntity(entity, specialVo, true);
			tpDao.save(entity);
			tpDao.flushAndEvict(entity.getIncidentAccountCode().getAccountCode());
			tpDao.flushAndEvict(entity.getIncidentAccountCode());
			tpDao.flushAndEvict(entity);
			entity = tpDao.getById(specialVo.getId(), AssignmentTimePostImpl.class);
			((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
			
		}else{
			/*
			 * Determine how many days to post
			 */
			Date dt1=specialVo.getPostStartDate();
			Date dt2=specialVo.getPostStopDate();
			dt1=DateUtil.addMilitaryTimeToDate(dt1, "1200");
			dt2=DateUtil.addMilitaryTimeToDate(dt2, "1200");
			int diffDays = (int)DateUtil.diffDays(dt1, dt2);
			//int diffDays = (int)DateUtil.diffDays(vo.getPostStartDate(), vo.getPostStopDate());
			//int diffDays = (int)DateUtil.diffDays(specialVo.getPostStartDate(), specialVo.getPostStopDate());
			
			Date startDate=specialVo.getPostStartDate();
			for(int x=0;x<(diffDays+1);x++){
				AssignmentTimePostVo atpvo=specialVo.clone();
				if(x>0)
					atpvo.setPostStartDate(DateUtil.addDays(startDate, x));
				
				//for daily, start/stop date must be the same
				atpvo.setPostStopDate(atpvo.getPostStartDate());
				
				entity=null;  // need to reset to null
				entity = AssignmentTimePostVo.toEntity(entity, atpvo, true);
				
				if(null != specialVo.getIncidentAccountCodeVo()){
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(specialVo.getIncidentAccountCodeVo().getId());
					iac.setAccountCode(AccountCodeVo.toEntity(null, specialVo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
					entity.setIncidentAccountCode(iac);
				}
				entity.setContractorPostType("SPECIAL");
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
