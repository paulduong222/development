package gov.nwcg.isuite.core.rules.timeassignadjust.save;

import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckRunCostAutoRules extends AbstractTimeAssignAdjustSaveRule implements IRule{
	public static final String _RULE_NAME=TimeAssignAdjustSaveRuleFactory.RuleEnum.CHECK_COST_AUTORUN.name();

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
	
		Boolean val=false;
		
		Long incId = super.taaDao.getIncidentId(super.timeAssignAdjustVo.getAssignmentId());

		if(LongUtility.hasValue(incId)){
			IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
			Incident inc = incDao.getById(incId);
			if(null != inc){
				if(inc.getCostAutoRun()==StringBooleanEnum.Y)
					val=true;
				
				incDao.flushAndEvict(inc);
			}
		}
		
		if(BooleanUtility.isTrue(val)){
			// generate costs setting is turned on (and changed from previous off position)
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME+"RUNCOSTS");
			coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			
			Long irId = super.taaDao.getIncidentResourceId(super.timeAssignAdjustVo.getAssignmentId());
			coaVo.setStoredObject(irId);

			dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
		}
		
		return _OK;
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
			if(null != coaVo.getStoredObject()){
				Long irId = 0L;
				try{
					irId=TypeConverter.convertToLong(coaVo.getStoredObject());
				}catch(Exception e){
					// smother
				}
				
				if(LongUtility.hasValue(irId)){
					IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
					IncidentResource irEntity = irDao.getById(irId, IncidentResourceImpl.class);
					
					// Try and run the cost process for this resource
					Long irid=irEntity.getId();
					Collection<CostResourceDataVo> costResourceDataVos = irDao.getCostResourceData(irid,null,null);

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
	}

}
