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

public class CheckCrossTimePostingsInverseRules extends AbstractDeleteIncidentRule implements IRule{
	public static final String _RULE_NAME = DeleteIncidentRuleFactory.RuleEnum.CHECK_CROSS_COST_RECORDS_INVERSE.name();
	
	public CheckCrossTimePostingsInverseRules(ApplicationContext ctx, String rname)
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
		 *  
		 * Check if incident is being removed from incident group.
		 * If so, verify incident's acct code not in other group incident time postings.
		 */
		try{
			if(null != incidentEntity){
				IncidentGroupDao incidentGroupDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentDao incidentDao=(IncidentDao)context.getBean("incidentDao");
				
				Long igId=incidentDao.getIncidentGroupId(incidentEntity.getId());

				if(LongUtility.hasValue(igId)){
					int cnt = incidentGroupDao.getCrossIncidentTimePostingsInverse(igId,incidentEntity.getId());
					if(cnt > 0){
						CourseOfActionVo coaVo = new CourseOfActionVo();
						coaVo.setCoaName(_RULE_NAME);
						coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
						coaVo.setIsDialogueEnding(true);
						
						Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
						String msg = "Unable to delete the incident.  Incidents that have resources with cross incident time postings cannot be deleted.";
						ErrorObject error= new ErrorObject("info.generic",new String[]{msg});
						if(null != error)errorObjects.add(error);
						coaVo.setErrorObjectVos(errorObjects);
						dialogueVo.setCourseOfActionVo(coaVo);
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
