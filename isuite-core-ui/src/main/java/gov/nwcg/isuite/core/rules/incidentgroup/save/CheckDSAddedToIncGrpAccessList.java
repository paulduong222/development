package gov.nwcg.isuite.core.rules.incidentgroup.save;

import gov.nwcg.isuite.core.vo.IncidentGroupUserVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.xml.SystemRoleType;


import org.springframework.context.ApplicationContext;

public class CheckDSAddedToIncGrpAccessList extends	AbstractIncidentGroupSaveRule implements IRule {

	public CheckDSAddedToIncGrpAccessList(ApplicationContext ctx, String rname) {
		super(ctx, rname);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;


			if(super.isCurrentCourseOfAction(dialogueVo, ruleName)){

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;

			}else{
				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;


				/*
				 * Do not mark as completed, as this will need to happen again 
				 * if a prompt sends this series of rule checks back to the client
				 */
//				dialogueVo.getProcessedCourseOfActionVos()
//				.add(super.buildNoActionCoaVo(ruleName,true));
			}

		}catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}

	/**
	 * 
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*	
		 * 	21.0001 – Manage Incident Groups - Enterprise
		 *  
		 *  	Business Requirements
		 *  		
		 *  		Add Incident Groups Business Requirements
		 *  
		 *  			17.	When a non-privileged user with a Data Steward role 
		 *  				creates a new Incident Group, the system must automatically 
		 *  				add that User Account to the Incident Group's access list. 
		 */

		if(null == originalVo) {
			//Check for Data Steward role.
			for(SystemRoleVo srvo : super.getUserSessionVo().getUserRoleVos()) {
				if(srvo.getRoleName().equals(SystemRoleType.ROLE_DATA_STEWARD.name())) {
					IncidentGroupUserVo igUser = new IncidentGroupUserVo();
					igUser.setIncidentGroupVo(newVo);
					igUser.setUserVo(super.getUserVo());
					
					//IncidentGroupUserVo iguv = IncidentGroupUserVo.getInstance(igUser, true);
					newVo.getIncidentGroupUsers().add(igUser);
					break;
				}
			}
		}
		
		return _OK;
	}

	/**
	 * 
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {

		// check prompt result
		if(getPromptResult(dialogueVo) == PromptVo._YES) {

			// add to processed
			dialogueVo.getCourseOfActionVo().setIsComplete(true);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

			// continue;

		}else if(getPromptResult(dialogueVo) == PromptVo._NO){

			// cannot continue if prompt was cancel post
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incident", "text.abortProcess" , new String[]{"save incident account code"}, MessageTypeEnum.INFO));
			coaVo.setIsDialogueEnding(Boolean.TRUE);

			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
		}

		return _OK;
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

		
	}
}
