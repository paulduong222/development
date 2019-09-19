package gov.nwcg.isuite.core.rules.timepost.contractor.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.timepost.contractor.ContractorSpecialEachDuplicateRules;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class SaveSpecialEachPostings extends BaseFinalAction {
	private Boolean needOverwrite=false;
	private Collection<Long> duplicateIds = new ArrayList<Long>();
	
	public SaveSpecialEachPostings(TimePostDao dao
									,AssignmentTimePostVo atpvo
									,IncidentResource irEntity){
		super.tpDao=dao;
		super.specialVo=atpvo;
		super.incidentResourceEntity=irEntity;
	}
	
	/**
	 * @param entity
	 * @param dialogueVo
	 * @throws Exception
	 */
	public void save(AssignmentTimePost entity,DialogueVo dialogueVo) throws Exception {
	
		if(LongUtility.hasValue(specialVo.getId()))
			entity = tpDao.getById(specialVo.getId(), AssignmentTimePostImpl.class);
		
		/*
		 * Set flag if we need to overwrite existing records.
		 */
		needOverwrite = 
			checkForAdditionalActionNeeded(ContractorSpecialEachDuplicateRules._RULE_NAME,dialogueVo);

		if(needOverwrite==true){
			/*
			 * if overwriting, update all duplicateIds
			 */
			CourseOfActionVo coaVo = super.getCoa(ContractorSpecialEachDuplicateRules._RULE_NAME, dialogueVo);
			if(null != coaVo){
				Collection<Object> ids = (Collection<Object>)coaVo.getStoredObject();
				duplicateIds=LongUtility.convertToLongs(ids);

				for(Long atpId : duplicateIds){
					AssignmentTimePost atp = tpDao.getById(atpId, AssignmentTimePostImpl.class);
					AssignmentTimePostVo atpvo=specialVo.clone();
					if(null != specialVo.getIncidentAccountCodeVo()){
						IncidentAccountCode iac = new IncidentAccountCodeImpl();
						iac.setId(specialVo.getIncidentAccountCodeVo().getId());
						iac.setAccountCode(AccountCodeVo.toEntity(null, specialVo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
						atp.setIncidentAccountCode(iac);
					}
					atp.setIsHalfRate(atpvo.getIsHalfRate());
					atp.setQuantity(atpvo.getQuantity());
					tpDao.save(atp);
					tpDao.flushAndEvict(atp);
					tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
					tpDao.flushAndEvict(atp.getIncidentAccountCode());
					atp = tpDao.getById(atpId, AssignmentTimePostImpl.class);
					
					if(null != specialVo.getIncidentAccountCodeVo()){
						IncidentAccountCode iac = new IncidentAccountCodeImpl();
						iac.setId(specialVo.getIncidentAccountCodeVo().getId());
						iac.setAccountCode(AccountCodeVo.toEntity(null, specialVo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
						atp.setIncidentAccountCode(iac);
					}
					
					((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atp, true));
					tpDao.flushAndEvict(atp);
					tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
					tpDao.flushAndEvict(atp.getIncidentAccountCode());
				}
			}
		}

		/*
		 * Only save if updating an existing record or if overwrite is false
		 */
		if(LongUtility.hasValue(specialVo.getId()) || needOverwrite==false){
			specialVo.setContractorPostType("SPECIAL");
			entity = AssignmentTimePostVo.toEntity(entity, specialVo, true);
			
			if(null != specialVo.getIncidentAccountCodeVo()){
				IncidentAccountCode iac = new IncidentAccountCodeImpl();
				iac.setId(specialVo.getIncidentAccountCodeVo().getId());
				iac.setAccountCode(AccountCodeVo.toEntity(null, specialVo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
				entity.setIncidentAccountCode(iac);
			}
			
			AssignmentTime assignmentTime = new AssignmentTimeImpl();
			assignmentTime.setId(specialVo.getAssignmentTimeId());
			entity.setAssignmentTime(assignmentTime);
			entity.setPrimaryPosting(false);
			entity.setSpecialPosting(true);
			
			tpDao.save(entity);
			tpDao.flushAndEvict(entity.getIncidentAccountCode());
			tpDao.flushAndEvict(entity);
			((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
			
		}

	}
}
