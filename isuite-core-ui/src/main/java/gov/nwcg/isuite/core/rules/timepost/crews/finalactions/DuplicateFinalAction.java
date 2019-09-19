package gov.nwcg.isuite.core.rules.timepost.crews.finalactions;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
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
public class DuplicateFinalAction extends AbstractCrewFinalAction implements ICrewFinalAction{

	public DuplicateFinalAction(ApplicationContext ctx){
		super(ctx);
	} 
	
	public int saveCrewPosting(AssignmentTime atEntity,AssignmentTimePostVo vo,DialogueVo dialogueVo) throws Exception {
		
		
		Collection<Long> ids = this.getDuplicateIds(atEntity, vo);
		
		if(CollectionUtility.hasValue(ids) && ids.size()>0){
			for(Long id : ids){
				AssignmentTimePost entity = tpDao.getById(id, AssignmentTimePostImpl.class);
				
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
					
					if(VoValidator.isValidAbstractVo(vo.getIncidentAccountCodeVo())){
						entity.setIncidentAccountCode(IncidentAccountCodeVo.toEntity(null, vo.getIncidentAccountCodeVo(),false));
					}

					if(VoValidator.isValidAbstractVo(vo.getKindVo())){
						entity.setKind(vo.getKindVo().toEntity(null, vo.getKindVo(), false));
					}
					
					if(VoValidator.isValidAbstractVo(vo.getRefContractorRateVo())){
						entity.setRefContractorRate(ContractorRateVo.toEntity(null, vo.getRefContractorRateVo(), false));
					}
					
					if(VoValidator.isValidAbstractVo(vo.getSpecialPayVo())){
						entity.setSpecialPay(vo.getSpecialPayVo().toEntity(vo.getSpecialPayVo(), false));
					}else
						entity.setSpecialPay(null);

					entity.setRateAmount(vo.getRateAmount());
					entity.setTraining(vo.getTraining());
					entity.setReturnTravelStartOnly(vo.getReturnTravelStartOnly());
					
					if(atEntity.getEmploymentType()==EmploymentTypeEnum.FED){
						entity.setRateClassRate(null);
						entity.setRateAmount(new BigDecimal(0.0));
					}

					
					tpDao.save(entity);
				}
			}
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
