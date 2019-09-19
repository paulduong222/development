package gov.nwcg.isuite.core.rules.incidentgroup.adduser;

import java.util.ArrayList;

import gov.nwcg.isuite.core.vo.IncidentGroupUserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import org.springframework.context.ApplicationContext;

public class CheckUsersNotAlreadyAddedRules extends AbstractAddUserToIGRule implements IRule{

	public CheckUsersNotAlreadyAddedRules(ApplicationContext ctx, String rname)
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
		 *  Check if any of the users being added are already
		 *  incident group users.
		 */
		findAddedUsers();
		int count = 0;
		
		for(IncidentGroupUserVo ugu : addedUsers) {
			count = 0;
			for(int i=newVo.getIncidentGroupUsers().size()-1; i>-1; i--) {
				if(ugu.getUserVo().getId().equals(
						((ArrayList<IncidentGroupUserVo>) newVo.getIncidentGroupUsers()).get(i).getUserVo().getId() )) {
					count++;
					if(count > 1) {
						((ArrayList<IncidentGroupUserVo>)newVo.getIncidentGroupUsers()).remove(i);
					}
				}
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
