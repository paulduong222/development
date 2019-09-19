package gov.nwcg.isuite.core.rules.timepost.crews.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.KindDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

/*
 * This class handles the situation where the time post being saved
 * is a duplicate.
 */
public class DuplicateFinalActionCrew extends AbstractCrewFinalAction implements ICrewFinalAction{
	public Long atpId;
	
	public DuplicateFinalActionCrew(ApplicationContext ctx, int ccount){
		super(ctx);
		super.crewCount=ccount;
	} 
	
	public int saveCrewPosting(AssignmentTime atEntity,AssignmentTimePostVo voOrig,DialogueVo dialogueVo) throws Exception {
		
		super.kindDao=(KindDao)context.getBean("kindDao");
		
		AssignmentTimePost entity = tpDao.getById(atpId, AssignmentTimePostImpl.class);
		Kind kind=kindDao.getById(entity.getKindId(), KindImpl.class);
		kindDao.flushAndEvict(kind);
		
		AssignmentTimePostVo vo = (AssignmentTimePostVo)voOrig.clone();
		
		if(null != entity){
					
			// set the correct times
			if(StringUtility.hasValue(vo.getPostStartTime())){
				if(null != vo.getPostStartDate()){
					Date start = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
					entity.setPostStartDate(start);
				}
			}else{
				if(null != vo.getPostStartDate()){
					entity.setPostStartDate(vo.getPostStartDate());
				}
			}
					
			if(StringUtility.hasValue(vo.getPostStopTime())){
				if(null != vo.getPostStopDate()){
					Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStopDate(), vo.getPostStopTime());
					entity.setPostStopDate(stop);
				}else{
					Date stop = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
					entity.setPostStopDate(stop);
				}
			}else{
				entity.setPostStopDate(null);
				entity.setQuantity(vo.getQuantity());
			}

			entity.setQuantity(vo.getQuantity());
			
			if(VoValidator.isValidAbstractVo(vo.getIncidentAccountCodeVo())){
				entity.setIncidentAccountCode(IncidentAccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo(),true));
			}

			if(VoValidator.isValidAbstractVo(vo.getKindVo())){
				if(entity.getKind()!=null){
					if(vo.getAssignmentTimeId().compareTo(entity.getAssignmentTimeId())==0)
						entity.setKind(vo.getKindVo().toEntity(null, vo.getKindVo(), false));
					else
						entity.setKind(kind);
				}else
					entity.setKind(vo.getKindVo().toEntity(null, vo.getKindVo(), false));
					
			}
					
			if(VoValidator.isValidAbstractVo(vo.getRefContractorRateVo())){
				entity.setRefContractorRate(ContractorRateVo.toEntity(null, vo.getRefContractorRateVo(), false));
			}
					
			super.verifySpecialCodes(atEntity, vo);
			if(VoValidator.isValidAbstractVo(vo.getSpecialPayVo())){
				entity.setSpecialPay(vo.getSpecialPayVo().toEntity(vo.getSpecialPayVo(), false));
			}else
				entity.setSpecialPay(null);

			
			// only update rate for primary record used during posting
			if(vo.getAssignmentTimeId().compareTo(entity.getAssignmentTimeId())==0)
				entity.setRateAmount(vo.getRateAmount());
			
			entity.setTraining(vo.getTraining());
			entity.setReturnTravelStartOnly(vo.getReturnTravelStartOnly());
			
			if(atEntity.getEmploymentType()==EmploymentTypeEnum.FED){
				entity.setRateClassRate(null);
				entity.setRateAmount(new BigDecimal(0.0));
			}

			tpDao.save(entity);
			if(VoValidator.isValidAbstractVo(vo.getKindVo())){
				if(vo.getAssignmentTimeId().compareTo(entity.getAssignmentTimeId())==0)
					entity.getKind().setCode(vo.getKindVo().getCode());
			}
			if(VoValidator.isValidAbstractVo(vo.getSpecialPayVo())){
				if(null != entity.getSpecialPay()){
					entity.getSpecialPay().setCode(vo.getSpecialPayVo().getCode());
				}
			}
			tpDao.flushAndEvict(entity);
			
			
			Collection<AssignmentTimePostVo> vosToSave=(Collection<AssignmentTimePostVo>)dialogueVo.getResultObjectAlternate3();
			vosToSave.add(AssignmentTimePostVo.getInstance(entity, true));
			dialogueVo.setResultObjectAlternate3(vosToSave);
		}
		
		return AbstractRule._OK;
	}
	
	private Collection<Long> getDuplicateIds(AssignmentTime atEntity,AssignmentTimePostVo vo) throws Exception {
		Date startTime=null;
		
		IncidentResourceDao irDao = (IncidentResourceDao)super.context.getBean("incidentResourceDao");
		
		WorkPeriod wp = atEntity.getAssignment().getWorkPeriods().iterator().next();

		irDao.flushAndEvict(wp.getIncidentResource());

		startTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStartTime());
		Date stopTime = DateUtil.addMilitaryTimeToDate(vo.getPostStartDate(), vo.getPostStopTime());
					
		if(null != startTime && null != stopTime){
			Collection<Long> ids = tpDao.getDuplicateTimePostIds(vo.getId(),wp.getIncidentResourceId(), false, startTime, stopTime);
			return ids;
		}
		
		return null;
	}
	
}
