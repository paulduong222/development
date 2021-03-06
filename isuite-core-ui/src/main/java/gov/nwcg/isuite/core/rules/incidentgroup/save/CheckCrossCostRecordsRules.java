package gov.nwcg.isuite.core.rules.incidentgroup.save;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

public class CheckCrossCostRecordsRules extends AbstractIncidentGroupSaveRule implements IRule {
	public static final String _RULE_NAME = IncidentGroupSaveRuleFactory.RuleEnum.CHECK_CROSS_COST_RECORDS.name();

	public CheckCrossCostRecordsRules(ApplicationContext ctx, String rname)
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
		 * Check if any resources in the incident being removed from an
		 * incident group has cost records with an accounting code from
		 * another incident in the group.
		 * 
		 */
		if(null != entity){
			Collection<Incident> removeList = new ArrayList<Incident>();
			removeList=IncidentVo.toEntityRemoveList(newVo.getIncidentVos(),entity.getIncidents());
			
			String incNames="";
			
			for(Incident inc : removeList){
				// check for cross incident time postings
				int cnt = super.incidentGroupDao.getCrossIncidentCostRecords(inc.getId());
				if(cnt > 0){
					if(StringUtility.hasValue(incNames)){
						incNames+=", "+inc.getIncidentName();
					}else{
						incNames=inc.getIncidentName();
					}
				}
			}
			
			if(StringUtility.hasValue(incNames)){
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
				coaVo.setIsDialogueEnding(true);
				
				Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
				String msg = "Unable to remove the following incidents (" + incNames+").  Incidents that have resources with cross incident cost records cannot be removed from an Incident Group.";
				ErrorObject error= new ErrorObject("info.generic",new String[]{msg});
				if(null != error)errorObjects.add(error);
				coaVo.setErrorObjectVos(errorObjects);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
