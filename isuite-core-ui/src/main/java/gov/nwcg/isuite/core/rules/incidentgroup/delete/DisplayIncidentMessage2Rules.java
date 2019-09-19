package gov.nwcg.isuite.core.rules.incidentgroup.delete;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class DisplayIncidentMessage2Rules extends AbstractIncidentGroupDeleteRule implements IRule{
	public static final String _RULE_NAME=IncidentGroupDeleteRuleFactory.RuleEnum.DISPLAY_MESSAGE_INCIDENTS2.name();

	public DisplayIncidentMessage2Rules(ApplicationContext ctx)
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

	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * Defect 4446
		 * During the functional meeting held on 11/13/2014, 
		 * the decision was made to require a user to remove all incidents from an Incident Group 
		 * before allowing the user to delete that Incident Group. 
		 * Setting priority to Urgent and the Needed By to Release 1.
		 */
		
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		// check if the incident group has any incidents still associated with it?
		Collection<Long> incidentIds = super.incidentGroupDao.getIncidentIdsInGroup(super.incidentGroupId);
		if(CollectionUtility.hasValue(incidentIds)){
			ErrorObject error2 = 
				new ErrorObject("error.800000"
									,"All Incidents in the Incident Group must be removed before the group can be deleted.");
			errorObjects.add(error2);
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
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
