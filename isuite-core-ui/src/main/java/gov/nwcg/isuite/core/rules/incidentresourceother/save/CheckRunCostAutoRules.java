package gov.nwcg.isuite.core.rules.incidentresourceother.save;

import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.persistence.CostDataDao;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
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
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckRunCostAutoRules extends AbstractSaveIRORule implements IRule{
	public static final String _RULE_NAME=SaveIRORuleFactory.RuleEnum.CHECK_RUN_COST_AUTO.name();

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
		if(null != costDataVo && BooleanUtility.isTrue(vo.getCostDataVo().getGenerateCosts())){
			Boolean runResourceCosts=false;
					
			// verify assign date is available
			if(null!=super.vo && DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
							
				// is incident autorun turned on?
				if(this.isIncidentAutoRunEnabled()==true){

					// Check data triggers to run cost
					int cnt = 0;

					if(!LongUtility.hasValue(vo.getId())){
						cnt=1;
					}
					
					if(null != super.iroEntity){
						if(LongUtility.hasValue(iroEntity.getId())){
							IncidentResourceOtherDao dao = (IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
							try{
								Date origDate=(Date)(dao.getCostDataValue("ASSIGN_DATE", iroEntity.getId()));
								Long origKindId=TypeConverter.convertToLong(dao.getCostDataValue("KIND_ID", iroEntity.getId()));
								Long origIacId=TypeConverter.convertToLong(dao.getCostDataValue("INCIDENT_ACCOUNT_CODE_ID", iroEntity.getId()));
								
								cnt+=this.genCostChanged();
								cnt+=this.assignDateChanged(origDate);
								cnt+=this.itemCodeChanged(origKindId);
								cnt+=this.accountingCodeChanged(origIacId);
							}catch(Exception e){}
							
						}
					}
						
					if(cnt > 0)
						runResourceCosts=true;
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
			Long costDataId = super.iroEntity.getCostDataId();
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
	
	private int accountingCodeChanged(Long origIacId){
		Long newIacId=0L;
		
		if(null != super.vo && null!=vo.getResourceOtherVo() ){
			if(null!=vo.getResourceOtherVo().getIncidentAccountCodeVo())
				newIacId=vo.getResourceOtherVo().getIncidentAccountCodeVo().getId();
		}

		if(LongUtility.hasValue(origIacId) && !LongUtility.hasValue(newIacId))
			return 1;
		else if(!LongUtility.hasValue(origIacId) && LongUtility.hasValue(newIacId))
			return 1;
		else if(LongUtility.hasValue(origIacId) && LongUtility.hasValue(newIacId)){
			if(origIacId.compareTo(newIacId) != 0)
				return 1;
		}
		
		return 0;
	}
	
	private int itemCodeChanged(Long origKindId){
		Long newKindId=0L;
		
		if(null != super.vo && null!=vo.getResourceOtherVo() ){
			if(null!=vo.getResourceOtherVo().getKindVo())
				newKindId=vo.getResourceOtherVo().getKindVo().getId();
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

	private int assignDateChanged(Date origDate){
		Date newDate=null;

		// set dates
		if(null != super.vo && null != vo.getCostDataVo()){
			if(DateTransferVo.hasDateString(vo.getCostDataVo().getAssignDateVo())){
				try{
					newDate=DateTransferVo.getTransferDate(vo.getCostDataVo().getAssignDateVo());
				}catch(Exception smother){}
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
			
			try{
				// Try and run the cost process for this resource
				IncidentResourceOtherDao incResourceOtherDao=(IncidentResourceOtherDao)context.getBean("incidentResourceOtherDao");
				Long iroid=iroEntity.getId();
				Collection<CostResourceDataVo> costResourceDataVos = incResourceOtherDao.getCostResourceData(iroid,null,null);

				CostGenerator costGen = new CostGenerator(this.context);
				for(CostResourceDataVo v : costResourceDataVos){
					if(v.getIncidentResourceId().compareTo(iroid)==0){
						costGen.generateCosts(v, costResourceDataVos,false);
					}
				}
			}catch(Exception e){}
		}
	}

}
