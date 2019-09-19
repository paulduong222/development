package gov.nwcg.isuite.core.rules.datatransfer.exportdata;

import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class CheckSingleIncidentRules extends AbstractExportDataRule implements IRule {
	public static final String _RULE_NAME = ExportDataRuleFactory.RuleEnum.CHECK_SINGLE_INCIDENT.name();

	public CheckSingleIncidentRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/*
	 * (non-Javadoc)
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		/*
		 * can only export an incident by itself if the following rules are true
		 * 	1) incident is not part of an incident group
		 *  2) incident is part of an incident group but is the only incident in the group
		 *  
		 *  3/31/2015  - defect 4804
		 *  3) if there is only 1 incident, regardless if the group or incident 
		 *     is selected, the data transfer file should always be for the single incident
		 */
		// check for incident or group id
		if(LongUtility.hasValue(vo.getIncidentId()) ){
			// is incident part of a group
			IncidentDao incDao=(IncidentDao)context.getBean("incidentDao");
			Long groupId = incDao.getIncidentGroupId(vo.getIncidentId());
			if(LongUtility.hasValue(groupId)){
				// check how many incidents are in the group
				IncidentGroupDao igDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				Collection<Long> incIds = igDao.getIncidentIdsInGroup(groupId);
				if(CollectionUtility.hasValue(incIds) && incIds.size() > 1){
					// cannot export incident
					ErrorObject error2 = new ErrorObject("error.800000"
															,"You cannot export the Incident by itself because the incident is part of an Incident Group with more than 1 incident.");
					errorObjects.add(error2);
				}
			}
		}
		
		if(CollectionUtility.hasValue(errorObjects)){
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}

		return _OK;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
