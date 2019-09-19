package gov.nwcg.isuite.core.rules.incidentgroup.addincident;

import java.util.ArrayList;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckIncidentNotAlreadyAssignedRules extends AbstractAddIncidentRule implements IRule{
	public CheckIncidentNotAlreadyAssignedRules(ApplicationContext ctx, String rname)
	{
		super(ctx, rname);
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
			if(runRuleCheck(dialogueVo) == _OK) {
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(ruleName,true));
			}
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
		 *  Check if an incident has already been assigned to an incident group?
		 * 
		 * 	The system must prevent the user from adding the same Incident to multiple Incident Groups. 
  			(Applies to Use Cases 21.0003, 21.0004, 21.0005 and 21.0006.)
  		 *  	
		 */
		findAddedIncidents();
		int value = _OK;
		
		for(IncidentVo vo : addedIncidents) {
			if(incidentGroupDao.getIncidentGroupsIncidentId(vo.getId()) != null) {
				value = _FAIL;
				((ArrayList<IncidentVo>)newVo.getIncidentVos()).remove(vo);
				
				CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName);
				MessageVo msgVo;
				if(coaVo == null) {
					coaVo = new CourseOfActionVo();
					coaVo.setCoaName(ruleName);
					coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
					coaVo.setIsComplete(true);
					
					dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
					
					msgVo = new MessageVo();
					msgVo.setMessageType(MessageTypeEnum.INFO);
					msgVo.setTitleProperty("text.incidentGroup");
					msgVo.setMessageProperty("error.incidentAlreadyAssignedToAnotherGroup");
					msgVo.setParameters(new String[]{": \n" + vo.getIncidentName()});
					
					coaVo.setMessageVo(msgVo);
				} else {
					msgVo = coaVo.getMessageVo();
					String msg = msgVo.getParameters()[0];
					msg = msg + " \n" + vo.getIncidentName();
					msgVo.getParameters()[0] = msg;
				}
			}
		}
		
		return value;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
		

}
