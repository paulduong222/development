package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.persistence.CostDataDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.CostDataVo;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckRunCostAutoRules extends AbstractIncidentResourceSaveRule implements IRule{
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.CHECK_RUN_COST_AUTO.name();

	public CheckRunCostAutoRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

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
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
	
		/*
		 * Determine if the generatecosts automatically setting was turned on
		 * for this resource?
		 */
		CostDataVo costDataVo = super.vo.getCostDataVo();
		if(null != costDataVo){
			if(null!=super.irEntity &&
					(null != irEntity.getCostData() 
							&& BooleanUtility.isTrue(costDataVo.getGenerateCosts()))){
				
				Boolean runResourceCosts=false;

				
				// verify agency/assign date is available
				if(null!=vo.getResourceVo().getAgencyVo()){
					if(!DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())
							&& DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo())){
						vo.getCostDataVo().setAssignDateVo(vo.getWorkPeriodVo().getCiCheckInDateVo());
					}
					
					if(null!=super.vo && DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
							
						// is incident autorun turned on?
						if(this.isIncidentAutoRunEnabled()==true){
							// Check data triggers to run cost
							int cnt = 0;
							
							cnt+=this.genCostChanged();
							cnt+=this.actualReleaseDateChanged();
							cnt+=this.agencyChanged();
							cnt+=this.assignDateChanged();
							cnt+=this.estimateDataOfArrivalChanged();
							cnt+=this.itemCodeChanged();
							
							if(cnt > 0)
								runResourceCosts=true;
						}
						
					}
				}
					
				if(runResourceCosts==true){
					// generate costs setting is turned on (and changed from previous off position)
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME+"RUNCOSTS");
					coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
				}
					
			}else{
				Boolean runResourceCosts=false;
				
				if(BooleanUtility.isTrue(costDataVo.getGenerateCosts())){
					if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCiCheckInDateVo()) && !DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo()) ){
						vo.getCostDataVo().setAssignDateVo(vo.getWorkPeriodVo().getCiCheckInDateVo());
						//vo.getCostDataVo().setAssignDate(DateTransferVo.getDate(vo.getWorkPeriodVo().getCiCheckInDateVo()));
					}
					
					// verify agency/assign date is available
					if(null!=vo.getResourceVo().getAgencyVo()){
						if(null!=super.vo && DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
								
							// is incident autorun turned on?
							if(this.isIncidentAutoRunEnabled()==true){
								runResourceCosts=true;
							}
						}
					}
					
					if(runResourceCosts==true){
						// generate costs setting is turned on (and changed from previous off position)
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME+"RUNCOSTS");
						coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
						dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
					}
				}
			}
		}
		
		return _OK;
	}

	private Boolean isIncidentAutoRunEnabled() throws Exception{
		Boolean val=false;
		
		IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
		Incident inc = incDao.getById(vo.getIncidentVo().getId());
		if(null != inc){
			if(inc.getCostAutoRun()==StringBooleanEnum.Y)
				val=true;
			
			incDao.flushAndEvict(inc);
		}
		
		return val;
	}
	
	private int genCostChanged(){
		try{
			Long costDataId = super.irEntity.getCostDataId();
			if(LongUtility.hasValue(costDataId)){
				CostDataDao cdDao=(CostDataDao)context.getBean("costDataDao");
				CostData cd = cdDao.getById(costDataId, CostDataImpl.class);
				cdDao.flushAndEvict(cd);
				
				Boolean origValue=BooleanUtility.isTrue(cd.getGenerateCosts());
	
				if(BooleanUtility.isFalse(origValue) && BooleanUtility.isTrue(super.vo.getCostDataVo().getGenerateCosts()))
					return 1;
			}
		}catch(Exception e){
			//smother
		}
		
		return 0;
	}

	private int actualReleaseDateChanged(){
		Date newDate=null;
		Date origDate=null;
		
		Date releaseDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmReleaseDateVo());
		if(DateUtil.hasValue(releaseDate)){
			newDate=releaseDate;
		}
		
		if(null != irEntity && DateUtil.hasValue(super.irEntity.getWorkPeriod().getDMReleaseDate())){
			origDate=irEntity.getWorkPeriod().getDMReleaseDate();
		}

		if(!DateUtil.hasValue(origDate) && DateUtil.hasValue(newDate))
			return 1;
		else if(DateUtil.hasValue(origDate) && !DateUtil.hasValue(newDate))
			return 1;
		else if(DateUtil.hasValue(origDate) && DateUtil.hasValue(newDate)){
			if(!DateUtil.isSameDate(origDate, newDate))
				return 1;
		}

		return 0;
	}
	
	private int estimateDataOfArrivalChanged(){
		Date newDate=null;
		Date origDate=null;

		// set dates
		if(null != irEntity){
			origDate=irEntity.getWorkPeriod().getDmTentativeArrivalDate();
		}
		
		newDate=DateTransferVo.getDate(vo.getWorkPeriodVo().getDmTentativeArrivalDateVo());
		
		if(!DateUtil.hasValue(origDate) && DateUtil.hasValue(newDate))
			return 1;
		else if(DateUtil.hasValue(origDate) && !DateUtil.hasValue(newDate))
			return 1;
		else if(DateUtil.hasValue(origDate) && DateUtil.hasValue(newDate)){
			if(!DateUtil.isSameDate(origDate, newDate))
				return 1;
		}

		return 0;
	}

	private int itemCodeChanged(){
		Long newKindId=0L;
		Long origKindId=0L;
		
		if(null != vo.getWorkPeriodVo() && null != vo.getWorkPeriodVo().getCurrentAssignmentVo()){
			if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo())
				newKindId=vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getId();
		}

		if(null != super.irEntity && null != irEntity.getWorkPeriod()){
			for(Assignment a : irEntity.getWorkPeriod().getAssignments()){
				if(!DateUtil.hasValue(a.getEndDate())){
					if(null!=a.getKindId())
						origKindId=a.getKindId();
					break;
				}
			}
		}
		
		if(LongUtility.hasValue(origKindId) && !LongUtility.hasValue(newKindId))
			return 1;
		else if(!LongUtility.hasValue(origKindId) && LongUtility.hasValue(newKindId))
			return 1;
		else if(LongUtility.hasValue(origKindId) && LongUtility.hasValue(newKindId)){
			if(origKindId.compareTo(newKindId) != 0)
				return 1;
		}
		
		return 0;
	}

	private int agencyChanged(){
		Long origId=0L;
		Long newId=0L;
		
		if(null != super.irEntity){
			origId=irEntity.getResource().getAgencyId();
		}
		if(null != vo.getResourceVo().getAgencyVo())
			newId=vo.getResourceVo().getAgencyVo().getId();
		
		if(LongUtility.hasValue(origId) && LongUtility.hasValue(newId)){
			if(origId.compareTo(newId)!=0)
				return 1;
		}else if(!LongUtility.hasValue(origId) && LongUtility.hasValue(newId)){
			return 1;
		}else if(LongUtility.hasValue(origId) && !LongUtility.hasValue(newId)){
			return 1;
		}
		
		return 0;
	}

	private int assignDateChanged(){
		Date newDate=null;
		Date origDate=null;

		// set dates
		if(null != super.irEntity && null != super.irEntity.getCostData()){
			origDate=irEntity.getCostData().getAssignDate();
		}
		if(null != super.vo && null != vo.getCostDataVo()){
			if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
				try{
					newDate=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
				}catch(Exception e){}
			}
		}
		
		if(!DateUtil.hasValue(origDate) && DateUtil.hasValue(newDate))
			return 1;
		else if(DateUtil.hasValue(origDate) && !DateUtil.hasValue(newDate))
			return 1;
		else if(DateUtil.hasValue(origDate) && DateUtil.hasValue(newDate)){
			if(!DateUtil.isSameDate(origDate, newDate))
				return 1;
		}

		return 0;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

		/*
		 * If additional action is needed,
		 * generate costs for the resource
		 */
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName+"RUNCOSTS");
		if(coaVo != null && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){

			IncidentResourceDao incResourceDao=(IncidentResourceDao)context.getBean("incidentResourceDao");
			Long irid=super.irEntity.getId();
			Collection<CostResourceDataVo> costResourceDataVos = incResourceDao.getCostResourceData(irid,null,null);

			CostGenerator costGen = new CostGenerator(this.context);
			for(CostResourceDataVo v : costResourceDataVos){
				if(v.getIncidentResourceId().compareTo(irid)==0){
					costGen.generateCosts(v, costResourceDataVos,false);
					
					if(LongUtility.hasValue(v.getParentResourceId())){
						costGen.generateCostsForParent(v.getParentResourceId(), costResourceDataVos);
					}
				}
			}
			
		}
	}

}
