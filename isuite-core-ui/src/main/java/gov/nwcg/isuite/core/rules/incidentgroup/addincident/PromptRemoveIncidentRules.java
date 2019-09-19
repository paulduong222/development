package gov.nwcg.isuite.core.rules.incidentgroup.addincident;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class PromptRemoveIncidentRules extends AbstractAddIncidentRule implements IRule{
	
	public PromptRemoveIncidentRules(ApplicationContext ctx, String rname)
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

			if(isCurrentCourseOfAction(dialogueVo, ruleName)){

				// add to processed
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

				return checkPromptResult(dialogueVo);
				
			}else{
				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * Check for incidents that are getting removed
		 */
		if(null != originalVo){
			findRemovedIncidents();
			if(removedIncidents.size()>0) {
				String names = "";
				for(IncidentVo ivo : removedIncidents) {
					String s = ivo.getIncidentName().toUpperCase();
					if(!StringUtility.hasValue(names))
						names=s;
					else
						names=names+", " + s;
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(ruleName);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.incidentGroup"
												,"prompt.removeItem"
												,new String[]{"Incident", names}
												,PromptVo._YES | PromptVo._NO ));
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
		
		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
			
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}else if(getPromptResult(dialogueVo) == PromptVo._NO){
			// TODO: TWO OPTIONS: Cancel all of the save, OR undo the remove incidents.
			// If take option one, how two set the client side view back to including removed incidents?
			// If take option two, add the removed incidents back to the newVo and continue

			// Mar 28, 2012--Trudi says to go with option 2
			
			// Option1: cannot continue if prompt was cancel post
/*			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("SAVE IG COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentGroup", "text.abortProcess" , new String[]{"removing incident"}, MessageTypeEnum.URGENT));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(coaVo);
			
			// add removed incidents back into the newVo, and return the newVo in the dialogueVo to update the view
			findRemovedIncidents();
			newVo.getIncidentVos().addAll(removedIncidents);
			dialogueVo.setResultObject(newVo);
			
			return _FAIL;
*/			
			// Option 2: add removed incidents back into newVo
			findRemovedIncidents();
			newVo.getIncidentVos().addAll(removedIncidents);
			
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getCourseOfActionVo().setMessageVo(
					new MessageVo("text.incidentGroup", "info.noItemsRemoved" , new String[]{"Incident"}, MessageTypeEnum.URGENT));
			
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		findRemovedIncidents();
		
		if(removedIncidents.size()>0) {
			String msg = "";
			for(IncidentVo igInc : removedIncidents) {
				if(msg.length()==0)
					msg = msg + igInc.getIncidentName();
				else
					msg = msg + ", " + igInc.getIncidentName();
			}
		
			MessageVo msgVo = new MessageVo();
			msgVo.setMessageType(MessageTypeEnum.INFO);
			msgVo.setTitleProperty("text.incidentGroup");
			msgVo.setMessageProperty("info.itemRemovedFromItem");
			msgVo.setParameters(new String[]{"Incident"
					,"Incident Group"
					,msg});
			
			dialogueVo.getCourseOfActionByName(ruleName).setMessageVo(msgVo);
		}
	}

}
