package gov.nwcg.isuite.core.rules.timepost.contractor.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.rules.timepost.contractor.ContractorPrimaryMileageDuplicateRules;
import gov.nwcg.isuite.core.vo.AccountCodeVo;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class SavePrimaryMileagePostings extends BaseFinalAction {
	private Boolean needOverwrite = false;
	private Collection<Long> duplicateIds = new ArrayList<Long>();
	
	public SavePrimaryMileagePostings(TimePostDao dao
									,AssignmentTimePostVo atpvo
									,IncidentResource irEntity){
		super.tpDao=dao;
		super.vo=atpvo;
		super.incidentResourceEntity=irEntity;
	}
	
	/**
	 * @param entity
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
			checkForAdditionalActionNeeded(ContractorPrimaryMileageDuplicateRules._RULE_NAME,dialogueVo);
		
		if(needOverwrite==true){
			/*
			 * overwrite the duplicates with new information
			 */
			CourseOfActionVo coaVo = super.getCoa(ContractorPrimaryMileageDuplicateRules._RULE_NAME, dialogueVo);
			if(null != coaVo){
				Collection<Object> ids = (Collection<Object>)coaVo.getStoredObject();
				duplicateIds=LongUtility.convertToLongs(ids);
			}
			
			for(Long atpId : duplicateIds){
				AssignmentTimePost atp = tpDao.getById(atpId, AssignmentTimePostImpl.class);
				if(null != vo.getIncidentAccountCodeVo()){
					IncidentAccountCode iac = new IncidentAccountCodeImpl();
					iac.setId(vo.getIncidentAccountCodeVo().getId());
					iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
					atp.setIncidentAccountCode(iac);
				}
				atp.setIsHalfRate(vo.getIsHalfRate());
				atp.setQuantity(vo.getQuantity());
				tpDao.save(atp);
				tpDao.flushAndEvict(atp.getIncidentAccountCode().getAccountCode());
				tpDao.flushAndEvict(atp.getIncidentAccountCode());
				tpDao.flushAndEvict(atp);
				((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(atp, true));
			}
			
		}
		
		/*
		 * Only save if updating an existing record or if overwrite is false
		 */
		if(LongUtility.hasValue(vo.getId()) || needOverwrite==false){
			vo.setContractorPostType("PRIMARY");
			entity = AssignmentTimePostVo.toEntity(entity, vo, true);
			
			if(null != vo.getIncidentAccountCodeVo()){
				IncidentAccountCode iac = new IncidentAccountCodeImpl();
				iac.setId(vo.getIncidentAccountCodeVo().getId());
				iac.setAccountCode(AccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo().getAccountCodeVo(), true));
				entity.setIncidentAccountCode(iac);
			}
			
			AssignmentTime assignmentTime = new AssignmentTimeImpl();
			assignmentTime.setId(vo.getAssignmentTimeId());
			entity.setAssignmentTime(assignmentTime);
			entity.setPrimaryPosting(true);
			entity.setSpecialPosting(false);
			
			tpDao.save(entity);
			tpDao.flushAndEvict(entity.getIncidentAccountCode());
			tpDao.flushAndEvict(entity);
			((Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3()).add(AssignmentTimePostVo.getInstance(entity, true));
		}
		
	}
	
	
}
