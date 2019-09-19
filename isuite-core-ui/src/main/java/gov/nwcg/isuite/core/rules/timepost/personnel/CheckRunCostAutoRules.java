package gov.nwcg.isuite.core.rules.timepost.personnel;

import gov.nwcg.isuite.core.cost.CostGenerator;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class CheckRunCostAutoRules extends AbstractPersonnelRule implements IRule{
	public static final String _RULE_NAME=TimePostPersonnelRuleFactory.RuleEnum.CHECK_RUN_COST_AUTO.name();

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

		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		IncidentResource irEntity = irDao.getByAssignmentTimeId(vo.getAssignmentTimeId());
		if(null != irEntity){
			IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
			Incident incident = incDao.getById(irEntity.getIncidentId());

			if(BooleanUtility.isTrue(incident.getCostAutoRun().getValue())){
				CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
				courseOfActionVo.setCoaName(_RULE_NAME+"RUNCOSTS");
				courseOfActionVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
				courseOfActionVo.setIsComplete(true);
				courseOfActionVo.setStoredObject(super.vo.getPostStartDate());
				dialogueVo.getProcessedCourseOfActionVos().add(courseOfActionVo);
			}
			
			irDao.flushAndEvict(irEntity);
			incDao.flushAndEvict(incident);
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(_RULE_NAME+"RUNCOSTS");

		if(null != coaVo && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != coaVo.getStoredObject()){
				Date postDate=(Date)coaVo.getStoredObject();
				
				try{
					IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
					IncidentResource irEntity = irDao.getByAssignmentTimeId(vo.getAssignmentTimeId());
					
					// Try and run the cost process for this resource
					IncidentResourceDao incResourceDao=(IncidentResourceDao)context.getBean("incidentResourceDao");
					Long irid=irEntity.getId();
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

					irDao.flushAndEvict(irEntity);
				}catch(Exception e){
					//smother
				}
				
			}
		}
		
	}

}
