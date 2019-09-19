package gov.nwcg.isuite.core.rules.incidentgroup.adduser;

import gov.nwcg.isuite.core.vo.IncidentGroupUserVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class AddUsersRules extends AbstractAddUserToIGRule implements IRule{

	public AddUsersRules(ApplicationContext ctx, String rname)
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
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {	
		//add users
		findAddedUsers();
		if(addedUsers.size() > 0) {
			MessageVo msgVo = new MessageVo();
			msgVo.setTitleProperty("text.incidentGroup");
			msgVo.setMessageProperty("info.itemAddedToItem");
			msgVo.setMessageType(MessageTypeEnum.INFO);
			
			String str = "";
			
			for(IncidentGroupUserVo uVo : addedUsers){
				if(str.length()==0)
					str = str + uVo.getUserVo().getLoginName();
				else
					str = str + ", " + uVo.getUserVo().getLoginName();
			}
			
			msgVo.setParameters(new String[] {"User", "Incident Group", str});
			dialogueVo.getCourseOfActionByName(ruleName).setMessageVo(msgVo);
		}
	}

}
