package gov.nwcg.isuite.core.rules.incident.deleteincident;

import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckCrossCostRecordsInverseRules extends AbstractDeleteIncidentRule implements IRule {
	public static final String _RULE_NAME = DeleteIncidentRuleFactory.RuleEnum.CHECK_CROSS_COST_RECORDS_INVERSE.name();

	public CheckCrossCostRecordsInverseRules(ApplicationContext ctx, String rname)
	{
		super(ctx,rname);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;

			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) 
				return _FAIL;
			
			dialogueVo.getProcessedCourseOfActionVos().add(super.buildNoActionCoaVo(ruleName, true));
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * 
		 * Check if resources from other incidents in an incident
		 * group has cost records with an accounting code
		 * from the incident being removed. 
		 * 
		 */
		try{
			
			if(null != incidentEntity){
				IncidentGroupDao incidentGroupDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentDao incidentDao=(IncidentDao)context.getBean("incidentDao");

				Long igId=incidentDao.getIncidentGroupId(incidentEntity.getId());

				if(LongUtility.hasValue(igId)){
					int cnt = incidentGroupDao.getCrossIncidentCostRecordsInverse(incidentEntity.getId());

					if(cnt > 0){
					
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
						coaVo.setIsDialogueEnding(true);
						
						Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
						String msg = "Unable to delete the incident.  Incidents that have resources with cross incident cost records cannot be deleted.";
						ErrorObject error= new ErrorObject("info.generic",new String[]{msg});
						if(null != error)errorObjects.add(error);
						coaVo.setErrorObjectVos(errorObjects);
						dialogueVo.setCourseOfActionVo(coaVo);
						
						return _FAIL;
					}
				}
			}
		}catch(Exception failSafe){
			
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
